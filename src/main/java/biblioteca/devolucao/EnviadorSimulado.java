package biblioteca.devolucao;

import java.util.ArrayList;
import java.util.List;

public class EnviadorSimulado implements EnviadorDeEmail {

    private List<String> mensagensEnviadas = new ArrayList<>();

    @Override
    public void enviar(String destinatario, String mensagem) {
        mensagensEnviadas.add(destinatario + ": " + mensagem);
        System.out.println("[e-mail] para " + destinatario + ": " + mensagem);
    }

    public List<String> getMensagensEnviadas() {
        return new ArrayList<>(mensagensEnviadas);
    }
}
