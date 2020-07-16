package br.com.alura.forum.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//Topico -> TER @Entity. GERAR GETS E SETS, GERAR CONSTRUTOR DEFAULT. PODE CRIAR OUTROS CONSTRUTORES. 
//Long ID, String TITULO, String MENSAGEM, LocalDateTime DATACRIACAO, StatusTopico STATUS, 
//Usuario AUTOR, Curso CURSO, List<Resposta> RESPOSTAS.

@Entity
public class Topico {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@ManyToOne
	private Usuario autor;
	@ManyToOne
	private Curso curso;
	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas = new ArrayList<>();
	
//@Entity
//public class Topico {
//ENTIDADE TEM Q TER A ANOTAÇÃO.
//@Id @GeneratedValue
//(strategy = GenerationType.IDENTITY)
//BANCO DE DADOS VAI GERAR.
//LocalDateTime.now();
//PEGA A DATA ATUAL.
//@Enumerated(EnumType.STRING)
//GRAVAR O NOME DA CONSTANTE, EM VEZ DA ORDEM. NORMALMENTE ELE GRAVA POR ORDEM.
//StatusTopico.NAO_RESPONDIDO;
//STATUS ATUAL SERÁ NÃO RESPONDIDO.
//@ManyToOne
//TOPICO PRO USUARIO, PRO AUTOR.
//@OneToMany(mappedBy = "topico")
//UM TOPICO PODE TER VARIAS RESPOSTAS. 
//MAPPEDBY PARA ELE NÃO ACHAR QUE É UM NOVO MAPEAMENTO.
//A CLASSE RESPOSTA ESTARÁ MAPEADO O RELACIONAMENTO COM O TOPICO.
//List<Resposta> respostas = new ArrayList<>();
//LISTA DE RESPOSTA É VAZIA PQ ACABOU DE SER CRIADA.
	
	public Topico() {
	}
	
	public Topico(String titulo, String mensagem, Curso curso) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.curso = curso;
	}
	
//public Topico() {
//}
//JPA PRECISA Q A CLASSE TENHA UM CONSTRUTOR DEFAULT.
//Public Topico(String titulo, String mensagem, Curso curso) {
//CRIADO POR CAUSA DA RELAÇÃO COM O TOPICOFORM
//@Override
//É USADA PARA SOBRESCREVER O MÉTODO DA CLASSE MÃE, INDICANDO QUE O MÉTODO ORIGINAL FOI ALTERADO.

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topico other = (Topico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public void setStatus(StatusTopico status) {
		this.status = status;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
