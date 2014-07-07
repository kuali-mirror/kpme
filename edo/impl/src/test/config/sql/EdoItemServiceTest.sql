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

delete from EDO_ITEM_T where EDO_ITEM_ID = 'EDO_ITEM_ID_0001';
delete from EDO_ITEM_T where EDO_ITEM_ID = 'EDO_ITEM_ID_0002';
delete from EDO_ITEM_T where EDO_ITEM_ID = 'EDO_ITEM_ID_0003';

insert into EDO_ITEM_T (`edo_item_id`, `edo_dossier_id`, `edo_checklist_item_id`, `file_name`, `file_location`, `notes`, `routed`, `edo_item_type_id`, `content_type`, `row_index`, `edo_review_layer_def_id`, `file_description`, `action`, `USER_PRINCIPAL_ID`, `ACTIVE`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('EDO_ITEM_ID_0001', 'EDO_DOSSIER_ID_0001', 'EDO_CHECKLIST_ITEM_ID_0001', '', '', '', 'Y', 'EDO_ITEM_TYPE_ID_0001', '', 1, 'EDO_REV_LAYER_ID_0001', 'testEdoItemFileDescription', 'UPLOAD FILE', 'admin', 'Y', now(), null, '1');
insert into EDO_ITEM_T (`edo_item_id`, `edo_dossier_id`, `edo_checklist_item_id`, `file_name`, `file_location`, `notes`, `routed`, `edo_item_type_id`, `content_type`, `row_index`, `edo_review_layer_def_id`, `file_description`, `action`, `USER_PRINCIPAL_ID`, `ACTIVE`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('EDO_ITEM_ID_0002', 'EDO_DOSSIER_ID_0001', 'EDO_CHECKLIST_ITEM_ID_0001', '', '', '', 'N', 'EDO_ITEM_TYPE_ID_0001', '', 2, '', 'testEdoItemFileDescription', 'UPLOAD FILE', 'admin', 'Y', now(), null, '1');
insert into EDO_ITEM_T (`edo_item_id`, `edo_dossier_id`, `edo_checklist_item_id`, `file_name`, `file_location`, `notes`, `routed`, `edo_item_type_id`, `content_type`, `row_index`, `edo_review_layer_def_id`, `file_description`, `action`, `USER_PRINCIPAL_ID`, `ACTIVE`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('EDO_ITEM_ID_0003', 'EDO_DOSSIER_ID_0001', 'EDO_CHECKLIST_ITEM_ID_0001', '', '', '', 'N', 'EDO_ITEM_TYPE_ID_0002', '', 3, '', 'testEdoItemFileDescription', 'UPLOAD FILE', 'admin', 'Y', now(), null, '1');

