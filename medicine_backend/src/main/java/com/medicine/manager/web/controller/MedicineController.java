package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.MedicineQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.UserQuery;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.service.MedicineService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 药品表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("api")
public class MedicineController {
	@Autowired
	private MedicineService medicineService;

	@GetMapping(value = "medicine")
	@PreAuthorize("hasAnyRole('ADMIN','SERVICE','MEDICINE_ALL','MEDICINE_SELECT')")
	public ResponseEntity getMedicine(MedicineQuery medicineQuery, PageInfo pageInfo){
		return new ResponseEntity(medicineService.queryMedicine(medicineQuery, new Page(pageInfo.getPage(), pageInfo.getSize())), HttpStatus.OK);
	}
	@PutMapping(value = "medicine/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SERVICE','MEDICINE_ALL','MEDICINE_EDIT')")
	public ResponseEntity updateMedicine(@PathVariable Long id, @RequestBody @Validated Medicine medicine){
		medicineService.updateMedicineById(id, medicine);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping(value = "medicine")
	@PreAuthorize("hasAnyRole('ADMIN','SERVICE','MEDICINE_ALL','MEDICINE_CREATE')")
	public ResponseEntity createMedicine(@RequestBody @Validated Medicine medicine){
		medicineService.createMedicine(medicine);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@DeleteMapping(value = "medicine/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SERVICE','MEDICINE_ALL','MEDICINE_DELETE')")
	public ResponseEntity insertMedicine(@PathVariable Long id){
		medicineService.removeMedicineById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	@GetMapping(value="medicine/download")
	@PreAuthorize("hasAnyRole('ADMIN','MEDICINE_ALL','MEDICINE_SELECT')")
	public void download(MedicineQuery medicineQuery, HttpServletResponse response) throws IOException {
		medicineService.download(medicineService.queryMedicine(medicineQuery), response);
	}
}
