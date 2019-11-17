package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.dao.UserRoleDao;
import com.medicine.manager.model.UserRole;
import com.medicine.manager.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author lenvaco
 * @date 2019/11/11 13:58
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
}
