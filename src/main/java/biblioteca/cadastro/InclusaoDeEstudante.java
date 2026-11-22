package biblioteca.cadastro;

import biblioteca.dominio.Estudante;
import biblioteca.persistencia.RepositorioRelacionamento;

public class InclusaoDeEstudante extends InclusaoDeCadastro<Estudante> {

    private RepositorioRelacionamento relacionamento;
    private String cpf;
    private String matricula;

    public InclusaoDeEstudante(RepositorioRelacionamento relacionamento, String cpf, String matricula) {
        this.relacionamento = relacionamento;
        this.cpf = cpf;
        this.matricula = matricula;
    }

    @Override
    protected Estudante buscarExistente() {
        return relacionamento.buscarEstudante(matricula);
    }

    @Override
    protected void validar() {
        if (estaVazio(cpf) || estaVazio(matricula)) {
            throw new CadastroException("CPF e matricula sao obrigatorios");
        }
    }

    @Override
    protected void verificarPreRequisitos() {
        if (relacionamento.buscarPessoa(cpf) == null) {
            throw new CadastroException("Pessoa com CPF " + cpf + " nao esta cadastrada");
        }
    }

    @Override
    protected Estudante criar() {
        return new Estudante(matricula, relacionamento.buscarPessoa(cpf));
    }

    @Override
    protected void armazenar(Estudante estudante) {
        relacionamento.salvarEstudante(estudante);
    }

    @Override
    protected void apresentar(Estudante estudante) {
        System.out.println("Estudante: " + estudante.getMatricula() + " - "
                + estudante.getPessoa().getNomeCompleto());
    }
}
