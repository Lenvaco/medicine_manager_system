package com.medicine.manager.web.controller;

import com.medicine.manager.service.ChartSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenvaco
 * @date 2019/11/22 10:07
 */
@RestController
@RequestMapping("/api")
public class ChartController {

	@Autowired
	private ChartSummaryService chartSummaryService;

	@GetMapping("chart/count")
	public ResponseEntity getChartCount(){
		return new ResponseEntity(chartSummaryService.getSystemSummary(), HttpStatus.OK);
	}
	@GetMapping("chart/sale")
	public ResponseEntity getChartSale(){
		return new ResponseEntity(chartSummaryService.getWeekSummary(), HttpStatus.OK);
	}
	@GetMapping("chart/medicine")
	public ResponseEntity getChartMedicine(){
		return new ResponseEntity(chartSummaryService.getHotMedicine(), HttpStatus.OK);
	}
}
