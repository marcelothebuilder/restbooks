package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.tables.Livro.LIVRO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.repository.sql.mapper.LivroMapper;

@Repository
public class LivrosImpl implements Livros {

	@Autowired
	private DSLContext dsl;
	
	@Override
	public List<Livro> todos() {
		
		Set<Livro> livros = new HashSet<>();
		
		dsl.select(LIVRO.fields())
			.select(COMENTARIO.fields())
			.from(LIVRO)
			.join(COMENTARIO)
			.on(COMENTARIO.CODIGO_LIVRO.eq(LIVRO.CODIGO))
			.fetch(new LivroMapper(livros));
		
		return new ArrayList<>(livros);
	}

}
