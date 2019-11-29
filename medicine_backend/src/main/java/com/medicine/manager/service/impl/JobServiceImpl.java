package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.JobQuery;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.JobDTO;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.dao.DeptDao;
import com.medicine.manager.dao.JobDao;
import com.medicine.manager.model.Dept;
import com.medicine.manager.model.Job;
import com.medicine.manager.model.User;
import com.medicine.manager.service.JobService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/25 10:29
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends ServiceImpl<JobDao, Job> implements JobService {

	@Autowired
	private DeptDao deptDao;
	@Autowired
	private UserService userService;
	@Override
	public Object queryAll(JobQuery jobQuery, PageInfo pageInfo) {
		QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
		if (jobQuery != null && jobQuery.getName() != null) {
			queryWrapper.like("name", jobQuery.getName());
		}
		if(jobQuery.getDeptId() != null) {
			queryWrapper.eq("d_id", jobQuery.getDeptId());
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(Job job) {
		job.setCreateTime(new Date());
		boolean result = save(job);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 新建岗位[" + job.getName()  + "]成功!");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 新建岗位操作失败!");
		}
		return result;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateJob(Job job) {
		UpdateWrapper<Job>updateWrapper = new UpdateWrapper();
		updateWrapper.set("sort", job.getSort())
				.set("name", job.getName())
				.set("d_id", job.getDId())
				.eq("id", job.getId());
		boolean result = update(updateWrapper);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 更新岗位Id[" + job.getId()  + "]成功!");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 更新岗位Id[" + job.getId()  + "]操作失败!");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(Long id) {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("j_id", null);
		updateWrapper.eq("j_id", id);
		if(removeById(id) && userService.update(updateWrapper)) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 删除岗位Id[" + id + "]成功!");
			return true;
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 删除岗位Id[" + id + "]操作失败!");
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByDid(Long dId) {
		UpdateWrapper<Job> jobUpdateWrapper  = new UpdateWrapper<>();
		jobUpdateWrapper.eq("d_id", dId);
		List<Job> jobs = list(jobUpdateWrapper);
		jobs.forEach(job -> {
			this.deleteById(job.getId());
		});
		return true;
	}
}
