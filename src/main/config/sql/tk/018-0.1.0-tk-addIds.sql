ALTER TABLE `tk_roles_t` ADD COLUMN `tk_dept_id` BIGINT(20) NULL  AFTER `active`;
ALTER TABLE `tk_roles_t` ADD COLUMN `tk_work_area_id` BIGINT(20) NULL  AFTER `tk_dept_id`;

