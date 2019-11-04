package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Dept;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/10/21 15:34
 */
@Data
public class DeptDTO implements Serializable {
	private static final long serialVersionUID = 958569045410916429L;
	private Long id;
	private String name;
	private Long parentId;
	private Date createTime;
	private List<DeptDTO> children;

	public  DeptDTO(Dept dept) {
		this.id = dept.getId();
		this.name = dept.getName();
		this.parentId = dept.getParentId();
		this.createTime = dept.getCreateTime();
	}
	public String getLabel() {
		return name;
	}
}
