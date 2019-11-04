package com.medicine.manager.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medicine.manager.bean.UserQuery;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.model.User;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@CacheConfig(cacheNames = "user")
public interface UserService extends IService<User> {

	boolean updateUser(User user, Wrapper<User> updateWrapper);

	UserDTO findByUsername(String username);

	Object queryAllUsers(UserQuery userQuery, IPage iPage);

	boolean updatePasswordByUsername(String username, String newPassword);
}
