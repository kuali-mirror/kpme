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

delete from EDO_CHECKLIST_ITEM_T where EDO_CHECKLIST_ITEM_ID >= '1000';
delete from EDO_CHECKLIST_T where EDO_CHECKLIST_ID >= '2000';
delete from EDO_CHECKLIST_SECTION_T where EDO_CHECKLIST_SECTION_ID >= '2000';
delete from EDO_DOSSIER_T where edo_dossier_id >= '2000';

insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', '2000', 'testEdoChecklistItem1', 'Testing Immutable EdoChecklistItem1', 'Y', 1, '2012-01-01', 'Y', now(), null, '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1001', '2000', 'testEdoChecklistItem2', 'Testing Immutable EdoChecklistItem2', 'Y', 2, '2012-01-01', 'Y', now(), null, '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1002', '2001', 'testEdoChecklistItem3', 'Testing Immutable EdoChecklistItem3', 'Y', 1, '2012-01-01', 'Y', now(), null, '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1003', '2001', 'testEdoChecklistItem4', 'Testing Immutable EdoChecklistItem4', 'Y', 2, '2012-01-01', 'Y', now(), null, '1');

insert into EDO_CHECKLIST_T (`EDO_CHECKLIST_ID`, `DOSSIER_TYP_CD`, `DEPARTMENT_ID`, `ORG_CD`, `DESCRIPTION`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('2000', 'TA', 'DEFAULT', 'DEFAULT', 'Testing Immutable EdoChecklist', '2012-01-01', 'Y', now(), null, '1', 'IU-IN');
insert into EDO_CHECKLIST_T (`EDO_CHECKLIST_ID`, `DOSSIER_TYP_CD`, `DEPARTMENT_ID`, `ORG_CD`, `DESCRIPTION`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`, `GRP_KEY_CD`) values ('2001', 'TA', 'ENGINEERING', 'DEFAULT', 'Testing Immutable EdoChecklist', '2012-01-01', 'Y', now(), null, '1', 'IU-IN');

insert into EDO_CHECKLIST_SECTION_T (`EDO_CHECKLIST_SECTION_ID`, `EDO_CHECKLIST_ID`, `CHECKLIST_SECTION_NAME`, `DESCRIPTION`, `CHECKLIST_SECTION_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('2000', '2001', 'testEdoChecklistSection1', 'Testing Immutable EdoChecklistSection', 1, '2012-01-01', 'Y', now(), null, '1');
insert into EDO_CHECKLIST_SECTION_T (`EDO_CHECKLIST_SECTION_ID`, `EDO_CHECKLIST_ID`, `CHECKLIST_SECTION_NAME`, `DESCRIPTION`, `CHECKLIST_SECTION_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('2001', '2001', 'testEdoChecklistSection2', 'Testing Immutable EdoChecklistSection', 2, '2012-01-01', 'Y', now(), null, '1');

INSERT INTO EDO_DOSSIER_T(`edo_dossier_id`,`edo_dossier_type_id`,`edo_checklist_id`, `candidate_principalname`, `department_id`, `secondary_unit`, `aoe_code`,`current_rank`,`rank_sought`, `due_date`, `dossier_status`,`workflow_id`,`USER_PRINCIPAL_ID`,`GRP_KEY_CD`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('2000', '1000', '2001', 'admin', 'deptID', 'sDeptId', 'T','Asst', 'Asso', '2014-12-01', 'submitted', '1', 'admin', 'UGA-GA', 1, uuid(), now());
