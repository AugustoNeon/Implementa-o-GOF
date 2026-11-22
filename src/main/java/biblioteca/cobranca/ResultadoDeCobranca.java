package biblioteca.cobranca;

public class ResultadoDeCobranca {

    private String idBoleto;
    private SituacaoDeCobranca situacao;

    public ResultadoDeCobranca(String idBoleto, SituacaoDeCobranca situacao) {
        this.idBoleto = idBoleto;
        this.situacao = situacao;
    }

    public String getIdBoleto() {
        return idBoleto;
    }

    public SituacaoDeCobranca getSituacao() {
        return situacao;
    }
}
