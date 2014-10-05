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

delete from EDO_ROLE_RESPONSIBILITY_DEF_T where EDO_KIM_ROLE_RESPONSIBILITY_ID >= '1000';

INSERT INTO EDO_ROLE_RESPONSIBILITY_DEF_T(`EDO_KIM_ROLE_RESPONSIBILITY_ID`,`KIM_ROLE_NAME`, `KIM_RESPONSIBILITY_NAME`, `KIM_ACTION_TYPE_CODE`, `KIM_ACTION_POLICY_CODE`, `KIM_PRIORITY`, `KIM_FORCE_ACTION`, `VER_NBR`, `OBJ_ID`) VALUES ('1000', 'Department Admin', 'Approver', 'A', '', 1, 'Y', 1, uuid());
INSERT INTO EDO_ROLE_RESPONSIBILITY_DEF_T(`EDO_KIM_ROLE_RESPONSIBILITY_ID`,`KIM_ROLE_NAME`, `KIM_RESPONSIBILITY_NAME`, `KIM_ACTION_TYPE_CODE`, `KIM_ACTION_POLICY_CODE`, `KIM_PRIORITY`, `KIM_FORCE_ACTION`, `VER_NBR`, `OBJ_ID`) VALUES ('1001', 'School Admin', 'Approver', 'A', '', 2, 'N', 1, uuid());
INSERT INTO EDO_ROLE_RESPONSIBILITY_DEF_T(`EDO_KIM_ROLE_RESPONSIBILITY_ID`,`KIM_ROLE_NAME`, `KIM_RESPONSIBILITY_NAME`, `KIM_ACTION_TYPE_CODE`, `KIM_ACTION_POLICY_CODE`, `KIM_PRIORITY`, `KIM_FORCE_ACTION`, `VER_NBR`, `OBJ_ID`) VALUES ('1002', 'Institution Admin', 'Approver', 'A', '', 3, 'N', 1, uuid());
INSERT INTO EDO_ROLE_RESPONSIBILITY_DEF_T(`EDO_KIM_ROLE_RESPONSIBILITY_ID`,`KIM_ROLE_NAME`, `KIM_RESPONSIBILITY_NAME`, `KIM_ACTION_TYPE_CODE`, `KIM_ACTION_POLICY_CODE`, `KIM_PRIORITY`, `KIM_FORCE_ACTION`, `VER_NBR`, `OBJ_ID`) VALUES ('1003', 'Department Admin', 'Reviewer', 'K', '', 4, 'Y', 1, uuid());
