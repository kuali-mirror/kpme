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

delete from lm_sys_schd_timeoff_t where lm_sys_schd_timeoff_id >= 2000;
delete from lm_leave_block_t where lm_leave_block_id >= 6000;

-- for testCanDeleteLeaveBlockForBankableSSTOUsageLB
insert into lm_sys_schd_timeoff_t (`LM_SYS_SCHD_TIMEOFF_ID`, `LEAVE_PLAN`, `ACCRUAL_CATEGORY`, `EARN_CODE`, `ACCR_DT`, `SCH_TIME_OFF_DT`, `LOCATION`, `DESCR`, `AMOUNT_OF_TIME`, `UNUSED_TIME`, `TRANS_CONV_FACTOR`, `TRANSFER_TO_EARN_CODE`, `PREMIUM_HOLIDAY`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`) values ('2000', 'testLP', 'holAC', 'hEC', '2012-04-10', NULL, 'testLocation','testSSTO', '8', 'B', null, null,'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t (`LM_SYS_SCHD_TIMEOFF_ID`, `LEAVE_PLAN`, `ACCRUAL_CATEGORY`, `EARN_CODE`, `ACCR_DT`, `SCH_TIME_OFF_DT`, `LOCATION`, `DESCR`, `AMOUNT_OF_TIME`, `UNUSED_TIME`, `TRANS_CONV_FACTOR`, `TRANSFER_TO_EARN_CODE`, `PREMIUM_HOLIDAY`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`) values ('2001', 'testLP', 'holAC', 'hEC', '2012-04-10', NULL, 'testLocation','testSSTO', '8', 'T', null, null,'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('6000', '2012-03-31', 'Send for Approval', 'testUser', 'EC', '2000', 'testAC', '-8', null, 'Y', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'A', 'AS');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('6001', '2012-03-31', 'Send for Approval', 'testUser', 'EC', '2001', 'testAC', '-8', null, 'Y', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'A', 'AS');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('6002', '2012-03-31', 'Send for Approval', 'testUser', 'EC', '2000', 'testAC', '-8', null, 'Y', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'A', 'AS');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('6003', '2012-03-31', 'Send for Approval', 'testUser', 'EC', '2000', 'testAC', '8', null, 'Y', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'A', 'AS');