package com.medicine.manager.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author lenvaco
 * @date 2019/10/11 10:31
 */
public class EntityExistException extends RuntimeException {
	public EntityExistException(Class clazz, Object... searchParamsMap) {
		super(EntityExistException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return StringUtils.capitalize(entity) +
				" 已存在 " +
				searchParams;
	}

	private static <K, V> Map<K, V> toMap(
			Class<K> keyType, Class<V> valueType, Object... entries) {
		if ( (entries.length & 0x1 ) == 1) {
			throw new IllegalArgumentException("Invalid entries");
		}
		return IntStream.range(0, entries.length / 2).map(i -> i * 2)
				.collect(HashMap::new,
						(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
						Map::putAll);
	}
}
