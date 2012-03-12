DROP TABLE IF EXISTS `lm_leave_status_hist_s`;

CREATE TABLE `lm_leave_status_hist_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  
drop table if exists  `lm_leave_status_hist_t`;

CREATE TABLE `lm_leave_status_hist_t` (
  `lm_leave_status_hist_id` varchar(60) NOT NULL,
  `lm_leave_block_id` varchar(60) NOT NULL,
  `request_status` varchar(1),
  `timestamp` time NOT NULL,
  `principal_id_modified` varchar(40),
  `reason` varchar(255),
  PRIMARY KEY (`lm_leave_status_hist_id`)
);