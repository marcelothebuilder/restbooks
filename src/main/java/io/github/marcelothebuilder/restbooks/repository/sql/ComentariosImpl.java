package io.github.marcelothebuilder.restbooks.repository.sql;

import static io.github.marcelothebuilder.restbooks.jooq.tables.Comentario.COMENTARIO;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.jooq.tables.records.ComentarioRecord;
import io.github.marcelothebuilder.restbooks.model.Comentario;
import io.github.marcelothebuilder.restbooks.repository.Comentarios;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;
import io.github.marcelothebuilder.restbooks.util.PojoUtils.CopyStrictness;

@Repository
public class ComentariosImpl extends JooqRepository implements Comentarios {

	@Override
	public Comentario salvar(Comentario comentario) {
		ComentarioRecord record = PojoUtils.copyProperties(comentario, ComentarioRecord.class, CopyStrictness.LOOSE_DATETIME);
		record.setCodigoLivro(comentario.getLivro().getCodigo());
		
		dsl().attach(record);
		record.store();
		
		return PojoUtils.copyProperties(record, Comentario.class, CopyStrictness.LOOSE_DATETIME);
	}

	@Override
	public List<Comentario> buscarPorCodigoLivro(Long codigoLivro) {
		return dsl().selectFrom(COMENTARIO)
				.where(COMENTARIO.CODIGO_LIVRO.eq(codigoLivro))
				.fetchInto(Comentario.class);
	}

}
