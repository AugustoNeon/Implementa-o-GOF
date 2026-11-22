package biblioteca.estado;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public class EstadoReparando extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.REPARANDO;
    }

    @Override
    public boolean retornarDoReparo(Livro livro) {
        livro.mudarEstado(new EstadoLivre());
        return true;
    }

    @Override
    public boolean incinerarLivro(Livro livro) {
        livro.mudarEstado(new EstadoIncinerado());
        return true;
    }
}
