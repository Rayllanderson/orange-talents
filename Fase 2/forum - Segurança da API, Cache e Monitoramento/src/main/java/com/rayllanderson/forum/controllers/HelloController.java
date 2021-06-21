package com.rayllanderson.forum.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String hello(@RequestParam(required = false, defaultValue = "Convidado") String name){
        return "Olá, " + name +"!";
    }
}
