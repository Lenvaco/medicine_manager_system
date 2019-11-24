package com.medicine.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.model.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 药品经销商表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface SupplierService extends IService<Supplier> {

	List<Supplier> querySuppliers(String name);

	Object querySuppliers(String supplierName, Page page);

	boolean createSupplier(Supplier supplier);

	boolean updateSupplier(Long id, Supplier supplier);

	boolean removeSupplierById(Long id);

	void download(List<Supplier> suppliers, HttpServletResponse response) throws IOException;
}
