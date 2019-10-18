package com.medicine.manager.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;

/**
 * @author lenvaco
 * @date 2019/9/27 15:17
 */
@Slf4j
public class JacksonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		// 对象的所有字段全部列入
//		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
		// 取消默认转换timestamps形式
//		objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
		// 忽略空Bean转Json的错误
//		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		// 所有日期格式统一为 yyyy:MM:dd HH:mm:ss
//		objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
		// 忽略在Json字符串中存在，但在Java对象中不存在的对应属性的状况，防止错误
//		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 对象返回Json字符串
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> String obj2String(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("Parse object to String error", e);
			return null;
		}
	}

	/**
	 * 对象返回格式化后的Json字符串
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> String obj2StringPretty(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("Parse Object to String error", e);
			return null;
		}
	}

	/**
	 * Json字符串转成对象
	 * @param str
	 * @param clazz
	 * @param <T>
	 */
	public static <T> T string2Obj(String str, Class<T> clazz) {
		if (Strings.isEmpty(str) || clazz == null) {
			return null;
		}
		try {
			return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.warn("Parse String to Object error", e);
			return null;
		}
	}

	/**
	 * 泛型反序列化
	 * @param str
	 * @param typeReference 对应返回值的类型
	 * @param <T>
	 * @return
	 */
	public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
		if (Strings.isEmpty(str) || typeReference == null) {
			return null;
		}

		try {
			return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
		} catch (IOException e) {
			log.warn("Parse String to Object error", e);
			return null;
		}
	}

	/**
	 * 泛型反序列化
	 * @param str
	 * @param collectionClass 集合的类型
	 * @param elementClasses 集合中元素的类型
	 * @param <T>
	 * @return
	 */
	public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		try {
			return objectMapper.readValue(str, javaType);
		} catch (IOException e) {
			log.warn("Parse String to Object error", e);
			return null;
		}
	}
}