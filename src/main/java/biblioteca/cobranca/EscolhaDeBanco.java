package biblioteca.cobranca;

import biblioteca.cobranca.banco.BancoA;
import biblioteca.cobranca.banco.BancoB;

public class EscolhaDeBanco {

    public static iCobranca escolher(String nomeDoBanco, iQuitacao quitacao) {
        if (nomeDoBanco.equals("A")) {
            return new AdaptadorBancoA(new BancoA(), quitacao);
        } else if (nomeDoBanco.equals("B")) {
            return new AdaptadorBancoB(new BancoB(), quitacao);
        }
        throw new IllegalArgumentException("Banco desconhecido: " + nomeDoBanco);
    }
}
