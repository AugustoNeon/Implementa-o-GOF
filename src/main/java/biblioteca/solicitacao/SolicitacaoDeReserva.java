package biblioteca.solicitacao;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.dominio.Comprovante;

public class SolicitacaoDeReserva extends Solicitacao {

    private FachadaDeCirculacao fachada;
    private String idLivro;
    private String idEstudante;

    public SolicitacaoDeReserva(String id, FachadaDeCirculacao fachada, String idLivro,
            String idEstudante) {
        super(id, idEstudante);
        this.fachada = fachada;
        this.idLivro = idLivro;
        this.idEstudante = idEstudante;
    }

    @Override
    public Comprovante executar() {
        return fachada.solicitarReserva(idLivro, idEstudante);
    }
}
