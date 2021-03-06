package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.bean.dto.DeptDTO;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.dao.DeptDao;
import com.medicine.manager.model.Dept;
import com.medicine.manager.model.Job;
import com.medicine.manager.model.User;
import com.medicine.manager.service.DeptService;
import com.medicine.manager.service.JobService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lenvaco
 * @date 2019/10/24 11:01
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {

	private static final String PARENT_ID = "0";
	@Autowired
	private UserService userService;
	@Autowired
	private JobService jobService;
	@Override
	public List<DeptDTO> queryAll(DeptQuery deptQuery){
		QueryWrapper<Dept>queryWrapper = new QueryWrapper();
		queryWrapper.orderByDesc("parent_id");
		if(deptQuery != null && deptQuery.getName() != null) {
			queryWrapper.like("name", deptQuery.getName());
		}
		return Lists.transform(this.list(queryWrapper), new Function<Dept, DeptDTO>() {
			@Nullable
			@Override
			public DeptDTO apply(@Nullable Dept input) {
				return new DeptDTO(input);
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeptDTO create(Dept dept) {
		dept.setCreateTime(new Date());
		if (save(dept)) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 新建部门[" + dept.getName()  + "]成功!");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 新建部门[" + dept.getName()  + "]操作失败!" );
		}
		return new DeptDTO(dept);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDept(Dept dept) {
		UpdateWrapper<Dept> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("name", dept.getName());
		updateWrapper.set("parent_id", dept.getParentId());
		updateWrapper.eq("id", dept.getId());
		 if(update(updateWrapper)) {
		 	log.info("用户编号为" + SecurityUtil.getUserId() + " 更新部门[" + dept.getName()  + "]成功!");
		 } else {
			 log.warn("用户编号为" + SecurityUtil.getUserId() + " 更新部门[" + dept.getName()  + "]操作失败!");
		 }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(Long id) {
		UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.set("d_id", null).eq("d_id", id);
		userService.update(userUpdateWrapper);
		jobService.deleteByDid(id);
		if(removeById(id)) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 删除部门Id [" + id  + "]成功!");
			return true;
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 删除部门Id [" + id  + "]操作失败!");
			return false;
		}
	}

	@Override
	public Object buildTree(List<DeptDTO> deptDTOS) {
		Set<DeptDTO> trees = new LinkedHashSet<>();
		Set<DeptDTO> deptSet= new LinkedHashSet<>();
		Object[] depts = deptDTOS.toArray();
		boolean isChild;
		for (int i = 0; i < deptDTOS.size(); i++) {
			isChild = false;
			DeptDTO deptDTO= deptDTOS.get(i);
			List<DeptDTO> children = null;
			for (int j = 0; j < deptDTOS.size(); j++) {
				if(deptDTO.getId().equals(deptDTOS.get(j).getParentId())) {
					isChild = true;
					if (children == null) {
						children = new ArrayList<>();
					}
					children.add((DeptDTO)depts[j]);
				}
			}
			((DeptDTO)depts[i]).setChildren(children);
			if (PARENT_ID.equals(deptDTO.getParentId().toString())) {
				trees.add(((DeptDTO)depts[i]));
			}
			if(isChild) {
				deptSet.add((DeptDTO)depts[i]);
			}
		}
		if (CollectionUtils.isEmpty(trees)) {
			trees = deptSet;
		}
		Map map = new HashMap();
		map.put("totalElements",deptDTOS !=null ? deptDTOS.size() : 0);
		map.put("content",CollectionUtils.isEmpty(trees)?deptDTOS:trees);
		return map;
	}
}
