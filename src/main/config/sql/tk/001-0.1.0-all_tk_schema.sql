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
-- Table structure for table `hr_job_s`
--

DROP TABLE IF EXISTS `hr_job_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_job_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1024 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `krns_att_t`
--

DROP TABLE IF EXISTS `krns_att_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_att_t` (
  `NTE_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `MIME_TYP` varchar(150) COLLATE utf8_bin DEFAULT NULL,
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
-- Table structure for table `krns_maint_doc_att_t`
--

DROP TABLE IF EXISTS `krns_maint_doc_att_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_doc_att_t` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ATT_CNTNT` longblob NOT NULL,
  `FILE_NM` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `CNTNT_TYP` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`DOC_HDR_ID`),
  UNIQUE KEY `KRNS_MAINT_DOC_ATT_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `krns_maint_lock_s`
--

DROP TABLE IF EXISTS `krns_maint_lock_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_maint_lock_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `krns_nte_s`
--

DROP TABLE IF EXISTS `krns_nte_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_nte_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2020 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `krsb_msg_que_s`
--

DROP TABLE IF EXISTS `krsb_msg_que_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_msg_que_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2068 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `timestamp_test`
--

DROP TABLE IF EXISTS `timestamp_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timestamp_test` (
  `ts1` timestamp NULL DEFAULT NULL,
  `ts2` timestamp NULL DEFAULT NULL,
  `ts3` timestamp NULL DEFAULT NULL,
  `zonename` varchar(10) COLLATE utf8_bin DEFAULT NULL
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
  `FIN_OBJECT_CD` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `FIN_SUB_OBJ_CD` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `ORG_REF_ID` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `PERCENT` decimal(5,2) DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  `ASSIGNMENT_ID` bigint(19) NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ASSIGN_ACCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_assignment_acct_s`
--

DROP TABLE IF EXISTS `tk_assignment_acct_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_assignment_acct_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
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
-- Table structure for table `tk_clock_location_rl_s`
--

DROP TABLE IF EXISTS `tk_clock_location_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_clock_location_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2130 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
) ENGINE=MyISAM AUTO_INCREMENT=2406 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `tk_hour_detail_s`
--

DROP TABLE IF EXISTS `tk_hour_detail_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1032 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tk_hour_detail_t`
--

DROP TABLE IF EXISTS `tk_hour_detail_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_hour_detail_t` (
  `tk_hour_detail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tk_time_block_id` bigint(20) NOT NULL,
  `document_id` varchar(14) COLLATE utf8_bin DEFAULT NULL,
  `job_number` bigint(20) DEFAULT NULL,
  `work_area_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `erncd` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `begin_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `begin_ts_tz` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `end_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_ts_tz` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `clock_log_created` bit(1) DEFAULT b'0',
  `hours` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `obj_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `ver_nbr` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`tk_hour_detail_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `tk_shift_diff_rl_s`
--

DROP TABLE IF EXISTS `tk_shift_diff_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_shift_diff_rl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `tk_sys_lunch_rl_s`
--

DROP TABLE IF EXISTS `tk_sys_lunch_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_sys_lunch_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
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
-- Table structure for table `tk_time_block_s`
--

DROP TABLE IF EXISTS `tk_time_block_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_block_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1109 DEFAULT CHARSET=latin1;
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
-- Table structure for table `tk_time_collection_rl_s`
--

DROP TABLE IF EXISTS `tk_time_collection_rl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tk_time_collection_rl_s` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
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

-- Dump completed on 2010-08-20 13:14:21
