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

delete from EDO_REVIEW_LAYER_DEF_T where edo_review_layer_def_id >= '1000';

INSERT INTO EDO_REVIEW_LAYER_DEF_T(`edo_review_layer_def_id`,`node_name`, `vote_type`, `DESCRIPTION`, `review_letter`, `route_level`, `review_level`, `WORKFLOW_ID`, `WORKFLOW_QUALIFIER`, `USER_PRINCIPAL_ID`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('1000', 'nodeName', 'T', 'DESC', 'Y', '1', '1', '1000', 'qualifier', 'admin', 1, uuid(), now());
INSERT INTO EDO_REVIEW_LAYER_DEF_T(`edo_review_layer_def_id`,`node_name`, `vote_type`, `DESCRIPTION`, `review_letter`, `route_level`, `review_level`, `WORKFLOW_ID`, `WORKFLOW_QUALIFIER`, `USER_PRINCIPAL_ID`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('1001', 'nodeName1', 'T', 'DESC1', 'Y', '1', '1', '1001', 'qualifier1', 'admin', 1, uuid(), now());


INSERT INTO EDO_REVIEW_LAYER_DEF_T(`edo_review_layer_def_id`,`node_name`, `vote_type`, `DESCRIPTION`, `review_letter`, `route_level`, `review_level`, `WORKFLOW_ID`, `WORKFLOW_QUALIFIER`, `USER_PRINCIPAL_ID`,`VER_NBR`, `OBJ_ID`, `TIMESTAMP`) VALUES ('1010', 'nodeName', 'P', 'DESC', 'Y', '1', '1', '1000', 'qualifier', 'admin', 1, uuid(), now());
