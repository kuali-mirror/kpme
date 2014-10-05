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

delete from EDO_GROUP_DEF_T where EDO_GROUP_ID >= '1000';

insert into EDO_GROUP_DEF_T (`EDO_GROUP_ID`, `EDO_WORKFLOW_ID`, `WORKFLOW_LEVEL`, `DOSSIER_TYPE`, `WORKFLOW_TYPE`, `KIM_TYPE_NAME`, `KIM_ROLE_NAME`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', '1000', '01', 'PT', 'DEFAULT', 'DEFAULT', 'DEFAULT', '2012-01-01', 'Y', now(), null, '1');
insert into EDO_GROUP_DEF_T (`EDO_GROUP_ID`, `EDO_WORKFLOW_ID`, `WORKFLOW_LEVEL`, `DOSSIER_TYPE`, `WORKFLOW_TYPE`, `KIM_TYPE_NAME`, `KIM_ROLE_NAME`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1001', '1000', '02', 'PT', 'DEFAULT', 'DEFAULT', 'DEFAULT', '2012-01-01', 'Y', now(), null, '1');

