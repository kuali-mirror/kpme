#delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '1000';
#insert into lm_leave_code_t (lm_leave_code_id, LEAVE_PLAN, ELIGIBLE_FOR_ACC, ACCRUAL_CAT, EARN_CODE, LEAVE_CODE, DISP_NAME, UNIT_OF_TIME, FRACT_TIME_ALLOWD, ROUND_OPT, ALLOW_SCHD_LEAVE, FMLA, WORKMANS_COMP, DEF_TIME, EMPLOYEE, APPROVER, DEPT_ADMIN, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP, ALLOW_NEGATIVE_ACC_BALANCE, AFFECT_PAY) values ('1000',	'testLP', 'Y', 'testAC', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04', 'N', 'N');

delete from hr_earn_code_t where HR_EARN_CODE_ID = '1000';
delete from hr_earn_code_t where HR_EARN_CODE_ID = '1001';
insert into hr_earn_code_t values('1000', 'EC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours');
insert into hr_earn_code_t values('1001', 'TST', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours');
delete from hr_location_t where hr_location_id = '1000';
INSERT INTO hr_location_t(hr_location_id, location, description, effdt, timestamp, timezone, active, user_principal_id) VALUES('1000', 'CST', 'Central Standard Time', '2010-01-01', '2012-08-21 09:44:28.0', 'America/Chicago', 'Y', NULL);
delete from hr_principal_attributes_t where principal_id = '111';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID >= '3000';

insert into lm_sys_schd_timeoff_t values ('3000', 'testLP',     'testAC',  'EC',  '2012-01-01', NULL, 'testLocation','testSSTO', '3', null,   null, null, null, 'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t values ('3001', 'InactiveLP', 'testAC',  'EC',  '2012-01-01', NULL, 'testLocation','testSSTO', '3', null,   null, null, null, 'testH', '2012-03-01', uuid(), '1', 'N', now());
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3002','TEST','TEST-AC','TLC','2013-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC',NULL,'N','2012-02-08','8751782B-193F-0201-99E5-375FA84D7B78',2,'N','2012-02-08 21:45:20');
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3007','TEST','TEST-AC','IND','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC',NULL,'N','2012-02-08','64359087-1F4C-3490-E0E8-9513DC9BEDC1',3,'N','2012-02-08 21:47:16');
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3008','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC',NULL,'N','2012-02-08','615EA427-1BC0-60DB-E85E-66CD03923BA9',3,'Y','2012-02-08 21:47:17');
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3003','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC',NULL,'N','2012-02-09','162120C5-ADEC-239E-9249-BB727CCFA362',4,'N','2012-02-08 21:58:37');
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3004','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC',NULL,'N','2012-02-09','F4C4F1C2-62B7-4FB0-4B2D-B447E1F42D44',4,'N','2012-02-08 21:58:38');
INSERT INTO lm_sys_schd_timeoff_t VALUES ('3005','test','testAC','TLC','2012-02-24',NULL,'CST','test1363',3,NULL,NULL,NULL,NULL,'N','2012-02-24','A928B2CB-EB49-7ED8-9E88-813E857D2B79',1,'Y','2012-02-22 15:41:25');



delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from hr_principal_attributes_t where principal_id = '111';
insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values('2000','111', 'BWS-CAL', 'testLP','2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);