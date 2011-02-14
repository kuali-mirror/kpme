DROP TABLE IF EXISTS `hr_location_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_location_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_location_t`
--

DROP TABLE IF EXISTS `hr_location_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_location_t` (
  `hr_location_id` bigint(19) NOT NULL,
  `location` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(40) COLLATE utf8_bin NOT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `hr_pay_grade_s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_pay_grade_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hr_pay_grade_t`
--

DROP TABLE IF EXISTS `hr_pay_grade_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_pay_grade_t` (
  `hr_pay_grade_id` bigint(19) NOT NULL,
  `pay_grade` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(40) COLLATE utf8_bin NOT NULL,
  `effdt` date NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`hr_pay_grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

