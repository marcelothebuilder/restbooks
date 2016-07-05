package io.github.marcelothebuilder.restbooks.domain;

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
