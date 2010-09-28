CREATE TABLE `tk_roles_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin

CREATE TABLE `tk_roles_t` (
  `tk_roles_id` bigint(19) NOT NULL,
  `principal_id` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `role_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `user_principal_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `work_area` bigint(20) DEFAULT NULL,
  `dept` varchar(21) COLLATE utf8_bin DEFAULT NULL,
  `effdt` date DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`tk_roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin
