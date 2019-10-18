package com.medicine.manager.service.impl;

import com.medicine.manager.bean.JwtUser;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.service.JwtUserService;
import com.medicine.manager.service.JwtPermissionService;
import com.medicine.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lenvaco
 * @date 2019/9/27 9:26
 */
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
			return createJwtUser(user);
		}
	}

	private UserDetails createJwtUser(UserDTO user) {
		return new JwtUser(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				user.getEmail(),
				user.getPhone(),
				jwtPermissionService.mapToGrantedAuthorities(user),
				user.getGmt_modified(),
				user.getGmt_create()
		);
	}
}
