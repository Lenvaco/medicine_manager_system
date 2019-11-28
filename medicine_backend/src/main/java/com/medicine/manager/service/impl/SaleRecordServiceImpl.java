package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.medicine.manager.bean.PageInfo;
import com.medicine.manager.bean.RecordQuery;
import com.medicine.manager.bean.dto.SaleRecordDTO;
import com.medicine.manager.common.utils.FileUtil;
import com.medicine.manager.model.Customer;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.model.SaleRecord;
import com.medicine.manager.dao.SaleRecordDao;
import com.medicine.manager.model.User;
import com.medicine.manager.service.CustomerService;
import com.medicine.manager.service.MedicineService;
import com.medicine.manager.service.SaleRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 销售记录表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SaleRecordServiceImpl extends ServiceImpl<SaleRecordDao, SaleRecord> implements SaleRecordService {

	@Autowired
	private UserService userService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private CustomerService customerService;

	@Override
	public List<SaleRecordDTO> querySaleRecord(RecordQuery recordQuery) {
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
	public Object querySaleRecord(RecordQuery recordQuery, PageInfo pageInfo) {
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
		List<SaleRecordDTO> saleRecordDTOList = this.baseMapper.queryByMap(queryMap);
		Long totalRecord = this.baseMapper.selectCountByMap(queryMap);
		saleRecordDTOList.forEach(saleRecordDTO -> {
			saleRecordDTO.setSumPrice(saleRecordDTO.getSalePrice().multiply(new BigDecimal(saleRecordDTO.getSaleCount())));
		});
		return new HashMap(){{
			put("content", saleRecordDTOList);
			put("totalElements", totalRecord);
		}};
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(SaleRecord saleRecord) {
		if (!isSaleRecordOk(saleRecord)) {
			throw  new IllegalArgumentException("输入的编号错误");
		}
		Medicine medicine = medicineService.getById(saleRecord.getId());
		if(medicine != null) {
			if(medicine.getInventory() >= saleRecord.getSaleCount()) {
				return medicineService.descInventory(saleRecord.getMedicineId(), (long)saleRecord.getSaleCount()) && save(saleRecord);
			}
		}
		return  false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateSaleRecord(Long id,SaleRecord saleRecord) {
		if (!isSaleRecordOk(saleRecord)) {
			throw  new IllegalArgumentException("输入的编号错误");
		}
		UpdateWrapper<SaleRecord> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		return update(saleRecord, updateWrapper);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteSaleRecordById(Long id) {
		return removeById(id);
	}

	@Override
	public void download(List<SaleRecordDTO> saleRecords, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (SaleRecordDTO saleRecordDTO : saleRecords) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("销售记录编号", saleRecordDTO.getId());
			map.put("药品编号", saleRecordDTO.getMedicine() != null? saleRecordDTO.getMedicine().getId() : "");
			map.put("药品名", saleRecordDTO.getMedicine() != null? saleRecordDTO.getMedicine().getName() : "");
			map.put("顾客编号", saleRecordDTO.getCustomer() != null? saleRecordDTO.getCustomer().getId() : "");
			map.put("顾客名", saleRecordDTO.getCustomer() != null? saleRecordDTO.getCustomer().getName() : "");
			map.put("销售人员编号", saleRecordDTO.getUser() != null? saleRecordDTO.getUser().getId() : "");
			map.put("销售人员姓名", saleRecordDTO.getUser() != null? saleRecordDTO.getUser().getName() : "");
			map.put("销售总数", saleRecordDTO.getSaleCount());
			map.put("销售价格", saleRecordDTO.getSalePrice());
			map.put("总金额", saleRecordDTO.getSalePrice().multiply(new BigDecimal(saleRecordDTO.getSaleCount())));
			map.put("销售时间", saleRecordDTO.getSaleTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}

	public boolean isSaleRecordOk(SaleRecord saleRecord) {
		return  customerService.getById(saleRecord.getCustomerId()) != null && medicineService.getById(saleRecord.getMedicineId()) != null && userService.getById(saleRecord.getUserId()) != null;
	}
}
