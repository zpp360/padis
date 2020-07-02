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

 Date: 16/05/2020 19:23:21
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
  `user_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人编号（警员编号）',
  `apply_number` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子公函编码',
  `user_number_file_name1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '警员证1文件名',
  `user_number_file_path1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '警员证1文件路径',
  `user_number_file_name2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '警员证2文件名',
  `user_number_file_path2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '警员证2文件路径',
  `certify_file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子公函文件名称',
  `certify_file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子公函文件路径',
  `apply_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请描述，需要的人员和数据',
  `apply_file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请表名称',
  `apply_file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请表路径',
  `apply_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '1保存状态 2综合部审核中 3账目中心处理 4退回 5完成',
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
INSERT INTO `m_apply` VALUES ('4A66C6DDD3A6468693D3A8F65DD3A3D0', '提取张三信息', '王五，赵丽', '001，003', 'GB45664564', '372301199201082911郑鹏鹏.jpg', '/upload/usernumber/2D8E3FB8693D400786CAC0E370A862F8.jpg', '毕业证.jpg', '/upload/usernumber/C4B8E4C9A6F54E52A4B0AB0412168987.jpg', 'aaa.xlsx', '/upload/certify/42FBC301223F47C7BCA0041F9221305F.xlsx', '士大夫士大夫', '', '', '5', 'aaa.xlsx', '/upload/deal/8FAE54F9E6C542408CC8D0E363BE971A.xlsx', '0', 'DC69A83278CB4B219C3019886A49776A', '2019-07-21 11:44:31', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-21 15:33:57');
INSERT INTO `m_apply` VALUES ('52E3BE5CBD6C4DF197F8EB8111C98DE4', '提取张柳信息', '德州市国安局', '001', 'GB454564', '1122361563_15175774613641n.jpg', '/upload/usernumber/2C14EC9993474B0496F83BC3CA5B26FC.jpg', '1122361563_15175774613641n.jpg', '/upload/usernumber/FFD62B6283514B3CA1ED2736472ABE67.jpg', '1122361563_15175774613641n.jpg', '/upload/certify/B4054BB0435B43C2897F1BFCCDD56803.jpg', '沙发的斯蒂芬山东', '', '', '5', 'aaa.xlsx', '/upload/deal/66B54D0B9B57437291F645DFA2E12DAA.xlsx', '0', 'DC69A83278CB4B219C3019886A49776A', '2019-07-21 12:05:43', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-21 12:10:31');
INSERT INTO `m_apply` VALUES ('7D21235059BA4D27ADF2C7511E456D9C', '提取xxx信息', '德州市国安局', '001', 'GB0215485742', '微信图片_20171206125052.jpg', '/upload/usernumber/82B8DCC717FE4EE6AC81AAAE4469ACDE.jpg', '372301199201082911郑鹏鹏.jpg', '/upload/usernumber/19033F77553747D4A90A394906F1D357.jpg', 'user.jpg', '/upload/certify/B40003689A934706A40C3E5BD13A25A2.jpg', '水电费第三方', 'aaa.xlsx', '/upload/desc/18CABF8750324666A5800E1F6CC9675E.xlsx', '5', 'aaa.xlsx', '/upload/deal/4B40044B192A495989A02B90271860A3.xlsx', '0', 'DC69A83278CB4B219C3019886A49776A', '2019-07-20 11:56:53', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-20 22:10:43');
INSERT INTO `m_apply` VALUES ('A21E5609D6C34D68A1536D00CAF7C86F', '提取王瑞雪上午轨迹信息', '德州市国安局', '001', 'GB45789423464', '372301199201082911郑鹏鹏.jpg', '/upload/usernumber/E0F5C374091E4BA19936432A7F10B44E.jpg', '1122361563_15175774613641n.jpg', '/upload/usernumber/0690133979654FD38BA4A2B8F37E31DA.jpg', '1122361563_15175774613641n.jpg', '/upload/certify/2461F9BF91A94325BF1EF7F6979562FE.jpg', '', 'aaa.xlsx', '/upload/desc/893DC2CC8B7F4D80BD673143E379FB89.xlsx', '5', '图片1_0000.doc', '/upload/deal/F5FE801F059A4939BDCD295445E90DD1.doc', '0', 'DC69A83278CB4B219C3019886A49776A', '2019-07-20 22:18:22', '40DD194251CB49B3AFCCCCDC91307819', '2019-07-20 22:30:48');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一标识',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
  `user_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `user_role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色1 国安 2综合部 3账目中心',
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
INSERT INTO `sys_user` VALUES ('40DD194251CB49B3AFCCCCDC91307819', 'admin', '17852824021', '123456', '2', '移动', '002', 'system', '2019-07-06 14:54:08', 'system', '2019-09-08 22:39:16');
INSERT INTO `sys_user` VALUES ('D0DD557D65CA47DF80B29D23AA0CFFAC', '移动业支', '17852824022', '123456', '3', '移动', '003', 'system', '2019-07-20 07:45:41', 'system', '2019-07-20 07:45:47');
INSERT INTO `sys_user` VALUES ('DC69A83278CB4B219C3019886A49776A', '德州市国安局', '17852824020', '123456', '1', '德州市国安局', '001', 'system', '2019-07-06 14:51:17', 'system', '2019-07-20 07:46:48');

SET FOREIGN_KEY_CHECKS = 1;
