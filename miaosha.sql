/*
 Navicat Premium Data Transfer

 Source Server         : testOne
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 16/02/2023 16:55:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int(0) NOT NULL DEFAULT 0,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 'iphone13', 6999.00, '13香', 0, 'https://ts1.cn.mm.bing.net/th?id=OIP-C.bghtrpqTZBc99ChJyJbgbwHaHa&w=100&h=100&c=8&rs=1&qlt=90&o=6&dpr=1.12&pid=3.1&rm=2');
INSERT INTO `item` VALUES (2, 'iphone13', 6999.00, '13香', 0, 'https://cn.bing.com/images/search?q=%E8%8B%B9%E6%9E%9Clogo&FORM=IQFRBA&id=510CCF236AAF032C4EED3CE7DFAE9AE269B4655C');
INSERT INTO `item` VALUES (3, 'iPhone12', 5666.00, '12不香', 2, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.wA2U54keafI0-j18v174lAAAAA?w=197&h=164&c=7&r=0&o=5&dpr=1.12&pid=1.7');
INSERT INTO `item` VALUES (4, 'iphone4', 9999.00, '秒杀用', 21, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.-bwlxZAGmffz2AjSkesY6QHaJO?w=133&h=180&c=7&r=0&o=5&dpr=1.12&pid=1.7');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `stock` int(0) NOT NULL DEFAULT 0 COMMENT '库存',
  `item_id` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (1, 0, 1);
INSERT INTO `item_stock` VALUES (2, 0, 2);
INSERT INTO `item_stock` VALUES (3, 0, 3);
INSERT INTO `item_stock` VALUES (4, 645, 4);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(0) NOT NULL DEFAULT 0,
  `item_id` int(0) NOT NULL DEFAULT 0,
  `item_price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `amount` int(0) NOT NULL DEFAULT 0,
  `order_price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `promo_id` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('0', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('1', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000200', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000300', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000400', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000500', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000600', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042000000700', 17, 3, 5666.00, 1, 5666.00, 0);
INSERT INTO `order_info` VALUES ('2022042100000800', 17, 4, 9999.00, 1, 9999.00, 0);
INSERT INTO `order_info` VALUES ('2022042100000900', 17, 4, 9999.00, 1, 9999.00, 0);
INSERT INTO `order_info` VALUES ('2022042100001000', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042100001100', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042100001200', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042100001300', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042100001400', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042200001500', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042200001600', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042200001700', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022042500001800', 19, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022050400001900', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022050400002000', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022051200002100', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022060700002200', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022060700002300', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022060700002400', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022060700002500', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022062000002600', 20, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022071100002700', 17, 4, 500.00, 1, 500.00, 1);
INSERT INTO `order_info` VALUES ('2022090600002800', 21, 4, 500.00, 1, 500.00, 1);

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `start_time` datetime(0) NOT NULL,
  `item_id` int(0) NOT NULL DEFAULT 0,
  `promo_item_price` decimal(10, 0) NOT NULL DEFAULT 0,
  `end_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (1, 'iphone4抢购', '2022-04-22 11:10:00', 4, 500, '2022-04-21 14:36:00');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(0) NOT NULL DEFAULT 0,
  `step` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 29, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` int(0) NOT NULL DEFAULT 0 COMMENT '为1代表男性，2代表女性',
  `age` int(0) NOT NULL DEFAULT 0,
  `telpphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '注册方式 byphone bywechat byalipay',
  `third_party_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '第三方id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telpphone`) USING BTREE COMMENT '给手机号添加唯一索引，确保一个手机号只能注册一个用户'
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (17, '王硕', 1, 22, 'rongmuping', 'byphone', '');
INSERT INTO `user_info` VALUES (18, 'dbadh', 1, 15, '11111', 'byphone', '');
INSERT INTO `user_info` VALUES (19, '张奥', 2, 15, 'ZhangAo', 'byphone', '');
INSERT INTO `user_info` VALUES (20, '王二', 1, 12, 'wanger', 'byphone', '');
INSERT INTO `user_info` VALUES (21, '梁光贱', 1, 99, 'lgj', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (10, 'n3+ONiLNkxDp7gllN/TCjw==', 17);
INSERT INTO `user_password` VALUES (11, 'n3+ONiLNkxDp7gllN/TCjw==', 18);
INSERT INTO `user_password` VALUES (12, 'Ii3ikOGHm/R5mEAf4aAfwg==', 19);
INSERT INTO `user_password` VALUES (13, 'KqP0ujr327aCHE8Ol3YQoQ==', 20);
INSERT INTO `user_password` VALUES (14, 'ICy5YqxZB1uWSwcVLSNLcA==', 21);

SET FOREIGN_KEY_CHECKS = 1;
