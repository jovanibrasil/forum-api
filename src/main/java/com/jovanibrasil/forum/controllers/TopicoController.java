package com.jovanibrasil.forum.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovanibrasil.forum.dto.TopicoDto;
import com.jovanibrasil.forum.repositories.TopicoRepository;

@RestController
public class TopicoController {
	
	private final TopicoRepository topicoRepository;
	
	public TopicoController(TopicoRepository topicoRepository) {
		this.topicoRepository = topicoRepository;
	}
	
	@GetMapping("/topicos")
	public List<TopicoDto> lista() {
		return TopicoDto.converter(topicoRepository.findAll());
	}
	
}
