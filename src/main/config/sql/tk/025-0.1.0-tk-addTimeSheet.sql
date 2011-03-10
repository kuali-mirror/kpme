
DROP TABLE IF EXISTS `tk_time_sheet_init_s`;
CREATE TABLE `tk_time_sheet_init_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1101 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tk_time_sheet_init_t`;
CREATE TABLE `tk_time_sheet_init_t` (
  `tk_time_sheet_init_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `principal_id` VARCHAR(40) NOT NULL ,
  `py_calendar_entries_id` VARCHAR(40) NOT NULL ,
  PRIMARY KEY (`tk_time_sheet_init_id`) )
   ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;