CREATE DATABASE IF NOT EXISTS `medicine_system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `medicine_system`;

CREATE TABLE IF NOT EXISTS `user`(
	u_id bigint unsigned PRIMARY KEY COMMENT '用户id',
	usernamne varchar(18) NOT NULL  COMMENT '用户账号',
	password varchar(64) NOT NULL COMMENT '密码',
	name varchar(10) NOT NULL COMMENT '姓名',
	phone varchar(15) NOT NULL COMMENT '电话号码',
	sex char(1) NOT NULL COMMENT '0男1女',
	email varchar(25) DEFAULT NULL COMMENT '邮箱',
	id_card varchar(20) NOT NULL COMMENT '身份证',
	address varchar(60) NOT NULL COMMENT '地址',
	birthday datetime NOT NULL COMMENT '生日',
	gmt_modified  datetime NOT NULL COMMENT '更改日期',
	gmt_create   datetime NOT NULL COMMENT '创建日期',
	UNIQUE KEY `uk_usernamne`(`usernamne`),
	UNIQUE KEY `uk_phone`(`phone`),
	UNIQUE KEY `uk_email`(`email`),
	UNIQUE KEY `uk_id_card`(`id_card`)

)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '系统用户表';


CREATE TABLE IF NOT EXISTS `role`(
	role_id bigint unsigned PRIMARY KEY COMMENT '角色编号',
	role_name varchar(20) NOT NULL COMMENT '角色名字',
	role_description varchar(100) NOT NULL COMMENT '角色描述',
	gmt_modified  datetime NOT NULL COMMENT '更改日期',
	gmt_create   datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '角色表';

CREATE TABLE IF NOT EXISTS `user_role`(
	u_id bigint unsigned  NOT NULL COMMENT '用户id',
	role_id  bigint unsigned NOT NULL COMMENT '角色编号',
	key u_id_index(u_id),
	key role_id_index(role_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户-角色表';

CREATE TABLE IF NOT EXISTS `permission` (
	permission_id  bigint unsigned PRIMARY KEY COMMENT '权限编号',
	permission_name varchar(20) NOT NULL COMMENT '模块操作名',
	parent_id bigint unsigned COMMENT  '上一级权限',
	permission_description varchar(100) DEFAULT NULL COMMENT '描述',
	gmt_modified  datetime NOT NULL COMMENT '更改日期',
	gmt_create   datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '权限表';

CREATE TABLE IF NOT EXISTS `role_permission` (
	role_id bigint unsigned NOT NULL COMMENT '角色编号',
	permission_id  bigint unsigned NOT NULL COMMENT '权限编号',
	key role_id_index(role_id),
	key permission_id_index(permission_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '角色-权限表';


CREATE TABLE IF NOT EXISTS `menu` (
	menu_id bigint unsigned PRIMARY KEY COMMENT '菜单id',
	menu_name varchar(20) DEFAULT NULL COMMENT '菜单名称',
  	page_component varchar(50) DEFAULT NULL COMMENT '页面组件',
  	pid bigint unsigned NOT NULL COMMENT '上级菜单ID',
  	menu_sort bigint unsigned COMMENT '菜单序号',
	gmt_modified  datetime NOT NULL COMMENT '更改日期',
	gmt_create   datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '菜单表';

CREATE TABLE IF NOT EXISTS role_menu (
	role_id bigint unsigned NOT NULL COMMENT '角色编号',
	menu_id  bigint unsigned NOT NULL COMMENT '权限编号',
	key role_id_index(role_id),
	key menu_id_index(menu_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '角色-菜单表';

















CREATE TABLE IF NOT EXISTS  `customer`(
	c_id bigint unsigned PRIMARY KEY COMMENT '顾客id',
	c_name varchar(10) NOT NULL COMMENT '姓名',
	c_sex char(1) NOT NULL COMMENT '性别',
	c_age tinyint unsigned NOT NULL COMMENT '年龄',
	c_addresss varchar(60) DEFAULT NULL COMMENT '地址',
	c_phone varchar(15) DEFAULT NULL COMMENT '电话',
	c_symptom varchar(100) DEFAULT NULL COMMENT '症状',
	gmt_create	datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '顾客表';

CREATE TABLE IF NOT EXISTS `medicine` (
 	m_id bigint unsigned PRIMARY KEY COMMENT  '药品id',
 	m_name varchar(12) NOT NULL COMMENT '药品名称',
 	m_mode char(1) NOT NULL COMMENT '使用方法（0内服1外用）',
 	m_efficacy varchar(50) NOT NULL COMMENT '使用功效',
 	m_description varchar(100) NOT NULL COMMENT '药品组成',
 	m_caution varchar(100) NOT NULL COMMENT '注意事项',
 	p_create datetime NOT NULL COMMENT '生产日期',
	p_end datetime NOT NULL COMMENT '有效截止期',
 	gmt_create	datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '药品表';

CREATE TABLE IF NOT EXISTS  `supplier` (
	s_id  bigint unsigned PRIMARY KEY COMMENT  '供应商id',
	s_name varchar(16) NOT NULL COMMENT '药品经销商名称',
	s_phone varchar(15) NOT NULL COMMENT '电话号码',
	s_address varchar(60) NOT NULL COMMENT '地址',
	s_description varchar(100) NOT NULL COMMENT '供应商简介',
	gmt_create	datetime NOT NULL COMMENT '创建日期'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '药品经销商表';

CREATE TABLE IF NOT EXISTS `purchase_record` (
	p_id bigint unsigned PRIMARY KEY COMMENT  '采购记录id',
	m_id bigint unsigned NOT NULL COMMENT '药品id',
	s_id  bigint unsigned NOT NULL  COMMENT '供应商id',
	p_count int unsigned NOT NULL COMMENT '采购总数',
	p_price  decimal NOT NULL COMMENT '采购单价',
	u_id bigint unsigned NOT NULL COMMENT '采购人员',
	purchase_time timestamp NOT NULL COMMENT '采购日期',
	KEY m_id_index(m_id),
	KEY s_id_index(s_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '采购记录表';

CREATE TABLE IF NOT EXISTS `sale_record` (
	s_id bigint unsigned PRIMARY KEY COMMENT  '销售记录id',
	m_id bigint unsigned NOT NULL COMMENT '药品id',
	c_id bigint unsigned NOT NULL  COMMENT '顾客id',
	sale_count int unsigned NOT NULL COMMENT '销售总数',
	sale_price decimal(6,2) NOT NULL COMMENT  '销售价格',
	sale_time timestamp NOT NULL  COMMENT '销售时间',
	KEY m_id_index(m_id),
	KEY c_id_index(c_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '销售记录表';