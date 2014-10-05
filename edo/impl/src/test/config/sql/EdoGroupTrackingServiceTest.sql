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

delete from EDO_GROUP_TRACKING_T where EDO_GROUP_TRACKING_ID >= '1000';

insert into EDO_GROUP_TRACKING_T (`EDO_GROUP_TRACKING_ID`, `EDO_WORKFLOW_ID`, `DEPARTMENT_ID`, `ORG_CD`, `REVIEW_LEVEL_NAME`, `KIM_GROUP_NAME`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('1000', '1000', 'DEFAULT_DEPT', 'DEFAULT_OG', 'DEPARTMENT', 'DEPARTMENT', '2012-01-01', 'Y', now(), null, '1', 'ISU-IA');
insert into EDO_GROUP_TRACKING_T (`EDO_GROUP_TRACKING_ID`, `EDO_WORKFLOW_ID`, `DEPARTMENT_ID`, `ORG_CD`, `REVIEW_LEVEL_NAME`, `KIM_GROUP_NAME`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('1001', '1000', 'DEFAULT_DEPT', 'DEFAULT_OG', 'SCHOOL', 'SCHOOL', '2012-01-01', 'Y', now(), null, '1', 'IU-IN');
insert into EDO_GROUP_TRACKING_T (`EDO_GROUP_TRACKING_ID`, `EDO_WORKFLOW_ID`, `DEPARTMENT_ID`, `ORG_CD`, `REVIEW_LEVEL_NAME`, `KIM_GROUP_NAME`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('1002', '1000', 'DEFAULT_DEPT', 'DEFAULT_OG', 'DEAN', 'DEAN', '2012-01-01', 'Y', now(), null, '1', 'IU-IN');

