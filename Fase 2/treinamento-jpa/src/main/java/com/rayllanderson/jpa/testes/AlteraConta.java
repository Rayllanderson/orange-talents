package com.rayllanderson.jpa.testes;

import com.rayllanderson.jpa.entities.Conta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AlteraConta {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        Conta contaSemSaldo = em.find(Conta.class, 2L);

        em.getTransaction().begin();

        // a conta está em estado managed, portanto, qualquer alteração aqui, vai automaticamente mudar no banco
        contaSemSaldo.setTitular("Ray");
        contaSemSaldo.setSaldo(700.0);

        em.getTransaction().commit();
    }
}
