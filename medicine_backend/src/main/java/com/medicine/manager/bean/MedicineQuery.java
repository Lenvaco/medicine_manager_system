package com.medicine.manager.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/11/19 13:59
 */
@Data
public class MedicineQuery implements Serializable {
	private static final long serialVersionUID = -5578085919149745836L;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productDate;
}
