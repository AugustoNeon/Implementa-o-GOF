package biblioteca.consulta;

public class MetadadosDoLivro {

    private String id;
    private String titulo;

    public MetadadosDoLivro(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
}
