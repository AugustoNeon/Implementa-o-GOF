package biblioteca.solicitacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import biblioteca.circulacao.CirculacaoException;
import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Livro;
import biblioteca.dominio.Pessoa;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import biblioteca.persistencia.memoria.RepositorioLivroEmMemoria;
import biblioteca.persistencia.memoria.RepositorioMovimentacaoEmMemoria;
import biblioteca.persistencia.memoria.RepositorioRelacionamentoEmMemoria;
import biblioteca.persistencia.memoria.UnidadeDeTrabalhoEmMemoria;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolicitacaoTest {

    private UnidadeDeTrabalho unidade = new UnidadeDeTrabalhoEmMemoria();
    private RepositorioLivro acervo = new RepositorioLivroEmMemoria(unidade);
    private RepositorioMovimentacao movimentacao = new RepositorioMovimentacaoEmMemoria(unidade);
    private RepositorioRelacionamento relacionamento = new RepositorioRelacionamentoEmMemoria(unidade);
    private FachadaDeCirculacao fachada;
    private ExecutorDeSolicitacoes executor = new ExecutorDeSolicitacoes();

    @BeforeEach
    void preparar() {
        acervo.salvar(new Livro("L1", "Padroes de Projeto"));
        Pessoa pessoa = new Pessoa("Maria", "Silva", "123");
        relacionamento.salvarPessoa(pessoa);
        relacionamento.salvarEstudante(new Estudante("E1", pessoa));
        fachada = new FachadaDeCirculacao(acervo, movimentacao, relacionamento, unidade);
        fachada.definirDataAtual(LocalDate.of(2026, 11, 1));
        fachada.solicitarReserva("L1", "E1");
        fachada.emprestarLivro("L1", "E1");
    }

    @Test
    void R06_renovacaoComMesmoIdentificadorAcrescentaApenas30Dias() {
        Solicitacao solicitacao = new SolicitacaoDeRenovacao("S1", fachada, "L1", "E1");

        executor.executar(solicitacao);
        executor.executar(solicitacao);

        Emprestimo emprestimo = movimentacao.consultarEmprestimo("L1", "E1");
        assertEquals(LocalDate.of(2026, 12, 31), emprestimo.getDataVencimento());
        assertEquals(1, emprestimo.getNumRenovacoes());
    }

    @Test
    void R06_solicitacoesDiferentesContinuamLimitadasADuasRenovacoes() {
        executor.executar(new SolicitacaoDeRenovacao("S1", fachada, "L1", "E1"));
        executor.executar(new SolicitacaoDeRenovacao("S2", fachada, "L1", "E1"));

        assertThrows(CirculacaoException.class,
                () -> executor.executar(new SolicitacaoDeRenovacao("S3", fachada, "L1", "E1")));

        assertEquals(2, movimentacao.consultarEmprestimo("L1", "E1").getNumRenovacoes());
    }

    @Test
    void R06_duasSubmissoesSimultaneasExecutamUmaSoVez() throws Exception {
        Solicitacao solicitacao = new SolicitacaoDeRenovacao("S1", fachada, "L1", "E1");
        Thread t1 = new Thread(() -> executor.executar(solicitacao));
        Thread t2 = new Thread(() -> executor.executar(solicitacao));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(1, movimentacao.consultarEmprestimo("L1", "E1").getNumRenovacoes());
    }

}
