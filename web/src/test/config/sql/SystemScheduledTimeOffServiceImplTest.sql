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

DELETE FROM LM_SYS_SCHD_TIMEOFF_T WHERE LM_SYS_SCHD_TIMEOFF_ID >= 1000;

INSERT INTO LM_SYS_SCHD_TIMEOFF_T (LM_SYS_SCHD_TIMEOFF_ID, LEAVE_PLAN, ACCRUAL_CATEGORY, EARN_CODE, ACCR_DT, SCH_TIME_OFF_DT, LOCATION, DESCR, AMOUNT_OF_TIME, UNUSED_TIME, TRANS_CONV_FACTOR, TRANSFER_TO_EARN_CODE, PREMIUM_HOLIDAY, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP) VALUES ('1000','TEST','TEST-AC','RGH','2013-01-01',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-08','8751782B-193F-0201-99E5-375FA84D7B78',1,'Y','2012-02-08 21:45:20');
INSERT INTO LM_SYS_SCHD_TIMEOFF_T (LM_SYS_SCHD_TIMEOFF_ID, LEAVE_PLAN, ACCRUAL_CATEGORY, EARN_CODE, ACCR_DT, SCH_TIME_OFF_DT, LOCATION, DESCR, AMOUNT_OF_TIME, UNUSED_TIME, TRANS_CONV_FACTOR, TRANSFER_TO_EARN_CODE, PREMIUM_HOLIDAY, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP) VALUES ('1001','TEST','TEST-AC','RGH','2013-01-01',NULL,'IN','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-08','64359087-1F4C-3490-E0E8-9513DC9BEDC1',1,'Y','2012-02-08 21:47:16');
