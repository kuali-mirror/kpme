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

delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '1000';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
update hr_principal_attributes_t set leave_plan = NULL where principal_id = 'admin';

update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into lm_leave_plan_t (lm_leave_plan_id, LEAVE_PLAN, DESCR, CAL_YEAR_START, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP, PLANNING_MONTHS) values ('5555', 'testLP', 'Testing Leave Plan Months', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "1");

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003', '10004', '10005', '10006');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10000', '3', 'BWS-CAL', '2012-03-01 00:00:00', '2012-03-15 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10001', '3', 'BWS-CAL', '2012-03-15 00:00:00', '2012-04-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10002', '3', 'BWS-CAL', '2012-04-01 00:00:00', '2012-04-15 00:00:00');

insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');