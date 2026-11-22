package biblioteca.persistencia;

import biblioteca.persistencia.memoria.FabricaEmMemoria;
import biblioteca.persistencia.relacional.FabricaRelacional;

public class Configuracao {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "biblioteca";
    private static final String SENHA = "biblioteca123";

    private FabricaDeRepositorios fabrica;

    public Configuracao(String perfil) {
        if (perfil.equals("teste")) {
            fabrica = new FabricaEmMemoria();
        } else if (perfil.equals("producao")) {
            fabrica = new FabricaRelacional(URL, USUARIO, SENHA);
        } else {
            throw new IllegalArgumentException("Perfil desconhecido: " + perfil);
        }
    }

    public FabricaDeRepositorios getFabrica() {
        return fabrica;
    }
}
