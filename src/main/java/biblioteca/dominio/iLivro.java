package biblioteca.dominio;

public interface iLivro {

    Livro inserirLivro(String titulo);

    Livro consultarLivro(String titulo);

    boolean atualizarLivro(Livro livro);

    boolean excluirLivro(Livro livro);
}
