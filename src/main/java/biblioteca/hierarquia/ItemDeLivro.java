package biblioteca.hierarquia;

import java.util.ArrayList;
import java.util.List;

public class ItemDeLivro extends ComponenteDoAcervo {

    private String idLivro;

    public ItemDeLivro(String idLivro) {
        super(idLivro);
        this.idLivro = idLivro;
    }

    public String getIdLivro() {
        return idLivro;
    }

    @Override
    public int contarLivros() {
        return 1;
    }

    @Override
    public List<String> listarLivros() {
        List<String> ids = new ArrayList<>();
        ids.add(idLivro);
        return ids;
    }
}
