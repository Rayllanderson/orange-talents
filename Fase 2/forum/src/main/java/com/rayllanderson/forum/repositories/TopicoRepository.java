package com.rayllanderson.forum.repositories;

import com.rayllanderson.forum.entities.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
