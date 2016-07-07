package io.github.marcelothebuilder.restbooks.util;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

public class PojoUtils {
	public static <T, D> T copyProperties(D source, Class<T> dstClass) {
		T dst = null;

		try {
			Class<?> sourceClass = source.getClass();

			dst = (T) dstClass.newInstance();

			Method[] sourceMethods = sourceClass.getDeclaredMethods();

			for (Method method : sourceMethods) {
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					String property = methodName.substring(3);

					Class<?> sourceMethodReturnType = method.getReturnType();
					String setterName = String.format("set%s", property);

					Method dstMethod = ReflectionUtils.findMethod(dstClass, setterName, sourceMethodReturnType);

					if (dstMethod != null) {
						dstMethod.invoke(dst, method.invoke(source));
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error when transfering data", e);
		}

		return dst;
	}
}
