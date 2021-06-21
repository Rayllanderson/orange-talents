package com.rayllanderson.forum.controllers;

import com.rayllanderson.forum.controllers.dto.TopicoDto;
import com.rayllanderson.forum.entities.Curso;
import com.rayllanderson.forum.entities.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/topicos")
@RestController
public class TopicoController {

    @GetMapping
    public List<TopicoDto> findAll(){
        var topico = new Topico("Dúvidas", "Dúvida sobre Spring", new Curso("Spring boot", "Programação"));
        return TopicoDto.toTopicoDto(Arrays.asList(topico, topico, topico));
    }
}
