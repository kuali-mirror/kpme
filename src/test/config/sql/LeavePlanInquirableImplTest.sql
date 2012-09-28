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

delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5000';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5001';
delete from lm_leave_plan_t where LM_LEAVE_PLAN_ID = '5002';

insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('5000',	'TestLeavePlan', 'staff monthly', '01/01', '2011-12-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', 'Y', '2011-12-09 11:38:04', 'Feb');
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('5001',	'TestLeavePlan', 'staff monthly', '01/01', '2012-01-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '2', 'Y', '2012-01-01 11:38:04', 'Feb');
insert into lm_leave_plan_t (`lm_leave_plan_id`, `LEAVE_PLAN`, `DESCR`, `CAL_YEAR_START`, `EFFDT`, `OBJ_ID`, `VER_NBR`, `ACTIVE`, `TIMESTAMP`, `PLANNING_MONTHS`) values ('5002',	'TestLeavePlan', 'staff monthly', '01/01', '2012-03-01', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '3', 'Y', '2012-02-09 11:38:04', 'Feb');