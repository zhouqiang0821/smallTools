/*
 Navicat Premium Data Transfer

 Source Server         : txy
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 62.234.51.145:3306
 Source Schema         : zqadmin

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 25/11/2020 15:29:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for holiday_info
-- ----------------------------
DROP TABLE IF EXISTS `holiday_info`;
CREATE TABLE `holiday_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `holiday_date` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `holiday_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '   ',
  `type` int(11) NULL DEFAULT NULL COMMENT '0 星期六、日/1  节假日',
  `year` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of holiday_info
-- ----------------------------
INSERT INTO `holiday_info` VALUES (1, '2020-01-01', '元旦', 1, 2020);
INSERT INTO `holiday_info` VALUES (2, '2020-01-04', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (3, '2020-01-05', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (4, '2020-01-11', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (5, '2020-01-12', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (6, '2020-01-18', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (7, '2020-01-24', '除夕', 1, 2020);
INSERT INTO `holiday_info` VALUES (8, '2020-01-25', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (9, '2020-01-26', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (10, '2020-01-27', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (11, '2020-01-28', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (12, '2020-01-29', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (13, '2020-01-30', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (14, '2020-01-31', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (15, '2020-02-01', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (16, '2020-02-02', '春节', 1, 2020);
INSERT INTO `holiday_info` VALUES (17, '2020-02-08', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (18, '2020-02-09', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (19, '2020-02-15', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (20, '2020-02-16', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (21, '2020-02-22', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (22, '2020-02-23', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (23, '2020-02-29', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (24, '2020-03-01', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (25, '2020-03-07', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (26, '2020-03-08', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (27, '2020-03-14', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (28, '2020-03-15', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (29, '2020-03-21', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (30, '2020-03-22', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (31, '2020-03-28', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (32, '2020-03-29', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (33, '2020-04-04', '清明节', 1, 2020);
INSERT INTO `holiday_info` VALUES (34, '2020-04-05', '清明节', 1, 2020);
INSERT INTO `holiday_info` VALUES (35, '2020-04-06', '清明节', 1, 2020);
INSERT INTO `holiday_info` VALUES (36, '2020-04-11', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (37, '2020-04-12', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (38, '2020-04-18', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (39, '2020-04-19', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (40, '2020-04-25', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (41, '2020-05-01', '劳动节', 1, 2020);
INSERT INTO `holiday_info` VALUES (42, '2020-05-02', '劳动节', 1, 2020);
INSERT INTO `holiday_info` VALUES (43, '2020-05-03', '劳动节', 1, 2020);
INSERT INTO `holiday_info` VALUES (44, '2020-05-04', '劳动节', 1, 2020);
INSERT INTO `holiday_info` VALUES (45, '2020-05-05', '劳动节', 1, 2020);
INSERT INTO `holiday_info` VALUES (46, '2020-05-10', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (47, '2020-05-16', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (48, '2020-05-17', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (49, '2020-05-23', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (50, '2020-05-24', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (51, '2020-05-30', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (52, '2020-05-31', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (53, '2020-06-06', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (54, '2020-06-07', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (55, '2020-06-13', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (56, '2020-06-14', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (57, '2020-06-20', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (58, '2020-06-21', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (59, '2020-06-25', '端午节', 1, 2020);
INSERT INTO `holiday_info` VALUES (60, '2020-06-26', '端午节', 1, 2020);
INSERT INTO `holiday_info` VALUES (61, '2020-06-27', '端午节', 1, 2020);
INSERT INTO `holiday_info` VALUES (62, '2020-07-04', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (63, '2020-07-05', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (64, '2020-07-11', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (65, '2020-07-12', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (66, '2020-07-18', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (67, '2020-07-19', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (68, '2020-07-25', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (69, '2020-07-26', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (70, '2020-08-01', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (71, '2020-08-02', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (72, '2020-08-08', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (73, '2020-08-09', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (74, '2020-08-15', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (75, '2020-08-16', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (76, '2020-08-22', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (77, '2020-08-23', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (78, '2020-08-29', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (79, '2020-08-30', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (80, '2020-09-05', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (81, '2020-09-06', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (82, '2020-09-12', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (83, '2020-09-13', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (84, '2020-09-19', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (85, '2020-09-20', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (86, '2020-09-26', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (87, '2020-10-01', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (88, '2020-10-02', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (89, '2020-10-03', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (90, '2020-10-04', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (91, '2020-10-05', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (92, '2020-10-06', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (93, '2020-10-07', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (94, '2020-10-08', '中秋节', 1, 2020);
INSERT INTO `holiday_info` VALUES (95, '2020-10-11', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (96, '2020-10-17', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (97, '2020-10-18', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (98, '2020-10-24', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (99, '2020-10-25', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (100, '2020-10-31', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (101, '2020-11-01', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (102, '2020-11-07', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (103, '2020-11-08', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (104, '2020-11-14', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (105, '2020-11-15', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (106, '2020-11-21', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (107, '2020-11-22', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (108, '2020-11-28', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (109, '2020-11-29', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (110, '2020-12-05', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (111, '2020-12-06', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (112, '2020-12-12', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (113, '2020-12-13', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (114, '2020-12-19', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (115, '2020-12-20', '星期日', 0, 2020);
INSERT INTO `holiday_info` VALUES (116, '2020-12-26', '星期六', 0, 2020);
INSERT INTO `holiday_info` VALUES (117, '2020-12-27', '星期日', 0, 2020);

SET FOREIGN_KEY_CHECKS = 1;
