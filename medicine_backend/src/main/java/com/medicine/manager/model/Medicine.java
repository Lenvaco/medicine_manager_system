package com.medicine.manager.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 药品表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("medicine")
@ApiModel(value="Medicine类", description="药品表")
public class Medicine extends Model<Medicine> {

    private static final long serialVersionUID = -4234855453233544533L;

    @ApiModelProperty(value = "药品id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "药品名")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "使用方法（0内服1外用）")
    private String mode;

    @NotBlank
    @ApiModelProperty(value = "使用功效")
    private String efficacy;

    @NotBlank
    @ApiModelProperty(value = "组成成份")
    private String description;

    @NotBlank
    @ApiModelProperty(value = "注意事项")
    private String caution;

    @NotNull
    @Size
    @ApiModelProperty(value = "库存量")
    private Integer inventory;

    @NotNull
    @ApiModelProperty(value = "生产日期")
    private Date productTime;

    @NotNull
    @ApiModelProperty(value = "有效日期")
    private Date expireTime;
}
