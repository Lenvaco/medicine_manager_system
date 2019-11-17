package com.medicine.manager.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/10/26 9:45
 */
@Data
public class UserQuery implements Serializable {

	private static final long serialVersionUID = -7221637524494853613L;

	private Long id;

	private Long deptId;

	private String blurry;

	private Boolean enabled;
}
