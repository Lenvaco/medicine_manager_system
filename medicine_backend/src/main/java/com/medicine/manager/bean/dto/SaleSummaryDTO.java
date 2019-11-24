package com.medicine.manager.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/11/22 21:02
 */
@Data
public class SaleSummaryDTO implements Serializable {

	private static final long serialVersionUID = 625991710456216894L;

	private Long saleCount;
	private String date;
}
