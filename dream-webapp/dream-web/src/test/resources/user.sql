/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50528
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-07-26 21:49:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `sex` char(1) DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '吴鹏伟', '121212', '男', '12@qq.com');
INSERT INTO `user` VALUES ('2', '王鹏', '1212', '男', '12@qq.com');
INSERT INTO `user` VALUES ('3', '望你', '113', '男', null);
INSERT INTO `user` VALUES ('4', '我的', '1313', '男', null);
INSERT INTO `user` VALUES ('5', '没有啊', '133', '男', null);
INSERT INTO `user` VALUES ('6', '啦啦', '1313', '女', null);
INSERT INTO `user` VALUES ('7', '你是', '1313', '女', null);
INSERT INTO `user` VALUES ('8', '本页', '1313', '男', '12@qq.com');
