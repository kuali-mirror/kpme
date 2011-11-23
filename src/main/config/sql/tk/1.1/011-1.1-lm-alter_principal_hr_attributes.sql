ALTER TABLE `hr_principal_hr_attributes_t` CHANGE COLUMN `calendar_name` `pay_calendar` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
 MODIFY COLUMN `leave_calendar` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL;
