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



delete from lm_leave_block_t where lm_leave_block_id like "5000";
delete from tk_time_block_t where tk_time_block_id like "5000";

# insert a single leave block
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('5000', '2012-04-26', 'Send for Approval', 'testUser7', 'EC6', NULL, 'testAC9', '2', null, 'N', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  'P', 'AS');
# insert a single time block
insert into tk_time_block_t (`tk_time_block_id`, `document_id`, `job_number`, `work_area`, `task`, `earn_code`, `begin_ts`, `end_ts`, `clock_log_created`, `hours`, `amount`, `user_principal_id`, `timestamp`, `obj_id`, `ver_nbr`, `clock_log_begin_id`, `clock_log_end_id`, `principal_id`, `ovt_pref`, `lunch_deleted`) values('5000', '10029', '10', '20', '30', 'EC', now(), now(), 'Y', null, null, '10001', now(), 'B2991ADA-E866-F28C-7E95-A897AC377D0D', '1', null, null, '10002', null, 'Y');