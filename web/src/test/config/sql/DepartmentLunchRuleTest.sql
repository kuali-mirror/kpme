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

# delete from hr_principal_attributes_t where principal_id in ('edna');
insert into hr_job_t (hr_job_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, dept, HR_SAL_GROUP, pay_grade, TIMESTAMP, OBJ_ID, VER_NBR, comp_rate, location, std_hours, hr_paytype, active, primary_indicator, position_nbr, eligible_for_leave, FLSA_STATUS) values ('2000', 'edna', '1', '2010-01-01', 'TEST-DEPT', 'SD1', 'SD1', now(), uuid(), '1', '0.000000', 'SD1', '40.00', 'BW', 'Y',  'Y', 'N', 'Y', 'NE');
insert into tk_assignment_t (tk_assignment_id, PRINCIPAL_ID, JOB_NUMBER, EFFDT, WORK_AREA, TASK, OBJ_ID, VER_NBR, active, timestamp) values('2000', 'edna', '1', '2010-01-01', '1234', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values('5001','edna', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);
# insert into hr_principal_attributes_t (hr_principal_attribute_id, principal_id, pay_calendar, leave_plan, service_date, fmla_eligible, workers_eligible, timezone, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, active, leave_calendar) values('1009','edna', 'BWS-CAL', NULL,'2010-01-01', 'Y','Y',null, '2010-01-01',now(), uuid(), '1', 'Y', null);
