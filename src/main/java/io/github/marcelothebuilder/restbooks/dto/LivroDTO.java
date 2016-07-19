package io.github.marcelothebuilder.restbooks.dto;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.github.marcelothebuilder.restbooks.util.json.JsonDateFormat;
import lombok.Data;

@Data
public class LivroDTO {
	private Long codigo;

	@NotEmpty(message = "O nome não pode ser nulo")
	private String nome;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonDateFormat.DATE_ONLY, timezone = "GMT-3")
	private Date publicacao;
	private String editora;

	@JsonInclude(Include.NON_EMPTY)
	private String resumo;

	private AutorDTO autor;

	@JsonManagedReference
	private Set<ComentarioDTO> comentarios = new LinkedHashSet<>();
}
