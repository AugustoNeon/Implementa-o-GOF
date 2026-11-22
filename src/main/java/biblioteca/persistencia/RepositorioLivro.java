package biblioteca.persistencia;

import biblioteca.dominio.Livro;

public interface RepositorioLivro {

    void salvar(Livro livro);

    Livro buscarPorId(String id);

    Livro buscarPorTitulo(String titulo);

    void remover(Livro livro);
}
