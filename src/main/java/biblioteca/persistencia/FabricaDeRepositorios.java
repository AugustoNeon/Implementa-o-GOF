package biblioteca.persistencia;

public interface FabricaDeRepositorios {

    UnidadeDeTrabalho criarUnidadeDeTrabalho();

    RepositorioLivro criarRepositorioLivro(UnidadeDeTrabalho unidade);

    RepositorioMovimentacao criarRepositorioMovimentacao(UnidadeDeTrabalho unidade);

    RepositorioRelacionamento criarRepositorioRelacionamento(UnidadeDeTrabalho unidade);
}
