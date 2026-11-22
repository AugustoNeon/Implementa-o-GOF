package biblioteca.cobranca.banco;

public class NotificacaoBancoB {

    private int numero;
    private String status;

    public NotificacaoBancoB(int numero, String status) {
        this.numero = numero;
        this.status = status;
    }

    public int getNumero() {
        return numero;
    }

    public String getStatus() {
        return status;
    }
}
