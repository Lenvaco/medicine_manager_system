package com.medicine.manager.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/11/17 14:30
 */
@Data
public class CustomerSmallDTO implements Serializable {
	private static final long serialVersionUID = -2833271019640894140L;
	@NotNull(message="顾客编号不允许留空")
	private Long id;
	private Long name;
}
