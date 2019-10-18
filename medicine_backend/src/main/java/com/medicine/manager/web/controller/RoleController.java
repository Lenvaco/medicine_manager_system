package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.dto.RoleDTO;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Controller
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	/**
	 * 获取单个role
	 * @param roleId
	 * @return
	 */
	@GetMapping(value = "/roles/{roleId}")
	@ApiOperation(value = "获取角色", notes = "根据roleId获取角色", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
	public ResponseEntity getRoles(@PathVariable Long roleId){
		return new ResponseEntity(roleService.getById(roleId), HttpStatus.OK);
	}
	/**
	 * 返回全部的角色，新增用户时下拉选择
	 * @return
	 */
	@GetMapping(value = "/roles/all")
	@ApiOperation(value = "获取全部角色", notes = "获取全部角色信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
	public ResponseEntity getAll(){
		return new ResponseEntity(roleService.list(), HttpStatus.OK);
	}

	@PostMapping(value = "/roles")
	@ApiOperation(value = "新增角色", notes = "新建新的角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_CREATE')")
	public ResponseEntity create(@RequestBody Role role){
		if (role.getRoleId() != null) {
			throw new BadRequestException("RoleId should be null but failed!");
		}
		roleService.save(role);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping(value = "/roles")
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
	public ResponseEntity update(@RequestBody Role role){
		if(role == null || role.getRoleId() == null) {
			throw new BadRequestException("Role id should not be null");
		}
		roleService.update(role, null);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/roles/{roleId}")
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_DELETE')")
	public ResponseEntity delete(@PathVariable Long roleId){
		roleService.removeById(roleId);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping(value = "/roles/permission")
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
	public ResponseEntity updatePermission(@RequestBody Role role){
		roleService.updatePermission(role);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/roles/menu")
	@PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
	public ResponseEntity updateMenu(@RequestBody Role role){
		roleService.updateMenu(role);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
