package biblioteca.devolucao;

import java.util.ArrayList;
import java.util.List;

public class OuvinteDeInspecao implements OuvinteDeDevolucao {

    private List<String> livrosParaInspecionar = new ArrayList<>();

    @Override
    public void aoDevolver(EventoDeDevolucao evento) {
        livrosParaInspecionar.add(evento.getIdLivro());
    }

    public List<String> getLivrosParaInspecionar() {
        return new ArrayList<>(livrosParaInspecionar);
    }
}
