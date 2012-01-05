
DROP TABLE IF EXISTS `tk_ip_address_s`;
DROP TABLE IF EXISTS `tk_clock_location_rl_ip_address_s`;
CREATE TABLE `tk_clock_location_rl_ip_address_s` (
  `ID` BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1101 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tk_ip_address_t`;
DROP TABLE IF EXISTS `tk_clok_location_rl_ip_address_t`;
CREATE TABLE `tk_clok_location_rl_ip_address_t` (
  `tk_clock_loc_rule_ip_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `tk_clock_loc_rule_id` VARCHAR(20) NOT NULL,
  `ip_address` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`tk_clock_loc_rule_ip_id`) )
   ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;