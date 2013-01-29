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

delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3000';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
insert into lm_leave_code_t (`lm_leave_code_id`, `LEAVE_PLAN`, `ELIGIBLE_FOR_ACC`, `ACCRUAL_CAT`, `EARN_CODE`, `LEAVE_CODE`, `DISP_NAME`, `UNIT_OF_TIME`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `EMPLOYEE`, `APPROVER`, `DEPT_ADMIN`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `ALLOW_NEGATIVE_ACC_BALANCE`, `AFFECT_PAY`) values ('3000',	'IU-SM', 'Y', 'AC1', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2012-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2012-02-09 11:38:04','N', 'N' );
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('3000', 'AC1', 'IU-SM', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
insert into lm_leave_plan_t values ('3000',	'IU-SM', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2012-02-09 11:38:04', 'Feb', '1971-01-01 00:00:00');

