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

delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '1000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3001';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
DELETE FROM `lm_leave_block_t`;
DELETE FROM `lm_leave_block_hist_t`;
DELETE FROM `lm_leave_status_hist_t`;
delete from lm_leave_document_header_t where document_id >= '5000';
update hr_principal_attributes_t set leave_plan = NULL where principal_id = 'admin';

/** Update HR_Principal_attribute_t */
update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into lm_leave_code_t (`lm_leave_code_id`, `LEAVE_PLAN`, `ELIGIBLE_FOR_ACC`, `ACCRUAL_CAT`, `EARN_CODE`, `LEAVE_CODE`, `DISP_NAME`, `UNIT_OF_TIME`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `EMPLOYEE`, `APPROVER`, `DEPT_ADMIN`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `ALLOW_NEGATIVE_ACC_BALANCE`, `AFFECT_PAY`) values ('1000',	'testLP', 'Y', 'testAC', 'TC1', 'testLC', 'testLC', 'D', 99,	'T', 'Y', 'N', 'N', null, 'Y', 'N', 'N', '2013-02-09', 'B2991ADA-E866-F28C-7E95-A897AC377D0C',	'1', 'Y', '2013-02-09 11:38:04', 'N', 'N');
insert into lm_sys_schd_timeoff_t values ('3000', 'testLP', 'testAC', 'testLC', '2013-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, 'testH', '2013-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t values ('3001', 'InactiveLP', 'testAC', 'testLC', '2013-01-01', NULL, 'testLocation','testSSTO', '3', null, null, null, 'testH', '2013-03-01', uuid(), '1', 'N', now());
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');

/** Leave block */
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('1000', '2013-03-01', 'Send for Approval', 'admin', 'testLC', '3000', 'testAC', '8', '5000', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'P', 'LC');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type` ) values('1001', '2013-03-02', 'Send for Approval 1', 'admin', 'testLC', '3000', 'testAC', '8', '12546', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'P', 'LC');
insert into lm_leave_block_t (`lm_leave_block_id`, `leave_date`,`description`,  `principal_id` , `earn_code` ,`lm_sys_schd_timeoff_id` ,`accrual_category` ,`leave_amount`, `document_id`, `accrual_generated`, `block_id`,`ver_nbr`, `obj_id`, `timestamp`,`principal_id_modified`, `request_status`, `leave_block_type`) values('1002', '2013-03-02', 'Waiting for Approval', 'admin', 'testLC', '3000', 'testAC', '8', '12546', 'A', '0','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', now(), 'admin', 'R', 'LC');

/** Leave Block history */
insert into lm_leave_block_hist_t (`LM_LEAVE_BLOCK_HIST_ID`, `LM_LEAVE_BLOCK_ID`, `LEAVE_DATE`, `DESCRIPTION`, `PRINCIPAL_ID`, `EARN_CODE`, `LM_SYS_SCHD_TIMEOFF_ID`, `ACCRUAL_CATEGORY`, `LEAVE_AMOUNT`, `DOCUMENT_ID`, `PRINCIPAL_ID_MODIFIED`, `TIMESTAMP`, `PRINCIPAL_ID_DELETED`, `TIMESTAMP_DELETED`, `BLOCK_ID`, `ACCRUAL_GENERATED`, `REQUEST_STATUS`, `ACTION`, `VER_NBR`, `OBJ_ID`, `LEAVE_BLOCK_TYPE`) values ('1000','1000', '2013-03-01', 'Disapproved Leave', 'admin', 'testLC', 3000, 'testAC', '8', '12546', 'fran', now(), null, null, 0,'A','D','M','1','B2991ADA-E866-F28C-7E95-A897AC377D0S', 'LC' );
insert into lm_leave_block_hist_t (`LM_LEAVE_BLOCK_HIST_ID`, `LM_LEAVE_BLOCK_ID`, `LEAVE_DATE`, `DESCRIPTION`, `PRINCIPAL_ID`, `EARN_CODE`, `LM_SYS_SCHD_TIMEOFF_ID`, `ACCRUAL_CATEGORY`, `LEAVE_AMOUNT`, `DOCUMENT_ID`, `PRINCIPAL_ID_MODIFIED`, `TIMESTAMP`, `PRINCIPAL_ID_DELETED`, `TIMESTAMP_DELETED`, `BLOCK_ID`, `ACCRUAL_GENERATED`, `REQUEST_STATUS`, `ACTION`, `VER_NBR`, `OBJ_ID`, `LEAVE_BLOCK_TYPE`) values ('1001','1001', '2013-03-01', 'Updated by others', 'admin', 'testLC', 3000, 'testAC', '8', '12546', null, now(), 'fran', now(), 0,'A','P','D','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', 'LC' );
insert into lm_leave_block_hist_t (`LM_LEAVE_BLOCK_HIST_ID`, `LM_LEAVE_BLOCK_ID`, `LEAVE_DATE`, `DESCRIPTION`, `PRINCIPAL_ID`, `EARN_CODE`, `LM_SYS_SCHD_TIMEOFF_ID`, `ACCRUAL_CATEGORY`, `LEAVE_AMOUNT`, `DOCUMENT_ID`, `PRINCIPAL_ID_MODIFIED`, `TIMESTAMP`, `PRINCIPAL_ID_DELETED`, `TIMESTAMP_DELETED`, `BLOCK_ID`, `ACCRUAL_GENERATED`, `REQUEST_STATUS`, `ACTION`, `VER_NBR`, `OBJ_ID`, `LEAVE_BLOCK_TYPE`) values ('1002','1002', '2013-03-01', 'Updated by self', 'admin', 'testLC', 3000, 'testAC', '8', '12546', 'admin', now(), null, null, 0,'A','P','M','1','B2991ADA-E866-F28C-7E95-A897AC377D0C', 'LC' );

/** Leave status history */
insert into lm_leave_status_hist_t values ('2000', '1000', 'D', now(), 'admin', 'Work Load', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1');

/* Leave Calnedar Document */
INSERT INTO lm_leave_document_header_t (`document_id`,`principal_id`,`begin_date`,`end_date`,`document_status`,`obj_id`,`ver_nbr`) values ('5000', 'testUser', '2013-03-01 00:00:00','2013-03-15 00:00:00', 'F', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');