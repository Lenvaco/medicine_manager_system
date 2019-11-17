package com.medicine.manager.service;

import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.SaleRecordDTO;
import com.medicine.manager.model.SaleRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 销售记录表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface SaleRecordService extends IService<SaleRecord> {

	Object querySaleRecord(RecordQuery recordQuery, PageInfo pageInfo);

	boolean create(SaleRecord saleRecord);

	boolean updateSaleRecord(Long id,SaleRecord saleRecord);

	boolean deleteSaleRecordById(Long id);
}
