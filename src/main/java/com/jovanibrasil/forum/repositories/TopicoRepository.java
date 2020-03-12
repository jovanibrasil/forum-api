package com.jovanibrasil.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jovanibrasil.forum.models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {}
