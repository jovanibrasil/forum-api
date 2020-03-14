package com.jovanibrasil.forum.configurations.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jovanibrasil.forum.models.Usuario;
import com.jovanibrasil.forum.repositories.UsuarioRepository;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final TokenService tokenService;
	private final UsuarioRepository usuarioRepository;
	
	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader(AUTHORIZATION_HEADER);
		
		if(token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
			if(tokenService.isValid(token)) {
				Optional<Usuario> optUsuario = usuarioRepository.findById(tokenService.getIdUsuario(token));
				if(optUsuario.isPresent()) {
					SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(optUsuario.get(), null, optUsuario.get().getAuthorities()));	
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}	
	
}
