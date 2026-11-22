package biblioteca.cadastro;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import biblioteca.dominio.Estudante;
import biblioteca.persistencia.Configuracao;
import biblioteca.persistencia.FabricaDeRepositorios;
import biblioteca.persistencia.RepositorioLivro;
import biblioteca.persistencia.RepositorioRelacionamento;
import biblioteca.persistencia.UnidadeDeTrabalho;
import biblioteca.tratador.TratadorDeEstudante;
import biblioteca.tratador.TratadorDePessoa;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CadastroTest {

    private UnidadeDeTrabalho unidade;
    private RepositorioLivro acervo;
    private RepositorioRelacionamento relacionamento;

    @BeforeEach
    void preparar() {
        FabricaDeRepositorios fabrica = new Configuracao("teste").getFabrica();
        unidade = fabrica.criarUnidadeDeTrabalho();
        acervo = fabrica.criarRepositorioLivro(unidade);
        relacionamento = fabrica.criarRepositorioRelacionamento(unidade);
    }

    @Test
    void R07_estudanteDuplicadoRetornaCadastroExistente() {
        TratadorDePessoa tratadorPessoa = new TratadorDePessoa(relacionamento, unidade);
        TratadorDeEstudante tratadorEstudante = new TratadorDeEstudante(relacionamento, unidade);
        tratadorPessoa.inserirPessoa("Maria", "Silva", "123");

        Estudante primeiro = tratadorEstudante.inserirEstudante("123", "M1");
        Estudante segundo = tratadorEstudante.inserirEstudante("123", "M1");

        assertSame(primeiro, segundo);
    }

    @Test
    void R07_estudanteComCpfNaoCadastradoFalhaAntesDeGravar() {
        TratadorDeEstudante tratadorEstudante = new TratadorDeEstudante(relacionamento, unidade);

        assertThrows(CadastroException.class,
                () -> tratadorEstudante.inserirEstudante("999", "M2"));

        assertNull(relacionamento.buscarEstudante("M2"));
        assertNull(relacionamento.buscarPessoa("999"));
    }

    @Test
    void R07_metodoModeloEhFinal() throws Exception {
        int modificadores = InclusaoDeCadastro.class.getMethod("incluir").getModifiers();
        assertTrue(Modifier.isFinal(modificadores));
    }

}
