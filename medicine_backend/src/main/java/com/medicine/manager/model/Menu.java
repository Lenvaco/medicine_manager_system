package com.medicine.manager.model;

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
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu")
@ApiModel(value="Menu类", description="菜单表")
public class Menu extends Model<Menu> {

	private static final long serialVersionUID = 1669812014417725675L;

	@ApiModelProperty(value = "菜单id")
    @TableId(value = "m_id", type = IdType.ID_WORKER)
    private Long mId;

	@NotBlank
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "页面组件")
    private String component;

	@NotNull
    @ApiModelProperty(value = "菜单序号")
    private Long sort;

	@ApiModelProperty(value = "模块图标")
	private String icon;

	@ApiModelProperty(value = "是否为外链")
	private Boolean iFrame;

	@ApiModelProperty(value = "链接地址")
	private String path;

	@ApiModelProperty(value = "上级菜单Id")
	private Long parentId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

	@JsonIgnore
	private transient Set<Role> roles;
}
