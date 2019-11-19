package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.UserPassVo;
import com.medicine.manager.bean.UserQuery;
import com.medicine.manager.bean.dto.UserDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
	public ResponseEntity getUsers(UserQuery userQuery, PageInfo pageInfo){
		return new ResponseEntity(userService.queryAllUsers(userQuery, new Page(pageInfo.getPage(), pageInfo.getSize())), HttpStatus.OK);
	}


	@PostMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
	public ResponseEntity createUser(@Validated @RequestBody UserDTO userDTO){
		userService.saveUser(userDTO);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping(value = "/users")
	@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
	public ResponseEntity updateUser(@Validated @RequestBody UserDTO userDto){
		userService.updateUser(userDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	@DeleteMapping(value = "/users/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity delete(@PathVariable Long userId){
		//根据用户id删除角色
		userService.deleteUserById(userId);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value = "/users/changePsd")
	public ResponseEntity updatePass(@RequestBody UserPassVo userPassVo){
		//获取当前用户信息
		UserDetails userDetails = SecurityUtil.getUserDetails();
		//判断旧密码是否匹配
		if(!bCryptPasswordEncoder.matches(userPassVo.getOldPassword(), userDetails.getPassword())) {
			throw new BadRequestException("修改失败，旧密码错误");
		}
		//判断新旧密码是否相同
		if(bCryptPasswordEncoder.matches(userPassVo.getNewPassword(), userDetails.getPassword())) {
			throw new BadRequestException("新密码不能与旧密码相同");
		}
		userService.updatePasswordByUsername(userDetails.getUsername(), bCryptPasswordEncoder.encode(userPassVo.getNewPassword()));
		return new ResponseEntity(HttpStatus.OK);
	}
}
