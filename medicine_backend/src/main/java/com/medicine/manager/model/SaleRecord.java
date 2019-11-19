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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 销售记录表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sale_record")
@ApiModel(value="SaleRecord类", description="销售记录表")
public class SaleRecord extends Model<SaleRecord> {

    private static final long serialVersionUID = 1132455035730902134L;

    @ApiModelProperty(value = "销售记录id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "药品id")
    private Long medicineId;

    @ApiModelProperty(value = "顾客id")
    private Long customerId;

    @ApiModelProperty(value = "采购人员id")
    private Long userId;

    @ApiModelProperty(value = "销售总数")
    private Integer saleCount;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "销售时间")
    private Date saleTime;

}
