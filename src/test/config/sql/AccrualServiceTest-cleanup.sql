delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '8000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= '5000';
delete from lm_leave_code_t where lm_leave_code_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from hr_principal_attributes_t where hr_principal_attribute_id >= '5000';
delete from hr_principal_attributes_t where principal_id in('testUser', 'testUser1', 'testUser2', 'testUser3', 'testUser4', 'testUser5', 'testUser6', 'testUser7');
delete from hr_job_t where hr_job_id >= '5000';
delete from lm_sys_schd_timeoff_t where lm_sys_schd_timeoff_id = '5000';
delete from lm_leave_block_t where principal_id in ('testUser', 'testUser1', 'testUser2', 'testUser3', 'testUser4', 'testUser5', 'testUser6', 'testUser7');
delete from lm_leave_block_hist_t where principal_id in ('testUser', 'testUser1', 'testUser2', 'testUser3', 'testUser4', 'testUser5', 'testUser6', 'testUser7');
