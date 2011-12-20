
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