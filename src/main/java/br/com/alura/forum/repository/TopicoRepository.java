package br.com.alura.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

//TopicoRepository -> Topico(JpaRepository)

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}

//findByCursoNome
//CURSO É A ENTIDADE, COMPLETADA COM O NOME.
//Exemplo: @Query
//("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
//List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") Spring nomeCurso);
//QUANDO CRIAR FUGINDO DO PADRÃO DO SPRING DATA. USAR A SINTAXE DO JPQL.
//FOI TROCADO A LIST POR PAGE E ACRESCENTOU O “Pageable paginacao"
