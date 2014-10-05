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

delete from EDO_SUPP_REVIEW_LAYER_DEF_T where EDO_SUPP_REVIEW_LAYER_DEF_ID >= '1000';

INSERT INTO EDO_SUPP_REVIEW_LAYER_DEF_T(`EDO_SUPP_REVIEW_LAYER_DEF_ID`,`EDO_REVIEW_LAYER_DEF_ID`, `SUPPLEMENTAL_NODE_NAME`, `ACKNOWLEDGE_FLAG`, `VER_NBR`, `OBJ_ID`) VALUES ('1000', '1000', 'suppDeanAck', 'Y', 1, uuid());
INSERT INTO EDO_SUPP_REVIEW_LAYER_DEF_T(`EDO_SUPP_REVIEW_LAYER_DEF_ID`,`EDO_REVIEW_LAYER_DEF_ID`, `SUPPLEMENTAL_NODE_NAME`, `ACKNOWLEDGE_FLAG`, `VER_NBR`, `OBJ_ID`) VALUES ('1001', '1000', 'suppDean', 'N', 1, uuid());
