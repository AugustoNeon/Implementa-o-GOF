package biblioteca.persistencia.relacional;

import biblioteca.dominio.Atendente;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.Pessoa;
import biblioteca.persistencia.RepositorioRelacionamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositorioRelacionamentoRelacional implements RepositorioRelacionamento {

    private Connection conexao;

    public RepositorioRelacionamentoRelacional(UnidadeDeTrabalhoRelacional unidade) {
        this.conexao = unidade.getConexao();
    }

    @Override
    public void salvarPessoa(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (cpf, nome, sobrenome) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nome = ?, sobrenome = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pessoa.getCpf());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getSobrenome());
            ps.setString(4, pessoa.getNome());
            ps.setString(5, pessoa.getSobrenome());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pessoa", e);
        }
    }

    @Override
    public Pessoa buscarPessoa(String cpf) {
        String sql = "SELECT nome, sobrenome FROM pessoa WHERE cpf = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(rs.getString("nome"), rs.getString("sobrenome"), cpf);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa", e);
        }
    }

    @Override
    public void removerPessoa(Pessoa pessoa) {
        apagar("DELETE FROM pessoa WHERE cpf = ?", pessoa.getCpf());
    }

    @Override
    public void salvarEstudante(Estudante estudante) {
        String sql = "INSERT INTO estudante (matricula, cpf) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE cpf = ?";
        gravarPapel(sql, estudante.getMatricula(), estudante.getPessoa().getCpf());
    }

    @Override
    public Estudante buscarEstudante(String matricula) {
        String cpf = buscarCpf("SELECT cpf FROM estudante WHERE matricula = ?", matricula);
        if (cpf == null) {
            return null;
        }
        return new Estudante(matricula, buscarPessoa(cpf));
    }

    @Override
    public void removerEstudante(Estudante estudante) {
        apagar("DELETE FROM estudante WHERE matricula = ?", estudante.getMatricula());
    }

    @Override
    public void salvarAtendente(Atendente atendente) {
        String sql = "INSERT INTO atendente (matricula_funcional, cpf) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE cpf = ?";
        gravarPapel(sql, atendente.getMatriculaFuncional(), atendente.getPessoa().getCpf());
    }

    @Override
    public Atendente buscarAtendente(String matriculaFuncional) {
        String cpf = buscarCpf("SELECT cpf FROM atendente WHERE matricula_funcional = ?",
                matriculaFuncional);
        if (cpf == null) {
            return null;
        }
        return new Atendente(matriculaFuncional, buscarPessoa(cpf));
    }

    @Override
    public void removerAtendente(Atendente atendente) {
        apagar("DELETE FROM atendente WHERE matricula_funcional = ?", atendente.getMatriculaFuncional());
    }

    private void gravarPapel(String sql, String matricula, String cpf) {
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, matricula);
            ps.setString(2, cpf);
            ps.setString(3, cpf);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gravar cadastro", e);
        }
    }

    private String buscarCpf(String sql, String matricula) {
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("cpf");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cadastro", e);
        }
    }

    private void apagar(String sql, String chave) {
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, chave);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover cadastro", e);
        }
    }
}
