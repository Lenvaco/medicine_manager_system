package com.medicine.manager.dao;

import com.medicine.manager.model.Menu;
import com.medicine.manager.model.Permission;
import com.medicine.manager.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface RoleDao extends BaseMapper<Role> {

	Role findByUserId(Long userId);


	boolean insertPermission(Long roleId, Set<Permission> permissions);

	boolean deletePermission(Long roleId);

	boolean insertMenu(Long roleId, Set<Menu> menus);

	boolean deleteMenu(Long roleId);
}
