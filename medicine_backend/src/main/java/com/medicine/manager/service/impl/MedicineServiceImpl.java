package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.bean.MedicineQuery;
import com.medicine.manager.common.utils.FileUtil;
import com.medicine.manager.common.utils.IdWorker;
import com.medicine.manager.dao.MedicineDao;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.service.MedicineService;
import com.medicine.manager.service.SaleRecordService;
import org.checkerframework.checker.units.qual.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 药品表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MedicineServiceImpl extends ServiceImpl<MedicineDao, Medicine> implements MedicineService {

	@Override
	public List<Medicine> queryMedicine(MedicineQuery medicineQuery) {
		QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
		if(medicineQuery != null) {
			if (!StrUtil.isBlank(medicineQuery.getName())) {
				queryWrapper.like("name", medicineQuery.getName());
			}
			if(medicineQuery.getProductDate() != null) {
				queryWrapper.ge("product_time", medicineQuery.getProductDate());
			}
		}
		return list(queryWrapper);
	}

	@Override
	public Object queryMedicine(MedicineQuery medicineQuery, Page page) {

		QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
		if(medicineQuery != null) {
			if (!StrUtil.isBlank(medicineQuery.getName())) {
				queryWrapper.like("name", medicineQuery.getName());
			}
			if(medicineQuery.getProductDate() != null) {
				queryWrapper.ge("product_time", medicineQuery.getProductDate());
			}
		}
		IPage iPage = page(page, queryWrapper);
		return new HashMap(){{
			put("content", iPage.getRecords());
			put("totalElements", iPage.getTotal());
		}};
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createMedicine(Medicine medicine) {
		return save(medicine);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMedicineById(Long id, Medicine medicine) {
		medicine.setId(id);
		medicine.setProductTime(null);
		medicine.setExpireTime(null);
		return updateById(medicine);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeMedicineById(Long id) {
		return removeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean increInventory(Long id, Long inventory) {
		return this.baseMapper.increInventory(id, inventory);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean descInventory(Long id, Long inventory) {
		Medicine medicine = getById(id);
		if (medicine != null &&   inventory >= 0){
			UpdateWrapper<Medicine> updateWrapper = new UpdateWrapper<>();
			updateWrapper.set("inventory", medicine.getInventory() - inventory);
			updateWrapper.eq("id", id);
			update(updateWrapper);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void download(List<Medicine> medicines, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Medicine medicine : medicines) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("药品编号", medicine.getId().toString());
			map.put("药品名", medicine.getName());
			map.put("使用方法", "0".equals(medicine.getMode())? "内服" : "外用");
			map.put("使用功效", medicine.getEfficacy());
			map.put("组成成份", medicine.getDescription());
			map.put("注意事项", medicine.getCaution());
			map.put("库存量", medicine.getInventory());
			map.put("生产日期", medicine.getProductTime());
			map.put("过期日期", medicine.getExpireTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}

}