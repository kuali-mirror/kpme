--
-- Copyright 2004-2013 The Kuali Foundation
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

delete from lm_leave_donation_t where LM_LEAVE_DONATION_ID = '3000';
delete from hr_principal_attributes_t where hr_principal_attribute_id in ('10001', '10003');
delete from lm_leave_plan_t where lm_leave_plan_id >= '8000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from lm_leave_block_t where principal_id in('testuser1', 'testuser2');
delete from lm_leave_block_hist_t where principal_id in('testuser1', 'testuser2');

insert into lm_leave_donation_t values ('3000',	'dAC', 'rDC', '300', '300', 'donor', 'recipient', 'testDescription','2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C','1', 'Y', '2012-02-09 11:38:04', 'LC-TEST2', 'LC-TEST2' );

# for testCreatingLeaveBlocks
insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values( '10001', 'testuser1', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM');
insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values( '10003', 'testuser2', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM');

insert into lm_leave_plan_t (lm_leave_plan_id, LEAVE_PLAN, DESCR, CAL_YEAR_START, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP, PLANNING_MONTHS) values ('8000', 'testLP', 'Test Leave Plan', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', '12');
insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('5000', 'testAC', 'testLP', 'test', 'M', '40', '2012-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '0', 'EC', 'Y');
insert into hr_earn_code_t values('5000', 'EC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I');