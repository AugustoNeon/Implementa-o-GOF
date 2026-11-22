package biblioteca.consulta;

import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;
import biblioteca.persistencia.RepositorioLivro;

public class ConsultaDeAcervoPadrao implements ConsultaDeAcervo {

    private RepositorioLivro acervo;

    public ConsultaDeAcervoPadrao(RepositorioLivro acervo) {
        this.acervo = acervo;
    }

    @Override
    public MetadadosDoLivro consultarMetadados(String idLivro) {
        Livro livro = acervo.buscarPorId(idLivro);
        if (livro == null) {
            return null;
        }
        return new MetadadosDoLivro(livro.getId(), livro.getTitulo());
    }

    @Override
    public boolean podeReservar(String idLivro) {
        Livro livro = acervo.buscarPorId(idLivro);
        return livro != null && livro.getEstado() == EstadoLivro.LIVRE;
    }
}
