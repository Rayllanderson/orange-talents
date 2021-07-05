package br.com.caelum.leilao.tests;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoTest {

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 15")
                .lance(new Usuario("Steve Jobs"), 2000)
                .lance(new Usuario("Steve Jobs"), 3000)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000.0));
        leilao.propoe(new Lance(billGates, 3000.0));
        leilao.propoe(new Lance(steveJobs, 4000.0));
        leilao.propoe(new Lance(billGates, 5000.0));
        leilao.propoe(new Lance(steveJobs, 6000.0));
        leilao.propoe(new Lance(billGates, 7000.0));
        leilao.propoe(new Lance(steveJobs, 8000.0));
        leilao.propoe(new Lance(billGates, 9000.0));
        leilao.propoe(new Lance(steveJobs, 10000.0));
        leilao.propoe(new Lance(billGates, 11000.0));

        // deve ser ignorado
        leilao.propoe(new Lance(steveJobs, 12000.0));

        assertEquals(10, leilao.getLances().size());
        assertEquals(11000.0, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
    }
}