package io.github.marcelothebuilder.restbooks.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Livro {
	private Long codigo;
	private String nome;

	private Date publicacao;
	private String editora;
	private String resumo;

	private Autor autor;

	private Set<Comentario> comentarios = new HashSet<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

}
