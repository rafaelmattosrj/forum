package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

//SecurityConfigurations -> AutenticacaoService, tokenService, usuarioRepository e AutenticacaoViaTokenFilter.

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

//@EnableWebSecurity
//HABILITAR SEGURANÇA
//@Configuration
//CARREGAR AS CONFIGURAÇÕES DENTRO DA CLASSE
//WebSecurityConfigurerAdapter
//HERDAR METODOS DE CONFIGURAÇÃO DE SEGURANÇA
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
//(AuthenticationManagerBuilder auth)
//CONFIGURAÇÕES DE AUTENTICAÇÃO
//.passwordEncoder(new BCryptPasswordEncoder())
//ALGORITMO
//@Bean
//AuthenticationManager
//A AutenticacaoController NECESSITA DA CRIAÇÃO DO METODO AuthenticationManager. JUNTO COM O @Bean.
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()// QDO POR A API EM PRODUÇÃO DELETAR O .permitAll()
		.anyRequest().authenticated()
//		.and().formLogin();	
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
//(HttpSecurity http)
//CONFIGURAÇÕES DE AUTORIZAÇÃO
//.antMatchers(HttpMethod.GET, "/topicos/*").permitAll() 
//FILTRO DE URL, SE É PARA PERMRTIR OU BLOQUEAR.
//.antMatchers(HttpMethod.GET, "/actuator").permitAll()
//QDO POR A API EM PRODUÇÃO DELETAR O .permitAll()
//.anyRequest().authenticated()
//SO VAI DISPARAR A AQUISIÇÃO SE TIVER AUTENTICADO.
//.and().formLogin();
//GERAR UM FORMULARIO DE AUTENTICAÇÃO. DELETAR DEPOIS
//.and().csrf().disable()
//DESABILITAR POR CAUSA DE HACKERS.
//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//QDO FIZER AUTENTICAÇÃO POR TOKEN NÃO É PARA CRIAR SESSÃO.
//.and().addFilterBefore(new AutenticacaoViaTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//RODAR O FILTRO PARA RECUPERAR O TOKEN DO CABEÇALHO Authorization, VALIDA-LO E AUTENTICAR O CLIENTE.
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
		super.configure(web);
	}}

//(WebSecurity web)
//CONFIGURAÇÕES DE RECURSOS ESTATICOS (JS, CSS, IMAGENS, ETC.)

//public static void main(String[] args) {
//	System.out.println(new BCryptPasswordEncoder().encode("123456")); CRIPTOGRAFAR SENHA!
//}
