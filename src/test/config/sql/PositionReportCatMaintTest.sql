delete from PM_PSTN_RPT_CAT_T where pstn_rpt_CAT = 'testPRC';
delete from KRLC_CMP_T where CAMPUS_CD in('TS', 'NN');
delete from PM_INSTITUTION_T where pm_institution_id >= 5000;
delete from PM_PSTN_RPT_TYP_T where pm_pstn_rpt_typ_id >= 5000;

insert into PM_PSTN_RPT_TYP_T (`pm_pstn_rpt_typ_id`, `pstn_rpt_typ`, `description`, `institution`, `campus`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRT', null, 'testInt', 'TS', '2012-01-01', 'Y', now(), uuid(), '1');
insert into KRLC_CMP_T (`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('TS', null, null, null, uuid(), '1', 'Y');
insert into KRLC_CMP_T (`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('NN', null, null, null, uuid(), '1', 'Y');
-- insert into PM_INSTITUTION_T (`pm_institution_id`, `effdt`, `institution_code`, `description`, `active`, `timestamp`) values ('5000', '2012-01-01', 'testInst', 'test', 'Y', now());