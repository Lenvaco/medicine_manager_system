package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/10/21 15:31
 */
@Data
public class RoleSmallDTO implements Serializable {

	private static final long serialVersionUID = -501851930029501160L;
	private Long id;

	private String name;

	private Integer level;

	public RoleSmallDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.level = role.getLevel();
	}
}
