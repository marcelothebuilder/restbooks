package io.github.marcelothebuilder.restbooks.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.marcelothebuilder.restbooks.util.json.JsonDateFormat;
import lombok.Data;

@Data
public class ComentarioDTO {
	private Long codigo;
	private String autor;
	private String conteudo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonDateFormat.ISO_8601_24H_FULL_FORMAT, timezone = "GMT-3")
	private Date data;

	@JsonBackReference
	private LivroDTO livro;
	
	
}
