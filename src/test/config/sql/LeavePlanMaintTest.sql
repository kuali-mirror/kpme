delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID >= '1000';
delete from hr_principal_attributes_t where principal_id = '10005';

insert into lm_leave_plan_t values ('5555', 'TLPM', 'Testing Leave Plan Months', '02/01', '2012-02-01', '', '1', 'Y', '2012-02-06 11:59:46', "1");
insert into lm_leave_plan_t values ('2000', 'ASN', 'Testing LP Inactive Flag', '02/01', '2012-02-01', uuid(), '1', 'Y', now(), "1");
insert into hr_principal_attributes_t values('1001', '10005', 'BWS-CAL', 'ASN', '2010-01-01', 'Y', 'Y', null, '2010-01-01', now(), uuid(), '1', 'Y', null, 'Y', 'LM');