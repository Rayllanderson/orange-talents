package com.rayllanderson.exercicio3;

public class TabelaDePrecoDiferenciada implements TabelaDePreco{

    @Override
    public double descontoPara(double valor) {
        if(valor>5000) return 0.53;
        if(valor>1000) return 0.85;
        return 0;
    }
}
