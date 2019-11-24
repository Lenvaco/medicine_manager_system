package com.medicine.manager.dao;

import com.medicine.manager.bean.dto.MedicineDTO;
import com.medicine.manager.model.Medicine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 药品表 Mapper 接口
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface MedicineDao extends BaseMapper<Medicine> {
	boolean increInventory(Long id, Long increCount);
}
