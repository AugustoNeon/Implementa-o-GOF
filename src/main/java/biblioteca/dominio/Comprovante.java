package biblioteca.dominio;

import java.time.LocalDate;

public class Comprovante {

    private String tipo;
    private String idLivro;
    private String idEstudante;
    private LocalDate data;
    private LocalDate dataVencimento;

    public Comprovante(String tipo, String idLivro, String idEstudante, LocalDate data,
            LocalDate dataVencimento) {
        this.tipo = tipo;
        this.idLivro = idLivro;
        this.idEstudante = idEstudante;
        this.data = data;
        this.dataVencimento = dataVencimento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public String getIdEstudante() {
        return idEstudante;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    @Override
    public String toString() {
        String texto = tipo + " | livro " + idLivro;
        if (idEstudante != null) {
            texto = texto + " | estudante " + idEstudante;
        }
        texto = texto + " | data " + data;
        if (dataVencimento != null) {
            texto = texto + " | vencimento " + dataVencimento;
        }
        return texto;
    }
}
