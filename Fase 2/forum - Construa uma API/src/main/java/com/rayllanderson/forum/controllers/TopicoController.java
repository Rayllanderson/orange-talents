package com.rayllanderson.forum.controllers;

import com.rayllanderson.forum.controllers.dto.TopicoDetailsDto;
import com.rayllanderson.forum.controllers.dto.TopicoDto;
import com.rayllanderson.forum.controllers.form.AtualizacaoTopicoForm;
import com.rayllanderson.forum.controllers.form.TopicoForm;
import com.rayllanderson.forum.entities.Topico;
import com.rayllanderson.forum.exceptions.NaoEncontradoException;
import com.rayllanderson.forum.repositories.CursoRepository;
import com.rayllanderson.forum.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/topicos")
@RestController
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public ResponseEntity<List<TopicoDto>> findAll(String nomeCurso) {
        if (nomeCurso == null){
            return ResponseEntity.ok(TopicoDto.toTopicoDto(topicoRepository.findAll()));
        } else {
            return ResponseEntity.ok(TopicoDto.toTopicoDto(topicoRepository.findByCursoNome(nomeCurso)));
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.toModel(cursoRepository);
        topico = topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(TopicoDto.toTopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetailsDto> findById(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não encontrado"));
        return ResponseEntity.ok(new TopicoDetailsDto(topico));
    }

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<TopicoDto> atualizar (@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não encontrado"));
        Topico topicoAtualizado = form.atualizar(id, topico);
        return ResponseEntity.ok(new TopicoDto(topicoRepository.save(topicoAtualizado)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não encontrado"));
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
