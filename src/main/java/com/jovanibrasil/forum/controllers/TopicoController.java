package com.jovanibrasil.forum.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovanibrasil.forum.dto.TopicoDto;
import com.jovanibrasil.forum.model.Curso;
import com.jovanibrasil.forum.model.Topico;

@RestController
public class TopicoController {
	
	@GetMapping("/topicos")
	public List<TopicoDto> lista() {
		Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		return TopicoDto
				.converter(Arrays.asList(topico, topico, topico));
	}
	
}
