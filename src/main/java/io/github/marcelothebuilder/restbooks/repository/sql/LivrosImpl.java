package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.Tables.AUTOR;
import static io.github.marcelothebuilder.restbooks.jooq.Tables.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.Tables.LIVRO;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.jooq.tables.records.AutorRecord;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.ComentarioRecord;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.LivroRecord;
import io.github.marcelothebuilder.restbooks.model.Autor;
import io.github.marcelothebuilder.restbooks.model.Comentario;
import io.github.marcelothebuilder.restbooks.model.Livro;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.util.DateUtils;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;
import io.github.marcelothebuilder.restbooks.util.PojoUtils.CopyStrictness;

@Repository
public class LivrosImpl implements Livros {

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Livro> todos() {
		Set<Livro> set = selectLivros()
				.fetch()
				.stream()
				.collect(
					Collectors.groupingBy(
						this.recordToLivro(),
						LinkedHashMap::new,
						Collectors.mapping(
							this.recordToComentario(), 
							Collectors.toSet()
						)
					)
				)
				.entrySet()
				.stream()
				.map(e -> {
					Livro livro = e.getKey();
					Set<Comentario> comentarios = e.getValue();
					comentarios.stream().forEach(c -> c.setLivro(livro));
					livro.setComentarios(comentarios);
					return livro;
				})
				.collect(Collectors.toSet());
				
		return new LinkedList<>(set);

	}

	@Override
	public Livro salvar(Livro livro) {
		LivroRecord record = PojoUtils.copyProperties(livro, LivroRecord.class, CopyStrictness.LOOSE_DATETIME);
		
		dsl.attach(record);
		
		record.store();
		
		return PojoUtils.copyProperties(record, Livro.class, CopyStrictness.LOOSE_DATETIME);
	}

	@Override
	public Livro buscar(Long codigo) {
		Set<Livro> set = selectLivros()
				.where(LIVRO.CODIGO.eq(codigo))
				.fetch()
				.stream()
				.collect(
					Collectors.groupingBy(
						this.recordToLivro(),
						LinkedHashMap::new,
						Collectors.mapping(
							this.recordToComentario(), 
							Collectors.toSet()
						)
					)
				)
				.entrySet()
				.stream()
				.map(e -> {
					Livro livro = e.getKey();
					Set<Comentario> comentarios = e.getValue();
					comentarios.stream().forEach(c -> c.setLivro(livro));
					livro.setComentarios(comentarios);
					return livro;
				})
				.collect(Collectors.toSet());
		
		return set.isEmpty() ? null : set.iterator().next();
	}

	@Override
	public void deletar(Long codigo) {
		dsl.deleteFrom(LIVRO).where(LIVRO.CODIGO.eq(codigo));
	}
	
	private SelectWhereStep<Record> selectLivros() {
		return this.dsl.select(LIVRO.fields())
				.select(COMENTARIO.fields())
				.select(AUTOR.fields())
				.from(LIVRO)
				.join(COMENTARIO, JoinType.LEFT_OUTER_JOIN)
					.onKey()
				.join(AUTOR, JoinType.LEFT_OUTER_JOIN)
					.onKey();
	}
	
	private Function<Record, Livro> recordToLivro() {
		return r -> {			
			LivroRecord livroRecord = r.into(LIVRO.fields()).into(LivroRecord.class);
			AutorRecord autorRecord = r.into(AUTOR.fields()).into(AutorRecord.class);
			
			Livro livro = PojoUtils.copyProperties(livroRecord, Livro.class);
			livro.setPublicacao(livroRecord.getPublicacao());
			
			Autor autor = PojoUtils.copyProperties(autorRecord, Autor.class);
			autor.setNascimento(autorRecord.getNascimento());
			
			livro.setAutor(autor);
			
			return livro;
		};
	}
	
	private Function<Record, Comentario> recordToComentario() {
		return r -> {
			ComentarioRecord comentarioRecord = r.into(COMENTARIO.fields()).into(ComentarioRecord.class);
			
			Comentario comentario = PojoUtils.copyProperties(comentarioRecord, Comentario.class);
			
			comentario.setData(DateUtils.toDate(comentarioRecord.getData()));
			
			return comentario;
		};
	}
}
