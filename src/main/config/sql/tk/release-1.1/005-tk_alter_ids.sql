alter table hr_roles_t add column expdt date NULL;

alter table hr_job_t modify column hr_job_id varchar(60) NOT NULL;
alter table tk_time_block_t modify column hr_job_id varchar(60) NOT NULL;
alter table tk_time_block_hist_t modify column hr_job_id varchar(60) NOT NULL;
alter table tk_clock_log_t modify column hr_job_id varchar(60) NOT NULL;
alter table tk_clock_log_t modify column TK_WORK_AREA_ID varchar(60) NOT NULL;
alter table tk_clock_log_t modify column TK_TASK_ID varchar(60) NOT NULL;
alter table tk_clock_log_t modify column tk_clock_log_id varchar(60) NOT NULL;

alter table tk_time_block_t modify column tk_time_block_id varchar(60) NOT NULL;
alter table tk_time_block_t modify column tk_work_area_id varchar(60) NOT NULL;
alter table tk_time_block_t modify column tk_task_id varchar(60) NOT NULL;

alter table tk_assignment_t modify column tk_assignment_id varchar(60) NOT NULL;
alter table tk_assign_acct_t modify column tk_assignment_id varchar(60) NOT NULL;

alter table tk_clok_location_rl_ip_address_t modify column TK_CLOCK_LOC_RULE_IP_ID varchar(60) NOT NULL;
alter table tk_clok_location_rl_ip_address_t modify column TK_CLOCK_LOC_RULE_ID varchar(60) NOT NULL;
alter table tk_clock_location_rl_t modify column TK_CLOCK_LOC_RULE_ID varchar(60) NOT NULL;

alter table hr_dept_t modify column hr_dept_id varchar(60) NOT NULL;

alter table TK_GRACE_PERIOD_RL_T modify column TK_GRACE_PERIOD_RULE_ID varchar(60) NOT NULL;

alter table tk_dept_lunch_rl_t modify column TK_DEPT_LUNCH_RL_ID varchar(60) NOT NULL;

alter table tk_work_area_t modify column TK_WORK_AREA_ID varchar(60) NOT NULL;

alter table tk_time_sheet_init_t modify column tk_time_sheet_init_id varchar(60) NOT NULL;

alter table hr_earn_code_t modify column hr_earn_code_id varchar(60) NOT NULL;

alter table hr_earn_group_t modify column hr_earn_group_id varchar(60) NOT NULL;

alter table hr_earn_group_def_t modify column hr_earn_group_id varchar(60) NOT NULL;
alter table hr_earn_group_def_t modify column hr_earn_group_def_id varchar(60) NOT NULL;

alter table tk_task_t modify column tk_task_id varchar(60) NOT NULL;
alter table tk_task_t modify column tk_work_area_id varchar(60) NOT NULL;

alter table hr_py_calendar_entries_t modify column hr_py_calendar_entry_id varchar(60) NOT NULL;
alter table hr_py_calendar_entries_t modify column hr_py_calendar_id varchar(60) NOT NULL;

alter table hr_py_calendar_t modify column hr_py_calendar_id varchar(60) NOT NULL;

alter table lm_accrual_categories_t modify column LM_ACCRUAL_CATEGORY_ID varchar(60) NOT NULL;

alter table hr_sal_group_t modify column hr_sal_group_id varchar(60) NOT NULL;

alter table hr_paytype_t modify column hr_paytype_id varchar(60) NOT NULL;

alter table lm_accruals_t modify column lm_accruals_id varchar(60) NOT NULL;

alter table hr_holiday_calendar_dates_t modify column hr_holiday_calendar_dates_id varchar(60) NOT NULL;
alter table hr_holiday_calendar_dates_t modify column hr_holiday_calendar_id varchar(60) NOT NULL;

alter table hr_holiday_calendar_t modify column hr_holiday_calendar_id varchar(60) NOT NULL;

alter table TK_TIME_COLLECTION_RL_T modify column TK_TIME_COLL_RULE_ID varchar(60) NOT NULL;

alter table tk_system_lunch_rl_t modify column tk_system_lunch_rl_id varchar(60) NOT NULL;

alter table tk_shift_differential_rl_t modify column TK_SHIFT_DIFF_RL_ID varchar(60) NOT NULL;

alter table tk_weekly_overtime_rl_t modify column tk_weekly_overtime_rl_id varchar(60) NOT NULL;

alter table hr_dept_earn_code_t modify column hr_dept_earn_code_id varchar(60) NOT NULL;

alter table tk_daily_overtime_rl_t modify column tk_daily_overtime_rl_id varchar(60) NOT NULL;

alter table hr_roles_t modify column hr_roles_id varchar(60) NOT NULL;

alter table hr_location_t modify column hr_location_id varchar(60) NOT NULL;

alter table hr_pay_grade_t modify column hr_pay_grade_id varchar(60) NOT NULL;

alter table hr_position_t modify column hr_position_id varchar(60) NOT NULL;

alter table tk_time_sheet_init_t modify column py_calendar_entries_id varchar(60) NOT NULL;

alter table tk_time_block_hist_t modify column tk_time_block_hist_id varchar(60) NOT NULL;
alter table tk_time_block_hist_t modify column tk_time_block_id varchar(60) NOT NULL;

alter table tk_time_block_hist_detail_t modify column tk_time_block_hist_detail_id varchar(60) NOT NULL;
alter table tk_time_block_hist_detail_t modify column tk_time_block_hist_id varchar(60) NOT NULL;

alter table tk_missed_punch_doc_t modify column tk_clock_log_id varchar(60) NOT NULL;





