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

delete from PM_PSTN_APPOINTMENT_T where PSTN_APPOINTMENT = 'testAppointment';
#delete from KRLC_CMP_T where CAMPUS_CD = 'TS';
delete from PM_INSTITUTION_T where pm_institution_id >= 5000;
DELETE FROM HR_LOCATION_T WHERE HR_LOCATION_ID >= 1000;

#insert into KRLC_CMP_T (`campus_cd`, `campus_nm`, `campus_shrt_nm`, `campus_typ_cd`, `obj_id`, `ver_nbr`, `actv_ind`) values ('TS', null, null, null, uuid(), '1', 'Y');
INSERT INTO HR_LOCATION_T (HR_LOCATION_ID, LOCATION, DESCRIPTION, EFFDT, TIMESTAMP, TIMEZONE, ACTIVE) VALUES('1000','BL','Bloomington','2010-01-01','2012-08-21 09:44:28.0','America/Indianapolis','Y');
insert into PM_INSTITUTION_T (`pm_institution_id`, `effdt`, `institution_code`, `description`, `active`, `timestamp`) values ('5000', '2012-01-01', 'testInst', 'test', 'Y', now());
insert into PM_PSTN_APPOINTMENT_T (`pm_pstn_appointment_id`, `pstn_appointment`, `description`, `institution`, `location`, `effdt`, `active`, `timestamp`, `obj_id`, `ver_nbr`) values ('123456789', 'testAppointment', 'This is a test appointment description', 'testInst', 'BL', '2012-01-01', 'Y', now(), null, '1');
