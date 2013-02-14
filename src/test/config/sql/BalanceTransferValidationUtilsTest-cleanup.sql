delete from lm_sys_schd_timeoff_t where lm_sys_schd_timeoff_id >= 5000;
delete from lm_accrual_category_t where lm_accrual_category_id >= 5000;
delete from hr_principal_attributes_t where principal_id like('testUser%');