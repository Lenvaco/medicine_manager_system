package com.medicine.manager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/16 20:07
 */
@Data
public class MenuVo {
	private String name;

	private String path;

	private String redirect;

	private String component;

	private Boolean alwaysShow;

	private MenuMeta meta;

	private List<MenuVo> children;
}
