delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '8000';
delete from hr_principal_attributes_t where hr_principal_attribute_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';

# for testGetEarnCodesForDisplay
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('8000', 'testLP', 'Test Leave Plan', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', '12');
insert into hr_principal_attributes_t (`hr_principal_attribute_id`, `principal_id`, `pay_calendar`, `leave_plan`, `service_date`, `fmla_eligible`, `worksman_eligible`, `timezone`, `EFFDT`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `active`, `leave_calendar`, `record_time`, `record_leave`) values('5001', 'testUser', 'BWS-CAL', 'testLP', '2012-03-01', 'Y', 'Y', null, '2012-03-01', now(), uuid(), '1', 'Y', 'LM', 'Y', 'Y');
# earn code with allow_schd_leave = Y
insert into hr_earn_code_t values('5000', 'EC1', 'test1', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours');
# earn code with allow_schd_leave = N
insert into hr_earn_code_t values('5001', 'EC2', 'test2', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'N', 'Y', 'test', null, 'N', 'Hours');