package com.jovanibrasil.forum.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovanibrasil.forum.model.Curso;
import com.jovanibrasil.forum.model.Topico;

@RestController
public class TopicoController {
	
	@GetMapping("/topicos")
	public List<Topico> lista() {
		Topico topico = new Topico("Dúvida", "Dú vida com Spring", new Curso("Spring", "Programação"));
		return Arrays.asList(topico, topico, topico);
	}
	
}
