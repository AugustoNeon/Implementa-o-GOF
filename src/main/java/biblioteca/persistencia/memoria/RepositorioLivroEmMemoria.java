package biblioteca.persistencia.memoria;

import biblioteca.dominio.Livro;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.UnidadeDeTrabalho;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLivroEmMemoria implements RepositorioLivro {

    private UnidadeDeTrabalho unidade;
    private List<Livro> livros = new ArrayList<>();

    public RepositorioLivroEmMemoria(UnidadeDeTrabalho unidade) {
        this.unidade = unidade;
    }

    @Override
    public void salvar(Livro livro) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId().equals(livro.getId())) {
                livros.set(i, livro);
                return;
            }
        }
        livros.add(livro);
    }

    @Override
    public Livro buscarPorId(String id) {
        for (Livro livro : livros) {
            if (livro.getId().equals(id)) {
                return livro;
            }
        }
        return null;
    }

    @Override
    public Livro buscarPorTitulo(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(titulo)) {
                return livro;
            }
        }
        return null;
    }

    @Override
    public void remover(Livro livro) {
        livros.remove(livro);
    }
}
