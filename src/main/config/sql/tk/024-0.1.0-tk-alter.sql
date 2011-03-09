ALTER TABLE tk_py_calendar_t ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `flsa_begin_time` ;
ALTER TABLE tk_holiday_calendar_t ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `ver_nbr` ;

ALTER TABLE tk_py_calendar_entries_t ADD COLUMN `calendar_group` VARCHAR(45) COLLATE utf8_bin NOT NULL AFTER `tk_py_calendar_id` ;
ALTER TABLE tk_py_calendar_entries_t change `tk_py_calendar_id` `tk_py_calendar_id` bigint(20) NULL;
ALTER TABLE tk_holiday_calendar_dates_t MODIFY COLUMN `HOLIDAY_HRS` DECIMAL(4,2) NOT NULL;
ALTER TABLE tk_holiday_calendar_t ADD COLUMN `location` VARCHAR(30) AFTER `descr` ;
ALTER TABLE tk_daily_overtime_rl_t MODIFY COLUMN `MAX_GAP` DECIMAL(8,2);
