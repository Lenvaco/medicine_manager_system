package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author lenvaco
 * @date 2019/10/20 23:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dept")
@ApiModel(value="Dept类", description="部门表")
public class Dept extends Model<Dept> {

	private static final long serialVersionUID = 6966459077637312903L;

	@ApiModelProperty(value = "部门id")
	@TableId(value = "d_id", type = IdType.ID_WORKER)
	private Long dId;

	@NotBlank
	@TableField(value="name")
	@ApiModelProperty(value = "部门名")
	private String name;

	@NotNull
	@TableField(value="parent_id")
	@ApiModelProperty(value = "上级部门id")
	private Long parentId;

	@JsonIgnore
	@TableField(exist=false)
	@ApiModelProperty(value = "角色")
	private transient Set<Role> roles;

	@ApiModelProperty(value = "创建时间")
	@TableField(value="create_time")
	private Date createTime;
}
