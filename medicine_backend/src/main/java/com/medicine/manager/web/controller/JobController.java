package com.medicine.manager.web.controller;

import com.medicine.manager.bean.JobQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.Job;
import com.medicine.manager.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping(value = "/job")
	@PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_CREATE')")
	public ResponseEntity create(@Validated @RequestBody Job job){
		if (job.getId() != null) {
			throw new BadRequestException("A new job cannot already have an ID");
		}
		jobService.create(job);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping(value = "/job")
	@PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_EDIT')")
	public ResponseEntity update( @RequestBody Job job){
		jobService.updateJob(job);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/job/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_DELETE')")
	public ResponseEntity delete(@PathVariable Long id){
		jobService.removeById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
