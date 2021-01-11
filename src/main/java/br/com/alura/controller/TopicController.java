package br.com.alura.controller;


import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.dto.DetalhesTopicoDTO;
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
	
	
//	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso,
//								 @RequestParam(required = true) int page, 
//								 @RequestParam(required = true) int size,
//								 @RequestParam(required = true) String order){
	
	@GetMapping("/topicos")
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, Pageable pageable){
		
		if(nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(pageable);
			return TopicoDTO.converter(topicos);
		}else {
			
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso,pageable);
			return TopicoDTO.converter(topicos);
		}
		 
	}
	
	@PostMapping("/cadastrar")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topico, UriComponentsBuilder uriComponentsBuilder) {
		Topico novoTopico = topicoRepository.save(TopicoForm.converter(topico, cursoRepository));
		
		URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(novoTopico));
	}
	
	@GetMapping("/topicos/{id}")
	public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {
		// get onde se nao achar um elemento dispara um erro;
		//Topico topico = topicoRepository.getOne(id);
		
		//Pode ser que tenha um registro ou nao tenha
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
		}else {
			return ResponseEntity.notFound().build();
		}
		
 	}
	
	@Transactional
	@PutMapping("/topicos/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizacaoTopicoForm form){
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/topicos/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
