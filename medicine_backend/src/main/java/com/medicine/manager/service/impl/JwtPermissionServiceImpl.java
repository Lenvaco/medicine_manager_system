package com.medicine.manager.service.impl;

import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.JwtPermissionService;
import com.medicine.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lenvaco
 * @date 2019/9/29 11:30
 */
@Service
public class JwtPermissionServiceImpl implements JwtPermissionService {
	@Autowired
	private RoleService roleService;
	@Override
	public Collection<GrantedAuthority> mapToGrantedAuthorities(UserDTO user) {
		final Role role = roleService.findByUserId(user.getId());
		return role.getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
				.collect(Collectors.toList());
	}
}
