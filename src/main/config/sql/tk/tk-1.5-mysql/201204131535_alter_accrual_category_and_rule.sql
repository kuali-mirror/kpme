alter table lm_accrual_category_t add column HAS_RULES varchar(1);
alter table lm_accrual_category_rules_t add column MAX_BAL_FLAG varchar(1);
alter table lm_accrual_category_rules_t modify column LM_ACCRUAL_CATEGORY_ID varchar(60);