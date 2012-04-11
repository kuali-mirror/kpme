INSERT INTO `hr_calendar_entries_t` (`hr_calendar_entry_id`,`hr_calendar_id`, `calendar_name`, `begin_period_date`,`end_period_date`,`initiate_date`,`initiate_time`,`end_pay_period_date`,`end_pay_period_time`,`employee_approval_date`,`employee_approval_time`,`supervisor_approval_date`,`supervisor_approval_time`) VALUES('59', '2', 'BWS-CAL', '2012-04-01 00:00:00', '2012-04-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';


INSERT INTO lm_leave_document_header_t values ('1000', 'admin', '2012-03-01 00:00:00','2012-03-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');
INSERT INTO lm_leave_document_header_t values ('1001', 'admin', '2012-03-15 00:00:00','2012-04-01 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E96', '1');
INSERT INTO lm_leave_document_header_t values ('1002', 'admin', '2012-04-01 00:00:00','2012-04-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E95', '1');