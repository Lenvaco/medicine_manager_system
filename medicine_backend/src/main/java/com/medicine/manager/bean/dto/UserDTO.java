package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicine.manager.model.Role;
import com.medicine.manager.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
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

	@JsonIgnore
	private String password;

	private String name;

	private String phone;

	private String sex;

	private String email;

	private String address;

	private Date birthday;

	@ApiModelProperty(hidden = true)
	private Role role;

	@JsonIgnore
	private Date gmt_modified;

	private Date gmt_create;

	public UserDTO(User user) {
		this.id = user.getUId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.sex = user.getSex();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.birthday = user.getBirthday();
		this.gmt_modified = user.getGmtModified();
		this.gmt_create = user.getGmtCreate();
	}
}
