package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.CustomerQuery;
import com.medicine.manager.common.utils.FileUtil;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.model.Customer;
import com.medicine.manager.dao.CustomerDao;
import com.medicine.manager.model.PurchaseRecord;
import com.medicine.manager.model.Supplier;
import com.medicine.manager.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.manager.service.PurchaseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 顾客表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Slf4j
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {

	@Autowired
	private PurchaseRecordService purchaseRecordService;

	@Override
	public List<Customer> queryCustomers(CustomerQuery customerQuery) {
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		if(customerQuery != null) {
			if (!StrUtil.isBlank(customerQuery.getName())) {
				queryWrapper.like("name", customerQuery.getName());
			}
		}
		return list(queryWrapper);
	}

	@Override
	public Object queryCustomers(CustomerQuery customerQuery, Page page) {
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		if(customerQuery != null) {
			if (!StrUtil.isBlank(customerQuery.getName())) {
				queryWrapper.like("name", customerQuery.getName());
			}
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
		customer.setCreateTime(new Date());
		boolean result = save(customer);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 新建了顾客[" + customer.getName() +"]成功!" );
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + "新建顾客失败!");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateCustomer(Long id, Customer customer) {
		UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		customer.setCreateTime(null);
		boolean result = update(customer, updateWrapper);
		if(result) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 修改了顾客Id[" + id + "]成功!");
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 修改顾客Id[" + id + "]操作失败!" );
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeCustomerById(Long id) {
		UpdateWrapper<PurchaseRecord> purchaseRecordUpdateWrapper = new UpdateWrapper<>();
		purchaseRecordUpdateWrapper.eq("user_id", id );
		purchaseRecordUpdateWrapper.set("user_id", null);
		purchaseRecordService.update(purchaseRecordUpdateWrapper);
		if(removeById(id)) {
			log.info("用户编号为" + SecurityUtil.getUserId() + " 删除顾客Id[" + id + "]成功!");
			return true;
		} else {
			log.warn("用户编号为" + SecurityUtil.getUserId() + " 删除顾客Id[" + id + "]操作失败!" );
			return false;
		}
	}

	@Override
	public void download(List<Customer> customers, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Customer customer : customers) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("顾客编号", customer.getId().toString());
			map.put("姓名", customer.getName());
			map.put("性别", "0".equals(customer.getSex())? "男" : "女");
			map.put("年龄", customer.getAge());
			map.put("地址", customer.getAddress());
			map.put("电话", customer.getPhone());
			map.put("症状", customer.getSymptom());
			map.put("备注", customer.getRemark());
			map.put("创建日期", customer.getCreateTime());
			list.add(map);
		}
		FileUtil.downloadExcel(list, response);
	}
}
