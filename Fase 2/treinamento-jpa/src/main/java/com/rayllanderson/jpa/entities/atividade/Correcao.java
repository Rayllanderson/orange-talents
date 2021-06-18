package com.rayllanderson.jpa.entities.atividade;

import javax.persistence.*;

@Entity
public class Correcao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer nota;
    private String descricao;

    @JoinColumn(unique = true)
    @OneToOne
    private Resposta resposta;

    public Correcao(){};

    public Correcao(Integer nota, String descricao, Resposta resposta) {
        this.nota = nota;
        this.descricao = descricao;
        this.resposta = resposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }

    @Override
    public String toString() {
        return "Correcao{" +
                "id=" + id +
                ", nota=" + nota +
                ", descricao='" + descricao + '\'' +
                ", resposta=" + resposta +
                '}';
    }
}
