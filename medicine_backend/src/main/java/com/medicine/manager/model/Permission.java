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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("permission")
@ApiModel(value="Permission类", description="权限表")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限编号")
    @TableId(value = "p_id", type = IdType.ID_WORKER)
    private Long pId;

    @ApiModelProperty(value = "模块名")
    private String name;

    @ApiModelProperty(value = "权限别名")
    private String alias;

    @ApiModelProperty(value = "上一级权限")
    private Long parentId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

}
