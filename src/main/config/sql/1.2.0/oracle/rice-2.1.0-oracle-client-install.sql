--
-- Copyright 2004-2012 The Kuali Foundation
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




-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-19.sql
-- 


CREATE TABLE KRNS_MAINT_DOC_ATT_LST_T  (
    ATT_ID      VARCHAR2(40) NOT NULL,
	DOC_HDR_ID	VARCHAR2(14) NOT NULL,
	ATT_CNTNT 	BLOB NOT NULL,
	FILE_NM   	VARCHAR2(150) NULL,
	CNTNT_TYP 	VARCHAR2(255) NULL,
	OBJ_ID    	VARCHAR2(36) NOT NULL,
	VER_NBR   	NUMBER(8) DEFAULT 0 NOT NULL,
	PRIMARY KEY(ATT_ID),
	CONSTRAINT KRNS_MAINT_DOC_ATT_LST_FK1 FOREIGN KEY (DOC_HDR_ID) REFERENCES KRNS_MAINT_DOC_T (DOC_HDR_ID)
)
/
ALTER TABLE KRNS_MAINT_DOC_ATT_LST_T
	ADD CONSTRAINT KRNS_MAINT_DOC_ATT_LST_TC0
	UNIQUE (OBJ_ID)
/
CREATE INDEX KRNS_MAINT_DOC_ATT_LST_TI1 ON KRNS_MAINT_DOC_ATT_LST_T (DOC_HDR_ID)
/
CREATE SEQUENCE KRNS_MAINT_DOC_ATT_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/





-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-17.sql
-- 



-- KULRICE-7237: KRNS_NTE_T is selected by a field with no indexes - full table scan every time
CREATE INDEX KRNS_NTE_TI1 ON KRNS_NTE_T (RMT_OBJ_ID)
/
