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

delete from EDO_COMMITTEES_READY_T where EDO_COMMITTEES_READY_ID >= '1000';

insert into EDO_COMMITTEES_READY_T (`EDO_COMMITTEES_READY_ID`, `READY`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('1000', 'Y', now(), uuid(), '1', 'ISU-IA');
insert into EDO_COMMITTEES_READY_T (`EDO_COMMITTEES_READY_ID`, `READY`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('1001', 'N', now(), uuid(), '1', 'IU-IN');




