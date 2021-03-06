package com.medicine.manager.dao;

import com.medicine.manager.bean.dto.MedicineSmallDTO;
import com.medicine.manager.bean.dto.SaleRecordDTO;
import com.medicine.manager.bean.dto.SaleSummaryDTO;
import com.medicine.manager.model.SaleRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售记录表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface SaleRecordDao extends BaseMapper<SaleRecord> {
	List<SaleRecordDTO> queryByMap(Map queryMap);

	Long selectCountByMap(Map queryMap);

	BigDecimal selectSummaryPrice();

	List<MedicineSmallDTO> getTopMedicine(int topNumber);

	List<SaleSummaryDTO> getRecentSaleCount();

	Long getAllSaleCount();
}
