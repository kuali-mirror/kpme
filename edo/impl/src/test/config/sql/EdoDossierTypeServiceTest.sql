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

delete from EDO_DOSSIER_TYPE_T where edo_dossier_type_id >= '1000';

INSERT INTO EDO_DOSSIER_TYPE_T(`edo_dossier_type_id`,`dossier_type_code`,`doc_type_name`, `dossier_type_name`, `EFFDT`, `ACTIVE`, `VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('1000', 'aa','docTypeName0', 'dossierTypeName0', '2014-07-01', 'Y', 1, uuid(), now());
INSERT INTO EDO_DOSSIER_TYPE_T(`edo_dossier_type_id`,`dossier_type_code`,`doc_type_name`, `dossier_type_name`, `EFFDT`, `ACTIVE`, `VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('1001', 'bb','docTypeName1', 'dossierTypeName1', '2014-07-01', 'Y', 1, uuid(), now());

