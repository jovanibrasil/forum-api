package com.jovanibrasil.forum.forms;

import com.jovanibrasil.forum.models.Curso;
import com.jovanibrasil.forum.models.Topico;
import com.jovanibrasil.forum.repositories.CursoRepository;

public class TopicoForm {
	
	private String titulo;
	private String mensagem;
	private String nomeCurso;
	
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.carregarByName(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
	
}
