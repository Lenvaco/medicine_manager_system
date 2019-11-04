package com.medicine.manager.web.controller;

import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.Dept;
import com.medicine.manager.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lenvaco
 * @date 2019/10/24 9:17
 */
@RestController
@RequestMapping("api")
public class DeptController {
	@Autowired
	private DeptService deptService;

	@GetMapping(value = "/dept")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
	public ResponseEntity getDepts( DeptQuery deptQuery){
		System.err.println("dept query name is "+ deptQuery);
		return new ResponseEntity(deptService.buildTree(deptService.queryAll(deptQuery)), HttpStatus.OK);
	}
	@PostMapping(value = "/dept")
	@PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
	public ResponseEntity create(@Validated @RequestBody Dept dept){
		if (dept.getId() != null) {
			throw new BadRequestException("The new dept should not have a Id");
		}
		return new ResponseEntity(deptService.create(dept),HttpStatus.CREATED);
	}

	@PutMapping(value = "/dept")
	@PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
	public ResponseEntity update(@Validated @RequestBody Dept dept){
		if(dept.getId() == null) {
			throw new BadRequestException("传入的部门标志码非法");
		}
		if(dept.getId().equals(dept.getParentId())) {
			throw new BadRequestException("上级不能为自己");
		}
		deptService.updateDept(dept);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/dept/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
	public ResponseEntity delete(@PathVariable Long id){
		deptService.removeById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
