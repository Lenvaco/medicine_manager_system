package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.JobQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.JobDTO;
import com.medicine.manager.dao.DeptDao;
import com.medicine.manager.dao.JobDao;
import com.medicine.manager.model.Job;
import com.medicine.manager.service.JobService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/25 10:29
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobDao, Job> implements JobService {

	@Autowired
	private DeptDao deptDao;
	@Override
	public Object queryAll(JobQuery jobQuery, PageInfo pageInfo) {
		QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
		if (jobQuery != null && jobQuery.getName() != null) {
			queryWrapper.likeLeft("name", jobQuery.getName());
		}
		queryWrapper.orderByAsc("sort");
		IPage<Job> page = this.page(new Page<>(pageInfo.getPage(), pageInfo.getSize()), queryWrapper);
		List<Object> objectList = Lists.transform(page.getRecords(), new Function<Job, Object>() {
			@Nullable
			@Override
			public Object apply(@Nullable Job input) {
				input.setDept(deptDao.findById(input.getDId()));
				return new JobDTO(input);
			}
		});
		return new HashMap() {
			{
				put("content", objectList);
				put("totalElements", page.getTotal());
			}
		};
	}
}
