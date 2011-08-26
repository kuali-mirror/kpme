DROP TABLE IF EXISTS `hr_position_id_s`;

CREATE TABLE `hr_position_id_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `hr_position_s`;

CREATE TABLE `hr_position_s` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `hr_position_t`;

CREATE TABLE `hr_position_t` (
  `HR_POSITION_ID` bigint(20) NOT NULL,
  `POSITION_NBR` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(40) NOT NULL,
  `EFFDT` date NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OBJ_ID` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `VER_NBR` bigint(20) NOT NULL DEFAULT '1',
  `ACTIVE` varchar(1) COLLATE utf8_bin DEFAULT 'N',
  PRIMARY KEY (`HR_POSITION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table hr_job_t add column position_nbr bigint(20) NULL;

alter table hr_roles_t modify principal_id varchar(40) NULL;

alter table hr_roles_t add column position_nbr bigint(20) NULL;