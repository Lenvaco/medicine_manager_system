package com.medicine.manager.web.controller;


import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.PurchaseRecordDTO;
import com.medicine.manager.model.PurchaseRecord;
import com.medicine.manager.service.PurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 采购记录表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("/api")
public class PurchaseRecordController {

	@Autowired
	private PurchaseRecordService purchaseRecordService;

	@GetMapping("purchase")
	@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_ALL','PURCHASE_SELECT')")
	public ResponseEntity getPurchaseRecord(RecordQuery recordQuery, PageInfo pageInfo){
		return new ResponseEntity(purchaseRecordService.queryPurchaseRecord(recordQuery, pageInfo), HttpStatus.OK);
	}
	@PostMapping("purchase")
	@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_ALL','PURCHASE_CREATE')")
	public ResponseEntity createPurchaseRecord(@Validated PurchaseRecordDTO purchaseRecordDTO){
		purchaseRecordService.createPurchase(purchaseRecordDTO.toPurchaseRecord());
		return new ResponseEntity( HttpStatus.CREATED);
	}
	@PutMapping("purchase/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_ALL','PURCHASE_EDIT')")
	public ResponseEntity updatePurchaseRecord(@PathVariable Long id, @Validated PurchaseRecordDTO purchaseRecordDTO){
		purchaseRecordService.updatePurchaseById(id, purchaseRecordDTO.toPurchaseRecord());
		return new ResponseEntity(HttpStatus.OK);
	}
	@GetMapping("purchase/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_ALL','PURCHASE_DELETE')")
	public ResponseEntity deletePurchaseRecord(@PathVariable Long id){
		purchaseRecordService.removePurchaseById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
