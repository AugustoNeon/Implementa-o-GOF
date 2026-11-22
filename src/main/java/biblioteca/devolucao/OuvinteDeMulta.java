package biblioteca.devolucao;

import biblioteca.multa.ServicoDeMultas;

public class OuvinteDeMulta implements OuvinteDeDevolucao {

    private ServicoDeMultas servicoDeMultas;

    public OuvinteDeMulta(ServicoDeMultas servicoDeMultas) {
        this.servicoDeMultas = servicoDeMultas;
    }

    @Override
    public void aoDevolver(EventoDeDevolucao evento) {
        servicoDeMultas.emitirMulta(evento.getIdLivro(), evento.getIdEstudante(),
                evento.getDiasDeAtraso());
    }
}
