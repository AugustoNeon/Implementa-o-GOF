package biblioteca.consulta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.dominio.Livro;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.memoria.RepositorioLivroEmMemoria;
import biblioteca.persistencia.memoria.UnidadeDeTrabalhoEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsultaTest {

    private RepositorioLivro acervo = new RepositorioLivroEmMemoria(new UnidadeDeTrabalhoEmMemoria());
    private Livro livro = new Livro("L1", "Padroes de Projeto");

    @BeforeEach
    void preparar() {
        acervo.salvar(livro);
    }

    @Test
    void R09_segundaConsultaUsaCacheEAindaGeraMedicao() {
        ConsultaDeAcervo original = new ConsultaDeAcervoPadrao(acervo);
        ConsultaComCache cache = new ConsultaComCache(original);
        ConsultaComMedicao medicao = new ConsultaComMedicao(cache);

        medicao.consultarMetadados("L1");
        MetadadosDoLivro segunda = medicao.consultarMetadados("L1");

        assertEquals("Padroes de Projeto", segunda.getTitulo());
        assertEquals(1, cache.getAcertos());
        assertEquals(2, medicao.getTotalDeConsultas());
    }

    @Test
    void R09_cacheAntigoNuncaAutorizaReservaDeLivroQueNaoEstaLivre() {
        ConsultaDeAcervo consulta = new ConsultaComMedicao(new ConsultaComCache(new ConsultaDeAcervoPadrao(acervo)));
        consulta.consultarMetadados("L1");
        assertTrue(consulta.podeReservar("L1"));

        livro.solicitarReserva();

        assertNotNull(consulta.consultarMetadados("L1"));
        assertFalse(consulta.podeReservar("L1"));
    }

    @Test
    void R09_seOCacheFalharAConsultaContinuaFuncionando() {
        ConsultaComCache cacheQuebrado = new ConsultaComCache(new ConsultaDeAcervoPadrao(acervo)) {
            @Override
            protected MetadadosDoLivro lerDoCache(String idLivro) {
                throw new RuntimeException("cache fora do ar");
            }

            @Override
            protected void guardarNoCache(String idLivro, MetadadosDoLivro metadados) {
                throw new RuntimeException("cache fora do ar");
            }
        };

        MetadadosDoLivro resultado = cacheQuebrado.consultarMetadados("L1");

        assertEquals("L1", resultado.getId());
        assertEquals(0, cacheQuebrado.getAcertos());
    }

}
