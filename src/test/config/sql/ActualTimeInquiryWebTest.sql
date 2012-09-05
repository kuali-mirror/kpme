delete from hr_calendar_entries_t where hr_calendar_entry_id = '5000';
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date, initiate_date, initiate_time, end_pay_period_date, end_pay_period_time, employee_approval_date, employee_approval_time,supervisor_approval_date,supervisor_approval_time ) values ('5000', '2', 'BWS-CAL', now(), ADDDATE(now(),14), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

delete from tk_document_header_t where document_id = '2';
insert into tk_document_header_t values ('2', 'admin', ADDDATE(now(),14), 'I', now(), NULL, '1');

update tk_grace_period_rl_t set active = 'Y' where tk_grace_period_rule_id = '1';
