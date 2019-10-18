package com.medicine.manager.service.impl;

import com.medicine.manager.model.Customer;
import com.medicine.manager.dao.CustomerDao;
import com.medicine.manager.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
