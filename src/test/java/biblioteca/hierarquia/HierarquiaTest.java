package biblioteca.hierarquia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.EstadoLivro;
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
import org.junit.jupiter.api.Test;

class HierarquiaTest {

    private GrupoDoAcervo unidade = new GrupoDoAcervo("Unidade Ciencias");
    private GrupoDoAcervo secaoA = new GrupoDoAcervo("Secao A");
    private GrupoDoAcervo secaoB = new GrupoDoAcervo("Secao B");

    private void montarArvore() {
        unidade.adicionar(secaoA);
        unidade.adicionar(secaoB);
        secaoA.adicionar(new ItemDeLivro("L1"));
        secaoA.adicionar(new ItemDeLivro("L2"));
        secaoA.adicionar(new ItemDeLivro("L3"));
        secaoB.adicionar(new ItemDeLivro("L4"));
        secaoB.adicionar(new ItemDeLivro("L5"));
    }

    @Test
    void R10_unidadeComDuasSecoesInformaTotalDeCincoLivros() {
        montarArvore();

        assertEquals(5, unidade.contarLivros());
        assertEquals(3, secaoA.contarLivros());
        assertEquals(2, secaoB.contarLivros());
        assertEquals(5, unidade.listarLivros().size());
    }

    @Test
    void R10_moverLivroEntreSecoesNaoAlteraLivroNemEmprestimo() {
        UnidadeDeTrabalho unidadeDeTrabalho = new UnidadeDeTrabalhoEmMemoria();
        RepositorioLivro acervo = new RepositorioLivroEmMemoria(unidadeDeTrabalho);
        RepositorioMovimentacao movimentacao = new RepositorioMovimentacaoEmMemoria(unidadeDeTrabalho);
        RepositorioRelacionamento relacionamento = new RepositorioRelacionamentoEmMemoria(unidadeDeTrabalho);
        acervo.salvar(new Livro("L1", "Padroes de Projeto"));
        Pessoa pessoa = new Pessoa("Maria", "Silva", "123");
        relacionamento.salvarPessoa(pessoa);
        relacionamento.salvarEstudante(new Estudante("E1", pessoa));
        FachadaDeCirculacao fachada = new FachadaDeCirculacao(acervo, movimentacao,
                relacionamento, unidadeDeTrabalho);
        fachada.solicitarReserva("L1", "E1");
        fachada.emprestarLivro("L1", "E1");
        montarArvore();
        ItemDeLivro item = (ItemDeLivro) secaoA.getFilhos().get(0);

        item.moverPara(secaoB);

        assertEquals(2, secaoA.contarLivros());
        assertEquals(3, secaoB.contarLivros());
        assertEquals(5, unidade.contarLivros());
        assertEquals("L1", acervo.buscarPorId("L1").getId());
        assertEquals(EstadoLivro.EMPRESTADO, acervo.buscarPorId("L1").getEstado());
        assertTrue(movimentacao.consultarEmprestimo("L1", "E1") != null);
    }

}
