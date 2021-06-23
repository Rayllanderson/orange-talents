package com.rayllanderson.exercicio5;

public class ContaComum {

    private ManipuladorDeSaldo manipuladorDeSaldo;

    public ContaComum() {
        manipuladorDeSaldo = new ManipuladorDeSaldo();
    }

    public void deposita(double valor) {
        manipuladorDeSaldo.deposita(valor);
    }

    public void saca(double valor) {
        double taxa = 6.0;
        manipuladorDeSaldo.saca(valor - taxa);
    }

    public void rende() {
        manipuladorDeSaldo.rende();
    }

    public double getSaldo() {
        return manipuladorDeSaldo.getSaldo();
    }
}
