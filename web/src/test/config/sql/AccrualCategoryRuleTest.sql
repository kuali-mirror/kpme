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

delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= '3000';


insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values ('3000', 'testAC', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');	
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3000', 'M', 0, 6, 0.50, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23', 'N');
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3001', 'M', 7, 13, 1.00, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23', 'N');
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3002', 'M', 14, 23, 1.50, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23', 'N');
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3003', 'M', 24, 999, 2.00, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23', 'N');
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3004', 'M', 24, 999, 2.00, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'N', '2010-01-09 12:10:23', 'N');
