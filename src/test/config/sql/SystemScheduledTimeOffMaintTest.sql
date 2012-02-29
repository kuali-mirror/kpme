delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '1000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3000';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from hr_principal_attributes_t where principal_id = '111';

insert into lm_leave_code_t values ('1000',	'testLP', 'Y', 'testAC', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04', 'N');
insert into lm_sys_schd_timeoff_t values ('3000', 'testLP', 'testAC', 'testLC', '2012-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, null, 'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t values ('3001', 'InactiveLP', 'testAC', 'testLC', '2012-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, null, 'testH', '2012-03-01', uuid(), '1', 'N', now());
insert into lm_accrual_category_t values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5');
insert into hr_principal_attributes_t values('111', 'BWS-CAL', 'testLP','2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);