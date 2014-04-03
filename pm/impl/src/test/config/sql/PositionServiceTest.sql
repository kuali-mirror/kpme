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

delete from HR_POSITION_T where HR_POSITION_ID = '1';

INSERT INTO hr_position_t(`HR_POSITION_ID`,`POSITION_NBR`,`DESCRIPTION`, `INSTITUTION`,`LOCATION`, EFFDT, PSTN_TYP, CL_TTL, POOL_ELIG, PSTN_STATUS, ACTIVE) VALUES ('1', '1','testPosition','IU', 'IN', '2010-01-01', 'HOURLY', 'HOURLY', 'N', 'NEW','Y');
