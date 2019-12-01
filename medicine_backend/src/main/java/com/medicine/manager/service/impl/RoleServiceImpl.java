package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.RoleDTO;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.dao.MenuDao;
import com.medicine.manager.dao.PermissionDao;
import com.medicine.manager.dao.RoleDao;
import com.medicine.manager.exception.EntityExistException;
import com.medicine.manager.exception.EntityNotFoundException;
import com.medicine.manager.model.*;
import com.medicine.manager.service.RoleMenuService;
import com.medicine.manager.service.RolePermissionService;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private UserRoleService userRoleService;

	@Override
	public Role findByRoleId(Long id) {
		Role role = getById(id);
		role.setPermissions(permissionDao.selectAllByRoleId(role.getId()));
		role.setMenus(menuDao.selectAllByRoleId(role.getId()));
		return role;
	}

	@Override
	public Set<Role> findByUserId(Long userId) {
		return this.baseMapper.findByUserId(userId);
	}

	@Override
	public Object queryAll( PageInfo pageInfo) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("level");
		IPage page = this.page(new Page(pageInfo.getPage(), pageInfo.getSize()), queryWrapper);
		return new HashMap(){
			{
				put("content", Lists.transform(page.getRecords(), new Function<Object, Object>() {
					@Nullable
					@Override
					public Object apply(@Nullable Object input) {
						Role role = (Role) input;
						role.setMenus(menuDao.selectAllByRoleId(role.getId()));
						role.setPermissions(permissionDao.selectAllByRoleId(role.getId()));
						return new RoleDTO(role);
					}
				}));
				put("totalElements", page.getTotal());
			}
		};
	}
	@Override
	public Object queryAll(String blurry, PageInfo pageInfo) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		if(blurry != null){
			queryWrapper.like("name",blurry).or().like("remark", blurry);
		}
		queryWrapper.orderByAsc("level");
		IPage page = this.page(new Page(pageInfo.getPage(), pageInfo.getSize()), queryWrapper);
		return new HashMap(){
			{
				put("content", Lists.transform(page.getRecords(), new Function<Object, Object>() {
					@Nullable
					@Override
					public Object apply(@Nullable Object input) {
						Role role = (Role) input;
						role.setMenus(menuDao.selectAllByRoleId(role.getId()));
						role.setPermissions(permissionDao.selectAllByRoleId(role.getId()));
						return new RoleDTO(role);
					}
				}));
				put("totalElements", page.getTotal());
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
		role.setCreateTime(new Date());
		boolean result = super.save(role);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + "增加角色[" + role.getName() +"]成功");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "增加角色["+ role.getName() +"]操作失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateRole(Role role) {
		UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
		if(getById(role.getId()) == null ) {
			 throw new EntityNotFoundException(Role.class, "role", role.getId());
		}
		updateWrapper.set("name", role.getName());
		updateWrapper.set("remark", role.getRemark());
		updateWrapper.set("level", role.getLevel() );
		updateWrapper.eq("id", role.getId());
		boolean result = update(updateWrapper);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + "更新角色[" + role.getId() +"]成功");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "更新角色["+ role.getId() +"]操作失败");
		}
		return result;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePermission(Role role) {
		UpdateWrapper<RolePermission> updateWrapper = new UpdateWrapper<>();
		Long roleId = role.getId();
		updateWrapper.eq("r_id", roleId);
		rolePermissionService.remove(updateWrapper);
		final Set<Permission> permissions = role.getPermissions();
		if(permissions != null && permissions.size() > 0) {
			List<RolePermission> rolePermissionList = new ArrayList<>();
			permissions.forEach(permission -> {
				rolePermissionList.add(new RolePermission(roleId, permission.getId()));
			});
			rolePermissionService.saveBatch(rolePermissionList);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMenu(Role role) {
		UpdateWrapper<RoleMenu> updateWrapper = new UpdateWrapper<>();
		Long roleId = role.getId();
		updateWrapper.eq("r_id", roleId);
		roleMenuService.remove(updateWrapper);
		final Set<Menu> menus = role.getMenus();
		if(menus != null && menus.size() > 0) {
			List<RoleMenu> roleMenuList = new ArrayList<>();
			menus.forEach(menu -> {
				roleMenuList.add(new RoleMenu(roleId, menu.getId()));
			});
			roleMenuService.saveBatch(roleMenuList);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeRoleById(Long roleId) {
		UpdateWrapper<RoleMenu> roleMenuUpdateWrapper = new UpdateWrapper<>();
		roleMenuUpdateWrapper.eq("r_id", roleId);
		UpdateWrapper<RolePermission>  rolePermissionUpdateWrapper = new UpdateWrapper<>();
		rolePermissionUpdateWrapper.eq("r_id", roleId);
		UpdateWrapper<UserRole> userRoleUpdateWrapper = new UpdateWrapper<>();
		userRoleUpdateWrapper.eq("r_id", roleId);
		roleMenuService.remove(roleMenuUpdateWrapper);
		rolePermissionService.remove(rolePermissionUpdateWrapper);
		userRoleService.remove(userRoleUpdateWrapper);
		if(removeById(roleId)) {
			log.info("用户编号为" + SecurityUtil.getUserId() + "删除角色[" + roleId +"]成功");
			return true;
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "删除角色[" + roleId +"]操作失败");
			return false;
		}
	}
}
