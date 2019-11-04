package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.bean.dto.DeptDTO;
import com.medicine.manager.model.Dept;

import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/24 10:59
 */
public interface DeptService extends IService<Dept> {
	Object buildTree(List<DeptDTO> deptDTOS);

	List<DeptDTO> queryAll(DeptQuery deptQuery);

	DeptDTO create(Dept dept);

	void updateDept(Dept dept);
}
