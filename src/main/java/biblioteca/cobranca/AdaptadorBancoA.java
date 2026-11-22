package biblioteca.cobranca;

import biblioteca.cobranca.banco.BancoA;
import biblioteca.cobranca.banco.NotificacaoBancoA;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorBancoA implements iCobranca {

    private BancoA banco;
    private iQuitacao quitacao;
    private Map<String, String> idsDaInstituicao = new HashMap<>();

    public AdaptadorBancoA(BancoA banco, iQuitacao quitacao) {
        this.banco = banco;
        this.quitacao = quitacao;
    }

    @Override
    public ResultadoDeCobranca emitirCobranca(PedidoDeCobranca pedido) {
        long valorEmCentavos = Math.round(pedido.getValor() * 100);
        String documento = pedido.getCpf().replaceAll("[^0-9]", "");

        String idBanco = banco.gerarBoleto(documento, valorEmCentavos);

        idsDaInstituicao.put(idBanco, pedido.getIdCobranca());
        return new ResultadoDeCobranca(idBanco, SituacaoDeCobranca.EMITIDA);
    }

    public void receberNotificacao(NotificacaoBancoA notificacao) {
        String idCobranca = idsDaInstituicao.get(notificacao.getIdBoleto());
        if (idCobranca != null && traduzirSituacao(notificacao.getCodigoSituacao()) == SituacaoDeCobranca.PAGA) {
            quitacao.registrarQuitacao(idCobranca);
        }
    }

    private SituacaoDeCobranca traduzirSituacao(int codigo) {
        if (codigo == 2) {
            return SituacaoDeCobranca.PAGA;
        }
        return SituacaoDeCobranca.EMITIDA;
    }
}
