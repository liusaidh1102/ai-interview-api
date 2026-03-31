/*
 Navicat Premium Data Transfer

 Source Server         : 10.104.64.6_3306
 Source Server Type    : MySQL
 Source Server Version : 90001
 Source Host           : 10.104.64.6:3306
 Source Schema         : ai_university

 Target Server Type    : MySQL
 Target Server Version : 90001
 File Encoding         : 65001

 Date: 22/09/2025 08:30:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for credit_breakdown
-- ----------------------------
DROP TABLE IF EXISTS `credit_breakdown`;
CREATE TABLE `credit_breakdown`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `catalog_id1` bigint NULL DEFAULT NULL,
  `catalog_id2` bigint NULL DEFAULT NULL,
  `catalog_name1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `catalog_name2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `channel` int NULL DEFAULT NULL,
  `class_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `college_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `create_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `join_date` datetime NULL DEFAULT NULL,
  `join_end_date` datetime NULL DEFAULT NULL,
  `join_start_date` datetime NULL DEFAULT NULL,
  `join_time_count` int NULL DEFAULT NULL,
  `num` double NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `scoreid` bigint NULL DEFAULT NULL,
  `score_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_status` int NULL DEFAULT NULL,
  `unit_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
