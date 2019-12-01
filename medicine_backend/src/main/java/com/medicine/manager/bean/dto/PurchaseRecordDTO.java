package com.medicine.manager.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medicine.manager.model.PurchaseRecord;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/11/17 19:53
 */
@Data
public class PurchaseRecordDTO implements Serializable {
	private static final long serialVersionUID = -5196554675365558377L;
	private Long id;
	@NotNull
	private MedicineSmallDTO medicine;
	@NotNull
	private SupplierSmallDTO supplier;
	@NotNull
	private UserSmallDTO user;
	@NotNull(message="采购数目不能为空")
	@Min(value = 1, message = "采购数目须大于0")
	private Integer purchaseCount;
	@NotNull
	@DecimalMin(value = "0", message = "采购金额不能为负数")
	private BigDecimal purchasePrice;
	private BigDecimal sumPrice;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date purchaseTime;

	public PurchaseRecord toPurchaseRecord(){
		return new PurchaseRecord(id, medicine.getId(), supplier.getId(), user.getId(), purchaseCount, purchasePrice, purchaseTime);
	}
}
