ALTER TABLE `hr_roles_t`
  DROP COLUMN `tk_work_area_id` ,
  DROP COLUMN `hr_dept_id` ,
  ADD COLUMN `chart` VARCHAR(10) NULL DEFAULT NULL  AFTER `dept` ,
  CHANGE COLUMN `active` `active` VARCHAR(1) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT 'Y';

update hr_roles_t set role_name = 'TK_CHART_ADMIN' where role_name = 'TK_ORG_ADMIN';