package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.bean.MedicineQuery;
import com.medicine.manager.dao.MedicineDao;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeMedicineById(Long id) {
		return false;
	}
}