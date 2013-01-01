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

delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3001';
delete from hr_principal_attributes_t where principal_id = 'fran';
delete from hr_principal_attributes_t where principal_id = 'frank';
delete from hr_principal_attributes_t where principal_id = 'edna';
delete from hr_principal_attributes_t where principal_id = 'fred';

insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
insert into lm_accrual_category_rules_t (lm_accrual_category_rules_id, SERVICE_UNIT_OF_TIME, START_ACC, END_ACC, ACCRUAL_RATE, MAX_BAL, MAX_BAL_ACTION_FREQUENCY, ACTION_AT_MAX_BAL, MAX_BAL_TRANS_ACC_CAT, MAX_BAL_TRANS_CONV_FACTOR, MAX_TRANS_AMOUNT, MAX_PAYOUT_AMOUNT, MAX_PAYOUT_EARN_CODE, MAX_USAGE, MAX_CARRY_OVER, LM_ACCRUAL_CATEGORY_ID, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP) values ('3000', 'M', 1, 12, 0.50, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23');
insert into lm_leave_plan_t values ('3000', 'IU-SM', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2012-02-09 11:38:04', 'Feb');
insert into lm_leave_plan_t values ('3001', 'IU-SM-W', 'staff weekly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'N', '2012-02-09 11:38:04', 'Feb');

insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('2001','fran', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('2002','frank', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('2003','edna', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('2004','fred', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);