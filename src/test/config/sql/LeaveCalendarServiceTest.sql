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

DELETE FROM lm_leave_block_t where lm_leave_block_id >= '5000';
delete from hr_calendar_entries_t where hr_calendar_entry_id in ('5000', '5001');
delete from LM_LEAVE_DOCUMENT_HEADER_T where principal_id = 'admin';
delete from tk_assignment_t where tk_assignment_id >= '5000';
delete from hr_job_t where hr_job_id >= '5000';

/* leave blocks */
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `apply_to_ytd_used`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `tk_assignment_id`, `request_status`, `leave_block_type`) values('5000', '2012-03-01', 'Send for Approval', 'admin', 'testLC','3000', 'testAC', '8', 'Appy to ytd', null, 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  '1100', 'P', 'LC');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `apply_to_ytd_used`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `tk_assignment_id`, `request_status`, `leave_block_type` ) values('5001', '2012-03-02', 'Send for Approval 1', 'admin', 'testLC','3000', 'testAC', '8', 'Appy to ytd', null, 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin',  '1100', 'P', 'LC');

/* calendar entry */
insert into hr_calendar_entries_t (`hr_calendar_entry_id`,`hr_calendar_id`, `calendar_name`, `begin_period_date`, `end_period_date`) values ('5000', '50', 'BWS-CAL', '2012-03-01 00:00:00', '2012-03-15 00:00:00');

insert into hr_calendar_entries_t (`hr_calendar_entry_id`,`hr_calendar_id`, `calendar_name`, `begin_period_date`, `end_period_date`) values ('5001', '50', 'BWS-CAL', '2012-04-01 00:00:00', '2012-04-15 00:00:00');

/* Assignments */
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('5000', 'testUser1', '50', '2010-01-01', '1234', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('5001', 'testUser1', '51', '2010-01-01', '1234', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('5002', 'testUser2', '52', '2010-01-01', '1234', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');

/* Jobs */
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5000', 'testUser1', '50', '2012-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '30.00', 'BW', 'Y',  'Y', 'N', 'N', 'NE');
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5001', 'testUser1', '51', '2012-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '30.00', 'BW', 'Y',  'Y', 'N', 'Y', 'NE');
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5002', 'testUser2', '52', '2012-03-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '30.00', 'BW', 'Y',  'Y', 'N', 'Y', 'E');