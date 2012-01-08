--
-- Table structure for table `lm_leave_plan_t`
--

DROP TABLE IF EXISTS `lm_leave_plan_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_plan_t` (
  `LM_LEAVE_PLAN_ID` bigint(20) NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin NOT NULL,
  `CAL_YEAR_START` varchar(5) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_LEAVE_PLAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_leave_plan_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_plan_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_plan_s`
--

LOCK TABLES `lm_leave_plan_s` WRITE;
/*!40000 ALTER TABLE `lm_leave_plan_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_plan_s` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `lm_accrual_category_t`
--

DROP TABLE IF EXISTS `lm_accrual_category_t`;
DROP TABLE IF EXISTS `lm_accrual_categories_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_t` (
  `LM_ACCRUAL_CATEGORY_ID` bigint(20) NOT NULL,
  `ACCRUAL_CATEGORY` varchar(15) COLLATE utf8_bin NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ACCRUAL_INTERVAL_EARN` varchar(5) COLLATE utf8_bin NOT NULL,
  `UNIT_OF_TIME` varchar(5) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `PRORATION` varchar(1) DEFAULT 'N',
  `DONATION` varchar(1) DEFAULT 'N',
  `SHOW_ON_GRID` varchar(1) DEFAULT 'N',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_ACCRUAL_CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_accrual_category_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accrual_category_s`
--

LOCK TABLES `lm_accrual_category_s` WRITE;
/*!40000 ALTER TABLE `lm_accrual_category_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accrual_category_s` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `lm_accrual_category_rules_t`
--

DROP TABLE IF EXISTS `lm_accrual_category_rules_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_rules_t` (
  `LM_ACCRUAL_CATEGORY_RULES_ID` bigint(20) NOT NULL,
  `SERVICE_UNIT_OF_TIME` varchar(15) COLLATE utf8_bin NOT NULL,
  `START_ACC` bigint(20) NOT NULL,
  `END_ACC` bigint(20) NOT NULL,
  `ACCRUAL_RATE` decimal(10,2) NOT NULL,
  `MAX_BAL` decimal(10,2) NOT NULL,
  `MAX_BAL_ACTION_FREQUENCY` varchar(5) COLLATE utf8_bin NOT NULL,
  `ACTION_AT_MAX_BAL` varchar(5) COLLATE utf8_bin NOT NULL,
  `MAX_BAL_TRANS_ACC_CAT` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MAX_BAL_TRANS_CONV_FACTOR` decimal(10,2) DEFAULT NULL,
  `MAX_TRANS_AMOUNT` bigint(20) DEFAULT NULL,
  `MAX_PAYOUT_AMOUNT` bigint(20) DEFAULT NULL,
  `MAX_PAYOUT_LEAVE_CODE` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MAX_USAGE` bigint(20) DEFAULT NULL,
  `MAX_CARRY_OVER` bigint(20) DEFAULT NULL,
  `LM_ACCRUAL_CATEGORY_ID` bigint(20) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_ACCRUAL_CATEGORY_RULES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_accrual_category_rules_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_rules_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accrual_category_rules_s`
--

LOCK TABLES `lm_accrual_category_rules_s` WRITE;
/*!40000 ALTER TABLE `lm_accrual_category_rules_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accrual_category_rules_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_leave_code_t`
--

DROP TABLE IF EXISTS `lm_leave_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_code_t` (
  `LM_LEAVE_CODE_ID` bigint(20) NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `ELIGIBLE_FOR_ACC` varchar(5) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CAT` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `EARN_CODE` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `LEAVE_CODE` varchar(15) COLLATE utf8_bin NOT NULL,
  `DISP_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `UNIT_OF_TIME` varchar(5) COLLATE utf8_bin NOT NULL,
  `FRACT_TIME_ALLOWD` varchar(5) COLLATE utf8_bin NOT NULL,
  `ROUND_OPT` varchar(5) COLLATE utf8_bin NOT NULL,
  `ALLOW_SCHD_LEAVE` varchar(5) COLLATE utf8_bin NOT NULL,
  `FMLA` varchar(5) COLLATE utf8_bin NOT NULL,
  `WORKMANS_COMP` varchar(5) COLLATE utf8_bin NOT NULL,
  `DEF_TIME` bigint(2) DEFAULT NULL,
  `EMPLOYEE` varchar(1) DEFAULT 'N',
  `APPROVER` varchar(1) DEFAULT 'N',
  `DEPT_ADMIN` varchar(1) DEFAULT 'N',
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_LEAVE_CODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_leave_code_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_code_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_code_s`
--

LOCK TABLES `lm_leave_code_s` WRITE;
/*!40000 ALTER TABLE `lm_leave_code_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_code_s` ENABLE KEYS */;
UNLOCK TABLES;

ALTER TABLE `hr_job_t` ADD COLUMN `eligible_for_leave` VARCHAR(1)  DEFAULT 'N' AFTER `position_nbr`;

ALTER TABLE `hr_principal_calendar_t` ADD COLUMN `leave_calendar` VARCHAR(45)  DEFAULT NULL;

ALTER TABLE `hr_py_calendar_t` RENAME TO `hr_calendar_t`,
 CHANGE COLUMN `hr_py_calendar_id` `hr_calendar_id` BIGINT(20)  NOT NULL,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
 ADD COLUMN `calendar_types` VARCHAR(9) DEFAULT NULL AFTER `flsa_begin_time`,
 DROP COLUMN `active`,
 ADD COLUMN `calendar_descriptions` VARCHAR(50)  NOT NULL AFTER `calendar_types`,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`hr_calendar_id`);

ALTER TABLE `hr_py_calendar_s` RENAME TO `hr_calendar_s`;

ALTER TABLE `hr_py_calendar_entries_t` RENAME TO `hr_calendar_entries_t`,
 CHANGE COLUMN `hr_py_calendar_entry_id` `hr_calendar_entry_id` BIGINT(20)  NOT NULL,
 CHANGE COLUMN `hr_py_calendar_id` `hr_calendar_id` BIGINT(20)  DEFAULT NULL,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`hr_calendar_entry_id`);

ALTER TABLE `hr_py_calendar_entries_s` RENAME TO `hr_calendar_entries_s`;


--
-- Table structure for table `lm_sys_schd_timeoff_t`
--

DROP TABLE IF EXISTS `lm_sys_schd_timeoff_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `lm_sys_schd_timeoff_t` (
  `LM_SYS_SCHD_TIMEOFF_ID` bigint(20) NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CATEGORY` varchar(15) COLLATE utf8_bin NOT NULL,
  `LEAVE_CODE` varchar(15) COLLATE utf8_bin NOT NULL,
  `ACCR_DT` date NOT NULL,
  `SCH_TIME_OFF_DT` date DEFAULT NULL,
  `LOCATION` varchar(15) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(50) COLLATE utf8_bin NOT NULL,
  `AMOUNT_OF_TIME` bigint(20) NOT NULL DEFAULT '1',
  `UNUSED_TIME` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `TRANS_CONV_FACTOR` decimal(10,2) DEFAULT NULL,
  `TRANSFER_TO_LEAVE_CODE` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `EXP_DT` date DEFAULT NULL,
  `PREMIUM_HOLIDAY` varchar(5) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_SYS_SCHD_TIMEOFF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_sys_schd_timeoff_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_sys_schd_timeoff_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_sys_schd_timeoff_s`
--

LOCK TABLES `lm_sys_schd_timeoff_s` WRITE;
/*!40000 ALTER TABLE `lm_sys_schd_timeoff_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_sys_schd_timeoff_s` ENABLE KEYS */;
UNLOCK TABLES;

ALTER TABLE `hr_principal_calendar_t` RENAME TO `hr_principal_attributes_t`,
 CHANGE COLUMN `py_calendar_group` `calendar_name` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin,
 ADD COLUMN `leave_plan` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin NOT NULL AFTER `holiday_calendar_group`,
 ADD COLUMN `service_date` DATE  NOT NULL AFTER `leave_plan`,
 ADD COLUMN `fmla_eligible` VARCHAR(1)  DEFAULT 'N' AFTER `service_date`,
 ADD COLUMN `worksman_eligible` VARCHAR(1)  DEFAULT 'N' AFTER `fmla_eligible`;

UPDATE hr_principal_attributes_t SET service_date = '2011-01-01';

ALTER TABLE `lm_leave_plan_t` MODIFY COLUMN `DESCR` VARCHAR(30)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
 MODIFY COLUMN `CAL_YEAR_START` VARCHAR(5)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL;
 
 DROP TABLE `hr_holiday_calendar_t`;
DROP TABLE `hr_holiday_calendar_s`;
DROP TABLE `hr_holiday_calendar_dates_t`;
DROP TABLE `hr_holiday_calendar_dates_s`;
ALTER TABLE `hr_principal_attributes_t` DROP COLUMN `holiday_calendar_group`;

CREATE TABLE `lm_leave_document_header_t` (
  `document_id` varchar(14) NOT NULL,
  `principal_id` varchar(10) NOT NULL,
  `begin_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `document_status` varchar(1) NOT NULL,
  `obj_id` varchar(36) NOT NULL,
  `ver_nbr` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lm_ledger_t`;
CREATE TABLE `lm_ledger_t` (
  `lm_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ledger_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `principal_id` varchar(40) NOT NULL,
  `leave_code` varchar(15) NOT NULL,
  `lm_leave_code_id` bigint(20) NOT NULL,
  `lm_sys_schd_timeoff_id` bigint(20) NOT NULL,
  `lm_accrual_category_id` bigint(20) NOT NULL,
  `active` varchar(1) NOT NULL,
  `hours` int(11) NOT NULL,
  `apply_to_ytd_used` varchar(255) NOT NULL,
  `document_id` varchar(14) NOT NULL,
  `principal_activated` varchar(40) NOT NULL,
  `principal_inactivated` varchar(40) NOT NULL,
  `timestamp_activated` time NOT NULL,
  `timestamp_inactivated` time NOT NULL,
  `accrual_generated` varchar(1) NOT NULL,
  `block_id` bigint(20) NOT NULL,
  `ver_nbr` bigint(20) NOT NULL,
  `obj_id` bigint(20) NOT NULL,
  PRIMARY KEY (`lm_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lm_ledger_s`;
CREATE TABLE `lm_ledger_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lm_ledger_block_s`;
CREATE TABLE `lm_ledger_block_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `hr_principal_attributes_t` CHANGE COLUMN `calendar_name` `pay_calendar` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
 MODIFY COLUMN `leave_calendar` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL;
 
 ALTER TABLE `hr_pay_grade_t` ADD COLUMN `sal_group` VARCHAR(20)  NOT NULL AFTER `pay_grade`;
 
 ALTER TABLE `lm_accrual_category_t` ADD COLUMN `PLANNING_MONTHS` BIGINT(2)  NOT NULL DEFAULT 1 AFTER `SHOW_ON_GRID`;
 
 ALTER TABLE `lm_ledger_t` CHANGE COLUMN `ver_nbr` `ver_nbr` BIGINT(20) NULL DEFAULT 1  , CHANGE COLUMN `obj_id` `obj_id` VARCHAR(36) NULL  ;
 
 ALTER TABLE `hr_principal_attributes_t` MODIFY COLUMN `leave_plan` VARCHAR(15)  CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
 MODIFY COLUMN `service_date` DATE  DEFAULT NULL;
 
 ALTER TABLE `hr_position_t` ADD COLUMN `WORK_AREA` VARCHAR(10);
 
 
--
-- Table structure for table `lm_sys_schd_timeoff_t`
--

DROP TABLE IF EXISTS `lm_leave_donation_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `lm_leave_donation_t` (
  `LM_LEAVE_DONATION_ID` bigint(20) NOT NULL,
  `DONATED_ACC_CAT` varchar(15) COLLATE utf8_bin NOT NULL,
  `RECIPIENTS_ACC_CAT` varchar(15) COLLATE utf8_bin NOT NULL,
  `AMOUNT_DONATED` decimal(18,2) COLLATE utf8_bin NOT NULL,
  `AMOUNT_RECEIVED` decimal(18,2) COLLATE utf8_bin NOT NULL,
  `DONOR` varchar(40) COLLATE utf8_bin NOT NULL,
  `RECEPIENT` varchar(40) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(50) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`LM_LEAVE_DONATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `lm_leave_donation_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_donation_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_donation_s`
--

LOCK TABLES `lm_leave_donation_s` WRITE;
/*!40000 ALTER TABLE `lm_leave_donation_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_donation_s` ENABLE KEYS */;
UNLOCK TABLES;

alter table lm_leave_plan_t modify column lm_leave_plan_id varchar(60) NOT NULL;
alter table lm_accrual_category_t modify column lm_accrual_category_id varchar(60) NOT NULL;
alter table lm_accrual_category_rules_t modify column lm_accrual_category_rules_id varchar(60) NOT NULL;
alter table lm_leave_code_t modify column lm_leave_code_id varchar(60) NOT NULL;
alter table lm_sys_schd_timeoff_t modify column lm_sys_schd_timeoff_id varchar(60) NOT NULL;
alter table lm_ledger_t modify column lm_ledger_id varchar(60) NOT NULL;
alter table lm_leave_donation_t modify column lm_leave_donation_id varchar(60) NOT NULL;
alter table hr_calendar_t modify column hr_calendar_id varchar(60) NOT NULL;
alter table hr_calendar_entries_t modify column hr_calendar_entry_id varchar(60) NOT NULL;