package biblioteca.tratador;

import biblioteca.cadastro.InclusaoDeLivro;
import biblioteca.dominio.Livro;
import biblioteca.dominio.iLivro;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class TratadorDeAcervo implements iLivro {

    private RepositorioLivro acervo;
    private UnidadeDeTrabalho unidade;

    public TratadorDeAcervo(RepositorioLivro acervo, UnidadeDeTrabalho unidade) {
        this.acervo = acervo;
        this.unidade = unidade;
    }

    @Override
    public Livro inserirLivro(String titulo) {
        try {
            Livro livro = new InclusaoDeLivro(acervo, titulo).incluir();
            unidade.confirmar();
            return livro;
        } catch (RuntimeException e) {
            unidade.desfazer();
            throw e;
        }
    }

    @Override
    public Livro consultarLivro(String titulo) {
        return acervo.buscarPorTitulo(titulo);
    }

    @Override
    public boolean atualizarLivro(Livro livro) {
        if (acervo.buscarPorId(livro.getId()) == null) {
            return false;
        }
        acervo.salvar(livro);
        unidade.confirmar();
        return true;
    }

    @Override
    public boolean excluirLivro(Livro livro) {
        if (acervo.buscarPorId(livro.getId()) == null) {
            return false;
        }
        if (!livro.excluirLivro()) {
            return false;
        }
        acervo.remover(livro);
        unidade.confirmar();
        return true;
    }
}
