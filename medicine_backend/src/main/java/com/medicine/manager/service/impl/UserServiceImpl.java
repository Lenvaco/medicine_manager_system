package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.UserQuery;
import com.medicine.manager.bean.dto.RoleSmallDTO;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.common.utils.ValidationUtil;
import com.medicine.manager.dao.DeptDao;
import com.medicine.manager.dao.JobDao;
import com.medicine.manager.dao.UserDao;
import com.medicine.manager.exception.EntityExistException;
import com.medicine.manager.exception.EntityNotFoundException;
import com.medicine.manager.model.Role;
import com.medicine.manager.model.User;
import com.medicine.manager.service.DeptService;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private JobDao jobDao;

	@Override
	public UserDTO findByUsername(String username) {

		User user;
		if(ValidationUtil.isEmail(username)) {
			user = userDao.findByEmail(username);
		} else {
			user = userDao.findByUsername(username);
		}
		if (user == null) {
			throw new EntityNotFoundException(User.class,"username", username);
		} else {
			Set<Role> roles = roleService.findByUserId(user.getUId());
			user.setRoles(roles);
			return new UserDTO(user);
		}
	}

	@Override
	public Object queryAllUsers(UserQuery userQuery, IPage iPage) {
		QueryWrapper<User>userQueryWrapper = new QueryWrapper();
		if(userQuery.getId() != null) {
			userQueryWrapper.eq("u_id", userQuery.getId());
		}
		if(userQuery.getBlurry() != null) {
			userQueryWrapper.or(wrapper -> wrapper.like("username", userQuery.getBlurry()).or().like("email", userQuery.getBlurry()));
		}
		if(userQuery.getDeptId() != null) {
			userQueryWrapper.eq("d_id", userQuery.getDeptId());
		}
		userQueryWrapper.orderByAsc("u_id");
		IPage page = super.page(iPage, userQueryWrapper);
		Map map = new HashMap();
		map.put("content", Lists.transform(page.getRecords(), new Function<User, UserDTO>() {
			@Nullable
			@Override
			public UserDTO apply(@Nullable User user){
				user.setDept(deptDao.findById(user.getDId()));
				user.setJob(jobDao.findById(user.getJId()));
				UserDTO userDTO = new UserDTO(user);
				userDTO.setRoles(roleService.findByUserId(user.getUId()).stream().map(role -> new RoleSmallDTO(role)).collect(Collectors.toSet()));
				return userDTO;
			}
		}));

		map.put("totalElements", page.getSize());
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(User user) {
		//username 和 email 都不能已经被使用
		String username  = user.getUsername();
		if(null != findByUsername(username)) {
			throw new EntityExistException(User.class, "username", username);
		}
		String email = user.getEmail();
		if(null != findByUsername(email)) {
			throw new EntityExistException(User.class, "email", email);
		}
		Date now = new Date();
		user.setModifyTime(now);
		user.setCreateTime(now);
		return super.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(User user, Wrapper<User> updateWrapper) {
		String username = user.getUsername();
		UserDTO userDto = findByUsername(username);
		//判断是否存在整个用户 ，并且根据username查出来的user_id是否相同
		if(null == userDto && !userDto.getId().equals(user.getUId())) {
			throw new EntityNotFoundException(User.class, "username", username);
		}
		updateWrapper = new UpdateWrapper<>();
		((UpdateWrapper<User>) updateWrapper).set("modify_time", new Date());
		((UpdateWrapper<User>) updateWrapper).set("email", user.getEmail());
		if (user.getPhone() != null) {
			((UpdateWrapper<User>) updateWrapper).set("phone", user.getPhone());
		}
		if( user.getAddress() != null) {
			((UpdateWrapper<User>) updateWrapper).set("address", user.getAddress());
		}
		if(user.getName() != null) {
			((UpdateWrapper<User>) updateWrapper).set("name", user.getName());
		}
		if(user.getSex() != null) {
			((UpdateWrapper<User>) updateWrapper).set("sex", user.getSex());
		}
		//默认我们根据user_id来修改信息
		((UpdateWrapper<User>) updateWrapper).eq("u_id", user.getUId());
		return super.update(updateWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePasswordByUsername(String username, String newPassword) {
		UpdateWrapper updateWrapper= new UpdateWrapper();
		updateWrapper.set("password", newPassword);
		updateWrapper.eq("username", username);
		return update(updateWrapper);
	}
}
