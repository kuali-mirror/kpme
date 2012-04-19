delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '1000';

update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into lm_leave_plan_t values ('5555', 'testLP', 'Testing Leave Plan Months', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "1");


insert into hr_calendar_entries_t values ('59', '2', 'BWS-CAL', '2012-04-01 00:00:00', '2012-04-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t values ('60', '2', 'BWS-CAL', '2012-04-15 00:00:00', '2012-05-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t values ('61', '2', 'BWS-CAL', '2012-05-01 00:00:00', '2012-05-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO lm_leave_document_header_t values ('1000', 'admin', '2012-03-01 00:00:00','2012-03-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');
INSERT INTO lm_leave_document_header_t values ('1001', 'admin', '2012-03-15 00:00:00','2012-04-01 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E96', '1');
INSERT INTO lm_leave_document_header_t values ('1002', 'admin', '2012-04-01 00:00:00','2012-04-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E95', '1');