package biblioteca.dominio;

import java.time.LocalDate;

public class Devolucao extends Transacao {

    private LocalDate dataDevolucao;

    public Devolucao(LocalDate data, String idLivro, String idEstudante) {
        super(data, idLivro, idEstudante);
        this.dataDevolucao = data;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
}
