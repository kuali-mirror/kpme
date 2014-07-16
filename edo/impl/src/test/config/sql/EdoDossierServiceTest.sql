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

delete from EDO_DOSSIER_T where edo_dossier_id >= '1000';

INSERT INTO EDO_DOSSIER_T(`edo_dossier_id`,`edo_dossier_type_id`,`edo_checklist_id`, `candidate_principalname`, `department_id`, `secondary_unit`, `aoe_code`,`current_rank`,`rank_sought`, `due_date`, `dossier_status`,`workflow_id`,`USER_PRINCIPAL_ID`,`GRP_KEY_CD`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`, `ORG_CD`) VALUES ('1000', '1000','1000', 'admin', 'deptID', 'sDeptId', 'T','Asst', 'Asso', '2014-12-01', 'submitted', '1', 'admin', 'UGA-GA', 1, uuid(), now(), 'UA');
INSERT INTO EDO_DOSSIER_T(`edo_dossier_id`,`edo_dossier_type_id`,`edo_checklist_id`, `candidate_principalname`, `department_id`, `secondary_unit`, `aoe_code`,`current_rank`,`rank_sought`, `due_date`, `dossier_status`,`workflow_id`,`USER_PRINCIPAL_ID`,`GRP_KEY_CD`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`, `ORG_CD`) VALUES ('1001', '1000','1000', 'earncode', 'deptID', 'sDeptId', 'T','Asst', 'Asso', '2014-12-01', 'submitted', '1', 'earncode', 'IU-IN', 1, uuid(), now(), 'UA');

