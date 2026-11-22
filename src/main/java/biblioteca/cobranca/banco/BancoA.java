package biblioteca.cobranca.banco;

public class BancoA {

    private int contador = 0;
    private long ultimoValorEmCentavos;
    private String ultimoDocumento;

    public String gerarBoleto(String documentoSoDigitos, long valorEmCentavos) {
        contador++;
        ultimoDocumento = documentoSoDigitos;
        ultimoValorEmCentavos = valorEmCentavos;
        return "A-" + contador;
    }

    public long getUltimoValorEmCentavos() {
        return ultimoValorEmCentavos;
    }

    public String getUltimoDocumento() {
        return ultimoDocumento;
    }
}
