package com.medicine.manager.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/11/16 22:38
 */
@Data
public class SaleRecordDTO implements Serializable {
	private static final long serialVersionUID = -5750583724904304808L;
	private Long id;
	private MedicineSmallDTO medicineSmallDTO;
	private CustomerSmallDTO customerSmallDTO;
	private UserSmallDTO userSmallDTO;
	@NotNull
	private Integer saleCount;
	@NotNull
	private BigDecimal salePrice;
	private BigDecimal sumPrice;
	private Date saleTime;
}
