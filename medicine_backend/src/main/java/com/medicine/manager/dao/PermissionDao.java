package com.medicine.manager.dao;

import com.medicine.manager.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface PermissionDao extends BaseMapper<Permission> {
	Set<Permission> selectAllByRoleId(Long rId);
}
