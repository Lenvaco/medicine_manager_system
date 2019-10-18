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
 * 菜单表
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu")
@ApiModel(value="Menu对象", description="菜单表")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "menu_id", type = IdType.ID_WORKER)
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "页面组件")
    private String pageComponent;

    @ApiModelProperty(value = "上级菜单Id")
    private Long pId;

    @ApiModelProperty(value = "菜单序号")
    private Long menuSort;

	@ApiModelProperty(value = "是否为外链")
	private Boolean iFrame;

	@ApiModelProperty(value = "链接地址")
	private String path;

	@ApiModelProperty(value = "模块图标")
	private String icon;

    @ApiModelProperty(value = "更改日期")
    private Date gmtModified;

    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;

}
