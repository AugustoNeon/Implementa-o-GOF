package biblioteca.solicitacao;

import biblioteca.dominio.Comprovante;
import java.util.HashMap;
import java.util.Map;

public class ExecutorDeSolicitacoes {

    private Map<String, Comprovante> jaExecutadas = new HashMap<>();

    public synchronized Comprovante executar(Solicitacao solicitacao) {
        if (jaExecutadas.containsKey(solicitacao.getId())) {
            return jaExecutadas.get(solicitacao.getId());
        }
        Comprovante comprovante = solicitacao.executar();
        jaExecutadas.put(solicitacao.getId(), comprovante);
        return comprovante;
    }
}
