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