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

delete from tk_clock_log_t where tk_clock_log_id = '5000';

/* clock log */
insert into tk_clock_log_t (TK_CLOCK_LOG_ID,PRINCIPAL_ID,JOB_NUMBER,WORK_AREA,TASK,TK_WORK_AREA_ID,TK_TASK_ID,CLOCK_TS,CLOCK_TS_TZ,CLOCK_ACTION,IP_ADDRESS,USER_PRINCIPAL_ID,TIMESTAMP,HR_JOB_ID,OBJ_ID, unapproved_ip) values ('5000','testUser',30,30,30,'1','1','2012-03-01 08:08:08','America/Indianapolis','CO','TEST','admin','2012-03-01 08:08:08','1','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', 'Y');

