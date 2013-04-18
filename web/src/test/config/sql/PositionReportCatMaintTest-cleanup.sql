delete from PM_PSTN_RPT_CAT_T where pstn_rpt_CAT = 'testPRC';
delete from KRLC_CMP_T where CAMPUS_CD in('TS', 'NN');
delete from PM_INSTITUTION_T where pm_institution_id >= 5000;
delete from PM_PSTN_RPT_TYP_T where pm_pstn_rpt_typ_id >= 5000;