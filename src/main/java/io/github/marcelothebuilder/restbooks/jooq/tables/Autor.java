/**
 * This class is generated by jOOQ
 */
package io.github.marcelothebuilder.restbooks.jooq.tables;


import io.github.marcelothebuilder.restbooks.jooq.Keys;
import io.github.marcelothebuilder.restbooks.jooq.Public;
import io.github.marcelothebuilder.restbooks.jooq.tables.records.AutorRecord;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Autor extends TableImpl<AutorRecord> {

	private static final long serialVersionUID = 449033257;

	/**
	 * The reference instance of <code>PUBLIC.AUTOR</code>
	 */
	public static final Autor AUTOR = new Autor();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AutorRecord> getRecordType() {
		return AutorRecord.class;
	}

	/**
	 * The column <code>PUBLIC.AUTOR.CODIGO</code>.
	 */
	public final TableField<AutorRecord, Long> CODIGO = createField("CODIGO", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.AUTOR.NOME</code>.
	 */
	public final TableField<AutorRecord, String> NOME = createField("NOME", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.AUTOR.NASCIMENTO</code>.
	 */
	public final TableField<AutorRecord, Date> NASCIMENTO = createField("NASCIMENTO", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>PUBLIC.AUTOR.NACIONALIDADE</code>.
	 */
	public final TableField<AutorRecord, String> NACIONALIDADE = createField("NACIONALIDADE", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>PUBLIC.AUTOR</code> table reference
	 */
	public Autor() {
		this("AUTOR", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.AUTOR</code> table reference
	 */
	public Autor(String alias) {
		this(alias, AUTOR);
	}

	private Autor(String alias, Table<AutorRecord> aliased) {
		this(alias, aliased, null);
	}

	private Autor(String alias, Table<AutorRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AutorRecord, Long> getIdentity() {
		return Keys.IDENTITY_AUTOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AutorRecord> getPrimaryKey() {
		return Keys.CONSTRAINT_3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AutorRecord>> getKeys() {
		return Arrays.<UniqueKey<AutorRecord>>asList(Keys.CONSTRAINT_3);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Autor as(String alias) {
		return new Autor(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Autor rename(String name) {
		return new Autor(name, null);
	}
}
