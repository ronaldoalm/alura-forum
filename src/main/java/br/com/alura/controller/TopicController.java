package br.com.alura.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.dto.TopicoDTO;
import br.com.alura.repository.TopicoRepository;

@RestController
public class TopicController {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@RequestMapping("/topics")
	public List<TopicoDTO> lista(String nomeCurso){
		if(nomeCurso == null) {
			return TopicoDTO.converter(topicoRepository.findAll());
		}else {
			return TopicoDTO.converter(topicoRepository.findByCursoNome(nomeCurso));
		}
		 
	}
}
