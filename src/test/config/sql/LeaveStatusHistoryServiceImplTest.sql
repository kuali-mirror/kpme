/** create system scheduled time off data */
delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '1000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3001';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_leave_block_t where lm_leave_block_id = '1000';
delete from lm_leave_block_t where lm_leave_block_id = '1001';
delete from lm_leave_block_hist_t where lm_leave_block_hist_id = '1000';

insert into lm_leave_code_t values ('1000',	'testLP', 'Y', 'testAC', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04', 'N', 'N');
insert into lm_sys_schd_timeoff_t values ('3000', 'testLP', 'testAC', 'testLC', '2012-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, null, 'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t values ('3001', 'InactiveLP', 'testAC', 'testLC', '2012-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, null, 'testH', '2012-03-01', uuid(), '1', 'N', now());
insert into lm_accrual_category_t values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');

/** Leave block */
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `leave_code` ,`lm_leave_code_id` ,`lm_sys_schd_timeoff_id` ,`lm_accrual_category_id` ,`leave_amount`, `apply_to_ytd_used`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `tk_assignment_id`, `request_status` ) values('1000', '2012-03-01', 'Test Description', 'admin', 'testLC', '1000','3000', '3000', '8', 'Appy to ytd', '12546', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  '1100', 'P');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `leave_code` ,`lm_leave_code_id` ,`lm_sys_schd_timeoff_id` ,`lm_accrual_category_id` ,`leave_amount`, `apply_to_ytd_used`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `tk_assignment_id`, `request_status` ) values('1001', '2012-03-02', 'Test Description', 'admin', 'testLC', '1000','3000', '3000', '8', 'Appy to ytd', '12546', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  '1100', 'P');

/** Leave Block history */
insert into lm_leave_block_hist_t values ('1000','1000', '2012-03-01', 'Test Description', 'admin', 'testLC', '1000', 3000, 3000, 1100, '8', 'Apply to ytd', '12546', null, now(), null, null, 0,'A','P','A','1','B2991ADA-E866-F28C-7E95-A897AC377D0C' );

/** Leave status history */
insert into lm_leave_status_hist_t values ('1000', '1000', 'P', now(), 'admin', null, 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1');