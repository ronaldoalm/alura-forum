package br.com.alura.form;

import br.com.alura.model.Curso;
import br.com.alura.model.Topico;
import br.com.alura.repository.CursoRepository;

public class TopicoForm {

	private String titulo;
	private String mensagem;
	private String nomeCurso;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	public static Topico converter(TopicoForm topicoForm, CursoRepository repository) {
		Topico topico = new Topico();
		Curso curso = repository.findByNome(topicoForm.getNomeCurso());
		
		topico.setMensagem(topicoForm.getMensagem());
		topico.setTitulo(topicoForm.getTitulo());
		topico.setCurso(curso);
		return topico;
		
	}
	
}
