package biblioteca.cobranca.banco;

public class BancoB {

    private int contador = 100;
    private String ultimoValorEmReais;
    private String ultimoCpf;

    public int criarCobranca(String cpfFormatado, String valorEmReais) {
        contador++;
        ultimoCpf = cpfFormatado;
        ultimoValorEmReais = valorEmReais;
        return contador;
    }

    public String getUltimoValorEmReais() {
        return ultimoValorEmReais;
    }

    public String getUltimoCpf() {
        return ultimoCpf;
    }
}
