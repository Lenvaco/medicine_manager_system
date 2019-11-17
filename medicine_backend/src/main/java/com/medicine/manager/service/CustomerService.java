package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.model.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 顾客表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface CustomerService extends IService<Customer> {

	Object queryCustomers(String customerName, Page page);
	boolean createCustomer(Customer customer);
	boolean updateCustomer(Long id, Customer customer);
	boolean removeCustomerById(Long id);
}
