package biblioteca.devolucao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EventoDeDevolucao {

    private String idLivro;
    private String idEstudante;
    private LocalDate dataVencimento;
    private LocalDate dataDevolucao;

    public EventoDeDevolucao(String idLivro, String idEstudante, LocalDate dataVencimento,
            LocalDate dataDevolucao) {
        this.idLivro = idLivro;
        this.idEstudante = idEstudante;
        this.dataVencimento = dataVencimento;
        this.dataDevolucao = dataDevolucao;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public String getIdEstudante() {
        return idEstudante;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public int getDiasDeAtraso() {
        long dias = ChronoUnit.DAYS.between(dataVencimento, dataDevolucao);
        if (dias < 0) {
            return 0;
        }
        return (int) dias;
    }
}
