package biblioteca.dominio;

import biblioteca.estado.EstadoDoLivro;
import biblioteca.estado.EstadoLivre;

public class Livro {

    private String id;
    private String titulo;
    private EstadoDoLivro estado;

    public Livro(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.estado = new EstadoLivre();
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public EstadoLivro getEstado() {
        return estado.getEstado();
    }

    public void atualizarEstado(EstadoLivro novoEstado) {
        this.estado = EstadoDoLivro.aPartirDe(novoEstado);
    }

    public void mudarEstado(EstadoDoLivro novoEstado) {
        this.estado = novoEstado;
    }

    public boolean solicitarReserva() {
        return estado.solicitarReserva(this);
    }

    public boolean emprestarLivro() {
        return estado.emprestarLivro(this);
    }

    public boolean renovarEmprestimo(Emprestimo emprestimo) {
        return estado.renovarEmprestimo(this, emprestimo);
    }

    public boolean registrarDevolucao() {
        return estado.registrarDevolucao(this);
    }

    public boolean inspecionarLivro(boolean bomEstado) {
        return estado.inspecionarLivro(this, bomEstado);
    }

    public boolean enviarParaReparo() {
        return estado.enviarParaReparo(this);
    }

    public boolean retornarDoReparo() {
        return estado.retornarDoReparo(this);
    }

    public boolean incinerarLivro() {
        return estado.incinerarLivro(this);
    }

    public boolean excluirLivro() {
        return estado.excluirLivro(this);
    }
}
