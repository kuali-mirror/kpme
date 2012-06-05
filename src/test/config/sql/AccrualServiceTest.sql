delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '8000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '5000';
delete from lm_leave_code_t where lm_leave_code_id >= '5000';
delete from hr_principal_attributes_t where principal_id = 'testUser';
delete from hr_job_t where hr_job_id >= '5000';
delete from lm_sys_schd_timeoff_t where lm_sys_schd_timeoff_id = '5000';
delete from lm_leave_block_t where principal_id = 'testUser';
delete from lm_leave_block_hist_t where principal_id = 'testUser';

insert into lm_leave_plan_t values ('8000', 'testLP', 'Test Leave Plan', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "1");
insert into hr_principal_attributes_t values('testUser', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM', 'Y', 'Y');
insert into lm_accrual_category_t values('5000', 'testAC', 'testLP', 'test', 'M', '40', '2012-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '1.5', 'testLC', 'Y');
insert into lm_accrual_category_rules_t values ('5000', 'M', 0, 12, 16, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '5000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-02-03 12:10:23', 'N');
insert into lm_leave_code_t values ('5000',	'testLP', 'Y', 'testAC', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-02 11:38:04', 'N', 'N');

# two not eligible for leave jobs and one eligible one
insert into hr_job_t values ('5000', 'testUser', '1', '2012-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'Y',  'Y', 'N', 'N');
insert into hr_job_t values ('5001', 'testUser', '2', '2012-02-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'Y',  'Y', 'N', 'N');
insert into hr_job_t values ('5002', 'testUser', '3', '2012-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'Y',  'Y', 'N', 'Y');

# set up a system scheduled time off on 04/10/2012
insert into lm_accrual_category_t values('5001', 'holAC', 'testLP', 'test', 'M', '40', '2012-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y', now(), '1.5', 'testLC', 'Y');
insert into lm_leave_code_t values ('5001',	'testLP', 'Y', 'holAC', 'TC1', 'holLC', 'holLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-02 11:38:04', 'N', 'N');
insert into lm_sys_schd_timeoff_t values ('5000', 'testLP', 'holAC', 'holLC', '2012-04-10', NULL, 'testLocation','testSSTO', '8', null, null, null, null, 'testH', '2012-03-01', uuid(), '1', 'Y', now());

# add an inactive entry for job 3 and add a new job 4 for employee status change
# employee changed status on 04/01, fte is changed from 1 to 0.5
insert into hr_job_t values ('5003', 'testUser', '3', '2012-04-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'N',  'Y', 'N', 'Y');
insert into hr_job_t values ('5004', 'testUser', '4', '2012-04-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '20.00', 'BW', 'Y',  'Y', 'N', 'Y');


