--
-- Copyright 2004-2012 The Kuali Foundation
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

insert into hr_job_t (`hr_job_id`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `dept`, `HR_SAL_GROUP`, `pay_grade`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `comp_rate`, `location`, `std_hours`, `hr_paytype`, `active`, `primary_indicator`, `position_nbr`, `eligible_for_leave`, `FLSA_STATUS`) values ('2000', 'eric', '30', '2010-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW','Y',  'Y', 'N', 'Y', 'E');

insert into hr_roles_t (`hr_roles_id`, `principal_id`, `role_name`, `user_principal_id`, `work_area`, `dept`, `chart`, `effdt`, `timestamp`, `active`, `position_nbr`, `expdt`, `tk_work_area_id`) values ('2000', 'eric', 'TK_APPROVER', 'admin', '30', NULL, NULL, '2010-08-20', now(), 'Y', NULL, NULL, NULL);
insert into hr_roles_t (`hr_roles_id`, `principal_id`, `role_name`, `user_principal_id`, `work_area`, `dept`, `chart`, `effdt`, `timestamp`, `active`, `position_nbr`, `expdt`, `tk_work_area_id`) values ('2001', 'eric', 'TK_APPROVER', 'admin', '5555', NULL, NULL, '2010-08-20', now(), 'Y', NULL, NULL, NULL);
insert into hr_roles_t (`hr_roles_id`, `principal_id`, `role_name`, `user_principal_id`, `work_area`, `dept`, `chart`, `effdt`, `timestamp`, `active`, `position_nbr`, `expdt`, `tk_work_area_id`) values ('2002', 'eric', 'TK_APPROVER', 'admin', '1234', NULL, NULL, '2010-08-20', now(), 'Y', NULL, NULL, NULL);
insert into hr_roles_t (`hr_roles_id`, `principal_id`, `role_name`, `user_principal_id`, `work_area`, `dept`, `chart`, `effdt`, `timestamp`, `active`, `position_nbr`, `expdt`, `tk_work_area_id`) values ('2003', 'eric', 'TK_APPROVER', 'admin', '1000', NULL, NULL, '2010-08-20', now(), 'Y', NULL, NULL, NULL);
insert into hr_roles_t (`hr_roles_id`, `principal_id`, `role_name`, `user_principal_id`, `work_area`, `dept`, `chart`, `effdt`, `timestamp`, `active`, `position_nbr`, `expdt`, `tk_work_area_id`) values ('2004', 'eric', 'TK_APPROVER', 'admin', '1100', NULL, NULL, '2010-08-20', now(), 'Y', NULL, NULL, NULL);
