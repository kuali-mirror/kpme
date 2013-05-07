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

DELETE FROM TK_SHIFT_DIFFERENTIAL_RL_T WHERE TK_SHIFT_DIFF_RL_ID >= 1000;

INSERT INTO TK_SHIFT_DIFFERENTIAL_RL_T (TK_SHIFT_DIFF_RL_ID,LOCATION,HR_SAL_GROUP,PAY_GRADE,EARN_CODE,BEGIN_TS,END_TS,MIN_HRS,PY_CALENDAR_GROUP,FROM_EARN_GROUP,MAX_GAP,SUN,MON,TUE,WED,THU,FRI,SAT,EFFDT,TIMESTAMP,USER_PRINCIPAL_ID,ACTIVE) VALUES ('1000','BL','PAO','2IT','SRD','15:00:00','8:00:00',6,'IU','T30',75,'Y','Y','Y','Y','Y','Y','Y','2011-01-01','2011-10-14 14:08:20','admin','Y');
INSERT INTO TK_SHIFT_DIFFERENTIAL_RL_T (TK_SHIFT_DIFF_RL_ID,LOCATION,HR_SAL_GROUP,PAY_GRADE,EARN_CODE,BEGIN_TS,END_TS,MIN_HRS,PY_CALENDAR_GROUP,FROM_EARN_GROUP,MAX_GAP,SUN,MON,TUE,WED,THU,FRI,SAT,EFFDT,TIMESTAMP,USER_PRINCIPAL_ID,ACTIVE) VALUES ('1001','IN','CL','4','SRD','15:00:00','8:00:00',6,'IU','T30',75,'Y','Y','Y','Y','Y','Y','Y','2011-01-01','2011-10-14 14:15:23','admin','Y');
