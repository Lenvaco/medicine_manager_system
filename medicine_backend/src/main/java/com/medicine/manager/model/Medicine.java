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
@ApiModel(value="Medicine对象", description="药品表")
public class Medicine extends Model<Medicine> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "药品id")
    @TableId(value = "m_id", type = IdType.ID_WORKER)
    private Long mId;

    @ApiModelProperty(value = "药品名称")
    private String mName;

    @ApiModelProperty(value = "使用方法（0内服1外用）")
    private String mMode;

    @ApiModelProperty(value = "使用功效")
    private String mEfficacy;

    @ApiModelProperty(value = "药品组成")
    private String mDescription;

    @ApiModelProperty(value = "注意事项")
    private String mCaution;

    @ApiModelProperty(value = "库存量")
    private Integer mInventory;

    @ApiModelProperty(value = "生产日期")
    private Date pCreate;

    @ApiModelProperty(value = "有效截止期")
    private Date pEnd;

    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;


}
