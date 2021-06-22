package com.rayllanderson.forum.controllers.dto;

import com.rayllanderson.forum.entities.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static TopicoDto toTopicoDto (Topico topico){
        return new TopicoDto(topico);
    }

    public static Page<TopicoDto> toTopicoDto(Page<Topico> paginaDetopicos) {
        return paginaDetopicos.map(TopicoDto::toTopicoDto);
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
