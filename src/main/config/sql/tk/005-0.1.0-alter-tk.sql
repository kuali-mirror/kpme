drop table tk_assignment_acct_s;
ALTER TABLE `tk_hour_detail_t` DROP COLUMN `begin_ts_tz` , DROP COLUMN `begin_ts` , DROP COLUMN `clock_log_created` , DROP COLUMN `document_id` , DROP COLUMN `end_ts_tz` , DROP COLUMN `end_ts` , DROP COLUMN `hr_job_id` , DROP COLUMN `job_number` , DROP COLUMN `task` , DROP COLUMN `timestamp` , DROP COLUMN `tk_task_id` , DROP COLUMN `tk_work_area_id` , DROP COLUMN `user_principal_id` , DROP COLUMN `work_area` ;

drop table tk_hour_detail_t;

CREATE TABLE `tk_hour_detail_t` (
  `TK_HOUR_DETAIL_ID` bigint(20) NOT NULL,
  `TK_TIME_BLOCK_ID` bigint(20) NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',  
  PRIMARY KEY (`TK_HOUR_DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table tk_time_hour_detail_t;
