package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication
//O RUN!
//@EnableSpringDataWebSupport 
//HABILITA O SUPORTE PARA O SPRING PEGAR A REQUISIÇÃO COM OS PARAMETROS DA URL, OS CAMPOS, 
//AS INFORMAÇÕES DE PAGINACAO E ORDENAÇÃO E PASSAR ISSO PARA O SPRING DATA.
//@EnableCaching
//HABILITAR O USO DE CACHE NA APLICAÇÃO.

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);

	}

}