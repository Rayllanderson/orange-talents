package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.ArrayList;
import java.util.List;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao){
        maiorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).max().orElse(0);
        menorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).min().orElse(0);
        maiores = new ArrayList<>(leilao.getLances());
        maiores.sort((o1, o2) -> Double.compare(o2.getValor(), o1.getValor()));
        maiores = maiores.subList(0, Math.min(maiores.size(), 3));
    }

    public List<Lance> get3maiores() {
        return maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }
}
