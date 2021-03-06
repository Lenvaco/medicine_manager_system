package com.medicine.manager.service;

import com.medicine.manager.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface PermissionService extends IService<Permission> {
	List<Permission> findByParentId(Long parentId);
	Object getPermissionTree(List<Permission> permissions);

}
