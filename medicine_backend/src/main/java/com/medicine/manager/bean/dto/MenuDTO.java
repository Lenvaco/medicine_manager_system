package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicine.manager.model.Menu;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/10 9:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 5306631867619191707L;

	private Long id;
	private String name;
	private String component;
	private Long pid;
	private Long menuSort;
	private Boolean iFrame;
	private String path;
	private String icon;
	private List<MenuDTO> children;

	@JsonIgnore
	public static MenuDTO toDTO(Menu menu, List<MenuDTO> children) {
		return new MenuDTO(menu.getMenuId(), menu.getMenuName(), menu.getPageComponent(), menu.getPId(), menu.getMenuSort(), menu.getIFrame(), menu.getPath(), menu.getIcon(), children);
	}
}
