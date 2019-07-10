/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50709
 Source Host           : localhost:3306
 Source Schema         : padis

 Target Server Type    : MySQL
 Target Server Version : 50709
 File Encoding         : 65001

 Date: 09/07/2019 22:44:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_apply
-- ----------------------------
DROP TABLE IF EXISTS `m_apply`;
CREATE TABLE `m_apply`  (
  `apply_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `apply_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请名称',
  `apply_user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人',
  `user_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人编号（警员编号）',
  `certify_file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明文件名称',
  `certify_file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明文件路径',
  `apply_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请描述，需要的人员和数据',
  `apply_file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请表名称',
  `apply_file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请表路径',
  `apply_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '0保存状态 1申请中 2已处理',
  `apply_deal_file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务处理上传文件名称',
  `apply_deal_file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务处理上传文件路径',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `insert_time` timestamp(0) NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_apply
-- ----------------------------
INSERT INTO `m_apply` VALUES ('1', '1231', '4564', '23416', '456', '456', '456', '4', '5', '2', '1805031455485903527.doc', '/upload/deal/74041EB169894F758B0DAE31FBC29CE7.doc', '0', '1', '2019-07-08 19:36:14', '1', '2019-07-09 22:09:28');
INSERT INTO `m_apply` VALUES ('A55463039E9447428BBF40706F38A808', '第二次提取', '移动', '002', '1805031455486356400.xls', '/upload/certify/219CA02258134ED8A8566172EE93693D.xls', '士大夫士大夫士大夫', '信息提取表 (1).xlsx', '/upload/desc/0397335EB9C2428D98F90161BBB8935B.xlsx', '1', NULL, NULL, '0', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-09 20:14:17', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-09 20:14:17');
INSERT INTO `m_apply` VALUES ('B8805AE313A6436C93F73DA7D4B0AC35', '提取信息', '移动', '002', NULL, '/upload/certify/E8B29FF6EA844D0EB2E8EDC8207E7160.xls', '提取xxx通话记录', NULL, '/upload/desc/022925E9F2DD4E77B71BC6247A0EFCAA.xls', '2', '1805031455486356400.xls', '/upload/deal/B2D4943267AF403C9D84E08715C97DAD.xls', '0', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-09 20:08:54', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-09 22:09:12');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一标识',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
  `user_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `user_role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色 1移动 2公安',
  `user_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `user_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `insert_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `insert_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `unique_user_name`(`user_name`) USING BTREE,
  UNIQUE INDEX `unique_user_phone`(`user_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('40DD194251CB49B3AFCCCCDC91307819', '移动', '17852824021', '123456', '2', '移动', '002', 'system', '2019-07-06 14:54:08', 'system', '2019-07-07 18:16:56');
INSERT INTO `sys_user` VALUES ('DC69A83278CB4B219C3019886A49776A', '公安', '17852824020', '123456', '1', '公安', '001', 'system', '2019-07-06 14:51:17', 'system', '2019-07-06 14:54:00');

SET FOREIGN_KEY_CHECKS = 1;
