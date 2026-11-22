package biblioteca.dominio;

public interface iAtendente {

    Atendente inserirAtendente(String cpf, String matriculaFuncional);

    Atendente consultarAtendente(String matriculaFuncional);

    boolean atualizarAtendente(Atendente atendente);

    boolean excluirAtendente(Atendente atendente);
}
