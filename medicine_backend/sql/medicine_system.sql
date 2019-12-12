/*
Navicat MySQL Data Transfer

Source Server         : MyMySQL
Source Server Version : 80017
Source Host           : 47.98.137.65:3306
Source Database       : medicine

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-12-12 16:45:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) unsigned NOT NULL COMMENT '顾客id',
  `name` varchar(18) NOT NULL COMMENT '姓名',
  `sex` char(1) NOT NULL COMMENT '性别',
  `age` tinyint(3) unsigned DEFAULT NULL COMMENT '年龄',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `phone` varchar(15) DEFAULT NULL COMMENT '电话',
  `symptom` varchar(100) DEFAULT NULL COMMENT '症状',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `customer_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='顾客表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1197081579957043201', '小胖', '0', '19', '广东省肇庆市肇庆学院映翠苑', '15013928681', '发烧', '发烧达到38度', '2019-11-20 17:17:44');
INSERT INTO `customer` VALUES ('1197106243169267713', '小红', '1', '20', '广东省肇庆市肇庆学院映翠苑', '15013928682', '感冒流鼻涕', '持续了1天，一半鼻塞', '2019-11-20 18:55:44');
INSERT INTO `customer` VALUES ('1200072569613574146', '小明', '0', '20', '广东省肇庆市肇庆学院映翠苑', '15013928680', '头疼', '头疼已经有1到2天了', '2019-11-28 23:22:52');
INSERT INTO `customer` VALUES ('1200074767118475265', '大胖', '0', '21', '广东省肇庆市肇庆学院映翠苑2区1栋', '15013927681', '咽喉痛', '早上开始持续', '2019-11-28 23:31:35');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL COMMENT '部门姓名',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门父id',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', 'MedicineSystem', '0', '2019-10-23 08:32:44');
INSERT INTO `dept` VALUES ('2', '总部', '1', '2019-10-28 16:48:14');
INSERT INTO `dept` VALUES ('3', '销售部', '2', '2019-10-28 16:48:37');
INSERT INTO `dept` VALUES ('4', '采购部', '2', '2019-10-28 16:48:55');
INSERT INTO `dept` VALUES ('5', '会计部', '2', '2019-10-28 16:49:13');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '职业id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业名',
  `sort` bigint(20) unsigned NOT NULL COMMENT '序列',
  `d_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `key_dId` (`d_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES ('1', '系统工程师', '0', '1', '2019-10-23 08:32:56');
INSERT INTO `job` VALUES ('2', '董事长', '0', '1', '2019-10-31 00:24:41');
INSERT INTO `job` VALUES ('3', '总经理', '1', '2', '2019-10-31 00:25:28');
INSERT INTO `job` VALUES ('4', '销售经理', '2', '3', '2019-10-31 00:25:50');
INSERT INTO `job` VALUES ('14', '采购经理', '2', '4', '2019-11-28 11:22:33');
INSERT INTO `job` VALUES ('15', '销售人员', '3', '3', '2019-11-28 11:23:30');
INSERT INTO `job` VALUES ('16', '采购人员', '3', '4', '2019-11-28 11:23:50');

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `id` bigint(20) unsigned zerofill NOT NULL COMMENT '药品id',
  `name` varchar(15) NOT NULL COMMENT '药品名',
  `mode` tinyint(4) NOT NULL COMMENT '0内服1外用',
  `dosage` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '使用量',
  `efficacy` varchar(100) NOT NULL COMMENT '使用功效',
  `description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组成成份',
  `caution` varchar(100) NOT NULL COMMENT '注意事项',
  `inventory` int(10) unsigned NOT NULL COMMENT '库存量',
  `product_time` datetime NOT NULL COMMENT '生产日期',
  `expire_time` datetime NOT NULL COMMENT '过期日期',
  PRIMARY KEY (`id`),
  KEY `medicine_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品表';

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES ('01199921637554769921', '夏桑菊颗粒', '0', '一次1~2袋（10~20克），一日2~3次', '清肝明目，疏风散热，除湿痹，解疮毒。用于风热感冒，目赤头痛，头晕耳鸣，咽喉肿痛等', '夏枯草、野菊花、桑叶。辅料为蔗糖', '不宜在服药期间同时服用滋补性中成药。过敏者慎用', '2', '2019-11-01 08:00:00', '2020-11-01 08:00:00');
INSERT INTO `medicine` VALUES ('01200047832837513218', '玄麦甘桔颗粒', '0', '一次1袋，一日3~4次。', '清热滋阴，祛痰利咽。用于阴虚火旺，虚火上浮，口鼻干燥，咽喉肿痛。', '玄参、麦冬、甘草、桔梗。辅料为蔗糖、糊精。', '对本品过敏者禁用，过敏体质者慎用。忌烟酒、辛辣、鱼腥食物。', '3', '2019-10-16 08:00:00', '2020-12-16 08:00:00');
INSERT INTO `medicine` VALUES ('01200048785447837697', '复方金银花颗粒', '0', '一次10～20克，一日2～3次。', '一次10～20克　，一日2～3次。', '清热解毒，凉血消肿。用于风热感冒，咽炎，扁桃体炎，目痛，牙痛及痈肿疮疖。', '宜在服药期间同时服用滋补性中成药。脾胃虚寒，症见腹痛、喜暖、泄泻者慎用。忌烟、酒及辛辣、生冷、油腻食物。对本品过敏者禁用，过敏体质者慎用。', '2', '2019-11-13 08:00:00', '2020-11-13 08:00:00');
INSERT INTO `medicine` VALUES ('01200052078395908098', '双黄连口服液', '0', '一次2支，一日3次。', '疏风解表、清热解毒。', '金银花、黄芩、连翘；辅料为蔗糖、香精。', '风寒感冒者不适用。对本品过敏者禁用，过敏体质者慎用。使用时需注意吸管插入方式，如产生胶塞落屑，慎用。', '3', '2019-11-11 08:00:00', '2020-11-11 08:00:00');
INSERT INTO `medicine` VALUES ('01200053643873738753', '板蓝根颗粒', '0', '一次半～1袋（5～10克），一日3～4次。', '清热解毒，凉血利咽。用于肺胃热盛所致的咽喉肿痛、口咽干燥；急性扁桃体炎见上述证候者。', '板蓝根。辅料为糊精、蔗糖。', '不宜在服药期间同时服用滋补性中药。对本品过敏者禁用，过敏体质者慎用。糖尿病患者及有高血压、心脏病、肝病、肾病等慢性病严重者应在医师指导下服用。', '0', '2019-11-28 08:00:00', '2020-12-28 08:00:00');
INSERT INTO `medicine` VALUES ('01200061970242461697', '复方氨酚烷胺胶囊', '0', '成人，一次1粒，一日2次。', '用于缓解普通感冒及流行性感冒引起的发热、头痛、鼻塞、咽痛等症状，也可用于流行性感冒的预防和治疗。', '本品为复方制剂，每粒含对乙酰氨基酚250毫克、盐酸金刚烷胺100毫克、马来酸氯苯那敏2毫克、人工牛黄10毫克、咖啡因15毫克。辅料为淀粉、聚维酮K-30。', '服用本品期间不得饮酒或含有酒精的饮料。肝功能不全、肾功能不全、脑血管病史、精神病史或癫痫病史患者慎用。孕妇及哺乳期妇女慎用。', '2', '2019-11-28 08:00:00', '2020-11-28 08:00:00');
INSERT INTO `medicine` VALUES ('01200065422234345473', '香砂养胃丸', '0', '一次9克，一日2次。', '用于胃阳不足、湿阻气滞所致的胃痛、痞满，症见胃痛隐隐、脘闷不舒、呕吐酸水、嘈杂不适、不思饮食、四肢倦怠。', '木香，砂仁，白术，陈皮，茯苓，半夏（制），醋香附，枳实（炒），豆蔻（去壳），姜厚朴，广藿香，甘草', '本品宜用温开水送服。忌生冷油腻食物。', '3', '2019-11-18 08:00:00', '2020-11-18 08:00:00');
INSERT INTO `medicine` VALUES ('01200066625110401025', '消炎止咳片', '0', '一次2片，一日3次。', '消炎，镇咳，化痰，定喘。用于咳嗽痰多，胸满气短，气管炎。', '胡颓子叶、桔梗、太子参、百部、罂粟壳、麻黄、黄荆子、南沙参、穿心莲。', '尚不明确。', '2', '2019-11-02 08:00:00', '2020-11-02 08:00:00');
INSERT INTO `medicine` VALUES ('01200068474689413121', '烧烫伤膏', '1', '', '清热解毒，消肿止痛。用于轻度水、火烫伤。', '獾油、地榆、大黄、冰片、虫白蜡、无水羊毛脂、蜂蜡、茉莉香精、白凡士林。', '本品为外用药，禁止内服。用药后局部出现皮疹等过敏表现者应停用。对本品过敏者禁用，过敏体质者慎用。', '3', '2019-11-06 08:00:00', '2021-05-06 08:00:00');
INSERT INTO `medicine` VALUES ('01200069602265128961', '999感冒灵颗粒', '0', '一次1袋，一日3次。', '本品解热镇痛。用于感冒引起的头痛，发热，鼻塞，流涕，咽痛等。', '三叉苦、岗梅、金盏银盘、薄荷油、野菊花、马来酸氯苯那敏、咖啡因、对乙酰氨基酚。辅料为蔗糖粉。', '不宜在服药期间同时服用滋补性中成药。脾胃虚寒，症见腹痛，喜暖，泄泻者慎用。服药3天后症状无改善，或症状加重，或出现新的严重症状如胸闷，心悸等应立即停药，并去医院就诊。', '7', '2019-10-08 08:00:00', '2020-10-08 08:00:00');
INSERT INTO `medicine` VALUES ('01200070295063486465', '蓝芩口服液', '0', '一次10ml，一日3次。', '清热解毒，急性咽炎、肺胃实热证所致的咽痛、咽干、咽部灼热。', '板蓝根、黄芩、栀子、黄柏、胖大海。', '不宜在服药期间同时服用温补性中药。对本品过敏者禁用，过敏体质者慎用。脾虚大便溏者慎用。', '3', '2019-06-05 08:00:00', '2020-11-05 08:00:00');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) unsigned NOT NULL COMMENT '菜单id',
  `name` varchar(20) NOT NULL COMMENT '菜单名',
  `component` varchar(50) DEFAULT NULL COMMENT '组件',
  `sort` bigint(20) unsigned NOT NULL COMMENT '序列',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '上级菜单',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', null, '1', 'system', '\0', 'system', '0', '2019-10-23 18:39:22');
INSERT INTO `menu` VALUES ('2', '用户管理', 'system/user/index', '2', 'users', '\0', 'user', '1', '2019-10-13 10:09:54');
INSERT INTO `menu` VALUES ('3', '角色管理', 'system/role/index', '3', 'role', '\0', 'role', '1', '2019-10-17 18:24:20');
INSERT INTO `menu` VALUES ('4', '部门管理', 'system/dept/index', '4', 'dept', '\0', 'dept', '1', '2019-10-27 23:47:07');
INSERT INTO `menu` VALUES ('5', '岗位管理', 'system/job/index', '5', 'job', '\0', 'job', '1', '2019-10-27 23:47:46');
INSERT INTO `menu` VALUES ('6', '业务管理', null, '1', 'service', '\0', 'service', '0', '2019-11-17 13:57:45');
INSERT INTO `menu` VALUES ('7', '药品管理', 'service/medicine/index', '2', 'medicine', '\0', 'medicine', '6', '2019-11-17 14:04:39');
INSERT INTO `menu` VALUES ('8', '供应商管理', 'service/supplier', '3', 'supplier', '\0', 'supplier', '6', '2019-11-17 14:06:46');
INSERT INTO `menu` VALUES ('9', '销售管理', 'service/sale/index', '4', 'sale', '\0', 'sale', '6', '2019-11-17 14:07:28');
INSERT INTO `menu` VALUES ('10', '采购管理', 'service/purchase/index', '5', 'purchase', '\0', 'purchase', '6', '2019-11-17 14:08:09');
INSERT INTO `menu` VALUES ('11', '顾客管理', 'service/customer/index', '6', 'customer', '\0', 'customer', '6', '2019-11-19 19:23:52');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `name` varchar(20) NOT NULL COMMENT '权限名',
  `alias` varchar(20) NOT NULL COMMENT '别名',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '上级权限',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'ADMIN', '系统管理', '0', '2019-10-23 08:38:26');
INSERT INTO `permission` VALUES ('2', 'ROLE_ALL', '角色管理', '1', '2019-11-01 10:32:40');
INSERT INTO `permission` VALUES ('3', 'ROLE_SELECT', '查询角色', '2', '2019-11-01 10:35:18');
INSERT INTO `permission` VALUES ('4', 'ROLE_CREATE', '新建角色', '2', '2019-11-29 08:44:32');
INSERT INTO `permission` VALUES ('5', 'ROLE_EDIT', '编辑角色', '2', '2019-11-29 08:45:54');
INSERT INTO `permission` VALUES ('6', 'ROLE_DELETE', '删除角色', '2', '2019-11-29 08:46:14');
INSERT INTO `permission` VALUES ('7', 'USER_ALL', '用户管理', '1', '2019-11-29 08:46:40');
INSERT INTO `permission` VALUES ('8', 'USER_SELECT', '查询用户', '7', '2019-11-29 08:46:59');
INSERT INTO `permission` VALUES ('9', 'USER_CREATE', '新建用户', '7', '2019-11-29 08:47:21');
INSERT INTO `permission` VALUES ('10', 'USER_EDIT', '编辑用户', '7', '2019-11-29 08:47:40');
INSERT INTO `permission` VALUES ('11', 'USER_DELETE', '删除用户', '7', '2019-11-29 08:48:21');
INSERT INTO `permission` VALUES ('12', 'PERMISSION_ALL', '权限管理', '1', '2019-11-29 08:49:26');
INSERT INTO `permission` VALUES ('13', 'PERMISSION_SELECT', '查询权限', '12', '2019-11-29 08:50:40');
INSERT INTO `permission` VALUES ('14', 'PERMISSION_CREATE', '新建权限', '12', '2019-11-29 08:51:01');
INSERT INTO `permission` VALUES ('15', 'PERMISSION_EDIT', '编辑权限', '12', '2019-11-29 08:51:24');
INSERT INTO `permission` VALUES ('16', 'PERMISSION_DELETE', '删除权限', '12', '2019-11-29 08:51:49');
INSERT INTO `permission` VALUES ('17', 'DEPT_ALL', '部门管理', '1', '2019-11-29 08:52:24');
INSERT INTO `permission` VALUES ('18', 'DEPT_SELECT', '查询部门', '17', '2019-11-29 08:52:58');
INSERT INTO `permission` VALUES ('19', 'DEPT_CREATE', '新建部门', '17', '2019-11-29 08:53:21');
INSERT INTO `permission` VALUES ('20', 'DEPT_EDIT', '编辑部门 ', '17', '2019-11-29 08:53:38');
INSERT INTO `permission` VALUES ('21', 'DEPT_DELETE', '删除部门', '17', '2019-11-29 08:53:56');
INSERT INTO `permission` VALUES ('22', 'JOB_ALL', '岗位管理', '1', '2019-11-29 08:54:30');
INSERT INTO `permission` VALUES ('23', 'JOB_SELECT', '查询岗位', '22', '2019-11-29 08:54:46');
INSERT INTO `permission` VALUES ('24', 'JOB_CREATE', '新建岗位', '22', '2019-11-29 08:55:11');
INSERT INTO `permission` VALUES ('25', 'JOB_EDIT', '编辑岗位', '22', '2019-11-29 08:55:30');
INSERT INTO `permission` VALUES ('26', 'JOB_DELETE', '删除岗位', '22', '2019-11-29 08:57:17');
INSERT INTO `permission` VALUES ('27', 'SERVICE', '业务管理', '0', '2019-11-29 09:57:49');
INSERT INTO `permission` VALUES ('28', 'MEDICINE_ALL', '药品管理', '27', '2019-11-29 10:04:32');
INSERT INTO `permission` VALUES ('29', 'MEDICINE_SELECT', '查询药品', '28', '2019-11-29 10:04:54');
INSERT INTO `permission` VALUES ('30', 'MEDICINE_CREATE', '新建药品', '28', '2019-11-29 10:05:10');
INSERT INTO `permission` VALUES ('31', 'MEDICINE_EDIT', '编辑药品', '28', '2019-11-29 10:05:24');
INSERT INTO `permission` VALUES ('32', 'SUPPLIER_ALL', '供应商管理', '27', '2019-11-29 10:06:10');
INSERT INTO `permission` VALUES ('33', 'SUPPLIER_SELECT', '查询供应商', '32', '2019-11-29 10:07:30');
INSERT INTO `permission` VALUES ('34', 'SUPPLIER_CREATE', '新建供应商', '32', '2019-11-29 10:07:55');
INSERT INTO `permission` VALUES ('35', 'SUPPLIER_EDIT', '编辑供应商', '32', '2019-11-29 10:08:11');
INSERT INTO `permission` VALUES ('36', 'SUPPLIER_DELETE', '删除供应商', '32', '2019-11-29 10:08:33');
INSERT INTO `permission` VALUES ('37', 'CUSTOMER_ALL', '顾客管理', '27', '2019-11-29 10:09:05');
INSERT INTO `permission` VALUES ('38', 'CUSTOMER_SELECT', '查询顾客', '37', '2019-11-29 10:09:38');
INSERT INTO `permission` VALUES ('39', 'CUSTOMER_CREATE', '新建顾客', '37', '2019-11-29 10:10:24');
INSERT INTO `permission` VALUES ('40', 'CUSTOMER_EDIT', '编辑顾客', '37', '2019-11-29 10:11:08');
INSERT INTO `permission` VALUES ('41', 'CUSTOMER_DELETE', '删除顾客', '37', '2019-11-29 10:11:29');
INSERT INTO `permission` VALUES ('42', 'PURCHASE_ALL', '采购管理', '27', '2019-11-29 10:12:08');
INSERT INTO `permission` VALUES ('43', 'PURCHASE_SELECT', '查询采购记录', '42', '2019-11-29 10:12:30');
INSERT INTO `permission` VALUES ('44', 'PURCHASE_CREATE', '新建采购记录', '42', '2019-11-29 10:12:46');
INSERT INTO `permission` VALUES ('45', 'PURCHASE_EDIT', '编辑采购记录', '42', '2019-11-29 10:13:04');
INSERT INTO `permission` VALUES ('46', 'PURCHASE_DELETE', '删除采购记录', '42', '2019-11-29 10:13:26');
INSERT INTO `permission` VALUES ('47', 'SALE_ALL', '销售管理', '27', '2019-11-29 10:13:54');
INSERT INTO `permission` VALUES ('48', 'SALE_SELECT', '查询销售记录', '47', '2019-11-29 10:14:14');
INSERT INTO `permission` VALUES ('49', 'SALE_CREATE', '新建销售记录', '47', '2019-11-29 10:14:39');
INSERT INTO `permission` VALUES ('50', 'SALE_EDIT', '编辑销售记录', '47', '2019-11-29 10:14:54');
INSERT INTO `permission` VALUES ('51', 'SALE_DELETE', '删除销售记录', '47', '2019-11-29 10:15:14');
INSERT INTO `permission` VALUES ('52', 'MEDICINE_DELETE', '删除药品', '28', '2019-11-29 10:18:00');

-- ----------------------------
-- Table structure for purchase_record
-- ----------------------------
DROP TABLE IF EXISTS `purchase_record`;
CREATE TABLE `purchase_record` (
  `id` bigint(20) unsigned NOT NULL COMMENT '采购记录id',
  `medicine_id` bigint(20) unsigned NOT NULL COMMENT '药品id',
  `supplier_id` bigint(20) unsigned NOT NULL COMMENT '供应商id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '采购人员id',
  `purchase_count` bigint(20) NOT NULL COMMENT '采购数目',
  `purchase_price` decimal(7,2) NOT NULL COMMENT '采购单价',
  `purchase_time` datetime NOT NULL COMMENT '采购时间',
  PRIMARY KEY (`id`),
  KEY `sale_medicine_id_index` (`medicine_id`),
  KEY `sale_supplier_id_index` (`supplier_id`),
  KEY `sale_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购记录表';

-- ----------------------------
-- Records of purchase_record
-- ----------------------------
INSERT INTO `purchase_record` VALUES ('1197115579895410690', '1200066625110401025', '1197089641405112322', '11', '3', '123.00', '2019-11-21 03:32:00');
INSERT INTO `purchase_record` VALUES ('1197125547956666370', '1200048785447837697', '1200240645596332033', '2', '2', '123.00', '2019-11-21 04:12:00');
INSERT INTO `purchase_record` VALUES ('1197126141324836866', '1200047832837513218', '1197089641405112322', '9', '2', '123.00', '2019-11-21 04:14:00');
INSERT INTO `purchase_record` VALUES ('1197126491477934081', '1199921637554769921', '1197121304981934082', '3', '2', '213.00', '2019-11-29 08:00:00');
INSERT INTO `purchase_record` VALUES ('1200245329786650625', '1200066625110401025', '1200240645596332033', '11', '1', '50.00', '2019-11-29 18:46:00');
INSERT INTO `purchase_record` VALUES ('1200245647798779905', '1200070295063486465', '1197121304981934082', '11', '2', '30.00', '2019-11-29 18:50:00');
INSERT INTO `purchase_record` VALUES ('1201004968396460034', '1200065422234345473', '1200240323096297473', '11', '1', '20.00', '2019-12-01 21:07:00');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `level` int(10) unsigned DEFAULT NULL COMMENT '级数',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '系统的至高管理者', '1', '2019-10-23 08:32:08');
INSERT INTO `role` VALUES ('12', '董事长', '管理者', '1', '2019-11-27 21:13:55');
INSERT INTO `role` VALUES ('13', '总经理', '权限较高者', '2', '2019-11-27 21:14:20');
INSERT INTO `role` VALUES ('14', '销售经理', '负责销售部分', '3', '2019-11-27 21:14:39');
INSERT INTO `role` VALUES ('15', '采购经理', '负责采购模块', '3', '2019-11-27 21:15:43');
INSERT INTO `role` VALUES ('16', '销售人员', '普通员工', '4', '2019-11-27 21:16:14');
INSERT INTO `role` VALUES ('17', '采购人员', '普通员工', '4', '2019-11-27 21:16:40');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `r_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  `m_id` bigint(20) unsigned DEFAULT NULL COMMENT '菜单id',
  KEY `key_rId` (`r_id`),
  KEY `key_mId` (`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('1', '6');
INSERT INTO `role_menu` VALUES ('1', '7');
INSERT INTO `role_menu` VALUES ('1', '8');
INSERT INTO `role_menu` VALUES ('1', '9');
INSERT INTO `role_menu` VALUES ('1', '10');
INSERT INTO `role_menu` VALUES ('1', '11');
INSERT INTO `role_menu` VALUES ('12', '6');
INSERT INTO `role_menu` VALUES ('12', '7');
INSERT INTO `role_menu` VALUES ('12', '1');
INSERT INTO `role_menu` VALUES ('12', '8');
INSERT INTO `role_menu` VALUES ('12', '4');
INSERT INTO `role_menu` VALUES ('12', '11');
INSERT INTO `role_menu` VALUES ('12', '3');
INSERT INTO `role_menu` VALUES ('12', '10');
INSERT INTO `role_menu` VALUES ('12', '5');
INSERT INTO `role_menu` VALUES ('12', '9');
INSERT INTO `role_menu` VALUES ('12', '2');
INSERT INTO `role_menu` VALUES ('13', '6');
INSERT INTO `role_menu` VALUES ('13', '7');
INSERT INTO `role_menu` VALUES ('13', '8');
INSERT INTO `role_menu` VALUES ('13', '11');
INSERT INTO `role_menu` VALUES ('13', '10');
INSERT INTO `role_menu` VALUES ('13', '9');
INSERT INTO `role_menu` VALUES ('14', '6');
INSERT INTO `role_menu` VALUES ('14', '7');
INSERT INTO `role_menu` VALUES ('14', '11');
INSERT INTO `role_menu` VALUES ('14', '9');
INSERT INTO `role_menu` VALUES ('16', '6');
INSERT INTO `role_menu` VALUES ('16', '7');
INSERT INTO `role_menu` VALUES ('16', '11');
INSERT INTO `role_menu` VALUES ('16', '9');
INSERT INTO `role_menu` VALUES ('15', '6');
INSERT INTO `role_menu` VALUES ('15', '7');
INSERT INTO `role_menu` VALUES ('15', '8');
INSERT INTO `role_menu` VALUES ('15', '10');
INSERT INTO `role_menu` VALUES ('17', '6');
INSERT INTO `role_menu` VALUES ('17', '7');
INSERT INTO `role_menu` VALUES ('17', '8');
INSERT INTO `role_menu` VALUES ('17', '10');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `r_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `p_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  KEY `key_rId` (`r_id`),
  KEY `key_pId` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('4', '3');
INSERT INTO `role_permission` VALUES ('5', '3');
INSERT INTO `role_permission` VALUES ('6', '3');
INSERT INTO `role_permission` VALUES ('1', '30');
INSERT INTO `role_permission` VALUES ('1', '14');
INSERT INTO `role_permission` VALUES ('1', '21');
INSERT INTO `role_permission` VALUES ('1', '5');
INSERT INTO `role_permission` VALUES ('1', '12');
INSERT INTO `role_permission` VALUES ('1', '44');
INSERT INTO `role_permission` VALUES ('1', '48');
INSERT INTO `role_permission` VALUES ('1', '16');
INSERT INTO `role_permission` VALUES ('1', '39');
INSERT INTO `role_permission` VALUES ('1', '7');
INSERT INTO `role_permission` VALUES ('1', '34');
INSERT INTO `role_permission` VALUES ('1', '10');
INSERT INTO `role_permission` VALUES ('1', '18');
INSERT INTO `role_permission` VALUES ('1', '19');
INSERT INTO `role_permission` VALUES ('1', '25');
INSERT INTO `role_permission` VALUES ('1', '9');
INSERT INTO `role_permission` VALUES ('1', '28');
INSERT INTO `role_permission` VALUES ('1', '40');
INSERT INTO `role_permission` VALUES ('1', '52');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('1', '37');
INSERT INTO `role_permission` VALUES ('1', '43');
INSERT INTO `role_permission` VALUES ('1', '49');
INSERT INTO `role_permission` VALUES ('1', '46');
INSERT INTO `role_permission` VALUES ('1', '22');
INSERT INTO `role_permission` VALUES ('1', '38');
INSERT INTO `role_permission` VALUES ('1', '6');
INSERT INTO `role_permission` VALUES ('1', '15');
INSERT INTO `role_permission` VALUES ('1', '47');
INSERT INTO `role_permission` VALUES ('1', '31');
INSERT INTO `role_permission` VALUES ('1', '24');
INSERT INTO `role_permission` VALUES ('1', '36');
INSERT INTO `role_permission` VALUES ('1', '45');
INSERT INTO `role_permission` VALUES ('1', '27');
INSERT INTO `role_permission` VALUES ('1', '13');
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('1', '33');
INSERT INTO `role_permission` VALUES ('1', '26');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '42');
INSERT INTO `role_permission` VALUES ('1', '50');
INSERT INTO `role_permission` VALUES ('1', '35');
INSERT INTO `role_permission` VALUES ('1', '51');
INSERT INTO `role_permission` VALUES ('1', '41');
INSERT INTO `role_permission` VALUES ('1', '20');
INSERT INTO `role_permission` VALUES ('1', '32');
INSERT INTO `role_permission` VALUES ('1', '8');
INSERT INTO `role_permission` VALUES ('1', '29');
INSERT INTO `role_permission` VALUES ('1', '23');
INSERT INTO `role_permission` VALUES ('1', '11');
INSERT INTO `role_permission` VALUES ('1', '17');
INSERT INTO `role_permission` VALUES ('12', '27');
INSERT INTO `role_permission` VALUES ('12', '1');
INSERT INTO `role_permission` VALUES ('15', '38');
INSERT INTO `role_permission` VALUES ('15', '42');
INSERT INTO `role_permission` VALUES ('15', '29');
INSERT INTO `role_permission` VALUES ('15', '33');
INSERT INTO `role_permission` VALUES ('13', '28');
INSERT INTO `role_permission` VALUES ('13', '32');
INSERT INTO `role_permission` VALUES ('13', '42');
INSERT INTO `role_permission` VALUES ('13', '37');
INSERT INTO `role_permission` VALUES ('13', '27');
INSERT INTO `role_permission` VALUES ('13', '47');
INSERT INTO `role_permission` VALUES ('14', '29');
INSERT INTO `role_permission` VALUES ('14', '37');
INSERT INTO `role_permission` VALUES ('14', '47');
INSERT INTO `role_permission` VALUES ('14', '33');
INSERT INTO `role_permission` VALUES ('16', '48');
INSERT INTO `role_permission` VALUES ('16', '29');
INSERT INTO `role_permission` VALUES ('16', '50');
INSERT INTO `role_permission` VALUES ('16', '49');
INSERT INTO `role_permission` VALUES ('16', '33');
INSERT INTO `role_permission` VALUES ('17', '44');
INSERT INTO `role_permission` VALUES ('17', '45');
INSERT INTO `role_permission` VALUES ('17', '29');
INSERT INTO `role_permission` VALUES ('17', '43');
INSERT INTO `role_permission` VALUES ('17', '33');

-- ----------------------------
-- Table structure for sale_record
-- ----------------------------
DROP TABLE IF EXISTS `sale_record`;
CREATE TABLE `sale_record` (
  `id` bigint(20) unsigned NOT NULL COMMENT '销售记录id',
  `medicine_id` bigint(20) unsigned NOT NULL COMMENT '药品id',
  `customer_id` bigint(20) unsigned NOT NULL COMMENT '顾客id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '采购人员id',
  `sale_count` int(10) unsigned NOT NULL COMMENT '销售总数',
  `sale_price` decimal(7,2) NOT NULL COMMENT '销售价格',
  `sale_time` datetime NOT NULL COMMENT '销售时间',
  PRIMARY KEY (`id`),
  KEY `sale_medicine_id_index` (`medicine_id`),
  KEY `sale_customer_id_index` (`customer_id`),
  KEY `sale_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售记录表';

-- ----------------------------
-- Records of sale_record
-- ----------------------------
INSERT INTO `sale_record` VALUES ('1200085490334777346', '1199921637554769921', '1200072569613574146', '10', '1', '20.00', '2019-11-29 08:14:00');
INSERT INTO `sale_record` VALUES ('1200241564836773890', '1200069602265128961', '1197081579957043201', '9', '1', '123.00', '2019-11-29 18:34:00');
INSERT INTO `sale_record` VALUES ('1200242158695694337', '1200069602265128961', '1197106243169267713', '8', '2', '60.50', '2019-11-29 18:36:00');
INSERT INTO `sale_record` VALUES ('1200242899049062401', '1200069602265128961', '1197106243169267713', '8', '1', '50.25', '2019-11-29 18:39:00');
INSERT INTO `sale_record` VALUES ('1200243519839539202', '1200048785447837697', '1200072569613574146', '10', '1', '10.50', '2019-11-29 18:42:00');
INSERT INTO `sale_record` VALUES ('1200243787658432513', '1199921637554769921', '1200074767118475265', '10', '1', '50.00', '2019-11-29 18:43:00');
INSERT INTO `sale_record` VALUES ('1201000100101361665', '1200053643873738753', '1197106243169267713', '10', '1', '20.00', '2019-12-01 20:48:00');
INSERT INTO `sale_record` VALUES ('1201001524281479170', '1200065422234345473', '1201000445640708097', '10', '1', '50.00', '2019-12-01 20:53:00');
INSERT INTO `sale_record` VALUES ('1201002606458048513', '1200052078395908098', '1197106243169267713', '10', '1', '20.00', '2019-12-01 20:58:00');
INSERT INTO `sale_record` VALUES ('1201014958695911426', '1200053643873738753', '1197081579957043201', '10', '1', '30.00', '2019-12-01 21:47:00');
INSERT INTO `sale_record` VALUES ('1201041229274488833', '1199921637554769921', '1197081579957043201', '10', '1', '21.00', '2019-12-01 15:31:00');
INSERT INTO `sale_record` VALUES ('1201048348832829442', '1200047832837513218', '1197081579957043201', '10', '1', '20.00', '2019-12-01 16:00:00');
INSERT INTO `sale_record` VALUES ('1201050867344609281', '1199921637554769921', '1197081579957043201', '10', '1', '23.00', '2019-12-01 16:10:00');
INSERT INTO `sale_record` VALUES ('1201052154916245505', '1199921637554769921', '1197081579957043201', '10', '1', '30.00', '2019-12-01 16:15:00');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` bigint(20) unsigned NOT NULL COMMENT '供应商id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '供应商名称',
  `phone` varchar(15) NOT NULL COMMENT '电话',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `description` varchar(150) DEFAULT NULL COMMENT '简介',
  `cooperation_time` datetime NOT NULL COMMENT '合作时间',
  PRIMARY KEY (`id`),
  KEY `supplier_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商表';

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('1197089641405112322', '供应商1', '15013922692', '广东省肇庆市肇庆学院', '公司主要是生产、批发药品', '2019-11-14 08:00:00');
INSERT INTO `supplier` VALUES ('1197121304981934082', '供应商2', '15013928363', '广东省肇庆市肇庆学院', '公司主要是生产、批发药品', '2019-11-20 19:55:00');
INSERT INTO `supplier` VALUES ('1200240323096297473', '供应商3', '15013928666', '广东省肇庆市肇庆学院', '公司主要是生产、批发药品', '2019-11-29 18:29:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(18) NOT NULL COMMENT '用户账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(15) NOT NULL COMMENT '姓名',
  `phone` varchar(15) NOT NULL COMMENT '电话号码',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '0男1女',
  `email` varchar(25) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(60) NOT NULL COMMENT '地址',
  `enabled` bit(1) DEFAULT b'1' COMMENT '1启用0禁用',
  `d_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门id',
  `j_id` bigint(20) unsigned DEFAULT NULL COMMENT '职位id',
  `modify_time` datetime NOT NULL COMMENT '更改日期',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_phone` (`phone`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `K_dId` (`d_id`),
  KEY `K_jId` (`j_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1156434215', '$2a$10$eLMrpUAcK7M.abuZ73Q5s.VdUN/oNNTgZA9x32.Q4TOmGno2zZfKG', 'BlackCat', '15013928697', '0', '1156434215@qq.com', '广东省肇庆市端州区肇庆学院映翠苑3区1栋', '', '1', '1', '2019-12-01 16:23:28', '2019-10-19 19:24:54');
INSERT INTO `user` VALUES ('2', '1156434214', '$2a$10$lbP7MgcnvRB7RNFp0rbXqOsMUg78Wl7HAI0zXNWwMGBSbGNjdD/G.', 'WhiteCat', '15013928692', '0', '1156434214@qq.com', '广东省肇庆市端州区肇庆学院映翠苑3区1栋', '', '1', '2', '2019-11-28 23:43:53', '2019-10-23 11:12:26');
INSERT INTO `user` VALUES ('3', '1156434217', '$2a$10$wdAAGyNt/oPspQW.6/KIc.7cLu4U9Vr.kpxAIPztc93gdWupV/HHa', 'BlueCat', '15013928693', '0', '11564342153@qq.com', '广东省肇庆市端州区肇庆学院映翠苑3区1栋', '', '2', '3', '2019-11-28 23:44:00', '2019-11-11 21:47:46');
INSERT INTO `user` VALUES ('8', '1156434112', '$2a$10$zN6Foe49vmE/Ah9M5l/1f.OEtOAfaO3y.P.Av3KVZYISqGYiQVYki', 'RedCat', '15013928691', '0', '1156434211@qq.com', '广东省肇庆市肇庆学院映翠苑1区1栋', '', '3', '4', '2019-11-28 23:39:16', '2019-11-28 23:39:16');
INSERT INTO `user` VALUES ('9', '1156434210', '$2a$10$gy0NJt0aIpyOt51e3E3WBuDNi5L.LlRzEksvoIXLIIUl26Tst2j2m', 'GreenCat', '15013928681', '0', '1156434210@qq.com', '广东省肇庆市肇庆学院映翠苑3区1栋', '', '4', '14', '2019-11-28 23:42:13', '2019-11-28 23:42:13');
INSERT INTO `user` VALUES ('10', '1156434111', '$2a$10$RJ8LeYs6hch5dfTeNSX0HOvwgR1nAWseLcY5YZGVb69MWCddmxT3W', 'PinkCat', '15013928112', '1', '1156434112@qq.com', '广东省肇庆市肇庆学院1区1栋', '', '3', '15', '2019-11-28 23:49:43', '2019-11-28 23:46:27');
INSERT INTO `user` VALUES ('11', '1156434130', '$2a$10$OwDFyCrp9CC5IHXUPoBUaedj.7dcliaTvrgdUW1kvPvT12X6XG8Oi', 'PurpleCat', '15013872112', '1', '1156434130@qq.com', '广东省肇庆市肇庆学院2区1栋', '', '4', '16', '2019-12-06 10:40:37', '2019-11-28 23:51:32');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `u_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
  `r_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  KEY `key_uId` (`u_id`),
  KEY `key_rId` (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('8', '14');
INSERT INTO `user_role` VALUES ('9', '15');
INSERT INTO `user_role` VALUES ('2', '12');
INSERT INTO `user_role` VALUES ('3', '13');
INSERT INTO `user_role` VALUES ('10', '16');
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('11', '17');
