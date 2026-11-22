package biblioteca.estado;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public class EstadoEmprestado extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.EMPRESTADO;
    }

    @Override
    public boolean renovarEmprestimo(Livro livro, Emprestimo emprestimo) {
        if (emprestimo.getNumRenovacoes() >= 2) {
            return false;
        }
        emprestimo.setDataVencimento(emprestimo.getDataVencimento().plusDays(30));
        emprestimo.setNumRenovacoes(emprestimo.getNumRenovacoes() + 1);
        livro.mudarEstado(new EstadoRenovado());
        return true;
    }

    @Override
    public boolean registrarDevolucao(Livro livro) {
        livro.mudarEstado(new EstadoDevolvido());
        return true;
    }
}
