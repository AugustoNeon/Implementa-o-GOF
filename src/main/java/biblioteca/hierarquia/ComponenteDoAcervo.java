package biblioteca.hierarquia;

import java.util.List;

public abstract class ComponenteDoAcervo {

    private String nome;
    private GrupoDoAcervo pai;

    public ComponenteDoAcervo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public GrupoDoAcervo getPai() {
        return pai;
    }

    void definirPai(GrupoDoAcervo pai) {
        this.pai = pai;
    }

    public abstract int contarLivros();

    public abstract List<String> listarLivros();

    public void moverPara(GrupoDoAcervo destino) {
        GrupoDoAcervo origem = pai;
        if (origem == null) {
            throw new IllegalStateException("Este componente nao esta em nenhum grupo");
        }
        origem.remover(this);
        try {
            destino.adicionar(this);
        } catch (RuntimeException e) {
            origem.adicionar(this);
            throw e;
        }
    }
}
