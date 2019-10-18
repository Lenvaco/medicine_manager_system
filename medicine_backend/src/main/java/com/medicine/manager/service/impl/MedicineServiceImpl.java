package com.medicine.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.dao.MedicineDao;
import com.medicine.manager.model.Medicine;
import com.medicine.manager.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Autowired
	private MedicineDao medicineDao;
/*

	@Override
	public List<Medicine> selectAllByNameLike(String nameLike, IPage ipage) {
		QueryWrapper<Medicine> queryWrapper = new QueryWrapper();
		queryWrapper.like("m_name", nameLike);
		return medicineDao.selectPage(ipage, queryWrapper).getRecords();
	}

	@Override
	public List<Medicine> selectAll(IPage page) {
		return medicineDao.selectPage(page, null).getRecords();
	}
*/



}
