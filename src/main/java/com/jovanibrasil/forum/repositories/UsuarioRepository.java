package com.jovanibrasil.forum.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovanibrasil.forum.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}
