package biblioteca.cobranca.banco;

public class NotificacaoBancoA {

    private String idBoleto;
    private int codigoSituacao;

    public NotificacaoBancoA(String idBoleto, int codigoSituacao) {
        this.idBoleto = idBoleto;
        this.codigoSituacao = codigoSituacao;
    }

    public String getIdBoleto() {
        return idBoleto;
    }

    public int getCodigoSituacao() {
        return codigoSituacao;
    }
}
