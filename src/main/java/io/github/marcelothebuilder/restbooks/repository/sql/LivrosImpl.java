package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.tables.Livro.LIVRO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.LivroRecord;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.repository.sql.mapper.LivroMapper;

@Repository
public class LivrosImpl implements Livros {

	@Autowired
	private DSLContext dsl;
	
	@Override
	public Livro buscar(Long codigo) {
		// receberá apenas um livro
		Set<Livro> livros = new HashSet<>();
		
		dsl.select(LIVRO.fields())
			.select(COMENTARIO.fields())
			.from(LIVRO)
			.join(COMENTARIO, JoinType.LEFT_OUTER_JOIN)
			.on(COMENTARIO.CODIGO_LIVRO.eq(LIVRO.CODIGO))
			.where(LIVRO.CODIGO.eq(codigo))
			.fetch(new LivroMapper(livros));
		
		if (livros.isEmpty()) { 
			return null;
		}
		
		return livros.iterator().next();
	}
	
	@Override
	public void deletar(Long id) {
		dsl.deleteFrom(COMENTARIO)
			.where(COMENTARIO.CODIGO_LIVRO.eq(id))
			.execute();
		
		dsl.deleteFrom(LIVRO)
			.where(LIVRO.CODIGO.eq(id))
			.execute();
	}
	
	@Override
	public List<Livro> todos() {
		
		Set<Livro> livros = new HashSet<>();
		
		dsl.select(LIVRO.fields())
			.select(COMENTARIO.fields())
			.from(LIVRO)
			.join(COMENTARIO, JoinType.LEFT_OUTER_JOIN)
			.on(COMENTARIO.CODIGO_LIVRO.eq(LIVRO.CODIGO))
			.fetch(new LivroMapper(livros));
		
		return new ArrayList<>(livros);
	}

	@Override
	public Livro salvar(Livro livro) {
		if (livro.hasCodigo()) {
			return this.atualizar(livro);
		} else {
			return this.salvarNovo(livro);
		}
	}
	
	private Livro atualizar(Livro livro) {
		LivroRecord record = LivrosImpl.asRecord(livro);
		dsl.attach(record);
		record.update();
		return LivrosImpl.asDomain(record);
	}

	private Livro salvarNovo(Livro livro) {
		LivroRecord record = LivrosImpl.asRecord(livro);
		
		record = dsl.insertInto(LIVRO,
				LIVRO.AUTOR,
				LIVRO.EDITORA,
				LIVRO.NOME,
				LIVRO.PUBLICACAO,
				LIVRO.RESUMO)
		.values(record.getAutor(),
				record.getEditora(),
				record.getNome(),
				record.getPublicacao(),
				record.getResumo())
		.returning()
		.fetchOne();
		
		Livro livroReturn = LivrosImpl.asDomain(record);
		
		return livroReturn;
	}
	
	protected static LivroRecord asRecord(Livro livro) {
		Timestamp timestamp = new Timestamp(livro.getPublicacao().getTime());
		
		LivroRecord record = new LivroRecord();
		record.setCodigo(livro.getCodigo());
		record.setAutor(livro.getAutor());
		record.setEditora(livro.getEditora());
		record.setNome(livro.getNome());
		record.setResumo(livro.getResumo());
		record.setPublicacao(timestamp);
		
		return record;
	}
	
	protected static Livro asDomain(LivroRecord record) {
		Date date = new Date(record.getPublicacao().getTime());
		
		Livro livro = new Livro();
		livro.setAutor(record.getAutor());
		livro.setCodigo(record.getCodigo());
		livro.setEditora(record.getEditora());
		livro.setNome(record.getNome());
		livro.setPublicacao(date);
		livro.setResumo(record.getResumo());
		
		return livro;
	}

}
