package com.rayllanderson.forum.repositories;

import com.rayllanderson.forum.entities.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByTitulo(String titulo);
    Page<Topico> findByCursoNome(String nome, Pageable pageable);
}
