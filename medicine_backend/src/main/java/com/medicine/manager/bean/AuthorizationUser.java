package com.medicine.manager.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author lenvaco
 * @date 2019/9/27 9:00
 */
@Getter
@Setter
public class AuthorizationUser {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String captcha;

	private String uuid = "";

	@Override
	public String toString() {
		return "{username=" + username  + ", password= ******}";
	}
}