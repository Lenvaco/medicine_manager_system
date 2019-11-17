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
@ApiModel(value="PurchaseRecord类", description="采购记录表")
public class PurchaseRecord extends Model<PurchaseRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购记录id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "药品id")
    private Long medicineId;

    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @ApiModelProperty(value = "采购人员id")
    private Integer userId;

    @ApiModelProperty(value = "采购数目")
    private Integer purchaseCount;


    @ApiModelProperty(value = "采购单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "采购时间")
    private Date createTime;

}
