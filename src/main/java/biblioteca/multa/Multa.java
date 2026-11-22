package biblioteca.multa;

import java.util.UUID;

public class Multa {

    private String id;
    private String idLivro;
    private String idEstudante;
    private int diasDeAtraso;
    private double valor;
    private String politicaAplicada;
    private String idBoleto;
    private boolean paga = false;

    public Multa(String idLivro, String idEstudante, int diasDeAtraso, double valor,
            String politicaAplicada) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.idLivro = idLivro;
        this.idEstudante = idEstudante;
        this.diasDeAtraso = diasDeAtraso;
        this.valor = valor;
        this.politicaAplicada = politicaAplicada;
    }

    public String getId() {
        return id;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public String getIdEstudante() {
        return idEstudante;
    }

    public int getDiasDeAtraso() {
        return diasDeAtraso;
    }

    public double getValor() {
        return valor;
    }

    public String getPoliticaAplicada() {
        return politicaAplicada;
    }

    public String getIdBoleto() {
        return idBoleto;
    }

    public void registrarBoleto(String idBoleto) {
        this.idBoleto = idBoleto;
    }

    public boolean isPaga() {
        return paga;
    }

    @Override
    public String toString() {
        return "Multa: R$ " + String.format("%.2f", valor) + " por " + diasDeAtraso
                + " dia(s), politica " + politicaAplicada + ", boleto " + idBoleto;
    }

    public boolean quitar() {
        if (paga) {
            return false;
        }
        paga = true;
        return true;
    }
}
