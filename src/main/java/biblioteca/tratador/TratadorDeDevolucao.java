package biblioteca.tratador;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.Comprovante;
import biblioteca.dominio.Estudante;
import biblioteca.dominio.iDevolucao;
import biblioteca.multa.Multa;
import biblioteca.multa.ServicoDeMultas;
import biblioteca.persistencia.RepositorioRelacionamento;

public class TratadorDeDevolucao implements iDevolucao {

    private FachadaDeCirculacao fachada;
    private ServicoDeMultas servicoDeMultas;
    private RepositorioRelacionamento relacionamento;

    public TratadorDeDevolucao(FachadaDeCirculacao fachada, ServicoDeMultas servicoDeMultas,
            RepositorioRelacionamento relacionamento) {
        this.fachada = fachada;
        this.servicoDeMultas = servicoDeMultas;
        this.relacionamento = relacionamento;
    }

    @Override
    public Comprovante registrarDevolucao(String idLivro, String idEstudante) {
        return fachada.registrarDevolucao(idLivro, idEstudante);
    }

    @Override
    public Multa emitirBoletoDeMulta(String idLivro, String idEstudante) {
        Estudante estudante = relacionamento.buscarEstudante(idEstudante);
        if (estudante == null) {
            return null;
        }
        return servicoDeMultas.emitirBoleto(idLivro, idEstudante, estudante.getPessoa().getCpf());
    }

    public Comprovante inspecionarLivro(String idLivro, boolean bomEstado) {
        return fachada.inspecionarLivro(idLivro, bomEstado);
    }

    public Comprovante enviarParaReparo(String idLivro) {
        return fachada.enviarParaReparo(idLivro);
    }

    public Comprovante retornarDoReparo(String idLivro) {
        return fachada.retornarDoReparo(idLivro);
    }

    public Comprovante incinerarLivro(String idLivro) {
        return fachada.incinerarLivro(idLivro);
    }
}
