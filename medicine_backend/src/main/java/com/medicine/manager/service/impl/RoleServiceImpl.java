package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.RoleDTO;
import com.medicine.manager.dao.RoleDao;
import com.medicine.manager.exception.EntityExistException;
import com.medicine.manager.exception.EntityNotFoundException;
import com.medicine.manager.model.Menu;
import com.medicine.manager.model.Permission;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.RoleService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
	public Set<Role> findByUserId(Long userId) {
		return roleDao.findByUserId(userId);
	}

	@Override
	public Object queryAll(String burry, PageInfo pageInfo) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.and(wrapper -> wrapper.like("name", burry).or().like("remark", burry));
		queryWrapper.ne("level", 1);
		queryWrapper.orderByAsc("sort");
		IPage page = this.page(new Page(pageInfo.getPage(), pageInfo.getSize()), queryWrapper);
		return new HashMap(){
			{
				put("content", Lists.transform(page.getRecords(), new Function<Object, Object>() {
					@Nullable
					@Override
					public Object apply(@Nullable Object input) {
						return new RoleDTO((Role)input);
					}
				}));
				put("totalElements", page.getSize());
			}
		};
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean save(Role role) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", role.getName());
		if(super.getOne(queryWrapper) != null ) {
			throw new EntityExistException(Role.class,"username",role.getName());
		}
		return super.save(role);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(Role role, Wrapper<Role> updateWrapper) {
		if(getById(role.getRId() )== null ) {
			 throw new EntityNotFoundException(Role.class, "role", role.getRId());
		}
		if(updateWrapper == null ){
			updateWrapper = new UpdateWrapper<>();
			if(role.getName() != null){
				((UpdateWrapper<Role>) updateWrapper).set("name", role.getName());
			}
			if(role.getRemark() == null) {
				((UpdateWrapper<Role>) updateWrapper).set("remark", role.getRemark() );
			}
			if(role.getRemark() == null) {
				((UpdateWrapper<Role>) updateWrapper).set("level", role.getLevel() );
			}
		}
		return update(updateWrapper);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePermission(Role role) {
		Long roleId = role.getRId();
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
		Long roleId =  role.getRId();
		roleDao.deleteMenu(roleId);
		final Set<Menu> menus = role.getMenus();
		if(menus != null || menus.size() > 0) {
			roleDao.insertMenu(roleId, menus);
		}
		return true;
	}
}
