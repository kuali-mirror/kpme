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

drop table if exists tk_work_area_ovt_pref_t;
drop table if exists tk_work_area_ovt_pref_s;

ALTER TABLE `tk_work_area_t`
      CHANGE COLUMN
`DEFAULT_OVERTIME_PREFERENCE` `DEFAULT_OVERTIME_EARNCODE` VARCHAR(3) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL  ;


ALTER TABLE `tk_work_area_t` ADD COLUMN `OVERTIME_EDIT_ROLE` VARCHAR(20) NOT NULL  AFTER `VER_NBR` ;

ALTER TABLE `tk_daily_overtime_rl_t` DROP COLUMN `OVERTIME_PREFERENCE` ;

ALTER TABLE `tk_sal_group_t` ADD COLUMN `descr` VARCHAR(30) NULL;