package biblioteca.devolucao;

public class OuvinteDeIndicadores implements OuvinteDeDevolucao {

    private int totalDeDevolucoes = 0;
    private int totalDeAtrasos = 0;

    @Override
    public void aoDevolver(EventoDeDevolucao evento) {
        totalDeDevolucoes++;
        if (evento.getDiasDeAtraso() > 0) {
            totalDeAtrasos++;
        }
    }

    public int getTotalDeDevolucoes() {
        return totalDeDevolucoes;
    }

    public int getTotalDeAtrasos() {
        return totalDeAtrasos;
    }
}
