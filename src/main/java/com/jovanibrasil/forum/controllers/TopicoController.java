package com.jovanibrasil.forum.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jovanibrasil.forum.dto.DetalhesTopicoDto;
import com.jovanibrasil.forum.dto.TopicoDto;
import com.jovanibrasil.forum.forms.AtualizacaoTopicoForm;
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
	
	@Cacheable(value = "listaDeTopicos", key = "nomeCurso")
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		if(nomeCurso == null) {
			return TopicoDto.converter(topicoRepository.findAll());	
		}else {
			return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> getTopico(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));
	}
	
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoRepository.save(topicoForm.converter(cursoRepository));
		URI uri = uriBuilder.path("/topicos/{id}") // only the resource path
				.buildAndExpand(topico.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, 
			@Valid @RequestBody AtualizacaoTopicoForm atualizacaoTopicoForm){
		Optional<Topico> optTopico = topicoRepository.findById(id);
		if(!optTopico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Topico topico = atualizacaoTopicoForm.atualizar(id, topicoRepository);
		return ResponseEntity.ok(new TopicoDto(topico));
	}

	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = topicoRepository.findById(id);
		if(!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		topicoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
