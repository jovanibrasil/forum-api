package com.jovanibrasil.forum.configurations.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jovanibrasil.forum.models.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		Date dataAtual = new Date();
		Date dataExpiracao = new Date(dataAtual.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("API treinamento compasso")
				.setSubject(usuario.getId().toString())
				.setIssuedAt(dataAtual)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		return Long.parseLong(Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody()
			.getSubject());
	}	
	
}
