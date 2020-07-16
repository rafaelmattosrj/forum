package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Usuario.class)
        		.globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                        .name("Authorization")
                        .description("Header para Token JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()));
}
}

//@Bean
//PARA SABER Q ESTÁ EXPORTANDO O BEAN DO TIPO DOCKET
//return new Docket(DocumentationType.SWAGGER_2)
//TIPO DE DOCUMENTAÇÃO
//.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
//APARTIR DE QUAL PACOTE ELE VAI COMEÇAR A LER AS CLASSES DO PROJETO.
//.paths(PathSelectors.ant("/**"))
//QUAIS OS ENDPOINTS É PARA ELE FAZER ANALISE
//.ignoredParameterTypes(Usuario.class)
//IGNORE TODAS AS URLS QUE TRABALHEM COM A CLASSE USUARIO,
//PQ SE NAO NA TELA NO SWAGGER PODE APARECER A SENHA DO USUARIO.

