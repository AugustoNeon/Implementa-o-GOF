package biblioteca.dominio;

import java.util.List;

public interface iEmprestimo {

    Comprovante solicitarReserva(String idLivro, String idEstudante);

    List<Comprovante> consultarReservas(String idEstudante);

    Comprovante emprestarLivro(String idLivro, String idEstudante);

    Comprovante renovarEmprestimo(String idLivro, String idEstudante);
}
