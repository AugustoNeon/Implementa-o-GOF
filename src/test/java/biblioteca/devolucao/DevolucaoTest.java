package biblioteca.devolucao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Livro;
import biblioteca.dominio.Pessoa;
import biblioteca.multa.ConfiguracaoAdministrativa;
import biblioteca.multa.ServicoDeMultas;
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

class DevolucaoTest {

    private UnidadeDeTrabalho unidade = new UnidadeDeTrabalhoEmMemoria();
    private RepositorioLivro acervo = new RepositorioLivroEmMemoria(unidade);
    private RepositorioMovimentacao movimentacao = new RepositorioMovimentacaoEmMemoria(unidade);
    private RepositorioRelacionamento relacionamento = new RepositorioRelacionamentoEmMemoria(unidade);

    private ServicoDeMultas servicoDeMultas = new ServicoDeMultas(new ConfiguracaoAdministrativa());
    private OuvinteDeInspecao inspecao = new OuvinteDeInspecao();
    private OuvinteDeIndicadores indicadores = new OuvinteDeIndicadores();
    private EnviadorSimulado enviador = new EnviadorSimulado();
    private PublicadorDeDevolucao publicador = new PublicadorDeDevolucao();
    private FachadaDeCirculacao fachada;

    @BeforeEach
    void preparar() {
        acervo.salvar(new Livro("L1", "Padroes de Projeto"));
        Pessoa pessoa = new Pessoa("Maria", "Silva", "123");
        relacionamento.salvarPessoa(pessoa);
        relacionamento.salvarEstudante(new Estudante("E1", pessoa));

        publicador.adicionar(inspecao);
        publicador.adicionar(new OuvinteDeMulta(servicoDeMultas));
        publicador.adicionar(indicadores);
        publicador.adicionar(new OuvinteDeEmail(enviador));

        fachada = new FachadaDeCirculacao(acervo, movimentacao, relacionamento, unidade, publicador);
        fachada.definirDataAtual(LocalDate.of(2026, 11, 1));
        fachada.solicitarReserva("L1", "E1");
        fachada.emprestarLivro("L1", "E1");
    }

    @Test
    void R05_devolucaoAtrasadaAlimentaInspecaoMultaEIndicadores() {
        fachada.definirDataAtual(LocalDate.of(2026, 12, 6));

        fachada.registrarDevolucao("L1", "E1");

        assertTrue(inspecao.getLivrosParaInspecionar().contains("L1"));
        assertNotNull(servicoDeMultas.consultarMulta("L1", "E1"));
        assertEquals(5.0, servicoDeMultas.consultarMulta("L1", "E1").getValor(), 0.001);
        assertEquals(1, indicadores.getTotalDeDevolucoes());
        assertEquals(1, indicadores.getTotalDeAtrasos());
        assertEquals(1, enviador.getMensagensEnviadas().size());
        assertEquals(EstadoLivro.DEVOLVIDO, acervo.buscarPorId("L1").getEstado());
    }

    @Test
    void R05_devolucaoNoPrazoNaoGeraMulta() {
        fachada.definirDataAtual(LocalDate.of(2026, 12, 1));

        fachada.registrarDevolucao("L1", "E1");

        assertNull(servicoDeMultas.consultarMulta("L1", "E1"));
        assertTrue(inspecao.getLivrosParaInspecionar().contains("L1"));
        assertEquals(1, indicadores.getTotalDeDevolucoes());
        assertEquals(0, indicadores.getTotalDeAtrasos());
        assertEquals(EstadoLivro.DEVOLVIDO, acervo.buscarPorId("L1").getEstado());
    }

    @Test
    void R05_falhaNoEmailNaoAfetaOsOutrosNemDesfazADevolucao() {
        PublicadorDeDevolucao publicadorComFalha = new PublicadorDeDevolucao();
        publicadorComFalha.adicionar(new OuvinteDeEmail((destino, mensagem) -> {
            throw new RuntimeException("sem rede");
        }));
        publicadorComFalha.adicionar(inspecao);
        publicadorComFalha.adicionar(new OuvinteDeMulta(servicoDeMultas));
        FachadaDeCirculacao fachadaComFalha = new FachadaDeCirculacao(acervo, movimentacao,
                relacionamento, unidade, publicadorComFalha);
        fachadaComFalha.definirDataAtual(LocalDate.of(2026, 12, 6));

        fachadaComFalha.registrarDevolucao("L1", "E1");

        assertTrue(inspecao.getLivrosParaInspecionar().contains("L1"));
        assertNotNull(servicoDeMultas.consultarMulta("L1", "E1"));
        assertNotNull(movimentacao.consultarDevolucao("L1", "E1"));
        assertEquals(EstadoLivro.DEVOLVIDO, acervo.buscarPorId("L1").getEstado());
    }

}
