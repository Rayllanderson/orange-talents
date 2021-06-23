package com.rayllanderson.exercicio5;

public class ManipuladorDeSaldo {

    private double saldo;

    public void deposita(double valor) {
        this.saldo += valor;
    }

    public void saca(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void rende() {
        this.saldo += this.saldo * 0.01;
    }

    public double getSaldo() {
        return saldo;
    }
}
