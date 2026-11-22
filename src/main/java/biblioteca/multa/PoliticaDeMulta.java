package biblioteca.multa;

public interface PoliticaDeMulta {

    double calcular(int diasDeAtraso);

    String getNome();
}
