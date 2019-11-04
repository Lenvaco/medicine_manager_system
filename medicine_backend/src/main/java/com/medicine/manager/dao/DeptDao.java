package com.medicine.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.manager.model.Dept;
import com.medicine.manager.model.Job;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 * @author lenvaco
 * @date 2019/10/21 0:12
 */
public interface DeptDao extends BaseMapper<Dept> {
	Dept findById(Long d_id);
	@Select("select name from dept where id = #{id}")
	List<String> findNameById(Long id);
}

