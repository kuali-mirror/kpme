ALTER TABLE `tk_time_block_t`
    ADD COLUMN `clock_log_begin_id` BIGINT(20) NULL DEFAULT NULL  AFTER `BEGIN_TS_TZ` ,
    ADD COLUMN `clock_log_end_id` BIGINT(20) NULL DEFAULT NULL  AFTER `END_TS_TZ` ;