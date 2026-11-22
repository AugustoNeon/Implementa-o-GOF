package biblioteca;

import biblioteca.dominio.Comprovante;
import biblioteca.dominio.Livro;
import biblioteca.multa.Multa;
import java.time.LocalDate;

public class Demonstracao {

    private Sistema sistema;

    public Demonstracao(Sistema sistema) {
        this.sistema = sistema;
    }

    public void executar() {
        titulo("1. Cadastros (Administrador)");
        sistema.getTratadorDePessoa().inserirPessoa("Maria", "Silva", "12345678909");
        sistema.getTratadorDePessoa().inserirPessoa("Joao", "Souza", "98765432100");
        sistema.getTratadorDeEstudante().inserirEstudante("12345678909", "2024001");
        sistema.getTratadorDeAtendente().inserirAtendente("98765432100", "F001");
        Livro livro = sistema.getTratadorDeAcervo().inserirLivro("Padroes de Projeto");
        String idLivro = livro.getId();

        titulo("2. Reserva e emprestimo (Estudante) - data 01/11/2026");
        sistema.definirDataAtual(LocalDate.of(2026, 11, 1));
        mostrar(sistema.getTratadorDeEmprestimo().solicitarReserva(idLivro, "2024001"));
        for (Comprovante reserva : sistema.getTratadorDeEmprestimo().consultarReservas("2024001")) {
            System.out.println("Reserva encontrada: " + reserva);
        }
        mostrar(sistema.getTratadorDeEmprestimo().emprestarLivro(idLivro, "2024001"));

        titulo("3. Renovacao (Estudante) - ate duas vezes");
        mostrar(sistema.getTratadorDeEmprestimo().renovarEmprestimo(idLivro, "2024001"));
        mostrar(sistema.getTratadorDeEmprestimo().renovarEmprestimo(idLivro, "2024001"));
        try {
            sistema.getTratadorDeEmprestimo().renovarEmprestimo(idLivro, "2024001");
        } catch (RuntimeException e) {
            System.out.println("Terceira renovacao recusada: " + e.getMessage());
        }

        titulo("4. Devolucao atrasada (Atendente) - 5 dias depois do vencimento");
        sistema.definirDataAtual(LocalDate.of(2027, 2, 4));
        mostrar(sistema.getTratadorDeDevolucao().registrarDevolucao(idLivro, "2024001"));
        System.out.println("Livros na fila de inspecao: " + sistema.getInspecao().getLivrosParaInspecionar());
        System.out.println("Indicadores: " + sistema.getIndicadores().getTotalDeDevolucoes()
                + " devolucao(oes), " + sistema.getIndicadores().getTotalDeAtrasos() + " com atraso");

        titulo("5. Multa e boleto");
        Multa multa = sistema.getTratadorDeDevolucao().emitirBoletoDeMulta(idLivro, "2024001");
        System.out.println(multa);
        System.out.println("Pagamento notificado pelo banco: multa paga = " + sistema.simularPagamento(multa));
        sistema.simularPagamento(multa);
        System.out.println("Notificacao repetida nao quita de novo (multa continua paga uma vez so).");

        titulo("6. Inspecao (Atendente)");
        mostrar(sistema.getTratadorDeDevolucao().inspecionarLivro(idLivro, true));
        System.out.println("Livro de volta ao acervo: "
                + sistema.getTratadorDeAcervo().consultarLivro("Padroes de Projeto").getEstado());
    }

    private void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    private void mostrar(Comprovante comprovante) {
        System.out.println("Comprovante: " + comprovante);
    }
}
