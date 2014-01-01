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

delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from hr_earn_code_group_t where hr_earn_code_group_id >= '5000';
delete from hr_earn_code_group_def_t where hr_earn_code_group_def_id >= '5000';
delete from lm_employee_override_t where lm_employee_override_id >= '6000';
delete from lm_leave_plan_t where lm_leave_plan_id >= 8000;

insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('5000', 'testAC', 'testLP', 'test', 'M', '40', '2012-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', null, 'Y', 'Y',now(), '0.5', 'EC4', 'Y');
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('5001', 'testAC2', 'testLP', 'test', 'M', '40', '2012-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', null, 'Y', 'Y',now(), '0.5', 'EC4', 'Y');
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('8000', 'testLP', 'Test Leave Plan', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:55', '12');

# does NOT allow negative accrual balance
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5000', 'EC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
# allows negative accrual balance
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5001', 'EC1', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'Y', 'I', 'N');

# test max usage rules for case of editing leave block with a change in earn code.
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5002', 'EC2', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC2', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'Y', 'I', 'N');

#validateLeaveUnderMaxUsageLimitWithEmployeeOverride
insert into lm_employee_override_t (`lm_employee_override_id`,`principal_id`,`accrual_cat`,`leave_plan`,`override_type`,`override_value`,`description`,`active`,`timestamp`,`obj_id`,`ver_nbr`,`effdt`) values('6000','override20','testAC','testLP','MU','20',null,'Y',now(),null,'1','2012-02-01');
#validateLeaveNoUsageLimitWithEmployeeOverride
insert into lm_employee_override_t (`lm_employee_override_id`,`principal_id`,`accrual_cat`,`leave_plan`,`override_type`,`override_value`,`description`,`active`,`timestamp`,`obj_id`,`ver_nbr`,`effdt`) values('6001','nolimit','testAC','testLP','MU',null,null,'Y',now(),null,'1','2012-02-01');

# testGetWarningTextForLeaveBlocks
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5003', 'ECA', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5004', 'ECB', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'N', 'test', null, 'N', 'I', 'N');
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('5005', 'ECC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'N', 'test', null, 'N', 'I', 'N');

insert into hr_earn_code_group_t (`hr_earn_code_group_id`, `earn_code_group`, `descr`, `show_summary`, `effdt`, `active`, `timestamp`,`warning_text` ) values('5000', 'group1', 'group1', 'Y','2012-02-01', 'Y', now(), 'Test Message');
insert into hr_earn_code_group_t (`hr_earn_code_group_id`, `earn_code_group`, `descr`, `show_summary`, `effdt`, `active`, `timestamp`,`warning_text` ) values('5001', 'group2', 'group2', 'Y','2012-02-01', 'Y', now(), 'Test Message1');
insert into hr_earn_code_group_t (`hr_earn_code_group_id`, `earn_code_group`, `descr`, `show_summary`, `effdt`, `active`, `timestamp`,`warning_text` ) values('5002', 'group3', 'group3', 'Y','2012-02-01', 'Y', now(), '');

insert into hr_earn_code_group_def_t (`hr_earn_code_group_def_id`, `hr_earn_code_group_id`,`earn_code`) values ('5000', '5000', 'ECA');
insert into hr_earn_code_group_def_t (`hr_earn_code_group_def_id`, `hr_earn_code_group_id`,`earn_code`) values ('5001', '5001', 'ECB');