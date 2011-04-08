ALTER TABLE `tk_work_area_t` modify `work_area` bigint(10) NOT NULL;
ALTER TABLE `tk_work_area_t` DROP PRIMARY KEY;
ALTER TABLE `tk_work_area_t` ADD PRIMARY KEY (`TK_WORK_AREA_ID`);


DROP TABLE IF EXISTS `tk_work_area_key_s`;
CREATE TABLE `tk_work_area_key_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;


INSERT INTO `tk_work_area_key_s` (`ID`) (select max(`work_area`) from `tk_work_area_t`);