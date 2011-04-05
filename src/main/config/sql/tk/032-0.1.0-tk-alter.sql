CREATE TABLE `tk_missed_punch_t` (
  `tk_missed_punch_id` bigint(20) NOT NULL,
  `principal_id` varchar(40) NOT NULL,
  `clock_action` varchar(20) NOT NULL,
  `action_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action_time` time NOT NULL DEFAULT '00:00:00',
  `timesheet_doc_id` varchar(14) NOT NULL,
  `document_id` varchar(14) DEFAULT NULL,
  `document_status` varchar(1) DEFAULT NULL,
  `tk_clock_log_id` bigint(20) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tk_missed_punch_id`)
) ENGINE=InnoDB;
