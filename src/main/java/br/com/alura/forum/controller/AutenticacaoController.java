package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;

//AutenticacaoController -> TokenService, AuthenticationManager, TokenDto, LoginForm e gerarToken.

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		//System.out.println(form.getEmail()); TESTE
		//System.out.println(form.getSenha()); TESTE
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			//System.out.println(token); TESTE
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}

//@Autowired
//private AuthenticationManager authManager;
//AUTENTICAR NO SPRING SECURITY. 
//A AutenticacaoController NECESSITA DA CRIAÇÃO DO METODO AuthenticationManager NO SecurityConfigurations.

//@PostMapping
//RECEBER PARAMETROS!
//public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
//CORPO DA REQUISIÇÃO TEM Q RECEBER OS PARAMETROS.
	
//try {
//TRATAMENTO DE ERRO.	
//Authentication authentication = authManager.authenticate(dadosLogin);
		//SPRING VAI CHAMAR A CLASSE AutenticationService, QUE REQUISTA UsuarioRepository
//PARA CONSULTAR OS DADOS NOS BANCOS DE DADOS. SE DER CERTO, VAI PRO RETURN.
//return ResponseEntity.ok(new TokenDto(token, "Bearer"));
//Bearer É UM DOS MECANISMOS DE AUTENTICAÇÃO UTILIZADOS NO PROTOCOLO HTTP, TAL COMO O Basic E Digest.
