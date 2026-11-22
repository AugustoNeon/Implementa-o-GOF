package biblioteca.multa;

public class PoliticaOriginal implements PoliticaDeMulta {

    @Override
    public double calcular(int diasDeAtraso) {
        if (diasDeAtraso <= 0) {
            return 0;
        }
        return diasDeAtraso * 1.0;
    }

    @Override
    public String getNome() {
        return "ORIGINAL";
    }
}
