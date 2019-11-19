package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.PurchaseRecordDTO;
import com.medicine.manager.dao.PurchaseRecordDao;
import com.medicine.manager.model.PurchaseRecord;
import com.medicine.manager.service.MedicineService;
import com.medicine.manager.service.PurchaseRecordService;
import com.medicine.manager.service.SupplierService;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采购记录表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PurchaseRecordServiceImpl extends ServiceImpl<PurchaseRecordDao, PurchaseRecord> implements PurchaseRecordService {

	@Autowired
	private UserService userService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private SupplierService supplierService;

	@Override
	public Object queryPurchaseRecord(RecordQuery recordQuery, PageInfo pageInfo) {
		Map<String, Object> queryMap = new HashMap<>();
		if (!StrUtil.isEmpty(recordQuery.getName())) {
			queryMap.put("name", recordQuery.getName());
		}
		if (recordQuery.getStartTime() != null) {
			queryMap.put("startTime", recordQuery.getStartTime());
		}
		if (recordQuery.getEndTime() != null) {
			queryMap.put("endTime", recordQuery.getEndTime());
		}
		queryMap.put("pageNo", pageInfo.getPage());
		queryMap.put("pageSize", pageInfo.getSize());
		List<PurchaseRecordDTO> purchaseRecordDTOList = this.baseMapper.queryByMap(queryMap);
		Long totalRecord = this.baseMapper.selectCountByMap(queryMap);
		purchaseRecordDTOList.forEach(purchaseRecordDTO -> {
			purchaseRecordDTO.setSumPrice(purchaseRecordDTO.getPurchasePrice().multiply(new BigDecimal(purchaseRecordDTO.getPurchaseCount())));
		});
		return new HashMap(){{
			put("content", purchaseRecordDTOList);
			put("totalElements", totalRecord);
		}};
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createPurchase(PurchaseRecord purchaseRecord) {
		if (isPurchaseRecordOk(purchaseRecord)) {
			throw new IllegalArgumentException("输入的编号错误");
		}
		purchaseRecord.setPurchaseTime(new Date());
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePurchaseById(Long id, PurchaseRecord purchaseRecord) {
		if (isPurchaseRecordOk(purchaseRecord)) {
			throw new IllegalArgumentException("输入的编号错误");
		}
		UpdateWrapper<PurchaseRecord> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		return update(purchaseRecord, updateWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removePurchaseById(Long id) {
		return removeById(id);
	}

	public boolean isPurchaseRecordOk(PurchaseRecord purchaseRecord) {
		return medicineService.getById(purchaseRecord.getMedicineId()) != null && supplierService.getById(purchaseRecord.getSupplierId()) != null && userService.getById(purchaseRecord.getUserId()) != null;
	}
}
