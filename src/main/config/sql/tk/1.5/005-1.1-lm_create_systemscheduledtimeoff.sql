
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