package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Dept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/10/21 15:58
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeptSmallDTO implements Serializable {
	private static final long serialVersionUID = -7861153718153207854L;
	@NotNull(message="部门编号不允许留空")
	private Long id;
	private String name;
	public DeptSmallDTO(Dept dept) {
		this.id = dept.getId();
		this.name = dept.getName();
	}
}
