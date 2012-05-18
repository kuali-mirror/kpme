delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5001';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5002';

insert into lm_leave_plan_t values ('5000',	'TestLeavePlan', 'staff monthly', '01/01', '2011-12-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2011-12-09 11:38:04', 'Feb');
insert into lm_leave_plan_t values ('5001',	'TestLeavePlan', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '2', 'Y', '2012-01-01 11:38:04', 'Feb');
insert into lm_leave_plan_t values ('5002',	'TestLeavePlan', 'staff monthly', '01/01', '2012-03-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '3', 'Y', '2012-02-09 11:38:04', 'Feb');