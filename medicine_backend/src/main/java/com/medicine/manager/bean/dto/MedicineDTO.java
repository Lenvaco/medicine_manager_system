package com.medicine.manager.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/11/17 14:28
 */
@Data
public class MedicineDTO implements Serializable {
	private static final long serialVersionUID = 8976001565111024787L;
	private Long id;
	private String name;
	private String model;
	private String dosage;
	private String efficacy;
	private String description;
	private String caution;
	private Integer inventory;
	private Date productTime;
	private Date expireTime;
	private SupplierSmallDTO supplierSmallDTO;
}
