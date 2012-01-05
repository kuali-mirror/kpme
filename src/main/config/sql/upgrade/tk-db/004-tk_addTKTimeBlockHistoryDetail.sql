
DROP TABLE IF EXISTS `tk_time_block_hist_detail_s`;
CREATE TABLE `tk_time_block_hist_detail_s` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=5290 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tk_time_block_hist_detail_t`;
CREATE TABLE `tk_time_block_hist_detail_t` (
  `TK_TIME_BLOCK_HIST_DETAIL_ID` bigint(20) NOT NULL,
  `TK_TIME_BLOCK_HIST_ID` bigint(20) NOT NULL,
  `EARN_CODE` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `HOURS` decimal(5,2) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT '0.00',
  `OBJ_ID` varchar(36) COLLATE utf8_bin NOT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`TK_TIME_BLOCK_HIST_DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;