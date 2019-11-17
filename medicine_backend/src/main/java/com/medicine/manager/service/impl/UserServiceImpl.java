package com.medicine.manager.service.impl;

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
import com.medicine.manager.model.UserRole;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserRoleService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private static final String DEFAULT_PASSWORD = "abc123456";
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
			userQueryWrapper.or(wrapper -> wrapper.like("name", userQuery.getBlurry()).or().like("email", userQuery.getBlurry()));
		}
		if(userQuery.getDeptId() != null) {
			userQueryWrapper.eq("d_id", userQuery.getDeptId());
		}
		if(userQuery.getEnabled() != null) {
			userQueryWrapper.eq("enabled", userQuery.getEnabled());
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

		map.put("totalElements", page.getTotal());
		return map;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(UserDTO userDTO) {
		//username 和 email 都不能已经被使用
		String username  = userDTO.getUsername();
		if(null != userDao.findByUsername(username)) {
			throw new EntityExistException(User.class, "username", username);
		}
		String email = userDTO.getEmail();
		if(null != userDao.findByEmail(email)) {
			throw new EntityExistException(User.class, "email", email);
		}
		User user = userDTO.toUser();
//		user.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
		Date now = new Date();
		user.setModifyTime(now);
		user.setCreateTime(now);
		user.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
		save(user);
		Long id = findByUsername(username).getId();
		userRoleService.saveBatch(userDTO.getRoles().stream().map(
				roleSmallDTO -> new UserRole(id, roleSmallDTO.getId())
		).collect(Collectors.toList()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(UserDTO userDTO) {
		String username = userDTO.getUsername();
		User user = userDao.findByEmail(userDTO.getEmail());
		//判断是否存在这个个用户 ，并且根据username查出来的user_id是否相同
		if(null == user && !user.getUId().equals(userDTO.getId())) {
			throw new EntityNotFoundException(User.class, "username", username);
		}
		UpdateWrapper<User>  updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("modify_time", new Date());
		updateWrapper.set("email", userDTO.getEmail());
		updateWrapper.set("phone", userDTO.getPhone());
		updateWrapper.set("name", userDTO.getName());
		updateWrapper.set("j_id", userDTO.getJob().getId());
		updateWrapper.set("d_id", userDTO.getDept().getId());
		if( userDTO.getAddress() != null) {
			updateWrapper.set("address", userDTO.getAddress());
		}
		if(userDTO.getSex() != null) {
			updateWrapper.set("sex", userDTO.getSex());
		}
		if(userDTO.getAddress() != null) {
			updateWrapper.set("address", userDTO.getAddress());
		}
		//默认我们根据user_id来修改信息
		updateWrapper.eq("u_id", userDTO.getId());
		update(updateWrapper);
		UpdateWrapper<UserRole> userRoleUpdateWrapper = new UpdateWrapper<>();
		userRoleUpdateWrapper.eq("u_id", userDTO.getId());
		userRoleService.remove(userRoleUpdateWrapper);
		userRoleService.saveBatch(userDTO.getRoles().stream().map(roleSmallDTO -> new UserRole(userDTO.getId(), roleSmallDTO.getId())).collect(Collectors.toList()));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePasswordByUsername(String username, String newPassword) {
		UpdateWrapper updateWrapper= new UpdateWrapper();
		updateWrapper.set("password", newPassword);
		updateWrapper.eq("username", username);
		return update(updateWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserById(Long id) {
		UpdateWrapper<UserRole>updateWrapper = new UpdateWrapper();
		updateWrapper.eq("u_id", id);
		userRoleService.remove(updateWrapper);
		removeById(id);
	}
}
