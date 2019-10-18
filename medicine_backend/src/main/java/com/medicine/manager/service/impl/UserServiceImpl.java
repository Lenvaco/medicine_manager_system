package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.common.utils.ValidationUtil;
import com.medicine.manager.dao.RoleDao;
import com.medicine.manager.exception.EntityExistException;
import com.medicine.manager.exception.EntityNotFoundException;
import com.medicine.manager.model.Role;
import com.medicine.manager.model.User;
import com.medicine.manager.dao.UserDao;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

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

public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

	@Autowired
	private RoleService roleService;

	@Override
	public UserDTO findByUsername(String username) {
		QueryWrapper queryWrapper = new QueryWrapper();
		if(ValidationUtil.isEmail(username)) {
			queryWrapper.eq("email", username);
		} else {
			queryWrapper.eq("username", username);
		}
		User user = getOne(queryWrapper, true);
		if (user == null) {
			throw new EntityNotFoundException(User.class,"username", username);
		} else {
			return new UserDTO(user);
		}
	}

	@Override
	public List<UserDTO> queryAllUsers(IPage iPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		return Lists.transform(super.page(iPage).getRecords(), new Function<User, UserDTO>() {
			@Nullable
			@Override
			public UserDTO apply(@Nullable User user){
				//根据user_id查询用户角色权限
				queryWrapper.eq("u_id", user.getUId());
				UserDTO userDTO = new UserDTO(user);
				userDTO.setRole(roleService.getOne(queryWrapper));
				return userDTO;
			}
		});
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
		user.setGmtModified(now);
		user.setGmtCreate(now);
		return super.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(User user, Wrapper<User> updateWrapper) {
		String username = user.getUsername();
		UserDTO userDto = findByUsername(username);
		//判断是否存在整个用户 ，并且根据username查出来的user_id是否相同
		if(null == userDto && !userDto.getId().equals(user.getUId())) {
			throw new EntityNotFoundException(User.class, "username", username);
		}
		updateWrapper = new UpdateWrapper<>();
		((UpdateWrapper<User>) updateWrapper).set("gmt_modified", new Date());
		((UpdateWrapper<User>) updateWrapper).set("email", user.getEmail());
		if (user.getBirthday() != null){
			((UpdateWrapper<User>) updateWrapper).set("birthday", user.getBirthday());
		}
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
		if(user.getIdCard() != null) {
			((UpdateWrapper<User>) updateWrapper).set("card", user.getIdCard());
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
