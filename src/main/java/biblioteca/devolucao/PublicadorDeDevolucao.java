package biblioteca.devolucao;

import java.util.ArrayList;
import java.util.List;

public class PublicadorDeDevolucao {

    private List<OuvinteDeDevolucao> ouvintes = new ArrayList<>();

    public void adicionar(OuvinteDeDevolucao ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void publicar(EventoDeDevolucao evento) {
        for (OuvinteDeDevolucao ouvinte : ouvintes) {
            try {
                ouvinte.aoDevolver(evento);
            } catch (RuntimeException e) {
                System.out.println("Ouvinte falhou (" + ouvinte.getClass().getSimpleName()
                        + "): " + e.getMessage());
            }
        }
    }
}
