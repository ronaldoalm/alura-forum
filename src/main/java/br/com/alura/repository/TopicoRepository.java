package br.com.alura.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.model.Topico;


public interface TopicoRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByTitulo(String titulo);
	
	Page<Topico> findByCursoNome(String nomeCurso, Pageable pageable);
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeCurso(@Param("nomeCurso") String nomeCurso);
	
}
