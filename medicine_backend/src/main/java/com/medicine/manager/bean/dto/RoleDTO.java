package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lenvaco
 * @date 2019/10/11 9:09
 */
@Data
public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 5248707532780645197L;
	private Long id;

	private String name;

	private Integer level;

	private String remark;

	private Set<PermissionDTO> permissions;

	private Set<MenuDTO> menus;

	private Set<DeptDTO> depts;

	private Date createTime;

	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.remark = role.getRemark();
		this.level = role.getLevel();
		this.permissions = role.getPermissions().stream().map(permission ->  new PermissionDTO(permission)).collect(Collectors.toSet());
		this.menus = role.getMenus().stream().map(menu ->  MenuDTO.toDTO(menu, null)).collect(Collectors.toSet());
		this.depts = role.getDepts() != null? role.getDepts().stream().map(dept ->  new DeptDTO(dept)).collect(Collectors.toSet()) : null;
		this.createTime = role.getCreateTime();
	}
}
