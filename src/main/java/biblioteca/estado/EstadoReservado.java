package biblioteca.estado;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public class EstadoReservado extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.RESERVADO;
    }

    @Override
    public boolean emprestarLivro(Livro livro) {
        livro.mudarEstado(new EstadoEmprestado());
        return true;
    }
}
