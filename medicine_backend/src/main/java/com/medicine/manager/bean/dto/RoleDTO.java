package com.medicine.manager.bean.dto;

import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @author lenvaco
 * @date 2019/10/11 9:09
 */
@Data
public class RoleDTO {


	private Long roleId;

	private String roleName;

	private String roleDescription;

	private Set<PermissionDTO> permissions;

	private Set<MenuDTO> menus;

	private Date gmtCreate;

	public RoleDTO(Long roleId, String roleName, String roleDescription, Set<PermissionDTO>permissions,
				   Set<MenuDTO>menus, Date gmtCreate) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.permissions = permissions;
		this.menus = menus;
		this.gmtCreate = gmtCreate;
	}
}
