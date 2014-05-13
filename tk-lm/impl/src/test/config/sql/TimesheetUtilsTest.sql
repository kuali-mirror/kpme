--
-- Copyright 2004-2014 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= 5000;
delete from lm_leave_plan_t where lm_leave_plan_id >= 80000;
delete from hr_principal_attributes_t where hr_principal_attribute_id >= 5000;
delete from hr_job_t where hr_job_id >= 5000;
delete from lm_leave_document_header_t where document_id >= 5000;
delete from hr_earn_code_t where hr_earn_code_id >= 5000;
delete from hr_calendar_entries_t where hr_calendar_entry_id >= 5000;
delete from tk_document_header_t where document_id >= 5000;
delete from lm_leave_block_t;


-- setup leave plan, principal hr attributes, leave eligible jobs, leave calendar.
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('80000', 'testLP', 'Test Leave Plan', '02/01', '2011-02-01', '', '1', 'Y', '2011-02-06 11:59:46', '80');

insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('5001', 'testUser2', 'BWS-LM', 'testLP', '2011-03-10', 'Y', 'Y', null, '2011-03-10', now(), uuid(), '1', 'Y', 'BWS-CAL');


insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `GRP_KEY_CD`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5000', 'testUser1', '19', '2012-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'IU-IN', '40.00', 'BW', 'Y',  'Y', 'N', 'Y', null);
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `GRP_KEY_CD`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5001', 'testUser2', '19', '2010-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'IU-IN', '40.00', 'BW', 'Y',  'Y', 'N', 'Y', 'NE');


INSERT INTO lm_leave_document_header_t (`document_id`,`principal_id`,`begin_date`,`end_date`,`document_status`,`obj_id`,`ver_nbr`) values ('5001', 'testUser1', '2013-01-01 00:00:00','2013-02-01 00:00:00', 'S', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E98', '1');


insert into hr_calendar_entries_t (`hr_calendar_entry_id`,`hr_calendar_id`,`calendar_name`,`begin_period_date`,`end_period_date`) values ('5000','3','BWS-LM','2012-11-01 00:00:00','2012-12-01 00:00:00');
insert into hr_calendar_entries_t (`hr_calendar_entry_id`,`hr_calendar_id`,`calendar_name`,`begin_period_date`,`end_period_date`) values ('5001','3','BWS-LM','2012-12-01 00:00:00','2013-01-01 00:00:00');
insert into hr_calendar_entries_t (`hr_calendar_entry_id`,`hr_calendar_id`,`calendar_name`,`begin_period_date`,`end_period_date`) values ('5002','3','BWS-LM','2013-01-01 00:00:00','2013-02-01 00:00:00');


insert into tk_document_header_t (`document_id`, `principal_id`, `pay_end_dt`, `document_status`, `pay_begin_dt`, `obj_id`, `ver_nbr`) values ('5001', 'testUser2', '2012-01-08 00:00:00', 'I', '2011-12-25 00:00:00', NULL, '1');

-- Calendar entries defined in src/test/config/db/test/hr_calendar_entries_t.csv

insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5001', 'EC1', 'test', '2011-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'od-xfer', '1.5', '1.5', 'Hours', 'testLP', 'A', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5002', 'EC2', 'test', '2011-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'ye-xfer', '1.5', '1.5', 'Hours', 'testLP', 'A', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');

insert into tk_assignment_t (`TK_ASSIGNMENT_ID`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `WORK_AREA`, `TASK`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PRIMARY_ASSIGN`, `USER_PRINCIPAL_ID`, `GRP_KEY_CD`) values ('5047', 'testUser2', '0', '2012-01-01 00:00:00', '1200', '0', NULL, '1', 'Y', '1970-01-01 00:00:00', 'Y',  'admin', 'ISU-IA');

insert into tk_time_block_t (`tk_time_block_id`, `document_id`, `job_number`, `work_area`, `task`, `earn_code`, `begin_ts`, `end_ts`, `clock_log_created`, `hours`, `amount`, `user_principal_id`, `timestamp`, `obj_id`, `ver_nbr`, `clock_log_begin_id`, `clock_log_end_id`, `principal_id`, `ovt_pref`, `lunch_deleted`, `GRP_KEY_CD`) values('5000', '5001', '19', '20', '30', 'EC1', '2012-01-07 23:00:00', '2012-01-08 01:00:00', 'N', 2, null, '10001', now(), 'B2991ADA-E866-F28C-7E95-A897AC377D0D', '1', null, null, 'testUser2', null, 'Y', 'IU-IN');
insert into tk_time_block_t (`tk_time_block_id`, `document_id`, `job_number`, `work_area`, `task`, `earn_code`, `begin_ts`, `end_ts`, `clock_log_created`, `hours`, `amount`, `user_principal_id`, `timestamp`, `obj_id`, `ver_nbr`, `clock_log_begin_id`, `clock_log_end_id`, `principal_id`, `ovt_pref`, `lunch_deleted`, `GRP_KEY_CD`) values('5001', '5001', '19', '20', '30', 'EC1', '2012-01-07 19:00:00', '2012-01-07 20:00:00', 'N', 1, null, '10001', now(), 'B2991ADA-E866-F28C-7E95-A897AC377D0E', '1', null, null, 'testUser2', null, 'Y', 'IU-IN');


INSERT INTO TK_SHIFT_DIFFERENTIAL_RL_T (TK_SHIFT_DIFF_RL_ID,LOCATION,HR_SAL_GROUP,PAY_GRADE,EARN_CODE,BEGIN_TS,END_TS,MIN_HRS,PY_CALENDAR_GROUP,FROM_EARN_GROUP,MAX_GAP,SUN,MON,TUE,WED,THU,FRI,SAT,EFFDT,TIMESTAMP,USER_PRINCIPAL_ID,ACTIVE) VALUES ('1000','IN','SD1','SD1','EC2','10:00:00','14:00:00',0,'%','EC1',75,'Y','Y','Y','Y','Y','Y','Y','2010-01-01','2011-10-14 14:08:20','admin','Y');
INSERT INTO TK_SHIFT_DIFFERENTIAL_RL_T (TK_SHIFT_DIFF_RL_ID,LOCATION,HR_SAL_GROUP,PAY_GRADE,EARN_CODE,BEGIN_TS,END_TS,MIN_HRS,PY_CALENDAR_GROUP,FROM_EARN_GROUP,MAX_GAP,SUN,MON,TUE,WED,THU,FRI,SAT,EFFDT,TIMESTAMP,USER_PRINCIPAL_ID,ACTIVE) VALUES ('1001','IN','SD1','SD1','EC2','18:00:00','21:00:00',0,'%','EC1',75,'Y','Y','Y','Y','Y','Y','Y','2010-01-01','2011-10-14 14:08:20','admin','Y');

/* leave blocks */
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('5000', '2012-01-01', 'Send for Approval', 'testUser2', 'testLC','3000', 'testAC', '8', '5001', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  'A', 'LC');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type` ) values('5001', '2012-01-01', 'Send for Approval 1', 'testUser2', 'testLC','3000', 'testAC', '8', '5001', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  'A', 'LC');

