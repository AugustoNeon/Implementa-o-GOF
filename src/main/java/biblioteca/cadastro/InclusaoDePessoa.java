package biblioteca.cadastro;

import biblioteca.dominio.Pessoa;
import biblioteca.persistencia.RepositorioRelacionamento;

public class InclusaoDePessoa extends InclusaoDeCadastro<Pessoa> {

    private RepositorioRelacionamento relacionamento;
    private String nome;
    private String sobrenome;
    private String cpf;

    public InclusaoDePessoa(RepositorioRelacionamento relacionamento, String nome,
            String sobrenome, String cpf) {
        this.relacionamento = relacionamento;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
    }

    @Override
    protected Pessoa buscarExistente() {
        return relacionamento.buscarPessoa(cpf);
    }

    @Override
    protected void validar() {
        if (estaVazio(nome) || estaVazio(sobrenome) || estaVazio(cpf)) {
            throw new CadastroException("Nome, sobrenome e CPF sao obrigatorios");
        }
    }

    @Override
    protected void verificarPreRequisitos() {
    }

    @Override
    protected Pessoa criar() {
        return new Pessoa(nome, sobrenome, cpf);
    }

    @Override
    protected void armazenar(Pessoa pessoa) {
        relacionamento.salvarPessoa(pessoa);
    }

    @Override
    protected void apresentar(Pessoa pessoa) {
        System.out.println("Pessoa: " + pessoa.getNomeCompleto() + " (CPF " + pessoa.getCpf() + ")");
    }
}
