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

delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from hr_earn_code_group_t where hr_earn_code_group_id >= '5000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';

delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID='5000';
delete from tk_time_block_t where tk_time_block_id='5000';
delete from lm_leave_block_t where lm_leave_block_id='5000';
delete from hr_principal_attributes_t where HR_PRINCIPAL_ATTRIBUTE_ID='3';


insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5000', 'ECA', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'H', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5002', 'ECB', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'noAC', '1.5', '1.5', 'H', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'N', 'test', null, 'N', 'I', 'N');
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values ('5000', 'testAC', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');

INSERT INTO lm_leave_plan_t (`LM_LEAVE_PLAN_ID`,`LEAVE_PLAN`,`DESCR`,`CAL_YEAR_START`,`EFFDT`,`VER_NBR`,`ACTIVE`,`TIMESTAMP`,`PLANNING_MONTHS`,`BATCH_CARRY_OVER_START_DATE`,`BATCH_CARRY_OVER_START_TIME`) VALUES ('5000', 'testLP', 'test Leave Plan','01/01','2010-01-01', 1,'Y','1970-01-01',12, null,null);
insert into tk_time_block_t (`tk_time_block_id`, `document_id`, `job_number`, `work_area`, `task`, `earn_code`, `begin_ts`, `end_ts`, `clock_log_created`, `hours`, `amount`, `user_principal_id`, `timestamp`, `obj_id`, `ver_nbr`, `clock_log_begin_id`, `clock_log_end_id`, `principal_id`, `ovt_pref`, `lunch_deleted`) values('5000', '10029', '10', '20', '30', 'EC', now(), now(), 'Y', null, null, '10001', now(), 'B2991ADA-E866-F28C-7E95-A897AC377D0D', '1', null, null, '10002', null, 'Y');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('5000', '2012-04-26', 'Send for Approval', 'testUser7', 'EC6', NULL, 'testAC9', '2', null, 'N', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  'P', 'AS');

INSERT INTO hr_principal_attributes_t (`PRINCIPAL_ID`,`EFFDT`,`HR_PRINCIPAL_ATTRIBUTE_ID`,`PAY_CALENDAR`,`SERVICE_DATE`,`FMLA_ELIGIBLE`,`WORKERS_ELIGIBLE`,`LEAVE_PLAN`,`LEAVE_CALENDAR`,`USER_PRINCIPAL_ID`,`obj_id`) VALUES ('testPrincial','2010-01-01','3','BWS-CAL','2010-01-01','Y','Y','testLP','BWS-LM','testPrincial', uuid());





