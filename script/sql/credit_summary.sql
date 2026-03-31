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

 Date: 22/09/2025 08:30:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for credit_summary
-- ----------------------------
DROP TABLE IF EXISTS `credit_summary`;
CREATE TABLE `credit_summary`  (
  `user_id` bigint NOT NULL,
  `value_9227` double NULL DEFAULT NULL,
  `value_7146` double NULL DEFAULT NULL,
  `value_9226` double NULL DEFAULT NULL,
  `value_10211` double NULL DEFAULT NULL,
  `convert_6090` double NULL DEFAULT NULL,
  `value_6011` double NULL DEFAULT NULL,
  `value_9225` double NULL DEFAULT NULL,
  `value_9389` double NULL DEFAULT NULL,
  `value_10213` double NULL DEFAULT NULL,
  `value_9223` double NULL DEFAULT NULL,
  `value_10212` double NULL DEFAULT NULL,
  `channel` double NULL DEFAULT NULL,
  `value_9390` double NULL DEFAULT NULL,
  `convert_sum` double NULL DEFAULT NULL,
  `class_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_no` bigint NULL DEFAULT NULL,
  `value_sum_credit` double NULL DEFAULT NULL,
  `convert_score_sum` double NULL DEFAULT NULL,
  `convert_6089` int NULL DEFAULT NULL,
  `college_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `join_date` datetime NULL DEFAULT NULL,
  `value_6089` double NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `convert_9390` int NULL DEFAULT NULL,
  `value_sum_studytime` double NULL DEFAULT NULL,
  `convert_9227` double NULL DEFAULT NULL,
  `join_time_count` double NULL DEFAULT NULL,
  `convert_7146` double NULL DEFAULT NULL,
  `value_6090` double NULL DEFAULT NULL,
  `convert_9225` int NULL DEFAULT NULL,
  `end_date` datetime NULL DEFAULT NULL,
  `convert_9226` double NULL DEFAULT NULL,
  `score_sum` double NULL DEFAULT NULL,
  `convert_9223` double NULL DEFAULT NULL,
  `score_screening` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `convert_9224` double NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
