package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.model.Customer;
import com.medicine.manager.dao.CustomerDao;
import com.medicine.manager.model.Supplier;
import com.medicine.manager.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 顾客表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {

	@Override
	public Object queryCustomers(String customerName, Page page) {
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		if (!StrUtil.isBlank(customerName)) {
			queryWrapper.like("name", customerName);
		}
		IPage iPage = page(page, queryWrapper);
		return  new HashMap() {{
			put("content", iPage.getRecords());
			put("totalElements", iPage.getTotal());
		}};
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createCustomer(Customer customer) {
		return save(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateCustomer(Long id, Customer Customer) {
		UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		return update(Customer, updateWrapper);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeCustomerById(Long id) {
		return removeById(id);
	}
}
