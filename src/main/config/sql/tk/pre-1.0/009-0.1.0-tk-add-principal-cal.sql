CREATE TABLE `tk_principal_calendar_t` (
  `principal_id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `py_calendar_group` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `holiday_calendar_group` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `TIMESTAMP` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into tk_principal_calendar_t values('admin','BW-CAL1','HOL','2010-01-01', now(),uuid(),1);
insert into tk_principal_calendar_t values('eric','BW-CAL1','HOL','2010-01-01', now(),uuid(),1);
