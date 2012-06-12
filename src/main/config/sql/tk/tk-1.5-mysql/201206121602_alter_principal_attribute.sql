CREATE TABLE `hr_principal_attribute_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `hr_principal_attributes_t`;
CREATE TABLE `hr_principal_attributes_t` (
  `hr_principal_attribute_id` varchar(60) NOT NULL DEFAULT '',
  `principal_id` varchar(40) NOT NULL DEFAULT '',
  `pay_calendar` varchar(15) DEFAULT NULL,
  `leave_plan` varchar(15) DEFAULT NULL,
  `service_date` date DEFAULT NULL,
  `fmla_eligible` varchar(1) DEFAULT 'N',
  `worksman_eligible` varchar(1) DEFAULT 'N',
  `timezone` varchar(30) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `active` varchar(1) DEFAULT 'Y',
  `leave_calendar` varchar(15) DEFAULT NULL,
  `record_time` varchar(1) NOT NULL DEFAULT 'Y',
  `record_leave` varchar(2) NOT NULL DEFAULT 'LM',
  PRIMARY KEY (`hr_principal_attribute_id`)
);