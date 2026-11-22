package biblioteca.cobranca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import biblioteca.cobranca.banco.BancoA;
import biblioteca.cobranca.banco.BancoB;
import biblioteca.multa.ConfiguracaoAdministrativa;
import biblioteca.multa.ServicoDeMultas;
import org.junit.jupiter.api.Test;

class CobrancaTest {

    @Test
    void R03_mesmaSolicitacaoDe7ReaisProduz700CentavosNoBancoAE7NoBancoB() {
        ServicoDeMultas servicoA = new ServicoDeMultas(new ConfiguracaoAdministrativa());
        BancoA bancoA = new BancoA();
        servicoA.definirCobranca(new AdaptadorBancoA(bancoA, servicoA));
        servicoA.emitirMulta("L1", "E1", 7);
        servicoA.emitirBoleto("L1", "E1", "123.456.789-09");

        ServicoDeMultas servicoB = new ServicoDeMultas(new ConfiguracaoAdministrativa());
        BancoB bancoB = new BancoB();
        servicoB.definirCobranca(new AdaptadorBancoB(bancoB, servicoB));
        servicoB.emitirMulta("L1", "E1", 7);
        servicoB.emitirBoleto("L1", "E1", "123.456.789-09");

        assertEquals(700, bancoA.getUltimoValorEmCentavos());
        assertEquals("7.00", bancoB.getUltimoValorEmReais());
        assertEquals("12345678909", bancoA.getUltimoDocumento());
        assertEquals("123.456.789-09", bancoB.getUltimoCpf());
    }

}
