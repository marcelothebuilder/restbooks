/**
 * This class is generated by jOOQ
 */
package io.github.marcelothebuilder.restbooks.jooq;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in PUBLIC
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_009B6353_2586_4D3C_A312_78E38D69855A</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_009B6353_2586_4D3C_A312_78E38D69855A = new SequenceImpl<Long>("SYSTEM_SEQUENCE_009B6353_2586_4D3C_A312_78E38D69855A", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_C293F54F_D0B3_41D0_A207_DB21D38E7ED8</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_C293F54F_D0B3_41D0_A207_DB21D38E7ED8 = new SequenceImpl<Long>("SYSTEM_SEQUENCE_C293F54F_D0B3_41D0_A207_DB21D38E7ED8", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);
}
