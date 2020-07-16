package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

//AutenticacaoViaTokenFilter -> TokenService e UsuarioRepository

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UsuarioRepository repository;
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		//System.out.println(token);
		boolean valido = tokenService.isTokenValido(token);
		//System.out.println(valido);
		if (valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}
	
//String token = recuperarToken(request);
//RECUPERAR O TOKEN NO CABEÇALHO E VALIDAR, SE TIVER OK, AUTENTICAR O USUARIO PRO SPRING.
//filterChain.doFilter(request, response);
//RODOU O FILTRO, SEGUE O FLUXO DA REQUISIÇÃO

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);		
	}

//Long idUsuario = tokenService.getIdUsuario(token);
//PEGOU O ID DO TOKEN
//Usuario usuario = repository.findById(idUsuario).get();
//RECUPEROU O OBJETO USUARIO PASSANDO O ID
//UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
//CRIEI O UsernamePasswordAuthenticationToken PASSANDO PELO USUARIO, PASSANDO PELA SENHA, SEM NECESSIDADE PQ JA TA AUTENTICADO, PASSAMOS PELOS PERFIS DELE.
//SecurityContextHolder.getContext().setAuthentication(authentication);
//CHAMOU A CLASSE DO SPRING Q FORMA A AUTENTICAÇÃO PARA ESSE REQUEST.
		
	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
		return null;
	}
	return token.substring(7, token.length());
}
}

//String token = request.getHeader("Authorization");
//CABEÇALHO QUE VAI RECUPERAR.
//RODEI, SEGUE O FLUXO..
//if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
//SE O TOKEN FOR NULO OU SE O TOKEN ESTIVER VAZIO OU SE O TOKEN COMEÇAR COM BEARER ESPAÇO.
//return token.substring(7, token.length());
//"Bearer " PEGAR APÓS ISSO.
