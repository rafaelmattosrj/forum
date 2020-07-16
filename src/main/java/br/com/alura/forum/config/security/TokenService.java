package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//TokenService -> Usuario e application.properties

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String gerarToken (Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();	
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}

//@Value("${forum.jwt.expiration}")
//private String expiration;
//CITAR NO APPLICATION.PROPERTIES
//@Value("${forum.jwt.secret}")
//private String secret;
//CITAR NO APPLICATION.PROPERTIES

//Date hoje = new Date(); 
//JAVA.UTIL

//.setIssuer("API do Fórum da Alura")
//QUEM GEROU O TOKEN.
//.setSubject(logado.getId().toString())
//USUARIO QUE O TOKEN PERTECE.
//.setIssuedAt(hoje)
//DATA QUE O TOKEN FOI CONCEDIDO.
//.setExpiration(dataExpiracao)
//DATA DE EXPIRAÇÃO.
//.signWith(SignatureAlgorithm.HS256, secret)
//TOKEN CRIPTOGRAFADO, ALGORITMO E A SENHA DA APLICAÇÃO.
//.compact();
//COMPACTAR PARA TRANSFORMAR EM UM SPRING.	

//Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
//VAI DISCRIPTOGRAFAR E VER SE TA OK. parseClaimsJws RECUPERA O TOKEN.
