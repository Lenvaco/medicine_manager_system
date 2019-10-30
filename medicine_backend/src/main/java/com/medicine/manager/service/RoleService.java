package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.dto.RoleDTO;
import com.medicine.manager.model.Permission;
import com.medicine.manager.model.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface RoleService extends IService<Role> {
	Set<Role> findByUserId(Long userId);

	Object queryAll(String burry, PageInfo pageInfo);

	boolean updatePermission(Role role);

	boolean updateMenu(Role role);

}
