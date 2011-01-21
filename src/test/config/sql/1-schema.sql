-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: andover    Database: tk
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.6

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ca_account_t`
--

DROP TABLE IF EXISTS `ca_account_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_account_t` (
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCOUNT_NBR` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACCOUNT_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ORG_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `ACCT_CREATE_DT` datetime DEFAULT NULL,
  `ACCT_EFFECT_DT` datetime DEFAULT NULL,
  `ACCT_EXPIRATION_DT` datetime DEFAULT NULL,
  `ACCT_CLOSED_IND` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FIN_COA_CD`,`ACCOUNT_NBR`),
  UNIQUE KEY `CA_ACCOUNT_TC0` (`OBJ_ID`),
  KEY `CA_ACCOUNT_TI2` (`ACCOUNT_NBR`,`FIN_COA_CD`),
  KEY `CA_ACCOUNT_TR2` (`FIN_COA_CD`,`ORG_CD`),
  CONSTRAINT `CA_ACCOUNT_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`),
  CONSTRAINT `CA_ACCOUNT_TR2` FOREIGN KEY (`FIN_COA_CD`, `ORG_CD`) REFERENCES `ca_org_t` (`FIN_COA_CD`, `ORG_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_chart_t`
--

DROP TABLE IF EXISTS `ca_chart_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_chart_t` (
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `FIN_COA_DESC` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIN_COA_ACTIVE_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FIN_COA_CD`),
  UNIQUE KEY `CA_CHART_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_object_code_t`
--

DROP TABLE IF EXISTS `ca_object_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_object_code_t` (
  `UNIV_FISCAL_YR` decimal(4,0) NOT NULL DEFAULT '0',
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `FIN_OBJECT_CD` varchar(5) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `FIN_OBJ_CD_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIN_OBJ_CD_SHRT_NM` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `FIN_OBJ_ACTIVE_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UNIV_FISCAL_YR`,`FIN_COA_CD`,`FIN_OBJECT_CD`),
  UNIQUE KEY `CA_OBJECT_CODE_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_org_t`
--

DROP TABLE IF EXISTS `ca_org_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_org_t` (
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ORG_CD` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ORG_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ORG_ACTIVE_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FIN_COA_CD`,`ORG_CD`),
  UNIQUE KEY `CA_ORG_TC0` (`OBJ_ID`),
  CONSTRAINT `CA_ORG_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_project_t`
--

DROP TABLE IF EXISTS `ca_project_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_project_t` (
  `PROJECT_CD` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PROJECT_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `ORG_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `PROJ_ACTIVE_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_DESC` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`PROJECT_CD`),
  UNIQUE KEY `CA_PROJECT_TC0` (`OBJ_ID`),
  KEY `CA_PROJECT_TI2` (`FIN_COA_CD`,`ORG_CD`),
  CONSTRAINT `CA_PROJECT_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`),
  CONSTRAINT `CA_PROJECT_TR2` FOREIGN KEY (`FIN_COA_CD`, `ORG_CD`) REFERENCES `ca_org_t` (`FIN_COA_CD`, `ORG_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_sub_acct_t`
--

DROP TABLE IF EXISTS `ca_sub_acct_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_sub_acct_t` (
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCOUNT_NBR` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `SUB_ACCT_NBR` varchar(5) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `SUB_ACCT_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `SUB_ACCT_ACTV_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FIN_COA_CD`,`ACCOUNT_NBR`,`SUB_ACCT_NBR`),
  UNIQUE KEY `CA_SUB_ACCT_TC0` (`OBJ_ID`),
  KEY `CA_SUB_ACCT_TI2` (`FIN_COA_CD`,`ACCOUNT_NBR`),
  CONSTRAINT `CA_SUB_ACCT_TR2` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_sub_object_cd_t`
--

DROP TABLE IF EXISTS `ca_sub_object_cd_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_sub_object_cd_t` (
  `UNIV_FISCAL_YR` decimal(4,0) NOT NULL DEFAULT '0',
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCOUNT_NBR` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `FIN_OBJECT_CD` varchar(5) COLLATE utf8_bin NOT NULL DEFAULT '',
  `FIN_SUB_OBJ_CD` varchar(3) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `FIN_SUB_OBJ_CD_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIN_SUBOBJ_SHRT_NM` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `FIN_SUBOBJ_ACTV_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UNIV_FISCAL_YR`,`FIN_COA_CD`,`ACCOUNT_NBR`,`FIN_OBJECT_CD`,`FIN_SUB_OBJ_CD`),
  UNIQUE KEY `CA_SUB_OBJECT_CD_TC0` (`OBJ_ID`),
  KEY `CA_SUB_OBJECT_CD_TI2` (`UNIV_FISCAL_YR`),
  KEY `CA_SUB_OBJECT_CD_TR3` (`FIN_COA_CD`),
  KEY `CA_SUB_OBJECT_CD_TR1` (`UNIV_FISCAL_YR`,`FIN_COA_CD`,`FIN_OBJECT_CD`),
  CONSTRAINT `CA_SUB_OBJECT_CD_TR1` FOREIGN KEY (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `FIN_OBJECT_CD`) REFERENCES `ca_object_code_t` (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `FIN_OBJECT_CD`),
  CONSTRAINT `CA_SUB_OBJECT_CD_TR3` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_job_t`
--

DROP TABLE IF EXISTS `hr_job_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_t` (
  `HR_JOB_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `dept` varchar(21) COLLATE utf8_bin NOT NULL,
  `TK_SAL_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `PAY_GRADE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `location` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `std_hours` decimal(5,2) DEFAULT NULL,
  `fte` bit(1) DEFAULT NULL,
  `hr_paytype` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`HR_JOB_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=1101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_paytype_t`
--

DROP TABLE IF EXISTS `hr_paytype_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_paytype_t` (
  `HR_PAYTYPE_ID` bigint(20) NOT NULL,
  `PAYTYPE` varchar(5) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `REG_ERN_CODE` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `HOLIDAY_CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`HR_PAYTYPE_ID`)
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
  `HR_WORK_SCHEDULE_ENTRY_ID` bigint(20) NOT NULL,
  `HR_WORK_SCHEDULE_ID` bigint(20) NOT NULL,
  `CALENDAR_GROUP` varchar(45) COLLATE utf8_bin NOT NULL,
  `DAY_OF_PERIOD` int(11) NOT NULL,
  `REG_HOURS` int(11) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`HR_WORK_SCHEDULE_ENTRY_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=1023 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_work_schedule_t`
--

DROP TABLE IF EXISTS `hr_work_schedule_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_t` (
  `HR_WORK_SCHEDULE_ID` bigint(20) NOT NULL,
  `WORK_SCHEDULE_DESC` varchar(30) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`HR_WORK_SCHEDULE_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=1024 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `la_accrual_categories_t`
--

DROP TABLE IF EXISTS `la_accrual_categories_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `la_accrual_categories_t` (
  `LA_ACCRUAL_CATEGORY_ID` bigint(20) NOT NULL,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` bit(1) DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LA_ACCRUAL_CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `la_accrual_categories_s`
--

DROP TABLE IF EXISTS `la_accrual_categories_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `la_accrual_categories_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `la_accruals_t`
--

DROP TABLE IF EXISTS `la_accruals_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `la_accruals_t` (
  `LA_ACCRUALS_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(21) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` datetime NOT NULL,
  `HOURS_ACCRUED` int(11) NOT NULL,
  `HOURS_TAKEN` int(11) NOT NULL,
  `HOURS_ADJUST` int(11) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`LA_ACCRUALS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `la_accruals_s`
--

DROP TABLE IF EXISTS `la_accruals_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `la_accruals_s` (
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
  `TK_ASSIGN_ACCT_ID` bigint(19) NOT NULL,
  `FIN_COA_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `ACCOUNT_NBR` varchar(7) COLLATE utf8_bin DEFAULT NULL,
  `SUB_ACCT_NBR` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `FIN_OBJECT_CD` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `FIN_SUB_OBJ_CD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `ORG_REF_ID` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `PERCENT` decimal(5,2) DEFAULT NULL,
  `TK_ASSIGNMENT_ID` bigint(19) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TK_ASSIGN_ACCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assignment_t`
--

DROP TABLE IF EXISTS `tk_assignment_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_t` (
  `TK_ASSIGNMENT_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(10) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` bit(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TK_ASSIGNMENT_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=2108 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
) ENGINE=MyISAM AUTO_INCREMENT=2233 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_location_rl_t`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_t` (
  `TK_CLOCK_LOC_RULE_ID` bigint(20) NOT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `job_number` bigint(20) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `IP_ADDRESS` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL
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
) ENGINE=MyISAM AUTO_INCREMENT=2478 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_clock_log_t`
--

DROP TABLE IF EXISTS `tk_clock_log_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_log_t` (
  `TK_CLOCK_LOG_ID` bigint(20) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(10) DEFAULT NULL,
  `TK_WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TK_TASK_ID` bigint(20) DEFAULT NULL,
  `CLOCK_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CLOCK_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_ACTION` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `IP_ADDRESS` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `HR_JOB_ID` bigint(20) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_CLOCK_LOG_ID`)
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
  `PAYTYPE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(10) DEFAULT NULL,
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
) ENGINE=MyISAM AUTO_INCREMENT=2096 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_earn_code_t`
--

DROP TABLE IF EXISTS `tk_dept_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_earn_code_t` (
  `tk_dept_earn_code_id` bigint(20) NOT NULL,
  `dept` varchar(21) COLLATE utf8_bin NOT NULL,
  `tk_sal_group` varchar(10) COLLATE utf8_bin NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `employee` bit(1) DEFAULT b'0',
  `approver` bit(1) DEFAULT b'0',
  `org_admin` bit(1) DEFAULT b'0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `active` bit(1) DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_dept_earn_code_id`)
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
) ENGINE=MyISAM AUTO_INCREMENT=2103 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_lunch_rl_t`
--

DROP TABLE IF EXISTS `tk_dept_lunch_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_lunch_rl_t` (
  `TK_DEPT_LUNCH_RL_ID` bigint(20) NOT NULL,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `principal_id` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `job_number` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `REQUIRED_CLOCK_FL` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `MAX_MINS` decimal(2,0) DEFAULT NULL,
  `user_principal_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`TK_DEPT_LUNCH_RL_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_t`
--

DROP TABLE IF EXISTS `tk_dept_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_t` (
  `tk_dept_id` bigint(20) NOT NULL,
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ORG` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CHART` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`tk_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_dept_s`
--

DROP TABLE IF EXISTS `tk_dept_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_code_t`
--

DROP TABLE IF EXISTS `tk_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_code_t` (
  `tk_earn_code_id` int(11) NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `record_time` decimal(5,2) DEFAULT NULL,
  `record_amount` decimal(6,2) DEFAULT NULL,
  `record_hours` decimal(5,2) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
) ENGINE=MyISAM AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_earn_group_def_t`
--

DROP TABLE IF EXISTS `tk_earn_group_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_earn_group_def_t` (
  `tk_earn_group_id` bigint(20) DEFAULT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin DEFAULT NULL,
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
  `tk_earn_group_id` bigint(20) NOT NULL,
  `earn_group` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_earn_group_id`)
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
  `TK_GRACE_PERIOD_RULE_ID` bigint(20) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `GRACE_MINS` decimal(2,0) DEFAULT NULL,
  `HOUR_FACTOR` decimal(2,2) DEFAULT NULL,
  `user_principal_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  `calendar_group` varchar(45) COLLATE utf8_bin NOT NULL,
  `chart` varchar(45) COLLATE utf8_bin NOT NULL,
  `begin_date` date NOT NULL,
  `begin_time` time NOT NULL DEFAULT '00:00:00',
  `end_date` date NOT NULL,
  `end_time` time NOT NULL DEFAULT '23:59:59',
  `flsa_begin_day` varchar(9) not null,
  `flsa_begin_time` time not null,
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
  `begin_period_date` timestamp NOT NULL,
  `end_period_date` timestamp NOT NULL,
  `initiate_date` date DEFAULT NULL,
  `initiate_time` time DEFAULT NULL,
  `end_pay_period_date` date DEFAULT NULL,
  `end_pay_period_time` time DEFAULT NULL,
  `employee_approval_date` date DEFAULT NULL,
  `employee_approval_time` time DEFAULT NULL,
  `supervisor_approval_date` date DEFAULT NULL,
  `supervisor_approval_time` time DEFAULT NULL,
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
  `TK_SAL_GROUP_ID` bigint(20) NOT NULL,
  `TK_SAL_GROUP` varchar(10) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTIVE` varchar(1) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_SAL_GROUP_ID`)
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
  `LOCATION` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `TK_SAL_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `PAY_GRADE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS` TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',
  `MIN_HRS` decimal(2,0) DEFAULT NULL,
  `DAY0` bit(1) DEFAULT NULL,
  `DAY1` bit(1) DEFAULT NULL,
  `DAY2` bit(1) DEFAULT NULL,
  `DAY3` bit(1) DEFAULT NULL,
  `DAY4` bit(1) DEFAULT NULL,
  `DAY5` bit(1) DEFAULT NULL,
  `DAY6` bit(1) DEFAULT NULL,
  `CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `MAX_GAP` decimal(2,0) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
) ENGINE=MyISAM AUTO_INCREMENT=2106 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  `tk_task_id` bigint(20) NOT NULL,
  `task` bigint(10) DEFAULT NULL,
  `work_area` bigint(10) DEFAULT NULL,
  `tk_work_area_id` bigint(20) NOT NULL,
  `descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `admin_descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `obj_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT '1',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tk_task_id`)
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
) ENGINE=MyISAM AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
  `TASK` bigint(10) DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TK_WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `TK_TASK_ID` bigint(20) DEFAULT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BEGIN_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_LOG_CREATED` bit(1) DEFAULT b'0',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTION_HISTORY` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MODIFIED_BY_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_MODIFIED` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `amount` decimal(6,2) DEFAULT '0.00',
  `HR_JOB_ID` bigint(20) DEFAULT NULL,
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
) ENGINE=MyISAM AUTO_INCREMENT=1096 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_time_block_t`
--

DROP TABLE IF EXISTS `tk_time_block_s`;
CREATE TABLE `tk_time_block_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=5109 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tk_time_block_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_t` (
  `TK_TIME_BLOCK_ID` bigint(20) NOT NULL,
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(20) DEFAULT NULL,
  `TK_WORK_AREA_ID` bigint(20) DEFAULT NULL,
  `HR_JOB_ID` bigint(20) DEFAULT NULL,
  `TK_TASK_ID` bigint(20) DEFAULT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BEGIN_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_LOG_CREATED` bit(1) DEFAULT b'0',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_TIME_BLOCK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `tk_time_collection_rl_s`;
CREATE TABLE `tk_time_collection_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1018 DEFAULT CHARSET=latin1;

--
-- Table structure for table `tk_time_collection_rl_t`
--

DROP TABLE IF EXISTS `tk_time_collection_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_collection_rl_t` (
  `TK_TIME_COLL_RULE_ID` bigint(20) NOT NULL,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `CLOCK_USERS_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `HRS_DISTRIBUTION_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `ACTIVE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`TK_TIME_COLL_RULE_ID`)
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
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
) ENGINE=MyISAM AUTO_INCREMENT=1021 DEFAULT CHARSET=latin1;
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
) ENGINE=MyISAM AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_t`
--

DROP TABLE IF EXISTS `tk_work_area_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_t` (
  `TK_WORK_AREA_ID` bigint(20) NOT NULL,
  `WORK_AREA` bigint(10) NOT NULL,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DEFAULT_OVERTIME_PREFERENCE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`TK_WORK_AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_hour_detail_s`
--

DROP TABLE IF EXISTS `tk_hour_detail_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1049 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_hour_detail_t`
--

DROP TABLE IF EXISTS `tk_hour_detail_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_t` (
  `TK_HOUR_DETAIL_ID` bigint(20) NOT NULL,
  `TK_TIME_BLOCK_ID` bigint(20) NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_HOUR_DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_roles_s`
--

DROP TABLE IF EXISTS `tk_roles_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_roles_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_roles_t`
--

DROP TABLE IF EXISTS `tk_roles_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_roles_t` (
  `tk_roles_id` bigint(19) NOT NULL,
  `principal_id` varchar(20) COLLATE utf8_bin NOT NULL,
  `role_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `work_area` bigint(20) DEFAULT NULL,
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`tk_roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_ovt_pref_t`
--

DROP TABLE IF EXISTS `tk_work_area_ovt_pref_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_ovt_pref_t` (
  `tk_work_area_ovt_pref_id` bigint(20) NOT NULL,
  `tk_work_area_id` bigint(20) DEFAULT NULL,
  `paytype` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `overtime_preference` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tk_work_area_ovt_pref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_work_area_ovt_pref_s`
--

DROP TABLE IF EXISTS `tk_work_area_ovt_pref_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_ovt_pref_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1018 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_hour_detail_hist_t`
--

DROP TABLE IF EXISTS `tk_hour_detail_hist_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_hist_t` (
  `TK_HOUR_DETAIL_HIST_ID` bigint(20) NOT NULL,
  `TK_HOUR_DETAIL_ID` bigint(20) NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTION_HISTORY` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MODIFIED_BY_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_MODIFIED` timestamp NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_hour_detail_hist_s`
--

DROP TABLE IF EXISTS `tk_hour_detail_hist_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_hist_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_document_header_t`
--

DROP TABLE IF EXISTS `tk_document_header_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_document_header_t` (
  `DOCUMENT_ID` bigint(19) NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PAY_END_DT` datetime DEFAULT NULL,
  `DOCUMENT_STATUS` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `PAY_BEGIN_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`DOCUMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `tk_principal_calendar_t`;
CREATE TABLE `tk_principal_calendar_t` (
  `principal_id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `calendar_group` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `holiday_calendar_group` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE hr_job_t ADD COLUMN `comp_rate` DECIMAL(5,2) NULL DEFAULT 0.00  AFTER `VER_NBR` ;
ALTER TABLE `tk_daily_overtime_rl_t` CHANGE COLUMN `MAX_GAP` `MAX_GAP` DECIMAL(5,2) NULL DEFAULT 0.00  ;
ALTER TABLE `tk_shift_differential_rl_t` CHANGE COLUMN `MAX_GAP` `MAX_GAP` DECIMAL(5,2) NULL DEFAULT 0.00 ;

ALTER TABLE tk_clock_location_rl_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT NULL  ;
ALTER TABLE tk_daily_overtime_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT NULL  ;

ALTER TABLE tk_earn_group_t ADD COLUMN `show_summary` VARCHAR(1) NULL DEFAULT 'N'  AFTER `descr` ;

ALTER TABLE tk_system_lunch_rl_t DROP COLUMN `BLOCK_HOURS` , DROP COLUMN `MIN_MINS` , ADD COLUMN `SHOW_LUNCH_BUTTON` VARCHAR(1) NULL DEFAULT '0'  AFTER `USER_PRINCIPAL_ID` ;
ALTER TABLE tk_dept_lunch_rl_t ADD COLUMN `SHIFT_HOURS` DECIMAL(2,1) NULL DEFAULT NULL  ;
ALTER TABLE tk_dept_lunch_rl_t ADD COLUMN `DEDUCTION_MINS` DECIMAL(3,0) NULL DEFAULT NULL  ;
ALTER TABLE tk_dept_lunch_rl_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT NULL  ;

ALTER TABLE tk_sal_group_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT NULL  ;
ALTER TABLE tk_grace_period_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT NULL  ;
ALTER TABLE tk_earn_code_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT NULL  ;
ALTER TABLE tk_earn_code_t ADD COLUMN `ACCRUAL_CATEGORY` VARCHAR(3) NULL DEFAULT NULL  ;
ALTER TABLE tk_earn_code_t CHANGE COLUMN `record_time` `record_time` VARCHAR(1) NULL DEFAULT 'N'  , CHANGE COLUMN `record_amount` `record_amount` VARCHAR(1) NULL DEFAULT 'N'  , CHANGE COLUMN `record_hours` `record_hours` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE la_accrual_categories_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE la_accruals_t CHANGE COLUMN `HOURS_ACCRUED` `HOURS_ACCRUED` DECIMAL(6,2) NOT NULL  , CHANGE COLUMN `HOURS_TAKEN` `HOURS_TAKEN` DECIMAL(6,2) NOT NULL  , CHANGE COLUMN `HOURS_ADJUST` `HOURS_ADJUST` DECIMAL(6,2) NOT NULL  ;

ALTER TABLE tk_dept_earn_code_t CHANGE COLUMN `employee` `employee` VARCHAR(1) NULL DEFAULT 'N'  , CHANGE COLUMN `approver` `approver` VARCHAR(1) NULL DEFAULT 'N'  , CHANGE COLUMN `org_admin` `org_admin` VARCHAR(1) NULL DEFAULT 'N'  , CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_earn_group_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT NULL  ;
ALTER TABLE hr_job_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT 'N'  ;

ALTER TABLE tk_earn_code_t ADD COLUMN `inflate_factor` DECIMAL(3,2) NOT NULL DEFAULT 1  AFTER `accrual_category` , ADD COLUMN `inflate_min_hours` DECIMAL(3,2) NOT NULL DEFAULT 0  AFTER `accrual_category` ;

ALTER TABLE tk_system_lunch_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT NULL  ;

ALTER TABLE tk_shift_differential_rl_t DROP COLUMN `day0`,DROP COLUMN `day1`,DROP COLUMN `day2`,DROP COLUMN `day3`,
										DROP COLUMN `day4`,DROP COLUMN `day5`,DROP COLUMN `day6`,ADD COLUMN `sun` BIT(1) NOT NULL DEFAULT 0,
										ADD COLUMN `mon` BIT(1) NOT NULL DEFAULT 0,ADD COLUMN `tue` BIT(1) NOT NULL DEFAULT 0,ADD COLUMN `wed` BIT(1) NOT NULL DEFAULT 0,
										ADD COLUMN `thu` BIT(1) NOT NULL DEFAULT 0,ADD COLUMN `fri` BIT(1) NOT NULL DEFAULT 0,ADD COLUMN `sat` BIT(1) NOT NULL DEFAULT 0;

ALTER TABLE hr_paytype_t DROP COLUMN `CALENDAR_GROUP`, DROP COLUMN `HOLIDAY_CALENDAR_GROUP`;
ALTER TABLE hr_paytype_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE hr_work_schedule_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_assignment_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_dept_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_roles_t CHANGE COLUMN `active` `active` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_shift_differential_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_time_collection_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_weekly_overtime_rl_t CHANGE COLUMN `ACTIVE` `ACTIVE` VARCHAR(1) NULL DEFAULT 'N'  ;
ALTER TABLE tk_document_header_t ADD COLUMN `OBJ_ID` VARCHAR(36) NULL DEFAULT NULL, ADD COLUMN `VER_NBR` BIGINT(20) NULL DEFAULT 1;
ALTER TABLE tk_py_calendar_t DROP COLUMN `begin_date` ,
DROP COLUMN `begin_time` , DROP COLUMN `chart` , DROP COLUMN `end_date` , DROP COLUMN `end_time` ;

DROP TABLE IF EXISTS `tk_py_calendar_entries_t`;
DROP TABLE IF EXISTS `tk_py_calendar_entries_s`;
alter table tk_py_calendar_dates_t rename tk_py_calendar_entries_t;
alter table tk_py_calendar_dates_s rename tk_py_calendar_entries_s;

ALTER TABLE tk_py_calendar_entries_t CHANGE COLUMN `tk_py_calendar_dates_id` `tk_py_calendar_entry_id` BIGINT(20) NOT NULL
, DROP PRIMARY KEY , ADD PRIMARY KEY (`tk_py_calendar_entry_id`) ;

DROP TABLE IF EXISTS `tk_user_pref_t`;
create table tk_user_pref_t (
  `PRINCIPAL_ID` varchar(40) NOT NULL,
  `TIME_ZONE` varchar(30) NULL,
    PRIMARY KEY (`PRINCIPAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `tk_weekly_ovt_group_t`;
CREATE TABLE tk_weekly_ovt_group_t
( `TK_WEEKLY_OVERTIME_GROUP_ID` BIGINT(20) NOT NULL,PRIMARY KEY (`TK_WEEKLY_OVERTIME_GROUP_ID`))
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `tk_weekly_overtime_rl_t` ADD COLUMN `TK_WEEKLY_OVT_GROUP_ID` BIGINT(20) NOT NULL DEFAULT 1  AFTER `ACTIVE` ;

ALTER TABLE `tk_document_header_t` CHANGE COLUMN `DOCUMENT_ID` `DOCUMENT_ID` VARCHAR(14) NOT NULL  ;

ALTER TABLE `tk_holiday_calendar_dates_t` ADD COLUMN `HOLIDAY_HRS` DECIMAL(2,2) NOT NULL  AFTER `VER_NBR` ;

ALTER TABLE `tk_daily_overtime_rl_t` CHANGE COLUMN `SHIFT_HOURS` `MIN_HOURS` DECIMAL(2,0) NULL DEFAULT NULL  ;
ALTER TABLE `tk_daily_overtime_rl_t` ADD COLUMN `FROM_EARN_GROUP` VARCHAR(10) NULL  AFTER `ACTIVE` , ADD COLUMN `EARN_CODE` VARCHAR(10) NULL  AFTER `FROM_EARN_GROUP` , CHANGE COLUMN `LOCATION` `LOCATION` VARCHAR(40) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL  , CHANGE COLUMN `MIN_HOURS` `MIN_HOURS` DECIMAL(2,2) NULL DEFAULT NULL  ;
ALTER TABLE `tk_daily_overtime_rl_t` CHANGE COLUMN `MIN_HOURS` `MIN_HOURS` DECIMAL(2,0) NULL DEFAULT NULL  ;
ALTER TABLE `hr_job_t` ADD COLUMN `primary_indicator` VARCHAR(1) DEFAULT 'N';
ALTER TABLE `tk_daily_overtime_rl_t` DROP COLUMN `TASK` ;
ALTER TABLE `tk_roles_t` ADD COLUMN `tk_dept_id` BIGINT(20) NULL  AFTER `active`;
ALTER TABLE `tk_roles_t` ADD COLUMN `tk_work_area_id` BIGINT(20) NULL  AFTER `tk_dept_id`;

DROP TABLE IF EXISTS `tk_missed_punch_t`;
CREATE TABLE `tk_missed_punch_t` (
  `tk_missed_punch_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `principal_id` VARCHAR(40) NOT NULL ,
  `clock_action` VARCHAR(20) NOT NULL ,
  `action_date` DATETIME NOT NULL DEFAULT '0000-00-00',
  `action_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `document_id` VARCHAR(14) NULL ,
  `document_status` VARCHAR(1) NULL ,
  `tk_clock_log_id` BIGINT(20) NULL ,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`tk_missed_punch_id`) );

ALTER TABLE `tk_dept_t` CHANGE COLUMN `DESCRIPTION` `DESCRIPTION` VARCHAR(75) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL  ;

ALTER TABLE tk_principal_calendar_t ADD COLUMN `active` VARCHAR(1) NULL DEFAULT 'Y'  AFTER `VER_NBR` ;

ALTER TABLE tk_dept_earn_code_t ADD COLUMN `location` VARCHAR(10) NULL AFTER `VER_NBR`;

ALTER TABLE tk_work_area_t DROP PRIMARY KEY;

ALTER TABLE tk_work_area_t modify work_area bigint(10) NOT NULL auto_increment, ADD PRIMARY KEY (work_area);

DROP TABLE IF EXISTS `tk_wa_maint_doc_t`;
CREATE TABLE `tk_wa_maint_doc_t` (
  `doc_hdr_id` varchar(50) COLLATE utf8_bin PRIMARY KEY NOT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `obj_id` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `doc_cntnt` LONGTEXT);

ALTER TABLE `tk_task_t`
  ADD COLUMN `effdt` DATE NOT NULL  AFTER `USER_PRINCIPAL_ID` ,
  ADD COLUMN `active` VARCHAR(1) NOT NULL DEFAULT 'Y'  AFTER `effdt` ,
  ADD COLUMN `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `active` ;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-10-15 11:10:06
