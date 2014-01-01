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

delete from hr_principal_attributes_t where hr_principal_attribute_id in ('10000');
delete from tk_assignment_t where tk_assignment_id in ('10000', '10001');
delete from hr_job_t where hr_job_id in ('10000', '10001');
delete from hr_paytype_t where hr_paytype_id in ('10000', '10001');
delete from hr_earn_code_t where hr_earn_code_id in ('10000', '10001');

# for testGetEarnCodesForDisplay
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('10000', 'testuser', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM');
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('10000', 'testuser', '1234', '2010-01-01', '1', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('10001', 'testuser', '5678', '2010-01-01', '1', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('10000', 'testuser', '1234', '2012-01-01', 'TEST-DEPT', 'SD1', 'SD1', '2012-01-01 11:00:00', uuid(), '1', '0.000000', 'SD1', '40.00', 'EC1', 'Y',  'Y', 'N', 'N', null);
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('10001', 'testuser', '5678', '2012-01-01', 'TEST-DEPT', 'SD1', 'SD1', '2012-01-01 11:00:00', uuid(), '1', '0.000000', 'SD1', '40.00', 'EC2', 'Y',  'Y', 'N', 'N', null);
insert into hr_paytype_t (`hr_paytype_id`, `PAYTYPE`, `DESCR`, `reg_ern_code`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `LOCATION`) values ('10000', 'EC1', 'description', 'EC1', '2010-01-01', '2012-01-01 11:00:00', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '*');
insert into hr_paytype_t (`hr_paytype_id`, `PAYTYPE`, `DESCR`, `reg_ern_code`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `LOCATION`) values ('10001', 'EC2', 'description', 'EC2', '2010-01-01', '2012-01-01 11:00:00', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '*');
# earn code with allow_schd_leave = Y
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('10000', 'EC1', 'test1', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', null, 'U', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
# earn code with allow_schd_leave = N
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('10001', 'EC2', 'test2', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', null, 'U', '99', 'T', 'N', 'Y', 'Y', 'N', 'Y', 'test', null, 'N', 'I', 'N');