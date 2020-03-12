package com.jovanibrasil.forum.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jovanibrasil.forum.models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	// to ways to retrieve the same result
	List<Topico> findByCurso_Nome(String nome);
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
}
