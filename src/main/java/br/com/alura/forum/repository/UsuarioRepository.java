package br.com.alura.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Usuario;

//UsuarioRepository -> Usuario(JpaRepository)

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}

//EXTENDS -> É HERDAR, GANHANDO TUDO DA INTERFACE OU DA CLASSE QUE ESTÁ HERDANDO.
//A JPAREPOSITORY JÁ TEM UM MONTE DE MÉTODOS. CARREGAR TODOS, SALVAR, EXCLUIR, ALTERAR.
//ENTÃO A INTERFACE USUARIOREPOSITORY ESTÁ HERDANDO DA INTERFACE DO SPRING DATA JPA.
