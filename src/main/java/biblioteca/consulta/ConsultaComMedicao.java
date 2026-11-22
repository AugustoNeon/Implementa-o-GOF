package biblioteca.consulta;

public class ConsultaComMedicao implements ConsultaDeAcervo {

    private ConsultaDeAcervo proxima;
    private int totalDeConsultas = 0;
    private long tempoTotalEmNanos = 0;

    public ConsultaComMedicao(ConsultaDeAcervo proxima) {
        this.proxima = proxima;
    }

    @Override
    public MetadadosDoLivro consultarMetadados(String idLivro) {
        long inicio = System.nanoTime();
        MetadadosDoLivro resultado = proxima.consultarMetadados(idLivro);
        registrar(inicio);
        return resultado;
    }

    @Override
    public boolean podeReservar(String idLivro) {
        long inicio = System.nanoTime();
        boolean resultado = proxima.podeReservar(idLivro);
        registrar(inicio);
        return resultado;
    }

    private void registrar(long inicio) {
        totalDeConsultas++;
        tempoTotalEmNanos += System.nanoTime() - inicio;
    }

    public int getTotalDeConsultas() {
        return totalDeConsultas;
    }

    public long getTempoTotalEmNanos() {
        return tempoTotalEmNanos;
    }
}
