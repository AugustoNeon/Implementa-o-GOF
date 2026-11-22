package biblioteca.dominio;

import biblioteca.multa.Multa;

public interface iDevolucao {

    Comprovante registrarDevolucao(String idLivro, String idEstudante);

    Multa emitirBoletoDeMulta(String idLivro, String idEstudante);
}
