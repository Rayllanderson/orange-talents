package br.com.caelum.leilao.tests;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Usuario joao = new Usuario("João");

        Leilao leilao = new Leilao("PS5");
        leilao.propoe(new Lance(joao, 1000.0));

        Avaliador avaliador = new Avaliador();

        avaliador.avalia(leilao);

        double maiorEsperado = 1000.0;
        double menorEsperado = 1000.0;

        assertEquals(maiorEsperado, avaliador.getMaiorLance());
        assertEquals(menorEsperado, avaliador.getMenorLance());
    }

    @Test
    public void deveEncontrarOs3MaioresLances() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("PS5");
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(jose, 400.0));
        leilao.propoe(new Lance(maria, 500.0));
        leilao.propoe(new Lance(maria, 200.0));


        Avaliador avaliador = new Avaliador();

        avaliador.avalia(leilao);
        List<Lance> maiores = avaliador.get3maiores();

        assertEquals(3, maiores.size());
        assertEquals(500, maiores.get(0).getValor());
        assertEquals(400, maiores.get(1).getValor());
        assertEquals(300, maiores.get(2).getValor());
    }
}
