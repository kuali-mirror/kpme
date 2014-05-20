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

update hr_job_t set std_hours='0.00' where PRINCIPAL_ID='admin'
-- delete from hr_earn_code_security_t where hr_earn_code_security_id >= 500;
-- insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `GRP_KEY_CD`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('2000', 'eric', '30', '2010-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'IU-IN', '40.00', 'BW','Y',  'Y', 'N', 'Y', 'E');
insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `GRP_KEY_CD`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('2000', 'eric', '30', '2010-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'IU-IN', '40.00', 'BW','Y',  'Y', 'N', 'Y', 'E');

-- insert into hr_earn_code_security (hr_EARN_CODE_SECURITY_ID,DEPT,hr_sal_group,EARN_CODE,EMPLOYEE,APPROVER,EFFDT,TIMESTAMP,ACTIVE,USER_PRINCIPAL_ID,LOCATION,EARN_CODE_TYPE,GRP_KEY_CD) values (500,'LORA-DEPT','A10','RGH','Y','Y','2010-01-01','2010-01-01 08:08:08','Y','admin','IN','T','IU-IN');

