package com.medicine.manager.web.controller;


import com.medicine.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("/api")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	/**
	 * 返回全部的权限，新增角色时下拉选择
	 * @return
	 */
	@GetMapping(value = "/permissions/tree")
	@PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE','PERMISSION_EDIT','ROLES_SELECT','ROLES_ALL')")
	public ResponseEntity getTree(){
		return new ResponseEntity(permissionService.getPermissionTree(permissionService.findByParentId(0L)), HttpStatus.OK);
	}
}
