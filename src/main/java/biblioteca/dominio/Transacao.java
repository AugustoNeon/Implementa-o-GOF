package biblioteca.dominio;

import java.time.LocalDate;

public abstract class Transacao {

    private LocalDate data;
    private String idLivro;
    private String idEstudante;

    public Transacao(LocalDate data, String idLivro, String idEstudante) {
        this.data = data;
        this.idLivro = idLivro;
        this.idEstudante = idEstudante;
    }

    public LocalDate getData() {
        return data;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public String getIdEstudante() {
        return idEstudante;
    }
}
