package biblioteca.persistencia.relacional;

import biblioteca.persistencia.FabricaDeRepositorios;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;

public class FabricaRelacional implements FabricaDeRepositorios {

    private String url;
    private String usuario;
    private String senha;

    public FabricaRelacional(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    @Override
    public UnidadeDeTrabalho criarUnidadeDeTrabalho() {
        return new UnidadeDeTrabalhoRelacional(url, usuario, senha);
    }

    @Override
    public RepositorioLivro criarRepositorioLivro(UnidadeDeTrabalho unidade) {
        return new RepositorioLivroRelacional(converter(unidade));
    }

    @Override
    public RepositorioMovimentacao criarRepositorioMovimentacao(UnidadeDeTrabalho unidade) {
        return new RepositorioMovimentacaoRelacional(converter(unidade));
    }

    @Override
    public RepositorioRelacionamento criarRepositorioRelacionamento(UnidadeDeTrabalho unidade) {
        return new RepositorioRelacionamentoRelacional(converter(unidade));
    }

    private UnidadeDeTrabalhoRelacional converter(UnidadeDeTrabalho unidade) {
        if (!(unidade instanceof UnidadeDeTrabalhoRelacional)) {
            throw new IllegalArgumentException("A unidade de trabalho nao e da familia relacional");
        }
        return (UnidadeDeTrabalhoRelacional) unidade;
    }
}
