--
-- Copyright 2005-2013 The Kuali Foundation
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

--
--     KULRICE-9179: KRAD_MSG_T not in client dataset upgrade scripts
--

CREATE TABLE IF NOT EXISTS KRAD_MSG_T
(
	NMSPC_CD VARCHAR(20) NOT NULL,
	CMPNT_CD VARCHAR(100) NOT NULL,
	MSG_KEY VARCHAR(100) NOT NULL,
	LOC VARCHAR(80) NOT NULL,
	OBJ_ID VARCHAR(36) NOT NULL,
	VER_NBR DECIMAL(8) NOT NULL DEFAULT 1,
	MSG_DESC VARCHAR(255),
	TXT VARCHAR(4000),
	PRIMARY KEY (NMSPC_CD,CMPNT_CD,MSG_KEY,loc),
	UNIQUE KRAD_MSG_TC0(OBJ_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;
