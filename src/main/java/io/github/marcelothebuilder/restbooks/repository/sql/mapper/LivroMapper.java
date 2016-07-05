package io.github.marcelothebuilder.restbooks.repository.sql.mapper;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.tables.Livro.LIVRO;

import java.util.Set;

import org.jooq.Record;
import org.jooq.RecordMapper;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.domain.Livro;

/**
 * <p>
 * Recebe um record proveniente de um join com todas tabelas-filha de
 * {@link LIVRO} e retorna um objeto {@link Livro} com as tabelas-filha
 * inseridas como objetos DTO.
 * </p>
 * 
 * <p>
 * Mapeia records de {@link COMENTARIO} como uma coleção em ScheduledDTO, e para
 * isso armazenamos os Livro em um Set.
 * </p>
 * 
 * <p>
 * TODO: Esta classe viola o contrato de {@link RecordMapper} ao armazenar os
 * resultados num Set. O ideal é que esta classe não mantenha referências de
 * listas iniciadas externamente para funcionar.
 * </p>
 * 
 * @author Marcelo Paixao Resende
 *
 */
public class LivroMapper implements RecordMapper<Record, Livro> {
	private Set<Livro> livros;

	public LivroMapper(Set<Livro> livros) {
		this.livros = livros;
	}
	
	@Override
	public Livro map(Record record) {
		Record livroRecord = record.into(LIVRO.fields());
		Record comentarioRecord = record.into(COMENTARIO.fields());
		
		Livro livro = livroRecord.into(Livro.class);
		Comentario comentario = comentarioRecord.into(Comentario.class);
		
		this.livros.add(livro);
		
		if (comentario.hasCodigo()) {
			// get fresh livro from set
			Livro freshLivro = livros.stream().filter(l -> l.equals(livro)).findFirst().get();
			
			freshLivro.getComentarios().add(comentario);
		}
		
		return livro;
	}

}
