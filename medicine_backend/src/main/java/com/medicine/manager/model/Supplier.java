package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 药品经销商表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("supplier")
@ApiModel(value="Supplier类", description="药品供应商表")
public class Supplier extends Model<Supplier> {


    private static final long serialVersionUID = -8012316236527055589L;
    @ApiModelProperty(value = "供应商id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "供应商名称")
    @NotBlank(message = "供应商名称必须提供")
    private String name;

    @ApiModelProperty(value = "电话")
    @NotBlank(message = "联系电话不允许为空")
    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[34578]\\d{9}))$", message = "电话格式错误")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "合作时间")

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cooperationTime;

}
