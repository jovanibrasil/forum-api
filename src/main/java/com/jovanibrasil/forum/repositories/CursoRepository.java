package com.jovanibrasil.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovanibrasil.forum.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	Curso carregarByName(String nomeCurso);
}
