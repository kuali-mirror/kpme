ALTER TABLE hr_dept_earn_code_t add column EARN_CODE_TYPE varchar(1) NOT NULL DEFAULT 'T';

ALTER TABLE hr_dept_earn_code_t RENAME hr_earn_code_security_t;
ALTER TABLE hr_dept_earn_code_s RENAME hr_earn_code_security_s;

ALTER TABLE hr_earn_code_security_t CHANGE hr_dept_earn_code_id hr_earn_code_security_id varchar(60) NOT NULL;
