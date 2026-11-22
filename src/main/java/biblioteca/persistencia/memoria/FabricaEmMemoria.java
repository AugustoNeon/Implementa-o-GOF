package biblioteca.persistencia.memoria;

import biblioteca.persistencia.FabricaDeRepositorios;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class FabricaEmMemoria implements FabricaDeRepositorios {

    @Override
    public UnidadeDeTrabalho criarUnidadeDeTrabalho() {
        return new UnidadeDeTrabalhoEmMemoria();
    }

    @Override
    public RepositorioLivro criarRepositorioLivro(UnidadeDeTrabalho unidade) {
        return new RepositorioLivroEmMemoria(unidade);
    }

    @Override
    public RepositorioMovimentacao criarRepositorioMovimentacao(UnidadeDeTrabalho unidade) {
        return new RepositorioMovimentacaoEmMemoria(unidade);
    }

    @Override
    public RepositorioRelacionamento criarRepositorioRelacionamento(UnidadeDeTrabalho unidade) {
        return new RepositorioRelacionamentoEmMemoria(unidade);
    }
}
