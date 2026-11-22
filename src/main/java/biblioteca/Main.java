package biblioteca;

import biblioteca.dominio.Comprovante;
import biblioteca.dominio.Livro;
import biblioteca.multa.Multa;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        String modo = args.length > 0 ? args[0] : "menu";
        String perfil = args.length > 1 ? args[1] : "teste";
        String banco = args.length > 2 ? args[2] : "A";

        Sistema sistema = new Sistema(perfil, banco);
        try {
            if (modo.equals("demo")) {
                new Demonstracao(sistema).executar();
            } else {
                menu(sistema);
            }
        } finally {
            sistema.fechar();
        }
    }

    private static void menu(Sistema sistema) {
        int opcao = -1;
        while (opcao != 0) {
            mostrarMenu();
            opcao = lerNumero("Opcao: ");
            try {
                executarOpcao(sistema, opcao);
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== BIBLIOTECA iB2B =====");
        System.out.println("-- Administrador --");
        System.out.println(" 1 Cadastrar pessoa");
        System.out.println(" 2 Cadastrar estudante");
        System.out.println(" 3 Cadastrar atendente");
        System.out.println(" 4 Cadastrar livro");
        System.out.println(" 5 Consultar livro (por titulo)");
        System.out.println("-- Estudante --");
        System.out.println(" 6 Reservar livro");
        System.out.println(" 7 Consultar reservas");
        System.out.println(" 8 Emprestar livro");
        System.out.println(" 9 Renovar emprestimo");
        System.out.println("-- Atendente --");
        System.out.println("10 Registrar devolucao");
        System.out.println("11 Inspecionar livro");
        System.out.println("12 Enviar livro para reparo");
        System.out.println("13 Retornar livro do reparo");
        System.out.println("14 Incinerar livro (laudo irrecuperavel)");
        System.out.println("15 Emitir boleto de multa");
        System.out.println("-- Demonstracao --");
        System.out.println("16 Definir a data atual");
        System.out.println("17 Simular pagamento do boleto");
        System.out.println("18 Definir politica de multa");
        System.out.println(" 0 Sair");
    }

    private static void executarOpcao(Sistema sistema, int opcao) {
        if (opcao == 1) {
            sistema.getTratadorDePessoa().inserirPessoa(
                    ler("Nome: "), ler("Sobrenome: "), ler("CPF: "));
        } else if (opcao == 2) {
            sistema.getTratadorDeEstudante().inserirEstudante(ler("CPF da pessoa: "), ler("Matricula: "));
        } else if (opcao == 3) {
            sistema.getTratadorDeAtendente().inserirAtendente(
                    ler("CPF da pessoa: "), ler("Matricula funcional: "));
        } else if (opcao == 4) {
            sistema.getTratadorDeAcervo().inserirLivro(ler("Titulo: "));
        } else if (opcao == 5) {
            Livro livro = sistema.getTratadorDeAcervo().consultarLivro(ler("Titulo: "));
            if (livro == null) {
                System.out.println("Livro nao cadastrado.");
            } else {
                System.out.println("Livro: " + livro.getId() + " - " + livro.getTitulo()
                        + " [" + livro.getEstado() + "]");
            }
        } else if (opcao == 6) {
            mostrar(sistema.getTratadorDeEmprestimo().solicitarReserva(ler("Id do livro: "), ler("Matricula: ")));
        } else if (opcao == 7) {
            for (Comprovante reserva : sistema.getTratadorDeEmprestimo().consultarReservas(ler("Matricula: "))) {
                mostrar(reserva);
            }
        } else if (opcao == 8) {
            mostrar(sistema.getTratadorDeEmprestimo().emprestarLivro(ler("Id do livro: "), ler("Matricula: ")));
        } else if (opcao == 9) {
            mostrar(sistema.getTratadorDeEmprestimo().renovarEmprestimo(ler("Id do livro: "), ler("Matricula: ")));
        } else if (opcao == 10) {
            mostrar(sistema.getTratadorDeDevolucao().registrarDevolucao(ler("Id do livro: "), ler("Matricula: ")));
        } else if (opcao == 11) {
            String idLivro = ler("Id do livro: ");
            boolean bomEstado = ler("Livro em bom estado? (s/n): ").equalsIgnoreCase("s");
            mostrar(sistema.getTratadorDeDevolucao().inspecionarLivro(idLivro, bomEstado));
        } else if (opcao == 12) {
            mostrar(sistema.getTratadorDeDevolucao().enviarParaReparo(ler("Id do livro: ")));
        } else if (opcao == 13) {
            mostrar(sistema.getTratadorDeDevolucao().retornarDoReparo(ler("Id do livro: ")));
        } else if (opcao == 14) {
            mostrar(sistema.getTratadorDeDevolucao().incinerarLivro(ler("Id do livro: ")));
        } else if (opcao == 15) {
            Multa multa = sistema.getTratadorDeDevolucao().emitirBoletoDeMulta(
                    ler("Id do livro: "), ler("Matricula: "));
            mostrarMulta(multa);
        } else if (opcao == 16) {
            sistema.definirDataAtual(LocalDate.parse(ler("Data (aaaa-mm-dd): ")));
        } else if (opcao == 17) {
            Multa multa = sistema.getTratadorDeDevolucao().emitirBoletoDeMulta(
                    ler("Id do livro: "), ler("Matricula: "));
            System.out.println("Multa paga: " + sistema.simularPagamento(multa));
        } else if (opcao == 18) {
            sistema.definirPoliticaDeMulta(ler("Politica (ORIGINAL, TETO ou TOLERANCIA): "));
        } else if (opcao != 0) {
            System.out.println("Opcao invalida.");
        }
    }

    private static void mostrar(Comprovante comprovante) {
        System.out.println("Comprovante: " + comprovante);
    }

    private static void mostrarMulta(Multa multa) {
        if (multa == null) {
            System.out.println("Nao ha multa para esta devolucao.");
        } else {
            System.out.println(multa);
        }
    }

    private static String ler(String pergunta) {
        System.out.print(pergunta);
        return teclado.nextLine().trim();
    }

    private static int lerNumero(String pergunta) {
        String texto = ler(pergunta);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
