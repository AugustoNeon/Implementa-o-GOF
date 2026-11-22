package biblioteca.cobranca;

public class PedidoDeCobranca {

    private String idCobranca;
    private String cpf;
    private double valor;

    public PedidoDeCobranca(String idCobranca, String cpf, double valor) {
        this.idCobranca = idCobranca;
        this.cpf = cpf;
        this.valor = valor;
    }

    public String getIdCobranca() {
        return idCobranca;
    }

    public String getCpf() {
        return cpf;
    }

    public double getValor() {
        return valor;
    }
}
