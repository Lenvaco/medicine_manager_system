package com.medicine.manager.common.utils;

/**
 * @author lenvaco
 * @date 2019/9/29 11:00
 */
public class ValidationUtil {
	/**
	 * 验证是否为邮箱
	 * @param string
	 * @return
	 */
	public static boolean isEmail(String string) {
		if (string == null){
			return false;
		}
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return string.matches(regEx1);
	}
}
