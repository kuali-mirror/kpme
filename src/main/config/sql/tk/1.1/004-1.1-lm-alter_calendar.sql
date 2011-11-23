ALTER TABLE `hr_py_calendar_t` RENAME TO `hr_calendar_t`,
 CHANGE COLUMN `hr_py_calendar_id` `hr_calendar_id` BIGINT(20)  NOT NULL,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
 ADD COLUMN `calendar_types` VARCHAR(9) DEFAULT NULL AFTER `flsa_begin_time`,
 DROP COLUMN `active`,
 ADD COLUMN `calendar_descriptions` VARCHAR(50)  NOT NULL AFTER `calendar_types`,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`hr_calendar_id`);

ALTER TABLE `hr_py_calendar_s` RENAME TO `hr_calendar_s`;

ALTER TABLE `hr_py_calendar_entries_t` RENAME TO `hr_calendar_entries_t`,
 CHANGE COLUMN `hr_py_calendar_entry_id` `hr_calendar_entry_id` BIGINT(20)  NOT NULL,
 CHANGE COLUMN `hr_py_calendar_id` `hr_calendar_id` BIGINT(20)  DEFAULT NULL,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`hr_calendar_entry_id`);

ALTER TABLE `hr_py_calendar_entries_s` RENAME TO `hr_calendar_entries_s`;
