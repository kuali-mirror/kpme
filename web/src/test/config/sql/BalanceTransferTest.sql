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

delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= 5000;
delete from LM_BALANCE_TRANSFER_T where LM_BALANCE_TRANSFER_ID >= 5000;

insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('5000', 'M', 0, 888, 16, 100.00, 'NA', 'NA', NULL, 0.5, NULL, NULL, NULL, NULL, NULL, '5000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-02-03 12:10:23', 'N');

-- for testLookupPage
insert into LM_BALANCE_TRANSFER_T (`LM_BALANCE_TRANSFER_ID`, `LM_ACCRUAL_CATEGORY_RULES_ID`, `PRINCIPAL_ID`, `TRANSFER_AMOUNT`, `FROM_ACCRUAL_CATEGORY`, `AMOUNT_TRANSFERRED`, `TO_ACCRUAL_CATEGORY`, `FORFEITED_AMOUNT`, `EFFDT`) values('5000', '5000', 'testUser', '8.0', 'fromAC', '5.0', 'toAC', '3.0', '2012-05-20');
