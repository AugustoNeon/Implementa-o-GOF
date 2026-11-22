package biblioteca.cobranca;

import biblioteca.cobranca.banco.BancoB;
import biblioteca.cobranca.banco.NotificacaoBancoB;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdaptadorBancoB implements iCobranca {

    private BancoB banco;
    private iQuitacao quitacao;
    private Map<String, String> idsDaInstituicao = new HashMap<>();

    public AdaptadorBancoB(BancoB banco, iQuitacao quitacao) {
        this.banco = banco;
        this.quitacao = quitacao;
    }

    @Override
    public ResultadoDeCobranca emitirCobranca(PedidoDeCobranca pedido) {
        String valorEmReais = String.format(Locale.US, "%.2f", pedido.getValor());
        String cpfFormatado = formatarCpf(pedido.getCpf());

        int numero = banco.criarCobranca(cpfFormatado, valorEmReais);

        String idBanco = String.valueOf(numero);
        idsDaInstituicao.put(idBanco, pedido.getIdCobranca());
        return new ResultadoDeCobranca(idBanco, SituacaoDeCobranca.EMITIDA);
    }

    public void receberNotificacao(NotificacaoBancoB notificacao) {
        String idCobranca = idsDaInstituicao.get(String.valueOf(notificacao.getNumero()));
        if (idCobranca != null && traduzirSituacao(notificacao.getStatus()) == SituacaoDeCobranca.PAGA) {
            quitacao.registrarQuitacao(idCobranca);
        }
    }

    private SituacaoDeCobranca traduzirSituacao(String status) {
        if ("PAID".equals(status)) {
            return SituacaoDeCobranca.PAGA;
        }
        return SituacaoDeCobranca.EMITIDA;
    }

    private String formatarCpf(String cpf) {
        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() != 11) {
            return cpf;
        }
        return digitos.substring(0, 3) + "." + digitos.substring(3, 6) + "."
                + digitos.substring(6, 9) + "-" + digitos.substring(9);
    }
}
