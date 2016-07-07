package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;
import static io.github.marcelothebuilder.restbooks.jooq.tables.Livro.LIVRO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.LivroRecord;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.util.DateUtils;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;

@Repository
public class LivrosImpl implements Livros {

	@Autowired
	private DSLContext dsl;
	
	@Override
	public Livro buscar(Long codigo) {
		Livro livro = new Livro();
		
		dsl.select(LIVRO.fields())
			.select(COMENTARIO.fields())
			.from(LIVRO)
			.join(COMENTARIO, JoinType.LEFT_OUTER_JOIN)
			.on(COMENTARIO.CODIGO_LIVRO.eq(LIVRO.CODIGO))
			.where(LIVRO.CODIGO.eq(codigo))
			.fetchInto(r -> {
				if (!livro.hasCodigo()) {
					r.into(LIVRO.fields()).into(livro);
				}
				
				Comentario comentario = r.into(COMENTARIO.fields()).into(Comentario.class);
				
				if (comentario.hasCodigo()) {
					livro.getComentarios().add(comentario);
					comentario.setLivro(livro);
				}
			});
		
		return livro.hasCodigo() ? livro : null;
	}
	
	@Override
	public void deletar(Long id) {
		dsl.deleteFrom(COMENTARIO)
			.where(COMENTARIO.CODIGO_LIVRO.eq(id))
			.execute();
		
		int deleted = dsl.deleteFrom(LIVRO)
			.where(LIVRO.CODIGO.eq(id))
			.execute();
		
		if (deleted == 0) {
			throw new DataAccessException(String.format("Livro de código %d não existe", id));
		}
	}
	
	@Override
	public List<Livro> todos() {
		
		final Set<Livro> livros = new HashSet<>();
		
		dsl.select(LIVRO.fields())
			.select(COMENTARIO.fields())
			.from(LIVRO)
			.join(COMENTARIO, JoinType.LEFT_OUTER_JOIN)
			.on(COMENTARIO.CODIGO_LIVRO.eq(LIVRO.CODIGO))
			.fetchInto(r -> {
				Livro livro = r.into(LIVRO.fields()).into(Livro.class);
				Comentario comentario = r.into(COMENTARIO.fields()).into(Comentario.class);
				
				// livro existe no set, atualiza
				if (livros.contains(livro)) {
					final Livro livroCompare = livro; 
					livro = livros.stream()
						.filter(l -> livroCompare.equals(l))
						.findFirst()
						.get();
				}
				
				// se o comentário é válido, adiciona ao Livro
				if (comentario.hasCodigo()) {
					livro.getComentarios().add(comentario);
					comentario.setLivro(livro);
				}
				
				livros.add(livro);
			});
		
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
		LivroRecord record = PojoUtils.copyProperties(livro, LivroRecord.class);
		record.setPublicacao( DateUtils.toTimestamp( livro.getPublicacao() ) );
		return record;
	}
	
	protected static Livro asDomain(LivroRecord record) {
		Livro livro = PojoUtils.copyProperties(record, Livro.class);
		livro.setPublicacao( DateUtils.toDate( record.getPublicacao() ) );		
		return livro;
	}

}
