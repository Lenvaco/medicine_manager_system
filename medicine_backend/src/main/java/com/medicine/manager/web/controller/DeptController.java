package com.medicine.manager.web.controller;

import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.bean.dto.DeptDTO;
import com.medicine.manager.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	public ResponseEntity getDepts(@RequestBody(required = false) DeptQuery deptQuery){
		return new ResponseEntity(deptService.buildTree(deptService.queryAll(deptQuery)), HttpStatus.OK);
	}
}
