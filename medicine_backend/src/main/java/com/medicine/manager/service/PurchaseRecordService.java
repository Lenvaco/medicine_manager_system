package com.medicine.manager.service;

import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.PurchaseRecordDTO;
import com.medicine.manager.model.PurchaseRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 采购记录表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface PurchaseRecordService extends IService<PurchaseRecord> {

	List<PurchaseRecordDTO> queryPurchaseRecord(RecordQuery recordQuery);

	Object queryPurchaseRecord(RecordQuery recordQuery, PageInfo pageInfo);

	boolean createPurchase(PurchaseRecord purchaseRecord);

	boolean updatePurchaseById(Long id, PurchaseRecord purchaseRecord);

	boolean removePurchaseById(Long id);

	void download(List<PurchaseRecordDTO> purchaseRecordDTOS, HttpServletResponse response) throws IOException;
}
