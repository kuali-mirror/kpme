ALTER TABLE `tk_work_area_t` DROP PRIMARY KEY;

ALTER TABLE `tk_work_area_t` modify `work_area` bigint(10) NOT NULL auto_increment, ADD PRIMARY KEY (`work_area`);

DROP TABLE IF EXISTS `tk_wa_maint_doc_t`;
CREATE TABLE `tk_wa_maint_doc_t` (
  `doc_hdr_id` varchar(50) COLLATE utf8_bin PRIMARY KEY NOT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `obj_id` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `doc_cntnt` LONGTEXT);