package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lenvaco
 * @date 2019/11/6 18:41
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_menu")
@ApiModel(value="RoleMenu类", description="角色菜单表")
public class RoleMenu extends Model<RoleMenu> {

	private static final long serialVersionUID = 6269513955548343084L;

	@ApiModelProperty(value = "角色id")
	private Long rId;
	@ApiModelProperty(value = "菜单id")
	private Long mId;
}
