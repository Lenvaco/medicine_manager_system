package com.medicine.manager.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.model.Customer;
import com.medicine.manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 顾客表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Controller
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private  CustomerService customerService;

	@GetMapping(value = "/customer")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_ALL','CUSTOMER_SELECT')")
	public ResponseEntity getCustomers(String customerName,PageInfo pageInfo){
		return new ResponseEntity(customerService.queryCustomers(customerName, new Page(pageInfo.getPage(), pageInfo.getSize())), HttpStatus.OK);
	}
	@PostMapping(value = "/customer")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_ALL','CUSTOMER_CREATE')")
	public ResponseEntity createCustomer(Customer customer){
		customerService.createCustomer(customer);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@PutMapping(value = "/customer/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_ALL','CUSTOMER_UPDATE')")
	public ResponseEntity updateCustomer(@PathVariable @NotNull Long id, Customer customer){
		customerService.updateCustomer(id, customer);
		return new ResponseEntity(HttpStatus.OK);
	}
	@DeleteMapping(value = "/customer/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_ALL','CUSTOMER_DELETE')")
	public ResponseEntity updateCustomer(@PathVariable @NotNull Long id){
		customerService.removeCustomerById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
