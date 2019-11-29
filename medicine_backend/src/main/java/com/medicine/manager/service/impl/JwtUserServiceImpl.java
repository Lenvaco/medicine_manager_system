package com.medicine.manager.service.impl;

import com.medicine.manager.bean.JwtUser;
import com.medicine.manager.bean.dto.DeptSmallDTO;
import com.medicine.manager.bean.dto.JobSmallDTO;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.JwtUserService;
import com.medicine.manager.service.JwtPermissionService;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author lenvaco
 * @date 2019/9/27 9:26
 */
@Slf4j
@Service(value="jwtUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserServiceImpl implements JwtUserService {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtPermissionService jwtPermissionService;

	@Override
	public UserDetails loadUserByUsername(String username){
		UserDTO user = userService.findByUsername(username);
		if (user == null) {
			throw new BadRequestException("账号不存在");
		} else {
			log.info("用户账号为" + username + "登陆上线");
			return createJwtUser(user);
		}
	}

	private UserDetails createJwtUser(UserDTO user) {
		return new JwtUser(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				user.getName(),
				user.getEmail(),
				user.getPhone(),
				Optional.ofNullable(user.getDept()).map(DeptSmallDTO::getName).orElse(null),
				Optional.ofNullable(user.getJob()).map(JobSmallDTO::getName).orElse(null),
				jwtPermissionService.mapToGrantedAuthorities(user),
				user.getEnabled(),
				user.getModifyTime(),
				user.getCreateTime()
		);
	}
}
