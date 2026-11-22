package biblioteca.estado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EstadoTest {

    private Livro livroEmprestado() {
        Livro livro = new Livro("L1", "Padroes de Projeto");
        livro.solicitarReserva();
        livro.emprestarLivro();
        return livro;
    }

    @Test
    void R01_terceiraRenovacaoRecusada() {
        Livro livro = livroEmprestado();
        Emprestimo emprestimo = new Emprestimo(LocalDate.of(2026, 11, 1), "L1", "E1");

        assertTrue(livro.renovarEmprestimo(emprestimo));
        assertTrue(livro.renovarEmprestimo(emprestimo));
        LocalDate vencimentoAntes = emprestimo.getDataVencimento();

        assertFalse(livro.renovarEmprestimo(emprestimo));

        assertEquals(vencimentoAntes, emprestimo.getDataVencimento());
        assertEquals(2, emprestimo.getNumRenovacoes());
        assertEquals(EstadoLivro.RENOVADO, livro.getEstado());
    }

    @Test
    void R01_livroDevolvidoNaoInspecionadoNaoPodeSerReservado() {
        Livro livro = livroEmprestado();
        livro.registrarDevolucao();

        assertFalse(livro.solicitarReserva());

        assertEquals(EstadoLivro.DEVOLVIDO, livro.getEstado());
    }

}
