delete from lm_leave_document_header_t where document_id >= '5000';
delete from hr_calendar_entries_t where hr_calendar_entry_id >= '5000';
delete from hr_principal_attributes_t where principal_id like('testUser%');
delete from lm_leave_plan_t where lm_leave_plan_id >= '5000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from lm_leave_block_t where principal_id like('testUser%');