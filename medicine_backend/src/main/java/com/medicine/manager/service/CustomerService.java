package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.CustomerQuery;
import com.medicine.manager.model.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 顾客表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface CustomerService extends IService<Customer> {

	List<Customer> queryCustomers(CustomerQuery customerQuery);
	Object queryCustomers(CustomerQuery customerQuery, Page page);
	boolean createCustomer(Customer customer);
	boolean updateCustomer(Long id, Customer customer);
	boolean removeCustomerById(Long id);
	void download(List<Customer> customers, HttpServletResponse response) throws IOException;
}
