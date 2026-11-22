package biblioteca.estado;

import biblioteca.dominio.EstadoLivro;

public class EstadoIncinerado extends EstadoDoLivro {

    @Override
    public EstadoLivro getEstado() {
        return EstadoLivro.INCINERADO;
    }
}
