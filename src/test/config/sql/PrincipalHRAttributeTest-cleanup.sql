delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '3001';
delete from hr_principal_attributes_t where principal_id = 'fran';
delete from hr_principal_attributes_t where principal_id = 'frank';
delete from hr_principal_attributes_t where principal_id = 'edna';
delete from hr_principal_attributes_t where principal_id = 'fred';

insert into lm_accrual_category_t values('3000', 'testAC', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5', 'LC-DEFAULT');	
insert into lm_accrual_category_rules_t (`lm_accrual_category_rules_id`, `SERVICE_UNIT_OF_TIME`, `START_ACC`, `END_ACC`, `ACCRUAL_RATE`, `MAX_BAL`, `MAX_BAL_ACTION_FREQUENCY`, `ACTION_AT_MAX_BAL`, `MAX_BAL_TRANS_ACC_CAT`, `MAX_BAL_TRANS_CONV_FACTOR`, `MAX_TRANS_AMOUNT`, `MAX_PAYOUT_AMOUNT`, `MAX_PAYOUT_EARN_CODE`, `MAX_USAGE`, `MAX_CARRY_OVER`, `LM_ACCRUAL_CATEGORY_ID`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `MAX_BAL_FLAG`) values ('3000', 'M', 1, 12, 0.50, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23');
insert into lm_leave_plan_t values ('3000',	'IU-SM', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2012-02-09 11:38:04', 'Feb');
insert into lm_leave_plan_t values ('3001',	'IU-SM-W', 'staff weekly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'N', '2012-02-09 11:38:04', 'Feb');