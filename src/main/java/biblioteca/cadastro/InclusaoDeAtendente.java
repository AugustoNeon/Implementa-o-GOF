package biblioteca.cadastro;

import biblioteca.dominio.Atendente;
import biblioteca.persistencia.RepositorioRelacionamento;

public class InclusaoDeAtendente extends InclusaoDeCadastro<Atendente> {

    private RepositorioRelacionamento relacionamento;
    private String cpf;
    private String matriculaFuncional;

    public InclusaoDeAtendente(RepositorioRelacionamento relacionamento, String cpf,
            String matriculaFuncional) {
        this.relacionamento = relacionamento;
        this.cpf = cpf;
        this.matriculaFuncional = matriculaFuncional;
    }

    @Override
    protected Atendente buscarExistente() {
        return relacionamento.buscarAtendente(matriculaFuncional);
    }

    @Override
    protected void validar() {
        if (estaVazio(cpf) || estaVazio(matriculaFuncional)) {
            throw new CadastroException("CPF e matricula funcional sao obrigatorios");
        }
    }

    @Override
    protected void verificarPreRequisitos() {
        if (relacionamento.buscarPessoa(cpf) == null) {
            throw new CadastroException("Pessoa com CPF " + cpf + " nao esta cadastrada");
        }
    }

    @Override
    protected Atendente criar() {
        return new Atendente(matriculaFuncional, relacionamento.buscarPessoa(cpf));
    }

    @Override
    protected void armazenar(Atendente atendente) {
        relacionamento.salvarAtendente(atendente);
    }

    @Override
    protected void apresentar(Atendente atendente) {
        System.out.println("Atendente: " + atendente.getMatriculaFuncional() + " - "
                + atendente.getPessoa().getNomeCompleto());
    }
}
