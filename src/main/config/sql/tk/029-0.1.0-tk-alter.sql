ALTER TABLE `tk_missed_punch_t` ADD COLUMN `timesheet_doc_id` VARCHAR(14) NOT NULL  AFTER `action_time` ;

ALTER TABLE `tk_missed_punch_t` ENGINE = InnoDB , CHANGE COLUMN `action_time` `action_time` TIME NOT NULL DEFAULT '00:00:00'  ;
