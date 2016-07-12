package io.github.marcelothebuilder.restbooks.dto;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.github.marcelothebuilder.restbooks.util.json.JsonDateFormat;
import lombok.Data;

@Data
public class LivroDTO {
	private Long codigo;
	private String nome;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonDateFormat.DATE_ONLY, timezone = "GMT-3")
	private Date publicacao;
	private String editora;
	
	@JsonInclude(Include.NON_EMPTY)
	private String resumo;

	private AutorDTO autor;

	@JsonManagedReference
	private Set<ComentarioDTO> comentarios = new LinkedHashSet<>();	
}
