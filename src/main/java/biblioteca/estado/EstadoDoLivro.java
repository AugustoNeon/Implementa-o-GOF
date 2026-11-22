package biblioteca.estado;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public abstract class EstadoDoLivro {

    public abstract EstadoLivro getEstado();

    public boolean solicitarReserva(Livro livro) {
        return false;
    }

    public boolean emprestarLivro(Livro livro) {
        return false;
    }

    public boolean renovarEmprestimo(Livro livro, Emprestimo emprestimo) {
        return false;
    }

    public boolean registrarDevolucao(Livro livro) {
        return false;
    }

    public boolean inspecionarLivro(Livro livro, boolean bomEstado) {
        return false;
    }

    public boolean enviarParaReparo(Livro livro) {
        return false;
    }

    public boolean retornarDoReparo(Livro livro) {
        return false;
    }

    public boolean incinerarLivro(Livro livro) {
        return false;
    }

    public boolean excluirLivro(Livro livro) {
        return false;
    }

    public static EstadoDoLivro aPartirDe(EstadoLivro estado) {
        switch (estado) {
            case LIVRE:
                return new EstadoLivre();
            case RESERVADO:
                return new EstadoReservado();
            case EMPRESTADO:
                return new EstadoEmprestado();
            case RENOVADO:
                return new EstadoRenovado();
            case DEVOLVIDO:
                return new EstadoDevolvido();
            case REPARANDO:
                return new EstadoReparando();
            default:
                return new EstadoIncinerado();
        }
    }
}
