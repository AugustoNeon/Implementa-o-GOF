package biblioteca.multa;

public class PoliticaComTolerancia implements PoliticaDeMulta {

    private static final int DIAS_DE_TOLERANCIA = 2;

    @Override
    public double calcular(int diasDeAtraso) {
        int diasCobrados = diasDeAtraso - DIAS_DE_TOLERANCIA;
        if (diasCobrados <= 0) {
            return 0;
        }
        return diasCobrados * 1.0;
    }

    @Override
    public String getNome() {
        return "COM_TOLERANCIA";
    }
}
