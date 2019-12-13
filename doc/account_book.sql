/*
 Navicat Premium Data Transfer

 Source Server         : my_banwagong
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : db.duanfanyao.com:3306
 Source Schema         : account_book

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 23/11/2019 20:55:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for BaseFabricDetail
-- ----------------------------
DROP TABLE IF EXISTS `BaseFabricDetail`;
CREATE TABLE `BaseFabricDetail` (
  `fabric_code` varchar(10) NOT NULL COMMENT '布料编号',
  `fabric_name` varchar(50) DEFAULT NULL COMMENT '布料名称',
  `fabric_colour` varchar(4) DEFAULT NULL COMMENT '布料颜色',
  `fabric_weight` int(4) DEFAULT NULL COMMENT '布料克重',
  `fabric_width_meter` double(3,3) DEFAULT NULL COMMENT '布料幅宽(米)',
  `fabric_width_foot` int(4) DEFAULT NULL COMMENT '布料幅宽(英尺)',
  PRIMARY KEY (`fabric_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布料明细表';

-- ----------------------------
-- Table structure for BaseFabricUsage
-- ----------------------------
DROP TABLE IF EXISTS `BaseFabricUsage`;
CREATE TABLE `BaseFabricUsage` (
  `id` int(11) NOT NULL,
  `product_code` varchar(60) DEFAULT NULL COMMENT '产品编码',
  `type_number` varchar(40) DEFAULT NULL COMMENT '板型编号',
  `binding_quantity` int(3) DEFAULT NULL COMMENT '每扎数量',
  `flap_length` varchar(7) DEFAULT NULL COMMENT '挡风长度',
  `ton_flap` varchar(1) DEFAULT NULL COMMENT '挡风双面胶',
  `fabric_code` varchar(10) DEFAULT NULL COMMENT '布料编号',
  `fabric_width` double(2,2) DEFAULT NULL COMMENT '布料幅宽',
  `fabric_colour` varchar(3) DEFAULT NULL COMMENT '布料颜色',
  `box_quantity` int(3) DEFAULT NULL COMMENT '每箱数量',
  `pieces_number` int(3) DEFAULT NULL COMMENT '排版件数',
  `mark_width` varchar(12) DEFAULT NULL COMMENT '马克长度（米）',
  `max_floor_height` varchar(12) DEFAULT NULL COMMENT '最大拉布层高',
  `change_rate` varchar(12) DEFAULT NULL COMMENT '换片率',
  `fabric1_usage` varchar(12) DEFAULT NULL COMMENT '单件主面料用量 (米)',
  `fabric2_usage` varchar(12) DEFAULT NULL COMMENT 'WH/NV15后背用，单件用量(米)',
  `fabric3_usage` varchar(12) DEFAULT NULL COMMENT 'BLSS幅宽1.53米鞋底用，单件用量 (米)',
  `fabric4_usage` varchar(12) DEFAULT NULL COMMENT 'PVC鞋底幅宽1.372米，单件用量 (米)',
  `fabric5_usage` varchar(12) DEFAULT NULL COMMENT '透明面罩幅宽1.2米，单件用量 (米)',
  `fabric6_usage` varchar(12) DEFAULT NULL COMMENT 'WH25幅宽1.53米气管连接用，单件用量(米)',
  `fabric7_usage` varchar(12) DEFAULT NULL COMMENT 'BL15围脖用，单件用量(米)',
  `fabric8_usage` varchar(12) DEFAULT NULL COMMENT '其他单件用量(米)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品布料用量表';

-- ----------------------------
-- Records of BaseFabricUsage
-- ----------------------------
BEGIN;
INSERT INTO `BaseFabricUsage` VALUES (1, '1', '1', 1, '1', '1', '1', 0.99, '1', 1, 1, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `BaseFabricUsage` VALUES (2, '2', '2', 2, '2', '2', '2', 0.99, '2', 2, 2, '2', '2', '2', '2', '2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `BaseFabricUsage` VALUES (3, '3', '3', 3, '3', '3', '3', 0.99, '3', 3, 3, '3', '3', '3', '3', '3', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for BaseHPW
-- ----------------------------
DROP TABLE IF EXISTS `BaseHPW`;
CREATE TABLE `BaseHPW` (
  `id` int(11) NOT NULL,
  `product_group_code` varchar(10) DEFAULT NULL COMMENT '产品组代码',
  `product_code` varchar(30) DEFAULT NULL COMMENT '产品编码(按接缝和款式)',
  `eng_id` varchar(20) DEFAULT NULL COMMENT '工程编码',
  `item_category_code` varchar(5) DEFAULT NULL COMMENT '物料目录代码',
  `group_item_Code` varchar(6) DEFAULT NULL COMMENT '集团物料编号',
  `bom_version` varchar(4) DEFAULT NULL COMMENT 'BOM 版本',
  `base_unit` varchar(3) DEFAULT NULL COMMENT '基本单位',
  `unit_volume` double(4,4) DEFAULT NULL COMMENT '单位体积',
  `size` varchar(8) DEFAULT NULL COMMENT '尺寸',
  `colour` varchar(2) DEFAULT NULL COMMENT '颜色',
  `box_quantity` int(4) DEFAULT NULL COMMENT '每箱数量',
  `descripton_cn` varchar(30) DEFAULT NULL COMMENT '中文描述',
  `descripton_en` varchar(50) DEFAULT NULL COMMENT '英文描述',
  `tariff_no` text COMMENT '关税编号',
  `tariff_description` varchar(50) DEFAULT NULL COMMENT '关税说明',
  `default_bin_code_mxl` varchar(10) DEFAULT NULL COMMENT 'mxl(货仓代码)',
  `default_bin_code_msl` varchar(10) DEFAULT NULL COMMENT 'msl(货仓代码)',
  `process_type_mxl` varchar(20) DEFAULT NULL COMMENT '贸易方式MXL',
  `process_type_msl` varchar(255) DEFAULT NULL COMMENT '贸易方式MSL',
  `gross_weight` double(2,2) DEFAULT NULL COMMENT '总毛重 ',
  `net_weight` double(3,3) DEFAULT NULL COMMENT '总净重',
  `net_weight_each` int(4) DEFAULT NULL COMMENT '单位净重',
  `bag_quantity` int(3) DEFAULT NULL COMMENT '每压缩袋数量',
  `binding_quantity` int(3) DEFAULT NULL COMMENT '每扎数量',
  `box_weight` int(4) DEFAULT NULL COMMENT '纸箱重量',
  `sales_uom` varchar(3) DEFAULT NULL COMMENT '销售单位',
  `seam_technology` varchar(2) DEFAULT NULL COMMENT '接缝工艺',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='HPW表';

-- ----------------------------
-- Records of BaseHPW
-- ----------------------------
BEGIN;
INSERT INTO `BaseHPW` VALUES (1, NULL, '1', '1', NULL, NULL, '1', '1', NULL, '1', '1', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `BaseHPW` VALUES (2, NULL, '2', '2', NULL, NULL, '2', NULL, NULL, '2', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `BaseHPW` VALUES (3, NULL, '3', '3', NULL, NULL, '3', NULL, NULL, '3', '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for BaseTailoringDistribution
-- ----------------------------
DROP TABLE IF EXISTS `BaseTailoringDistribution`;
CREATE TABLE `BaseTailoringDistribution` (
  `group` varchar(20) NOT NULL COMMENT '组别',
  `member1` varchar(10) DEFAULT NULL COMMENT '裁剪成员1',
  `member2` varchar(10) DEFAULT NULL COMMENT '裁剪成员2',
  `member3` varchar(10) DEFAULT NULL COMMENT '裁剪成员3',
  `percent1` int(3) DEFAULT NULL COMMENT '分配比例1',
  `percent2` int(3) DEFAULT NULL COMMENT '分配比例2',
  `percent3` int(3) DEFAULT NULL COMMENT '分配比例3',
  `percent` int(3) DEFAULT NULL COMMENT '分配比例合计',
  PRIMARY KEY (`group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪人员组合及超省分配比例表';

-- ----------------------------
-- Records of BaseTailoringDistribution
-- ----------------------------
BEGIN;
INSERT INTO `BaseTailoringDistribution` VALUES ('1', '1', '1', '1', 1, 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for Tailoring
-- ----------------------------
DROP TABLE IF EXISTS `Tailoring`;
CREATE TABLE `Tailoring` (
  `id` int(11) NOT NULL COMMENT '裁剪ID',
  `tailoring_no` varchar(32) DEFAULT NULL COMMENT '裁剪编号',
  `check_name` varchar(16) DEFAULT NULL COMMENT '审核人',
  `check_date` datetime DEFAULT NULL COMMENT '审核时间',
  `ERPFLAG` varchar(1) DEFAULT NULL COMMENT '回传ERP标记',
  `ERPID` varchar(16) DEFAULT NULL COMMENT '回传ERP ID',
  `ERPDATUM` datetime DEFAULT NULL COMMENT '回传ERP时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪表';

-- ----------------------------
-- Table structure for TailoringDetail
-- ----------------------------
DROP TABLE IF EXISTS `TailoringDetail`;
CREATE TABLE `TailoringDetail` (
  `id` int(11) NOT NULL COMMENT '裁剪明细ID',
  `tailoring_id` int(11) DEFAULT NULL COMMENT '裁剪ID',
  `tailoring_plan_id` int(11) DEFAULT NULL COMMENT '裁剪计划ID',
  `work_order_no` varchar(32) DEFAULT NULL COMMENT '工单号',
  `type_group` varchar(16) DEFAULT NULL COMMENT '版型分组',
  `product_line_no` int(11) DEFAULT NULL COMMENT '产品行号',
  `product_code` varchar(64) DEFAULT NULL COMMENT '产品编码',
  `product_quantity` int(11) DEFAULT NULL COMMENT '成品数量',
  `fabric_code` varchar(16) DEFAULT NULL COMMENT '布料编码',
  `sum_length` double(11,3) DEFAULT NULL COMMENT '总长度（米）',
  `theory_length` double(2,2) DEFAULT NULL COMMENT '理论长度（米）',
  `spreading_length` int(11) DEFAULT NULL COMMENT '拉布长度（米）',
  `spreading_count` int(11) DEFAULT NULL COMMENT '拉布次数',
  `floor` int(11) DEFAULT NULL COMMENT '层数（层高）',
  `quantity` int(11) DEFAULT NULL COMMENT '件数',
  `binding_quantity` int(11) DEFAULT NULL COMMENT '扎单数量',
  `change_pieces_quantity` int(11) DEFAULT NULL COMMENT '换片数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪明细表';

-- ----------------------------
-- Table structure for TailoringFabricLeft
-- ----------------------------
DROP TABLE IF EXISTS `TailoringFabricLeft`;
CREATE TABLE `TailoringFabricLeft` (
  `id` int(11) NOT NULL COMMENT '布头ID',
  `type` varchar(4) DEFAULT NULL COMMENT '类型（1：布头，2：废弃）',
  `fabric_code` varchar(16) DEFAULT NULL COMMENT '布料编码',
  `reel_number` int(11) DEFAULT NULL COMMENT '布料卷号',
  `lot_number` int(11) DEFAULT NULL COMMENT '布批号',
  `theory_length` double(2,2) DEFAULT NULL COMMENT '理论长度',
  `group` varchar(32) DEFAULT NULL COMMENT '组别',
  `date` datetime DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪布头表';

-- ----------------------------
-- Table structure for TailoringFabricRecord
-- ----------------------------
DROP TABLE IF EXISTS `TailoringFabricRecord`;
CREATE TABLE `TailoringFabricRecord` (
  `id` int(11) NOT NULL COMMENT '裁剪布料消耗ID',
  `tailoring_id` int(11) DEFAULT NULL COMMENT '裁剪ID',
  `fabric_code` varchar(16) DEFAULT NULL COMMENT '布料编码',
  `reel_number` int(11) DEFAULT NULL COMMENT '布料卷号',
  `lot_number` int(11) DEFAULT NULL COMMENT '布批号',
  `theory_length` double(2,2) DEFAULT NULL COMMENT '理论长度（米）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪布料消耗表';

-- ----------------------------
-- Table structure for TailoringPlan
-- ----------------------------
DROP TABLE IF EXISTS `TailoringPlan`;
CREATE TABLE `TailoringPlan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '裁剪计划ID',
  `work_order_no` varchar(32) DEFAULT NULL COMMENT '工单号',
  `status` varchar(2) DEFAULT NULL COMMENT '计划状态',
  `type_number` varchar(32) DEFAULT NULL COMMENT '版号',
  `group` varchar(16) DEFAULT NULL COMMENT '组别',
  `member` varchar(32) DEFAULT NULL COMMENT '组员',
  `main_supplement` varchar(2) DEFAULT NULL COMMENT '主/辅/补',
  `quantity` int(11) DEFAULT NULL COMMENT '计划数量',
  `due_date` datetime DEFAULT NULL COMMENT '出货日期',
  `product_line_no` int(11) DEFAULT NULL COMMENT '产品行号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `fabric_code` varchar(32) DEFAULT NULL COMMENT '布料编码',
  `fabric_width` double(2,2) DEFAULT NULL COMMENT '布料幅宽',
  `fabric_colour` varchar(4) DEFAULT NULL COMMENT '布料颜色',
  `box_quantity` int(11) DEFAULT NULL COMMENT '装箱数量',
  `binding_quantity` int(11) DEFAULT NULL COMMENT '扎单数量',
  `change_pieces_quantity` int(11) DEFAULT NULL COMMENT '换片数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='裁剪计划表';

-- ----------------------------
-- Records of TailoringPlan
-- ----------------------------
BEGIN;
INSERT INTO `TailoringPlan` VALUES (1, '1', '-1', '1', '1', '1,1,1', '辅', 1, '2019-11-02 20:38:36', 1, '1', '1', 0.99, '1', 1, 3, 5);
INSERT INTO `TailoringPlan` VALUES (2, '2', '-1', '2', '1', '1,1,1', '2', 2, '2019-11-11 17:32:07', NULL, '2', '2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `TailoringPlan` VALUES (3, '3', '-1', '3', '1', '1,1,1', '辅', NULL, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 1, NULL);
INSERT INTO `TailoringPlan` VALUES (4, '2', '-1', '2', '1', '1,1,1', '辅', 2, '2019-10-31 11:11:11', 2, '2', '2', 0.99, '2', 2, 2, 1);
INSERT INTO `TailoringPlan` VALUES (5, '3', '-1', '3', '1', '1,1,1', '主', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 3, 9);
INSERT INTO `TailoringPlan` VALUES (6, '3', '-1', '3', '1', '1,1,1', '主', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 6, 18);
INSERT INTO `TailoringPlan` VALUES (7, '3', '-1', '3', '1', '1,1,1', '补', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 3, 9);
INSERT INTO `TailoringPlan` VALUES (8, '3', '-1', '3', '1', '1,1,1', '主', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 3, 9);
INSERT INTO `TailoringPlan` VALUES (9, '2', '1', '2', '1', '1,1,1', '辅', 2, '2019-10-31 11:11:11', 2, '2', '2', 0.99, '2', 2, 2, 4);
INSERT INTO `TailoringPlan` VALUES (10, '2', '1', '2', '1', '1,1,1', '辅', 2, '2019-10-31 11:11:11', 2, '2', '2', 0.99, '2', 2, 2, 4);
INSERT INTO `TailoringPlan` VALUES (11, '2', '1', '2', '1', '1,1,1', '辅', 2, '2019-10-31 11:11:11', 2, '2', '2', 0.99, '2', 2, 2, 4);
INSERT INTO `TailoringPlan` VALUES (12, '3', '1', '3', '1', '1,1,1', '主', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 3, 9);
INSERT INTO `TailoringPlan` VALUES (13, '3', '1', '3', '1', '1,1,1', '辅', 3, '2019-10-30 12:12:12', 3, '3', '3', 0.99, '3', 3, 3, 9);
COMMIT;

-- ----------------------------
-- Table structure for TailoringRecoveryRecord
-- ----------------------------
DROP TABLE IF EXISTS `TailoringRecoveryRecord`;
CREATE TABLE `TailoringRecoveryRecord` (
  `id` int(11) NOT NULL COMMENT '冲销记录ID',
  `tailoring_detail_id` int(11) DEFAULT NULL COMMENT '裁剪明细ID',
  `recovery_quantity` double(11,3) DEFAULT NULL COMMENT '冲销数量',
  `recovery_name` varchar(16) DEFAULT NULL COMMENT '冲销人',
  `recovery_time` datetime DEFAULT NULL COMMENT '冲销时间',
  `ERPFLAG` varchar(2) DEFAULT NULL COMMENT '回传ERP标记',
  `ERPID` varchar(16) DEFAULT NULL COMMENT '回传ERP ID',
  `ERPDATUM` datetime DEFAULT NULL COMMENT '回传ERP时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裁剪明细冲销记录表';

SET FOREIGN_KEY_CHECKS = 1;
