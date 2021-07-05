package br.com.caelum.leilao.tests;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteDoAvaliador {

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("PS5");
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(jose, 400.0));
        leilao.propoe(new Lance(maria, 500.0));

        Avaliador avaliador = new Avaliador();

        avaliador.avalia(leilao);

        double maiorEsperado = 500.0;
        double menorEsperado = 300.0;

        assertEquals(maiorEsperado, avaliador.getMaiorLance());
        assertEquals(menorEsperado, avaliador.getMenorLance());
    }
}
