package biblioteca.devolucao;

public class OuvinteDeEmail implements OuvinteDeDevolucao {

    private EnviadorDeEmail enviador;

    public OuvinteDeEmail(EnviadorDeEmail enviador) {
        this.enviador = enviador;
    }

    @Override
    public void aoDevolver(EventoDeDevolucao evento) {
        String mensagem = "Devolucao do livro " + evento.getIdLivro() + " registrada.";
        if (evento.getDiasDeAtraso() > 0) {
            mensagem = mensagem + " Atraso de " + evento.getDiasDeAtraso() + " dia(s): havera multa.";
        }
        enviador.enviar(evento.getIdEstudante(), mensagem);
    }
}
