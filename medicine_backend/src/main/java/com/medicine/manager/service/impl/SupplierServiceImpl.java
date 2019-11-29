package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.common.utils.FileUtil;
import com.medicine.manager.model.Supplier;
import com.medicine.manager.dao.SupplierDao;
import com.medicine.manager.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 药品经销商表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements SupplierService {

	@Override
	public List<Supplier> querySuppliers(String name) {
		QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
		if (!StrUtil.isBlank(name)) {
			queryWrapper.like("name", name);
		}
		return list(queryWrapper);
	}

	@Override
	public Object querySuppliers(String supplierName, Page page) {
		QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
		if (!StrUtil.isBlank(supplierName)) {
			queryWrapper.like("name", supplierName);
		}
		IPage iPage = page(page, queryWrapper);
		return  new HashMap() {{
			put("content", iPage.getRecords());
			put("totalElements", iPage.getTotal());
		}};
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createSupplier(Supplier supplier) {
		return save(supplier);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateSupplier(Long id, Supplier supplier) {
		UpdateWrapper<Supplier> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		return update(supplier, updateWrapper);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeSupplierById(Long id) {
		return removeById(id);
	}

	@Override
	public void download(List<Supplier> suppliers, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Supplier supplier : suppliers) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("供应商编号", supplier.getId().toString());
			map.put("供应商名称", supplier.getName());
			map.put("电话", supplier.getPhone());
			map.put("地址", supplier.getAddress());
			map.put("简介", supplier.getDescription());
			map.put("合作时间", supplier.getCooperationTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}
}
