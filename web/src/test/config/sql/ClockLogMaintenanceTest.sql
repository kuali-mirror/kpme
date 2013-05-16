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

delete from tk_clock_log_t;
insert into tk_clock_log_t (TK_CLOCK_LOG_ID,PRINCIPAL_ID,JOB_NUMBER,WORK_AREA,TASK,CLOCK_TS,CLOCK_TS_TZ,CLOCK_ACTION,IP_ADDRESS,USER_PRINCIPAL_ID,TIMESTAMP,OBJ_ID) values ('1','admin',30,30,30,'2010-01-01 08:08:08','America/Indianapolis','CO','TEST','admin','2010-01-01 08:08:08','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97');