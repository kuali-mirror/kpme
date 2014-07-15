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
