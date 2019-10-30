package com.medicine.manager.service;

import com.medicine.manager.bean.dto.MenuDTO;
import com.medicine.manager.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medicine.manager.model.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
public interface MenuService extends IService<Menu> {

	MenuDTO queryById(Long id);

	List<MenuDTO> queryByPid(Long pId);

	List<MenuDTO> findByRole(Set<Role> roles);

	Map buildTree(List<MenuDTO> menuDTOList);

	Object buildMenus(List<MenuDTO> menuDTOTree);

	Object getMenuTree(List<Menu> menus);
}
