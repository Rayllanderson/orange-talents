package com.rayllanderson.jpa.testes.atividade;

import com.rayllanderson.jpa.entities.atividade.Aluno;
import com.rayllanderson.jpa.entities.atividade.Avaliacao;
import com.rayllanderson.jpa.entities.atividade.Correcao;
import com.rayllanderson.jpa.entities.atividade.Resposta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");

        /* CRIAÇÕES */

        EntityManager em = emf.createEntityManager();

        //criando os alunos...
        Aluno ray = new Aluno("Ray", "ray@zup.com.br", 23);
        Aluno maria = new Aluno("Maria", "maria@zup.com.br", 22);

        //criando as avaliações...
        String desc = "Somos muitos alunos e alunas aqui no Bootcamp. Em função de todas as avaliações respondidas," +
                " muitos dados são gerados. E agora temos alguns desafios para você:";
        Avaliacao avaliacao = new Avaliacao("Java e JPA: Persista seus objetos com a JPA2 e Hibernate", desc);
        Avaliacao avaliacao2 = new Avaliacao("Avançando nas Consultas com MySQL", desc);

        //criando as respostas...
        Resposta respostaDoRay = new Resposta("acho que é por aqui e tals kkkk", ray, avaliacao);
        Resposta respostaDaMaria = new Resposta("acho que é por aqui e tals kkkk", maria, avaliacao);
        Resposta respostaDaMaria2 = new Resposta("pois é, tem que fazer isso e aquilo", maria, avaliacao2);

        //criando as correções...
        Correcao correcaoDoRay = new Correcao(5, "errei aqui e ali...", respostaDoRay);
        Correcao correcaoDaMaria = new Correcao(10, "fui bem", respostaDaMaria);
        Correcao correcaoDaMaria2 = new Correcao(8, "fui mais ou menos", respostaDaMaria2);

        em.getTransaction().begin();

        //salvando os alunos...
        em.persist(ray);
        em.persist(maria);

        //salvando as avaliações...
        em.persist(avaliacao);
        em.persist(avaliacao2);

        //salvando as respostas...
        em.persist(respostaDoRay);
        em.persist(respostaDaMaria);
        em.persist(respostaDaMaria2);

        //salvando as correções...
        em.persist(correcaoDoRay);
        em.persist(correcaoDaMaria);
        em.persist(correcaoDaMaria2);

        em.getTransaction().commit();
        em.close();

        /* BUSCAS */
        EntityManager em2 = emf.createEntityManager();

        /* Carregar uma auto correção e tenha que descobrir o nome do(a) aluno(a) que fez */
        Long correcaoId = correcaoDaMaria.getId();
        System.out.println("------------------------------------------");
        String jpql = "from Correcao c where c.id = :pId";
        TypedQuery<Correcao> query = em2.createQuery(jpql, Correcao.class).setParameter("pId", correcaoId);
        Correcao correcao = query.getSingleResult();
        System.out.println(correcao);
        System.out.println(correcao.getResposta().getAluno().getNome());

        /* Carregar as respostas de um(a) aluno(a) a partir do objeto da classe Aluno */
        System.out.println("------------------------------------------");
        jpql = "from Resposta r where r.aluno = :pAluno";
        TypedQuery<Resposta> query2 = em2.createQuery(jpql, Resposta.class).setParameter("pAluno", maria);
        List<Resposta> respostasDaMaria = query2.getResultList();
        System.out.println(respostasDaMaria);
    }
}
