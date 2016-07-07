package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.ComentarioRecord;
import io.github.marcelothebuilder.restbooks.repository.Comentarios;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;

@Repository
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

	@Override
	public List<Comentario> buscarPorCodigoLivro(Long codigoLivro) {
		List<Comentario> comentarios = dsl.select(COMENTARIO.fields())
				.from(COMENTARIO)
				.where(COMENTARIO.CODIGO_LIVRO.eq(codigoLivro))
				.fetchInto(Comentario.class);
		return comentarios;
	}

	
}

