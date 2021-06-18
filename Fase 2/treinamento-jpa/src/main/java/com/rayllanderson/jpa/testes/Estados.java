package com.rayllanderson.jpa.testes;

import com.rayllanderson.jpa.entities.Conta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Estados {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        //estado Transient
        //estado transient = Objeto sem qualquer vínculo com a JPA
        //Sua característica é uma conta que existe na memória,
        // possui informações e não tem Id nenhum, mas pode se tornar Managed futuramente.
        Conta conta = new Conta();
        conta.setAgencia(656500);
        conta.setNumero(65656);
        conta.setTitular("Whatever there");

        em.getTransaction().begin();

        // Transient -> Maneged
        em.persist(conta);

        // Maneged -> Removed
        em.remove(conta);

        em.getTransaction().commit();

    }

}
