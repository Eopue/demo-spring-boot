/*
Navicat MySQL Data Transfer

Source Server         : 192.168.93.132
Source Server Version : 50710
Source Host           : 192.168.93.132:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2018-08-31 15:57:51
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
-- Table structure for sys_m_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_role`;
CREATE TABLE `sys_m_role` (
  `role_sid` bigint(20) NOT NULL,
  `role_name` varchar(16) DEFAULT NULL,
  `role_desc` varchar(64) DEFAULT NULL,
  `role_type` varchar(16) DEFAULT NULL,
  `created_by` varchar(16) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(16) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`role_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Table structure for sys_m_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_m_user_role`;
CREATE TABLE `sys_m_user_role` (
  `role_sid` bigint(20) NOT NULL,
  `user_sid` bigint(20) NOT NULL,
  PRIMARY KEY (`role_sid`,`user_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
