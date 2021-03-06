package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medicine.manager.model.SaleRecord;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
	private MedicineSmallDTO medicine;
	private CustomerSmallDTO customer;
	private UserSmallDTO user;
	@NotNull(message = "销售数目不能留空")
	@Min(value = 1, message = "销售数目需大于0")
	private Integer saleCount;
	@NotNull(message = "销售价格不能留空")
	@DecimalMin(value = "0", message = "销售价格不能为负数")
	private BigDecimal salePrice;
	private BigDecimal sumPrice;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date saleTime;

	public SaleRecord toSaleRecord(){
		return new SaleRecord(id, medicine.getId(), customer.getId(), user.getId(), saleCount, salePrice, saleTime);
	}
}
