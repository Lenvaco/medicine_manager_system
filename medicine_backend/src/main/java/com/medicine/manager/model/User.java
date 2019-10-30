package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User类", description="系统用户表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "u_id", type = IdType.ID_WORKER)
    private Long uId;

    @NotBlank
    @ApiModelProperty(value = "用户账号")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @NotBlank
    @ApiModelProperty(value = "0男1女")
    private String sex;

    @NotBlank
    @Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}",message = "格式错误")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "0禁用1启用")
    private Boolean enabled;

    @ApiModelProperty(value = "更改日期")
    private Date modifyTime;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @TableField(exist = false)
    private  transient Set<Role> roles;

    @TableField(exist = false)
    private transient Job job;

    @TableField(exist = false)
    private transient Dept dept;

    private Long dId;
    private Long jId;
}
