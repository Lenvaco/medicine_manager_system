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
 * 销售记录表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sale_record")
@ApiModel(value="SaleRecord对象", description="销售记录表")
public class SaleRecord extends Model<SaleRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售记录id")
    @TableId(value = "s_id", type = IdType.ID_WORKER)
    private Long sId;

    @ApiModelProperty(value = "药品id")
    private Long mId;

    @ApiModelProperty(value = "顾客id")
    private Long cId;

    @ApiModelProperty(value = "销售总数")
    private Integer saleCount;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "销售时间")
    private Date saleTime;

}
