package com.jovanibrasil.forum.configurations.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jovanibrasil.forum.models.Usuario;
import com.jovanibrasil.forum.repositories.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public AutenticacaoService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optUser = usuarioRepository.findByEmail(username);
		
		if(optUser.isPresent()) {
			return optUser.get();
		}
		
		throw new UsernameNotFoundException("User not found");
	}	
	
}
