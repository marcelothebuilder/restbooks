package io.github.marcelothebuilder.restbooks.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Representa a classe de domínio Livro.
 * 
 * @author Marcelo Paixao Resende
 *
 */
public @Data class Livro {
	private Long codigo;
	private String nome;
	private Date publicacao;
	private String editora;
	private String resumo;
	private Set<Comentario> comentarios = new HashSet<>();
	private String autor;

	public Livro() {
	}

	public Livro(String nome) {
		super();
		this.nome = nome;
	}

	public Livro(Long codigo, String nome, Date publicacao, String editora, String resumo, Set<Comentario> comentarios,
			String autor) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.publicacao = publicacao;
		this.editora = editora;
		this.resumo = resumo;
		this.comentarios = comentarios;
		this.autor = autor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Livro))
			return false;
		Livro other = (Livro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
}
