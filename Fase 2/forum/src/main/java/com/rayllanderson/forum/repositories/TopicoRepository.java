package com.rayllanderson.forum.repositories;

import com.rayllanderson.forum.entities.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByTitulo(String titulo);
    List<Topico> findByCursoNome(String nome);
}
