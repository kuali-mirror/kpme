-- MySQL dump 10.13  Distrib 5.1.48, for Win32 (ia32)
--
-- Host: localhost    Database: kfs3_test3
-- ------------------------------------------------------
-- Server version	5.1.48-community

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
  CONSTRAINT `CA_ACCOUNT_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`),
  CONSTRAINT `CA_ACCOUNT_TR2` FOREIGN KEY (`FIN_COA_CD`, `ORG_CD`) REFERENCES `ca_org_t` (`FIN_COA_CD`, `ORG_CD`)
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
