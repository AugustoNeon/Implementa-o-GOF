package biblioteca.cadastro;

public abstract class InclusaoDeCadastro<T> {

    public final T incluir() {
        T existente = buscarExistente();
        if (existente != null) {
            apresentar(existente);
            return existente;
        }
        validar();
        verificarPreRequisitos();
        T novo = criar();
        armazenar(novo);
        apresentar(novo);
        return novo;
    }

    protected abstract T buscarExistente();

    protected abstract void validar();

    protected abstract void verificarPreRequisitos();

    protected abstract T criar();

    protected abstract void armazenar(T cadastro);

    protected void apresentar(T cadastro) {
    }

    protected boolean estaVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
