package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicine.manager.model.Menu;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/10 9:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 5306631867619191707L;

	private Long id;
	private String name;
	private String component;
	private Long parentId;
	private Long sort;
	private Boolean iFrame;
	private String path;
	private String icon;
	private List<MenuDTO> children;
	private Date createTime;
	@JsonIgnore
	public static MenuDTO toDTO(Menu menu, List<MenuDTO> children) {
		return new MenuDTO(menu.getId(), menu.getName(), menu.getComponent(),
				menu.getParentId(), menu.getSort(), menu.getIFrame(), menu.getPath(), menu.getIcon(), children, menu.getCreateTime());
	}
}
