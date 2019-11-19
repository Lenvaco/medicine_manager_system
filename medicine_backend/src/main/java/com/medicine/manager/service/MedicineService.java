package com.medicine.manager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.manager.bean.MedicineQuery;
import com.medicine.manager.model.Medicine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * <p>
 * 药品表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface MedicineService extends IService<Medicine> {
	Object queryMedicine(MedicineQuery medicineQuery, Page page);
	boolean createMedicine(Medicine medicine);
	boolean updateMedicineById(Long id, Medicine medicine);
	boolean removeMedicineById(Long id);
}
