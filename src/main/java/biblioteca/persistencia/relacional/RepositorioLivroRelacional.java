package biblioteca.persistencia.relacional;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;
import biblioteca.persistencia.RepositorioLivro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositorioLivroRelacional implements RepositorioLivro {

    private Connection conexao;

    public RepositorioLivroRelacional(UnidadeDeTrabalhoRelacional unidade) {
        this.conexao = unidade.getConexao();
    }

    @Override
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livro (id, titulo, estado) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE titulo = ?, estado = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, livro.getId());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getEstado().name());
            ps.setString(4, livro.getTitulo());
            ps.setString(5, livro.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar livro", e);
        }
    }

    @Override
    public Livro buscarPorId(String id) {
        return buscarUm("SELECT id, titulo, estado FROM livro WHERE id = ?", id);
    }

    @Override
    public Livro buscarPorTitulo(String titulo) {
        return buscarUm("SELECT id, titulo, estado FROM livro WHERE titulo = ?", titulo);
    }

    @Override
    public void remover(Livro livro) {
        try (PreparedStatement ps = conexao.prepareStatement("DELETE FROM livro WHERE id = ?")) {
            ps.setString(1, livro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover livro", e);
        }
    }

    private Livro buscarUm(String sql, String valor) {
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarLivro(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro", e);
        }
    }

    private Livro montarLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro(rs.getString("id"), rs.getString("titulo"));
        livro.atualizarEstado(EstadoLivro.valueOf(rs.getString("estado")));
        return livro;
    }
}
