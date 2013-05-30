--
-- Copyright 2004-2013 The Kuali Foundation
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
delete from HR_LOCATION_T where LOCATION = 'BL';
delete from PM_PSTN_RPT_TYP_T where pm_pstn_rpt_typ_id >= 5000;
delete from PM_PSTN_RPT_CAT_T where pm_pstn_rpt_cat_id >= 5000;
delete from PM_PSTN_RPT_SUB_CAT_T where pm_pstn_rpt_sub_cat_id >= 5000;
delete from PM_PSTN_RPT_GRP_T where pm_pstn_rpt_grp_id >= 5000;
delete from PM_PSTN_QLFCTN_VL_T where PM_PSTN_QLFCTN_VL_ID >= 5000;

insert into PM_INSTITUTION_T (`pm_institution_id`, `effdt`, `institution_code`, `description`, `active`, `timestamp`) values ('5000', '2012-01-01', 'testInst', 'test', 'Y', now());
insert into KRLC_CMP_T(`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('TS', 'testCam', 'testCam', null, uuid(), '1', 'Y');
INSERT INTO HR_LOCATION_T (HR_LOCATION_ID, LOCATION, DESCRIPTION, EFFDT, TIMESTAMP, TIMEZONE, ACTIVE) VALUES('1000','BL','Bloomington','2010-01-01','2012-08-21 09:44:28.0','America/Indianapolis','Y');
insert into PM_PSTN_RPT_TYP_T (`pm_pstn_rpt_typ_id`, `pstn_rpt_typ`, `description`, `institution`, `location`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRT', null, 'testInst', 'BL', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_RPT_CAT_T (`pm_pstn_rpt_cat_id`, `pstn_rpt_cat`, `pstn_rpt_type`, `description`, `institution`, `location`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRC', 'testPRT', null, 'testInst', 'BL', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_RPT_SUB_CAT_T (`pm_pstn_rpt_sub_cat_id`, `pstn_rpt_sub_cat`, `pstn_rpt_cat`, `pstn_rpt_type`, `description`, `institution`, `location`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRSC', 'testPRC', 'testPRT', null, 'testInst', 'BL', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_RPT_GRP_T (`pm_pstn_rpt_grp_id`, `pstn_rpt_grp`, `description`, `institution`, `location`, `effdt`, `active`,  `timestamp`, `obj_id`, `ver_nbr` ) values ('5000', 'testPRG', null, 'testInst', 'BL', '2012-01-01', 'Y', now(), uuid(), '1');
insert into PM_PSTN_QLFCTN_VL_T(`PM_PSTN_QLFCTN_VL_ID`, `VL_NM`, `OBJ_ID`, `VER_NBR`) values ('5000', 'existing', uuid(), '1');