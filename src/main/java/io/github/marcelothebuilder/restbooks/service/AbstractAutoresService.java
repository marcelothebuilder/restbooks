package io.github.marcelothebuilder.restbooks.service;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.model.Autor;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;

public abstract class AbstractAutoresService implements AutoresService {
	protected static AutorDTO toDto(Autor livro) {
		return PojoUtils.copyProperties(livro, AutorDTO.class);
	}

	protected static Autor fromDto(AutorDTO livroDto) {
		return PojoUtils.copyProperties(livroDto, Autor.class);
	}
}
