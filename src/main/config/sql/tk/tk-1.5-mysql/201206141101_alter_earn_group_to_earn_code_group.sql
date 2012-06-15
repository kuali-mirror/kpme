ALTER TABLE hr_earn_group_t CHANGE hr_earn_group_id hr_earn_code_group_id varchar(60) NOT NULL;
ALTER TABLE hr_earn_group_t CHANGE earn_group earn_code_group varchar(10) NOT NULL;

ALTER TABLE hr_earn_group_t RENAME hr_earn_code_group_t;
ALTER TABLE hr_earn_group_s RENAME hr_earn_code_group_s;

ALTER TABLE hr_earn_group_def_t CHANGE hr_earn_group_def_id hr_earn_code_group_def_id varchar(60) NOT NULL;
ALTER TABLE hr_earn_group_def_t CHANGE hr_earn_group_id hr_earn_code_group_id varchar(60) NOT NULL;

ALTER TABLE hr_earn_group_def_t RENAME hr_earn_code_group_def_t;
ALTER TABLE hr_earn_group_def_s RENAME hr_earn_code_group_def_s;