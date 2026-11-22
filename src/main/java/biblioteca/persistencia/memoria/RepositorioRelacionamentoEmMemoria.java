package biblioteca.persistencia.memoria;

import biblioteca.dominio.Atendente;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Pessoa;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import java.util.HashMap;
import java.util.Map;

public class RepositorioRelacionamentoEmMemoria implements RepositorioRelacionamento {

    private UnidadeDeTrabalho unidade;
    private Map<String, Pessoa> pessoas = new HashMap<>();
    private Map<String, Estudante> estudantes = new HashMap<>();
    private Map<String, Atendente> atendentes = new HashMap<>();

    public RepositorioRelacionamentoEmMemoria(UnidadeDeTrabalho unidade) {
        this.unidade = unidade;
    }

    @Override
    public void salvarPessoa(Pessoa pessoa) {
        pessoas.put(pessoa.getCpf(), pessoa);
    }

    @Override
    public Pessoa buscarPessoa(String cpf) {
        return pessoas.get(cpf);
    }

    @Override
    public void removerPessoa(Pessoa pessoa) {
        pessoas.remove(pessoa.getCpf());
    }

    @Override
    public void salvarEstudante(Estudante estudante) {
        estudantes.put(estudante.getMatricula(), estudante);
    }

    @Override
    public Estudante buscarEstudante(String matricula) {
        return estudantes.get(matricula);
    }

    @Override
    public void removerEstudante(Estudante estudante) {
        estudantes.remove(estudante.getMatricula());
    }

    @Override
    public void salvarAtendente(Atendente atendente) {
        atendentes.put(atendente.getMatriculaFuncional(), atendente);
    }

    @Override
    public Atendente buscarAtendente(String matriculaFuncional) {
        return atendentes.get(matriculaFuncional);
    }

    @Override
    public void removerAtendente(Atendente atendente) {
        atendentes.remove(atendente.getMatriculaFuncional());
    }
}
