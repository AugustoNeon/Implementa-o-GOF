package biblioteca.dominio;

public interface iEstudante {

    Estudante inserirEstudante(String cpf, String matricula);

    Estudante consultarEstudante(String matricula);

    boolean atualizarEstudante(Estudante estudante);

    boolean excluirEstudante(Estudante estudante);
}
