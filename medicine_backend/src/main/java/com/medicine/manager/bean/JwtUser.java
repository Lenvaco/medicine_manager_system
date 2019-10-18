package com.medicine.manager.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author lenvaco
 * @date 2019/9/23 18:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="JwtUser对象", description="")
public class JwtUser implements UserDetails {
	@JsonIgnore
	@ApiModelProperty(value = "角色id")
	private Long id;

	@ApiModelProperty(value = "角色账号")
	private String username;

	@JsonIgnore
	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "电话")
	private String phone;

	@JsonIgnore
	@ApiModelProperty(value = "权限")
	private Collection<GrantedAuthority> authorities;

	@JsonIgnore
	private Date updateTime;

	private Date createTime;


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Collection getRoles() {
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
	}
}
