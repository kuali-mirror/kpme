ALTER TABLE `hr_principal_calendar_t` RENAME TO `hr_principal_hr_attributes_t`,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin,
 ADD COLUMN `leave_plan` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL AFTER `holiday_calendar_group`,
 ADD COLUMN `service_date` DATE  NOT NULL AFTER `leave_plan`,
 ADD COLUMN `fmla_eligible` VARCHAR(1)  DEFAULT 'N' AFTER `service_date`,
 ADD COLUMN `worksman_eligible` VARCHAR(1)  DEFAULT 'N' AFTER `fmla_eligible`;

UPDATE hr_principal_hr_attributes_t SET service_date = '2011-01-01';