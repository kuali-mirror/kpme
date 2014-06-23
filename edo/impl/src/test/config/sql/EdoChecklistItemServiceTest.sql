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

delete from EDO_CHECKLIST_ITEM_T where EDO_CHECKLIST_ITEM_ID = 'EDO_CHECKLIST_ITEM_ID_0001';
delete from EDO_CHECKLIST_ITEM_T where EDO_CHECKLIST_ITEM_ID = 'EDO_CHECKLIST_ITEM_ID_0002';

insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('EDO_CHECKLIST_ITEM_ID_0001', 'EDO_CHECKLIST_SECTION_ID_0001', 'testEdoChecklistItem1', 'Testing Immutable EdoChecklistItem', 'Y', 1, '2012-01-01', 'Y', now(), null, '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('EDO_CHECKLIST_ITEM_ID_0002', 'EDO_CHECKLIST_SECTION_ID_0001', 'testEdoChecklistItem2', 'Testing Immutable EdoChecklistItem', 'Y', 2, '2012-01-01', 'Y', now(), null, '1');
