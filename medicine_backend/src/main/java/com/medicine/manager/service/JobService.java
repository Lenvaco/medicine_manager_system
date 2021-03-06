package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medicine.manager.bean.JobQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.model.Dept;
import com.medicine.manager.model.Job;

/**
 * @author lenvaco
 * @date 2019/10/25 10:26
 */
public interface JobService extends IService<Job> {
	Object queryAll(JobQuery jobQuery, PageInfo pageInfo);

	boolean create(Job job);

	boolean updateJob(Job job);

	boolean deleteById(Long id);

	boolean deleteByDid(Long dId);
}
