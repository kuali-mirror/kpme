delete from hr_principal_attributes_t where principal_id = 'fran';
delete from hr_principal_attributes_t where principal_id = 'frank';
delete from hr_principal_attributes_t where principal_id = 'edna';
delete from hr_principal_attributes_t where principal_id = 'fred';

insert into hr_principal_attributes_t values('2001','fran', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('2002','frank', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('2003','edna', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');
insert into hr_principal_attributes_t values('2004','fred', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null, 'Y', 'LM');