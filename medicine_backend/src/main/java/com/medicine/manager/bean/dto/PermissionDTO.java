package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Permission;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/11 9:18
 */
@Data
public class PermissionDTO {

	private Long id;

	private String name;

	private Long parentId;

	private String alias;

	private Date createTime;

	private List<PermissionDTO> children;
	public PermissionDTO(Permission permission) {
		this.id = permission.getId();
		this.name = permission.getName();
		this.parentId = permission.getParentId();
		this.alias = permission.getAlias();
		this.createTime = permission.getCreateTime();
	}
}
