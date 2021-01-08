package br.com.alura.controller;


import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.dto.TopicoDTO;
import br.com.alura.form.TopicoForm;
import br.com.alura.model.Topico;
import br.com.alura.repository.CursoRepository;
import br.com.alura.repository.TopicoRepository;

@RestController
public class TopicController {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping("/topics")
	public List<TopicoDTO> lista(String nomeCurso){
		if(nomeCurso == null) {
			return TopicoDTO.converter(topicoRepository.findAll());
		}else {
			return TopicoDTO.converter(topicoRepository.findByCursoNome(nomeCurso));
		}
		 
	}
	
	@PostMapping("/create")
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoForm topico, UriComponentsBuilder uriComponentsBuilder) {
		Topico novoTopico = topicoRepository.save(TopicoForm.converter(topico, cursoRepository));
		
		URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(novoTopico));
	}
	
}
