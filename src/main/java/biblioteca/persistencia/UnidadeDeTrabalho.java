package biblioteca.persistencia;

public interface UnidadeDeTrabalho {

    void confirmar();

    void desfazer();

    void fechar();
}
