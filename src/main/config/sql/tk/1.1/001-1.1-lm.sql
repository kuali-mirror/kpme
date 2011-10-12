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
) ENGINE=MyISAM AUTO_INCREMENT=2093 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lm_leave_plan_s`
--

LOCK TABLES `lm_leave_plan_s` WRITE;
/*!40000 ALTER TABLE `lm_leave_plan_s` DISABLE KEYS */;
/*!40000 ALTER TABLE `lm_leave_plan_s` ENABLE KEYS */;
UNLOCK TABLES;