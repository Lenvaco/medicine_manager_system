package com.medicine.manager.service;

import com.medicine.manager.bean.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author lenvaco
 * @date 2019/9/29 11:29
 */
public interface JwtPermissionService {
	Collection<GrantedAuthority> mapToGrantedAuthorities(UserDTO user);
}
