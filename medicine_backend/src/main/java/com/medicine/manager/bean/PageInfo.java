package com.medicine.manager.bean;

import lombok.Data;

/**
 * @author lenvaco
 * @date 2019/10/7 13:26
 */

@Data
public class PageInfo {
	/**
	 * 当前页 默认为 1
	 */
	private Long page = 1L;
	/**
	 * 每页显示条数，默认 10
	 */
	private Long size = 10L;

}
