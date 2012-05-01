delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3000';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
insert into lm_leave_code_t values ('3000',	'IU-SM', 'Y', 'AC1', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04','N', 'N');
insert into lm_accrual_category_t values('3000', 'AC1', 'IU-SM', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
insert into lm_leave_plan_t values ('3000',	'IU-SM', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2012-02-09 11:38:04', 'Feb');

