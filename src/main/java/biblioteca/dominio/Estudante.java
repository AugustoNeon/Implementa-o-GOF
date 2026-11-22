package biblioteca.dominio;

public class Estudante {

    private String matricula;
    private Pessoa pessoa;

    public Estudante(String matricula, Pessoa pessoa) {
        this.matricula = matricula;
        this.pessoa = pessoa;
    }

    public String getMatricula() {
        return matricula;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
