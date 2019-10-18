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
@ApiModel(value="Supplier对象", description="药品经销商表")
public class Supplier extends Model<Supplier> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "供应商id")
    @TableId(value = "s_id", type = IdType.ID_WORKER)
    private Long sId;

    @ApiModelProperty(value = "药品经销商名称")
    private String sName;

    @ApiModelProperty(value = "电话号码")
    private String sPhone;

    @ApiModelProperty(value = "地址")
    private String sAddress;

    @ApiModelProperty(value = "供应商简介")
    private String sDescription;

    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;

}
