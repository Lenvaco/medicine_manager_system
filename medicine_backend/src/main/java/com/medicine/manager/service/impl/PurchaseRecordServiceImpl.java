package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.PurchaseRecordDTO;
import com.medicine.manager.common.utils.FileUtil;
import com.medicine.manager.common.utils.SecurityUtil;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
	public List<PurchaseRecordDTO> queryPurchaseRecord(RecordQuery recordQuery) {
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
		return this.baseMapper.queryByMap(queryMap);
	}

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
		queryMap.put("pageNo", pageInfo.getPage() - 1);
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
		if (!isPurchaseRecordOk(purchaseRecord)) {
			throw new IllegalArgumentException("输入的编号错误");
		}
		if(medicineService.increInventory(purchaseRecord.getMedicineId(), (long)purchaseRecord.getPurchaseCount()) && save(purchaseRecord)){
			log.info("用户编号为" + SecurityUtil.getUserId() + "创建采购记录成功,采购时间为" + purchaseRecord.getPurchaseTime());
			return true;
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "创建采购记录失败");
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePurchaseById(Long id, PurchaseRecord purchaseRecord) {
		if (!isPurchaseRecordOk(purchaseRecord)) {
			throw new IllegalArgumentException("输入的编号错误");
		}
		purchaseRecord.setPurchaseTime(null);
		UpdateWrapper<PurchaseRecord> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		boolean result = update(purchaseRecord, updateWrapper);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + "更新采购记录[" + id +"]成功");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "更新采购记录["+ id +"]操作失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removePurchaseById(Long id) {
		boolean result = removeById(id);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + "删除采购记录[" + id +"]成功");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "删除采购记录["+ id +"]操作失败");
		}
		return result;
	}

	@Override
	public void download(List<PurchaseRecordDTO> purchaseRecordDTOS, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (PurchaseRecordDTO purchaseRecordDTO : purchaseRecordDTOS) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("采购记录编号", purchaseRecordDTO.getId().toString());
			map.put("药品编号", purchaseRecordDTO.getMedicine() != null? purchaseRecordDTO.getMedicine().getId().toString() : "");
			map.put("药品名", purchaseRecordDTO.getMedicine() != null? purchaseRecordDTO.getMedicine().getName() : "");
			map.put("供应商编号", purchaseRecordDTO.getSupplier() != null? purchaseRecordDTO.getSupplier().getId().toString() : "");
			map.put("供应商名", purchaseRecordDTO.getSupplier() != null? purchaseRecordDTO.getSupplier().getName() : "");
			map.put("采购人员编号", purchaseRecordDTO.getUser() != null? purchaseRecordDTO.getUser().getId().toString() : "");
			map.put("采购人员姓名", purchaseRecordDTO.getUser() != null? purchaseRecordDTO.getUser().getName() : "");
			map.put("采购总数", purchaseRecordDTO.getPurchaseCount());
			map.put("采购价格", purchaseRecordDTO.getPurchasePrice());
			map.put("总金额", purchaseRecordDTO.getPurchasePrice().multiply(new BigDecimal(purchaseRecordDTO.getPurchaseCount())));
			map.put("采购时间", purchaseRecordDTO.getPurchaseTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}

	public boolean isPurchaseRecordOk(PurchaseRecord purchaseRecord) {
		return medicineService.getById(purchaseRecord.getMedicineId()) != null && supplierService.getById(purchaseRecord.getSupplierId()) != null && userService.getById(purchaseRecord.getUserId()) != null;
	}
}
