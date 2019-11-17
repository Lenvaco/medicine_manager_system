package com.medicine.manager.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/11/17 14:31
 */
@Data
public class SupplierSmallDTO implements Serializable {
	private static final long serialVersionUID = -6188541573214250464L;
	@NotNull
	private Long id;
	private Long name;
}
