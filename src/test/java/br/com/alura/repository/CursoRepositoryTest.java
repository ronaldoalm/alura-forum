package br.com.alura.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.model.Curso;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager testeEntityManager;
	
	@Test
	public void deveCarregarUmCursoPeloNome() {
		
		//Persistindo Curso no Banco
		Curso novoCurso = new Curso();
		novoCurso.setNome("HTML 5");
		novoCurso.setCategoria("Programacao");
		testeEntityManager.persist(novoCurso);
		//Verificacao Curso
		String nomeCurso = "HTML 5";
		Curso  curso = repository.findByNome(nomeCurso);
		//Validação
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoDeveCarregarUmCursoSeNomeNaoEstaCadastrado() {
		String nomeCurso = "JPA";
		Curso  curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}

}
