ALTER TABLE tk_principal_calendar_t change `holiday_calendar_group` `holiday_calendar_group` varchar(45) COLLATE utf8_bin NULL;

DROP TABLE IF EXISTS `tk_roles_group_t`;
CREATE  TABLE `tk_roles_group_t` (
  `principal_id` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`principal_id`) )
ENGINE = InnoDB;
