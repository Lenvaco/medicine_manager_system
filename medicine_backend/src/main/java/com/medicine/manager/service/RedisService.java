package com.medicine.manager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lenvaco
 * @date 2019/9/29 15:21
 */
public interface RedisService {


	/**
	 * 查询验证码的值
	 * @param key
	 * @return
	 */
	String getCodeVal(String key);

	/**
	 * 保存验证码
	 * @param key
	 * @param val
	 */
	void saveCode(String key, Object val);

	/**
	 * delete
	 * @param key
	 */
	void delete(String key);

	/**
	 * 清空所有缓存
	 */
	void flushdb();
}