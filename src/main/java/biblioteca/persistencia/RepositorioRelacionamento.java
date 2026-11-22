package biblioteca.persistencia;

import biblioteca.dominio.Atendente;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Pessoa;

public interface RepositorioRelacionamento {

    void salvarPessoa(Pessoa pessoa);

    Pessoa buscarPessoa(String cpf);

    void removerPessoa(Pessoa pessoa);

    void salvarEstudante(Estudante estudante);

    Estudante buscarEstudante(String matricula);

    void removerEstudante(Estudante estudante);

    void salvarAtendente(Atendente atendente);

    Atendente buscarAtendente(String matriculaFuncional);

    void removerAtendente(Atendente atendente);
}
