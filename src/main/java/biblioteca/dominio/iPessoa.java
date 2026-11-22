package biblioteca.dominio;

public interface iPessoa {

    Pessoa inserirPessoa(String nome, String sobrenome, String cpf);

    Pessoa consultarPessoa(String cpf);

    boolean atualizarPessoa(Pessoa pessoa);

    boolean excluirPessoa(Pessoa pessoa);
}
