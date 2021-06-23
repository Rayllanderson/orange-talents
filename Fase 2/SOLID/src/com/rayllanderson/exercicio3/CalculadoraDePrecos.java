package com.rayllanderson.exercicio3;

public class CalculadoraDePrecos {

    private final TabelaDePreco tabelaDePreco;
    private final ServicoDeEntrega servicoDeEntrega;

    CalculadoraDePrecos(TabelaDePreco tabelaDePreco, ServicoDeEntrega servicoDeEntrega){
        this.tabelaDePreco = tabelaDePreco;
        this.servicoDeEntrega = servicoDeEntrega;
    }

    public double calcula(Compra produto) {

        double desconto = tabelaDePreco.descontoPara(produto.getValor());
        double frete = servicoDeEntrega.para(produto.getCidade());

        return produto.getValor() * (1-desconto) + frete;
    }
}
