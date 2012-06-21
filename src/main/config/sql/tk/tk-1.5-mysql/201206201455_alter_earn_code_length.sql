ALTER TABLE hr_earn_code_t modify column earn_code varchar(15) DEFAULT NULL;
ALTER TABLE hr_earn_code_t modify column ROLLUP_TO_EARNCODE varchar(15) DEFAULT NULL;

ALTER TABLE tk_time_block_t modify column earn_code varchar(15) DEFAULT NULL;
ALTER TABLE tk_time_block_hist_t modify column earn_code varchar(15) DEFAULT NULL;
ALTER TABLE tk_time_block_hist_detail_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_work_area_t modify column default_overtime_earncode varchar(15) DEFAULT NULL;

ALTER TABLE hr_earn_code_group_def_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE hr_paytype_t modify column reg_ern_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_assign_acct_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_shift_differential_rl_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_weekly_overtime_rl_t modify column CONVERT_TO_ERNCD varchar(15) DEFAULT NULL;

ALTER TABLE hr_earn_code_security_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_daily_overtime_rl_t modify column earn_code varchar(15) DEFAULT NULL;

ALTER TABLE tk_hour_detail_t modify column earn_code varchar(15) DEFAULT NULL;

# for Accrual category
ALTER TABLE lm_accruals_t modify column accrual_category varchar(15) NOT NULL;