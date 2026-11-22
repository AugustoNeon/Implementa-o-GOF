package biblioteca.dominio;

import java.time.LocalDate;

public class Emprestimo extends Transacao {

    private LocalDate dataVencimento;
    private int numRenovacoes;

    public Emprestimo(LocalDate data, String idLivro, String idEstudante) {
        super(data, idLivro, idEstudante);
        this.dataVencimento = data.plusDays(30);
        this.numRenovacoes = 0;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getNumRenovacoes() {
        return numRenovacoes;
    }

    public void setNumRenovacoes(int numRenovacoes) {
        this.numRenovacoes = numRenovacoes;
    }
}
