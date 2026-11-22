package biblioteca;

import biblioteca.circulacao.FachadaDeCirculacao;
import biblioteca.cobranca.AdaptadorBancoA;
import biblioteca.cobranca.AdaptadorBancoB;
import biblioteca.cobranca.EscolhaDeBanco;
import biblioteca.cobranca.banco.NotificacaoBancoA;
import biblioteca.cobranca.banco.NotificacaoBancoB;
import biblioteca.cobranca.iCobranca;
import biblioteca.devolucao.EnviadorSimulado;
import biblioteca.devolucao.OuvinteDeEmail;
import biblioteca.devolucao.OuvinteDeIndicadores;
import biblioteca.devolucao.OuvinteDeInspecao;
import biblioteca.devolucao.OuvinteDeMulta;
import biblioteca.devolucao.PublicadorDeDevolucao;
import biblioteca.multa.ConfiguracaoAdministrativa;
import biblioteca.multa.Multa;
import biblioteca.multa.PoliticaComTeto;
import biblioteca.multa.PoliticaComTolerancia;
import biblioteca.multa.PoliticaOriginal;
import biblioteca.multa.ServicoDeMultas;
import biblioteca.persistencia.Configuracao;
import biblioteca.persistencia.FabricaDeRepositorios;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioMovimentacao;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import biblioteca.solicitacao.ExecutorDeSolicitacoes;
import biblioteca.tratador.TratadorDeAcervo;
import biblioteca.tratador.TratadorDeAtendente;
import biblioteca.tratador.TratadorDeDevolucao;
import biblioteca.tratador.TratadorDeEmprestimo;
import biblioteca.tratador.TratadorDeEstudante;
import biblioteca.tratador.TratadorDePessoa;
import java.time.LocalDate;

public class Sistema {

    private UnidadeDeTrabalho unidade;
    private FachadaDeCirculacao fachada;
    private ConfiguracaoAdministrativa configuracaoAdministrativa;
    private ServicoDeMultas servicoDeMultas;
    private iCobranca cobranca;
    private OuvinteDeInspecao inspecao = new OuvinteDeInspecao();
    private OuvinteDeIndicadores indicadores = new OuvinteDeIndicadores();

    private TratadorDeAcervo tratadorDeAcervo;
    private TratadorDePessoa tratadorDePessoa;
    private TratadorDeEstudante tratadorDeEstudante;
    private TratadorDeAtendente tratadorDeAtendente;
    private TratadorDeEmprestimo tratadorDeEmprestimo;
    private TratadorDeDevolucao tratadorDeDevolucao;

    public Sistema(String perfil, String banco) {
        FabricaDeRepositorios fabrica = new Configuracao(perfil).getFabrica();
        unidade = fabrica.criarUnidadeDeTrabalho();
        RepositorioLivro acervo = fabrica.criarRepositorioLivro(unidade);
        RepositorioMovimentacao movimentacao = fabrica.criarRepositorioMovimentacao(unidade);
        RepositorioRelacionamento relacionamento = fabrica.criarRepositorioRelacionamento(unidade);

        configuracaoAdministrativa = new ConfiguracaoAdministrativa();
        servicoDeMultas = new ServicoDeMultas(configuracaoAdministrativa);
        cobranca = EscolhaDeBanco.escolher(banco, servicoDeMultas);
        servicoDeMultas.definirCobranca(cobranca);

        PublicadorDeDevolucao publicador = new PublicadorDeDevolucao();
        publicador.adicionar(inspecao);
        publicador.adicionar(new OuvinteDeMulta(servicoDeMultas));
        publicador.adicionar(indicadores);
        publicador.adicionar(new OuvinteDeEmail(new EnviadorSimulado()));

        fachada = new FachadaDeCirculacao(acervo, movimentacao, relacionamento, unidade, publicador);

        tratadorDeAcervo = new TratadorDeAcervo(acervo, unidade);
        tratadorDePessoa = new TratadorDePessoa(relacionamento, unidade);
        tratadorDeEstudante = new TratadorDeEstudante(relacionamento, unidade);
        tratadorDeAtendente = new TratadorDeAtendente(relacionamento, unidade);
        tratadorDeEmprestimo = new TratadorDeEmprestimo(fachada, new ExecutorDeSolicitacoes());
        tratadorDeDevolucao = new TratadorDeDevolucao(fachada, servicoDeMultas, relacionamento);
    }

    public TratadorDeAcervo getTratadorDeAcervo() {
        return tratadorDeAcervo;
    }

    public TratadorDePessoa getTratadorDePessoa() {
        return tratadorDePessoa;
    }

    public TratadorDeEstudante getTratadorDeEstudante() {
        return tratadorDeEstudante;
    }

    public TratadorDeAtendente getTratadorDeAtendente() {
        return tratadorDeAtendente;
    }

    public TratadorDeEmprestimo getTratadorDeEmprestimo() {
        return tratadorDeEmprestimo;
    }

    public TratadorDeDevolucao getTratadorDeDevolucao() {
        return tratadorDeDevolucao;
    }

    public OuvinteDeInspecao getInspecao() {
        return inspecao;
    }

    public OuvinteDeIndicadores getIndicadores() {
        return indicadores;
    }

    public void definirDataAtual(LocalDate data) {
        fachada.definirDataAtual(data);
    }

    public void definirPoliticaDeMulta(String nome) {
        if (nome.equals("ORIGINAL")) {
            configuracaoAdministrativa.definirPolitica(new PoliticaOriginal());
        } else if (nome.equals("TETO")) {
            configuracaoAdministrativa.definirPolitica(new PoliticaComTeto());
        } else if (nome.equals("TOLERANCIA")) {
            configuracaoAdministrativa.definirPolitica(new PoliticaComTolerancia());
        } else {
            throw new IllegalArgumentException("Politica desconhecida: " + nome);
        }
    }

    public boolean simularPagamento(Multa multa) {
        if (multa == null || multa.getIdBoleto() == null) {
            return false;
        }
        if (cobranca instanceof AdaptadorBancoA) {
            ((AdaptadorBancoA) cobranca).receberNotificacao(new NotificacaoBancoA(multa.getIdBoleto(), 2));
        } else if (cobranca instanceof AdaptadorBancoB) {
            ((AdaptadorBancoB) cobranca).receberNotificacao(
                    new NotificacaoBancoB(Integer.parseInt(multa.getIdBoleto()), "PAID"));
        }
        return multa.isPaga();
    }

    public void fechar() {
        unidade.fechar();
    }
}
