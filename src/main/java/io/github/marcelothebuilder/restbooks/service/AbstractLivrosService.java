package io.github.marcelothebuilder.restbooks.service;

import java.util.Set;
import java.util.stream.Collectors;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.dto.ComentarioDTO;
import io.github.marcelothebuilder.restbooks.dto.LivroDTO;
import io.github.marcelothebuilder.restbooks.model.Autor;
import io.github.marcelothebuilder.restbooks.model.Comentario;
import io.github.marcelothebuilder.restbooks.model.Livro;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;
import io.github.marcelothebuilder.restbooks.util.PojoUtils.CopyStrictness;

public abstract class AbstractLivrosService implements LivrosService {
	
	public AbstractLivrosService() {
		super();
	}
	
	protected static LivroDTO toDto(Livro livro) {
		Set<ComentarioDTO> comentarios = livro.getComentarios().stream()
			.map(AbstractLivrosService::comentarioToDto)
			.collect(Collectors.toSet());
		
		AutorDTO autor = PojoUtils.copyProperties(livro.getAutor(), AutorDTO.class, CopyStrictness.LOOSE_DATETIME);
		
		LivroDTO livroDto = PojoUtils.copyProperties(livro, LivroDTO.class, CopyStrictness.LOOSE_DATETIME);
		
		livroDto.setComentarios(comentarios);
		livroDto.setAutor(autor);
	
		return livroDto;
	}

	protected static Livro fromDto(LivroDTO livroDto) {
		Set<Comentario> comentarios = livroDto.getComentarios().stream()
				.map(AbstractLivrosService::comentarioFromDto)
				.collect(Collectors.toSet());
		
		Autor autor = PojoUtils.copyProperties(livroDto.getAutor(), Autor.class, CopyStrictness.LOOSE_DATETIME);
		
		Livro livro = PojoUtils.copyProperties(livroDto, Livro.class, CopyStrictness.LOOSE_DATETIME);
		
		livro.setAutor(autor);
		livro.setComentarios(comentarios);
		
		return livro;
	}

	protected static ComentarioDTO comentarioToDto(Comentario comentario) {
		return PojoUtils.copyProperties(comentario, ComentarioDTO.class, CopyStrictness.LOOSE_DATETIME);
	}

	protected static Comentario comentarioFromDto(ComentarioDTO comentario) {
		return PojoUtils.copyProperties(comentario, Comentario.class, CopyStrictness.LOOSE_DATETIME);
	}

}