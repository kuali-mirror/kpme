--
-- Copyright 2004-2012 The Kuali Foundation
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

delete from hr_principal_attributes_t where principal_id = '111';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_leave_plan_t where lm_leave_plan_id = '3000';
delete from lm_leave_code_t where lm_leave_code_id = '3000';
delete from lm_leave_adjustment_t where lm_leave_adjustment_id = '3000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';

update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('5001', '111', 'BWS-CAL', 'testLP','2010-01-01', 'Y','Y', null, '2010-01-01',now(), uuid(), '1', 'Y', null);
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('3000', 'AC1', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');	
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('3000',	'IU-SM', 'staff monthly', '01/01', '2011-01-01', 'F1248B7E-D13D-F3EE-20FF-9E428992F92C', '1', 'Y', '2012-01-07 11:38:02', null);
insert into lm_leave_code_t (`lm_leave_code_id`, `LEAVE_PLAN`, `ELIGIBLE_FOR_ACC`, `ACCRUAL_CAT`, `EARN_CODE`, `LEAVE_CODE`, `DISP_NAME`, `UNIT_OF_TIME`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `EMPLOYEE`, `APPROVER`, `DEPT_ADMIN`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `ALLOW_NEGATIVE_ACC_BALANCE`, `AFFECT_PAY`) values ('3000',	'IU-SM', 'Y', 'AC1', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04', 'N', 'N');
insert into lm_leave_adjustment_t (`LM_LEAVE_ADJUSTMENT_ID`, `PRINCIPAL_ID`, `ACCRUAL_CAT`, `LEAVE_PLAN`, `EARN_CODE`, `ADJUSTMENT_AMOUNT`, `DESCRIPTION`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `effdt`) values (	'3000',	'10001',	'AC1',	'IU-SM',	'testLC',	10,	'test 1239',	'2012-01-31 11:03:11',	'8B5039D6-5775-87AA-9012-1A6EDEB0FD16',	1, null);
insert into hr_earn_code_t values('5000', 'EC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99.9', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I');

