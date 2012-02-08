-- MySQL dump 10.13  Distrib 5.1.49, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tk-test
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1

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


CREATE TABLE `hr_work_schedule_t` (`HR_WORK_SCHEDULE_ID` BIGINT NOT NULL,
`HR_WORK_SCHEDULE` BIGINT NOT NULL,
`WORK_SCHEDULE_DESC` VARCHAR(30) NOT NULL,
`EFFDT` DATE DEFAULT '0002-11-30' NOT NULL,
`ACTIVE` VARCHAR(1) DEFAULT 'N',
 `OBJ_ID` VARCHAR(36),
  `VER_NBR` BIGINT DEFAULT 1 NOT NULL,
   `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_T` PRIMARY KEY (`HR_WORK_SCHEDULE_ID`));

CREATE TABLE `hr_holiday_calendar_dates_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_DATES_S` PRIMARY KEY (`id`));

-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-19::djunk (generated)::(Checksum: 3:da464cc64b5c3f7630ec2eff73e0927c)
CREATE TABLE `hr_holiday_calendar_dates_t` (`HR_HOLIDAY_CALENDAR_DATES_ID` VARCHAR(60) NOT NULL, `HOLIDAY_DATE` DATE NOT NULL, `HOLIDAY_DESC` VARCHAR(30) NOT NULL, `HR_HOLIDAY_CALENDAR_ID` VARCHAR(60) NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `HOLIDAY_HRS` DECIMAL(4,2) NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_DATES_T` PRIMARY KEY (`HR_HOLIDAY_CALENDAR_DATES_ID`));

-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-20::djunk (generated)::(Checksum: 3:fde6efcc06a92acf68e445d63c59db25)
CREATE TABLE `hr_holiday_calendar_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_S` PRIMARY KEY (`id`));

-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-21::djunk (generated)::(Checksum: 3:41eb52b1d8cf46b67342f5e6d41288e8)
CREATE TABLE `hr_holiday_calendar_t` (`HR_HOLIDAY_CALENDAR_ID` VARCHAR(60) NOT NULL, `HOLIDAY_CALENDAR_GROUP` VARCHAR(3) NOT NULL, `DESCR` VARCHAR(30), `location` VARCHAR(30), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `active` VARCHAR(1) DEFAULT 'Y' NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_T` PRIMARY KEY (`HR_HOLIDAY_CALENDAR_ID`));

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
-- Dumping data for table `ca_account_t`
--

LOCK TABLES `ca_account_t` WRITE;
/*!40000 ALTER TABLE `ca_account_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_account_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_chart_t`
--

LOCK TABLES `ca_chart_t` WRITE;
/*!40000 ALTER TABLE `ca_chart_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_chart_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_object_code_t`
--

LOCK TABLES `ca_object_code_t` WRITE;
/*!40000 ALTER TABLE `ca_object_code_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_object_code_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_org_t`
--

LOCK TABLES `ca_org_t` WRITE;
/*!40000 ALTER TABLE `ca_org_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_org_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_project_t`
--

LOCK TABLES `ca_project_t` WRITE;
/*!40000 ALTER TABLE `ca_project_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_project_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_sub_acct_t`
--

LOCK TABLES `ca_sub_acct_t` WRITE;
/*!40000 ALTER TABLE `ca_sub_acct_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_sub_acct_t` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ca_sub_object_cd_t`
--

LOCK TABLES `ca_sub_object_cd_t` WRITE;
/*!40000 ALTER TABLE `ca_sub_object_cd_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca_sub_object_cd_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_calendar_entries_s`
--

DROP TABLE IF EXISTS `hr_calendar_entries_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_calendar_entries_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2116 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_calendar_entries_s`
--

LOCK TABLES `hr_calendar_entries_s` WRITE;
/*!40000 ALTER TABLE `hr_calendar_entries_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_calendar_entries_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_calendar_entries_t`
--

DROP TABLE IF EXISTS `hr_calendar_entries_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_calendar_entries_t` (
  `hr_calendar_entry_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `hr_calendar_id` bigint(20) DEFAULT NULL,
  `calendar_name` varchar(15) COLLATE utf8_bin NOT NULL,
  `begin_period_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_period_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `initiate_date` date DEFAULT NULL,
  `initiate_time` time DEFAULT NULL,
  `end_pay_period_date` date DEFAULT NULL,
  `end_pay_period_time` time DEFAULT NULL,
  `employee_approval_date` date DEFAULT NULL,
  `employee_approval_time` time DEFAULT NULL,
  `supervisor_approval_date` date DEFAULT NULL,
  `supervisor_approval_time` time DEFAULT NULL,
  PRIMARY KEY (`hr_calendar_entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_calendar_entries_t`
--

LOCK TABLES `hr_calendar_entries_t` WRITE;
/*!40000 ALTER TABLE `hr_calendar_entries_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_calendar_entries_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_calendar_s`
--

DROP TABLE IF EXISTS `hr_calendar_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_calendar_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2088 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_calendar_s`
--

LOCK TABLES `hr_calendar_s` WRITE;
/*!40000 ALTER TABLE `hr_calendar_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_calendar_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_calendar_t`
--

DROP TABLE IF EXISTS `hr_calendar_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_calendar_t` (
  `hr_calendar_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `calendar_name` varchar(15) COLLATE utf8_bin NOT NULL,
  `flsa_begin_day` varchar(9) COLLATE utf8_bin NOT NULL,
  `flsa_begin_time` time NOT NULL,
  `calendar_types` varchar(9) COLLATE utf8_bin DEFAULT NULL,
  `calendar_descriptions` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`hr_calendar_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_calendar_t`
--

LOCK TABLES `hr_calendar_t` WRITE;
/*!40000 ALTER TABLE `hr_calendar_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_calendar_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_dept_earn_code_s`
--

DROP TABLE IF EXISTS `hr_dept_earn_code_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_dept_earn_code_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2130 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_dept_earn_code_s`
--

LOCK TABLES `hr_dept_earn_code_s` WRITE;
/*!40000 ALTER TABLE `hr_dept_earn_code_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_dept_earn_code_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_dept_earn_code_t`
--

DROP TABLE IF EXISTS `hr_dept_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_dept_earn_code_t` (
  `hr_dept_earn_code_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `dept` varchar(21) COLLATE utf8_bin NOT NULL,
  `hr_sal_group` varchar(10) COLLATE utf8_bin NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `employee` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `approver` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `org_admin` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `location` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `active` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `effdt` date DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hr_dept_earn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_dept_earn_code_t`
--

LOCK TABLES `hr_dept_earn_code_t` WRITE;
/*!40000 ALTER TABLE `hr_dept_earn_code_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_dept_earn_code_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_dept_s`
--

DROP TABLE IF EXISTS `hr_dept_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_dept_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2108 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_dept_s`
--

LOCK TABLES `hr_dept_s` WRITE;
/*!40000 ALTER TABLE `hr_dept_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_dept_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_dept_t`
--

DROP TABLE IF EXISTS `hr_dept_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_dept_t` (
  `hr_dept_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(75) COLLATE utf8_bin DEFAULT NULL,
  `ORG` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CHART` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `location` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`hr_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_dept_t`
--

LOCK TABLES `hr_dept_t` WRITE;
/*!40000 ALTER TABLE `hr_dept_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_dept_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_code_s`
--

DROP TABLE IF EXISTS `hr_earn_code_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_code_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1061 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_code_s`
--

LOCK TABLES `hr_earn_code_s` WRITE;
/*!40000 ALTER TABLE `hr_earn_code_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_code_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_code_t`
--

DROP TABLE IF EXISTS `hr_earn_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_code_t` (
  `hr_earn_code_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin NOT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `ovt_earn_code` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `record_time` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `record_amount` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `record_hours` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `inflate_min_hours` decimal(3,2) NOT NULL DEFAULT '0.00',
  `inflate_factor` decimal(3,2) NOT NULL DEFAULT '1.00',
  PRIMARY KEY (`hr_earn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_code_t`
--

LOCK TABLES `hr_earn_code_t` WRITE;
/*!40000 ALTER TABLE `hr_earn_code_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_code_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_group_def_s`
--

DROP TABLE IF EXISTS `hr_earn_group_def_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_group_def_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2132 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_group_def_s`
--

LOCK TABLES `hr_earn_group_def_s` WRITE;
/*!40000 ALTER TABLE `hr_earn_group_def_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_group_def_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_group_def_t`
--

DROP TABLE IF EXISTS `hr_earn_group_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_group_def_t` (
  `hr_earn_group_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `earn_code` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `hr_earn_group_def_id` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`hr_earn_group_def_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_group_def_t`
--

LOCK TABLES `hr_earn_group_def_t` WRITE;
/*!40000 ALTER TABLE `hr_earn_group_def_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_group_def_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_group_s`
--

DROP TABLE IF EXISTS `hr_earn_group_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_group_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2114 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_group_s`
--

LOCK TABLES `hr_earn_group_s` WRITE;
/*!40000 ALTER TABLE `hr_earn_group_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_group_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_earn_group_t`
--

DROP TABLE IF EXISTS `hr_earn_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_earn_group_t` (
  `hr_earn_group_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `earn_group` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `show_summary` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `effdt` date DEFAULT NULL,
  `active` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `warning_text` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hr_earn_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_earn_group_t`
--

LOCK TABLES `hr_earn_group_t` WRITE;
/*!40000 ALTER TABLE `hr_earn_group_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_earn_group_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_job_s`
--

DROP TABLE IF EXISTS `hr_job_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1138 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_job_s`
--

LOCK TABLES `hr_job_s` WRITE;
/*!40000 ALTER TABLE `hr_job_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_job_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_job_t`
--

DROP TABLE IF EXISTS `hr_job_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_t` (
  `hr_job_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `dept` varchar(90) COLLATE utf8_bin NOT NULL,
  `HR_SAL_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `pay_grade` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `comp_rate` decimal(18,6) DEFAULT '0.000000',
  `location` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `std_hours` decimal(5,2) DEFAULT NULL,
  `fte` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `hr_paytype` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `active` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `primary_indicator` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `position_nbr` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `eligible_for_leave` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`hr_job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_job_t`
--

LOCK TABLES `hr_job_t` WRITE;
/*!40000 ALTER TABLE `hr_job_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_job_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_location_s`
--

DROP TABLE IF EXISTS `hr_location_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_location_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2111 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_location_s`
--

LOCK TABLES `hr_location_s` WRITE;
/*!40000 ALTER TABLE `hr_location_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_location_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_location_t`
--

DROP TABLE IF EXISTS `hr_location_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_location_t` (
  `hr_location_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `location` varchar(20) COLLATE utf8_bin NOT NULL,
  `timezone` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT 'America\\Indianapolis',
  `description` varchar(60) COLLATE utf8_bin NOT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` char(1) COLLATE utf8_bin NOT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_location_t`
--

LOCK TABLES `hr_location_t` WRITE;
/*!40000 ALTER TABLE `hr_location_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_location_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_pay_grade_s`
--

DROP TABLE IF EXISTS `hr_pay_grade_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_pay_grade_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2097 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_pay_grade_s`
--

LOCK TABLES `hr_pay_grade_s` WRITE;
/*!40000 ALTER TABLE `hr_pay_grade_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_pay_grade_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_pay_grade_t`
--

DROP TABLE IF EXISTS `hr_pay_grade_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_pay_grade_t` (
  `hr_pay_grade_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `pay_grade` varchar(20) COLLATE utf8_bin NOT NULL,
  `sal_group` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(40) COLLATE utf8_bin NOT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` char(1) COLLATE utf8_bin NOT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_pay_grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_pay_grade_t`
--

LOCK TABLES `hr_pay_grade_t` WRITE;
/*!40000 ALTER TABLE `hr_pay_grade_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_pay_grade_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_paytype_s`
--

DROP TABLE IF EXISTS `hr_paytype_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_paytype_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2092 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_paytype_s`
--

LOCK TABLES `hr_paytype_s` WRITE;
/*!40000 ALTER TABLE `hr_paytype_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_paytype_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_paytype_t`
--

DROP TABLE IF EXISTS `hr_paytype_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_paytype_t` (
  `hr_paytype_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `PAYTYPE` varchar(5) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `REG_ERN_CODE` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`hr_paytype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_paytype_t`
--

LOCK TABLES `hr_paytype_t` WRITE;
/*!40000 ALTER TABLE `hr_paytype_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_paytype_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_position_id_s`
--

DROP TABLE IF EXISTS `hr_position_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_position_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2092 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_position_id_s`
--

LOCK TABLES `hr_position_id_s` WRITE;
/*!40000 ALTER TABLE `hr_position_id_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_position_id_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_position_s`
--

DROP TABLE IF EXISTS `hr_position_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_position_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2088 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_position_s`
--

LOCK TABLES `hr_position_s` WRITE;
/*!40000 ALTER TABLE `hr_position_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_position_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_position_t`
--

DROP TABLE IF EXISTS `hr_position_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_position_t` (
  `hr_position_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `POSITION_NBR` varchar(20) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(40) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `WORK_AREA` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_position_t`
--

LOCK TABLES `hr_position_t` WRITE;
/*!40000 ALTER TABLE `hr_position_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_position_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_principal_attributes_t`
--

DROP TABLE IF EXISTS `hr_principal_attributes_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_principal_attributes_t` (
  `principal_id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `pay_calendar` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `leave_plan` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `service_date` date DEFAULT NULL,
  `fmla_eligible` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `worksman_eligible` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `timezone` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `active` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `leave_calendar` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_principal_attributes_t`
--

LOCK TABLES `hr_principal_attributes_t` WRITE;
/*!40000 ALTER TABLE `hr_principal_attributes_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_principal_attributes_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_roles_group_t`
--

DROP TABLE IF EXISTS `hr_roles_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_roles_group_t` (
  `principal_id` varchar(20) NOT NULL,
  PRIMARY KEY (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_roles_group_t`
--

LOCK TABLES `hr_roles_group_t` WRITE;
/*!40000 ALTER TABLE `hr_roles_group_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_roles_group_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_roles_s`
--

DROP TABLE IF EXISTS `hr_roles_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_roles_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3062 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_roles_s`
--

LOCK TABLES `hr_roles_s` WRITE;
/*!40000 ALTER TABLE `hr_roles_s` DISABLE KEYS */;
INSERT INTO `hr_roles_s` VALUES (1000);
/*!40000 ALTER TABLE `hr_roles_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_roles_t`
--

DROP TABLE IF EXISTS `hr_roles_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_roles_t` (
  `hr_roles_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `role_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `work_area` bigint(20) DEFAULT NULL,
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `chart` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `position_nbr` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `expdt` date DEFAULT NULL,
  `tk_work_area_id` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_roles_t`
--

LOCK TABLES `hr_roles_t` WRITE;
/*!40000 ALTER TABLE `hr_roles_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_roles_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_sal_group_s`
--

DROP TABLE IF EXISTS `hr_sal_group_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_sal_group_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2091 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_sal_group_s`
--

LOCK TABLES `hr_sal_group_s` WRITE;
/*!40000 ALTER TABLE `hr_sal_group_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_sal_group_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_sal_group_t`
--

DROP TABLE IF EXISTS `hr_sal_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_sal_group_t` (
  `hr_sal_group_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `HR_SAL_GROUP` varchar(10) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTIVE` varchar(1) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(38) COLLATE utf8_bin NOT NULL DEFAULT '1',
  `VER_NBR` bigint(38) NOT NULL DEFAULT '1',
  `descr` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_sal_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_sal_group_t`
--

LOCK TABLES `hr_sal_group_t` WRITE;
/*!40000 ALTER TABLE `hr_sal_group_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_sal_group_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_work_schedule_assign_s`
--

DROP TABLE IF EXISTS `hr_work_schedule_assign_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_work_schedule_assign_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1023 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_work_schedule_assign_s`
--

LOCK TABLES `hr_work_schedule_assign_s` WRITE;
/*!40000 ALTER TABLE `hr_work_schedule_assign_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_work_schedule_assign_s` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `hr_work_schedule_entry_s`
--

LOCK TABLES `hr_work_schedule_entry_s` WRITE;
/*!40000 ALTER TABLE `hr_work_schedule_entry_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_work_schedule_entry_s` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `hr_work_schedule_s`
--

LOCK TABLES `hr_work_schedule_s` WRITE;
/*!40000 ALTER TABLE `hr_work_schedule_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `hr_work_schedule_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kr_unittest_t`
--

DROP TABLE IF EXISTS `kr_unittest_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_unittest_t` (
  `foo` varchar(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kr_unittest_t`
--

LOCK TABLES `kr_unittest_t` WRITE;
/*!40000 ALTER TABLE `kr_unittest_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `kr_unittest_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_adhoc_rte_actn_recip_t`
--

DROP TABLE IF EXISTS `krns_adhoc_rte_actn_recip_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_adhoc_rte_actn_recip_t` (
  `RECIP_TYP_CD` decimal(1,0) NOT NULL DEFAULT '0',
  `ACTN_RQST_CD` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACTN_RQST_RECIP_ID` varchar(70) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`RECIP_TYP_CD`,`ACTN_RQST_CD`,`ACTN_RQST_RECIP_ID`,`DOC_HDR_ID`),
  UNIQUE KEY `KRNS_ADHOC_RTE_ACTN_RECIP_TC0` (`OBJ_ID`),
  KEY `KRNS_ADHOC_RTE_ACTN_RECIP_T2` (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_adhoc_rte_actn_recip_t`
--

LOCK TABLES `krns_adhoc_rte_actn_recip_t` WRITE;
/*!40000 ALTER TABLE `krns_adhoc_rte_actn_recip_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_adhoc_rte_actn_recip_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_att_t`
--

DROP TABLE IF EXISTS `krns_att_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_att_t` (
  `NTE_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `MIME_TYP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FILE_NM` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `ATT_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `FILE_SZ` decimal(14,0) DEFAULT NULL,
  `ATT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`NTE_ID`),
  UNIQUE KEY `KRNS_ATT_TC0` (`OBJ_ID`),
  CONSTRAINT `KRNS_ATT_TR1` FOREIGN KEY (`NTE_ID`) REFERENCES `krns_nte_t` (`NTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_att_t`
--

LOCK TABLES `krns_att_t` WRITE;
/*!40000 ALTER TABLE `krns_att_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_att_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_doc_hdr_t`
--

DROP TABLE IF EXISTS `krns_doc_hdr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_doc_hdr_t` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `FDOC_DESC` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ORG_DOC_HDR_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TMPL_DOC_HDR_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `EXPLANATION` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_ID`),
  UNIQUE KEY `KRNS_DOC_HDR_TC0` (`OBJ_ID`),
  KEY `KRNS_DOC_HDR_TI3` (`ORG_DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_doc_hdr_t`
--

LOCK TABLES `krns_doc_hdr_t` WRITE;
/*!40000 ALTER TABLE `krns_doc_hdr_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_doc_hdr_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_lock_s`
--

DROP TABLE IF EXISTS `krns_lock_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_lock_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_lock_s`
--

LOCK TABLES `krns_lock_s` WRITE;
/*!40000 ALTER TABLE `krns_lock_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_lock_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_lookup_rslt_s`
--

DROP TABLE IF EXISTS `krns_lookup_rslt_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_lookup_rslt_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_lookup_rslt_s`
--

LOCK TABLES `krns_lookup_rslt_s` WRITE;
/*!40000 ALTER TABLE `krns_lookup_rslt_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_lookup_rslt_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_lookup_rslt_t`
--

DROP TABLE IF EXISTS `krns_lookup_rslt_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_lookup_rslt_t` (
  `LOOKUP_RSLT_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `LOOKUP_DT` datetime NOT NULL,
  `SERIALZD_RSLTS` longtext COLLATE utf8_bin,
  PRIMARY KEY (`LOOKUP_RSLT_ID`),
  UNIQUE KEY `KRNS_LOOKUP_RSLT_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_lookup_rslt_t`
--

LOCK TABLES `krns_lookup_rslt_t` WRITE;
/*!40000 ALTER TABLE `krns_lookup_rslt_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_lookup_rslt_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_lookup_sel_t`
--

DROP TABLE IF EXISTS `krns_lookup_sel_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_lookup_sel_t` (
  `LOOKUP_RSLT_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `LOOKUP_DT` datetime NOT NULL,
  `SEL_OBJ_IDS` longtext COLLATE utf8_bin,
  PRIMARY KEY (`LOOKUP_RSLT_ID`),
  UNIQUE KEY `KRNS_LOOKUP_SEL_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_lookup_sel_t`
--

LOCK TABLES `krns_lookup_sel_t` WRITE;
/*!40000 ALTER TABLE `krns_lookup_sel_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_lookup_sel_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_maint_doc_att_t`
--

DROP TABLE IF EXISTS `krns_maint_doc_att_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_doc_att_t` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ATT_CNTNT` longblob NOT NULL,
  `FILE_NM` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `CNTNT_TYP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`DOC_HDR_ID`),
  UNIQUE KEY `KRNS_MAINT_DOC_ATT_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_maint_doc_att_t`
--

LOCK TABLES `krns_maint_doc_att_t` WRITE;
/*!40000 ALTER TABLE `krns_maint_doc_att_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_maint_doc_att_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_maint_doc_t`
--

DROP TABLE IF EXISTS `krns_maint_doc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_doc_t` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DOC_CNTNT` longtext COLLATE utf8_bin,
  PRIMARY KEY (`DOC_HDR_ID`),
  UNIQUE KEY `KRNS_MAINT_DOC_TC0` (`OBJ_ID`),
  CONSTRAINT `KRNS_MAINT_DOC_TR1` FOREIGN KEY (`DOC_HDR_ID`) REFERENCES `krns_doc_hdr_t` (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_maint_doc_t`
--

LOCK TABLES `krns_maint_doc_t` WRITE;
/*!40000 ALTER TABLE `krns_maint_doc_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_maint_doc_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_maint_lock_s`
--

DROP TABLE IF EXISTS `krns_maint_lock_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_lock_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2873 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_maint_lock_s`
--

LOCK TABLES `krns_maint_lock_s` WRITE;
/*!40000 ALTER TABLE `krns_maint_lock_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_maint_lock_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_maint_lock_t`
--

DROP TABLE IF EXISTS `krns_maint_lock_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_lock_t` (
  `MAINT_LOCK_REP_TXT` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL,
  `MAINT_LOCK_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`MAINT_LOCK_ID`),
  UNIQUE KEY `KRNS_MAINT_LOCK_TC0` (`OBJ_ID`),
  KEY `KRNS_MAINT_LOCK_TI2` (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_maint_lock_t`
--

LOCK TABLES `krns_maint_lock_t` WRITE;
/*!40000 ALTER TABLE `krns_maint_lock_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_maint_lock_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_nte_s`
--

DROP TABLE IF EXISTS `krns_nte_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_nte_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2028 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_nte_s`
--

LOCK TABLES `krns_nte_s` WRITE;
/*!40000 ALTER TABLE `krns_nte_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_nte_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_nte_t`
--

DROP TABLE IF EXISTS `krns_nte_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_nte_t` (
  `NTE_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `RMT_OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `AUTH_PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `POST_TS` datetime NOT NULL,
  `NTE_TYP_CD` varchar(4) COLLATE utf8_bin NOT NULL,
  `TXT` varchar(800) COLLATE utf8_bin DEFAULT NULL,
  `PRG_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `TPC_TXT` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`NTE_ID`),
  UNIQUE KEY `KRNS_NTE_TC0` (`OBJ_ID`),
  KEY `KRNS_NTE_TR1` (`NTE_TYP_CD`),
  CONSTRAINT `KRNS_NTE_TR1` FOREIGN KEY (`NTE_TYP_CD`) REFERENCES `krns_nte_typ_t` (`NTE_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_nte_t`
--

LOCK TABLES `krns_nte_t` WRITE;
/*!40000 ALTER TABLE `krns_nte_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_nte_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_nte_typ_t`
--

DROP TABLE IF EXISTS `krns_nte_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_nte_typ_t` (
  `NTE_TYP_CD` varchar(4) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `TYP_DESC_TXT` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`NTE_TYP_CD`),
  UNIQUE KEY `KRNS_NTE_TYP_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_nte_typ_t`
--

LOCK TABLES `krns_nte_typ_t` WRITE;
/*!40000 ALTER TABLE `krns_nte_typ_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_nte_typ_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_pessimistic_lock_t`
--

DROP TABLE IF EXISTS `krns_pessimistic_lock_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_pessimistic_lock_t` (
  `PESSIMISTIC_LOCK_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `LOCK_DESC_TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL,
  `GNRT_DT` datetime NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`PESSIMISTIC_LOCK_ID`),
  UNIQUE KEY `KRNS_PESSIMISTIC_LOCK_TC0` (`OBJ_ID`),
  KEY `KRNS_PESSIMISTIC_LOCK_TI1` (`DOC_HDR_ID`),
  KEY `KRNS_PESSIMISTIC_LOCK_TI2` (`PRNCPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_pessimistic_lock_t`
--

LOCK TABLES `krns_pessimistic_lock_t` WRITE;
/*!40000 ALTER TABLE `krns_pessimistic_lock_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_pessimistic_lock_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krns_sesn_doc_t`
--

DROP TABLE IF EXISTS `krns_sesn_doc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_sesn_doc_t` (
  `SESN_DOC_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `IP_ADDR` varchar(60) COLLATE utf8_bin NOT NULL DEFAULT '',
  `SERIALZD_DOC_FRM` longblob,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  `CONTENT_ENCRYPTED_IND` char(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`SESN_DOC_ID`,`DOC_HDR_ID`,`PRNCPL_ID`,`IP_ADDR`),
  KEY `KRNS_SESN_DOC_TI1` (`LAST_UPDT_DT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krns_sesn_doc_t`
--

LOCK TABLES `krns_sesn_doc_t` WRITE;
/*!40000 ALTER TABLE `krns_sesn_doc_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krns_sesn_doc_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_bam_parm_s`
--

DROP TABLE IF EXISTS `krsb_bam_parm_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_bam_parm_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_bam_parm_s`
--

LOCK TABLES `krsb_bam_parm_s` WRITE;
/*!40000 ALTER TABLE `krsb_bam_parm_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_bam_parm_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_bam_parm_t`
--

DROP TABLE IF EXISTS `krsb_bam_parm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_bam_parm_t` (
  `BAM_PARM_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `BAM_ID` decimal(14,0) NOT NULL,
  `PARM` longtext COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`BAM_PARM_ID`),
  KEY `KREW_BAM_PARM_TI1` (`BAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_bam_parm_t`
--

LOCK TABLES `krsb_bam_parm_t` WRITE;
/*!40000 ALTER TABLE `krsb_bam_parm_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_bam_parm_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_bam_s`
--

DROP TABLE IF EXISTS `krsb_bam_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_bam_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_bam_s`
--

LOCK TABLES `krsb_bam_s` WRITE;
/*!40000 ALTER TABLE `krsb_bam_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_bam_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_bam_t`
--

DROP TABLE IF EXISTS `krsb_bam_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_bam_t` (
  `BAM_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `SVC_NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `SVC_URL` varchar(500) COLLATE utf8_bin NOT NULL,
  `MTHD_NM` varchar(2000) COLLATE utf8_bin NOT NULL,
  `THRD_NM` varchar(500) COLLATE utf8_bin NOT NULL,
  `CALL_DT` datetime NOT NULL,
  `TGT_TO_STR` varchar(2000) COLLATE utf8_bin NOT NULL,
  `SRVR_IND` decimal(1,0) NOT NULL,
  `EXCPN_TO_STR` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `EXCPN_MSG` longtext COLLATE utf8_bin,
  PRIMARY KEY (`BAM_ID`),
  KEY `KRSB_BAM_TI1` (`SVC_NM`,`MTHD_NM`(255)),
  KEY `KRSB_BAM_TI2` (`SVC_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_bam_t`
--

LOCK TABLES `krsb_bam_t` WRITE;
/*!40000 ALTER TABLE `krsb_bam_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_bam_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_msg_pyld_t`
--

DROP TABLE IF EXISTS `krsb_msg_pyld_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_msg_pyld_t` (
  `MSG_QUE_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `MSG_PYLD` longtext COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`MSG_QUE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_msg_pyld_t`
--

LOCK TABLES `krsb_msg_pyld_t` WRITE;
/*!40000 ALTER TABLE `krsb_msg_pyld_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_msg_pyld_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_msg_que_s`
--

DROP TABLE IF EXISTS `krsb_msg_que_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_msg_que_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3204 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_msg_que_s`
--

LOCK TABLES `krsb_msg_que_s` WRITE;
/*!40000 ALTER TABLE `krsb_msg_que_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_msg_que_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_msg_que_t`
--

DROP TABLE IF EXISTS `krsb_msg_que_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_msg_que_t` (
  `MSG_QUE_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DT` datetime NOT NULL,
  `EXP_DT` datetime DEFAULT NULL,
  `PRIO` decimal(8,0) NOT NULL,
  `STAT_CD` char(1) COLLATE utf8_bin NOT NULL,
  `RTRY_CNT` decimal(8,0) NOT NULL,
  `IP_NBR` varchar(2000) COLLATE utf8_bin NOT NULL,
  `SVC_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SVC_NMSPC` varchar(255) COLLATE utf8_bin NOT NULL,
  `SVC_MTHD_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `APP_VAL_ONE` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `APP_VAL_TWO` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`MSG_QUE_ID`),
  KEY `KRSB_MSG_QUE_TI1` (`SVC_NM`,`SVC_MTHD_NM`(255)),
  KEY `KRSB_MSG_QUE_TI2` (`SVC_NMSPC`,`STAT_CD`,`IP_NBR`(255),`DT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_msg_que_t`
--

LOCK TABLES `krsb_msg_que_t` WRITE;
/*!40000 ALTER TABLE `krsb_msg_que_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_msg_que_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_svc_def_s`
--

DROP TABLE IF EXISTS `krsb_svc_def_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_svc_def_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4058 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_svc_def_s`
--

LOCK TABLES `krsb_svc_def_s` WRITE;
/*!40000 ALTER TABLE `krsb_svc_def_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_svc_def_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `krsb_svc_def_t`
--

DROP TABLE IF EXISTS `krsb_svc_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_svc_def_t` (
  `SVC_DEF_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `SVC_NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `SVC_URL` varchar(500) COLLATE utf8_bin NOT NULL,
  `SRVR_IP` varchar(40) COLLATE utf8_bin NOT NULL,
  `SVC_NMSPC` varchar(255) COLLATE utf8_bin NOT NULL,
  `SVC_ALIVE` decimal(1,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `FLT_SVC_DEF_ID` decimal(14,0) NOT NULL,
  `SVC_DEF_CHKSM` varchar(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SVC_DEF_ID`),
  UNIQUE KEY `KRSB_SVC_DEF_TI2` (`FLT_SVC_DEF_ID`),
  KEY `KRSB_SVC_DEF_TI1` (`SRVR_IP`,`SVC_NMSPC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `krsb_svc_def_t`
--

LOCK TABLES `krsb_svc_def_t` WRITE;
/*!40000 ALTER TABLE `krsb_svc_def_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `krsb_svc_def_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_accrual_categories_s`
--

DROP TABLE IF EXISTS `lm_accrual_categories_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_categories_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2093 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accrual_categories_s`
--

LOCK TABLES `lm_accrual_categories_s` WRITE;
/*!40000 ALTER TABLE `lm_accrual_categories_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accrual_categories_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_accrual_category_rules_s`
--

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
-- Table structure for table `lm_accrual_category_rules_t`
--

DROP TABLE IF EXISTS `lm_accrual_category_rules_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_rules_t` (
  `lm_accrual_category_rules_id` varchar(60) COLLATE utf8_bin NOT NULL,
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
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_accrual_category_rules_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accrual_category_rules_t`
--

LOCK TABLES `lm_accrual_category_rules_t` WRITE;
/*!40000 ALTER TABLE `lm_accrual_category_rules_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accrual_category_rules_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_accrual_category_s`
--

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
-- Table structure for table `lm_accrual_category_t`
--

DROP TABLE IF EXISTS `lm_accrual_category_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accrual_category_t` (
  `lm_accrual_category_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CATEGORY` varchar(15) COLLATE utf8_bin NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ACCRUAL_INTERVAL_EARN` varchar(5) COLLATE utf8_bin NOT NULL,
  `UNIT_OF_TIME` varchar(5) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `PRORATION` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `DONATION` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `SHOW_ON_GRID` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `PLANNING_MONTHS` bigint(2) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_accrual_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accrual_category_t`
--

LOCK TABLES `lm_accrual_category_t` WRITE;
/*!40000 ALTER TABLE `lm_accrual_category_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accrual_category_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_accruals_s`
--

DROP TABLE IF EXISTS `lm_accruals_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accruals_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accruals_s`
--

LOCK TABLES `lm_accruals_s` WRITE;
/*!40000 ALTER TABLE `lm_accruals_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accruals_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_accruals_t`
--

DROP TABLE IF EXISTS `lm_accruals_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_accruals_t` (
  `lm_accruals_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `PRINCIPAL_ID` varchar(21) COLLATE utf8_bin NOT NULL,
  `ACCRUAL_CATEGORY` varchar(3) COLLATE utf8_bin NOT NULL,
  `EFFDT` datetime NOT NULL,
  `HOURS_ACCRUED` decimal(6,2) NOT NULL,
  `HOURS_TAKEN` decimal(6,2) NOT NULL,
  `HOURS_ADJUST` decimal(6,2) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`lm_accruals_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_accruals_t`
--

LOCK TABLES `lm_accruals_t` WRITE;
/*!40000 ALTER TABLE `lm_accruals_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_accruals_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_leave_code_s`
--

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

--
-- Table structure for table `lm_leave_code_t`
--

DROP TABLE IF EXISTS `lm_leave_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_code_t` (
  `lm_leave_code_id` varchar(60) COLLATE utf8_bin NOT NULL,
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
  `EMPLOYEE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `APPROVER` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `DEPT_ADMIN` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_leave_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_code_t`
--

LOCK TABLES `lm_leave_code_t` WRITE;
/*!40000 ALTER TABLE `lm_leave_code_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_code_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_leave_document_header_t`
--

DROP TABLE IF EXISTS `lm_leave_document_header_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_document_header_t` (
  `document_id` varchar(14) NOT NULL,
  `principal_id` varchar(10) NOT NULL,
  `begin_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `document_status` varchar(1) NOT NULL,
  `obj_id` varchar(36) NOT NULL,
  `ver_nbr` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_document_header_t`
--

LOCK TABLES `lm_leave_document_header_t` WRITE;
/*!40000 ALTER TABLE `lm_leave_document_header_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_document_header_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_leave_donation_s`
--

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

--
-- Table structure for table `lm_leave_donation_t`
--

DROP TABLE IF EXISTS `lm_leave_donation_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_donation_t` (
  `lm_leave_donation_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `DONATED_ACC_CAT` varchar(15) COLLATE utf8_bin NOT NULL,
  `RECIPIENTS_ACC_CAT` varchar(15) COLLATE utf8_bin NOT NULL,
  `AMOUNT_DONATED` decimal(18,2) NOT NULL,
  `AMOUNT_RECEIVED` decimal(18,2) NOT NULL,
  `DONOR` varchar(40) COLLATE utf8_bin NOT NULL,
  `RECEPIENT` varchar(40) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(50) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_leave_donation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_donation_t`
--

LOCK TABLES `lm_leave_donation_t` WRITE;
/*!40000 ALTER TABLE `lm_leave_donation_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_donation_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_leave_plan_s`
--

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
-- Table structure for table `lm_leave_plan_t`
--

DROP TABLE IF EXISTS `lm_leave_plan_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_leave_plan_t` (
  `lm_leave_plan_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `LEAVE_PLAN` varchar(15) COLLATE utf8_bin NOT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `CAL_YEAR_START` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_leave_plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_plan_t`
--

LOCK TABLES `lm_leave_plan_t` WRITE;
/*!40000 ALTER TABLE `lm_leave_plan_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_plan_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_ledger_block_s`
--

DROP TABLE IF EXISTS `lm_ledger_block_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_ledger_block_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_ledger_block_s`
--

LOCK TABLES `lm_ledger_block_s` WRITE;
/*!40000 ALTER TABLE `lm_ledger_block_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_ledger_block_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_ledger_s`
--

DROP TABLE IF EXISTS `lm_ledger_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_ledger_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_ledger_s`
--

LOCK TABLES `lm_ledger_s` WRITE;
/*!40000 ALTER TABLE `lm_ledger_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_ledger_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_ledger_t`
--

DROP TABLE IF EXISTS `lm_ledger_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_ledger_t` (
  `lm_ledger_id` varchar(60) NOT NULL,
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
  `ver_nbr` bigint(20) DEFAULT '1',
  `obj_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`lm_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_ledger_t`
--

LOCK TABLES `lm_ledger_t` WRITE;
/*!40000 ALTER TABLE `lm_ledger_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_ledger_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lm_sys_schd_timeoff_s`
--

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

--
-- Table structure for table `lm_sys_schd_timeoff_t`
--

DROP TABLE IF EXISTS `lm_sys_schd_timeoff_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lm_sys_schd_timeoff_t` (
  `lm_sys_schd_timeoff_id` varchar(60) COLLATE utf8_bin NOT NULL,
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
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TIMESTAMP` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lm_sys_schd_timeoff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_sys_schd_timeoff_t`
--

LOCK TABLES `lm_sys_schd_timeoff_t` WRITE;
/*!40000 ALTER TABLE `lm_sys_schd_timeoff_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_sys_schd_timeoff_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `BLOB_DATA` longblob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_BLOB_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `CALENDAR_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CALENDAR` longblob NOT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CRON_EXPRESSION` varchar(80) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_CRON_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL,
  `IS_VOLATILE` varchar(1) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(80) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` decimal(13,0) NOT NULL,
  `PRIORITY` decimal(13,0) NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `IS_STATEFUL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ENTRY_ID`),
  KEY `qrtz_FIRED_TRIGGERS_TI1` (`JOB_GROUP`),
  KEY `qrtz_FIRED_TRIGGERS_TI2` (`JOB_NAME`),
  KEY `qrtz_FIRED_TRIGGERS_TI3` (`REQUESTS_RECOVERY`),
  KEY `qrtz_FIRED_TRIGGERS_TI4` (`IS_STATEFUL`),
  KEY `qrtz_FIRED_TRIGGERS_TI5` (`TRIGGER_GROUP`),
  KEY `qrtz_FIRED_TRIGGERS_TI6` (`INSTANCE_NAME`),
  KEY `qrtz_FIRED_TRIGGERS_TI7` (`TRIGGER_NAME`),
  KEY `qrtz_FIRED_TRIGGERS_TI8` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `qrtz_FIRED_TRIGGERS_TI9` (`IS_VOLATILE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `JOB_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `DESCRIPTION` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(128) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_VOLATILE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_STATEFUL` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`),
  KEY `qrtz_JOB_DETAILS_TI1` (`REQUESTS_RECOVERY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_listeners`
--

DROP TABLE IF EXISTS `qrtz_job_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_listeners` (
  `JOB_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_LISTENER` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`,`JOB_LISTENER`),
  CONSTRAINT `KRSB_QUARTZ_JOB_LISTENERS_TR1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_listeners`
--

LOCK TABLES `qrtz_job_listeners` WRITE;
/*!40000 ALTER TABLE `qrtz_job_listeners` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_listeners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('CALENDAR_ACCESS'),('JOB_ACCESS'),('MISFIRE_ACCESS'),('STATE_ACCESS'),('TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `INSTANCE_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `LAST_CHECKIN_TIME` decimal(13,0) NOT NULL,
  `CHECKIN_INTERVAL` decimal(13,0) NOT NULL,
  PRIMARY KEY (`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REPEAT_COUNT` decimal(7,0) NOT NULL,
  `REPEAT_INTERVAL` decimal(12,0) NOT NULL,
  `TIMES_TRIGGERED` decimal(7,0) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_SIMPLE_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_trigger_listeners`
--

DROP TABLE IF EXISTS `qrtz_trigger_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_trigger_listeners` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_LISTENER` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_LISTENER`),
  CONSTRAINT `qrtz_TRIGGER_LISTENE_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_trigger_listeners`
--

LOCK TABLES `qrtz_trigger_listeners` WRITE;
/*!40000 ALTER TABLE `qrtz_trigger_listeners` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_trigger_listeners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_NAME` varchar(80) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(80) COLLATE utf8_bin NOT NULL,
  `IS_VOLATILE` varchar(1) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` decimal(13,0) DEFAULT NULL,
  `PREV_FIRE_TIME` decimal(13,0) DEFAULT NULL,
  `PRIORITY` decimal(13,0) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` decimal(13,0) NOT NULL,
  `END_TIME` decimal(13,0) DEFAULT NULL,
  `CALENDAR_NAME` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` decimal(2,0) DEFAULT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `qrtz_TRIGGERS_TI1` (`NEXT_FIRE_TIME`),
  KEY `qrtz_TRIGGERS_TI2` (`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `qrtz_TRIGGERS_TI3` (`TRIGGER_STATE`),
  KEY `qrtz_TRIGGERS_TI4` (`IS_VOLATILE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('PeriodicMessageProcessingTrigger','KCB-Delivery','MessageProcessingJobDetail','KCB-Delivery','0','\n                Trigger that periodically runs the KCB message processing job\n            ','1221578432323','1221578402323','5','WAITING','BLOB','1221577052323','0',NULL,'0',NULL);
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_assign_acct_s`
--

DROP TABLE IF EXISTS `tk_assign_acct_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assign_acct_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2165 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_assign_acct_s`
--

LOCK TABLES `tk_assign_acct_s` WRITE;
/*!40000 ALTER TABLE `tk_assign_acct_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_assign_acct_s` ENABLE KEYS */;
UNLOCK TABLES;

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
  `tk_assignment_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `active` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`TK_ASSIGN_ACCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_assign_acct_t`
--

LOCK TABLES `tk_assign_acct_t` WRITE;
/*!40000 ALTER TABLE `tk_assign_acct_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_assign_acct_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_assignment_s`
--

DROP TABLE IF EXISTS `tk_assignment_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2189 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_assignment_s`
--

LOCK TABLES `tk_assignment_s` WRITE;
/*!40000 ALTER TABLE `tk_assignment_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_assignment_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_assignment_t`
--

DROP TABLE IF EXISTS `tk_assignment_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_t` (
  `tk_assignment_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `EFFDT` date NOT NULL DEFAULT '0000-00-00',
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(10) DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `active` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_assignment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_assignment_t`
--

LOCK TABLES `tk_assignment_t` WRITE;
/*!40000 ALTER TABLE `tk_assignment_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_assignment_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_batch_job_entry_s`
--

DROP TABLE IF EXISTS `tk_batch_job_entry_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_batch_job_entry_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_batch_job_entry_s`
--

LOCK TABLES `tk_batch_job_entry_s` WRITE;
/*!40000 ALTER TABLE `tk_batch_job_entry_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_batch_job_entry_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_batch_job_entry_t`
--

DROP TABLE IF EXISTS `tk_batch_job_entry_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_batch_job_entry_t` (
  `TK_BATCH_JOB_ENTRY_ID` bigint(20) NOT NULL,
  `TK_BATCH_JOB_ID` bigint(20) NOT NULL,
  `document_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `BATCH_JOB_ENTRY_STATUS` varchar(1) COLLATE utf8_bin NOT NULL,
  `HR_PY_CALENDAR_ENTRY_ID` varchar(20) COLLATE utf8_bin NOT NULL,
  `batch_job_exception` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `IP_ADDRESS` varchar(20) COLLATE utf8_bin NOT NULL,
  `BATCH_JOB_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  `clock_log_id` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TK_BATCH_JOB_ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_batch_job_entry_t`
--

LOCK TABLES `tk_batch_job_entry_t` WRITE;
/*!40000 ALTER TABLE `tk_batch_job_entry_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_batch_job_entry_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_batch_job_s`
--

DROP TABLE IF EXISTS `tk_batch_job_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_batch_job_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2096 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_batch_job_s`
--

LOCK TABLES `tk_batch_job_s` WRITE;
/*!40000 ALTER TABLE `tk_batch_job_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_batch_job_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_batch_job_t`
--

DROP TABLE IF EXISTS `tk_batch_job_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_batch_job_t` (
  `TK_BATCH_JOB_ID` bigint(20) NOT NULL,
  `BATCH_JOB_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  `BATCH_JOB_STATUS` varchar(1) COLLATE utf8_bin NOT NULL,
  `HR_PY_CALENDAR_ENTRY_ID` bigint(20) NOT NULL,
  `TIME_ELAPSED` bigint(10) NOT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`TK_BATCH_JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_batch_job_t`
--

LOCK TABLES `tk_batch_job_t` WRITE;
/*!40000 ALTER TABLE `tk_batch_job_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_batch_job_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_loc_rl_ip_addr_s`
--

DROP TABLE IF EXISTS `tk_clock_loc_rl_ip_addr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_loc_rl_ip_addr_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_loc_rl_ip_addr_s`
--

LOCK TABLES `tk_clock_loc_rl_ip_addr_s` WRITE;
/*!40000 ALTER TABLE `tk_clock_loc_rl_ip_addr_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_loc_rl_ip_addr_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_loc_rl_ip_addr_t`
--

DROP TABLE IF EXISTS `tk_clock_loc_rl_ip_addr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_loc_rl_ip_addr_t` (
  `TK_CLOCK_LOC_RULE_IP_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `TK_CLOCK_LOC_RULE_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `ip_address` varchar(15) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`TK_CLOCK_LOC_RULE_IP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_loc_rl_ip_addr_t`
--

LOCK TABLES `tk_clock_loc_rl_ip_addr_t` WRITE;
/*!40000 ALTER TABLE `tk_clock_loc_rl_ip_addr_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_loc_rl_ip_addr_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_location_rl_s`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2262 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_location_rl_s`
--

LOCK TABLES `tk_clock_location_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_clock_location_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_location_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_location_rl_t`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_t` (
  `TK_CLOCK_LOC_RULE_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `job_number` bigint(20) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `active` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_location_rl_t`
--

LOCK TABLES `tk_clock_location_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_clock_location_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_location_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_log_s`
--

DROP TABLE IF EXISTS `tk_clock_log_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_log_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2896 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_log_s`
--

LOCK TABLES `tk_clock_log_s` WRITE;
/*!40000 ALTER TABLE `tk_clock_log_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_log_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_clock_log_t`
--

DROP TABLE IF EXISTS `tk_clock_log_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_log_t` (
  `tk_clock_log_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(10) DEFAULT NULL,
  `TK_WORK_AREA_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `TK_TASK_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `CLOCK_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CLOCK_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CLOCK_ACTION` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `IP_ADDRESS` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `hr_job_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`tk_clock_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_clock_log_t`
--

LOCK TABLES `tk_clock_log_t` WRITE;
/*!40000 ALTER TABLE `tk_clock_log_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_clock_log_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_daily_overtime_rl_s`
--

DROP TABLE IF EXISTS `tk_daily_overtime_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_daily_overtime_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2110 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_daily_overtime_rl_s`
--

LOCK TABLES `tk_daily_overtime_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_daily_overtime_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_daily_overtime_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_daily_overtime_rl_t`
--

DROP TABLE IF EXISTS `tk_daily_overtime_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_daily_overtime_rl_t` (
  `tk_daily_overtime_rl_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `LOCATION` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PAYTYPE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `MAX_GAP` decimal(8,2) DEFAULT NULL,
  `MIN_HOURS` decimal(2,0) DEFAULT NULL,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `FROM_EARN_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `EARN_CODE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tk_daily_overtime_rl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_daily_overtime_rl_t`
--

LOCK TABLES `tk_daily_overtime_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_daily_overtime_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_daily_overtime_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_dept_lunch_rl_s`
--

DROP TABLE IF EXISTS `tk_dept_lunch_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_lunch_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2110 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_dept_lunch_rl_s`
--

LOCK TABLES `tk_dept_lunch_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_dept_lunch_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_dept_lunch_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_dept_lunch_rl_t`
--

DROP TABLE IF EXISTS `tk_dept_lunch_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_dept_lunch_rl_t` (
  `TK_DEPT_LUNCH_RL_ID` varchar(60) COLLATE utf8_bin NOT NULL,
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
  `active` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `SHIFT_HOURS` decimal(2,1) DEFAULT NULL,
  `DEDUCTION_MINS` decimal(3,0) DEFAULT NULL,
  PRIMARY KEY (`TK_DEPT_LUNCH_RL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_dept_lunch_rl_t`
--

LOCK TABLES `tk_dept_lunch_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_dept_lunch_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_dept_lunch_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_document_header_t`
--

DROP TABLE IF EXISTS `tk_document_header_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_document_header_t` (
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin NOT NULL,
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PAY_END_DT` datetime DEFAULT NULL,
  `DOCUMENT_STATUS` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `PAY_BEGIN_DT` datetime DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  PRIMARY KEY (`DOCUMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_document_header_t`
--

LOCK TABLES `tk_document_header_t` WRITE;
/*!40000 ALTER TABLE `tk_document_header_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_document_header_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_grace_period_rl_s`
--

DROP TABLE IF EXISTS `tk_grace_period_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_grace_period_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_grace_period_rl_s`
--

LOCK TABLES `tk_grace_period_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_grace_period_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_grace_period_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_grace_period_rl_t`
--

DROP TABLE IF EXISTS `tk_grace_period_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_grace_period_rl_t` (
  `TK_GRACE_PERIOD_RULE_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `EFFDT` date DEFAULT NULL,
  `GRACE_MINS` decimal(2,0) DEFAULT NULL,
  `HOUR_FACTOR` decimal(6,0) DEFAULT NULL,
  `user_principal_id` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_grace_period_rl_t`
--

LOCK TABLES `tk_grace_period_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_grace_period_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_grace_period_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_holiday_calendar_dates_s`
--

DROP TABLE IF EXISTS `tk_holiday_calendar_dates_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_holiday_calendar_dates_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2099 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_holiday_calendar_dates_s`
--

LOCK TABLES `tk_holiday_calendar_dates_s` WRITE;
/*!40000 ALTER TABLE `tk_holiday_calendar_dates_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_holiday_calendar_dates_s` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tk_hour_detail_hist_s`
--

LOCK TABLES `tk_hour_detail_hist_s` WRITE;
/*!40000 ALTER TABLE `tk_hour_detail_hist_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_hour_detail_hist_s` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tk_hour_detail_hist_t`
--

LOCK TABLES `tk_hour_detail_hist_t` WRITE;
/*!40000 ALTER TABLE `tk_hour_detail_hist_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_hour_detail_hist_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_hour_detail_s`
--

DROP TABLE IF EXISTS `tk_hour_detail_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=5290 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_hour_detail_s`
--

LOCK TABLES `tk_hour_detail_s` WRITE;
/*!40000 ALTER TABLE `tk_hour_detail_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_hour_detail_s` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tk_hour_detail_t`
--

LOCK TABLES `tk_hour_detail_t` WRITE;
/*!40000 ALTER TABLE `tk_hour_detail_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_hour_detail_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_missed_punch_doc_t`
--

DROP TABLE IF EXISTS `tk_missed_punch_doc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_missed_punch_doc_t` (
  `doc_hdr_id` varchar(14) NOT NULL,
  `OBJ_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `principal_id` varchar(40) NOT NULL,
  `clock_action` varchar(20) NOT NULL,
  `action_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action_time` time NOT NULL DEFAULT '00:00:00',
  `timesheet_doc_id` varchar(14) NOT NULL,
  `document_status` varchar(1) DEFAULT NULL,
  `tk_clock_log_id` varchar(60) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `assignment` varchar(20) NOT NULL,
  PRIMARY KEY (`doc_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_missed_punch_doc_t`
--

LOCK TABLES `tk_missed_punch_doc_t` WRITE;
/*!40000 ALTER TABLE `tk_missed_punch_doc_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_missed_punch_doc_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_missed_punch_s`
--

DROP TABLE IF EXISTS `tk_missed_punch_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_missed_punch_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_missed_punch_s`
--

LOCK TABLES `tk_missed_punch_s` WRITE;
/*!40000 ALTER TABLE `tk_missed_punch_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_missed_punch_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_missed_punch_t`
--

DROP TABLE IF EXISTS `tk_missed_punch_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_missed_punch_t` (
  `tk_missed_punch_id` bigint(20) NOT NULL,
  `principal_id` varchar(40) NOT NULL,
  `clock_action` varchar(20) NOT NULL,
  `action_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action_time` time NOT NULL DEFAULT '00:00:00',
  `timesheet_doc_id` varchar(14) NOT NULL,
  `document_id` varchar(14) DEFAULT NULL,
  `document_status` varchar(1) DEFAULT NULL,
  `tk_clock_log_id` bigint(20) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_missed_punch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_missed_punch_t`
--

LOCK TABLES `tk_missed_punch_t` WRITE;
/*!40000 ALTER TABLE `tk_missed_punch_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_missed_punch_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_shift_differential_rl_s`
--

DROP TABLE IF EXISTS `tk_shift_differential_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_shift_differential_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2124 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_shift_differential_rl_s`
--

LOCK TABLES `tk_shift_differential_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_shift_differential_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_shift_differential_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_shift_differential_rl_t`
--

DROP TABLE IF EXISTS `tk_shift_differential_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_shift_differential_rl_t` (
  `TK_SHIFT_DIFF_RL_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `LOCATION` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `HR_SAL_GROUP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `pay_grade` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` time DEFAULT NULL COMMENT '	',
  `END_TS` time DEFAULT NULL,
  `MIN_HRS` decimal(2,0) DEFAULT NULL,
  `PY_CALENDAR_GROUP` varchar(30) COLLATE utf8_bin NOT NULL,
  `MAX_GAP` decimal(5,2) DEFAULT '0.00',
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `sun` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `mon` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `tue` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `wed` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `thu` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `fri` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `sat` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `from_earn_group` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TK_SHIFT_DIFF_RL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_shift_differential_rl_t`
--

LOCK TABLES `tk_shift_differential_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_shift_differential_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_shift_differential_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_system_lunch_rl_s`
--

DROP TABLE IF EXISTS `tk_system_lunch_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_system_lunch_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2091 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_system_lunch_rl_s`
--

LOCK TABLES `tk_system_lunch_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_system_lunch_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_system_lunch_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_system_lunch_rl_t`
--

DROP TABLE IF EXISTS `tk_system_lunch_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_system_lunch_rl_t` (
  `tk_system_lunch_rl_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `SHOW_LUNCH_BUTTON` varchar(1) COLLATE utf8_bin DEFAULT '0',
  PRIMARY KEY (`tk_system_lunch_rl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_system_lunch_rl_t`
--

LOCK TABLES `tk_system_lunch_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_system_lunch_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_system_lunch_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_task_s`
--

DROP TABLE IF EXISTS `tk_task_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_task_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1017 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_task_s`
--

LOCK TABLES `tk_task_s` WRITE;
/*!40000 ALTER TABLE `tk_task_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_task_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_task_t`
--

DROP TABLE IF EXISTS `tk_task_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_task_t` (
  `tk_task_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `task` bigint(10) DEFAULT NULL,
  `work_area` bigint(10) DEFAULT NULL,
  `tk_work_area_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `admin_descr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `obj_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT '1',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date NOT NULL,
  `active` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_task_t`
--

LOCK TABLES `tk_task_t` WRITE;
/*!40000 ALTER TABLE `tk_task_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_task_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_hist_detail_s`
--

DROP TABLE IF EXISTS `tk_time_block_hist_detail_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_detail_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=5290 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_hist_detail_s`
--

LOCK TABLES `tk_time_block_hist_detail_s` WRITE;
/*!40000 ALTER TABLE `tk_time_block_hist_detail_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_hist_detail_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_hist_detail_t`
--

DROP TABLE IF EXISTS `tk_time_block_hist_detail_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_detail_t` (
  `tk_time_block_hist_detail_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `tk_time_block_hist_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`tk_time_block_hist_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_hist_detail_t`
--

LOCK TABLES `tk_time_block_hist_detail_t` WRITE;
/*!40000 ALTER TABLE `tk_time_block_hist_detail_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_hist_detail_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_hist_s`
--

DROP TABLE IF EXISTS `tk_time_block_hist_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3127 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_hist_s`
--

LOCK TABLES `tk_time_block_hist_s` WRITE;
/*!40000 ALTER TABLE `tk_time_block_hist_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_hist_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_hist_t`
--

DROP TABLE IF EXISTS `tk_time_block_hist_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_hist_t` (
  `tk_time_block_hist_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `tk_time_block_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
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
  `CLOCK_LOG_CREATED` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTION_HISTORY` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `MODIFIED_BY_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_MODIFIED` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `amount` decimal(6,2) DEFAULT '0.00',
  `hr_job_id` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`tk_time_block_hist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_hist_t`
--

LOCK TABLES `tk_time_block_hist_t` WRITE;
/*!40000 ALTER TABLE `tk_time_block_hist_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_hist_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_s`
--

DROP TABLE IF EXISTS `tk_time_block_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6395 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_s`
--

LOCK TABLES `tk_time_block_s` WRITE;
/*!40000 ALTER TABLE `tk_time_block_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_block_t`
--

DROP TABLE IF EXISTS `tk_time_block_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_t` (
  `tk_time_block_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `DOCUMENT_ID` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NUMBER` bigint(20) DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `TASK` bigint(20) DEFAULT NULL,
  `tk_work_area_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `hr_job_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `tk_task_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `BEGIN_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BEGIN_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `clock_log_begin_id` bigint(20) DEFAULT NULL,
  `END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `END_TS_TZ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `clock_log_end_id` bigint(20) DEFAULT NULL,
  `CLOCK_LOG_CREATED` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ovt_pref` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tk_time_block_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_block_t`
--

LOCK TABLES `tk_time_block_t` WRITE;
/*!40000 ALTER TABLE `tk_time_block_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_block_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_collection_rl_s`
--

DROP TABLE IF EXISTS `tk_time_collection_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_collection_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1035 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_collection_rl_s`
--

LOCK TABLES `tk_time_collection_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_time_collection_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_collection_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_collection_rl_t`
--

DROP TABLE IF EXISTS `tk_time_collection_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_collection_rl_t` (
  `TK_TIME_COLL_RULE_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `WORK_AREA` bigint(10) DEFAULT NULL,
  `EFFDT` date DEFAULT NULL,
  `CLOCK_USERS_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `HRS_DISTRIBUTION_FL` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `pay_type` varchar(5) COLLATE utf8_bin DEFAULT '%',
  PRIMARY KEY (`TK_TIME_COLL_RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_collection_rl_t`
--

LOCK TABLES `tk_time_collection_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_time_collection_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_collection_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_sheet_init_s`
--

DROP TABLE IF EXISTS `tk_time_sheet_init_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_sheet_init_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1112 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_sheet_init_s`
--

LOCK TABLES `tk_time_sheet_init_s` WRITE;
/*!40000 ALTER TABLE `tk_time_sheet_init_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_sheet_init_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_time_sheet_init_t`
--

DROP TABLE IF EXISTS `tk_time_sheet_init_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_sheet_init_t` (
  `tk_time_sheet_init_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `calendar_group` varchar(30) COLLATE utf8_bin NOT NULL,
  `document_id` bigint(19) NOT NULL,
  `principal_id` varchar(40) COLLATE utf8_bin NOT NULL,
  `py_calendar_entries_id` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`tk_time_sheet_init_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_time_sheet_init_t`
--

LOCK TABLES `tk_time_sheet_init_t` WRITE;
/*!40000 ALTER TABLE `tk_time_sheet_init_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_time_sheet_init_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_user_pref_t`
--

DROP TABLE IF EXISTS `tk_user_pref_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_user_pref_t` (
  `PRINCIPAL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`PRINCIPAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_user_pref_t`
--

LOCK TABLES `tk_user_pref_t` WRITE;
/*!40000 ALTER TABLE `tk_user_pref_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_user_pref_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_weekly_overtime_rl_s`
--

DROP TABLE IF EXISTS `tk_weekly_overtime_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_weekly_overtime_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1025 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_weekly_overtime_rl_s`
--

LOCK TABLES `tk_weekly_overtime_rl_s` WRITE;
/*!40000 ALTER TABLE `tk_weekly_overtime_rl_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_weekly_overtime_rl_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_weekly_overtime_rl_t`
--

DROP TABLE IF EXISTS `tk_weekly_overtime_rl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_weekly_overtime_rl_t` (
  `tk_weekly_overtime_rl_id` varchar(60) COLLATE utf8_bin NOT NULL,
  `max_hrs_ern_group` varchar(10) COLLATE utf8_bin NOT NULL,
  `convert_from_ern_group` varchar(10) COLLATE utf8_bin NOT NULL,
  `convert_to_erncd` varchar(9) COLLATE utf8_bin NOT NULL,
  `EFFDT` date DEFAULT NULL,
  `STEP` decimal(2,0) DEFAULT NULL,
  `MAX_HRS` decimal(3,0) DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `TK_WEEKLY_OVT_GROUP_ID` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`tk_weekly_overtime_rl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_weekly_overtime_rl_t`
--

LOCK TABLES `tk_weekly_overtime_rl_t` WRITE;
/*!40000 ALTER TABLE `tk_weekly_overtime_rl_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_weekly_overtime_rl_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_weekly_ovt_group_t`
--

DROP TABLE IF EXISTS `tk_weekly_ovt_group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_weekly_ovt_group_t` (
  `TK_WEEKLY_OVERTIME_GROUP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`TK_WEEKLY_OVERTIME_GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_weekly_ovt_group_t`
--

LOCK TABLES `tk_weekly_ovt_group_t` WRITE;
/*!40000 ALTER TABLE `tk_weekly_ovt_group_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_weekly_ovt_group_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_work_area_key_s`
--

DROP TABLE IF EXISTS `tk_work_area_key_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_key_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1039 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_work_area_key_s`
--

LOCK TABLES `tk_work_area_key_s` WRITE;
/*!40000 ALTER TABLE `tk_work_area_key_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_work_area_key_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_work_area_s`
--

DROP TABLE IF EXISTS `tk_work_area_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1071 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_work_area_s`
--

LOCK TABLES `tk_work_area_s` WRITE;
/*!40000 ALTER TABLE `tk_work_area_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_work_area_s` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tk_work_area_t`
--

DROP TABLE IF EXISTS `tk_work_area_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_work_area_t` (
  `TK_WORK_AREA_ID` varchar(60) COLLATE utf8_bin NOT NULL,
  `work_area` bigint(10) NOT NULL,
  `DEPT` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `EFFDT` date NOT NULL,
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DEFAULT_OVERTIME_EARNCODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_DESCR` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `USER_PRINCIPAL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) DEFAULT '1',
  `OVERTIME_EDIT_ROLE` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`TK_WORK_AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tk_work_area_t`
--

LOCK TABLES `tk_work_area_t` WRITE;
/*!40000 ALTER TABLE `tk_work_area_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `tk_work_area_t` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-01-10 12:56:37
