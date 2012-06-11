alter table hr_earn_code_t add column LEAVE_PLAN varchar(15) DEFAULT NULL;
alter table hr_earn_code_t add column ACCRUAL_BAL_ACTION varchar(15) DEFAULT NULL;
alter table hr_earn_code_t add column FRACT_TIME_ALLOWD varchar(5) NOT NULL;
alter table hr_earn_code_t add column ROUND_OPT varchar(5) NOT NULL;
alter table hr_earn_code_t add column ROLLUP_TO_EARNCODE varchar(3) DEFAULT NULL;
alter table hr_earn_code_t add column ELIGIBLE_FOR_ACC varchar(1) NOT NULL;
alter table hr_earn_code_t add column AFFECT_PAY varchar(1) NOT NULL;
alter table hr_earn_code_t add column ALLOW_SCHD_LEAVE varchar(1) NOT NULL;
alter table hr_earn_code_t add column FMLA varchar(1) NOT NULL;
alter table hr_earn_code_t add column WORKMANS_COMP varchar(15) NOT NULL;
alter table hr_earn_code_t add column DEF_TIME varchar(15) DEFAULT NULL;
alter table hr_earn_code_t add column ALLOW_NEGATIVE_ACC_BALANCE varchar(1) NOT NULL;
ALTER TABLE hr_earn_code_t DROP column record_time;
ALTER TABLE hr_earn_code_t DROP column record_amount;
ALTER TABLE hr_earn_code_t DROP column record_hours;
ALTER TABLE hr_earn_code_t add column RECORD_METHOD varchar(5) NOT NULL;
ALTER TABLE hr_earn_code_t modify column ACCRUAL_CATEGORY varchar(15) DEFAULT NULL;