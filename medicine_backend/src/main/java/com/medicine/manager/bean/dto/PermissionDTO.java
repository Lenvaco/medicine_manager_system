package com.medicine.manager.bean.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/11 9:18
 */
@Data
public class PermissionDTO {

	private Long permissionId;

	private String permissionName;

	private Long parentId;

	private String permissionDescription;

	private Date gmtCreate;

	private List<PermissionDTO> children;
}
