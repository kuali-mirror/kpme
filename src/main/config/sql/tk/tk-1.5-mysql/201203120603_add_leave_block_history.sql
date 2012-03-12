
DROP TABLE IF EXISTS `lm_leave_block_hist_s`;
CREATE TABLE `lm_leave_block_hist_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));

DROP TABLE IF EXISTS `lm_leave_block_hist_t`;
CREATE TABLE `lm_leave_block_hist_t` (
  `lm_leave_block_hist_id` varchar(60) NOT NULL,
  `lm_leave_block_id` varchar(60) NOT NULL,
  `leave_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `principal_id` varchar(40) NOT NULL,
  `leave_code` varchar(15) NOT NULL,
  `lm_leave_code_id` VARCHAR(60) NOT NULL,
  `lm_sys_schd_timeoff_id` VARCHAR(60) NOT NULL,
  `lm_accrual_category_id` VARCHAR(60) NOT NULL,
  `tk_assignment_id` VARCHAR(60) DEFAULT NULL,
  `hours` int(11) NOT NULL,
  `apply_to_ytd_used` varchar(255) NOT NULL,
  `document_id` varchar(14) NOT NULL,
  `principal_id_modified` varchar(40) DEFAULT NULL,
  `timestamp` time ,
  `principal_id_deleted` varchar(40),
  `timestamp_deleted` time ,
  `block_id` bigint(20),
  `accrual_generated` varchar(1) NOT NULL,
  `request_status` VARCHAR(1),
  `action` varchar(1),
  `ver_nbr` bigint(20) DEFAULT '1',
  `obj_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`lm_leave_block_hist_id`)
)