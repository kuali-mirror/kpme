ALTER TABLE `tk_time_block_t` ADD COLUMN `ovt_pref` VARCHAR(5) NULL AFTER `VER_NBR`;
ALTER TABLE `hr_dept_earn_code_t` DELETE COLUMN `org_admin`;
