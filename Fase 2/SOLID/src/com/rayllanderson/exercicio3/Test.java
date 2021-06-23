package com.rayllanderson.exercicio3;

public class Test {

    public static void main(String[] args) {
        TabelaDePreco tabela = new TabelaDePrecoDiferenciada();
        ServicoDeEntrega entrega = new Frete();
        new CalculadoraDePrecos(tabela, entrega);
    }
}
