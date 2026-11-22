package biblioteca.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;
import biblioteca.persistencia.relacional.RepositorioLivroRelacional;
import biblioteca.persistencia.relacional.RepositorioMovimentacaoRelacional;
import biblioteca.persistencia.relacional.RepositorioRelacionamentoRelacional;
import biblioteca.persistencia.relacional.UnidadeDeTrabalhoRelacional;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelacionalTest {

    private FabricaDeRepositorios fabrica;
    private UnidadeDeTrabalho unidade;

    @BeforeEach
    void preparar() throws Exception {
        fabrica = new Configuracao("producao").getFabrica();
        unidade = fabrica.criarUnidadeDeTrabalho();
        UnidadeDeTrabalhoRelacional relacional = (UnidadeDeTrabalhoRelacional) unidade;
        try (Statement st = relacional.getConexao().createStatement()) {
            for (String tabela : new String[] {"livro", "pessoa", "estudante", "atendente",
                    "reserva", "emprestimo", "devolucao"}) {
                st.executeUpdate("DELETE FROM " + tabela);
            }
        }
        unidade.confirmar();
    }

    @AfterEach
    void encerrar() {
        unidade.fechar();
    }

    @Test
    void R08_configuracaoDeProducaoFornecaRepositoriosMySqlComMesmaUnidade() {
        RepositorioLivro livros = fabrica.criarRepositorioLivro(unidade);
        RepositorioMovimentacao movimentacao = fabrica.criarRepositorioMovimentacao(unidade);
        RepositorioRelacionamento relacionamento = fabrica.criarRepositorioRelacionamento(unidade);

        assertTrue(unidade instanceof UnidadeDeTrabalhoRelacional);
        assertTrue(livros instanceof RepositorioLivroRelacional);
        assertTrue(movimentacao instanceof RepositorioMovimentacaoRelacional);
        assertTrue(relacionamento instanceof RepositorioRelacionamentoRelacional);
    }

    @Test
    void R08_naoMisturaFamilias() {
        UnidadeDeTrabalho unidadeEmMemoria = new Configuracao("teste").getFabrica().criarUnidadeDeTrabalho();
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.criarRepositorioLivro(unidadeEmMemoria));
    }

    @Test
    void livroEhGravadoEReconstituidoComSeuEstado() {
        RepositorioLivro livros = fabrica.criarRepositorioLivro(unidade);
        Livro livro = new Livro("L1", "Padroes de Projeto");
        livro.atualizarEstado(EstadoLivro.RENOVADO);

        livros.salvar(livro);
        unidade.confirmar();

        Livro lido = livros.buscarPorId("L1");
        assertEquals("Padroes de Projeto", lido.getTitulo());
        assertEquals(EstadoLivro.RENOVADO, lido.getEstado());
        assertNotNull(livros.buscarPorTitulo("Padroes de Projeto"));
    }

}
