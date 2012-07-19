delete from lm_leave_donation_t where LM_LEAVE_DONATION_ID = '3000';
delete from hr_principal_attributes_t where hr_principal_attribute_id >= '5000';
delete from lm_leave_plan_t where lm_leave_plan_id >= '8000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from lm_leave_block_t where principal_id like('testUser%');
delete from lm_leave_block_hist_t where principal_id like('testUser%');