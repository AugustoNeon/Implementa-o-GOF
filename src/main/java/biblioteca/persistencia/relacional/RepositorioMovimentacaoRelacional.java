package biblioteca.persistencia.relacional;

import biblioteca.dominio.Devolucao;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Reserva;
import biblioteca.persistencia.RepositorioMovimentacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMovimentacaoRelacional implements RepositorioMovimentacao {

    private Connection conexao;

    public RepositorioMovimentacaoRelacional(UnidadeDeTrabalhoRelacional unidade) {
        this.conexao = unidade.getConexao();
    }

    @Override
    public void adicionarReserva(Reserva reserva) {
        String sql = "INSERT INTO reserva (id_livro, id_estudante, data_reserva) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, reserva.getIdLivro());
            ps.setString(2, reserva.getIdEstudante());
            ps.setDate(3, Date.valueOf(reserva.getData()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar reserva", e);
        }
    }

    @Override
    public void removerReserva(Reserva reserva) {
        apagar("reserva", reserva.getIdLivro(), reserva.getIdEstudante());
    }

    @Override
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_livro, id_estudante, data_emprestimo, "
                + "data_vencimento, num_renovacoes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, emprestimo.getIdLivro());
            ps.setString(2, emprestimo.getIdEstudante());
            ps.setDate(3, Date.valueOf(emprestimo.getData()));
            ps.setDate(4, Date.valueOf(emprestimo.getDataVencimento()));
            ps.setInt(5, emprestimo.getNumRenovacoes());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar emprestimo", e);
        }
    }

    @Override
    public void removerEmprestimo(Emprestimo emprestimo) {
        apagar("emprestimo", emprestimo.getIdLivro(), emprestimo.getIdEstudante());
    }

    @Override
    public void atualizarEmprestimo(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET data_vencimento = ?, num_renovacoes = ? "
                + "WHERE id_livro = ? AND id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(emprestimo.getDataVencimento()));
            ps.setInt(2, emprestimo.getNumRenovacoes());
            ps.setString(3, emprestimo.getIdLivro());
            ps.setString(4, emprestimo.getIdEstudante());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar emprestimo", e);
        }
    }

    @Override
    public void adicionarDevolucao(Devolucao devolucao) {
        String sql = "INSERT INTO devolucao (id_livro, id_estudante, data_devolucao) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, devolucao.getIdLivro());
            ps.setString(2, devolucao.getIdEstudante());
            ps.setDate(3, Date.valueOf(devolucao.getDataDevolucao()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar devolucao", e);
        }
    }

    @Override
    public Reserva consultarReserva(String idLivro, String idEstudante) {
        String sql = "SELECT data_reserva FROM reserva WHERE id_livro = ? AND id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, idLivro);
            ps.setString(2, idEstudante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reserva(rs.getDate("data_reserva").toLocalDate(), idLivro, idEstudante);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar reserva", e);
        }
    }

    @Override
    public Emprestimo consultarEmprestimo(String idLivro, String idEstudante) {
        String sql = "SELECT data_emprestimo, data_vencimento, num_renovacoes FROM emprestimo "
                + "WHERE id_livro = ? AND id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, idLivro);
            ps.setString(2, idEstudante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Emprestimo emprestimo = new Emprestimo(
                            rs.getDate("data_emprestimo").toLocalDate(), idLivro, idEstudante);
                    emprestimo.setDataVencimento(rs.getDate("data_vencimento").toLocalDate());
                    emprestimo.setNumRenovacoes(rs.getInt("num_renovacoes"));
                    return emprestimo;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar emprestimo", e);
        }
    }

    @Override
    public Devolucao consultarDevolucao(String idLivro, String idEstudante) {
        String sql = "SELECT data_devolucao FROM devolucao WHERE id_livro = ? AND id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, idLivro);
            ps.setString(2, idEstudante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Devolucao(rs.getDate("data_devolucao").toLocalDate(), idLivro, idEstudante);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar devolucao", e);
        }
    }

    @Override
    public List<Reserva> consultarReservasDoEstudante(String idEstudante) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id_livro, data_reserva FROM reserva WHERE id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, idEstudante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservas.add(new Reserva(rs.getDate("data_reserva").toLocalDate(),
                            rs.getString("id_livro"), idEstudante));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar reservas", e);
        }
        return reservas;
    }

    private void apagar(String tabela, String idLivro, String idEstudante) {
        String sql = "DELETE FROM " + tabela + " WHERE id_livro = ? AND id_estudante = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, idLivro);
            ps.setString(2, idEstudante);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover de " + tabela, e);
        }
    }
}
