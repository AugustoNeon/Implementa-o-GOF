package biblioteca.tratador;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.Comprovante;
import biblioteca.dominio.iEmprestimo;
import biblioteca.solicitacao.ExecutorDeSolicitacoes;
import biblioteca.solicitacao.SolicitacaoDeRenovacao;
import biblioteca.solicitacao.SolicitacaoDeReserva;
import java.util.List;
import java.util.UUID;

public class TratadorDeEmprestimo implements iEmprestimo {

    private FachadaDeCirculacao fachada;
    private ExecutorDeSolicitacoes executor;

    public TratadorDeEmprestimo(FachadaDeCirculacao fachada, ExecutorDeSolicitacoes executor) {
        this.fachada = fachada;
        this.executor = executor;
    }

    @Override
    public Comprovante solicitarReserva(String idLivro, String idEstudante) {
        return solicitarReserva(novoId(), idLivro, idEstudante);
    }

    public Comprovante solicitarReserva(String idSolicitacao, String idLivro, String idEstudante) {
        return executor.executar(new SolicitacaoDeReserva(idSolicitacao, fachada, idLivro, idEstudante));
    }

    @Override
    public List<Comprovante> consultarReservas(String idEstudante) {
        return fachada.consultarReservas(idEstudante);
    }

    @Override
    public Comprovante emprestarLivro(String idLivro, String idEstudante) {
        return fachada.emprestarLivro(idLivro, idEstudante);
    }

    @Override
    public Comprovante renovarEmprestimo(String idLivro, String idEstudante) {
        return renovarEmprestimo(novoId(), idLivro, idEstudante);
    }

    public Comprovante renovarEmprestimo(String idSolicitacao, String idLivro, String idEstudante) {
        return executor.executar(new SolicitacaoDeRenovacao(idSolicitacao, fachada, idLivro, idEstudante));
    }

    private String novoId() {
        return UUID.randomUUID().toString();
    }
}
