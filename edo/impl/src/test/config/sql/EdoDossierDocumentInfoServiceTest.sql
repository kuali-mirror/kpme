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

delete from EDO_DOSSIER_DOC_INFO_T where edo_document_id >= '1000';

INSERT INTO EDO_DOSSIER_DOC_INFO_T(`edo_document_id`,`edo_dossier_id`,`DOCUMENT_STATUS`,`DOC_TYPE_NAME`, `principal_id`, `VER_NBR`, `OBJ_ID`) VALUES ('1000', '1000', 'P', 'documentTypeName1', 'admin',1, uuid());
INSERT INTO EDO_DOSSIER_DOC_INFO_T(`edo_document_id`,`edo_dossier_id`,`DOCUMENT_STATUS`,`DOC_TYPE_NAME`, `principal_id`, `VER_NBR`, `OBJ_ID`) VALUES ('1001', '1111', 'T', 'documentTypeName2', 'principal2',1, uuid());
INSERT INTO EDO_DOSSIER_DOC_INFO_T(`edo_document_id`,`edo_dossier_id`,`DOCUMENT_STATUS`,`DOC_TYPE_NAME`, `principal_id`, `VER_NBR`, `OBJ_ID`) VALUES ('1002', '1111', 'P', 'documentTypeName2', 'principal2',1, uuid());
