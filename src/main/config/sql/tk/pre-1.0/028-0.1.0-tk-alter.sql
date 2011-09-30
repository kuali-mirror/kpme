
DROP TABLE IF EXISTS `tk_missed_punch_s`;
CREATE TABLE `tk_missed_punch_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1001;
insert into tk_missed_punch_s values (1000);

ALTER TABLE `tk_time_block_t`
    ADD COLUMN `clock_log_begin_id` BIGINT(20) NULL DEFAULT NULL  AFTER `BEGIN_TS_TZ` ,
    ADD COLUMN `clock_log_end_id` BIGINT(20) NULL DEFAULT NULL  AFTER `END_TS_TZ` ;
