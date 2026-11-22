package biblioteca.persistencia.relacional;

import biblioteca.persistencia.UnidadeDeTrabalho;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UnidadeDeTrabalhoRelacional implements UnidadeDeTrabalho {

    private Connection conexao;

    public UnidadeDeTrabalhoRelacional(String url, String usuario, String senha) {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            conexao.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no MySQL", e);
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    @Override
    public void confirmar() {
        try {
            conexao.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erro no commit", e);
        }
    }

    @Override
    public void desfazer() {
        try {
            conexao.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Erro no rollback", e);
        }
    }

    @Override
    public void fechar() {
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexao", e);
        }
    }
}
