package biblioteca.estado;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;

public class EstadoDevolvido extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.DEVOLVIDO;
    }

    @Override
    public boolean inspecionarLivro(Livro livro, boolean bomEstado) {
        if (bomEstado) {
            livro.mudarEstado(new EstadoLivre());
        } else {
            livro.mudarEstado(new EstadoReparando());
        }
        return true;
    }
}
