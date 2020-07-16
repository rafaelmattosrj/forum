package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

//TopicosController -> No @Autowired - TopicoRepository e CursoRepository
//-> Listar no @GetMapping – TopicoDTO, Topico e TopicoRepository
//(value = "/topicos", method = RequestMethod.GET)
//-> Cadastrar no @PostMapping - TopicoDTO, TopicoFrom, Topico, TopicoRepository e CursoRepository
//(value = "/topicos", method = RequestMethod.POST)
//-> Detalhar no @GetMapping("/{id}") – DetalhesDoTopicoDto, Topico e TopicoRepository.
//-> Atualizar no @PutMapping("/{id}") – TopicoDto, AtualizacaoTopicoFrom, Topico e TopicoRepository.
//-> Deletar no @DeleteMapping("/{id}") – Topico e TopicoRepository.

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	//@RestController
	//INDICA AO SPRING QUE O RETORNO DO MÉTODO DEVE SER DEVOLVIDO COM RESPOSTA. RETORNA PRO NAVEGADOR.
	//@RequestMapping("/topicos")
	//MAPEAR (VALUE = "/TOPICOS")
	//@REQUESTMAPPING ABAIXO DO @RESTCONTROLLER PARA PEGAR EM TODOS.
	//@Autowired
	//private TopicoRepository topicoRepository;
	//@Autowired
	//private CursoRepository cursoRepository;
	//INJETAR OS REPOSITORIOS NO CONTROLLER.

	@GetMapping
	@Cacheable (value = "listaDeTopicos")
	public Page<TopicoDto> lista(@RequestParam (required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction =  Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
			
		} else {
			
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}

//@GetMapping
//FAZER LEITURA. MAPEAR (VALUE = "/TOPICOS", METHOD = REQUESTMETHOD.GET)
//@Cacheable
//IMPORTAR O org.spring...
//@RequestParam (required = false)
//PARÂMETRO DE REQUEST. O SPRING CONSIDERA QUE O PARÂMETRO É OBRIGATÓRIO.
//(required = false) - NOMECURSO NÃO É OBRIGATORIO.
//Pageable paginacao = PageRequest.of(pagina, qtd);
//IMPORTAR O ...data.domain.
//ASC - CRESCEMTE / DESC - DECRESCENTE
//List<Topico> topicos = topicoRepository.findAll();
//LISTA DE TOPICOS. FINDALL FAZ UMA CONSULTA, CARREGANDO TODOS OS REGISTROS DO
//BANCO DE DADOS.
//} else { - FILTRAR.
//"FOI TROCADO A LIST POR PAGE"
//Page - DEVOLVE REGISTROS, INFORMAÇÕES NO JSON DE RESPOSTA, COMO Nº TOTAL DE REGISTROS E PÁGS.
//@PageableDefault(sort = "id", direction =  Direction.DESC)
//PADRONIZAR O DEFAULT PARA PAGINAÇÃO http://localhost:8080/topicos?page=0&size=10 
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {

		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

//@PostMapping
//@REQUESTMAPPING(VALUE = "/TOPICOS", METHOD = REQUESTMETHOD.POST)
//@Transactional
//COLOCAR SEMPRE NO POST, PUT E DELETE.
//NÃO MANDA OS PARAMETROS VIA URL, OS PARAMETROS VÊM NO CORPO DA REQUISIÇÃO.
//@CacheEvict(value = "listaDeTopicos", allEntries = true)
//COLOCAR SEMPRE NO POST, PUT E DELETE.
//LIMPE E INVALIDE DETERMINADO CACHE / LIMPAR TODOS OS REGISTROS PARA ATULIZAR TUDO E DEIXAR O CACHE ZERADO DE NOVO. REATUALIZAR.
//public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
//RESPONSEENTITY<TOPICODTO> -> NÃO SE DEVOLVE A ENTIDADE (CLASSE DE DOMINIO).
//@REQUESTBODY -> INDICAR AO SPRING QUE OS PARAMETROS ENVIADOS NO CORPO
//DA REQUISIÇÃO DEVEM SER ATRIBUÍDOS AO PARÂMETRO DO MÉTODO, E NÃO DO PARÂMETRO DA URL.
//@VALID -> RODAR AS VALIDAÇÕES DO BEAN VALIDATION.
//URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//{ID} FAZ DINAMICO. BUILDANDEXPAND PARA SUBSTITUIT O {ID}, VAI REQUISITAR O ID DO TOPICO CRIADO NO BANCO DE DADOS.
//TOURI CONVERTE E TRANSFORMA A URL CERTA.
//return ResponseEntity.created(uri).body(new TopicoDto(topico));
//CRIA O CORPO DA RESPOSTA. RETORNO 201, CABEÇALHO LOCATION E CORPO DA RESPOSTA SENDO UMA REPRESENTAÇÃO DO RECURSO QUE ACABOU DE SER CRIADO.

	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {

		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}

//@GetMapping("/{id}") - {ID} É DINAMICO.
//@PathVariable Long id - ASSOCIAR PELO @PATHVARIABLE COM "{ID}"
//Optional<Topico> topico = topicoRepository.findById(id);
//ID E ELE TE DEVOLVE UM METODO TOPICO QUE É A ENTIDADE.
//if (topico.isPresent()) – SE ESTÁ PRESENTE.
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}

//@PutMapping("/{id}") – SOBRESCREVE POR INTEIRO.
//@Transactional
//GERALMENTE QDO SALVAR, ALTERAR E EXCLUIR. COMITA A TRANSAÇÃO NO FINAL DO METODO.
//public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
//PARA CADA RETORNO DE STATUS CODE DO HTTP, UTILIZAMOS UMA FUNÇÃO DO RESPONSEENTITY.
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
//@DeleteMapping("/{id}") – DELETAR.
}
