package com.rayllanderson.jpa.testes;

import com.rayllanderson.jpa.entities.Categoria;
import com.rayllanderson.jpa.entities.Conta;
import com.rayllanderson.jpa.entities.Movimentacao;
import com.rayllanderson.jpa.entities.TipoMovimentacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TesteCategoria {

    public static void main(String[] args) {

        Categoria categoria = new Categoria("Viagem");
        Categoria categoria2 = new Categoria("Negócios");

        Conta conta = new Conta();
        conta.setId(2L);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setDescricao("Viagem à São Paulo");
        movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacao.setData(LocalDateTime.now());
        movimentacao.setValor(new BigDecimal("300.0"));
        movimentacao.getCategorias().addAll(Arrays.asList(categoria, categoria2));
        movimentacao.setConta(conta);

        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setDescricao("Viagem ao Rio de Janeiro");
        movimentacao2.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacao2.setData(LocalDateTime.now());
        movimentacao2.setValor(new BigDecimal("400.0"));
        movimentacao2.getCategorias().addAll(Arrays.asList(categoria, categoria2));
        movimentacao2.setConta(conta);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.persist(categoria);
        em.persist(categoria2);
        em.persist(movimentacao);
        em.persist(movimentacao2);

        em.getTransaction().commit();
        em.close();

    }
}
