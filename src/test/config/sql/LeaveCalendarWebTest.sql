delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '1000';

update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into lm_leave_plan_t (lm_leave_plan_id, LEAVE_PLAN, DESCR, CAL_YEAR_START, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP, PLANNING_MONTHS) values ('5555', 'testLP', 'Testing Leave Plan Months', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "1");

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003', '10004', '10005', '10006');
insert into hr_calendar_entries_t values ('10000', '2', 'BWS-CAL', '2012-04-01 00:00:00', '2012-04-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t values ('10001', '2', 'BWS-CAL', '2012-04-15 00:00:00', '2012-05-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t values ('10002', '2', 'BWS-CAL', '2012-05-01 00:00:00', '2012-05-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

delete from lm_leave_document_header_t where document_id in ('1000', '1001', '1002')
INSERT INTO lm_leave_document_header_t values ('1000', 'admin', '2012-03-01 00:00:00','2012-03-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');
INSERT INTO lm_leave_document_header_t values ('1001', 'admin', '2012-03-15 00:00:00','2012-04-01 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E96', '1');
INSERT INTO lm_leave_document_header_t values ('1002', 'admin', '2012-04-01 00:00:00','2012-04-15 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E95', '1');

insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date, initiate_date, initiate_time, end_pay_period_date, end_pay_period_time, employee_approval_date, employee_approval_time,supervisor_approval_date,supervisor_approval_time ) values ('10003', '2', 'BWS-CAL', ADDDATE(now(),-1), ADDDATE(now(),13), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date, initiate_date, initiate_time, end_pay_period_date, end_pay_period_time, employee_approval_date, employee_approval_time,supervisor_approval_date,supervisor_approval_time ) values ('10004', '3', 'BWS-LM', ADDDATE(now(),-1), ADDDATE(now(),13), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date, initiate_date, initiate_time, end_pay_period_date, end_pay_period_time, employee_approval_date, employee_approval_time,supervisor_approval_date,supervisor_approval_time ) values ('10005', '2', 'BWS-CAL', ADDDATE(now(),13), ADDDATE(now(),27), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date, initiate_date, initiate_time, end_pay_period_date, end_pay_period_time, employee_approval_date, employee_approval_time,supervisor_approval_date,supervisor_approval_time ) values ('10006', '3', 'BWS-LM', ADDDATE(now(),13), ADDDATE(now(),27), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
# delete from tk_document_header_t where document_id = '2';
# insert into tk_document_header_t values ('2', 'admin', ADDDATE(now(),14), 'I', now(), NULL, '1');