package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.UserQuery;
import com.medicine.manager.model.Supplier;
import com.medicine.manager.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 药品经销商表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("/api")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@GetMapping(value = "/supplier")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_SELECT')")
	public ResponseEntity getSuppliers(String supplierName, PageInfo pageInfo){
		return new ResponseEntity(supplierService.querySuppliers(supplierName, new Page(pageInfo.getPage(), pageInfo.getSize())), HttpStatus.OK);
	}

	@PostMapping(value = "/supplier")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_CREATE')")
	public ResponseEntity createSupplier(@Validated Supplier supplier){
		supplierService.createSupplier(supplier);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/supplier/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_UPDATE')")
	public ResponseEntity updateSupplier(@PathVariable Long id, @Validated Supplier supplier){
		supplierService.updateSupplier(id, supplier);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@DeleteMapping("/supplier/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_DELETE')")
	public ResponseEntity deleteSupplier(@PathVariable Long id){
		supplierService.removeSupplierById(id);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}
