package com.rayllanderson.exercicio5;

public class ContaDeEstudante {
    ManipuladorDeSaldo manipuladorDeSaldo;
    private int milhas;

    ContaDeEstudante(){
        manipuladorDeSaldo = new ManipuladorDeSaldo();
    }

    public void deposita(double valor) {
        this.manipuladorDeSaldo.deposita(valor);
        this.milhas += (int)valor;
    }

    public void saca(double valor) {
        manipuladorDeSaldo.saca(valor);
    }

    public double getSaldo() {
        return manipuladorDeSaldo.getSaldo();
    }

    public int getMilhas() {
        return milhas;
    }
}
