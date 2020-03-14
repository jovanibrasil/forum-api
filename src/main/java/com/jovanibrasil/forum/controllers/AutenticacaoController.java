package com.jovanibrasil.forum.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovanibrasil.forum.configurations.security.TokenService;
import com.jovanibrasil.forum.dto.TokenDto;
import com.jovanibrasil.forum.forms.LoginForm;
import com.jovanibrasil.forum.models.Usuario;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	private final AuthenticationManager authManager;
	private final TokenService tokenService;
	
	public AutenticacaoController(AuthenticationManager authManager, TokenService tokenService) {
		this.tokenService = tokenService;
		this.authManager = authManager;
	}

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@Valid @RequestBody LoginForm loginForm){
		
		try {
			UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getSenha());
			Authentication auth = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken((Usuario)auth.getPrincipal());
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
