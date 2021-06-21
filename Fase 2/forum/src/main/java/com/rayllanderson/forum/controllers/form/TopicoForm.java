package com.rayllanderson.forum.controllers.form;


import com.rayllanderson.forum.entities.Curso;
import com.rayllanderson.forum.entities.Topico;
import com.rayllanderson.forum.repositories.CursoRepository;

public class TopicoForm {
    private String titulo;
    private String mensagem;
    private String nomeCurso;

    public Topico toModel(CursoRepository cursoRepository){
        return new Topico(titulo, mensagem, cursoRepository.findByNome(nomeCurso));
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
