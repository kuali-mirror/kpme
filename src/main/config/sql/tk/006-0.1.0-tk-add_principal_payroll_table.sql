DROP TABLE IF EXISTS `hr_principal_pay_type_t`;
create table hr_principal_pay_type_t (
	`hr_principal_pay_type_id` bigint(20) NOT NULL,
	`PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`HR_PAYTYPE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
	`OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
 	`VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
 	PRIMARY KEY (`hr_principal_pay_type_id`)
);

DROP TABLE IF EXISTS `hr_principal_pay_type_s`;
CREATE TABLE `hr_principal_pay_type_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1101 DEFAULT CHARSET=latin1;
