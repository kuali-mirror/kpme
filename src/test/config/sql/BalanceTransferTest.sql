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

delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '3000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '3001';
delete from lm_accrual_category_t where lm_accrual_category_id = '10055';
delete from lm_accrual_category_t where lm_accrual_category_id = '10056';
delete from hr_earn_code_t where hr_earn_code_id = '5000';
delete from hr_earn_code_t where hr_earn_code_id = '5001';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '1000';
delete from hr_job_t where hr_job_id = '5018';

update hr_principal_attributes_t set leave_plan = 'testLP' where principal_id = 'admin';

insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('5018', 'admin', '18', '2010-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'Y',  'Y', 'N', 'Y', null);

insert into lm_leave_plan_t (lm_leave_plan_id, LEAVE_PLAN, DESCR, CAL_YEAR_START, EFFDT, OBJ_ID, VER_NBR, ACTIVE, TIMESTAMP, PLANNING_MONTHS) values ('5555', 'testLP', 'Testing Leave Plan Months', '03/01', '2009-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "12");

insert into hr_earn_code_t (`hr_earn_code_id` , `earn_code` ,`descr`,`effdt` ,`ovt_earn_code` ,`ACTIVE`,`OBJ_ID` ,`VER_NBR` ,`timestamp`,`ACCRUAL_CATEGORY`,`inflate_min_hours` , `inflate_factor` ,`LEAVE_PLAN`,`ACCRUAL_BAL_ACTION`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ROLLUP_TO_EARNCODE`, `ELIGIBLE_FOR_ACC`, `AFFECT_PAY`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `ALLOW_NEGATIVE_ACC_BALANCE`, `RECORD_METHOD`, `USAGE_LIMIT`) values ('5000', 'creditedEC1', 'test credit earn code', '2010-01-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'creditAC1', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours', 'I');
insert into hr_earn_code_t (`hr_earn_code_id` , `earn_code` ,`descr`,`effdt` ,`ovt_earn_code` ,`ACTIVE`,`OBJ_ID` ,`VER_NBR` ,`timestamp`,`ACCRUAL_CATEGORY`,`inflate_min_hours` , `inflate_factor` ,`LEAVE_PLAN`,`ACCRUAL_BAL_ACTION`, `FRACT_TIME_ALLOWD`, `ROUND_OPT`, `ROLLUP_TO_EARNCODE`, `ELIGIBLE_FOR_ACC`, `AFFECT_PAY`, `ALLOW_SCHD_LEAVE`, `FMLA`, `WORKMANS_COMP`, `DEF_TIME`, `ALLOW_NEGATIVE_ACC_BALANCE`, `RECORD_METHOD`, `USAGE_LIMIT`) values ('5001', 'debitedEC1', 'test debit earn code', '2010-01-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0D', '1', now(), 'debitAC1', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours', 'I');

insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values ('10055', 'debitAC1', 'testLP', 'a debitted accrual category under testLP', 'M', 'M', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'Y', 'Y',now(), '1.5', 'debitedEC1', 'Y');	
insert into lm_accrual_category_t (`lm_accrual_category_id`, `ACCRUAL_CATEGORY`, `LEAVE_PLAN`, `DESCR`, `ACCRUAL_INTERVAL_EARN`, `UNIT_OF_TIME`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `PRORATION`, `DONATION`, `SHOW_ON_GRID`, `ACTIVE`, `TIMESTAMP`, `MIN_PERCENT_WORKED`, `EARN_CODE`, `HAS_RULES`) values ('10056', 'creditAC1', 'testLP', 'a credited accrual category under testLP', 'M', 'M', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CF', '1', 'Y', 'N', 'Y', 'Y',now(), '1.5', 'creditedEC1', 'Y');	

insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3000', 'M', 0, 999, 25.00, 100.00, 'L', 'T', 'creditAC1', 0.33, 1000, NULL, NULL, NULL, NULL, '10055', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23', 'Y');
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3001', 'M', 0, 999, 10.00, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '10056', 'DEDC243D-4E51-CCDE-1326-E1700B2631E2', '1', 'Y', '2012-01-09 12:10:23', 'N');

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003', '10004', '10005', '10006', '10007', '10008', '10009', '10010', '10011');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10000', '3', 'BWS-CAL', '2012-03-01 00:00:00', '2012-04-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10001', '3', 'BWS-CAL', '2012-04-01 00:00:00', '2012-05-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10002', '3', 'BWS-CAL', '2012-05-01 00:00:00', '2012-06-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10003', '3', 'BWS-CAL', '2012-06-01 00:00:00', '2012-07-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10004', '3', 'BWS-CAL', '2012-07-01 00:00:00', '2012-08-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10005', '3', 'BWS-CAL', '2012-08-01 00:00:00', '2012-09-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10006', '3', 'BWS-CAL', '2012-09-01 00:00:00', '2012-10-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10007', '3', 'BWS-CAL', '2012-10-01 00:00:00', '2012-11-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10008', '3', 'BWS-CAL', '2012-11-01 00:00:00', '2012-12-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10009', '3', 'BWS-CAL', '2012-12-01 00:00:00', '2013-01-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10010', '3', 'BWS-CAL', '2013-01-01 00:00:00', '2013-02-01 00:00:00');
insert into hr_calendar_entries_t (hr_calendar_entry_id, hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10011', '3', 'BWS-CAL', '2013-02-01 00:00:00', '2013-03-01 00:00:00');

delete from lm_leave_document_header_t where document_id in ('1000', '1001', '1002', '1003', '1004', '1005', '1006', '1007', '1008', '1009', '1010', '1011')
INSERT INTO lm_leave_document_header_t values ('1000', 'admin', '2012-03-01 00:00:00','2012-04-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');
INSERT INTO lm_leave_document_header_t values ('1001', 'admin', '2012-04-01 00:00:00','2012-05-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E98', '1');
INSERT INTO lm_leave_document_header_t values ('1002', 'admin', '2012-05-01 00:00:00','2012-06-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E99', '1');
INSERT INTO lm_leave_document_header_t values ('1003', 'admin', '2012-06-01 00:00:00','2012-07-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9A', '1');
INSERT INTO lm_leave_document_header_t values ('1004', 'admin', '2012-07-01 00:00:00','2012-08-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9B', '1');
INSERT INTO lm_leave_document_header_t values ('1005', 'admin', '2012-08-01 00:00:00','2012-09-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9C', '1');
INSERT INTO lm_leave_document_header_t values ('1006', 'admin', '2012-09-01 00:00:00','2012-10-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9D', '1');
INSERT INTO lm_leave_document_header_t values ('1007', 'admin', '2012-10-01 00:00:00','2012-11-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9E', '1');
INSERT INTO lm_leave_document_header_t values ('1008', 'admin', '2012-11-01 00:00:00','2012-12-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E9F', '1');
INSERT INTO lm_leave_document_header_t values ('1009', 'admin', '2012-12-01 00:00:00','2013-01-01 00:00:00', 'A', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0EA0', '1');
INSERT INTO lm_leave_document_header_t values ('1010', 'admin', '2013-01-01 00:00:00','2013-02-01 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0EA1', '1');
INSERT INTO lm_leave_document_header_t values ('1011', 'admin', '2013-02-01 00:00:00','2013-03-01 00:00:00', 'I', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0EA2', '1');
