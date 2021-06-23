package com.rayllanderson.exercicio5;

public class ContaDeEstudante extends ContaComum{

    private int milhas;

    public void deposita(double valor) {
        super.deposita(valor);
        this.milhas += (int)valor;
    }

    public int getMilhas() {
        return milhas;
    }

    public void rende() {
        throw new RuntimeException("Não pode render");
    }
}
