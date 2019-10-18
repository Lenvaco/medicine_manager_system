package com.medicine.manager.web.controller;


import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.exception.BadRequestException;
import com.medicine.manager.model.User;
import com.medicine.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Controller
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

	@GetMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
	public ResponseEntity getUsers(PageInfo pageInfo){
		return new ResponseEntity(userService.queryAllUsers(new Page(pageInfo.getPageNo(), pageInfo.getPageSize())), HttpStatus.OK);
	}


	@PostMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
	public ResponseEntity createUser( @RequestBody User user){
		if(user == null || user.getUId() == null  || user.getUsername() == null || user.getEmail() == null || (!Validator.isEmail(user.getEmail())) ) {
			throw new BadRequestException("请求参数错误!");
		}
		return new ResponseEntity(userService.save(user),HttpStatus.CREATED);
	}

	@PutMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_UPDATE')")
	public ResponseEntity updateUser( @RequestBody User user){
		//判断基本必填信息是否正常
		if(user == null || user.getUId() == null  || user.getUsername() == null
				|| user.getEmail() == null || (!Validator.isEmail(user.getEmail()))
				|| user.getPassword() == null || !Pattern.matches(PASSWORD_REGEX, user.getPassword())) {
			throw new BadRequestException("请求参数错误!");
		}
		userService.update(user, null);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	@DeleteMapping(value = "/users/{roleId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity delete(@PathVariable Long roleId){
		//根据用户id删除角色
		userService.removeById(roleId);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value = "/users/changePsd")
	public ResponseEntity updatePass(@RequestParam(name = "oldPassword")String oldPassword, @RequestParam("newPassword") String newPassword){
		//获取当前用户信息
		UserDetails userDetails = SecurityUtil.getUserDetails();
		//判断旧密码是否匹配
		if(!bCryptPasswordEncoder.matches(oldPassword, userDetails.getPassword())) {
			throw new BadRequestException("修改失败，旧密码错误");
		}
		//判断新旧密码是否相同
		if(bCryptPasswordEncoder.matches(newPassword, userDetails.getPassword())) {
			throw new BadRequestException("新密码不能与旧密码相同");
		}
		userService.updatePasswordByUsername(userDetails.getUsername(), bCryptPasswordEncoder.encode(newPassword));
		return new ResponseEntity(HttpStatus.OK);
	}
}
