package biblioteca.multa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MultaTest {

    @Test
    void R02_cincoDiasDeAtrasoEmCadaPolitica() {
        assertEquals(5.0, new PoliticaOriginal().calcular(5), 0.001);
        assertEquals(3.0, new PoliticaComTolerancia().calcular(5), 0.001);

        assertEquals(0.0, new PoliticaOriginal().calcular(0), 0.001);
        assertEquals(0.0, new PoliticaComTeto().calcular(0), 0.001);
        assertEquals(0.0, new PoliticaComTolerancia().calcular(0), 0.001);
    }

}
