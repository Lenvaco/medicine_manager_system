package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicine.manager.model.Role;
import com.medicine.manager.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author lenvaco
 * @date 2019/9/29 10:14
 */
@Data
public class UserDTO implements Serializable {

	@ApiModelProperty(hidden = true)
	private Long id;

	private String username;

	private String password;

	private String name;

	private String phone;

	private String sex;

	private String email;

	private String address;

	private Boolean enabled;

	@ApiModelProperty(hidden = true)
	private Set<RoleSmallDTO> roles;

	private Date modifyTime;

	private Date createTime;

	private JobSmallDTO job;

	private DeptSmallDTO dept;


	public UserDTO(User user) {
		this.id = user.getUId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.sex = user.getSex();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.modifyTime = user.getModifyTime();
		this.createTime = user.getCreateTime();
		this.job = new JobSmallDTO(user.getJob());
		this.dept = new DeptSmallDTO(user.getDept());
		this.enabled = user.getEnabled();
//		this.getRoles() = user.getRoles().stream().forEach(role -> return new RoleDTO(role));
	}
}
