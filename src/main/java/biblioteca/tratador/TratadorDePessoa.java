package biblioteca.tratador;

import biblioteca.cadastro.InclusaoDePessoa;
import biblioteca.dominio.Pessoa;
import biblioteca.dominio.iPessoa;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class TratadorDePessoa implements iPessoa {

    private RepositorioRelacionamento relacionamento;
    private UnidadeDeTrabalho unidade;

    public TratadorDePessoa(RepositorioRelacionamento relacionamento, UnidadeDeTrabalho unidade) {
        this.relacionamento = relacionamento;
        this.unidade = unidade;
    }

    @Override
    public Pessoa inserirPessoa(String nome, String sobrenome, String cpf) {
        try {
            Pessoa pessoa = new InclusaoDePessoa(relacionamento, nome, sobrenome, cpf).incluir();
            unidade.confirmar();
            return pessoa;
        } catch (RuntimeException e) {
            unidade.desfazer();
            throw e;
        }
    }

    @Override
    public Pessoa consultarPessoa(String cpf) {
        return relacionamento.buscarPessoa(cpf);
    }

    @Override
    public boolean atualizarPessoa(Pessoa pessoa) {
        if (relacionamento.buscarPessoa(pessoa.getCpf()) == null) {
            return false;
        }
        relacionamento.salvarPessoa(pessoa);
        unidade.confirmar();
        return true;
    }

    @Override
    public boolean excluirPessoa(Pessoa pessoa) {
        if (relacionamento.buscarPessoa(pessoa.getCpf()) == null) {
            return false;
        }
        relacionamento.removerPessoa(pessoa);
        unidade.confirmar();
        return true;
    }
}
