package com.medicine.manager.web.controller;


import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.RoleDTO;
import com.medicine.manager.bean.dto.RoleSmallDTO;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.Permission;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
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
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_SELECT')")
	public ResponseEntity getRoles(@PathVariable Long roleId){
		return new ResponseEntity(roleService.findByRoleId(roleId), HttpStatus.OK);
	}
	/**
	 * 返回全部的角色，新增用户时下拉选择
	 * @return
	 */
	@GetMapping(value = "/roles/all")
	@ApiOperation(value = "获取全部角色", notes = "获取全部角色信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
	public ResponseEntity getAll( PageInfo pageInfo){
		if(pageInfo == null ){
			pageInfo = new PageInfo();
		}
		return new ResponseEntity(roleService.queryAll(pageInfo), HttpStatus.OK);
	}

	@GetMapping(value = "/roles")
	@ApiOperation(value = "获取全部角色", notes = "模糊查询获取全部角色信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
	public ResponseEntity getAll(String blurry, PageInfo pageInfo){
		if(pageInfo == null ){
			pageInfo = new PageInfo();
		}
		return new ResponseEntity(roleService.queryAll(blurry, pageInfo), HttpStatus.OK);
	}

	@PostMapping(value = "/roles")
	@ApiOperation(value = "新增角色", notes = "新建新的角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_CREATE')")
	public ResponseEntity create(@RequestBody Role role){
		if (role.getId() != null) {
			throw new BadRequestException("RoleId should be null but failed!");
		}
		roleService.save(role);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@GetMapping(value = "/roles/level")
	public ResponseEntity getLevel(){
		List<Integer> levels = roleService.findByUserId(SecurityUtil.getUserId()).stream().map(Role::getLevel).collect(Collectors.toList());
		return new ResponseEntity(Dict.create().set("level", Collections.min(levels)),HttpStatus.OK);
	}

	@PutMapping(value = "/roles")
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_EDIT')")
	public ResponseEntity update(@RequestBody Role role){
		if(role == null || role.getId() == null) {
			throw new BadRequestException("Role id should not be null");
		}
		roleService.updateRole(role);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/roles/{roleId}")
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_DELETE')")
	public ResponseEntity delete(@PathVariable Long roleId){
		roleService.removeRoleById(roleId);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping(value = "/roles/permission")
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_EDIT')")
	public ResponseEntity updatePermission(@RequestBody Role role){
		roleService.updatePermission(role);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/roles/menu")
	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_EDIT')")
	public ResponseEntity updateMenu(@RequestBody Role role){
		roleService.updateMenu(role);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
