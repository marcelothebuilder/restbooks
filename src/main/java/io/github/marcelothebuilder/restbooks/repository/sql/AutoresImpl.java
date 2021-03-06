package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.Tables.AUTOR;

import java.util.List;

import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.jooq.tables.records.AutorRecord;
import io.github.marcelothebuilder.restbooks.model.Autor;
import io.github.marcelothebuilder.restbooks.repository.Autores;

@Repository
public class AutoresImpl extends JooqRepository implements Autores {

	@Override
	public List<Autor> todos() {
		return selectAutores().fetchInto(Autor.class);
	}
	
	@Override
	public Autor buscar(Long codigo) {
		return selectAutores().where(AUTOR.CODIGO.eq(codigo)).fetchOneInto(Autor.class);
	}

	private SelectWhereStep<AutorRecord> selectAutores() {
		return dsl().selectFrom(AUTOR);
	}

	@Override
	public Autor salvar(Autor autor) {
		AutorRecord autorRecord = new AutorRecord();
		autorRecord.from(autor);
		dsl().attach(autorRecord);
		autorRecord.store();
		return autorRecord.into(Autor.class);
	}

	

}
