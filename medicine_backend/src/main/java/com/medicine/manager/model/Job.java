package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/10/20 23:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job")
@ApiModel(value="Job类", description="岗位表")
public class Job  extends Model<Job> {

	private static final long serialVersionUID = 442993594527713917L;

	@ApiModelProperty(value = "岗位id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@NotBlank
	@ApiModelProperty(value = "岗位名")
	private String name;

	@NotNull
	@ApiModelProperty(value = "序列")
	private Long sort;

	@ApiModelProperty(value = "部门id")
	@NotNull(message = "不允许不选部门")
	@JsonProperty(value = "dId")
	private Long dId;

	@ApiModelProperty(value = "部门")
	private transient Dept dept;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;
}
