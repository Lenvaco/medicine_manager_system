package com.medicine.manager.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.manager.bean.dto.MenuDTO;
import com.medicine.manager.bean.dto.UserDTO;
import com.medicine.manager.common.utils.SecurityUtil;
import com.medicine.manager.model.Menu;
import com.medicine.manager.service.MenuService;
import com.medicine.manager.service.RoleService;
import com.medicine.manager.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author lenvaco
 * @since 2019-09-26
 */
@RestController
@RequestMapping("api")
public class MenuController {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;


	@GetMapping(value = "/menus/build")
	public ResponseEntity buildMenus(){
		UserDTO user = userService.findByUsername(SecurityUtil.getUsername());
		List<MenuDTO> menuDTOList = menuService.findByRole(roleService.findByUserId(user.getId()));
		List<MenuDTO> menuDTOTree = (List<MenuDTO>)menuService.buildTree(menuDTOList).get("content");
		return new ResponseEntity(menuService.buildMenus(menuDTOTree),HttpStatus.OK);
	}


	/**
	 * 返回父级的菜单
	 * @return
	 */
	@GetMapping(value = "/menus/tree")
//	@PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
	@ApiOperation(value = "获取全部菜单", notes = "获取全部菜单", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getMenuTree(){
		List<Menu> menuList = menuService.list(new QueryWrapper<Menu>() {{
			eq("parent_id", 0L);
		}});
		return new ResponseEntity(menuService.getMenuTree(menuList), HttpStatus.OK);
	}
}
