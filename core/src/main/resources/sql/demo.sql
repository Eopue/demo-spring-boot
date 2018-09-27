/*
Navicat MySQL Data Transfer

Source Server         : 192.168.93.132
Source Server Version : 50710
Source Host           : 192.168.93.132:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2018-08-31 17:45:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_m_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_permission`;
CREATE TABLE `sys_m_permission` (
  `permission_sid` bigint(20) NOT NULL,
  `permission_name` varchar(16) DEFAULT NULL,
  `permission_code` varchar(16) DEFAULT NULL,
  `created_by` varchar(16) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(16) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`permission_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_permission
-- ----------------------------
INSERT INTO `sys_m_permission` VALUES ('1', 'delete', 'delete', 'liuxiaolu', '2018-08-31 17:39:29', 'liuxiaolu', '2018-08-31 17:39:35', '1');
INSERT INTO `sys_m_permission` VALUES ('1', 'add', 'add', 'liuxiaolu', '2018-08-31 17:39:29', 'liuxiaolu', '2018-08-31 17:39:35', '1');

-- ----------------------------
-- Table structure for sys_m_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_record`;
CREATE TABLE `sys_m_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operate` varchar(16) DEFAULT NULL,
  `created_by` varchar(16) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(16) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_m_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_role`;
CREATE TABLE `sys_m_role` (
  `role_sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(16) DEFAULT NULL,
  `role_desc` varchar(64) DEFAULT NULL,
  `role_type` varchar(16) DEFAULT NULL,
  `created_by` varchar(16) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(16) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`role_sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_role
-- ----------------------------
INSERT INTO `sys_m_role` VALUES ('1', 'admin', 'admin', '01', 'liuxiaolu', '2018-08-31 17:29:10', 'liuxiaolu', '2018-08-31 17:29:17', '1');

-- ----------------------------
-- Table structure for sys_m_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_user`;
CREATE TABLE `sys_m_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `created_by` varchar(16) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(16) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_user
-- ----------------------------
INSERT INTO `sys_m_user` VALUES ('1', 'liuxiaolu', '12c6ab2100060239bca85f4bc3bb32dc', '13618375618', null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_m_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_user_permission`;
CREATE TABLE `sys_m_user_permission` (
  `permission_sid` bigint(20) NOT NULL,
  `user_sid` bigint(20) NOT NULL,
  PRIMARY KEY (`permission_sid`,`user_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_user_permission
-- ----------------------------
INSERT INTO `sys_m_user_permission` VALUES ('1', '1');
INSERT INTO `sys_m_user_permission` VALUES ('2', '1');
-- ----------------------------
-- Table structure for sys_m_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_user_role`;
CREATE TABLE `sys_m_user_role` (
  `role_sid` bigint(20) NOT NULL,
  `user_sid` bigint(20) NOT NULL,
  PRIMARY KEY (`role_sid`,`user_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_m_user_role
-- ----------------------------
INSERT INTO `sys_m_user_role` VALUES ('1', '1');

/*
Navicat MySQL Data Transfer

Source Server         : 47.105.112.226
Source Server Version : 50723
Source Host           : 47.105.112.226:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-25 23:12:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_m_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_user_token`;
CREATE TABLE `sys_m_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) DEFAULT NULL,
  `user_sid` bigint(20) DEFAULT NULL,
  `access_token` varchar(1024) DEFAULT NULL,
  `access_expire` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

