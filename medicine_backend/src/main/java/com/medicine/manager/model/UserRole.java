package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lenvaco
 * @date 2019/11/11 13:34
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_role")
@ApiModel(value="UserRole类", description="用户角色表")
public class UserRole extends Model<UserRole> {
	private static final long serialVersionUID = 5724953381637255310L;
	private Long uId;
	private Long rId;
}
