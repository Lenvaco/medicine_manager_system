package com.medicine.manager.service.impl;

import com.medicine.manager.bean.dto.MedicineSmallDTO;
import com.medicine.manager.bean.dto.SaleSummaryDTO;
import com.medicine.manager.dao.PurchaseRecordDao;
import com.medicine.manager.dao.SaleRecordDao;
import com.medicine.manager.service.ChartSummaryService;
import com.medicine.manager.service.CustomerService;
import com.medicine.manager.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenvaco
 * @date 2019/11/22 10:14
 */
@Service
public class ChartSummaryServiceImpl implements ChartSummaryService {
	@Autowired
	private PurchaseRecordDao purchaseRecordDao;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SaleRecordDao saleRecordDao;
	@Autowired
	private SupplierService supplierService;
	@Override
	public Object getSystemSummary() {
		int customerCount = customerService.count();
		int supplierCount = supplierService.count();
		BigDecimal saleRecordPrice = saleRecordDao.selectSummaryPrice();
		BigDecimal purchaseRecordPrice = purchaseRecordDao.selectSummaryPrice();
		Map<String, Object>data = new HashMap<>(4);
		data.put("purchasePrice", purchaseRecordPrice);
		data.put("supplierCount", supplierCount);
		data.put("salePrice", saleRecordPrice);
		data.put("customerCount", customerCount);
		return data;
	}

	@Override
	public Object getHotMedicine() {
		final int topNumber = 5;
		List<MedicineSmallDTO> topMedicine = saleRecordDao.getTopMedicine(topNumber);
		final Long count = saleRecordDao.getAllSaleCount();
		Long sum = topMedicine.stream().map(MedicineSmallDTO::getSaleCount).reduce(Long::sum).get();
		topMedicine.add(new MedicineSmallDTO(null, "其他", count-sum));
		return topMedicine;
	}

	@Override
	public Object getWeekSummary() {
		return saleRecordDao.getRecentSaleCount();
	}
}
