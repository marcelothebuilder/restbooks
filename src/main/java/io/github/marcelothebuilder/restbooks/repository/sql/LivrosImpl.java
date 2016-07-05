package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.tables.Livro.LIVRO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.repository.Livros;

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
			.fetch(	r -> {
				Record livroRecord = r.into(LIVRO.fields());
				Record comentarioRecord = r.into(COMENTARIO.fields());
				
				Livro livro = livroRecord.into(Livro.class);
				Comentario comentario = comentarioRecord.into(Comentario.class);
				
				livros.add(livro);
				
				// get fresh livro from set
				Livro freshLivro = livros.stream().filter(l -> l.equals(livro)).findFirst().get();
				
				freshLivro.getComentarios().add(comentario);
				
				return null;
			});
		
		return new ArrayList<>(livros);
	}

//
//	public List<Livro> todos2() {
//		List<Livro> livros = dsl.select(
//				LIVRO.CODIGO,
//				LIVRO.NOME,
//				LIVRO.PUBLICACAO,
//				LIVRO.EDITORA,
//				LIVRO.RESUMO,
//				LIVRO.AUTOR)
//			.from(LIVRO)
//			.fetchInto(Livro.class);
//		
//		livros.stream().forEach(livro -> {
//			livro.setComentarios(
//					dsl.select(
//							COMENTARIO.CODIGO,
//							COMENTARIO.CODIGO_LIVRO,
//							COMENTARIO.AUTOR,
//							COMENTARIO.CONTEUDO
//						)
//						.from(COMENTARIO).fetchInto(Comentario.class)
//					);
//			
//		});
//		
//		return livros;
//	}

}
