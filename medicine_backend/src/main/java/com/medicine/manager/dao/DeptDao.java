package com.medicine.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.manager.model.Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 * @author lenvaco
 * @date 2019/10/21 0:12
 */
public interface DeptDao extends BaseMapper<Dept> {
	Dept findByDId(Long d_id);
	@Select("select name from dept where d_id = #{id}")
	List<String> findNameById(Long id);
}

