package com.medicine.manager.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/11/17 14:42
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicineSmallDTO implements Serializable {
	private static final long serialVersionUID = -4854176566090341354L;
	@NotNull
	private Long id;
	private String name;
	private Long saleCount;
}
