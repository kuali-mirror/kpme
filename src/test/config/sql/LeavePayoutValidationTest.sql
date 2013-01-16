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

delete from lm_leave_payout_t where lm_leave_payout_id >= '10000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';

insert into hr_earn_code_t (`hr_earn_code_id` , `earn_code` ,`descr`,`effdt` ,`ovt_earn_code` ,`ACTIVE`,`OBJ_ID` ,`VER_NBR` ,`timestamp`,`ACCRUAL_CATEGORY`,`inflate_min_hours` , `inflate_factor` ,`LEAVE_PLAN`,`ACCRUAL_BAL_ACTION`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ROLLUP_TO_EARNCODE`, `ELIGIBLE_FOR_ACC`, `AFFECT_PAY`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `ALLOW_NEGATIVE_ACC_BALANCE`, `RECORD_METHOD`, `USAGE_LIMIT`) values ('5005', 'payoutEC2', 'test payout earn code 2', '2012-03-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'payoutAC2', '1.5', '1.5', 'testLP2', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours', 'I');

insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values ('10059', 'payoutAC1', 'testLP1', 'a payout accrual category under testLP1', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505D2', '1', 'Y', 'N', 'Y', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
