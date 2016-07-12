package io.github.marcelothebuilder.restbooks.repository.sql;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

abstract class JooqRepository {
	@Autowired
	private DSLContext dsl;
	
	protected DSLContext dsl() {
		return dsl;
	}
}
