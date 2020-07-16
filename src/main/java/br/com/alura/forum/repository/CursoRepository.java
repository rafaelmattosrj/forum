package br.com.alura.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Curso;

//CursoRepository -> Curso(JpaRepository)

public interface CursoRepository extends JpaRepository<Curso, Long> {
	Curso findByNome(String nomeCurso);
}

//EXTENDS -> É HERDAR, GANHANDO TUDO DA INTERFACE OU DA CLASSE QUE ESTÁ HERDANDO.
//A JPAREPOSITORY JÁ TEM UM MONTE DE MÉTODOS. CARREGAR TODOS, SALVAR, EXCLUIR, ALTERAR.
//ENTÃO A INTERFACE CURSOREPOSITORY ESTÁ HERDANDO DA INTERFACE DO SPRING DATA JPA.

