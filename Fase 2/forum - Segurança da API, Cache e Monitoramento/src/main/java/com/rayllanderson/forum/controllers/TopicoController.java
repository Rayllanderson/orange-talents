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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    @Cacheable(value = "listaDeTopicos")
    public ResponseEntity<Page<TopicoDto>> findAll(@RequestParam(required = false) String nomeCurso, Pageable pageable) {
        if (nomeCurso == null){
            return ResponseEntity.ok(TopicoDto.toTopicoDto(topicoRepository.findAll(pageable)));
        } else {
            return ResponseEntity.ok(TopicoDto.toTopicoDto(topicoRepository.findByCursoNome(nomeCurso, pageable)));
        }
    }

    @Transactional
    @PostMapping
    @CacheEvict(value = "listaDeTopicos", allEntries = true) // limpar todos os registros
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.toModel(cursoRepository);
        topico = topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(TopicoDto.toTopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetailsDto> findById(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("N??o encontrado"));
        return ResponseEntity.ok(new TopicoDetailsDto(topico));
    }

    @Transactional
    @PutMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    ResponseEntity<TopicoDto> atualizar (@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("N??o encontrado"));
        Topico topicoAtualizado = form.atualizar(id, topico);
        return ResponseEntity.ok(new TopicoDto(topicoRepository.save(topicoAtualizado)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        topicoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("N??o encontrado"));
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
