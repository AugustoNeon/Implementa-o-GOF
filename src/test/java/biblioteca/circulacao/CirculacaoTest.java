package biblioteca.circulacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import biblioteca.dominio.Comprovante;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Livro;
import biblioteca.dominio.Pessoa;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import biblioteca.persistencia.memoria.RepositorioMovimentacaoEmMemoria;
import biblioteca.persistencia.memoria.RepositorioLivroEmMemoria;
import biblioteca.persistencia.memoria.RepositorioRelacionamentoEmMemoria;
import biblioteca.persistencia.memoria.UnidadeDeTrabalhoEmMemoria;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CirculacaoTest {

    private UnidadeDeTrabalho unidade = new UnidadeDeTrabalhoEmMemoria();
    private RepositorioLivro acervo = new RepositorioLivroEmMemoria(unidade);
    private RepositorioMovimentacao movimentacao = new RepositorioMovimentacaoEmMemoria(unidade);
    private RepositorioRelacionamento relacionamento = new RepositorioRelacionamentoEmMemoria(unidade);
    private FachadaDeCirculacao fachada;

    @BeforeEach
    void preparar() {
        acervo.salvar(new Livro("L1", "Padroes de Projeto"));
        Pessoa pessoa = new Pessoa("Maria", "Silva", "123");
        relacionamento.salvarPessoa(pessoa);
        relacionamento.salvarEstudante(new Estudante("E1", pessoa));
        fachada = new FachadaDeCirculacao(acervo, movimentacao, relacionamento, unidade);
        fachada.definirDataAtual(LocalDate.of(2026, 11, 1));
    }

    @Test
    void R04_efetivacaoDeEmprestimoCoordenaTodosOsPassos() {
        fachada.solicitarReserva("L1", "E1");
        assertNotNull(movimentacao.consultarReserva("L1", "E1"));

        Comprovante comprovante = fachada.emprestarLivro("L1", "E1");

        assertNull(movimentacao.consultarReserva("L1", "E1"));
        assertNotNull(movimentacao.consultarEmprestimo("L1", "E1"));
        assertEquals(EstadoLivro.EMPRESTADO, acervo.buscarPorId("L1").getEstado());
        assertEquals("EMPRESTIMO", comprovante.getTipo());
        assertEquals(LocalDate.of(2026, 12, 1), comprovante.getDataVencimento());
    }

    @Test
    void R04_falhaNoMeioDesfazTudo() {
        RepositorioMovimentacao movimentacaoComDefeito = new RepositorioMovimentacaoEmMemoria(unidade) {
            @Override
            public void adicionarEmprestimo(Emprestimo emprestimo) {
                throw new RuntimeException("falha simulada");
            }
        };
        FachadaDeCirculacao fachadaComDefeito = new FachadaDeCirculacao(acervo,
                movimentacaoComDefeito, relacionamento, unidade);
        fachadaComDefeito.solicitarReserva("L1", "E1");

        assertThrows(RuntimeException.class, () -> fachadaComDefeito.emprestarLivro("L1", "E1"));

        assertEquals(EstadoLivro.RESERVADO, acervo.buscarPorId("L1").getEstado());
        assertNotNull(movimentacaoComDefeito.consultarReserva("L1", "E1"));
        assertNull(movimentacaoComDefeito.consultarEmprestimo("L1", "E1"));
    }

}
