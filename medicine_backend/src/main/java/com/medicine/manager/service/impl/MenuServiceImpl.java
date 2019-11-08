package com.medicine.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medicine.manager.bean.MenuMeta;
import com.medicine.manager.bean.MenuVo;
import com.medicine.manager.bean.dto.MenuDTO;
import com.medicine.manager.dao.MenuDao;
import com.medicine.manager.model.Menu;
import com.medicine.manager.model.Role;
import com.medicine.manager.service.MenuService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@Service
//@CacheConfig(cacheNames = "menu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu>  implements MenuService, Serializable {

	@Autowired
	private MenuDao menuDao;

	private static final String PARENT_ID = "1";

	@Override
//	@Cacheable(key = "#p0")
	public MenuDTO queryById(Long id) {
		return MenuDTO.toDTO(this.baseMapper.selectById(id), null);
	}

	@Override
//	@Cacheable(key = "'parentId:' + #p0")
	public List<MenuDTO> queryByPid(Long pId) {
		QueryWrapper<Menu> queryWrapper = new QueryWrapper();
		queryWrapper.eq("parent_id", pId);
//		List<MenuDTO> menuDTOList = new ArrayList<>();
		/*for (Menu menu: super.list(queryWrapper)){
			menuDTOList.add(MenuDTO.toDTO(menu, null));
		}
		return menuDTOList;*/
		return  Lists.transform(super.list(queryWrapper), new Function<Menu, MenuDTO>(){
			@Nullable
			@Override
			public MenuDTO apply(@Nullable Menu menu) {
				return MenuDTO.toDTO(menu, null);
			}
		});
	}

	@Override
	public List<MenuDTO> findByRole(Set<Role> roles) {
		Set<Menu> menus = new LinkedHashSet<>();
		for (Role role : roles) {
			menus.addAll(menuDao.selectAllByRoleId(role.getId()));
		}

		return menus.stream().map(menu -> {
			return MenuDTO.toDTO(menu, null);
		}).collect(Collectors.toList());
	}


	@Override
	public Map buildTree(List<MenuDTO> menuDTOList) {
		menuDTOList = menuDTOList.stream().sorted(Comparator.comparing(MenuDTO::getSort)).collect(Collectors.toList());
		List<MenuDTO> trees = new ArrayList<>();
		for (MenuDTO menuDTO: menuDTOList) {
			if (PARENT_ID.equals(menuDTO.getId().toString())){
				trees.add(menuDTO);
			}
			for (MenuDTO mdt : menuDTOList){
				if(mdt.getParentId().equals(menuDTO.getId())) {
					if(menuDTO.getChildren() == null ) {
						menuDTO.setChildren(new ArrayList<>());
					}
					menuDTO.getChildren().add(mdt);
				}
			}
		}
		Map menuMap = new HashMap();
		menuMap.put("content",	trees.size() == 0? menuDTOList:trees);
		menuMap.put("totalElementSize", menuDTOList.size());
		return menuMap;
	}

	@Override
	public List<MenuVo> buildMenus(List<MenuDTO> menuDTOTree) {
		List<MenuVo> list = new ArrayList<>();
		menuDTOTree.forEach(menuDTO -> {
			if(menuDTO != null) {
				List<MenuDTO> menuDTOChildren = menuDTO.getChildren();
				MenuVo menuVo = new MenuVo();
				menuVo.setName(menuDTO.getName());
				menuVo.setPath(menuDTO.getPath());
				if(!menuDTO.getIFrame()){
					if(menuDTO.getParentId().equals(0L)){
						//一级目录需要加斜杠，不然访问 会跳转404页面
						menuVo.setPath("/" + menuDTO.getPath());
						menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
					}else if(!StrUtil.isEmpty(menuDTO.getComponent())){
						menuVo.setComponent(menuDTO.getComponent());
					}
					menuVo.setMeta(new MenuMeta(menuDTO.getIcon(), menuDTO.getName()));
					if(menuDTOChildren!=null && menuDTOChildren.size()!=0){
						menuVo.setAlwaysShow(true);
						menuVo.setRedirect("noredirect");
						menuVo.setChildren(buildMenus(menuDTOChildren));
						// 处理是一级菜单并且没有子菜单的情况
					} else if(menuDTO.getParentId().equals(0L)){
						MenuVo menuVo1 = new MenuVo();
						menuVo1.setMeta(menuVo.getMeta());
						// 非外链
						if(!menuDTO.getIFrame()){
							menuVo1.setPath("index");
							menuVo1.setName(menuVo.getName());
							menuVo1.setComponent(menuVo.getComponent());
						} else {
							menuVo1.setPath(menuDTO.getPath());
						}
						menuVo.setName(null);
						menuVo.setMeta(null);
						menuVo.setComponent("Layout");
						List<MenuVo> list1 = new ArrayList<MenuVo>();
						list1.add(menuVo1);
						menuVo.setChildren(list1);
					}
					list.add(menuVo);
				}
			}
		});
		return list;
	}

	@Override
	public Object getMenuTree(List<Menu> menus) {
		List<Map<String,Object>> menuTree = new LinkedList<>();

		menus.forEach(menu  -> {
				if(menu != null) {
					QueryWrapper<Menu>queryWrapper = new QueryWrapper();
					queryWrapper.eq("parent_id", menu.getId());
					List<Menu> menuList = this.list(queryWrapper);
					Map<String, Object> menuMap = new HashMap<>();
					menuMap.put("id",menu.getId());
					menuMap.put("label",menu.getName());
					if(menuList!=null && menuList.size()!=0){
						menuMap.put("children",getMenuTree(menuList));
					}
					menuTree.add(menuMap);
				}
			}
		);
		return menuTree;
	}
}
