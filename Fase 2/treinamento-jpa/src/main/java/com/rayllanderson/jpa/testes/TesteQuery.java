package com.rayllanderson.jpa.testes;

import com.rayllanderson.jpa.entities.Conta;
import com.rayllanderson.jpa.entities.Movimentacao;

import javax.persistence.*;
import java.util.List;

public class TesteQuery {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        Conta conta = new Conta();
        conta.setId(4L);
        String jpql = "select m from Movimentacao m where m.conta = :conta";

        TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
        query.setParameter("conta", conta);
        List<Movimentacao> resultList = query.getResultList();
        System.out.println(resultList.size());
        resultList.forEach(movimentacao -> {
            System.out.println("Descrição: " + movimentacao.getDescricao());
            System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
        });
    }
}
