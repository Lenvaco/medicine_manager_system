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
@TableName("role_permission")
@ApiModel(value="RolePermission类", description="角色权限表")
public class RolePermission extends Model<RolePermission> {
	private static final long serialVersionUID = 2888965779069620566L;
	@ApiModelProperty(value = "角色id")
	private Long rId;
	@ApiModelProperty(value = "权限id")
	private Long pId;
}