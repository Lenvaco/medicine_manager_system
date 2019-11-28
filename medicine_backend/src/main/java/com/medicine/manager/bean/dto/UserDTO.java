package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicine.manager.model.Role;
import com.medicine.manager.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author lenvaco
 * @date 2019/9/29 10:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 5491128699082883311L;
	@ApiModelProperty(hidden = true)
	private Long id;
	@NotBlank(message = "账号不能留空")
	private String username;

	private String password;
	@NotBlank(message = "姓名不能留空")
	@Length(min = 2, max = 14)
	private String name;
	@NotBlank(message = "电话不能留空")
	@Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "电话格式出错")
	private String phone;

	private String sex;
	@NotBlank(message = "邮箱不能留空")
	@Pattern(regexp = "^([a-zA-Z0-9]+[-_.]?)+@[a-zA-Z0-9]+\\.[a-z]+$", message = "邮箱格式出错")
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
	@JsonIgnore
	public User toUser() {
		return new User(id, username, password, name, phone, sex, email, address, enabled, dept.getId(), job.getId());
	}
}
