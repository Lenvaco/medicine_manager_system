package com.medicine.manager.bean.dto;

import com.medicine.manager.model.PurchaseRecord;
import lombok.Data;

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
	private MedicineSmallDTO medicineSmallDTO;
	@NotNull
	private SupplierSmallDTO supplierSmallDTO;
	@NotNull
	private UserSmallDTO userSmallDTO;
	@NotNull
	@Size
	private Integer purchaseCount;
	@NotNull
	private BigDecimal purchasePrice;
	private BigDecimal sumPrice;
	private Date purchaseTime;

	public PurchaseRecord toPurchaseRecord(){
		return new PurchaseRecord(id, medicineSmallDTO.getId(), supplierSmallDTO.getId(), userSmallDTO.getId(), purchaseCount, purchasePrice, purchaseTime);
	}
}
