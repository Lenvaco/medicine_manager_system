package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Dept;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/10/21 15:58
 */
@Data
public class DeptSmallDTO implements Serializable {
	private static final long serialVersionUID = -7861153718153207854L;
	private Long id;
	private String name;
	public DeptSmallDTO(Dept dept) {
		this.id = dept.getDId();
		this.name = dept.getName();
	}
}
