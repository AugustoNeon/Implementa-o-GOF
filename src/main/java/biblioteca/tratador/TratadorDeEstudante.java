package biblioteca.tratador;

import biblioteca.cadastro.InclusaoDeEstudante;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.iEstudante;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class TratadorDeEstudante implements iEstudante {

    private RepositorioRelacionamento relacionamento;
    private UnidadeDeTrabalho unidade;

    public TratadorDeEstudante(RepositorioRelacionamento relacionamento, UnidadeDeTrabalho unidade) {
        this.relacionamento = relacionamento;
        this.unidade = unidade;
    }

    @Override
    public Estudante inserirEstudante(String cpf, String matricula) {
        try {
            Estudante estudante = new InclusaoDeEstudante(relacionamento, cpf, matricula).incluir();
            unidade.confirmar();
            return estudante;
        } catch (RuntimeException e) {
            unidade.desfazer();
            throw e;
        }
    }

    @Override
    public Estudante consultarEstudante(String matricula) {
        return relacionamento.buscarEstudante(matricula);
    }

    @Override
    public boolean atualizarEstudante(Estudante estudante) {
        if (relacionamento.buscarEstudante(estudante.getMatricula()) == null
                || relacionamento.buscarPessoa(estudante.getPessoa().getCpf()) == null) {
            return false;
        }
        relacionamento.salvarEstudante(estudante);
        unidade.confirmar();
        return true;
    }

    @Override
    public boolean excluirEstudante(Estudante estudante) {
        if (relacionamento.buscarEstudante(estudante.getMatricula()) == null) {
            return false;
        }
        relacionamento.removerEstudante(estudante);
        unidade.confirmar();
        return true;
    }
}
