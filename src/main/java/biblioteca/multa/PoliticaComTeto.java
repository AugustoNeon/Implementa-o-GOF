package biblioteca.multa;

public class PoliticaComTeto implements PoliticaDeMulta {

    private static final double TETO = 20.0;

    @Override
    public double calcular(int diasDeAtraso) {
        if (diasDeAtraso <= 0) {
            return 0;
        }
        double valor = diasDeAtraso * 1.0;
        if (valor > TETO) {
            return TETO;
        }
        return valor;
    }

    @Override
    public String getNome() {
        return "COM_TETO";
    }
}
