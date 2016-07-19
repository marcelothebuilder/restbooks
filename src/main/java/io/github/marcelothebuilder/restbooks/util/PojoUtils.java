package io.github.marcelothebuilder.restbooks.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ReflectionUtils;

public class PojoUtils {
	
	public static <T, D> T copyProperties(D source, Class<T> dstClass) {
		return PojoUtils.copyProperties(source, dstClass, PojoUtils.CopyStrictness.STRICT);
	}
	
	public static <T, D> T copyProperties(D source, Class<T> destinationClass, CopyStrictness strictness) {
		if (source == null) {
			return null;
		}
		
		Class<?> sourceClass = source.getClass();

		T dst = createClassInstance(destinationClass);
		
		List<Method> getters = getGetterMethods(sourceClass);
		
		getters.forEach(getter -> {
			String property = getter.getName().substring(3);
			Class<?> propertyType = getter.getReturnType();
			String setterName = String.format("set%s", property);
			
			// procura um método setter na classe de destino
			// que tenha como unico parametro do tipo propertyType
			Method setter = ReflectionUtils.findMethod(destinationClass, setterName, propertyType);
			
			
			try {
				// encontrado
				if (setter != null) {
					setter.invoke(dst, getter.invoke(source));
				// busca métodos alternativos que sabemos converter
				} else if (strictness == CopyStrictness.LOOSE_DATETIME) {
					
					if (java.util.Date.class.equals(propertyType)) {
						setter = ReflectionUtils.findMethod(destinationClass, setterName, java.sql.Timestamp.class);
						
						// encontrado?
						if (setter != null) {
							java.util.Date getterDate = (java.util.Date) getter.invoke(source);
							
							// o setter é para Timestamp
							java.sql.Timestamp timestampValue = DateUtils.toTimestamp(getterDate);
							
							// invoca o setter de timestamp
							setter.invoke(dst, timestampValue);
						}
					} else if (java.sql.Timestamp.class.equals(propertyType)) {
						setter = ReflectionUtils.findMethod(destinationClass, setterName, java.sql.Timestamp.class);
						
						// encontrado?
						if (setter != null) {
							java.sql.Timestamp getterTimestamp = (java.sql.Timestamp) getter.invoke(source);
							
							// o setter é para Date
							java.util.Date dateValue = DateUtils.toDate(getterTimestamp);
							
							// invoca o setter de date
							setter.invoke(dst, dateValue);
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while transfering data", e);
			}				
		});

		return dst;
	}
	
	private static <T> T createClassInstance(Class<T> destinationClass) {
		try {
			return (T) destinationClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Error while transfering data", e);
		}
	}

	private static List<Method> getGetterMethods(Class<?> sourceClass) {
		return Arrays.stream(sourceClass.getDeclaredMethods())
			.filter(method -> method.getParameterTypes().length == 0) // getters não podem ter parametros
			.filter(method -> method.getName().startsWith("get")) // devem começar com "get"
			.filter(method -> !Collection.class.isAssignableFrom(method.getReturnType())) // não queremos getters que retornam collections
			.collect(Collectors.toList());
	}

	public enum CopyStrictness {
		STRICT,
		LOOSE_DATETIME;
	}
}
