delete from PM_PSTN_APPOINTMENT_T where PSTN_APPOINTMENT = 'testAppointment';
delete from KRLC_CMP_T where CAMPUS_CD = 'TS';
delete from PM_INSTITUTION_T where pm_institution_id >= 5000;

insert into KRLC_CMP_T (`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('TS', null, null, null, uuid(), '1', 'Y');
insert into PM_INSTITUTION_T (`pm_institution_id`, `effdt`, `institution_code`, `description`, `active`, `timestamp`) values ('5000', '2012-01-01', 'testInst', 'test', 'Y', now());
insert into PM_PSTN_APPOINTMENT_T (`pm_pstn_appointment_id`, `pstn_appointment`, `description`, `institution`, `campus`, `effdt`, `active`, `timestamp`, `obj_id`, `ver_nbr`) values ('123456789', 'testAppointment', 'This is a test appointment description', 'testInst', 'TS', '2012-01-01', 'Y', now(), null, '1');
