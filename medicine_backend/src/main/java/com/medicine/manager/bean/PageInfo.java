package com.medicine.manager.bean;

import cn.hutool.core.util.PageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lenvaco
 * @date 2019/10/7 13:26
 */

@AllArgsConstructor
@Getter
@Setter
public class PageInfo {
	/**
	 * 当前页
	 */
	private long pageNo = 1;
	/**
	 * 每页显示条数，默认 10
	 */
	private long pageSize = 10;
}
