package io.github.marcelothebuilder.restbooks.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
			String setterName = getSetterNameFromGetter(getter);
			
			Class<?> propertyType = getter.getReturnType();
			
			// procura um método setter na classe de destino
			// que tenha como unico parametro do tipo propertyType
			Method setter = ReflectionUtils.findMethod(destinationClass, setterName, propertyType);
						
			// encontrado
			if (setter != null) {
				setPropertyValue(dst, setter, getPropertyValue(source, getter));
			// busca métodos alternativos que sabemos converter
			} else if (strictness == CopyStrictness.LOOSE_DATETIME) {
				if (java.util.Date.class.equals(propertyType)) {
					setter = findTimestampSetter(destinationClass, setterName);
					
					// encontrado?
					if (setter != null) {
						// o setter é para Timestamp
						java.sql.Timestamp timestampValue = DateUtils.toTimestamp((Date) getPropertyValue(source, getter));
						
						setPropertyValue(dst, setter, timestampValue);
					} else {
						// procura setter de sqlDate
						setter = findSqlDateSetter(destinationClass, setterName);
						
						// encontrado?
						if (setter != null) {
							setPropertyValue(dst, setter, (java.sql.Date) getPropertyValue(source, getter));
						}
					}
				} else if (java.sql.Timestamp.class.equals(propertyType)) {
					setter = ReflectionUtils.findMethod(destinationClass, setterName, java.sql.Timestamp.class);
					
					// encontrado?
					if (setter != null) {
						// o setter é para Date
						java.util.Date dateValue = DateUtils.toDate((Timestamp) getPropertyValue(source, getter));
						
						setPropertyValue(dst, setter, dateValue);
					}
				}
			}			
		});

		return dst;
	}

	private static <T> Method findSqlDateSetter(Class<T> destinationClass, String setterName) {
		return ReflectionUtils.findMethod(destinationClass, setterName, java.sql.Date.class);
	}

	private static <T> Method findTimestampSetter(Class<T> destinationClass, String setterName) {
		return ReflectionUtils.findMethod(destinationClass, setterName, java.sql.Timestamp.class);
	}

	private static <T> void setPropertyValue(T dst, Method setter, Object propertyValue) {
		try {
			setter.invoke(dst, propertyValue);
		} catch (Exception e) {
			throw new RuntimeException("Error while transfering data", e);
		}
	}

	private static <D> Object getPropertyValue(D source, Method getter) {
		try {
			return getter.invoke(source);
		} catch (Exception e) {
			throw new RuntimeException("Error while transfering data", e);
		}
	}

	private static String getSetterNameFromGetter(Method getter) {
		String property = getter.getName().substring(3);
		String setterName = String.format("set%s", property);
		return setterName;
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
