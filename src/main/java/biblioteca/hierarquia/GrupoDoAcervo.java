package biblioteca.hierarquia;

import java.util.ArrayList;
import java.util.List;

public class GrupoDoAcervo extends ComponenteDoAcervo {

    private List<ComponenteDoAcervo> filhos = new ArrayList<>();

    public GrupoDoAcervo(String nome) {
        super(nome);
    }

    public void adicionar(ComponenteDoAcervo componente) {
        if (componente instanceof GrupoDoAcervo) {
            GrupoDoAcervo grupo = (GrupoDoAcervo) componente;
            if (grupo == this || grupo.contemGrupo(this)) {
                throw new IllegalArgumentException("Ciclo: o grupo nao pode ficar dentro dele mesmo");
            }
        }
        if (componente.getPai() != null) {
            throw new IllegalArgumentException(componente.getNome() + " ja pertence a outro grupo");
        }
        List<String> livrosDaArvore = getRaiz().listarLivros();
        for (String idLivro : componente.listarLivros()) {
            if (livrosDaArvore.contains(idLivro)) {
                throw new IllegalArgumentException("O livro " + idLivro + " ja pertence a um grupo");
            }
        }
        filhos.add(componente);
        componente.definirPai(this);
    }

    public void remover(ComponenteDoAcervo componente) {
        if (filhos.remove(componente)) {
            componente.definirPai(null);
        }
    }

    public List<ComponenteDoAcervo> getFilhos() {
        return new ArrayList<>(filhos);
    }

    @Override
    public int contarLivros() {
        int total = 0;
        for (ComponenteDoAcervo filho : filhos) {
            total = total + filho.contarLivros();
        }
        return total;
    }

    @Override
    public List<String> listarLivros() {
        List<String> ids = new ArrayList<>();
        for (ComponenteDoAcervo filho : filhos) {
            ids.addAll(filho.listarLivros());
        }
        return ids;
    }

    private boolean contemGrupo(GrupoDoAcervo procurado) {
        for (ComponenteDoAcervo filho : filhos) {
            if (filho == procurado) {
                return true;
            }
            if (filho instanceof GrupoDoAcervo && ((GrupoDoAcervo) filho).contemGrupo(procurado)) {
                return true;
            }
        }
        return false;
    }

    private GrupoDoAcervo getRaiz() {
        GrupoDoAcervo atual = this;
        while (atual.getPai() != null) {
            atual = atual.getPai();
        }
        return atual;
    }
}
