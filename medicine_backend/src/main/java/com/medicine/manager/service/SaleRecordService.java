package com.medicine.manager.service;

import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.SaleRecordDTO;
import com.medicine.manager.model.SaleRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

	List<SaleRecordDTO> querySaleRecord(RecordQuery recordQuery);

	Object querySaleRecord(RecordQuery recordQuery, PageInfo pageInfo);

	boolean createSaleRecord(SaleRecord saleRecord);

	boolean updateSaleRecord(Long id,SaleRecord saleRecord);

	boolean deleteSaleRecordById(Long id);

	void download(List<SaleRecordDTO> saleRecords, HttpServletResponse response) throws IOException;
}
