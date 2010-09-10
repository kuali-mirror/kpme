-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: andover    Database: krtt
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
-- Table structure for table `acct_dd_attr_doc`
--

DROP TABLE IF EXISTS `acct_dd_attr_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acct_dd_attr_doc` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(14,0) DEFAULT NULL,
  `ACCT_NUM` decimal(14,0) NOT NULL,
  `ACCT_OWNR` varchar(50) COLLATE utf8_bin NOT NULL,
  `ACCT_BAL` decimal(16,2) NOT NULL,
  `ACCT_OPN_DAT` datetime NOT NULL,
  `ACCT_STAT` varchar(30) COLLATE utf8_bin NOT NULL,
  `ACCT_UPDATE_DT_TM` datetime DEFAULT NULL,
  `ACCT_AWAKE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kr_country_t`
--

DROP TABLE IF EXISTS `kr_country_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_country_t` (
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `POSTAL_CNTRY_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PSTL_CNTRY_RSTRC_IND` varchar(1) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`POSTAL_CNTRY_CD`),
  UNIQUE KEY `KR_COUNTRY_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kr_county_t`
--

DROP TABLE IF EXISTS `kr_county_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_county_t` (
  `COUNTY_CD` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `STATE_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'US',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `COUNTY_NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`COUNTY_CD`,`STATE_CD`,`POSTAL_CNTRY_CD`),
  UNIQUE KEY `KR_COUNTY_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kr_kim_test_bo`
--

DROP TABLE IF EXISTS `kr_kim_test_bo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_kim_test_bo` (
  `PK` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kr_postal_code_t`
--

DROP TABLE IF EXISTS `kr_postal_code_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_postal_code_t` (
  `POSTAL_CD` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'US',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `POSTAL_STATE_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `COUNTY_CD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CITY_NM` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`POSTAL_CD`,`POSTAL_CNTRY_CD`),
  UNIQUE KEY `KR_POSTAL_CODE_TC0` (`OBJ_ID`),
  KEY `KR_POSTAL_CODE_TR3` (`COUNTY_CD`,`POSTAL_STATE_CD`,`POSTAL_CNTRY_CD`),
  KEY `KR_POSTAL_CODE_TR2` (`POSTAL_STATE_CD`,`POSTAL_CNTRY_CD`),
  KEY `KR_POSTAL_CODE_TR1` (`POSTAL_CNTRY_CD`),
  CONSTRAINT `KR_POSTAL_CODE_TR1` FOREIGN KEY (`POSTAL_CNTRY_CD`) REFERENCES `kr_country_t` (`POSTAL_CNTRY_CD`),
  CONSTRAINT `KR_POSTAL_CODE_TR2` FOREIGN KEY (`POSTAL_STATE_CD`, `POSTAL_CNTRY_CD`) REFERENCES `kr_state_t` (`POSTAL_STATE_CD`, `POSTAL_CNTRY_CD`),
  CONSTRAINT `KR_POSTAL_CODE_TR3` FOREIGN KEY (`COUNTY_CD`, `POSTAL_STATE_CD`, `POSTAL_CNTRY_CD`) REFERENCES `kr_county_t` (`COUNTY_CD`, `STATE_CD`, `POSTAL_CNTRY_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kr_state_t`
--

DROP TABLE IF EXISTS `kr_state_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kr_state_t` (
  `POSTAL_STATE_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'US',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `POSTAL_STATE_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`POSTAL_STATE_CD`,`POSTAL_CNTRY_CD`),
  UNIQUE KEY `KR_STATE_TC0` (`OBJ_ID`),
  KEY `KR_STATE_TR1` (`POSTAL_CNTRY_CD`),
  CONSTRAINT `KR_STATE_TR1` FOREIGN KEY (`POSTAL_CNTRY_CD`) REFERENCES `kr_country_t` (`POSTAL_CNTRY_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_chnl_prodcr_t`
--

DROP TABLE IF EXISTS `kren_chnl_prodcr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_chnl_prodcr_t` (
  `CHNL_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `PRODCR_ID` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CHNL_ID`,`PRODCR_ID`),
  KEY `KREN_CHNL_PRODCR_TI1` (`CHNL_ID`),
  KEY `KREN_CHNL_PRODCR_TI2` (`PRODCR_ID`),
  CONSTRAINT `KREN_CHNL_PRODCR_FK1` FOREIGN KEY (`CHNL_ID`) REFERENCES `kren_chnl_t` (`CHNL_ID`),
  CONSTRAINT `KREN_CHNL_PRODCR_FK2` FOREIGN KEY (`PRODCR_ID`) REFERENCES `kren_prodcr_t` (`PRODCR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_chnl_s`
--

DROP TABLE IF EXISTS `kren_chnl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_chnl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_chnl_subscrp_s`
--

DROP TABLE IF EXISTS `kren_chnl_subscrp_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_chnl_subscrp_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1020 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_chnl_subscrp_t`
--

DROP TABLE IF EXISTS `kren_chnl_subscrp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_chnl_subscrp_t` (
  `CHNL_SUBSCRP_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `CHNL_ID` decimal(8,0) NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CHNL_SUBSCRP_ID`),
  UNIQUE KEY `KREN_CHNL_SUBSCRP_TC0` (`CHNL_ID`,`PRNCPL_ID`),
  KEY `KREN_CHNL_SUBSCRP_TI1` (`CHNL_ID`),
  CONSTRAINT `KREN_CHAN_SUBSCRP_FK1` FOREIGN KEY (`CHNL_ID`) REFERENCES `kren_chnl_t` (`CHNL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_chnl_t`
--

DROP TABLE IF EXISTS `kren_chnl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_chnl_t` (
  `CHNL_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(4000) COLLATE utf8_bin NOT NULL,
  `SUBSCRB_IND` char(1) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CHNL_ID`),
  UNIQUE KEY `KREN_CHNL_TC0` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_cntnt_typ_s`
--

DROP TABLE IF EXISTS `kren_cntnt_typ_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_cntnt_typ_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_cntnt_typ_t`
--

DROP TABLE IF EXISTS `kren_cntnt_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_cntnt_typ_t` (
  `CNTNT_TYP_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `CUR_IND` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'T',
  `CNTNT_TYP_VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  `DESC_TXT` varchar(1000) COLLATE utf8_bin NOT NULL,
  `NMSPC_CD` varchar(1000) COLLATE utf8_bin NOT NULL,
  `XSD` longtext COLLATE utf8_bin NOT NULL,
  `XSL` longtext COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CNTNT_TYP_ID`),
  UNIQUE KEY `KREN_CNTNT_TYP_TC0` (`NM`,`CNTNT_TYP_VER_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_msg_deliv_s`
--

DROP TABLE IF EXISTS `kren_msg_deliv_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_msg_deliv_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_msg_deliv_t`
--

DROP TABLE IF EXISTS `kren_msg_deliv_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_msg_deliv_t` (
  `MSG_DELIV_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `MSG_ID` decimal(8,0) NOT NULL,
  `TYP_NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `SYS_ID` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `STAT_CD` varchar(15) COLLATE utf8_bin NOT NULL,
  `PROC_CNT` decimal(4,0) NOT NULL DEFAULT '0',
  `LOCKD_DTTM` datetime DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MSG_DELIV_ID`),
  UNIQUE KEY `KREN_MSG_DELIV_TC0` (`MSG_ID`,`TYP_NM`),
  KEY `KREN_MSG_DELIV_TI1` (`MSG_ID`),
  CONSTRAINT `KREN_MSG_DELIV_FK1` FOREIGN KEY (`MSG_ID`) REFERENCES `kren_msg_t` (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_msg_s`
--

DROP TABLE IF EXISTS `kren_msg_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_msg_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_msg_t`
--

DROP TABLE IF EXISTS `kren_msg_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_msg_t` (
  `MSG_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `ORGN_ID` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `DELIV_TYP` varchar(500) COLLATE utf8_bin NOT NULL,
  `CRTE_DTTM` datetime NOT NULL,
  `TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CHNL` varchar(300) COLLATE utf8_bin NOT NULL,
  `PRODCR` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `CNTNT` longtext COLLATE utf8_bin NOT NULL,
  `CNTNT_TYP` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `URL` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `RECIP_ID` varchar(300) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MSG_ID`),
  UNIQUE KEY `KREN_MSG_TC0` (`ORGN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_ntfctn_msg_deliv_s`
--

DROP TABLE IF EXISTS `kren_ntfctn_msg_deliv_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_ntfctn_msg_deliv_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_ntfctn_msg_deliv_t`
--

DROP TABLE IF EXISTS `kren_ntfctn_msg_deliv_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_ntfctn_msg_deliv_t` (
  `NTFCTN_MSG_DELIV_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NTFCTN_ID` decimal(8,0) NOT NULL,
  `RECIP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `STAT_CD` varchar(15) COLLATE utf8_bin NOT NULL,
  `SYS_ID` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `LOCKD_DTTM` datetime DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`NTFCTN_MSG_DELIV_ID`),
  UNIQUE KEY `KREN_NTFCTN_MSG_DELIV_TC0` (`NTFCTN_ID`,`RECIP_ID`),
  KEY `KREN_MSG_DELIVSI1` (`NTFCTN_ID`),
  CONSTRAINT `KREN_NTFCTN_MSG_DELIV_FK1` FOREIGN KEY (`NTFCTN_ID`) REFERENCES `kren_ntfctn_t` (`NTFCTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_ntfctn_s`
--

DROP TABLE IF EXISTS `kren_ntfctn_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_ntfctn_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_ntfctn_t`
--

DROP TABLE IF EXISTS `kren_ntfctn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_ntfctn_t` (
  `NTFCTN_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `DELIV_TYP` varchar(3) COLLATE utf8_bin NOT NULL,
  `CRTE_DTTM` datetime NOT NULL,
  `SND_DTTM` datetime DEFAULT NULL,
  `AUTO_RMV_DTTM` datetime DEFAULT NULL,
  `PRIO_ID` decimal(8,0) NOT NULL,
  `TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CNTNT` longtext COLLATE utf8_bin NOT NULL,
  `CNTNT_TYP_ID` decimal(8,0) NOT NULL,
  `CHNL_ID` decimal(8,0) NOT NULL,
  `PRODCR_ID` decimal(8,0) NOT NULL,
  `PROCESSING_FLAG` varchar(15) COLLATE utf8_bin NOT NULL,
  `LOCKD_DTTM` datetime DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`NTFCTN_ID`),
  KEY `KREN_NTFCTN_I1` (`CNTNT_TYP_ID`),
  KEY `KREN_NTFCTN_I2` (`PRIO_ID`),
  KEY `KREN_NTFCTN_I3` (`PRODCR_ID`),
  KEY `KREN_NTFCTN_FK1` (`CHNL_ID`),
  CONSTRAINT `KREN_NTFCTN_FK1` FOREIGN KEY (`CHNL_ID`) REFERENCES `kren_chnl_t` (`CHNL_ID`),
  CONSTRAINT `KREN_NTFCTN_FK2` FOREIGN KEY (`CNTNT_TYP_ID`) REFERENCES `kren_cntnt_typ_t` (`CNTNT_TYP_ID`),
  CONSTRAINT `KREN_NTFCTN_FK3` FOREIGN KEY (`PRIO_ID`) REFERENCES `kren_prio_t` (`PRIO_ID`),
  CONSTRAINT `KREN_NTFCTN_FK4` FOREIGN KEY (`PRODCR_ID`) REFERENCES `kren_prodcr_t` (`PRODCR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_prio_s`
--

DROP TABLE IF EXISTS `kren_prio_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_prio_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_prio_t`
--

DROP TABLE IF EXISTS `kren_prio_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_prio_t` (
  `PRIO_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NM` varchar(40) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(500) COLLATE utf8_bin NOT NULL,
  `PRIO_ORD` decimal(4,0) NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`PRIO_ID`),
  UNIQUE KEY `KREN_PRIO_TC0` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_prodcr_s`
--

DROP TABLE IF EXISTS `kren_prodcr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_prodcr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_prodcr_t`
--

DROP TABLE IF EXISTS `kren_prodcr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_prodcr_t` (
  `PRODCR_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(1000) COLLATE utf8_bin NOT NULL,
  `CNTCT_INFO` varchar(1000) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`PRODCR_ID`),
  UNIQUE KEY `KREN_PRODCR_TC0` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_deliv_s`
--

DROP TABLE IF EXISTS `kren_recip_deliv_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_deliv_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_deliv_t`
--

DROP TABLE IF EXISTS `kren_recip_deliv_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_deliv_t` (
  `RECIP_DELIV_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `RECIP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `CHNL` varchar(300) COLLATE utf8_bin NOT NULL,
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`RECIP_DELIV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_list_s`
--

DROP TABLE IF EXISTS `kren_recip_list_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_list_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_list_t`
--

DROP TABLE IF EXISTS `kren_recip_list_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_list_t` (
  `RECIP_LIST_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `CHNL_ID` decimal(8,0) NOT NULL,
  `RECIP_TYP_CD` varchar(10) COLLATE utf8_bin NOT NULL,
  `RECIP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RECIP_LIST_ID`),
  UNIQUE KEY `KREN_RECIP_LIST_TC0` (`CHNL_ID`,`RECIP_TYP_CD`,`RECIP_ID`),
  KEY `KREN_RECIP_LIST_TI1` (`CHNL_ID`),
  CONSTRAINT `KREN_RECIP_LIST_FK1` FOREIGN KEY (`CHNL_ID`) REFERENCES `kren_chnl_t` (`CHNL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_pref_s`
--

DROP TABLE IF EXISTS `kren_recip_pref_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_pref_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_prefs_t`
--

DROP TABLE IF EXISTS `kren_recip_prefs_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_prefs_t` (
  `RECIP_PREFS_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `RECIP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `PROP` varchar(200) COLLATE utf8_bin NOT NULL,
  `VAL` varchar(1000) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`RECIP_PREFS_ID`),
  UNIQUE KEY `KREN_RECIP_PREFS_TC0` (`RECIP_ID`,`PROP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_s`
--

DROP TABLE IF EXISTS `kren_recip_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_recip_t`
--

DROP TABLE IF EXISTS `kren_recip_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_recip_t` (
  `RECIP_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NTFCTN_ID` decimal(8,0) NOT NULL,
  `RECIP_TYP_CD` varchar(10) COLLATE utf8_bin NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RECIP_ID`),
  UNIQUE KEY `KREN_RECIP_TC0` (`NTFCTN_ID`,`RECIP_TYP_CD`,`PRNCPL_ID`),
  KEY `KREN_RECIP_TI1` (`NTFCTN_ID`),
  CONSTRAINT `KREN_RECIP_FK1` FOREIGN KEY (`NTFCTN_ID`) REFERENCES `kren_ntfctn_t` (`NTFCTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_rvwer_s`
--

DROP TABLE IF EXISTS `kren_rvwer_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_rvwer_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_rvwer_t`
--

DROP TABLE IF EXISTS `kren_rvwer_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_rvwer_t` (
  `RVWER_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `CHNL_ID` decimal(8,0) NOT NULL,
  `TYP` varchar(10) COLLATE utf8_bin NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`RVWER_ID`),
  UNIQUE KEY `KREN_RVWER_TC0` (`CHNL_ID`,`TYP`,`PRNCPL_ID`),
  KEY `KREN_RVWER_TI1` (`CHNL_ID`),
  CONSTRAINT `NOTIFICATION_REVIEWERS_N_FK1` FOREIGN KEY (`CHNL_ID`) REFERENCES `kren_chnl_t` (`CHNL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_sndr_s`
--

DROP TABLE IF EXISTS `kren_sndr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_sndr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kren_sndr_t`
--

DROP TABLE IF EXISTS `kren_sndr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kren_sndr_t` (
  `SNDR_ID` decimal(8,0) NOT NULL DEFAULT '0',
  `NTFCTN_ID` decimal(8,0) NOT NULL,
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SNDR_ID`),
  UNIQUE KEY `KREN_SNDR_TC0` (`NTFCTN_ID`,`NM`),
  KEY `KREN_SNDR_TI1` (`NTFCTN_ID`),
  CONSTRAINT `KREN_SNDR_FK1` FOREIGN KEY (`NTFCTN_ID`) REFERENCES `kren_ntfctn_t` (`NTFCTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_itm_s`
--

DROP TABLE IF EXISTS `krew_actn_itm_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_itm_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10247 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_itm_t`
--

DROP TABLE IF EXISTS `krew_actn_itm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_itm_t` (
  `ACTN_ITM_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ASND_DT` datetime NOT NULL,
  `RQST_CD` char(1) COLLATE utf8_bin NOT NULL,
  `ACTN_RQST_ID` decimal(14,0) NOT NULL,
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `ROLE_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DOC_HDR_TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DOC_TYP_LBL` varchar(128) COLLATE utf8_bin NOT NULL,
  `DOC_HDLR_URL` varchar(255) COLLATE utf8_bin NOT NULL,
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin NOT NULL,
  `RSP_ID` decimal(14,0) NOT NULL,
  `DLGN_TYP` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `DTYPE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `RQST_LBL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ACTN_ITM_ID`),
  KEY `KREW_ACTN_ITM_T1` (`PRNCPL_ID`),
  KEY `KREW_ACTN_ITM_TI2` (`DOC_HDR_ID`),
  KEY `KREW_ACTN_ITM_TI3` (`ACTN_RQST_ID`),
  KEY `KREW_ACTN_ITM_TI5` (`PRNCPL_ID`,`DLGN_TYP`,`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_list_optn_s`
--

DROP TABLE IF EXISTS `krew_actn_list_optn_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_list_optn_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1290 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_rqst_s`
--

DROP TABLE IF EXISTS `krew_actn_rqst_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_rqst_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2426 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_rqst_t`
--

DROP TABLE IF EXISTS `krew_actn_rqst_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_rqst_t` (
  `ACTN_RQST_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `PARNT_ID` decimal(14,0) DEFAULT NULL,
  `ACTN_RQST_CD` char(1) COLLATE utf8_bin NOT NULL,
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `RULE_ID` decimal(19,0) DEFAULT NULL,
  `STAT_CD` char(1) COLLATE utf8_bin NOT NULL,
  `RSP_ID` decimal(14,0) NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `QUAL_ROLE_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `QUAL_ROLE_NM_LBL_TXT` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `RECIP_TYP_CD` char(1) COLLATE utf8_bin DEFAULT NULL,
  `PRIO_NBR` decimal(8,0) NOT NULL,
  `RTE_TYP_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RTE_LVL_NBR` decimal(8,0) NOT NULL,
  `RTE_NODE_INSTN_ID` decimal(19,0) DEFAULT NULL,
  `ACTN_TKN_ID` decimal(14,0) DEFAULT NULL,
  `DOC_VER_NBR` decimal(8,0) NOT NULL,
  `CRTE_DT` datetime NOT NULL,
  `RSP_DESC_TXT` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `FRC_ACTN` decimal(1,0) DEFAULT '0',
  `ACTN_RQST_ANNOTN_TXT` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_TYP` char(1) COLLATE utf8_bin DEFAULT NULL,
  `APPR_PLCY` char(1) COLLATE utf8_bin DEFAULT NULL,
  `CUR_IND` decimal(1,0) DEFAULT '1',
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `RQST_LBL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ACTN_RQST_ID`),
  KEY `KREW_ACTN_RQST_T11` (`DOC_HDR_ID`),
  KEY `KREW_ACTN_RQST_T12` (`PRNCPL_ID`),
  KEY `KREW_ACTN_RQST_T13` (`ACTN_TKN_ID`),
  KEY `KREW_ACTN_RQST_T14` (`PARNT_ID`),
  KEY `KREW_ACTN_RQST_T15` (`RSP_ID`),
  KEY `KREW_ACTN_RQST_T16` (`STAT_CD`,`RSP_ID`),
  KEY `KREW_ACTN_RQST_T17` (`RTE_NODE_INSTN_ID`),
  KEY `KREW_ACTN_RQST_T19` (`STAT_CD`,`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_tkn_s`
--

DROP TABLE IF EXISTS `krew_actn_tkn_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_tkn_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2495 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_actn_tkn_t`
--

DROP TABLE IF EXISTS `krew_actn_tkn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_actn_tkn_t` (
  `ACTN_TKN_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `DLGTR_PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTN_CD` char(1) COLLATE utf8_bin NOT NULL,
  `ACTN_DT` datetime NOT NULL,
  `DOC_VER_NBR` decimal(8,0) NOT NULL,
  `ANNOTN` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `CUR_IND` decimal(1,0) DEFAULT '1',
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `DLGTR_GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ACTN_TKN_ID`),
  KEY `KREW_ACTN_TKN_TI1` (`DOC_HDR_ID`,`PRNCPL_ID`),
  KEY `KREW_ACTN_TKN_TI2` (`DOC_HDR_ID`,`PRNCPL_ID`,`ACTN_CD`),
  KEY `KREW_ACTN_TKN_TI3` (`PRNCPL_ID`),
  KEY `KREW_ACTN_TKN_TI4` (`DLGTR_PRNCPL_ID`),
  KEY `KREW_ACTN_TKN_TI5` (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_att_t`
--

DROP TABLE IF EXISTS `krew_att_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_att_t` (
  `ATTACHMENT_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NTE_ID` decimal(19,0) NOT NULL,
  `FILE_NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `FILE_LOC` varchar(255) COLLATE utf8_bin NOT NULL,
  `MIME_TYP` varchar(255) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`ATTACHMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_dlgn_rsp_t`
--

DROP TABLE IF EXISTS `krew_dlgn_rsp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_dlgn_rsp_t` (
  `DLGN_RULE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RSP_ID` decimal(19,0) NOT NULL,
  `DLGN_RULE_BASE_VAL_ID` decimal(19,0) NOT NULL,
  `DLGN_TYP` varchar(20) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`DLGN_RULE_ID`),
  UNIQUE KEY `KREW_DLGN_RSP_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_cntnt_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_cntnt_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_cntnt_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DOC_CNTNT_TXT` longtext COLLATE utf8_bin,
  PRIMARY KEY (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_ext_dt_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_ext_dt_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_ext_dt_t` (
  `DOC_HDR_EXT_DT_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `KEY_CD` varchar(256) COLLATE utf8_bin NOT NULL,
  `VAL` datetime DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_EXT_DT_ID`),
  KEY `KREW_DOC_HDR_EXT_DT_TI1` (`KEY_CD`(255),`VAL`),
  KEY `KREW_DOC_HDR_EXT_DT_TI2` (`DOC_HDR_ID`),
  KEY `KREW_DOC_HDR_EXT_DT_TI3` (`VAL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_ext_flt_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_ext_flt_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_ext_flt_t` (
  `DOC_HDR_EXT_FLT_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `KEY_CD` varchar(256) COLLATE utf8_bin NOT NULL,
  `VAL` decimal(30,15) DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_EXT_FLT_ID`),
  KEY `KREW_DOC_HDR_EXT_FLT_TI1` (`KEY_CD`(255),`VAL`),
  KEY `KREW_DOC_HDR_EXT_FLT_TI2` (`DOC_HDR_ID`),
  KEY `KREW_DOC_HDR_EXT_FLT_TI3` (`VAL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_ext_long_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_ext_long_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_ext_long_t` (
  `DOC_HDR_EXT_LONG_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `KEY_CD` varchar(256) COLLATE utf8_bin NOT NULL,
  `VAL` decimal(22,0) DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_EXT_LONG_ID`),
  KEY `KREW_DOC_HDR_EXT_LONG_TI1` (`KEY_CD`(255),`VAL`),
  KEY `KREW_DOC_HDR_EXT_LONG_TI2` (`DOC_HDR_ID`),
  KEY `KREW_DOC_HDR_EXT_LONG_TI3` (`VAL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_ext_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_ext_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_ext_t` (
  `DOC_HDR_EXT_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `KEY_CD` varchar(256) COLLATE utf8_bin NOT NULL,
  `VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_EXT_ID`),
  KEY `KREW_DOC_HDR_EXT_TI1` (`KEY_CD`(255),`VAL`(255)),
  KEY `KREW_DOC_HDR_EXT_TI2` (`DOC_HDR_ID`),
  KEY `KREW_DOC_HDR_EXT_TI3` (`VAL`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_s`
--

DROP TABLE IF EXISTS `krew_doc_hdr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3927 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_hdr_t`
--

DROP TABLE IF EXISTS `krew_doc_hdr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_hdr_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DOC_TYP_ID` decimal(19,0) DEFAULT NULL,
  `DOC_HDR_STAT_CD` char(1) COLLATE utf8_bin NOT NULL,
  `RTE_LVL` decimal(8,0) NOT NULL,
  `STAT_MDFN_DT` datetime NOT NULL,
  `CRTE_DT` datetime NOT NULL,
  `APRV_DT` datetime DEFAULT NULL,
  `FNL_DT` datetime DEFAULT NULL,
  `RTE_STAT_MDFN_DT` datetime DEFAULT NULL,
  `RTE_LVL_MDFN_DT` datetime DEFAULT NULL,
  `TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `APP_DOC_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DOC_VER_NBR` decimal(8,0) NOT NULL,
  `INITR_PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `RTE_PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DTYPE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`DOC_HDR_ID`),
  UNIQUE KEY `KREW_DOC_HDR_TC0` (`OBJ_ID`),
  KEY `KREW_DOC_HDR_TI1` (`DOC_TYP_ID`),
  KEY `KREW_DOC_HDR_TI2` (`INITR_PRNCPL_ID`),
  KEY `KREW_DOC_HDR_TI3` (`DOC_HDR_STAT_CD`),
  KEY `KREW_DOC_HDR_TI4` (`TTL`),
  KEY `KREW_DOC_HDR_TI5` (`CRTE_DT`),
  KEY `KREW_DOC_HDR_TI6` (`RTE_STAT_MDFN_DT`),
  KEY `KREW_DOC_HDR_TI7` (`APRV_DT`),
  KEY `KREW_DOC_HDR_TI8` (`FNL_DT`),
  KEY `KREW_DOC_HDR_TI9` (`APP_DOC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_nte_s`
--

DROP TABLE IF EXISTS `krew_doc_nte_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_nte_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2020 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_nte_t`
--

DROP TABLE IF EXISTS `krew_doc_nte_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_nte_t` (
  `DOC_NTE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `AUTH_PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `CRT_DT` datetime NOT NULL,
  `TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`DOC_NTE_ID`),
  KEY `KREW_DOC_NTE_TI1` (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_typ_attr_s`
--

DROP TABLE IF EXISTS `krew_doc_typ_attr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_typ_attr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2009 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_typ_attr_t`
--

DROP TABLE IF EXISTS `krew_doc_typ_attr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_typ_attr_t` (
  `DOC_TYP_ATTRIB_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_TYP_ID` decimal(19,0) NOT NULL,
  `RULE_ATTR_ID` decimal(19,0) NOT NULL,
  `ORD_INDX` decimal(4,0) DEFAULT '0',
  PRIMARY KEY (`DOC_TYP_ATTRIB_ID`),
  KEY `KREW_DOC_TYP_ATTR_TI1` (`DOC_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_typ_plcy_reln_t`
--

DROP TABLE IF EXISTS `krew_doc_typ_plcy_reln_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_typ_plcy_reln_t` (
  `DOC_TYP_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_PLCY_NM` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PLCY_NM` decimal(1,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`DOC_TYP_ID`,`DOC_PLCY_NM`),
  UNIQUE KEY `KREW_DOC_TYP_PLCY_RELN_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_typ_proc_t`
--

DROP TABLE IF EXISTS `krew_doc_typ_proc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_typ_proc_t` (
  `DOC_TYP_PROC_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_TYP_ID` decimal(19,0) NOT NULL,
  `INIT_RTE_NODE_ID` decimal(22,0) DEFAULT NULL,
  `NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `INIT_IND` decimal(1,0) NOT NULL DEFAULT '0',
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`DOC_TYP_PROC_ID`),
  KEY `KREW_DOC_TYP_PROC_TI1` (`DOC_TYP_ID`),
  KEY `KREW_DOC_TYP_PROC_TI2` (`INIT_RTE_NODE_ID`),
  KEY `KREW_DOC_TYP_PROC_TI3` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_doc_typ_t`
--

DROP TABLE IF EXISTS `krew_doc_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_doc_typ_t` (
  `DOC_TYP_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `PARNT_ID` decimal(19,0) DEFAULT NULL,
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOC_TYP_VER_NBR` decimal(10,0) DEFAULT '0',
  `ACTV_IND` decimal(1,0) DEFAULT NULL,
  `CUR_IND` decimal(1,0) DEFAULT NULL,
  `LBL` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `PREV_DOC_TYP_VER_NBR` decimal(19,0) DEFAULT NULL,
  `DOC_HDR_ID` decimal(14,0) DEFAULT NULL,
  `DOC_TYP_DESC` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DOC_HDLR_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `POST_PRCSR` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `JNDI_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BLNKT_APPR_PLCY` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `ADV_DOC_SRCH_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CSTM_ACTN_LIST_ATTRIB_CLS_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CSTM_ACTN_EMAIL_ATTRIB_CLS_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CSTM_DOC_NTE_ATTRIB_CLS_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RTE_VER_NBR` varchar(2) COLLATE utf8_bin DEFAULT '1',
  `NOTIFY_ADDR` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SVC_NMSPC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_XSL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SEC_XML` longtext COLLATE utf8_bin,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `BLNKT_APPR_GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `RPT_GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `HELP_DEF_URL` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `DOC_SEARCH_HELP_URL` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DOC_TYP_ID`),
  UNIQUE KEY `KREW_DOC_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KREW_DOC_TYP_TI1` (`DOC_TYP_NM`,`DOC_TYP_VER_NBR`),
  KEY `KREW_DOC_TYP_TI2` (`PARNT_ID`),
  KEY `KREW_DOC_TYP_TI3` (`DOC_TYP_ID`,`PARNT_ID`),
  KEY `KREW_DOC_TYP_TI4` (`PREV_DOC_TYP_VER_NBR`),
  KEY `KREW_DOC_TYP_TI5` (`CUR_IND`),
  KEY `KREW_DOC_TYP_TI6` (`DOC_TYP_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_assctn_t`
--

DROP TABLE IF EXISTS `krew_edl_assctn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_assctn_t` (
  `EDOCLT_ASSOC_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin NOT NULL,
  `EDL_DEF_NM` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `STYLE_NM` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`EDOCLT_ASSOC_ID`),
  UNIQUE KEY `KREW_EDL_ASSCTN_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_def_t`
--

DROP TABLE IF EXISTS `krew_edl_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_def_t` (
  `EDOCLT_DEF_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `XML` longtext COLLATE utf8_bin NOT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`EDOCLT_DEF_ID`),
  UNIQUE KEY `KREW_EDL_DEF_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_dmp_t`
--

DROP TABLE IF EXISTS `krew_edl_dmp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_dmp_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin NOT NULL,
  `DOC_HDR_STAT_CD` char(1) COLLATE utf8_bin NOT NULL,
  `DOC_HDR_MDFN_DT` datetime NOT NULL,
  `DOC_HDR_CRTE_DT` datetime NOT NULL,
  `DOC_HDR_TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DOC_HDR_INITR_PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `CRNT_NODE_NM` varchar(30) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`DOC_HDR_ID`),
  KEY `KREW_EDL_DMP_TI1` (`DOC_TYP_NM`,`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_fld_dmp_s`
--

DROP TABLE IF EXISTS `krew_edl_fld_dmp_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_fld_dmp_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_fld_dmp_t`
--

DROP TABLE IF EXISTS `krew_edl_fld_dmp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_fld_dmp_t` (
  `EDL_FIELD_DMP_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `FLD_NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `FLD_VAL` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`EDL_FIELD_DMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_edl_s`
--

DROP TABLE IF EXISTS `krew_edl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_edl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2020 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_hlp_s`
--

DROP TABLE IF EXISTS `krew_hlp_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_hlp_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_hlp_t`
--

DROP TABLE IF EXISTS `krew_hlp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_hlp_t` (
  `HLP_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(500) COLLATE utf8_bin NOT NULL,
  `KEY_CD` varchar(500) COLLATE utf8_bin NOT NULL,
  `HLP_TXT` varchar(4000) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`HLP_ID`),
  KEY `KREW_HLP_TI1` (`KEY_CD`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_init_rte_node_instn_t`
--

DROP TABLE IF EXISTS `krew_init_rte_node_instn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_init_rte_node_instn_t` (
  `DOC_HDR_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RTE_NODE_INSTN_ID` decimal(19,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DOC_HDR_ID`,`RTE_NODE_INSTN_ID`),
  KEY `KREW_INIT_RTE_NODE_INSTN_TI1` (`DOC_HDR_ID`),
  KEY `KREW_INIT_RTE_NODE_INSTN_TI2` (`RTE_NODE_INSTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_out_box_itm_s`
--

DROP TABLE IF EXISTS `krew_out_box_itm_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_out_box_itm_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10043 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_out_box_itm_t`
--

DROP TABLE IF EXISTS `krew_out_box_itm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_out_box_itm_t` (
  `ACTN_ITM_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ASND_DT` datetime NOT NULL,
  `RQST_CD` char(1) COLLATE utf8_bin NOT NULL,
  `ACTN_RQST_ID` decimal(14,0) NOT NULL,
  `DOC_HDR_ID` decimal(14,0) NOT NULL,
  `ROLE_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DOC_HDR_TTL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DOC_TYP_LBL` varchar(128) COLLATE utf8_bin NOT NULL,
  `DOC_HDLR_URL` varchar(255) COLLATE utf8_bin NOT NULL,
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin NOT NULL,
  `RSP_ID` decimal(14,0) NOT NULL,
  `DLGN_TYP` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `RQST_LBL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ACTN_ITM_ID`),
  KEY `KREW_OUT_BOX_ITM_TI1` (`PRNCPL_ID`),
  KEY `KREW_OUT_BOX_ITM_TI2` (`DOC_HDR_ID`),
  KEY `KREW_OUT_BOX_ITM_TI3` (`ACTN_RQST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rmv_rplc_doc_t`
--

DROP TABLE IF EXISTS `krew_rmv_rplc_doc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rmv_rplc_doc_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `OPRN` char(1) COLLATE utf8_bin NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `RPLC_PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rmv_rplc_grp_t`
--

DROP TABLE IF EXISTS `krew_rmv_rplc_grp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rmv_rplc_grp_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `GRP_ID` decimal(14,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DOC_HDR_ID`,`GRP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rmv_rplc_rule_t`
--

DROP TABLE IF EXISTS `krew_rmv_rplc_rule_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rmv_rplc_rule_t` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `RULE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DOC_HDR_ID`,`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rsp_s`
--

DROP TABLE IF EXISTS `krew_rsp_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rsp_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2069 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_brch_proto_t`
--

DROP TABLE IF EXISTS `krew_rte_brch_proto_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_brch_proto_t` (
  `RTE_BRCH_PROTO_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `BRCH_NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RTE_BRCH_PROTO_ID`),
  KEY `KREW_RTE_BRCH_PROTO_TI1` (`BRCH_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_brch_st_t`
--

DROP TABLE IF EXISTS `krew_rte_brch_st_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_brch_st_t` (
  `RTE_BRCH_ST_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RTE_BRCH_ID` decimal(19,0) NOT NULL,
  `KEY_CD` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RTE_BRCH_ST_ID`),
  KEY `KREW_RTE_BRCH_ST_TI1` (`RTE_BRCH_ID`,`KEY_CD`),
  KEY `KREW_RTE_BRCH_ST_TI2` (`RTE_BRCH_ID`),
  KEY `KREW_RTE_BRCH_ST_TI3` (`KEY_CD`,`VAL`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_brch_t`
--

DROP TABLE IF EXISTS `krew_rte_brch_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_brch_t` (
  `RTE_BRCH_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `PARNT_ID` decimal(19,0) DEFAULT NULL,
  `INIT_RTE_NODE_INSTN_ID` decimal(19,0) DEFAULT NULL,
  `SPLT_RTE_NODE_INSTN_ID` decimal(19,0) DEFAULT NULL,
  `JOIN_RTE_NODE_INSTN_ID` decimal(19,0) DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RTE_BRCH_ID`),
  KEY `KREW_RTE_BRCH_TI1` (`NM`),
  KEY `KREW_RTE_BRCH_TI2` (`PARNT_ID`),
  KEY `KREW_RTE_BRCH_TI3` (`INIT_RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_BRCH_TI4` (`SPLT_RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_BRCH_TI5` (`JOIN_RTE_NODE_INSTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_cfg_parm_s`
--

DROP TABLE IF EXISTS `krew_rte_node_cfg_parm_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_cfg_parm_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2482 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_cfg_parm_t`
--

DROP TABLE IF EXISTS `krew_rte_node_cfg_parm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_cfg_parm_t` (
  `RTE_NODE_CFG_PARM_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RTE_NODE_ID` decimal(19,0) NOT NULL,
  `KEY_CD` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAL` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`RTE_NODE_CFG_PARM_ID`),
  KEY `KREW_RTE_NODE_CFG_PARM_TI1` (`RTE_NODE_ID`),
  CONSTRAINT `EN_RTE_NODE_CFG_PARM_TR1` FOREIGN KEY (`RTE_NODE_ID`) REFERENCES `krew_rte_node_t` (`RTE_NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_instn_lnk_t`
--

DROP TABLE IF EXISTS `krew_rte_node_instn_lnk_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_instn_lnk_t` (
  `FROM_RTE_NODE_INSTN_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `TO_RTE_NODE_INSTN_ID` decimal(19,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`FROM_RTE_NODE_INSTN_ID`,`TO_RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_NODE_INSTN_LNK_TI1` (`FROM_RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_NODE_INSTN_LNK_TI2` (`TO_RTE_NODE_INSTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_instn_st_t`
--

DROP TABLE IF EXISTS `krew_rte_node_instn_st_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_instn_st_t` (
  `RTE_NODE_INSTN_ST_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RTE_NODE_INSTN_ID` decimal(19,0) NOT NULL,
  `KEY_CD` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RTE_NODE_INSTN_ST_ID`),
  KEY `KREW_RTE_NODE_INSTN_ST_TI1` (`RTE_NODE_INSTN_ID`,`KEY_CD`),
  KEY `KREW_RTE_NODE_INSTN_ST_TI2` (`RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_NODE_INSTN_ST_TI3` (`KEY_CD`,`VAL`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_instn_t`
--

DROP TABLE IF EXISTS `krew_rte_node_instn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_instn_t` (
  `RTE_NODE_INSTN_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_HDR_ID` decimal(19,0) NOT NULL,
  `RTE_NODE_ID` decimal(19,0) NOT NULL,
  `BRCH_ID` decimal(19,0) DEFAULT NULL,
  `PROC_RTE_NODE_INSTN_ID` decimal(19,0) DEFAULT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL DEFAULT '0',
  `CMPLT_IND` decimal(1,0) NOT NULL DEFAULT '0',
  `INIT_IND` decimal(1,0) NOT NULL DEFAULT '0',
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RTE_NODE_INSTN_ID`),
  KEY `KREW_RTE_NODE_INSTN_TI1` (`DOC_HDR_ID`,`ACTV_IND`,`CMPLT_IND`),
  KEY `KREW_RTE_NODE_INSTN_TI2` (`RTE_NODE_ID`),
  KEY `KREW_RTE_NODE_INSTN_TI3` (`BRCH_ID`),
  KEY `KREW_RTE_NODE_INSTN_TI4` (`PROC_RTE_NODE_INSTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_lnk_t`
--

DROP TABLE IF EXISTS `krew_rte_node_lnk_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_lnk_t` (
  `FROM_RTE_NODE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `TO_RTE_NODE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`FROM_RTE_NODE_ID`,`TO_RTE_NODE_ID`),
  KEY `KREW_RTE_NODE_LNK_TI1` (`FROM_RTE_NODE_ID`),
  KEY `KREW_RTE_NODE_LNK_TI2` (`TO_RTE_NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_s`
--

DROP TABLE IF EXISTS `krew_rte_node_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4690 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_node_t`
--

DROP TABLE IF EXISTS `krew_rte_node_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_node_t` (
  `RTE_NODE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `DOC_TYP_ID` decimal(19,0) DEFAULT NULL,
  `NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `TYP` varchar(255) COLLATE utf8_bin NOT NULL,
  `RTE_MTHD_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RTE_MTHD_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `FNL_APRVR_IND` decimal(1,0) DEFAULT NULL,
  `MNDTRY_RTE_IND` decimal(1,0) DEFAULT NULL,
  `ACTVN_TYP` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `BRCH_PROTO_ID` decimal(19,0) DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `CONTENT_FRAGMENT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`RTE_NODE_ID`),
  KEY `KREW_RTE_NODE_TI1` (`NM`,`DOC_TYP_ID`),
  KEY `KREW_RTE_NODE_TI2` (`DOC_TYP_ID`,`FNL_APRVR_IND`),
  KEY `KREW_RTE_NODE_TI3` (`BRCH_PROTO_ID`),
  KEY `KREW_RTE_NODE_TI4` (`DOC_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rte_tmpl_s`
--

DROP TABLE IF EXISTS `krew_rte_tmpl_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rte_tmpl_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1656 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_attr_t`
--

DROP TABLE IF EXISTS `krew_rule_attr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_attr_t` (
  `RULE_ATTR_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(255) COLLATE utf8_bin NOT NULL,
  `LBL` varchar(2000) COLLATE utf8_bin NOT NULL,
  `RULE_ATTR_TYP_CD` varchar(2000) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `CLS_NM` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `XML` longtext COLLATE utf8_bin,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `SVC_NMSPC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RULE_ATTR_ID`),
  UNIQUE KEY `KREW_RULE_ATTR_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_expr_s`
--

DROP TABLE IF EXISTS `krew_rule_expr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_expr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_expr_t`
--

DROP TABLE IF EXISTS `krew_rule_expr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_expr_t` (
  `RULE_EXPR_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `TYP` varchar(256) COLLATE utf8_bin NOT NULL,
  `RULE_EXPR` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RULE_EXPR_ID`),
  UNIQUE KEY `KREW_RULE_EXPR_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_ext_t`
--

DROP TABLE IF EXISTS `krew_rule_ext_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_ext_t` (
  `RULE_EXT_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RULE_TMPL_ATTR_ID` decimal(19,0) NOT NULL,
  `RULE_ID` decimal(19,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RULE_EXT_ID`),
  KEY `KREW_RULE_EXT_T1` (`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_ext_val_t`
--

DROP TABLE IF EXISTS `krew_rule_ext_val_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_ext_val_t` (
  `RULE_EXT_VAL_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RULE_EXT_ID` decimal(19,0) NOT NULL,
  `VAL` varchar(2000) COLLATE utf8_bin NOT NULL,
  `KEY_CD` varchar(2000) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RULE_EXT_VAL_ID`),
  KEY `KREW_RULE_EXT_VAL_T1` (`RULE_EXT_ID`),
  KEY `KREW_RULE_EXT_VAL_T2` (`RULE_EXT_VAL_ID`,`KEY_CD`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_rsp_t`
--

DROP TABLE IF EXISTS `krew_rule_rsp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_rsp_t` (
  `RULE_RSP_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RSP_ID` decimal(19,0) NOT NULL,
  `RULE_ID` decimal(19,0) NOT NULL,
  `PRIO` decimal(5,0) DEFAULT NULL,
  `ACTN_RQST_CD` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `NM` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `TYP` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `APPR_PLCY` char(1) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RULE_RSP_ID`),
  UNIQUE KEY `KREW_RULE_RSP_TC0` (`OBJ_ID`),
  KEY `KREW_RULE_RSP_TI1` (`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_t`
--

DROP TABLE IF EXISTS `krew_rule_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_t` (
  `RULE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `RULE_TMPL_ID` decimal(19,0) DEFAULT NULL,
  `RULE_EXPR_ID` decimal(19,0) DEFAULT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL,
  `RULE_BASE_VAL_DESC` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `FRC_ACTN` decimal(1,0) NOT NULL,
  `DOC_TYP_NM` varchar(64) COLLATE utf8_bin NOT NULL,
  `DOC_HDR_ID` decimal(14,0) DEFAULT NULL,
  `TMPL_RULE_IND` decimal(1,0) DEFAULT NULL,
  `FRM_DT` datetime DEFAULT NULL,
  `TO_DT` datetime DEFAULT NULL,
  `DACTVN_DT` datetime DEFAULT NULL,
  `CUR_IND` decimal(1,0) DEFAULT '0',
  `RULE_VER_NBR` decimal(8,0) DEFAULT '0',
  `DLGN_IND` decimal(1,0) DEFAULT NULL,
  `PREV_RULE_VER_NBR` decimal(19,0) DEFAULT NULL,
  `ACTVN_DT` datetime DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RULE_ID`),
  UNIQUE KEY `KREW_RULE_TC0` (`OBJ_ID`),
  KEY `KREW_RULE_TR1` (`RULE_EXPR_ID`),
  CONSTRAINT `KREW_RULE_TR1` FOREIGN KEY (`RULE_EXPR_ID`) REFERENCES `krew_rule_expr_t` (`RULE_EXPR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_tmpl_attr_t`
--

DROP TABLE IF EXISTS `krew_rule_tmpl_attr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_tmpl_attr_t` (
  `RULE_TMPL_ATTR_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RULE_TMPL_ID` decimal(19,0) NOT NULL,
  `RULE_ATTR_ID` decimal(19,0) NOT NULL,
  `REQ_IND` decimal(1,0) NOT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL,
  `DSPL_ORD` decimal(5,0) NOT NULL,
  `DFLT_VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RULE_TMPL_ATTR_ID`),
  UNIQUE KEY `KREW_RULE_TMPL_ATTR_TC0` (`OBJ_ID`),
  KEY `KREW_RULE_TMPL_ATTR_TI1` (`RULE_TMPL_ID`),
  KEY `KREW_RULE_TMPL_ATTR_TI2` (`RULE_ATTR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_tmpl_optn_s`
--

DROP TABLE IF EXISTS `krew_rule_tmpl_optn_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_tmpl_optn_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2020 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_tmpl_optn_t`
--

DROP TABLE IF EXISTS `krew_rule_tmpl_optn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_tmpl_optn_t` (
  `RULE_TMPL_OPTN_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `RULE_TMPL_ID` decimal(19,0) DEFAULT NULL,
  `KEY_CD` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`RULE_TMPL_OPTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_rule_tmpl_t`
--

DROP TABLE IF EXISTS `krew_rule_tmpl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_rule_tmpl_t` (
  `RULE_TMPL_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(250) COLLATE utf8_bin NOT NULL,
  `RULE_TMPL_DESC` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_RULE_TMPL_ID` decimal(19,0) DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RULE_TMPL_ID`),
  UNIQUE KEY `KREW_RULE_TMPL_TC0` (`OBJ_ID`),
  UNIQUE KEY `KREW_RULE_TMPL_TI1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_srch_attr_s`
--

DROP TABLE IF EXISTS `krew_srch_attr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_srch_attr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2060 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_style_t`
--

DROP TABLE IF EXISTS `krew_style_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_style_t` (
  `STYLE_ID` decimal(19,0) NOT NULL DEFAULT '0',
  `NM` varchar(200) COLLATE utf8_bin NOT NULL,
  `XML` longtext COLLATE utf8_bin NOT NULL,
  `ACTV_IND` decimal(1,0) NOT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`STYLE_ID`),
  UNIQUE KEY `KREW_STYLE_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_usr_optn_t`
--

DROP TABLE IF EXISTS `krew_usr_optn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_usr_optn_t` (
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PRSN_OPTN_ID` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VAL` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) DEFAULT '0',
  PRIMARY KEY (`PRNCPL_ID`,`PRSN_OPTN_ID`),
  KEY `KREW_USR_OPTN_TI1` (`PRNCPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krew_usr_s`
--

DROP TABLE IF EXISTS `krew_usr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krew_usr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100000000000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_addr_typ_t`
--

DROP TABLE IF EXISTS `krim_addr_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_addr_typ_t` (
  `ADDR_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ADDR_TYP_CD`),
  UNIQUE KEY `KRIM_ADDR_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ADDR_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_afltn_typ_t`
--

DROP TABLE IF EXISTS `krim_afltn_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_afltn_typ_t` (
  `AFLTN_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_AFLTN_TYP_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`AFLTN_TYP_CD`),
  UNIQUE KEY `KRIM_AFLTN_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_AFLTN_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_attr_data_id_s`
--

DROP TABLE IF EXISTS `krim_attr_data_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_attr_data_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10028 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_attr_defn_id_s`
--

DROP TABLE IF EXISTS `krim_attr_defn_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_attr_defn_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_attr_defn_t`
--

DROP TABLE IF EXISTS `krim_attr_defn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_attr_defn_t` (
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LBL` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CMPNT_NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`KIM_ATTR_DEFN_ID`),
  UNIQUE KEY `KRIM_ATTR_DEFN_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_ctznshp_stat_t`
--

DROP TABLE IF EXISTS `krim_ctznshp_stat_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_ctznshp_stat_t` (
  `CTZNSHP_STAT_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`CTZNSHP_STAT_CD`),
  UNIQUE KEY `KRIM_CTZNSHP_STAT_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_CTZNSHP_STAT_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_dlgn_id_s`
--

DROP TABLE IF EXISTS `krim_dlgn_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_dlgn_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_dlgn_mbr_attr_data_t`
--

DROP TABLE IF EXISTS `krim_dlgn_mbr_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_dlgn_mbr_attr_data_t` (
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DLGN_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ATTR_DATA_ID`),
  UNIQUE KEY `KRIM_DLGN_MBR_ATTR_DATA_TC0` (`OBJ_ID`),
  KEY `KRIM_DLGN_MBR_ATTR_DATA_TR2` (`KIM_ATTR_DEFN_ID`),
  KEY `KRIM_DLGN_MBR_ATTR_DATA_TR1` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_DLGN_MBR_ATTR_DATA_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_DLGN_MBR_ATTR_DATA_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_dlgn_mbr_id_s`
--

DROP TABLE IF EXISTS `krim_dlgn_mbr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_dlgn_mbr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_dlgn_mbr_t`
--

DROP TABLE IF EXISTS `krim_dlgn_mbr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_dlgn_mbr_t` (
  `DLGN_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `DLGN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` char(1) COLLATE utf8_bin DEFAULT 'P',
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DLGN_MBR_ID`),
  UNIQUE KEY `KRIM_DLGN_MBR_TC0` (`OBJ_ID`),
  KEY `KRIM_DLGN_MBR_TR2` (`DLGN_ID`),
  CONSTRAINT `KRIM_DLGN_MBR_TR2` FOREIGN KEY (`DLGN_ID`) REFERENCES `krim_dlgn_t` (`DLGN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_dlgn_t`
--

DROP TABLE IF EXISTS `krim_dlgn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_dlgn_t` (
  `DLGN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `ROLE_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `DLGN_TYP_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DLGN_ID`),
  UNIQUE KEY `KRIM_DLGN_TC0` (`OBJ_ID`),
  KEY `KRIM_DLGN_TR1` (`ROLE_ID`),
  KEY `KRIM_DLGN_TR2` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_DLGN_TR1` FOREIGN KEY (`ROLE_ID`) REFERENCES `krim_role_t` (`ROLE_ID`),
  CONSTRAINT `KRIM_DLGN_TR2` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_email_typ_t`
--

DROP TABLE IF EXISTS `krim_email_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_email_typ_t` (
  `EMAIL_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`EMAIL_TYP_CD`),
  UNIQUE KEY `KRIM_EMAIL_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_EMAIL_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_emp_stat_t`
--

DROP TABLE IF EXISTS `krim_emp_stat_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_emp_stat_t` (
  `EMP_STAT_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`EMP_STAT_CD`),
  UNIQUE KEY `KRIM_EMP_STAT_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_EMP_STAT_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_emp_typ_t`
--

DROP TABLE IF EXISTS `krim_emp_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_emp_typ_t` (
  `EMP_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`EMP_TYP_CD`),
  UNIQUE KEY `KRIM_EMP_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_EMP_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_ent_nm_typ_t`
--

DROP TABLE IF EXISTS `krim_ent_nm_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_ent_nm_typ_t` (
  `ENT_NM_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENT_NM_TYP_CD`),
  UNIQUE KEY `KRIM_ENT_NM_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ENT_NM_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_ent_typ_t`
--

DROP TABLE IF EXISTS `krim_ent_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_ent_typ_t` (
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`ENT_TYP_CD`),
  UNIQUE KEY `KRIM_ENT_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ENT_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_addr_id_s`
--

DROP TABLE IF EXISTS `krim_entity_addr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_addr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_addr_t`
--

DROP TABLE IF EXISTS `krim_entity_addr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_addr_t` (
  `ENTITY_ADDR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_1` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_2` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_3` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `CITY_NM` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_STATE_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CD` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_ADDR_ID`),
  UNIQUE KEY `KRIM_ENTITY_ADDR_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_ADDR_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_ADDR_TR1` (`ENT_TYP_CD`,`ENTITY_ID`),
  KEY `KRIM_ENTITY_ADDR_TR2` (`ADDR_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_ADDR_TR1` FOREIGN KEY (`ENT_TYP_CD`, `ENTITY_ID`) REFERENCES `krim_entity_ent_typ_t` (`ENT_TYP_CD`, `ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_ADDR_TR2` FOREIGN KEY (`ADDR_TYP_CD`) REFERENCES `krim_addr_typ_t` (`ADDR_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_afltn_id_s`
--

DROP TABLE IF EXISTS `krim_entity_afltn_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_afltn_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_afltn_t`
--

DROP TABLE IF EXISTS `krim_entity_afltn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_afltn_t` (
  `ENTITY_AFLTN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `AFLTN_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CAMPUS_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_AFLTN_ID`),
  UNIQUE KEY `KRIM_ENTITY_AFLTN_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_AFLTN_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_AFLTN_TR2` (`AFLTN_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_AFLTN_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_AFLTN_TR2` FOREIGN KEY (`AFLTN_TYP_CD`) REFERENCES `krim_afltn_typ_t` (`AFLTN_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_bio_t`
--

DROP TABLE IF EXISTS `krim_entity_bio_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_bio_t` (
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `BIRTH_DT` datetime DEFAULT NULL,
  `GNDR_CD` varchar(1) COLLATE utf8_bin NOT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  `DECEASED_DT` datetime DEFAULT NULL,
  `MARITAL_STATUS` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRIM_LANG_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `SEC_LANG_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `BIRTH_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `BIRTH_STATE_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `BIRTH_CITY` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `GEO_ORIGIN` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ENTITY_ID`),
  UNIQUE KEY `KRIM_ENTITY_BIO_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_cache_t`
--

DROP TABLE IF EXISTS `krim_entity_cache_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_cache_t` (
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `PRNCPL_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENTITY_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIRST_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MIDDLE_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `LAST_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRSN_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CAMPUS_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRMRY_DEPT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_TS` datetime DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ENTITY_ID`),
  UNIQUE KEY `KRIM_ENTITY_CACHE_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ENTITY_CACHE_TC1` (`PRNCPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ctznshp_id_s`
--

DROP TABLE IF EXISTS `krim_entity_ctznshp_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ctznshp_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ctznshp_t`
--

DROP TABLE IF EXISTS `krim_entity_ctznshp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ctznshp_t` (
  `ENTITY_CTZNSHP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `CTZNSHP_STAT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `STRT_DT` datetime DEFAULT NULL,
  `END_DT` datetime DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_CTZNSHP_ID`),
  UNIQUE KEY `KRIM_ENTITY_CTZNSHP_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_CTZNSHP_TR1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_CTZNSHP_TR2` (`CTZNSHP_STAT_CD`),
  CONSTRAINT `KRIM_ENTITY_CTZNSHP_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_CTZNSHP_TR2` FOREIGN KEY (`CTZNSHP_STAT_CD`) REFERENCES `krim_ctznshp_stat_t` (`CTZNSHP_STAT_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_email_id_s`
--

DROP TABLE IF EXISTS `krim_entity_email_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_email_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_email_t`
--

DROP TABLE IF EXISTS `krim_entity_email_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_email_t` (
  `ENTITY_EMAIL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_ADDR` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_EMAIL_ID`),
  UNIQUE KEY `KRIM_ENTITY_EMAIL_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_EMAIL_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_EMAIL_TR1` (`ENT_TYP_CD`,`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_EMAIL_TR1` FOREIGN KEY (`ENT_TYP_CD`, `ENTITY_ID`) REFERENCES `krim_entity_ent_typ_t` (`ENT_TYP_CD`, `ENTITY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_emp_id_s`
--

DROP TABLE IF EXISTS `krim_entity_emp_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_emp_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_emp_info_t`
--

DROP TABLE IF EXISTS `krim_entity_emp_info_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_emp_info_t` (
  `ENTITY_EMP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENTITY_AFLTN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_STAT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `BASE_SLRY_AMT` decimal(15,2) DEFAULT NULL,
  `PRMRY_IND` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  `PRMRY_DEPT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_REC_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ENTITY_EMP_ID`),
  UNIQUE KEY `KRIM_ENTITY_EMP_INFO_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_EMP_INFO_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_EMP_INFO_TI2` (`ENTITY_AFLTN_ID`),
  KEY `KRIM_ENTITY_EMP_INFO_TR2` (`EMP_STAT_CD`),
  KEY `KRIM_ENTITY_EMP_INFO_TR3` (`EMP_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_EMP_INFO_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_EMP_INFO_TR2` FOREIGN KEY (`EMP_STAT_CD`) REFERENCES `krim_emp_stat_t` (`EMP_STAT_CD`),
  CONSTRAINT `KRIM_ENTITY_EMP_INFO_TR3` FOREIGN KEY (`EMP_TYP_CD`) REFERENCES `krim_emp_typ_t` (`EMP_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_EMP_INFO_TR4` FOREIGN KEY (`ENTITY_AFLTN_ID`) REFERENCES `krim_entity_afltn_t` (`ENTITY_AFLTN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ent_typ_t`
--

DROP TABLE IF EXISTS `krim_entity_ent_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ent_typ_t` (
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENT_TYP_CD`,`ENTITY_ID`),
  UNIQUE KEY `KRIM_ENTITY_ENT_TYP_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_ENT_TYP_TI1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_ENT_TYP_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_ENT_TYP_TR2` FOREIGN KEY (`ENT_TYP_CD`) REFERENCES `krim_ent_typ_t` (`ENT_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ethnic_id_s`
--

DROP TABLE IF EXISTS `krim_entity_ethnic_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ethnic_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ethnic_t`
--

DROP TABLE IF EXISTS `krim_entity_ethnic_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ethnic_t` (
  `ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ETHNCTY_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `SUB_ETHNCTY_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `KRIM_ENTITY_ETHNIC_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_ETHNIC_TR1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_ETHNIC_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ext_id_id_s`
--

DROP TABLE IF EXISTS `krim_entity_ext_id_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ext_id_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_ext_id_t`
--

DROP TABLE IF EXISTS `krim_entity_ext_id_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_ext_id_t` (
  `ENTITY_EXT_ID_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EXT_ID_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EXT_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_EXT_ID_ID`),
  UNIQUE KEY `KRIM_ENTITY_EXT_ID_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_EXT_ID_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_EXT_ID_TR2` (`EXT_ID_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_EXT_ID_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_EXT_ID_TR2` FOREIGN KEY (`EXT_ID_TYP_CD`) REFERENCES `krim_ext_id_typ_t` (`EXT_ID_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_id_s`
--

DROP TABLE IF EXISTS `krim_entity_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_nm_id_s`
--

DROP TABLE IF EXISTS `krim_entity_nm_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_nm_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_nm_t`
--

DROP TABLE IF EXISTS `krim_entity_nm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_nm_t` (
  `ENTITY_NM_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NM_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIRST_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MIDDLE_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `LAST_NM` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `SUFFIX_NM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TITLE_NM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_NM_ID`),
  UNIQUE KEY `KRIM_ENTITY_NM_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_NM_TI1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_NM_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_phone_id_s`
--

DROP TABLE IF EXISTS `krim_entity_phone_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_phone_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_phone_t`
--

DROP TABLE IF EXISTS `krim_entity_phone_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_phone_t` (
  `ENTITY_PHONE_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_NBR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_EXTN_NBR` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_PHONE_ID`),
  UNIQUE KEY `KRIM_ENTITY_PHONE_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_PHONE_TI1` (`ENTITY_ID`),
  KEY `KRIM_ENTITY_PHONE_TR1` (`ENT_TYP_CD`,`ENTITY_ID`),
  KEY `KRIM_ENTITY_PHONE_TR2` (`PHONE_TYP_CD`),
  CONSTRAINT `KRIM_ENTITY_PHONE_TR1` FOREIGN KEY (`ENT_TYP_CD`, `ENTITY_ID`) REFERENCES `krim_entity_ent_typ_t` (`ENT_TYP_CD`, `ENTITY_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_ENTITY_PHONE_TR2` FOREIGN KEY (`PHONE_TYP_CD`) REFERENCES `krim_phone_typ_t` (`PHONE_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_priv_pref_t`
--

DROP TABLE IF EXISTS `krim_entity_priv_pref_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_priv_pref_t` (
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `SUPPRESS_NM_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `SUPPRESS_EMAIL_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_ADDR_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_PHONE_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_PRSNL_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_ID`),
  UNIQUE KEY `KRIM_ENTITY_PRIV_PREF_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_residency_id_s`
--

DROP TABLE IF EXISTS `krim_entity_residency_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_residency_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_residency_t`
--

DROP TABLE IF EXISTS `krim_entity_residency_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_residency_t` (
  `ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DETERMINATION_METHOD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `IN_STATE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `KRIM_ENTITY_RESIDENCY_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_RESIDENCY_TR1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_RESIDENCY_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_t`
--

DROP TABLE IF EXISTS `krim_entity_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_t` (
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_ID`),
  UNIQUE KEY `KRIM_ENTITY_TC0` (`OBJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_visa_id_s`
--

DROP TABLE IF EXISTS `krim_entity_visa_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_visa_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_entity_visa_t`
--

DROP TABLE IF EXISTS `krim_entity_visa_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_entity_visa_t` (
  `ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VISA_TYPE_KEY` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VISA_ENTRY` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VISA_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `KRIM_ENTITY_VISA_TC0` (`OBJ_ID`),
  KEY `KRIM_ENTITY_VISA_TR1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_ENTITY_VISA_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_ext_id_typ_t`
--

DROP TABLE IF EXISTS `krim_ext_id_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_ext_id_typ_t` (
  `EXT_ID_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENCR_REQ_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`EXT_ID_TYP_CD`),
  UNIQUE KEY `KRIM_EXT_ID_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_EXT_ID_TYP_TC1` (`NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_attr_data_id_s`
--

DROP TABLE IF EXISTS `krim_grp_attr_data_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_attr_data_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_attr_data_t`
--

DROP TABLE IF EXISTS `krim_grp_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_attr_data_t` (
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ATTR_DATA_ID`),
  UNIQUE KEY `KRIM_GRP_ATTR_DATA_TC0` (`OBJ_ID`),
  KEY `KRIM_GRP_ATTR_DATA_TR1` (`KIM_TYP_ID`),
  KEY `KRIM_GRP_ATTR_DATA_TR3` (`GRP_ID`),
  KEY `KRIM_GRP_ATTR_DATA_TR2` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_GRP_ATTR_DATA_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_GRP_ATTR_DATA_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_GRP_ATTR_DATA_TR3` FOREIGN KEY (`GRP_ID`) REFERENCES `krim_grp_t` (`GRP_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_document_t`
--

DROP TABLE IF EXISTS `krim_grp_document_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_document_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GRP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `GRP_NMSPC` varchar(100) COLLATE utf8_bin NOT NULL,
  `GRP_NM` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `GRP_DESC` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_id_s`
--

DROP TABLE IF EXISTS `krim_grp_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_mbr_id_s`
--

DROP TABLE IF EXISTS `krim_grp_mbr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_mbr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_grp_mbr_t`
--

DROP TABLE IF EXISTS `krim_grp_mbr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_mbr_t` (
  `GRP_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` char(1) COLLATE utf8_bin DEFAULT 'P',
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`GRP_MBR_ID`),
  UNIQUE KEY `KRIM_GRP_MBR_TC0` (`OBJ_ID`),
  KEY `KRIM_GRP_MBR_TI1` (`MBR_ID`),
  KEY `KRIM_GRP_MBR_TR1` (`GRP_ID`),
  CONSTRAINT `KRIM_GRP_MBR_TR1` FOREIGN KEY (`GRP_ID`) REFERENCES `krim_grp_t` (`GRP_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_grp_mbr_v`
--

DROP TABLE IF EXISTS `krim_grp_mbr_v`;
/*!50001 DROP VIEW IF EXISTS `krim_grp_mbr_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_grp_mbr_v` (
  `NMSPC_CD` varchar(40),
  `grp_nm` varchar(80),
  `GRP_ID` varchar(40),
  `PRNCPL_NM` varchar(100),
  `PRNCPL_ID` varchar(40),
  `mbr_grp_nm` varchar(80),
  `mbr_grp_id` varchar(40)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_grp_t`
--

DROP TABLE IF EXISTS `krim_grp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_grp_t` (
  `GRP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `GRP_NM` varchar(80) COLLATE utf8_bin NOT NULL,
  `NMSPC_CD` varchar(40) COLLATE utf8_bin NOT NULL,
  `GRP_DESC` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`GRP_ID`),
  UNIQUE KEY `KRIM_GRP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_GRP_TC1` (`GRP_NM`,`NMSPC_CD`),
  KEY `KRIM_GRP_TR1` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_GRP_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_grp_v`
--

DROP TABLE IF EXISTS `krim_grp_v`;
/*!50001 DROP VIEW IF EXISTS `krim_grp_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_grp_v` (
  `NMSPC_CD` varchar(40),
  `grp_nm` varchar(80),
  `GRP_ID` varchar(40),
  `grp_typ_nm` varchar(100),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_perm_attr_data_t`
--

DROP TABLE IF EXISTS `krim_perm_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_attr_data_t` (
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PERM_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ATTR_DATA_ID`),
  UNIQUE KEY `KRIM_PERM_ATTR_DATA_TC0` (`OBJ_ID`),
  KEY `KRIM_PERM_ATTR_DATA_TI1` (`PERM_ID`),
  KEY `KRIM_PERM_ATTR_DATA_TR1` (`KIM_TYP_ID`),
  KEY `KRIM_PERM_ATTR_DATA_TR2` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_PERM_ATTR_DATA_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_PERM_ATTR_DATA_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_PERM_ATTR_DATA_TR3` FOREIGN KEY (`PERM_ID`) REFERENCES `krim_perm_t` (`PERM_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_perm_attr_v`
--

DROP TABLE IF EXISTS `krim_perm_attr_v`;
/*!50001 DROP VIEW IF EXISTS `krim_perm_attr_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_perm_attr_v` (
  `tmpl_nmspc_cd` varchar(40),
  `tmpl_nm` varchar(100),
  `PERM_TMPL_ID` varchar(40),
  `perm_nmspc_cd` varchar(40),
  `perm_nm` varchar(100),
  `PERM_ID` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_perm_id_s`
--

DROP TABLE IF EXISTS `krim_perm_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_perm_rqrd_attr_id_s`
--

DROP TABLE IF EXISTS `krim_perm_rqrd_attr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_rqrd_attr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_perm_t`
--

DROP TABLE IF EXISTS `krim_perm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_t` (
  `PERM_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PERM_TMPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `DESC_TXT` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`PERM_ID`),
  UNIQUE KEY `KRIM_PERM_TC0` (`OBJ_ID`),
  KEY `KRIM_PERM_TR1` (`PERM_TMPL_ID`),
  CONSTRAINT `KRIM_PERM_TR1` FOREIGN KEY (`PERM_TMPL_ID`) REFERENCES `krim_perm_tmpl_t` (`PERM_TMPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_perm_tmpl_id_s`
--

DROP TABLE IF EXISTS `krim_perm_tmpl_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_tmpl_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_perm_tmpl_t`
--

DROP TABLE IF EXISTS `krim_perm_tmpl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_perm_tmpl_t` (
  `PERM_TMPL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `DESC_TXT` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`PERM_TMPL_ID`),
  UNIQUE KEY `KRIM_PERM_TMPL_TC0` (`OBJ_ID`),
  KEY `KRIM_PERM_TMPL_TR1` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_PERM_TMPL_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_perm_v`
--

DROP TABLE IF EXISTS `krim_perm_v`;
/*!50001 DROP VIEW IF EXISTS `krim_perm_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_perm_v` (
  `tmpl_nmspc_cd` varchar(40),
  `tmpl_nm` varchar(100),
  `PERM_TMPL_ID` varchar(40),
  `perm_nmspc_cd` varchar(40),
  `perm_nm` varchar(100),
  `PERM_ID` varchar(40),
  `perm_typ_nm` varchar(100),
  `SRVC_NM` varchar(200)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_person_document_t`
--

DROP TABLE IF EXISTS `krim_person_document_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_person_document_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `PRNCPL_NM` varchar(100) COLLATE utf8_bin NOT NULL,
  `PRNCPL_PSWD` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `TAX_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `UNIV_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_phone_typ_t`
--

DROP TABLE IF EXISTS `krim_phone_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_phone_typ_t` (
  `PHONE_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PHONE_TYP_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`PHONE_TYP_CD`),
  UNIQUE KEY `KRIM_PHONE_TYP_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_PHONE_TYP_TC1` (`PHONE_TYP_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_addr_mt`
--

DROP TABLE IF EXISTS `krim_pnd_addr_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_addr_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ADDR_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_1` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_2` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADDR_LINE_3` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CITY_NM` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_STATE_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CD` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DISPLAY_SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `ENTITY_ADDR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_ADDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_afltn_mt`
--

DROP TABLE IF EXISTS `krim_pnd_afltn_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_afltn_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_AFLTN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `AFLTN_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CAMPUS_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_AFLTN_ID`),
  CONSTRAINT `KRIM_PND_AFLTN_MT_FK1` FOREIGN KEY (`FDOC_NBR`) REFERENCES `krim_person_document_t` (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_ctznshp_mt`
--

DROP TABLE IF EXISTS `krim_pnd_ctznshp_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_ctznshp_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_CTZNSHP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `CTZNSHP_STAT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `STRT_DT` datetime DEFAULT NULL,
  `END_DT` datetime DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_CTZNSHP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_dlgn_mbr_attr_data_t`
--

DROP TABLE IF EXISTS `krim_pnd_dlgn_mbr_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_dlgn_mbr_attr_data_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DLGN_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ATTR_DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_dlgn_mbr_t`
--

DROP TABLE IF EXISTS `krim_pnd_dlgn_mbr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_dlgn_mbr_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `DLGN_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `DLGN_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FDOC_NBR`,`DLGN_MBR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_dlgn_t`
--

DROP TABLE IF EXISTS `krim_pnd_dlgn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_dlgn_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `DLGN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DLGN_TYP_CD` varchar(100) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`FDOC_NBR`,`DLGN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_email_mt`
--

DROP TABLE IF EXISTS `krim_pnd_email_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_email_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_EMAIL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_ADDR` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_EMAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_emp_info_mt`
--

DROP TABLE IF EXISTS `krim_pnd_emp_info_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_emp_info_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PRMRY_DEPT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ENTITY_EMP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `EMP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_REC_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENTITY_AFLTN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_STAT_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMP_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `BASE_SLRY_AMT` decimal(15,2) DEFAULT NULL,
  `PRMRY_IND` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_EMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_grp_attr_data_t`
--

DROP TABLE IF EXISTS `krim_pnd_grp_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_grp_attr_data_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `GRP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FDOC_NBR`,`ATTR_DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_grp_mbr_t`
--

DROP TABLE IF EXISTS `krim_pnd_grp_mbr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_grp_mbr_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GRP_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `GRP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`FDOC_NBR`,`GRP_MBR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_grp_prncpl_mt`
--

DROP TABLE IF EXISTS `krim_pnd_grp_prncpl_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_grp_prncpl_mt` (
  `GRP_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `GRP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `GRP_NM` varchar(80) COLLATE utf8_bin NOT NULL,
  `GRP_TYPE` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`GRP_MBR_ID`,`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_nm_mt`
--

DROP TABLE IF EXISTS `krim_pnd_nm_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_nm_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_NM_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FIRST_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MIDDLE_NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `LAST_NM` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `SUFFIX_NM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TITLE_NM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_NM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_phone_mt`
--

DROP TABLE IF EXISTS `krim_pnd_phone_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_phone_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ENTITY_PHONE_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ENT_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_NBR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PHONE_EXTN_NBR` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `POSTAL_CNTRY_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `DFLT_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ENTITY_PHONE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_priv_pref_mt`
--

DROP TABLE IF EXISTS `krim_pnd_priv_pref_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_priv_pref_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `SUPPRESS_NM_IND` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `SUPPRESS_EMAIL_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_ADDR_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_PHONE_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `SUPPRESS_PRSNL_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_mbr_attr_data_mt`
--

DROP TABLE IF EXISTS `krim_pnd_role_mbr_attr_data_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_mbr_attr_data_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ATTR_DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_mbr_mt`
--

DROP TABLE IF EXISTS `krim_pnd_role_mbr_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_mbr_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ROLE_MBR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_mt`
--

DROP TABLE IF EXISTS `krim_pnd_role_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_mt` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_NM` varchar(100) COLLATE utf8_bin NOT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`FDOC_NBR`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_perm_t`
--

DROP TABLE IF EXISTS `krim_pnd_role_perm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_perm_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_PERM_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `PERM_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`FDOC_NBR`,`ROLE_PERM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_rsp_actn_mt`
--

DROP TABLE IF EXISTS `krim_pnd_role_rsp_actn_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_rsp_actn_mt` (
  `ROLE_RSP_ACTN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTN_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_NBR` decimal(3,0) DEFAULT NULL,
  `ACTN_PLCY_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_RSP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EDIT_FLAG` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  `FRC_ACTN` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ROLE_RSP_ACTN_ID`,`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_pnd_role_rsp_t`
--

DROP TABLE IF EXISTS `krim_pnd_role_rsp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_pnd_role_rsp_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_RSP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `RSP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`FDOC_NBR`,`ROLE_RSP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_prncpl_id_s`
--

DROP TABLE IF EXISTS `krim_prncpl_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_prncpl_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_prncpl_t`
--

DROP TABLE IF EXISTS `krim_prncpl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_prncpl_t` (
  `PRNCPL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PRNCPL_NM` varchar(100) COLLATE utf8_bin NOT NULL,
  `ENTITY_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRNCPL_PSWD` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`PRNCPL_ID`),
  UNIQUE KEY `KRIM_PRNCPL_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_PRNCPL_TC1` (`PRNCPL_NM`),
  KEY `KRIM_PRNCPL_TR1` (`ENTITY_ID`),
  CONSTRAINT `KRIM_PRNCPL_TR1` FOREIGN KEY (`ENTITY_ID`) REFERENCES `krim_entity_t` (`ENTITY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_prncpl_v`
--

DROP TABLE IF EXISTS `krim_prncpl_v`;
/*!50001 DROP VIEW IF EXISTS `krim_prncpl_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_prncpl_v` (
  `PRNCPL_ID` varchar(40),
  `PRNCPL_NM` varchar(100),
  `FIRST_NM` varchar(40),
  `LAST_NM` varchar(80),
  `AFLTN_TYP_CD` varchar(40),
  `CAMPUS_CD` varchar(2),
  `EMP_STAT_CD` varchar(40),
  `EMP_TYP_CD` varchar(40)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_role_document_t`
--

DROP TABLE IF EXISTS `krim_role_document_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_document_t` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ROLE_NMSPC` varchar(100) COLLATE utf8_bin NOT NULL,
  `ROLE_NM` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `DESC_TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_role_grp_v`
--

DROP TABLE IF EXISTS `krim_role_grp_v`;
/*!50001 DROP VIEW IF EXISTS `krim_role_grp_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_role_grp_v` (
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `role_id` varchar(40),
  `grp_nmspc_cd` varchar(40),
  `GRP_NM` varchar(80),
  `ROLE_MBR_ID` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_role_id_s`
--

DROP TABLE IF EXISTS `krim_role_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_mbr_attr_data_t`
--

DROP TABLE IF EXISTS `krim_role_mbr_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_mbr_attr_data_t` (
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ATTR_DATA_ID`),
  UNIQUE KEY `KRIM_ROLE_MBR_ATTR_DATA_TC0` (`OBJ_ID`),
  KEY `KRIM_ROLE_MBR_ATTR_DATA_TI1` (`ROLE_MBR_ID`),
  KEY `KRIM_ROLE_MBR_ATTR_DATA_TR1` (`KIM_TYP_ID`),
  KEY `KRIM_ROLE_MBR_ATTR_DATA_TR2` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_ROLE_MBR_ATTR_DATA_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_ROLE_MBR_ATTR_DATA_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_mbr_id_s`
--

DROP TABLE IF EXISTS `krim_role_mbr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_mbr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10031 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_mbr_t`
--

DROP TABLE IF EXISTS `krim_role_mbr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_mbr_t` (
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `ROLE_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `MBR_TYP_CD` char(1) COLLATE utf8_bin DEFAULT 'P',
  `ACTV_FRM_DT` datetime DEFAULT NULL,
  `ACTV_TO_DT` datetime DEFAULT NULL,
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ROLE_MBR_ID`),
  UNIQUE KEY `KRIM_ROLE_MBR_TC0` (`OBJ_ID`),
  KEY `KRIM_ROLE_MBR_TI1` (`MBR_ID`),
  KEY `KRIM_ROLE_MBR_TR1` (`ROLE_ID`),
  CONSTRAINT `KRIM_ROLE_MBR_TR1` FOREIGN KEY (`ROLE_ID`) REFERENCES `krim_role_t` (`ROLE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_perm_id_s`
--

DROP TABLE IF EXISTS `krim_role_perm_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_perm_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_perm_t`
--

DROP TABLE IF EXISTS `krim_role_perm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_perm_t` (
  `ROLE_PERM_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PERM_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`ROLE_PERM_ID`),
  UNIQUE KEY `KRIM_ROLE_PERM_TC0` (`OBJ_ID`),
  KEY `KRIM_ROLE_PERM_TI1` (`PERM_ID`),
  CONSTRAINT `KRIM_ROLE_PERM_TR1` FOREIGN KEY (`PERM_ID`) REFERENCES `krim_perm_t` (`PERM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_role_perm_v`
--

DROP TABLE IF EXISTS `krim_role_perm_v`;
/*!50001 DROP VIEW IF EXISTS `krim_role_perm_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_role_perm_v` (
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `role_id` varchar(40),
  `tmpl_nmspc_cd` varchar(40),
  `tmpl_nm` varchar(100),
  `PERM_TMPL_ID` varchar(40),
  `perm_nmpsc_cd` varchar(40),
  `perm_nm` varchar(100),
  `PERM_ID` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `krim_role_prncpl_v`
--

DROP TABLE IF EXISTS `krim_role_prncpl_v`;
/*!50001 DROP VIEW IF EXISTS `krim_role_prncpl_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_role_prncpl_v` (
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `ROLE_ID` varchar(40),
  `PRNCPL_NM` varchar(100),
  `PRNCPL_ID` varchar(40),
  `FIRST_NM` varchar(40),
  `LAST_NM` varchar(80),
  `ROLE_MBR_ID` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `krim_role_role_v`
--

DROP TABLE IF EXISTS `krim_role_role_v`;
/*!50001 DROP VIEW IF EXISTS `krim_role_role_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_role_role_v` (
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `role_id` varchar(40),
  `mbr_role_nmspc_cd` varchar(40),
  `mbr_role_nm` varchar(80),
  `mbr_role_id` varchar(40),
  `role_mbr_id` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_role_rsp_actn_id_s`
--

DROP TABLE IF EXISTS `krim_role_rsp_actn_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_rsp_actn_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_rsp_actn_t`
--

DROP TABLE IF EXISTS `krim_role_rsp_actn_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_rsp_actn_t` (
  `ROLE_RSP_ACTN_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTN_TYP_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_NBR` decimal(3,0) DEFAULT NULL,
  `ACTN_PLCY_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_MBR_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_RSP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FRC_ACTN` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`ROLE_RSP_ACTN_ID`),
  UNIQUE KEY `KRIM_ROLE_RSP_ACTN_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ROLE_RSP_ACTN_TC1` (`ROLE_RSP_ID`,`ROLE_MBR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_rsp_id_s`
--

DROP TABLE IF EXISTS `krim_role_rsp_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_rsp_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_rsp_t`
--

DROP TABLE IF EXISTS `krim_role_rsp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_rsp_t` (
  `ROLE_RSP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `RSP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`ROLE_RSP_ID`),
  UNIQUE KEY `KRIM_ROLE_RSP_TC0` (`OBJ_ID`),
  KEY `KRIM_ROLE_RSP_TI1` (`RSP_ID`),
  CONSTRAINT `KRIM_ROLE_RSP_TR1` FOREIGN KEY (`RSP_ID`) REFERENCES `krim_rsp_t` (`RSP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_role_t`
--

DROP TABLE IF EXISTS `krim_role_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_role_t` (
  `ROLE_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ROLE_NM` varchar(80) COLLATE utf8_bin NOT NULL,
  `NMSPC_CD` varchar(40) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `LAST_UPDT_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `KRIM_ROLE_TC0` (`OBJ_ID`),
  UNIQUE KEY `KRIM_ROLE_TC1` (`ROLE_NM`,`NMSPC_CD`),
  KEY `KRIM_ROLE_TR1` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_ROLE_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_role_v`
--

DROP TABLE IF EXISTS `krim_role_v`;
/*!50001 DROP VIEW IF EXISTS `krim_role_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_role_v` (
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `ROLE_ID` varchar(40),
  `role_typ_nm` varchar(100),
  `SRVC_NM` varchar(200),
  `KIM_TYP_ID` varchar(40)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_rsp_attr_data_t`
--

DROP TABLE IF EXISTS `krim_rsp_attr_data_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_attr_data_t` (
  `ATTR_DATA_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `RSP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ATTR_VAL` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ATTR_DATA_ID`),
  UNIQUE KEY `KRIM_RSP_ATTR_DATA_TC0` (`OBJ_ID`),
  KEY `KRIM_RSP_ATTR_DATA_TR3` (`RSP_ID`),
  KEY `KRIM_RSP_ATTR_DATA_TR1` (`KIM_TYP_ID`),
  KEY `KRIM_RSP_ATTR_DATA_TR2` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_RSP_ATTR_DATA_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_RSP_ATTR_DATA_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_RSP_ATTR_DATA_TR3` FOREIGN KEY (`RSP_ID`) REFERENCES `krim_rsp_t` (`RSP_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_rsp_attr_v`
--

DROP TABLE IF EXISTS `krim_rsp_attr_v`;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_attr_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_rsp_attr_v` (
  `responsibility_type_name` varchar(100),
  `rsp_TEMPLATE_NAME` varchar(80),
  `rsp_namespace_code` varchar(40),
  `rsp_NAME` varchar(100),
  `rsp_id` varchar(40),
  `attribute_name` varchar(100),
  `attribute_value` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_rsp_id_s`
--

DROP TABLE IF EXISTS `krim_rsp_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `krim_rsp_role_actn_v`
--

DROP TABLE IF EXISTS `krim_rsp_role_actn_v`;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_role_actn_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_rsp_role_actn_v` (
  `rsp_nmspc_cd` varchar(40),
  `rsp_id` varchar(40),
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `ROLE_ID` varchar(40),
  `MBR_ID` varchar(40),
  `MBR_TYP_CD` char(1),
  `ROLE_MBR_ID` varchar(40),
  `ACTN_TYP_CD` varchar(40),
  `ACTN_PLCY_CD` varchar(40),
  `FRC_ACTN` varchar(1),
  `PRIORITY_NBR` decimal(3,0)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `krim_rsp_role_v`
--

DROP TABLE IF EXISTS `krim_rsp_role_v`;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_role_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `krim_rsp_role_v` (
  `rsp_tmpl_nmspc_cd` varchar(40),
  `rsp_tmpl_nm` varchar(80),
  `rsp_nmspc_cd` varchar(40),
  `rsp_nm` varchar(100),
  `rsp_id` varchar(40),
  `attr_nm` varchar(100),
  `attr_val` varchar(400),
  `NMSPC_CD` varchar(40),
  `ROLE_NM` varchar(80),
  `ROLE_ID` varchar(40)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `krim_rsp_rqrd_attr_id_s`
--

DROP TABLE IF EXISTS `krim_rsp_rqrd_attr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_rqrd_attr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_rsp_t`
--

DROP TABLE IF EXISTS `krim_rsp_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_t` (
  `RSP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `RSP_TMPL_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `DESC_TXT` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`RSP_ID`),
  UNIQUE KEY `KRIM_RSP_TC0` (`OBJ_ID`),
  KEY `KRIM_RSP_TR1` (`RSP_TMPL_ID`),
  CONSTRAINT `KRIM_RSP_TR1` FOREIGN KEY (`RSP_TMPL_ID`) REFERENCES `krim_rsp_tmpl_t` (`RSP_TMPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_rsp_tmpl_id_s`
--

DROP TABLE IF EXISTS `krim_rsp_tmpl_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_tmpl_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_rsp_tmpl_t`
--

DROP TABLE IF EXISTS `krim_rsp_tmpl_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_rsp_tmpl_t` (
  `RSP_TMPL_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `NM` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(100) COLLATE utf8_bin NOT NULL,
  `DESC_TXT` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`RSP_TMPL_ID`),
  UNIQUE KEY `KRIM_RSP_TMPL_TC0` (`OBJ_ID`),
  KEY `KRIM_RSP_TMPL_TR1` (`KIM_TYP_ID`),
  CONSTRAINT `KRIM_RSP_TMPL_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_typ_attr_id_s`
--

DROP TABLE IF EXISTS `krim_typ_attr_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_typ_attr_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_typ_attr_t`
--

DROP TABLE IF EXISTS `krim_typ_attr_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_typ_attr_t` (
  `KIM_TYP_ATTR_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `SORT_CD` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL,
  `KIM_ATTR_DEFN_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  PRIMARY KEY (`KIM_TYP_ATTR_ID`),
  UNIQUE KEY `KRIM_TYP_ATTR_TC0` (`OBJ_ID`),
  KEY `KRIM_TYP_ATTRIBUTE_TI1` (`KIM_TYP_ID`),
  KEY `KRIM_TYP_ATTR_TR2` (`KIM_ATTR_DEFN_ID`),
  CONSTRAINT `KRIM_TYP_ATTRIBUTE_TR1` FOREIGN KEY (`KIM_TYP_ID`) REFERENCES `krim_typ_t` (`KIM_TYP_ID`) ON DELETE CASCADE,
  CONSTRAINT `KRIM_TYP_ATTR_TR2` FOREIGN KEY (`KIM_ATTR_DEFN_ID`) REFERENCES `krim_attr_defn_t` (`KIM_ATTR_DEFN_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_typ_id_s`
--

DROP TABLE IF EXISTS `krim_typ_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_typ_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krim_typ_t`
--

DROP TABLE IF EXISTS `krim_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krim_typ_t` (
  `KIM_TYP_ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SRVC_NM` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` varchar(1) COLLATE utf8_bin DEFAULT 'Y',
  `NMSPC_CD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`KIM_TYP_ID`),
  UNIQUE KEY `KRIM_TYP_TC0` (`OBJ_ID`)
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
-- Table structure for table `krns_campus_t`
--

DROP TABLE IF EXISTS `krns_campus_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_campus_t` (
  `CAMPUS_CD` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CAMPUS_NM` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `CAMPUS_SHRT_NM` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `CAMPUS_TYP_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`CAMPUS_CD`),
  UNIQUE KEY `KRNS_CAMPUS_TC0` (`OBJ_ID`),
  KEY `SH_CAMPUS_TR1` (`CAMPUS_TYP_CD`),
  CONSTRAINT `SH_CAMPUS_TR1` FOREIGN KEY (`CAMPUS_TYP_CD`) REFERENCES `krns_cmp_typ_t` (`CAMPUS_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krns_cmp_typ_t`
--

DROP TABLE IF EXISTS `krns_cmp_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_cmp_typ_t` (
  `CAMPUS_TYP_CD` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CMP_TYP_NM` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `DOBJ_MAINT_CD_ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL,
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `ACTV_IND` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`CAMPUS_TYP_CD`),
  UNIQUE KEY `KRNS_CMP_TYP_TC0` (`OBJ_ID`)
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
-- Table structure for table `krns_doc_typ_attr_s`
--

DROP TABLE IF EXISTS `krns_doc_typ_attr_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_doc_typ_attr_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
) ENGINE=MyISAM AUTO_INCREMENT=2022 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `krns_nmspc_t`
--

DROP TABLE IF EXISTS `krns_nmspc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_nmspc_t` (
  `NMSPC_CD` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `APPL_NMSPC_CD` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`NMSPC_CD`),
  UNIQUE KEY `KRNS_NMSPC_TC0` (`OBJ_ID`)
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
-- Table structure for table `krns_parm_dtl_typ_t`
--

DROP TABLE IF EXISTS `krns_parm_dtl_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_parm_dtl_typ_t` (
  `NMSPC_CD` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PARM_DTL_TYP_CD` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`NMSPC_CD`,`PARM_DTL_TYP_CD`),
  UNIQUE KEY `KRNS_PARM_DTL_TYP_TC0` (`OBJ_ID`),
  CONSTRAINT `KRNS_PARM_DTL_TYP_TR1` FOREIGN KEY (`NMSPC_CD`) REFERENCES `krns_nmspc_t` (`NMSPC_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krns_parm_t`
--

DROP TABLE IF EXISTS `krns_parm_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_parm_t` (
  `NMSPC_CD` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PARM_DTL_TYP_CD` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `PARM_NM` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `PARM_TYP_CD` varchar(5) COLLATE utf8_bin NOT NULL,
  `TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PARM_DESC_TXT` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONS_CD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `APPL_NMSPC_CD` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'KUALI',
  PRIMARY KEY (`NMSPC_CD`,`PARM_DTL_TYP_CD`,`PARM_NM`,`APPL_NMSPC_CD`),
  UNIQUE KEY `KRNS_PARM_TC0` (`OBJ_ID`),
  KEY `KRNS_PARM_TR2` (`PARM_TYP_CD`),
  CONSTRAINT `KRNS_PARM_TR1` FOREIGN KEY (`NMSPC_CD`) REFERENCES `krns_nmspc_t` (`NMSPC_CD`),
  CONSTRAINT `KRNS_PARM_TR2` FOREIGN KEY (`PARM_TYP_CD`) REFERENCES `krns_parm_typ_t` (`PARM_TYP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krns_parm_typ_t`
--

DROP TABLE IF EXISTS `krns_parm_typ_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krns_parm_typ_t` (
  `PARM_TYP_CD` varchar(5) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `NM` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ACTV_IND` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`PARM_TYP_CD`),
  UNIQUE KEY `KRNS_PARM_TYP_TC0` (`OBJ_ID`)
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
-- Table structure for table `krsb_flt_svc_def_s`
--

DROP TABLE IF EXISTS `krsb_flt_svc_def_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_flt_svc_def_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1956 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_flt_svc_def_t`
--

DROP TABLE IF EXISTS `krsb_flt_svc_def_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_flt_svc_def_t` (
  `FLT_SVC_DEF_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `FLT_SVC_DEF` longtext COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`FLT_SVC_DEF_ID`)
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
) ENGINE=MyISAM AUTO_INCREMENT=10258 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `krsb_qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `krsb_qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_blob_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `BLOB_DATA` longblob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `KRSB_QRTZ_BLOB_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `krsb_qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_calendars`
--

DROP TABLE IF EXISTS `krsb_qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_calendars` (
  `CALENDAR_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CALENDAR` longblob NOT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `krsb_qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_cron_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `CRON_EXPRESSION` varchar(80) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `KRSB_QRTZ_CRON_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `krsb_qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `krsb_qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_fired_triggers` (
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
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI1` (`JOB_GROUP`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI2` (`JOB_NAME`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI3` (`REQUESTS_RECOVERY`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI4` (`IS_STATEFUL`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI5` (`TRIGGER_GROUP`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI6` (`INSTANCE_NAME`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI7` (`TRIGGER_NAME`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI8` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `KRSB_QRTZ_FIRED_TRIGGERS_TI9` (`IS_VOLATILE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_job_details`
--

DROP TABLE IF EXISTS `krsb_qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_job_details` (
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
  KEY `KRSB_QRTZ_JOB_DETAILS_TI1` (`REQUESTS_RECOVERY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_job_listeners`
--

DROP TABLE IF EXISTS `krsb_qrtz_job_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_job_listeners` (
  `JOB_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `JOB_LISTENER` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`,`JOB_LISTENER`),
  CONSTRAINT `KRSB_QUARTZ_JOB_LISTENERS_TR1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `krsb_qrtz_job_details` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_locks`
--

DROP TABLE IF EXISTS `krsb_qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_locks` (
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `krsb_qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_paused_trigger_grps` (
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `krsb_qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_scheduler_state` (
  `INSTANCE_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `LAST_CHECKIN_TIME` decimal(13,0) NOT NULL,
  `CHECKIN_INTERVAL` decimal(13,0) NOT NULL,
  PRIMARY KEY (`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `krsb_qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_simple_triggers` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REPEAT_COUNT` decimal(7,0) NOT NULL,
  `REPEAT_INTERVAL` decimal(12,0) NOT NULL,
  `TIMES_TRIGGERED` decimal(7,0) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `KRSB_QRTZ_SIMPLE_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `krsb_qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_trigger_listeners`
--

DROP TABLE IF EXISTS `krsb_qrtz_trigger_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_trigger_listeners` (
  `TRIGGER_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRIGGER_LISTENER` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_LISTENER`),
  CONSTRAINT `KRSB_QRTZ_TRIGGER_LISTENE_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `krsb_qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `krsb_qrtz_triggers`
--

DROP TABLE IF EXISTS `krsb_qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `krsb_qrtz_triggers` (
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
  KEY `KRSB_QRTZ_TRIGGERS_TI1` (`NEXT_FIRE_TIME`),
  KEY `KRSB_QRTZ_TRIGGERS_TI2` (`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `KRSB_QRTZ_TRIGGERS_TI3` (`TRIGGER_STATE`),
  KEY `KRSB_QRTZ_TRIGGERS_TI4` (`IS_VOLATILE`)
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
) ENGINE=MyISAM AUTO_INCREMENT=5014 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- Table structure for table `trav_doc_2_accounts`
--

DROP TABLE IF EXISTS `trav_doc_2_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trav_doc_2_accounts` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCT_NUM` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`FDOC_NBR`,`ACCT_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_acct`
--

DROP TABLE IF EXISTS `trv_acct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_acct` (
  `ACCT_NUM` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCT_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ACCT_TYPE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ACCT_FO_ID` decimal(14,0) DEFAULT NULL,
  PRIMARY KEY (`ACCT_NUM`),
  KEY `TRV_ACCT_FK1` (`ACCT_FO_ID`),
  CONSTRAINT `TRV_ACCT_FK1` FOREIGN KEY (`ACCT_FO_ID`) REFERENCES `trv_acct_fo` (`ACCT_FO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_acct_ext`
--

DROP TABLE IF EXISTS `trv_acct_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_acct_ext` (
  `ACCT_NUM` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCT_TYPE` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`ACCT_NUM`,`ACCT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_acct_fo`
--

DROP TABLE IF EXISTS `trv_acct_fo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_acct_fo` (
  `ACCT_FO_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `ACCT_FO_USER_NAME` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ACCT_FO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_acct_type`
--

DROP TABLE IF EXISTS `trv_acct_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_acct_type` (
  `ACCT_TYPE` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ACCT_TYPE_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ACCT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_doc_2`
--

DROP TABLE IF EXISTS `trv_doc_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_doc_2` (
  `FDOC_NBR` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` decimal(8,0) NOT NULL DEFAULT '1',
  `FDOC_EXPLAIN_TXT` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `REQUEST_TRAV` varchar(30) COLLATE utf8_bin NOT NULL,
  `TRAVELER` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `ORG` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `DEST` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`FDOC_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_doc_acct`
--

DROP TABLE IF EXISTS `trv_doc_acct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_doc_acct` (
  `DOC_HDR_ID` decimal(14,0) NOT NULL DEFAULT '0',
  `ACCT_NUM` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`DOC_HDR_ID`,`ACCT_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trv_fo_id_s`
--

DROP TABLE IF EXISTS `trv_fo_id_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trv_fo_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tst_search_attr_indx_tst_doc_t`
--

DROP TABLE IF EXISTS `tst_search_attr_indx_tst_doc_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tst_search_attr_indx_tst_doc_t` (
  `DOC_HDR_ID` varchar(14) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` decimal(14,0) DEFAULT NULL,
  `RTE_LVL_CNT` decimal(14,0) DEFAULT NULL,
  `CNSTNT_STR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `RTD_STR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `HLD_RTD_STR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `RD_ACCS_CNT` decimal(14,0) DEFAULT NULL,
  PRIMARY KEY (`DOC_HDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `krim_grp_mbr_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_grp_mbr_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_grp_mbr_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_grp_mbr_v` AS select `g`.`NMSPC_CD` AS `NMSPC_CD`,`g`.`GRP_NM` AS `grp_nm`,`g`.`GRP_ID` AS `GRP_ID`,`p`.`PRNCPL_NM` AS `PRNCPL_NM`,`p`.`PRNCPL_ID` AS `PRNCPL_ID`,`mg`.`GRP_NM` AS `mbr_grp_nm`,`mg`.`GRP_ID` AS `mbr_grp_id` from ((((`krim_grp_mbr_t` `gm` left join `krim_grp_t` `g` on((`g`.`GRP_ID` = `gm`.`GRP_ID`))) left join `krim_grp_t` `mg` on(((`mg`.`GRP_ID` = `gm`.`MBR_ID`) and (`gm`.`MBR_TYP_CD` = 'G')))) left join `krim_prncpl_t` `p` on(((`p`.`PRNCPL_ID` = `gm`.`MBR_ID`) and (`gm`.`MBR_TYP_CD` = 'P')))) left join `krim_entity_nm_t` `en` on(((`en`.`ENTITY_ID` = `p`.`ENTITY_ID`) and (`en`.`DFLT_IND` = 'Y') and (`en`.`ACTV_IND` = 'Y')))) order by `g`.`NMSPC_CD`,`g`.`GRP_NM`,`p`.`PRNCPL_NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_grp_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_grp_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_grp_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_grp_v` AS select `g`.`NMSPC_CD` AS `NMSPC_CD`,`g`.`GRP_NM` AS `grp_nm`,`g`.`GRP_ID` AS `GRP_ID`,`t`.`NM` AS `grp_typ_nm`,`a`.`NM` AS `attr_nm`,`d`.`ATTR_VAL` AS `attr_val` from (((`krim_grp_t` `g` left join `krim_grp_attr_data_t` `d` on((`d`.`GRP_ID` = `g`.`GRP_ID`))) left join `krim_attr_defn_t` `a` on((`a`.`KIM_ATTR_DEFN_ID` = `d`.`KIM_ATTR_DEFN_ID`))) left join `krim_typ_t` `t` on((`g`.`KIM_TYP_ID` = `t`.`KIM_TYP_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_perm_attr_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_perm_attr_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_perm_attr_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_perm_attr_v` AS select `t`.`NMSPC_CD` AS `tmpl_nmspc_cd`,`t`.`NM` AS `tmpl_nm`,`t`.`PERM_TMPL_ID` AS `PERM_TMPL_ID`,`p`.`NMSPC_CD` AS `perm_nmspc_cd`,`p`.`NM` AS `perm_nm`,`p`.`PERM_ID` AS `PERM_ID`,`a`.`NM` AS `attr_nm`,`ad`.`ATTR_VAL` AS `attr_val` from (((`krim_perm_t` `p` left join `krim_perm_tmpl_t` `t` on((`p`.`PERM_TMPL_ID` = `t`.`PERM_TMPL_ID`))) left join `krim_perm_attr_data_t` `ad` on((`p`.`PERM_ID` = `ad`.`PERM_ID`))) left join `krim_attr_defn_t` `a` on((`ad`.`KIM_ATTR_DEFN_ID` = `a`.`KIM_ATTR_DEFN_ID`))) order by `t`.`NMSPC_CD`,`t`.`NM`,`p`.`NMSPC_CD`,`p`.`PERM_ID`,`a`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_perm_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_perm_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_perm_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_perm_v` AS select `t`.`NMSPC_CD` AS `tmpl_nmspc_cd`,`t`.`NM` AS `tmpl_nm`,`t`.`PERM_TMPL_ID` AS `PERM_TMPL_ID`,`p`.`NMSPC_CD` AS `perm_nmspc_cd`,`p`.`NM` AS `perm_nm`,`p`.`PERM_ID` AS `PERM_ID`,`typ`.`NM` AS `perm_typ_nm`,`typ`.`SRVC_NM` AS `SRVC_NM` from ((`krim_perm_t` `p` join `krim_perm_tmpl_t` `t` on((`p`.`PERM_TMPL_ID` = `t`.`PERM_TMPL_ID`))) left join `krim_typ_t` `typ` on((`t`.`KIM_TYP_ID` = `typ`.`KIM_TYP_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_prncpl_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_prncpl_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_prncpl_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_prncpl_v` AS select `p`.`PRNCPL_ID` AS `PRNCPL_ID`,`p`.`PRNCPL_NM` AS `PRNCPL_NM`,`en`.`FIRST_NM` AS `FIRST_NM`,`en`.`LAST_NM` AS `LAST_NM`,`ea`.`AFLTN_TYP_CD` AS `AFLTN_TYP_CD`,`ea`.`CAMPUS_CD` AS `CAMPUS_CD`,`eei`.`EMP_STAT_CD` AS `EMP_STAT_CD`,`eei`.`EMP_TYP_CD` AS `EMP_TYP_CD` from (((`krim_prncpl_t` `p` left join `krim_entity_emp_info_t` `eei` on((`eei`.`ENTITY_ID` = `p`.`ENTITY_ID`))) left join `krim_entity_afltn_t` `ea` on((`ea`.`ENTITY_ID` = `p`.`ENTITY_ID`))) left join `krim_entity_nm_t` `en` on(((`p`.`ENTITY_ID` = `en`.`ENTITY_ID`) and ('Y' = `en`.`DFLT_IND`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_role_grp_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_role_grp_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_role_grp_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_role_grp_v` AS select `r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`r`.`ROLE_ID` AS `role_id`,`g`.`NMSPC_CD` AS `grp_nmspc_cd`,`g`.`GRP_NM` AS `GRP_NM`,`rm`.`ROLE_MBR_ID` AS `ROLE_MBR_ID`,`a`.`NM` AS `attr_nm`,`d`.`ATTR_VAL` AS `attr_val` from ((((`krim_role_mbr_t` `rm` left join `krim_role_t` `r` on((`r`.`ROLE_ID` = `rm`.`ROLE_ID`))) left join `krim_grp_t` `g` on((`g`.`GRP_ID` = `rm`.`MBR_ID`))) left join `krim_role_mbr_attr_data_t` `d` on((`d`.`ROLE_MBR_ID` = `rm`.`ROLE_MBR_ID`))) left join `krim_attr_defn_t` `a` on((`a`.`KIM_ATTR_DEFN_ID` = `d`.`KIM_ATTR_DEFN_ID`))) where (`rm`.`MBR_TYP_CD` = 'G') order by `r`.`NMSPC_CD`,`r`.`ROLE_NM`,`g`.`NMSPC_CD`,`g`.`GRP_NM`,`rm`.`ROLE_MBR_ID`,`a`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_role_perm_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_role_perm_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_role_perm_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_role_perm_v` AS select `r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`r`.`ROLE_ID` AS `role_id`,`pt`.`NMSPC_CD` AS `tmpl_nmspc_cd`,`pt`.`NM` AS `tmpl_nm`,`pt`.`PERM_TMPL_ID` AS `PERM_TMPL_ID`,`p`.`NMSPC_CD` AS `perm_nmpsc_cd`,`p`.`NM` AS `perm_nm`,`p`.`PERM_ID` AS `PERM_ID`,`a`.`NM` AS `attr_nm`,`ad`.`ATTR_VAL` AS `attr_val` from (((((`krim_perm_t` `p` left join `krim_perm_tmpl_t` `pt` on((`p`.`PERM_TMPL_ID` = `pt`.`PERM_TMPL_ID`))) left join `krim_perm_attr_data_t` `ad` on((`p`.`PERM_ID` = `ad`.`PERM_ID`))) left join `krim_attr_defn_t` `a` on((`ad`.`KIM_ATTR_DEFN_ID` = `a`.`KIM_ATTR_DEFN_ID`))) left join `krim_role_perm_t` `rp` on((`rp`.`PERM_ID` = `p`.`PERM_ID`))) left join `krim_role_t` `r` on((`rp`.`ROLE_ID` = `r`.`ROLE_ID`))) order by `r`.`NMSPC_CD`,`r`.`ROLE_NM`,`pt`.`NMSPC_CD`,`pt`.`NM`,`p`.`PERM_ID`,`a`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_role_prncpl_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_role_prncpl_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_role_prncpl_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_role_prncpl_v` AS select `r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`r`.`ROLE_ID` AS `ROLE_ID`,`p`.`PRNCPL_NM` AS `PRNCPL_NM`,`p`.`PRNCPL_ID` AS `PRNCPL_ID`,`en`.`FIRST_NM` AS `FIRST_NM`,`en`.`LAST_NM` AS `LAST_NM`,`rm`.`ROLE_MBR_ID` AS `ROLE_MBR_ID`,`ad`.`NM` AS `attr_nm`,`rmad`.`ATTR_VAL` AS `attr_val` from (((((`krim_role_t` `r` left join `krim_role_mbr_t` `rm` on((`r`.`ROLE_ID` = `rm`.`ROLE_ID`))) left join `krim_role_mbr_attr_data_t` `rmad` on((`rm`.`ROLE_MBR_ID` = `rmad`.`ROLE_MBR_ID`))) left join `krim_attr_defn_t` `ad` on((`rmad`.`KIM_ATTR_DEFN_ID` = `ad`.`KIM_ATTR_DEFN_ID`))) left join `krim_prncpl_t` `p` on(((`rm`.`MBR_ID` = `p`.`PRNCPL_ID`) and (`rm`.`MBR_TYP_CD` = 'P')))) left join `krim_entity_nm_t` `en` on((`p`.`ENTITY_ID` = `en`.`ENTITY_ID`))) where (`en`.`DFLT_IND` = 'Y') order by `r`.`NMSPC_CD`,`r`.`ROLE_NM`,`p`.`PRNCPL_NM`,`rm`.`ROLE_MBR_ID`,`ad`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_role_role_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_role_role_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_role_role_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_role_role_v` AS select `r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`r`.`ROLE_ID` AS `role_id`,`mr`.`NMSPC_CD` AS `mbr_role_nmspc_cd`,`mr`.`ROLE_NM` AS `mbr_role_nm`,`mr`.`ROLE_ID` AS `mbr_role_id`,`rm`.`ROLE_MBR_ID` AS `role_mbr_id`,`a`.`NM` AS `attr_nm`,`d`.`ATTR_VAL` AS `attr_val` from ((((`krim_role_mbr_t` `rm` left join `krim_role_t` `r` on((`r`.`ROLE_ID` = `rm`.`ROLE_ID`))) left join `krim_role_t` `mr` on((`mr`.`ROLE_ID` = `rm`.`MBR_ID`))) left join `krim_role_mbr_attr_data_t` `d` on((`d`.`ROLE_MBR_ID` = `rm`.`ROLE_MBR_ID`))) left join `krim_attr_defn_t` `a` on((`a`.`KIM_ATTR_DEFN_ID` = `d`.`KIM_ATTR_DEFN_ID`))) where (`rm`.`MBR_TYP_CD` = 'R') order by `r`.`NMSPC_CD`,`r`.`ROLE_NM`,`mr`.`NMSPC_CD`,`mr`.`ROLE_NM`,`rm`.`ROLE_MBR_ID`,`a`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_role_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_role_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_role_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_role_v` AS select `r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`r`.`ROLE_ID` AS `ROLE_ID`,`t`.`NM` AS `role_typ_nm`,`t`.`SRVC_NM` AS `SRVC_NM`,`t`.`KIM_TYP_ID` AS `KIM_TYP_ID` from (`krim_role_t` `r` join `krim_typ_t` `t`) where ((`t`.`KIM_TYP_ID` = `r`.`KIM_TYP_ID`) and (`r`.`ACTV_IND` = 'Y')) order by `r`.`NMSPC_CD`,`r`.`ROLE_NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_rsp_attr_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_rsp_attr_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_attr_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_rsp_attr_v` AS select `krim_typ_t`.`NM` AS `responsibility_type_name`,`krim_rsp_tmpl_t`.`NM` AS `rsp_TEMPLATE_NAME`,`krim_rsp_t`.`NMSPC_CD` AS `rsp_namespace_code`,`krim_rsp_t`.`NM` AS `rsp_NAME`,`krim_rsp_t`.`RSP_ID` AS `rsp_id`,`krim_attr_defn_t`.`NM` AS `attribute_name`,`krim_rsp_attr_data_t`.`ATTR_VAL` AS `attribute_value` from ((((`krim_rsp_t` join `krim_rsp_attr_data_t` on((`krim_rsp_t`.`RSP_ID` = `krim_rsp_attr_data_t`.`RSP_ID`))) join `krim_attr_defn_t` on((`krim_rsp_attr_data_t`.`KIM_ATTR_DEFN_ID` = `krim_attr_defn_t`.`KIM_ATTR_DEFN_ID`))) join `krim_rsp_tmpl_t` on((`krim_rsp_t`.`RSP_TMPL_ID` = `krim_rsp_tmpl_t`.`RSP_TMPL_ID`))) join `krim_typ_t` on((`krim_rsp_tmpl_t`.`KIM_TYP_ID` = `krim_typ_t`.`KIM_TYP_ID`))) order by `krim_rsp_tmpl_t`.`NM`,`krim_rsp_t`.`NM`,`krim_attr_defn_t`.`NM` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_rsp_role_actn_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_rsp_role_actn_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_role_actn_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_rsp_role_actn_v` AS select `rsp`.`NMSPC_CD` AS `rsp_nmspc_cd`,`rsp`.`RSP_ID` AS `rsp_id`,`r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`rr`.`ROLE_ID` AS `ROLE_ID`,`rm`.`MBR_ID` AS `MBR_ID`,`rm`.`MBR_TYP_CD` AS `MBR_TYP_CD`,`rm`.`ROLE_MBR_ID` AS `ROLE_MBR_ID`,`actn`.`ACTN_TYP_CD` AS `ACTN_TYP_CD`,`actn`.`ACTN_PLCY_CD` AS `ACTN_PLCY_CD`,`actn`.`FRC_ACTN` AS `FRC_ACTN`,`actn`.`PRIORITY_NBR` AS `PRIORITY_NBR` from (((((`krim_rsp_t` `rsp` left join `krim_rsp_tmpl_t` `rspt` on((`rsp`.`RSP_TMPL_ID` = `rspt`.`RSP_TMPL_ID`))) left join `krim_role_rsp_t` `rr` on((`rr`.`RSP_ID` = `rsp`.`RSP_ID`))) left join `krim_role_mbr_t` `rm` on((`rm`.`ROLE_ID` = `rr`.`ROLE_ID`))) left join `krim_role_rsp_actn_t` `actn` on(((`actn`.`ROLE_RSP_ID` = `rr`.`ROLE_RSP_ID`) and ((`actn`.`ROLE_MBR_ID` = `rm`.`ROLE_MBR_ID`) or (`actn`.`ROLE_MBR_ID` = '*'))))) left join `krim_role_t` `r` on((`rr`.`ROLE_ID` = `r`.`ROLE_ID`))) order by `rsp`.`NMSPC_CD`,`rsp`.`RSP_ID`,`rr`.`ROLE_ID`,`rm`.`ROLE_MBR_ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `krim_rsp_role_v`
--

/*!50001 DROP TABLE IF EXISTS `krim_rsp_role_v`*/;
/*!50001 DROP VIEW IF EXISTS `krim_rsp_role_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`krtt`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `krim_rsp_role_v` AS select `rspt`.`NMSPC_CD` AS `rsp_tmpl_nmspc_cd`,`rspt`.`NM` AS `rsp_tmpl_nm`,`rsp`.`NMSPC_CD` AS `rsp_nmspc_cd`,`rsp`.`NM` AS `rsp_nm`,`rsp`.`RSP_ID` AS `rsp_id`,`a`.`NM` AS `attr_nm`,`d`.`ATTR_VAL` AS `attr_val`,`r`.`NMSPC_CD` AS `NMSPC_CD`,`r`.`ROLE_NM` AS `ROLE_NM`,`rr`.`ROLE_ID` AS `ROLE_ID` from (((((`krim_rsp_t` `rsp` left join `krim_rsp_tmpl_t` `rspt` on((`rsp`.`RSP_TMPL_ID` = `rspt`.`RSP_TMPL_ID`))) left join `krim_rsp_attr_data_t` `d` on((`rsp`.`RSP_ID` = `d`.`RSP_ID`))) left join `krim_attr_defn_t` `a` on((`d`.`KIM_ATTR_DEFN_ID` = `a`.`KIM_ATTR_DEFN_ID`))) left join `krim_role_rsp_t` `rr` on((`rr`.`RSP_ID` = `rsp`.`RSP_ID`))) left join `krim_role_t` `r` on((`rr`.`ROLE_ID` = `r`.`ROLE_ID`))) order by `rspt`.`NMSPC_CD`,`rspt`.`NM`,`rsp`.`NMSPC_CD`,`rsp`.`NM`,`rsp`.`RSP_ID`,`a`.`NM`,`d`.`ATTR_VAL` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-09-08  9:01:13
