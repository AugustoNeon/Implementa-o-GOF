package biblioteca.dominio;

public class Atendente {

    private String matriculaFuncional;
    private Pessoa pessoa;

    public Atendente(String matriculaFuncional, Pessoa pessoa) {
        this.matriculaFuncional = matriculaFuncional;
        this.pessoa = pessoa;
    }

    public String getMatriculaFuncional() {
        return matriculaFuncional;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
