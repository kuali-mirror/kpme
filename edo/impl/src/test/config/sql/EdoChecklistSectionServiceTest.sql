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

delete from EDO_CHECKLIST_SECTION_T where EDO_CHECKLIST_SECTION_ID >= '1000';
delete from EDO_CHECKLIST_ITEM_T where EDO_CHECKLIST_ITEM_ID >= '2000';

insert into EDO_CHECKLIST_SECTION_T (`EDO_CHECKLIST_SECTION_ID`, `EDO_CHECKLIST_ID`, `CHECKLIST_SECTION_NAME`, `DESCRIPTION`, `CHECKLIST_SECTION_ORDINAL`, `OBJ_ID`, `VER_NBR`) values ('1000', '1000', 'testEdoChecklistSection1', 'Testing Immutable EdoChecklistSection', 1, null, '1');
insert into EDO_CHECKLIST_SECTION_T (`EDO_CHECKLIST_SECTION_ID`, `EDO_CHECKLIST_ID`, `CHECKLIST_SECTION_NAME`, `DESCRIPTION`, `CHECKLIST_SECTION_ORDINAL`, `OBJ_ID`, `VER_NBR`) values ('1001', '1000', 'testEdoChecklistSection2', 'Testing Immutable EdoChecklistSection', 2, null, '1');

insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `OBJ_ID`, `VER_NBR`) values ('2000', '1000', 'testEdoChecklistItem1', 'Testing Immutable EdoChecklistItem1', 'Y', 1, uuid(), '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `OBJ_ID`, `VER_NBR`) values ('2001', '1000', 'testEdoChecklistItem2', 'Testing Immutable EdoChecklistItem2', 'Y', 2, uuid(), '1');
insert into EDO_CHECKLIST_ITEM_T (`EDO_CHECKLIST_ITEM_ID`, `EDO_CHECKLIST_SECTION_ID`, `CHECKLIST_ITEM_NAME`, `DESCRIPTION`, `REQUIRED`, `CHECKLIST_ITEM_ORDINAL`, `OBJ_ID`, `VER_NBR`) values ('2002', '1000', 'testEdoChecklistItem3', 'Testing Immutable EdoChecklistItem3', 'Y', 3, uuid(), '1');
