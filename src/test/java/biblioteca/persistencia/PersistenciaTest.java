package biblioteca.persistencia;

import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.persistencia.memoria.RepositorioLivroEmMemoria;
import biblioteca.persistencia.memoria.RepositorioMovimentacaoEmMemoria;
import biblioteca.persistencia.memoria.RepositorioRelacionamentoEmMemoria;
import biblioteca.persistencia.memoria.UnidadeDeTrabalhoEmMemoria;
import org.junit.jupiter.api.Test;

class PersistenciaTest {

    @Test
    void R08_configuracaoDeTesteFornecaApenasObjetosEmMemoria() {
        FabricaDeRepositorios fabrica = new Configuracao("teste").getFabrica();

        UnidadeDeTrabalho unidade = fabrica.criarUnidadeDeTrabalho();
        RepositorioLivro livros = fabrica.criarRepositorioLivro(unidade);
        RepositorioMovimentacao movimentacao = fabrica.criarRepositorioMovimentacao(unidade);
        RepositorioRelacionamento relacionamento = fabrica.criarRepositorioRelacionamento(unidade);

        assertTrue(unidade instanceof UnidadeDeTrabalhoEmMemoria);
        assertTrue(livros instanceof RepositorioLivroEmMemoria);
        assertTrue(movimentacao instanceof RepositorioMovimentacaoEmMemoria);
        assertTrue(relacionamento instanceof RepositorioRelacionamentoEmMemoria);
    }

}
