ALTER TABLE tk_principal_calendar_t change `holiday_calendar_group` `holiday_calendar_group` varchar(45) COLLATE utf8_bin NULL;

DROP TABLE IF EXISTS `tk_roles_group_t`;
CREATE  TABLE `tk_roles_group_t` (
  `principal_id` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`principal_id`) )
ENGINE = InnoDB;


ALTER TABLE `tk_shift_differential_rl_t`
      CHANGE COLUMN `BEGIN_TS` `BEGIN_TS` TIME NULL COMMENT '	'  ,
      CHANGE COLUMN `END_TS` `END_TS` TIME NULL  ;
ALTER TABLE `tk_earn_code_t` ADD COLUMN `ovt_earn_code` CHAR(1) NOT NULL DEFAULT 'N'  AFTER `effdt` ;