package biblioteca.estado;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public class EstadoLivre extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.LIVRE;
    }

    @Override
    public boolean solicitarReserva(Livro livro) {
        livro.mudarEstado(new EstadoReservado());
        return true;
    }

    @Override
    public boolean enviarParaReparo(Livro livro) {
        livro.mudarEstado(new EstadoReparando());
        return true;
    }

    @Override
    public boolean excluirLivro(Livro livro) {
        return true;
    }
}
