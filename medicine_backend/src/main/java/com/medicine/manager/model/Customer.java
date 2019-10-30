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
 * 顾客表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("customer")
@ApiModel(value="Customer类", description="顾客表")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 2434382817377335275L;

    @ApiModelProperty(value = "顾客id")
    @TableId(value = "c_id", type = IdType.ID_WORKER)
    private Long cId;

    @ApiModelProperty(value = "姓名")
    private String cName;

    @ApiModelProperty(value = "性别")
    private String cSex;

    @ApiModelProperty(value = "年龄")
    private Integer cAge;

    @ApiModelProperty(value = "地址")
    private String cAddresss;

    @ApiModelProperty(value = "电话")
    private String cPhone;

    @ApiModelProperty(value = "症状")
    private String cSymptom;

    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;

}
