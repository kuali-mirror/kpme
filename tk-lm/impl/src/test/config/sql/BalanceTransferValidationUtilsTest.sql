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

delete from lm_sys_schd_timeoff_t where lm_sys_schd_timeoff_id >= 5000;
delete from lm_accrual_category_t where lm_accrual_category_id >= 5000;
delete from hr_principal_attributes_t where principal_id like('testUser%');

insert into lm_sys_schd_timeoff_t (`LM_SYS_SCHD_TIMEOFF_ID`, `LEAVE_PLAN`, `ACCRUAL_CATEGORY`, `EARN_CODE`, `ACCR_DT`, `SCH_TIME_OFF_DT`, `LOCATION`, `DESCR`, `AMOUNT_OF_TIME`, `UNUSED_TIME`, `TRANS_CONV_FACTOR`, `TRANSFER_TO_EARN_CODE`, `PREMIUM_HOLIDAY`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`) values ('5000', 'testLP21', 'ISU-HOL', 'ISU-HOL', '2013-02-11', NULL, 'testLocation','testSSTO', '8', 'T', null, 'ISU-HOL-TRF','testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('5000', 'ISU-HOL', 'testLP', 'test', 'M', '40', '2013-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '0', 'EC', 'Y');
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('5001', 'ISU-HOL-TRF', 'testLP', 'test', 'M', '40', '2013-02-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, 'Y', 'Y',now(), '0', 'EC', 'Y');
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `workers_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`) values('5001', 'testUser', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM');