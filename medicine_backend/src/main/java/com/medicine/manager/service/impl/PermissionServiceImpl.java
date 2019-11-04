package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.manager.model.Permission;
import com.medicine.manager.dao.PermissionDao;
import com.medicine.manager.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {
	@Override
	public List<Permission> findByParentId(Long parentId) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return this.list(queryWrapper);
	}
	@Override
	public Object getPermissionTree(List<Permission> permissions) {
		List<Map<String,Object>> list = new LinkedList<>();
		permissions.forEach(permission -> {
					if (permission!=null){
						List<Permission> permissionList = this.findByParentId(permission.getPId());
						Map<String,Object> map = new HashMap<>();
						map.put("id",permission.getPId());
						map.put("label",permission.getAlias());
						if(permissionList!=null && permissionList.size()!=0){
							map.put("children",getPermissionTree(permissionList));
						}
						list.add(map);
					}
				}
		);
		return list;
	}


}
