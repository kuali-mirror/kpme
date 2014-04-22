--
-- Copyright 2004-2014 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

delete from PM_INSTITUTION_T where pm_institution_id >= 5000;
delete from KRLC_CMP_T where campus_cd = 'TS';
-- delete from HR_LOCATION_T where LOCATION = 'BL';
delete from PM_PSTN_RPT_TYP_T where pm_pstn_rpt_typ_id >= 5000;
delete from PM_PSTN_RPT_CAT_T where pm_pstn_rpt_cat_id >= 5000;
delete from PM_PSTN_RPT_SUB_CAT_T where pm_pstn_rpt_sub_cat_id >= 5000;
delete from PM_PSTN_RPT_GRP_T where pm_pstn_rpt_grp_id >= 5000;
delete from PM_PSTN_QLFCTN_VL_T where PM_PSTN_QLFCTN_VL_ID >= 5000;
delete from HR_PAY_GRADE_T where HR_PAY_GRADE_ID >= 5000;
delete from HR_DEPT_AFFL_T where HR_DEPT_AFFL_ID >= 5000;
delete from PM_PSTN_TYP_T where PM_PSTN_TYP_ID >= 5000;
delete from PM_PSTN_APPOINTMENT_T where PM_PSTN_APPOINTMENT_ID >=5000

insert into PM_INSTITUTION_T (`pm_institution_id`, `effdt`, `institution_code`, `description`, `active`, `timestamp`) values ('5000', '2012-01-01', 'testInst', 'test', 'Y', now());
insert into KRLC_CMP_T(`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('TS', 'testCam', 'testCam', null, uuid(), '1', 'Y');
-- INSERT INTO HR_LOCATION_T (HR_LOCATION_ID, LOCATION, DESCRIPTION, EFFDT, TIMESTAMP, TIMEZONE, ACTIVE) VALUES('1000','BL','Bloomington','2010-01-01','2012-08-21 09:44:28.0','America/Indianapolis','Y');
insert into PM_PSTN_RPT_TYP_T (`pm_pstn_rpt_typ_id`, `pstn_rpt_typ`, `description`, `grp_key_cd`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRT', null, 'testGK', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_RPT_CAT_T (`pm_pstn_rpt_cat_id`, `pstn_rpt_cat`, `pstn_rpt_type`, `description`, `grp_key_cd`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRC', 'testPRT', null, 'testGK', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_RPT_SUB_CAT_T (`pm_pstn_rpt_sub_cat_id`, `pstn_rpt_sub_cat`, `pstn_rpt_cat`, `pstn_rpt_type`, `description`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr`, `GRP_KEY_CD`) values ('5000', 'testPRSC', 'testPRC', 'testPRT', null,  '2012-01-01', 'Y', now(), uuid(), '1', 'testInst-BL');
insert into PM_PSTN_RPT_GRP_T (`pm_pstn_rpt_grp_id`, `pstn_rpt_grp`, `description`, `GRP_KEY_CD`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRG', null, 'DEFAULT', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_QLFCTN_VL_T(`PM_PSTN_QLFCTN_VL_ID`, `VL_NM`, `OBJ_ID`, `VER_NBR`) values ('5000', 'existing', uuid(), '1');
insert into HR_PAY_GRADE_T(`HR_PAY_GRADE_ID`,`PAY_GRADE`,`DESCRIPTION`,`TIMESTAMP`,`ACTIVE`,`EFFDT`,`SAL_GROUP`,`INSTITUTION`,`LOCATION`) values ('5000','T','test',now(),'Y','2012-01-01','testSalGrp','testInst','BL');
insert into HR_DEPT_AFFL_T(`HR_DEPT_AFFL_ID`,`DEPT_AFFL_TYP`,`EFFDT`,`PRIMARY_INDICATOR`,`ACTIVE`,`VER_NBR`) values ('5000','testType','2012-01-01','Y','Y',1);
insert into PM_PSTN_TYP_T(`PM_PSTN_TYP_ID`,`PSTN_TYP`,`INSTITUTION`,`LOCATION`,`EFFDT`,`ACTIVE`,`VER_NBR`) values ('5000','testTyp','testInst','BL','2012-01-01','Y',1);

insert into PM_PSTN_APPOINTMENT_T(`PM_PSTN_APPOINTMENT_ID`,`PSTN_APPOINTMENT`,`DESCRIPTION`,`GRP_KEY_CD`,`EFFDT`,`ACTIVE`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`USER_PRINCIPAL_ID`) values ('5000','noWildCard','noWildCard','UGA-ATHENS','2010-01-01','Y',now(),1,1,'admin');
