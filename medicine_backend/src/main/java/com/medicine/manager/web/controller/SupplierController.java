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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
	public ResponseEntity getSuppliers(String name, PageInfo pageInfo){
		return new ResponseEntity(supplierService.querySuppliers(name, new Page(pageInfo.getPage(), pageInfo.getSize())), HttpStatus.OK);
	}

	@PostMapping(value = "/supplier")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_CREATE')")
	public ResponseEntity createSupplier(@RequestBody @Validated Supplier supplier){
		supplierService.createSupplier(supplier);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/supplier/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_EDIT')")
	public ResponseEntity updateSupplier(@PathVariable Long id, @RequestBody @Validated Supplier supplier){
		supplierService.updateSupplier(id, supplier);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@DeleteMapping("/supplier/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_DELETE')")
	public ResponseEntity deleteSupplier(@PathVariable Long id){
		supplierService.removeSupplierById(id);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@GetMapping(value = "/supplier/download")
	@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_ALL','SUPPLIER_SELECT')")
	public void download(String name, HttpServletResponse response) throws IOException {
		supplierService.download(supplierService.querySuppliers(name), response);
	}
}
