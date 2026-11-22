package biblioteca.circulacao;

import biblioteca.devolucao.EventoDeDevolucao;
import biblioteca.devolucao.PublicadorDeDevolucao;
import biblioteca.dominio.Comprovante;
import biblioteca.dominio.Devolucao;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.EstadoLivro;
import biblioteca.dominio.Livro;
import biblioteca.dominio.Reserva;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FachadaDeCirculacao {

    private RepositorioLivro acervo;
    private RepositorioMovimentacao movimentacao;
    private RepositorioRelacionamento relacionamento;
    private UnidadeDeTrabalho unidade;
    private PublicadorDeDevolucao publicador;
    private LocalDate dataAtual = LocalDate.now();

    public FachadaDeCirculacao(RepositorioLivro acervo, RepositorioMovimentacao movimentacao,
            RepositorioRelacionamento relacionamento, UnidadeDeTrabalho unidade,
            PublicadorDeDevolucao publicador) {
        this.acervo = acervo;
        this.movimentacao = movimentacao;
        this.relacionamento = relacionamento;
        this.unidade = unidade;
        this.publicador = publicador;
    }

    public FachadaDeCirculacao(RepositorioLivro acervo, RepositorioMovimentacao movimentacao,
            RepositorioRelacionamento relacionamento, UnidadeDeTrabalho unidade) {
        this(acervo, movimentacao, relacionamento, unidade, new PublicadorDeDevolucao());
    }

    public void definirDataAtual(LocalDate data) {
        this.dataAtual = data;
    }

    public Comprovante solicitarReserva(String idLivro, String idEstudante) {
        Livro livro = buscarLivro(idLivro);
        conferirEstudante(idEstudante);
        EstadoLivro estadoAntes = livro.getEstado();

        if (!livro.solicitarReserva()) {
            throw new CirculacaoException("Reserva recusada: o livro nao esta LIVRE");
        }
        try {
            movimentacao.adicionarReserva(new Reserva(dataAtual, idLivro, idEstudante));
            acervo.salvar(livro);
            unidade.confirmar();
        } catch (RuntimeException e) {
            unidade.desfazer();
            livro.atualizarEstado(estadoAntes);
            throw e;
        }
        return new Comprovante("RESERVA", idLivro, idEstudante, dataAtual, null);
    }

    public List<Comprovante> consultarReservas(String idEstudante) {
        List<Comprovante> comprovantes = new ArrayList<>();
        for (Reserva reserva : movimentacao.consultarReservasDoEstudante(idEstudante)) {
            comprovantes.add(new Comprovante("RESERVA", reserva.getIdLivro(), idEstudante,
                    reserva.getData(), null));
        }
        return comprovantes;
    }

    public Comprovante emprestarLivro(String idLivro, String idEstudante) {
        Livro livro = buscarLivro(idLivro);
        Reserva reserva = movimentacao.consultarReserva(idLivro, idEstudante);
        if (reserva == null) {
            throw new CirculacaoException("Nao ha reserva deste livro para este estudante");
        }
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.emprestarLivro()) {
            throw new CirculacaoException("Emprestimo recusado: o livro nao esta RESERVADO");
        }
        Emprestimo emprestimo = new Emprestimo(dataAtual, idLivro, idEstudante);
        try {
            movimentacao.removerReserva(reserva);
            movimentacao.adicionarEmprestimo(emprestimo);
            acervo.salvar(livro);
            unidade.confirmar();
        } catch (RuntimeException e) {
            unidade.desfazer();
            livro.atualizarEstado(estadoAntes);
            if (movimentacao.consultarReserva(idLivro, idEstudante) == null) {
                movimentacao.adicionarReserva(reserva);
            }
            throw e;
        }
        return new Comprovante("EMPRESTIMO", idLivro, idEstudante, dataAtual,
                emprestimo.getDataVencimento());
    }

    public Comprovante renovarEmprestimo(String idLivro, String idEstudante) {
        Livro livro = buscarLivro(idLivro);
        Emprestimo emprestimo = movimentacao.consultarEmprestimo(idLivro, idEstudante);
        if (emprestimo == null) {
            throw new CirculacaoException("Nao ha emprestimo deste livro para este estudante");
        }
        EstadoLivro estadoAntes = livro.getEstado();
        LocalDate vencimentoAntes = emprestimo.getDataVencimento();
        int renovacoesAntes = emprestimo.getNumRenovacoes();

        if (!livro.renovarEmprestimo(emprestimo)) {
            throw new CirculacaoException("Renovacao recusada (limite de 2 renovacoes)");
        }
        try {
            movimentacao.atualizarEmprestimo(emprestimo);
            acervo.salvar(livro);
            unidade.confirmar();
        } catch (RuntimeException e) {
            unidade.desfazer();
            livro.atualizarEstado(estadoAntes);
            emprestimo.setDataVencimento(vencimentoAntes);
            emprestimo.setNumRenovacoes(renovacoesAntes);
            throw e;
        }
        return new Comprovante("RENOVACAO", idLivro, idEstudante, dataAtual,
                emprestimo.getDataVencimento());
    }

    public Comprovante registrarDevolucao(String idLivro, String idEstudante) {
        Livro livro = buscarLivro(idLivro);
        Emprestimo emprestimo = movimentacao.consultarEmprestimo(idLivro, idEstudante);
        if (emprestimo == null) {
            throw new CirculacaoException("Nao ha emprestimo deste livro para este estudante");
        }
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.registrarDevolucao()) {
            throw new CirculacaoException("Devolucao recusada: o livro nao esta emprestado");
        }
        try {
            movimentacao.removerEmprestimo(emprestimo);
            movimentacao.adicionarDevolucao(new Devolucao(dataAtual, idLivro, idEstudante));
            acervo.salvar(livro);
            unidade.confirmar();
        } catch (RuntimeException e) {
            unidade.desfazer();
            livro.atualizarEstado(estadoAntes);
            if (movimentacao.consultarEmprestimo(idLivro, idEstudante) == null) {
                movimentacao.adicionarEmprestimo(emprestimo);
            }
            throw e;
        }
        publicador.publicar(new EventoDeDevolucao(idLivro, idEstudante,
                emprestimo.getDataVencimento(), dataAtual));
        return new Comprovante("DEVOLUCAO", idLivro, idEstudante, dataAtual,
                emprestimo.getDataVencimento());
    }

    public Comprovante inspecionarLivro(String idLivro, boolean bomEstado) {
        Livro livro = buscarLivro(idLivro);
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.inspecionarLivro(bomEstado)) {
            throw new CirculacaoException("Inspecao recusada: o livro nao esta DEVOLVIDO");
        }
        gravarLivro(livro, estadoAntes);
        return new Comprovante("INSPECAO", idLivro, null, dataAtual, null);
    }

    public Comprovante enviarParaReparo(String idLivro) {
        Livro livro = buscarLivro(idLivro);
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.enviarParaReparo()) {
            throw new CirculacaoException("Envio para reparo recusado");
        }
        gravarLivro(livro, estadoAntes);
        return new Comprovante("ENVIO_PARA_REPARO", idLivro, null, dataAtual, null);
    }

    public Comprovante retornarDoReparo(String idLivro) {
        Livro livro = buscarLivro(idLivro);
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.retornarDoReparo()) {
            throw new CirculacaoException("Retorno do reparo recusado: o livro nao esta REPARANDO");
        }
        gravarLivro(livro, estadoAntes);
        return new Comprovante("RETORNO_DO_REPARO", idLivro, null, dataAtual, null);
    }

    public Comprovante incinerarLivro(String idLivro) {
        Livro livro = buscarLivro(idLivro);
        EstadoLivro estadoAntes = livro.getEstado();
        if (!livro.incinerarLivro()) {
            throw new CirculacaoException("Incineracao recusada: o livro nao esta REPARANDO");
        }
        gravarLivro(livro, estadoAntes);
        return new Comprovante("INCINERACAO", idLivro, null, dataAtual, null);
    }

    private Livro buscarLivro(String idLivro) {
        Livro livro = acervo.buscarPorId(idLivro);
        if (livro == null) {
            throw new CirculacaoException("Livro nao cadastrado: " + idLivro);
        }
        return livro;
    }

    private void conferirEstudante(String idEstudante) {
        if (relacionamento.buscarEstudante(idEstudante) == null) {
            throw new CirculacaoException("Estudante nao cadastrado: " + idEstudante);
        }
    }

    private void gravarLivro(Livro livro, EstadoLivro estadoAntes) {
        try {
            acervo.salvar(livro);
            unidade.confirmar();
        } catch (RuntimeException e) {
            unidade.desfazer();
            livro.atualizarEstado(estadoAntes);
            throw e;
        }
    }
}
