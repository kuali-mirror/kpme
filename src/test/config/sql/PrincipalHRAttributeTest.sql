delete from hr_principal_attributes_t where principal_id = 'fran';
delete from hr_principal_attributes_t where principal_id = 'frank';
delete from hr_principal_attributes_t where principal_id = 'edna';
delete from hr_principal_attributes_t where principal_id = 'fred';

insert into hr_principal_attributes_t values('fran', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('frank', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('edna', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('fred', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');