package com.medicine.manager.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/11/16 22:23
 */
@Data
public class RecordQuery implements Serializable {
	private static final long serialVersionUID = -7285415481606796474L;
	//某种药品名称
	private String name;

	private Date startTime;

	private Date endTime;
}
