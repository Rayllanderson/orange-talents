package com.rayllanderson.jpa.testes;

import com.rayllanderson.jpa.entities.Conta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriarConta {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        Conta conta = new Conta();
        conta.setAgencia(123);
        conta.setNumero(15423);

        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
    }
}
