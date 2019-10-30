package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.service.MedicineService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity getMedicine(PageInfo pageInfo){
		return new ResponseEntity(medicineService.page(new Page(pageInfo.getPage(), pageInfo.getSize()), null).getRecords(), HttpStatus.OK);
	}
	@PutMapping(value = "medicine", produces="application/json; charset=UTF-8")
	public ResponseEntity updateMedicine(Medicine medicine){
		if(medicine.getMInventory() < 0) {
			throw new IllegalArgumentException("库存量不能为负数");
		}
		medicineService.updateById(medicine);
		return new ResponseEntity(HttpStatus.OK);
	}
	@DeleteMapping(value = "medicine/{id}")
	public ResponseEntity insertMedicine(@PathVariable Long id){
		medicineService.removeById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
