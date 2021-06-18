package com.rayllanderson.jpa.entities.atividade;

import javax.persistence.*;

@Entity
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String resposta;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Avaliacao avaliacao;

    public Resposta() {
    }

    public Resposta(String resposta, Aluno aluno, Avaliacao avaliacao) {
        this.resposta = resposta;
        this.aluno = aluno;
        this.avaliacao = avaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "id=" + id +
                ", resposta='" + resposta + '\'' +
                ", aluno=" + aluno +
                ", avaliacao=" + avaliacao +
                '}';
    }
}
