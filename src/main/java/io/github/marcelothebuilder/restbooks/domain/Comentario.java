package io.github.marcelothebuilder.restbooks.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Marcelo Paixao Resende
 *
 */
public @Data class Comentario {
	private Long codigo;
	private Long codigoLivro;
	private String autor;
	private String conteudo;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss", timezone="GMT-3")
	private Date data;
	
	@JsonIgnore
	private Livro livro;
	
	/**
	 * Verifica se o Comentario tem um código.
	 * @return true caso <tt>codigo</tt> seja true.
	 */
	public boolean hasCodigo() {
		return codigo != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Comentario))
			return false;
		Comentario other = (Comentario) obj;
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
