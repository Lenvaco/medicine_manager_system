package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.bean.dto.DeptDTO;
import com.medicine.manager.dao.DeptDao;
import com.medicine.manager.model.Dept;
import com.medicine.manager.service.DeptService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lenvaco
 * @date 2019/10/24 11:01
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {

	private static final String PARENT_ID = "0";

	@Autowired
	private DeptDao deptDao;

	@Override
	public List<DeptDTO> queryAll(DeptQuery deptQuery){
		QueryWrapper<Dept>queryWrapper = new QueryWrapper();
		queryWrapper.orderByDesc("parent_id");
		if(deptQuery != null) {
			queryWrapper.like("name", deptQuery.getDeptName());
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
	public Object buildTree(List<DeptDTO> deptDTOS) {
		Set<DeptDTO> trees = new LinkedHashSet<>();
		Object[] depts = deptDTOS.toArray();

		for (int i = 0; i < deptDTOS.size(); i++) {
			DeptDTO deptDTO= deptDTOS.get(i);
			List<DeptDTO> children = null;
			for (int j = 0; j < deptDTOS.size(); j++) {
				if(deptDTO.getId().equals(deptDTOS.get(j).getParentId())) {
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
		}
		for (int i = 0; i < depts.length; i++) {
			System.err.println((DeptDTO)depts[i]);
		}
		Map map = new HashMap();
		map.put("totalElements",deptDTOS !=null ? deptDTOS.size() : 0);
		map.put("content",CollectionUtils.isEmpty(trees)?deptDTOS:trees);
		return map;
	}
}
