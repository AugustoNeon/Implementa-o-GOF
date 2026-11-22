package biblioteca.solicitacao;

import biblioteca.dominio.Comprovante;
import java.time.LocalDateTime;

public abstract class Solicitacao {

    private String id;
    private String autor;
    private LocalDateTime criadaEm;

    public Solicitacao(String id, String autor) {
        this.id = id;
        this.autor = autor;
        this.criadaEm = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public abstract Comprovante executar();
}
