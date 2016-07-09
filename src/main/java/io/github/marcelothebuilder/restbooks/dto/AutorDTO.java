package io.github.marcelothebuilder.restbooks.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.marcelothebuilder.restbooks.util.json.JsonDateFormat;
import lombok.Data;

@Data
public class AutorDTO {
	private Long codigo;
	private String nome;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonDateFormat.DATE_ONLY, timezone = "GMT-3")
	private Date nascimento;
	private String nacionalidade;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AutorDTO [codigo=" + codigo + ", nome=" + nome + ", nascimento=" + nascimento + ", nacionalidade="
				+ nacionalidade + "]";
	}
	
	
}
