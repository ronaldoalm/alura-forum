package br.com.alura.dto;

import java.time.LocalDateTime;

import br.com.alura.model.Resposta;

public class RespostaDTO {

	private Long id;
	private String mensagem;
	private LocalDateTime dataCriação;
	private String nomeAutor;
	
	public RespostaDTO(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriação = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriação() {
		return dataCriação;
	}

	public void setDataCriação(LocalDateTime dataCriação) {
		this.dataCriação = dataCriação;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	
	
}
