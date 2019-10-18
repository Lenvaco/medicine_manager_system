package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.dao.RoleDao;
import com.medicine.manager.exception.EntityExistException;
import com.medicine.manager.exception.EntityNotFoundException;
import com.medicine.manager.model.Menu;
import com.medicine.manager.model.Permission;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Override
	public Role findByUserId(Long userId) {
		return roleDao.findByUserId(userId);
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean save(Role role) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("role_name", role.getRoleName());
		if(super.getOne(queryWrapper) != null ) {
			throw new EntityExistException(Role.class,"username",role.getRoleName());
		}
		return super.save(role);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(Role role, Wrapper<Role> updateWrapper) {
		if(getById(role.getRoleId()) == null ) {
			 throw new EntityNotFoundException(Role.class, "role", role.getRoleId());
		}
		if(updateWrapper == null ){
			updateWrapper = new UpdateWrapper<>();
			if(role.getRoleName() != null){
				((UpdateWrapper<Role>) updateWrapper).set("role_name", role.getRoleName());
			}
			if(role.getRoleDescription() == null) {
				((UpdateWrapper<Role>) updateWrapper).set("role_description", role.getRoleDescription() );
			}
			((UpdateWrapper<Role>) updateWrapper).set("gmt_modified", new Date());
		}
		return update(updateWrapper);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePermission(Role role) {
		Long roleId = role.getRoleId();
		roleDao.deletePermission(roleId);
		final Set<Permission> permissions = role.getPermissions();
		if(permissions != null && permissions.size() > 0) {
			roleDao.insertPermission(roleId, permissions);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMenu(Role role) {
		Long roleId =  role.getRoleId();
		roleDao.deleteMenu(roleId);
		final Set<Menu> menus = role.getMenus();
		if(menus != null || menus.size() > 0) {
			roleDao.insertMenu(roleId, menus);
		}
		return true;
	}
}
