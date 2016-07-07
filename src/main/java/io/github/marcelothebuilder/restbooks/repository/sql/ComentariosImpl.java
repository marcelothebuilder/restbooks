package io.github.marcelothebuilder.restbooks.repository.sql;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.ComentarioRecord;
import io.github.marcelothebuilder.restbooks.repository.Comentarios;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;

public class ComentariosImpl implements Comentarios {

	@Autowired
	private DSLContext dsl;
		
	@Override
	public Comentario salvar(Comentario comentario) {
		ComentarioRecord record = PojoUtils.copyProperties(comentario, ComentarioRecord.class);
		
		dsl.attach(record);
		
		if (comentario.hasCodigo()) {
			record.update();
		} else {
			record.insert();
		}
		
		return PojoUtils.copyProperties(record, Comentario.class);
	}

	
}

