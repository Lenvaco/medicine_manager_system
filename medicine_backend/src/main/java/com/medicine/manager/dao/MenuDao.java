package com.medicine.manager.dao;

import com.medicine.manager.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface MenuDao extends BaseMapper<Menu> {
	Set<Menu> selectAllByRoleId(Long roleId);
}
