package biblioteca.consulta;

import java.util.HashMap;
import java.util.Map;

public class ConsultaComCache implements ConsultaDeAcervo {

    private ConsultaDeAcervo proxima;
    private Map<String, MetadadosDoLivro> cache = new HashMap<>();
    private int acertos = 0;

    public ConsultaComCache(ConsultaDeAcervo proxima) {
        this.proxima = proxima;
    }

    @Override
    public MetadadosDoLivro consultarMetadados(String idLivro) {
        MetadadosDoLivro guardado = null;
        try {
            guardado = lerDoCache(idLivro);
        } catch (RuntimeException e) {
        }
        if (guardado != null) {
            acertos++;
            return guardado;
        }
        MetadadosDoLivro resultado = proxima.consultarMetadados(idLivro);
        if (resultado != null) {
            try {
                guardarNoCache(idLivro, resultado);
            } catch (RuntimeException e) {
            }
        }
        return resultado;
    }

    @Override
    public boolean podeReservar(String idLivro) {
        return proxima.podeReservar(idLivro);
    }

    protected MetadadosDoLivro lerDoCache(String idLivro) {
        return cache.get(idLivro);
    }

    protected void guardarNoCache(String idLivro, MetadadosDoLivro metadados) {
        cache.put(idLivro, metadados);
    }

    public int getAcertos() {
        return acertos;
    }
}
