package com.rayllanderson.forum.controllers;

import com.rayllanderson.forum.controllers.dto.TopicoDto;
import com.rayllanderson.forum.entities.Curso;
import com.rayllanderson.forum.entities.Topico;
import com.rayllanderson.forum.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/topicos")
@RestController
public class TopicoController {

    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @GetMapping
    public List<TopicoDto> findAll(){
        return TopicoDto.toTopicoDto(topicoRepository.findAll());
    }
}
