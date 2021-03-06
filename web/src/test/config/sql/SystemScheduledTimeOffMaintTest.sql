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


delete from hr_earn_code_t where HR_EARN_CODE_ID = '1000';
delete from hr_earn_code_t where HR_EARN_CODE_ID = '1001';
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('1000', 'EC', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
insert into hr_earn_code_t (`hr_earn_code_id`,`earn_code`,`descr`,`effdt`,`ovt_earn_code`,`active`,`obj_id`,`ver_nbr`,`timestamp`,`accrual_category`,`inflate_min_hours`,`inflate_factor`,`record_method`,`leave_plan`,`accrual_bal_action`,`fract_time_allowd`,`round_opt`,`rollup_to_earncode`,`eligible_for_acc`,`affect_pay`,`allow_schd_leave`,`fmla`,`workmans_comp`,`def_time`,`allow_negative_acc_balance`,`usage_limit`,`count_as_reg_pay`) values('1001', 'TST', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'Hours', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'I', 'N');
-- delete from hr_location_t where hr_location_id = '1000';
-- INSERT INTO hr_location_t(hr_location_id, location, description, effdt, timestamp, timezone, active, user_principal_id) VALUES('1000', 'CST', 'Central Standard Time', '2010-01-01', '2012-08-21 09:44:28.0', 'America/Chicago', 'Y', 'admin');
delete from hr_principal_attributes_t where principal_id = '111';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID >= '3000';

insert into lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) values ('3000', 'testLP',     'testAC',  'EC',  '2012-01-01', NULL, 'BL','testSSTO', '3', null,   null, null, 'testH', '2012-03-01', uuid(), '1', 'Y', now());
insert into lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) values ('3001', 'InactiveLP', 'testAC',  'EC',  '2012-01-01', NULL, 'BL','testSSTO', '3', null,   null, null, 'testH', '2012-03-01', uuid(), '1', 'N', now());
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3002','TEST','TEST-AC','TLC','2013-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-08','8751782B-193F-0201-99E5-375FA84D7B78',2,'N','2012-02-08 21:45:20');
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3007','TEST','TEST-AC','IND','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-08','64359087-1F4C-3490-E0E8-9513DC9BEDC1',3,'N','2012-02-08 21:47:16');
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3008','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-08','615EA427-1BC0-60DB-E85E-66CD03923BA9',3,'Y','2012-02-08 21:47:17');
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3003','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-09','162120C5-ADEC-239E-9249-BB727CCFA362',4,'N','2012-02-08 21:58:37');
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3004','TEST','TEST-AC','TEST-LC','2014-01-02',NULL,'BL','Test New Years Day',8,'NUTA',1.50,'TEST-LC','N','2012-02-09','F4C4F1C2-62B7-4FB0-4B2D-B447E1F42D44',4,'N','2012-02-08 21:58:38');
INSERT INTO lm_sys_schd_timeoff_t (`lm_sys_schd_timeoff_id`,`leave_plan`,`accrual_category`,`earn_code`,`accr_dt`,`sch_time_off_dt`,`location`,`descr`,`amount_of_time`,`unused_time`,`trans_conv_factor`,`transfer_to_earn_code`,`premium_holiday`,`effdt`,`obj_id`,`ver_nbr`,`active`,`timestamp`) VALUES ('3005','test','testAC','TLC','2012-02-24',NULL,'BL','test1363',3,NULL,NULL,NULL,'N','2012-02-24','A928B2CB-EB49-7ED8-9E88-813E857D2B79',1,'Y','2012-02-22 15:41:25');



delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from hr_principal_attributes_t where principal_id = '111';
insert into lm_accrual_category_t (lm_accrual_category_id, ACCRUAL_CATEGORY, LEAVE_PLAN, DESCR, ACCRUAL_INTERVAL_EARN, UNIT_OF_TIME, EFFDT, OBJ_ID, VER_NBR, PRORATION, DONATION, SHOW_ON_GRID, ACTIVE, TIMESTAMP, MIN_PERCENT_WORKED, EARN_CODE, HAS_RULES) values('3000', 'testAC', 'testLP', 'test', '', '', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', null, null, null, 'Y',now(), '1.5', 'LC-DEFAULT', 'Y');
insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values('2000','111', 'BWS-CAL', 'testLP','2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);