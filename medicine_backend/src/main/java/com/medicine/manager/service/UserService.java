package com.medicine.manager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface UserService extends IService<User> {
	UserDTO findByUsername(String username);

	List queryAllUsers(IPage iPage);

	boolean updatePasswordByUsername(String username, String newPassword);
}
