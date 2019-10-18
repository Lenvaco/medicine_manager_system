package com.medicine.manager.model;

import java.math.BigDecimal;
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
 * 采购记录表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("purchase_record")
@ApiModel(value="PurchaseRecord对象", description="采购记录表")
public class PurchaseRecord extends Model<PurchaseRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购记录id")
    @TableId(value = "p_id", type = IdType.ID_WORKER)
    private Long pId;

    @ApiModelProperty(value = "药品id")
    private Long mId;

    @ApiModelProperty(value = "供应商id")
    private Long sId;

    @ApiModelProperty(value = "采购总数")
    private Integer pCount;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal pPrice;

    @ApiModelProperty(value = "采购人员")
    private Long uId;

    @ApiModelProperty(value = "采购日期")
    private Date purchaseTime;

}
