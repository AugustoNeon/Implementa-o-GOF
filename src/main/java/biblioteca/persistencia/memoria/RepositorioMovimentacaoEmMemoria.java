package biblioteca.persistencia.memoria;

import biblioteca.dominio.Devolucao;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Reserva;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.UnidadeDeTrabalho;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMovimentacaoEmMemoria implements RepositorioMovimentacao {

    private UnidadeDeTrabalho unidade;
    private List<Reserva> reservas = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Devolucao> devolucoes = new ArrayList<>();

    public RepositorioMovimentacaoEmMemoria(UnidadeDeTrabalho unidade) {
        this.unidade = unidade;
    }

    @Override
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public void removerReserva(Reserva reserva) {
        reservas.remove(reserva);
    }

    @Override
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    @Override
    public void removerEmprestimo(Emprestimo emprestimo) {
        emprestimos.remove(emprestimo);
    }

    @Override
    public void atualizarEmprestimo(Emprestimo emprestimo) {
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            if (e.getIdLivro().equals(emprestimo.getIdLivro())
                    && e.getIdEstudante().equals(emprestimo.getIdEstudante())) {
                emprestimos.set(i, emprestimo);
                return;
            }
        }
    }

    @Override
    public void adicionarDevolucao(Devolucao devolucao) {
        devolucoes.add(devolucao);
    }

    @Override
    public Reserva consultarReserva(String idLivro, String idEstudante) {
        for (Reserva r : reservas) {
            if (r.getIdLivro().equals(idLivro) && r.getIdEstudante().equals(idEstudante)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public Emprestimo consultarEmprestimo(String idLivro, String idEstudante) {
        for (Emprestimo e : emprestimos) {
            if (e.getIdLivro().equals(idLivro) && e.getIdEstudante().equals(idEstudante)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Devolucao consultarDevolucao(String idLivro, String idEstudante) {
        for (Devolucao d : devolucoes) {
            if (d.getIdLivro().equals(idLivro) && d.getIdEstudante().equals(idEstudante)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Reserva> consultarReservasDoEstudante(String idEstudante) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getIdEstudante().equals(idEstudante)) {
                resultado.add(r);
            }
        }
        return resultado;
    }
}
