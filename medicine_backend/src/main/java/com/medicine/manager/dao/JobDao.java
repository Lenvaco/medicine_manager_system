package com.medicine.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.manager.model.Job;

/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 * @author lenvaco
 * @date 2019/10/21 0:12
 */
public interface JobDao extends BaseMapper<Job> {
	Job findById(Long id);
}