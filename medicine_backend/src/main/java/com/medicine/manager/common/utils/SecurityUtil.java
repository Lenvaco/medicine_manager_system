package com.medicine.manager.common.utils;

import com.medicine.manager.bean.JwtUser;
import com.medicine.manager.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 获取当前用户
 * @author lenvaco
 * @date 2019/10/9 20:39
 */
public class SecurityUtil {

	public static UserDetails getUserDetails() {
		UserDetails userDetails = null;
		try {
			userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录状态过期");
		}
		return userDetails;
	}

	/**
	 * 获取系统用户名称
	 * @return 系统用户名称
	 */
	public static String getUsername(){
		UserDetails userDetails = getUserDetails();
		return userDetails.getUsername();
	}

	/**
	 * 获取系统用户id
	 * @return 系统用户id
	 */
	public static Long getUserId(){
		UserDetails userDetails = getUserDetails();
		return ((JwtUser)userDetails).getId();
	}
}
