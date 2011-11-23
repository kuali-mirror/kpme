/*
 Navicat MySQL Data Transfer

 Source Server         : localhost-root
 Source Server Version : 50513
 Source Host           : localhost
 Source Database       : kpme

 Target Server Version : 50513
 File Encoding         : utf-8

 Date: 11/02/2011 16:11:04 PM
*/

-- ----------------------------
--  Table structure for `lm_ledger_t`
-- ----------------------------
DROP TABLE IF EXISTS `lm_ledger_t`;
CREATE TABLE `lm_ledger_t` (
  `lm_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ledger_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `principal_id` varchar(40) NOT NULL,
  `leave_code` varchar(15) NOT NULL,
  `lm_leave_code_id` bigint(20) NOT NULL,
  `lm_sys_schd_timeoff_id` bigint(20) NOT NULL,
  `lm_accrual_category_id` bigint(20) NOT NULL,
  `active` varchar(1) NOT NULL,
  `hours` int(11) NOT NULL,
  `apply_to_ytd_used` varchar(255) NOT NULL,
  `document_id` varchar(14) NOT NULL,
  `principal_activated` varchar(40) NOT NULL,
  `principal_inactivated` varchar(40) NOT NULL,
  `timestamp_activated` time NOT NULL,
  `timestamp_inactivated` time NOT NULL,
  `accrual_generated` varchar(1) NOT NULL,
  `block_id` bigint(20) NOT NULL,
  `ver_nbr` bigint(20) NOT NULL,
  `obj_id` bigint(20) NOT NULL,
  PRIMARY KEY (`lm_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lm_ledger_s`;
CREATE TABLE `lm_ledger_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lm_ledger_block_s`;
CREATE TABLE `lm_ledger_block_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;