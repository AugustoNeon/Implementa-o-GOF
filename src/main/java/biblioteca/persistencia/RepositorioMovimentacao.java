package biblioteca.persistencia;

import biblioteca.dominio.Devolucao;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Reserva;
import java.util.List;

public interface RepositorioMovimentacao {

    void adicionarReserva(Reserva reserva);

    void removerReserva(Reserva reserva);

    void adicionarEmprestimo(Emprestimo emprestimo);

    void removerEmprestimo(Emprestimo emprestimo);

    void atualizarEmprestimo(Emprestimo emprestimo);

    void adicionarDevolucao(Devolucao devolucao);

    Reserva consultarReserva(String idLivro, String idEstudante);

    Emprestimo consultarEmprestimo(String idLivro, String idEstudante);

    Devolucao consultarDevolucao(String idLivro, String idEstudante);

    List<Reserva> consultarReservasDoEstudante(String idEstudante);
}
