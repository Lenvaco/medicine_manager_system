package com.medicine.manager.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/9/27 8:59
 */
@Getter
@AllArgsConstructor
public class AuthenticationInfo implements Serializable {

	private final String token;

	private final JwtUser user;
}