package io.github.marcelothebuilder.restbooks.jooqconfig;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

public class CodeGenerationStrategy extends DefaultGeneratorStrategy {

	@Override
	public String getJavaPackageName(Definition definition, Mode mode) {
		switch (mode) {
		case INTERFACE:
			//return "io.github.marcelothebuilder.restbooks.entity";
			break;
		case POJO:
			//return "io.github.marcelothebuilder.restbooks.entity.pojo";
			break;
		case DAO:
			break;
		case DEFAULT:
			break;
		case ENUM:
			break;
		case RECORD:
			break;
		default:
			break;
		}

		return super.getJavaPackageName(definition, mode);
	}

}
