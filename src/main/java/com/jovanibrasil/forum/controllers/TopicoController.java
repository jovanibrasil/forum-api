package com.jovanibrasil.forum.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jovanibrasil.forum.dto.TopicoDto;
import com.jovanibrasil.forum.forms.TopicoForm;
import com.jovanibrasil.forum.models.Topico;
import com.jovanibrasil.forum.repositories.CursoRepository;
import com.jovanibrasil.forum.repositories.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
	
	private final TopicoRepository topicoRepository;
	private final CursoRepository cursoRepository;
	
	public TopicoController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
		this.topicoRepository = topicoRepository;
		this.cursoRepository = cursoRepository;
	}
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		if(nomeCurso == null) {
			return TopicoDto.converter(topicoRepository.findAll());	
		}else {
			return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));
			// second option
			//return TopicoDto.converter(topicoRepository.carregarPorNomeDoCurso(nomeCurso));
		}
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoRepository.save(topicoForm.converter(cursoRepository));
		URI uri = uriBuilder.path("/topicos/{id}") // only the resource path
				.buildAndExpand(topico.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
}
