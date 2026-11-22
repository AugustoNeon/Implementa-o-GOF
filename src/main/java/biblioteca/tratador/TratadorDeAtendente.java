package biblioteca.tratador;

import biblioteca.cadastro.InclusaoDeAtendente;
import biblioteca.dominio.Atendente;
import biblioteca.dominio.iAtendente;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class TratadorDeAtendente implements iAtendente {

    private RepositorioRelacionamento relacionamento;
    private UnidadeDeTrabalho unidade;

    public TratadorDeAtendente(RepositorioRelacionamento relacionamento, UnidadeDeTrabalho unidade) {
        this.relacionamento = relacionamento;
        this.unidade = unidade;
    }

    @Override
    public Atendente inserirAtendente(String cpf, String matriculaFuncional) {
        try {
            Atendente atendente = new InclusaoDeAtendente(relacionamento, cpf, matriculaFuncional).incluir();
            unidade.confirmar();
            return atendente;
        } catch (RuntimeException e) {
            unidade.desfazer();
            throw e;
        }
    }

    @Override
    public Atendente consultarAtendente(String matriculaFuncional) {
        return relacionamento.buscarAtendente(matriculaFuncional);
    }

    @Override
    public boolean atualizarAtendente(Atendente atendente) {
        if (relacionamento.buscarAtendente(atendente.getMatriculaFuncional()) == null
                || relacionamento.buscarPessoa(atendente.getPessoa().getCpf()) == null) {
            return false;
        }
        relacionamento.salvarAtendente(atendente);
        unidade.confirmar();
        return true;
    }

    @Override
    public boolean excluirAtendente(Atendente atendente) {
        if (relacionamento.buscarAtendente(atendente.getMatriculaFuncional()) == null) {
            return false;
        }
        relacionamento.removerAtendente(atendente);
        unidade.confirmar();
        return true;
    }
}
