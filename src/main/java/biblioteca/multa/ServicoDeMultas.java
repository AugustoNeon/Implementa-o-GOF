package biblioteca.multa;

import biblioteca.cobranca.PedidoDeCobranca;
import biblioteca.cobranca.ResultadoDeCobranca;
import biblioteca.cobranca.iCobranca;
import biblioteca.cobranca.iQuitacao;
import java.util.ArrayList;
import java.util.List;

public class ServicoDeMultas implements iQuitacao {

    private ConfiguracaoAdministrativa configuracao;
    private iCobranca cobranca;
    private List<Multa> multasEmitidas = new ArrayList<>();

    public ServicoDeMultas(ConfiguracaoAdministrativa configuracao) {
        this.configuracao = configuracao;
    }

    public void definirCobranca(iCobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Multa emitirMulta(String idLivro, String idEstudante, int diasDeAtraso) {
        if (diasDeAtraso <= 0) {
            return null;
        }
        PoliticaDeMulta politica = configuracao.getPoliticaVigente();
        double valor = politica.calcular(diasDeAtraso);
        Multa multa = new Multa(idLivro, idEstudante, diasDeAtraso, valor, politica.getNome());
        multasEmitidas.add(multa);
        return multa;
    }

    public Multa consultarMulta(String idLivro, String idEstudante) {
        for (int i = multasEmitidas.size() - 1; i >= 0; i--) {
            Multa multa = multasEmitidas.get(i);
            if (multa.getIdLivro().equals(idLivro) && multa.getIdEstudante().equals(idEstudante)) {
                return multa;
            }
        }
        return null;
    }

    public Multa emitirBoleto(String idLivro, String idEstudante, String cpf) {
        Multa multa = consultarMulta(idLivro, idEstudante);
        if (multa == null) {
            return null;
        }
        if (multa.getIdBoleto() != null) {
            return multa;
        }
        if (cobranca == null) {
            throw new IllegalStateException("Nenhum banco foi configurado");
        }
        ResultadoDeCobranca resultado = cobranca.emitirCobranca(
                new PedidoDeCobranca(multa.getId(), cpf, multa.getValor()));
        multa.registrarBoleto(resultado.getIdBoleto());
        return multa;
    }

    @Override
    public boolean registrarQuitacao(String idCobranca) {
        for (Multa multa : multasEmitidas) {
            if (multa.getId().equals(idCobranca)) {
                return multa.quitar();
            }
        }
        return false;
    }
}
