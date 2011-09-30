ALTER TABLE `tk_task_t`
  ADD COLUMN `effdt` DATE NOT NULL  AFTER `USER_PRINCIPAL_ID` ,
  ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y'  AFTER `effdt` ,
  ADD COLUMN `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `active` ;

update tk_task_t set effdt = '2010-01-01';
update tk_task_t set timestamp = now();