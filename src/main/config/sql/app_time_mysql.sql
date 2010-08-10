-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: andover    Database: tk
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hr_job_t`
--

DROP TABLE IF EXISTS `hr_job_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_t` (
  `JOB_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `dept_id` varchar(21) COLLATE utf8_bin NOT NULL,
  `TK_SAL_GROUP` varchar(7) COLLATE utf8_bin DEFAULT NULL,
  `PAY_GRADE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `location` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `std_hours` decimal(5,2) DEFAULT NULL,
  `fte` bit(1) DEFAULT NULL,
  `hr_paytype_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_job_s`
--

DROP TABLE IF EXISTS `hr_job_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1015 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_paytype_t`
--

DROP TABLE IF EXISTS `hr_paytype_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_paytype_t` (
  `PAYTYPE_ID` bigint(20) NOT NULL,
  `PAYTYPE` varchar(5) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `REG_ERN_CODE` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `HOLIDAY_CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`PAYTYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_paytype_s`
--

DROP TABLE IF EXISTS `hr_paytype_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_paytype_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_work_schedule_entry_t`
--

DROP TABLE IF EXISTS `hr_work_schedule_entry_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_entry_t` (
  `WORK_SCHDULE_ENTRY_ID` bigint(20) NOT NULL,
  `WORK_SCHEDULE_ID` bigint(20) NOT NULL,
  `CAL_DAY_ID` bigint(19) NOT NULL,
  `DAY_OF_PERIOD_ID` int(11) NOT NULL,
  `REG_HOURS` int(11) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`WORK_SCHDULE_ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_work_schedule_entry_s`
--

DROP TABLE IF EXISTS `hr_work_schedule_entry_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_entry_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_work_schedule_t`
--

DROP TABLE IF EXISTS `hr_work_schedule_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_t` (
  `WORK_SCHEDULE_ID` bigint(20) NOT NULL,
  `WORK_SCHEDULE_DESC` varchar(30) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `DEPT_ID` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `PRINCIPAL_ID` bigint(20) DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`WORK_SCHEDULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_work_schedule_s`
--

DROP TABLE IF EXISTS `hr_work_schedule_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_accrual_categories_t`
--

DROP TABLE IF EXISTS `tk_accrual_categories_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_accrual_categories_t` (
  `TK_ACCRUAL_CATEGORY_ID` bigint(20) NOT NULL,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`TK_ACCRUAL_CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_accrual_categories_s`
--

DROP TABLE IF EXISTS `tk_accrual_categories_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_accrual_categories_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_accruals_t`
--

DROP TABLE IF EXISTS `tk_accruals_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_accruals_t` (
  `TK_ACCRUALS_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(21) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` datetime NOT NULL,
  `HOURS_ACCRUED` int(11) NOT NULL,
  `HOURS_TAKEN` int(11) NOT NULL,
  `HOURS_ADJUST` int(11) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_ACCRUALS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_accruals_s`
--

DROP TABLE IF EXISTS `tk_accruals_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_accruals_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assign_acct_s`
--

DROP TABLE IF EXISTS `tk_assign_acct_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assign_acct_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assign_acct_t`
--

DROP TABLE IF EXISTS `tk_assign_acct_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assign_acct_t` (
  `ASSIGN_ACCT_ID` bigint(19) NOT NULL,
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `ACCOUNT_NBR` varchar(7) COLLATE utf8_bin DEFAULT NULL,
  `SUB_ACCT_NBR` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `FIN_OBJECT_NBR` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `FIN_SUB_OBJ_CD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `ORG_REF_ID` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `PERCENT` decimal(5,2) DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  `ASSIGNMENT_ID` bigint(19) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ASSIGN_ACCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assignment_t`
--

DROP TABLE IF EXISTS `tk_assignment_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_t` (
  `ASSIGNMENT_ID` bigint(19) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `ERNCD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TASK_ID` bigint(20) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ASSIGNMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assignment_s`
--

DROP TABLE IF EXISTS `tk_assignment_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_location_rl_s`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2093 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_location_rl_t`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_t` (
  `CLOCK_LOC_RULE_ID` bigint(19) NOT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `job_number` bigint(20) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `IP_ADDRESS` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `dept_id` varchar(21) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_log_s`
--

DROP TABLE IF EXISTS `tk_clock_log_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_log_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2373 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_log_t`
--

DROP TABLE IF EXISTS `tk_clock_log_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_log_t` (
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TASK_ID` bigint(20) DEFAULT NULL,
  `CLOCK_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CLOCK_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_ACTION` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `IP_ADDRESS` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `CLOCK_LOG_ID` bigint(19) NOT NULL,
  PRIMARY KEY (`CLOCK_LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_daily_overtime_rl_t`
--

DROP TABLE IF EXISTS `tk_daily_overtime_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_daily_overtime_rl_t` (
  `tk_daily_overtime_rl_id` bigint(20) NOT NULL,
  `LOCATION` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `HR_PAYTYPE` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DEPTID` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TASK_ID` bigint(20) DEFAULT NULL,
  `MAX_GAP` decimal(3,0) DEFAULT NULL,
  `SHIFT_HOURS` decimal(2,0) DEFAULT NULL,
  `OVERTIME_PREFERENCE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`tk_daily_overtime_rl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_daily_overtime_rl_s`
--

DROP TABLE IF EXISTS `tk_daily_overtime_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_daily_overtime_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_earn_code_t`
--

DROP TABLE IF EXISTS `tk_dept_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_earn_code_t` (
  `dept_earn_code_id` bigint(20) NOT NULL,
  `dept_id` varchar(21) COLLATE utf8_bin NOT NULL,
  `tk_sal_group` varchar(10) COLLATE utf8_bin NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `employee` bit(1) DEFAULT b'0',
  `approver` bit(1) DEFAULT b'0',
  `org_admin` bit(1) DEFAULT b'0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`dept_earn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_earn_code_s`
--

DROP TABLE IF EXISTS `tk_dept_earn_code_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_earn_code_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_lunch_rl_t`
--

DROP TABLE IF EXISTS `tk_dept_lunch_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_lunch_rl_t` (
  `DEPT_LUNCH_RULE_ID` bigint(19) NOT NULL,
  `DEPT_ID` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `principal_id` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `job_number` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `REQUIRED_CLOCK_FL` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `MAX_MINS` decimal(2,0) DEFAULT NULL,
  `user_principal_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_lunch_rl_s`
--

DROP TABLE IF EXISTS `tk_dept_lunch_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_lunch_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2079 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_t`
--

DROP TABLE IF EXISTS `tk_dept_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_t` (
  `dept_id` varchar(21) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ORG` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CHART` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_code_t`
--

DROP TABLE IF EXISTS `tk_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_code_t` (
  `tk_earn_code_id` bigint(20) NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `record_time` decimal(5,2) DEFAULT NULL,
  `record_amount` decimal(6,2) DEFAULT NULL,
  `record_hours` decimal(5,2) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`tk_earn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_code_s`
--

DROP TABLE IF EXISTS `tk_earn_code_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_code_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_group_def_t`
--

DROP TABLE IF EXISTS `tk_earn_group_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_group_def_t` (
  `earns_group` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `tk_earn_group_def_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tk_earn_group_def_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_group_def_s`
--

DROP TABLE IF EXISTS `tk_earn_group_def_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_group_def_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_group_t`
--

DROP TABLE IF EXISTS `tk_earn_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_group_t` (
  `earn_group_id` bigint(20) NOT NULL,
  `earn_group` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`earn_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_group_s`
--

DROP TABLE IF EXISTS `tk_earn_group_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_group_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_grace_period_rl_t`
--

DROP TABLE IF EXISTS `tk_grace_period_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_grace_period_rl_t` (
  `GRACE_PERIOD_RULE_ID` bigint(19) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `GRACE_MINS` decimal(2,0) DEFAULT NULL,
  `HOUR_FACTOR` decimal(2,2) DEFAULT NULL,
  `user_principal_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_grace_period_rl_s`
--

DROP TABLE IF EXISTS `tk_grace_period_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_grace_period_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2078 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_holiday_calendar_dates_t`
--

DROP TABLE IF EXISTS `tk_holiday_calendar_dates_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_holiday_calendar_dates_t` (
  `HOLIDAY_CALENDAR_DATES_ID` bigint(20) NOT NULL,
  `HOLIDAY_DATE` date NOT NULL,
  `HOLIDAY_DESC` varchar(30) COLLATE utf8_bin NOT NULL,
  `HOLIDAY_CALENDAR_ID` int(11) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`HOLIDAY_CALENDAR_DATES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_holiday_calendar_dates_s`
--

DROP TABLE IF EXISTS `tk_holiday_calendar_dates_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_holiday_calendar_dates_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2087 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_holiday_calendar_t`
--

DROP TABLE IF EXISTS `tk_holiday_calendar_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_holiday_calendar_t` (
  `HOLIDAY_CALENDAR_ID` bigint(20) NOT NULL,
  `HOLIDAY_CALENDAR_GROUP` varchar(3) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`HOLIDAY_CALENDAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_holiday_calendar_s`
--

DROP TABLE IF EXISTS `tk_holiday_calendar_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_holiday_calendar_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_py_calendar_t`
--

DROP TABLE IF EXISTS `tk_py_calendar_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_py_calendar_t` (
  `tk_py_calendar_id` bigint(20) NOT NULL,
  `calendar_group` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `chart` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `begin_date` date DEFAULT NULL,
  `begin_time` time DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  PRIMARY KEY (`tk_py_calendar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_py_calendar_s`
--

DROP TABLE IF EXISTS `tk_py_calendar_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_py_calendar_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2083 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_py_calendar_dates_t`
--

DROP TABLE IF EXISTS `tk_py_calendar_dates_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_py_calendar_dates_t` (
  `tk_py_calendar_dates_id` bigint(20) NOT NULL,
  `tk_py_calendar_id` bigint(20) NOT NULL,
  `begin_period_date` date DEFAULT NULL,
  `begin_period_time` time DEFAULT NULL,
  `end_period_date` date DEFAULT NULL,
  `end_period_time` time DEFAULT NULL,
  `initiate_date` date DEFAULT NULL,
  `initiate_time` time DEFAULT NULL,
  `end_pay_period_date` date DEFAULT NULL,
  `end_pay_period_time` time DEFAULT NULL,
  `employee_approval_date` date DEFAULT NULL,
  `employee_approval_time` time DEFAULT NULL,
  `supervisor_approval_date` date DEFAULT NULL,
  `supervisor_approval_time` time DEFAULT NULL,
  `pay_period_end_date` date DEFAULT NULL,
  `pay_period_end_time` time DEFAULT NULL,
  PRIMARY KEY (`tk_py_calendar_dates_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_py_calendar_dates_s`
--

DROP TABLE IF EXISTS `tk_py_calendar_dates_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_py_calendar_dates_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_sal_group_t`
--

DROP TABLE IF EXISTS `tk_sal_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_sal_group_t` (
  `SAL_GROUP_ID` bigint(20) NOT NULL,
  `SAL_GROUP` varchar(10) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`SAL_GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_sal_group_s`
--

DROP TABLE IF EXISTS `tk_sal_group_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_sal_group_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_shift_differential_rl_t`
--

DROP TABLE IF EXISTS `tk_shift_differential_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_shift_differential_rl_t` (
  `TK_SHIFT_DIFF_RL_ID` bigint(20) NOT NULL,
  `LOCATION` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `TK_SAL_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `PAY_GRADE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `ERNCD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `END_TS` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `MIN_HRS` decimal(2,0) DEFAULT NULL,
  `SUN` bit(1) DEFAULT NULL,
  `MON` bit(1) DEFAULT NULL,
  `TUE` bit(1) DEFAULT NULL,
  `WED` bit(1) DEFAULT NULL,
  `THU` bit(1) DEFAULT NULL,
  `FRI` bit(1) DEFAULT NULL,
  `SAT` bit(1) DEFAULT NULL,
  `MAX_GAP` decimal(2,0) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`TK_SHIFT_DIFF_RL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_shift_differential_rl_s`
--

DROP TABLE IF EXISTS `tk_shift_differential_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_shift_differential_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_system_lunch_rl_t`
--

DROP TABLE IF EXISTS `tk_system_lunch_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_system_lunch_rl_t` (
  `TK_SYSTEM_LUNCH_RL_ID` bigint(20) NOT NULL,
  `EFFDT` date NOT NULL,
  `MIN_MINS` decimal(3,0) DEFAULT NULL,
  `BLOCK_HOURS` decimal(3,0) DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TK_SYSTEM_LUNCH_RL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_system_lunch_rl_s`
--

DROP TABLE IF EXISTS `tk_system_lunch_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_system_lunch_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_task_t`
--

DROP TABLE IF EXISTS `tk_task_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_task_t` (
  `task_id` bigint(20) NOT NULL,
  `work_area_id` bigint(20) NOT NULL,
  `effdt` date DEFAULT NULL,
  `descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `admin_descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `active` bit(1) DEFAULT b'1',
  `obj_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT '1',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_task_s`
--

DROP TABLE IF EXISTS `tk_task_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_task_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_time_block_hist_t`
--

DROP TABLE IF EXISTS `tk_time_block_hist_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_t` (
  `TK_TIME_BLOCK_HIST_ID` bigint(20) NOT NULL,
  `TK_TIME_BLOCK_ID` bigint(20) DEFAULT NULL,
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TASK_ID` bigint(20) DEFAULT NULL,
  `ERNCD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BEGIN_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_LOG_CREATED` bit(1) DEFAULT b'0',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `ACTION_HISTORY` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MODIFIED_BY_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_MODIFIED` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `amount` decimal(6,2) DEFAULT '0.00',
  PRIMARY KEY (`TK_TIME_BLOCK_HIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_time_block_hist_s`
--

DROP TABLE IF EXISTS `tk_time_block_hist_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_time_block_t`
--

DROP TABLE IF EXISTS `tk_time_block_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_t` (
  `TK_TIME_BLOCK_ID` bigint(20) NOT NULL,
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TASK_ID` bigint(20) DEFAULT NULL,
  `ERNCD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BEGIN_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_LOG_CREATED` bit(1) DEFAULT b'0',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_TIME_BLOCK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_time_collection_rl_t`
--

DROP TABLE IF EXISTS `tk_time_collection_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_collection_rl_t` (
  `TIME_COLL_RULE_ID` bigint(20) NOT NULL,
  `DEPTID` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `CLOCK_USERS_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `HRS_DISTRIBUTION_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`TIME_COLL_RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_weekly_overtime_rl_t`
--

DROP TABLE IF EXISTS `tk_weekly_overtime_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_weekly_overtime_rl_t` (
  `TK_WEEKLY_OVERTIME_RL_ID` bigint(20) NOT NULL,
  `MAX_HRS_ERN_GROUP` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `CONVERT_FROM_ERN_GROUP` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `CONVERT_TO_ERNCD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `STEP` decimal(2,0) DEFAULT NULL,
  `MAX_HRS` decimal(3,0) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`TK_WEEKLY_OVERTIME_RL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_weekly_overtime_rl_s`
--

DROP TABLE IF EXISTS `tk_weekly_overtime_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_weekly_overtime_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_document_t`
--

DROP TABLE IF EXISTS `tk_work_area_document_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_document_t` (
  `doc_hdr_id` varchar(50) COLLATE utf8_bin NOT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `obj_id` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`doc_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_s`
--

DROP TABLE IF EXISTS `tk_work_area_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_t`
--

DROP TABLE IF EXISTS `tk_work_area_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_t` (
  `WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DEPT_ID` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `OVERTIME_PREFERENCE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-08-10 11:50:57
