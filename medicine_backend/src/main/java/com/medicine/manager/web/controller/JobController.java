package com.medicine.manager.web.controller;

import com.medicine.manager.bean.JobQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenvaco
 * @date 2019/10/28 16:44
 */
@RestController
@RequestMapping("api")
public class JobController {

	@Autowired
	private JobService jobService;
	@GetMapping(value = "/job")
	@PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_SELECT','USER_ALL','USER_SELECT')")
	public ResponseEntity getJobs(JobQuery jobQuery, PageInfo pageInfo){
		if(pageInfo == null) {
			pageInfo = new PageInfo();
		}
		return new ResponseEntity(jobService.queryAll(jobQuery, pageInfo), HttpStatus.OK);
	}
}
