ALTER TABLE tk_py_calendar_t ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `flsa_begin_time` ;
ALTER TABLE tk_holiday_calendar_t ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `ver_nbr` ;

ALTER TABLE tk_py_calendar_entries_t ADD COLUMN `calendar_group` VARCHAR(45) COLLATE utf8_bin NOT NULL AFTER `tk_py_calendar_id` ;
ALTER TABLE tk_py_calendar_entries_t change `tk_py_calendar_id` `tk_py_calendar_id` bigint(20) NULL;