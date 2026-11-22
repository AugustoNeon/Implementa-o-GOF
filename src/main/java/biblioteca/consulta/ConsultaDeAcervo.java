package biblioteca.consulta;

public interface ConsultaDeAcervo {

    MetadadosDoLivro consultarMetadados(String idLivro);

    boolean podeReservar(String idLivro);
}
