package com.medicine.manager.dao;

import com.medicine.manager.bean.dto.PurchaseRecordDTO;
import com.medicine.manager.model.PurchaseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采购记录表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface PurchaseRecordDao extends BaseMapper<PurchaseRecord> {
	List<PurchaseRecordDTO> queryByMap(Map queryMap);
	Long selectCountByMap(Map queryMap);
	BigDecimal selectSummaryPrice();
}
