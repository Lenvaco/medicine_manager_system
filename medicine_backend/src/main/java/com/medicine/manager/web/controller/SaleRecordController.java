package com.medicine.manager.web.controller;


import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.model.SaleRecord;
import com.medicine.manager.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 销售记录表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("/api")
public class SaleRecordController {
	@Autowired
	private SaleRecordService saleRecordService;

	@GetMapping("/sale")
	@PreAuthorize("hasAnyRole('ADMIN','SALE_ALL','SALE_SELECT')")
	public ResponseEntity getSaleRecord(RecordQuery recordQuery, PageInfo pageInfo){
		return new ResponseEntity(saleRecordService.querySaleRecord(recordQuery, pageInfo),HttpStatus.OK);
	}

	@PostMapping("/sale")
	@PreAuthorize("hasAnyRole('ADMIN','SALE_ALL','SALE_CREATE')")
	public ResponseEntity createSaleRecord(SaleRecord saleRecord){
		saleRecordService.create(saleRecord);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/sale/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SALE_ALL','SALE_UPDATE')")
	public ResponseEntity updateSaleRecord(@PathVariable Long id, SaleRecord saleRecord){
		saleRecordService.updateSaleRecord(id, saleRecord);
		return new ResponseEntity(HttpStatus.OK);
	}
	@DeleteMapping("/sale/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SALE_ALL','SALE_UPDATE')")
	public ResponseEntity deleteSaleRecord(@PathVariable Long id){
		saleRecordService.deleteSaleRecordById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
