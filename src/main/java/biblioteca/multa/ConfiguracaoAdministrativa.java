package biblioteca.multa;

public class ConfiguracaoAdministrativa {

    private PoliticaDeMulta politicaVigente = new PoliticaOriginal();

    public PoliticaDeMulta getPoliticaVigente() {
        return politicaVigente;
    }

    public void definirPolitica(PoliticaDeMulta politica) {
        this.politicaVigente = politica;
    }
}
