delete from hr_principal_attributes_t where principal_id = '111';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_leave_plan_t where lm_leave_plan_id = '3000';
delete from lm_leave_code_t where lm_leave_code_id = '3000';
delete from lm_leave_adjustment_t where lm_leave_adjustment_id = '3000';

insert into hr_principal_attributes_t values('111', 'BWS-CAL', 'testLP','2010-01-01', 'Y','Y', null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into lm_accrual_category_t values('3000', 'AC1', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT');	
insert into lm_leave_plan_t values ('3000',	'IU-SM', 'staff monthly', '01/01', '2011-01-01', 'F1248B7E-D13D-F3EE-20FF-9E428992F92C', '1', 'Y', '2012-01-07 11:38:02', null);
insert into lm_leave_code_t values ('3000',	'IU-SM', 'Y', 'AC1', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04', 'N');
insert into lm_leave_adjustment_t values (	'3000',	'10001',	'AC1',	'IU-SM',	'testLC',	10,	'test 1239',	'2012-01-31 11:03:11',	'8B5039D6-5775-87AA-9012-1A6EDEB0FD16',	1);