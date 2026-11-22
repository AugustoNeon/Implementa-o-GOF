package biblioteca.cadastro;

import biblioteca.dominio.Livro;
import biblioteca.persistencia.RepositorioLivro;
import java.util.UUID;

public class InclusaoDeLivro extends InclusaoDeCadastro<Livro> {

    private RepositorioLivro acervo;
    private String titulo;

    public InclusaoDeLivro(RepositorioLivro acervo, String titulo) {
        this.acervo = acervo;
        this.titulo = titulo;
    }

    @Override
    protected Livro buscarExistente() {
        return acervo.buscarPorTitulo(titulo);
    }

    @Override
    protected void validar() {
        if (estaVazio(titulo)) {
            throw new CadastroException("O titulo do livro nao pode ser vazio");
        }
    }

    @Override
    protected void verificarPreRequisitos() {
    }

    @Override
    protected Livro criar() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new Livro(id, titulo);
    }

    @Override
    protected void armazenar(Livro livro) {
        acervo.salvar(livro);
    }

    @Override
    protected void apresentar(Livro livro) {
        System.out.println("Livro: " + livro.getId() + " - " + livro.getTitulo()
                + " [" + livro.getEstado() + "]");
    }
}
