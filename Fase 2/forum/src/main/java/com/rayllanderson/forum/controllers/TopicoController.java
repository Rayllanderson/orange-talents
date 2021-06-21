package com.rayllanderson.forum.controllers;

import com.rayllanderson.forum.entities.Curso;
import com.rayllanderson.forum.entities.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/topicos")
@RestController
public class TopicoController {

    @GetMapping
    public List<Topico> findAll(){
        var topico = new Topico("Dúvida", "Duvida sobre Spring", new Curso("Spring boot", "Programação"));
        return Arrays.asList(topico, topico, topico);
    }
}
