package com.rayllanderson.exercicio2;

public class EnviadorDeEmail implements AcaoAposGerarNota{

    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("envia email da nf " + nf.getId());
    }
}
