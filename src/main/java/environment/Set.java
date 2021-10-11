package environment;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Set {

	public static void variable(String key, String value) {
		Map<String, String> result = new HashMap<String, String>();
		result.put(key, value);
		variables(result);		
	}
	
	public static void main(String[] args) {
		variable(args[0], args[1]);
	}

	public static void variables(Map<String, String> variables) {
		try {
			Class<?> classifier = Class.forName("java.lang.ProcessEnvironment");
			Field caseSensitiveField = classifier.getDeclaredField("theEnvironment");
			caseSensitiveField.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, String> caseSensitiveVariables = (Map<String, String>) caseSensitiveField.get(null);
			caseSensitiveVariables.putAll(variables);
			Field caseInsensitive = classifier.getDeclaredField("theCaseInsensitiveEnvironment");
			caseInsensitive.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, String> clientInsensitiveVariables = (Map<String, String>) caseInsensitive.get(null);
			clientInsensitiveVariables.putAll(variables);
		} catch (NoSuchFieldException e) {
			Class<?>[] classes = Collections.class.getDeclaredClasses();
			Map<String, String> environment = System.getenv();
			for (Class<?> classifier : classes) {
				if ("java.util.Collections$UnmodifiableMap".equals(classifier.getName())) {
					process(classifier, environment, variables);
				}
			}
		} catch (ClassNotFoundException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private static void process(Class<?> classifier, Map<String, String> environment, Map<String, String> variables) {
		try {
			Field field = classifier.getDeclaredField("m");
			field.setAccessible(true);
			Object obj = field.get(environment);
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) obj;
			map.clear();
			map.putAll(variables);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
