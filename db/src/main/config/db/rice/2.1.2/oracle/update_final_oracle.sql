--
-- Copyright 2004-2013 The Kuali Foundation
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
-- 2012-09-26.sql
-- 


--
--    KULRICE-8300 & KULRICE-7799
--
--    NOTE NOTE -  This is the first time that the master database will have KRxxx as the IDs on some of it's tables.
--    This SQL accounts for that and should be error free.
--


INSERT INTO KRIM_TYP_T(KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
  VALUES('KR1000', sys_guid(), 1, 'Document Type, Route Node, and Route Status', 'documentTypeAndNodeAndRouteStatusPermissionTypeService', 'Y', 'KR-SYS')
/

INSERT INTO KRIM_TYP_ATTR_T(KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
  VALUES('KR1000', sys_guid(), 1, 'a',
  (select KIM_TYP_ID from KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and NMSPC_CD = 'KR-SYS'),
  (select KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T where NM = 'documentTypeName' and NMSPC_CD = 'KR-WKFLW'), 'Y')
/

INSERT INTO KRIM_TYP_ATTR_T(KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
  VALUES('KR1001',  sys_guid(), 1, 'b',
  (select KIM_TYP_ID from KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and NMSPC_CD = 'KR-SYS'),
  (select KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T where NM = 'routeNodeName' and NMSPC_CD = 'KR-WKFLW'), 'Y')
/

INSERT INTO KRIM_TYP_ATTR_T(KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
  VALUES('KR1002', sys_guid(), 1, 'c',
  (select KIM_TYP_ID from KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and NMSPC_CD = 'KR-SYS'),
  (select KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T where NM = 'routeStatusCode' and NMSPC_CD = 'KR-WKFLW'), 'Y')
/

INSERT INTO KRIM_PERM_TMPL_T (PERM_TMPL_ID,ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('KR1000', 'Y',
  (SELECT KIM_TYP_ID FROM KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and SRVC_NM = 'documentTypeAndNodeAndRouteStatusPermissionTypeService'),
  'Super User Approve Single Action Request', 'KR-WKFLW', sys_guid(), 1)
/

INSERT INTO KRIM_PERM_TMPL_T (PERM_TMPL_ID,ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('KR1001', 'Y',
  (SELECT KIM_TYP_ID FROM KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and SRVC_NM = 'documentTypeAndNodeAndRouteStatusPermissionTypeService'),
  'Super User Approve Document', 'KR-WKFLW', sys_guid(), 1)
/

INSERT INTO KRIM_PERM_TMPL_T (PERM_TMPL_ID,ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('KR1002','Y',
  (SELECT KIM_TYP_ID FROM KRIM_TYP_T where NM = 'Document Type, Route Node, and Route Status' and SRVC_NM = 'documentTypeAndNodeAndRouteStatusPermissionTypeService'),
  'Super User Disapprove Document', 'KR-WKFLW', sys_guid(), 1)
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-10-12.sql
-- 


--
--    KULRICE-7792 & KULRICE-7793
--

UPDATE KREW_RTE_NODE_CFG_PARM_T
    SET VAL = REPLACE( VAL, 'org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver', 'org.kuali.rice.kim.impl.type.KimTypeQualifierResolver' )
    WHERE val LIKE '%org.kuali.rice.kim.workflow.attribute.KimTypeQualifierResolver%'
/

UPDATE KREW_RTE_NODE_CFG_PARM_T
    SET VAL = REPLACE( VAL, 'org.kuali.rice.kns.workflow.attribute.DataDictionaryQualifierResolver', 'org.kuali.rice.krad.workflow.attribute.DataDictionaryQualifierResolver' )
    WHERE val LIKE '%org.kuali.rice.kns.workflow.attribute.DataDictionaryQualifierResolver%'
/




-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-10-17.sql
-- 


--
-- Clean up data and tables that are no longer needed.  Depending on how you ran the 2.0 upgrade scripts, these items
-- may or may not need to be cleaned up.  The SQL will run without error even if the items do not need cleaning.
--
-- The final SQL in this script will add a needed foreign key
--

--
-- KULRICE-7440 - KRMS_CNTXT_TERM_SPEC_PREREQ_S is still in master datasource
--

BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE KRMS_CNTXT_TERM_SPEC_PREREQ_S'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -2289
  THEN RAISE; END IF; END;
/

--
-- KULRICE-7412 - KREW_HLP_T and KREW_HLP_S is still in master datasource
--

BEGIN EXECUTE IMMEDIATE 'DROP TABLE KREW_HLP_T'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942
  THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE KREW_HLP_S'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -2289
  THEN RAISE; END IF; END;
/

--
-- KULRICE-7346 - ACTVN_TYP on KREW_RTE_NODE_T should be a varchar2(1)
--

ALTER TABLE KREW_RTE_NODE_T MODIFY (ACTVN_TYP VARCHAR2(1))
/

--
-- KULRICE-7376 - APPL_ID length is inconsistent; Should always be 255
--

ALTER TABLE KREW_DOC_TYP_T MODIFY (APPL_ID VARCHAR2(255))
/
ALTER TABLE KREW_RULE_ATTR_T MODIFY (APPL_ID VARCHAR2(255))
/
ALTER TABLE KRSB_SVC_DEF_T MODIFY (APPL_ID VARCHAR2(255))
/
ALTER TABLE KRSB_MSG_QUE_T MODIFY (APPL_ID VARCHAR2(255))
/
ALTER TABLE KRCR_NMSPC_T MODIFY (APPL_ID VARCHAR2(255))
/
ALTER TABLE KRCR_PARM_T MODIFY (APPL_ID VARCHAR2(255))
/

--
-- KULRICE-7745 - County (not Country) maintenance document allowing bad state data - add FK constraint
--

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME ='KRLC_CNTY_TR1';
	IF temp = 0 THEN EXECUTE IMMEDIATE
		'ALTER TABLE KRLC_CNTY_T ADD CONSTRAINT KRLC_CNTY_TR1 FOREIGN KEY (STATE_CD,POSTAL_CNTRY_CD)
         REFERENCES KRLC_ST_T (POSTAL_STATE_CD, POSTAL_CNTRY_CD)';
	END IF;
END;
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-10-19.sql
-- 



--
-- KULRICE-7786: Document Specific Doc Search Application Document Status should be available
-- (and groupable) on the basic version of search
--

-- add category name (which is part of composite fk) to app doc stat
ALTER TABLE KREW_DOC_TYP_APP_DOC_STAT_T ADD CAT_NM VARCHAR2(64)
/

-- add index for queries from category to status.  Using non-standard index name to follow table precedent.
CREATE INDEX KREW_DOC_TYP_APP_DOC_STAT_T2 ON KREW_DOC_TYP_APP_DOC_STAT_T (DOC_TYP_ID, CAT_NM)
/

-- add sequence number column for ordering to app doc stat
ALTER TABLE KREW_DOC_TYP_APP_DOC_STAT_T ADD SEQ_NO NUMBER(5)
/

-- create category table
CREATE TABLE KREW_DOC_TYP_APP_STAT_CAT_T  (
    DOC_TYP_ID   VARCHAR2(40) NOT NULL,
	CAT_NM	VARCHAR2(64) NOT NULL,
    VER_NBR NUMBER(8) DEFAULT '0',
    OBJ_ID VARCHAR2(36) NOT NULL,

	PRIMARY KEY(DOC_TYP_ID, CAT_NM),
	CONSTRAINT KREW_DOC_TYP_APP_STAT_CAT_FK1 FOREIGN KEY (DOC_TYP_ID) REFERENCES KREW_DOC_TYP_T (DOC_TYP_ID)
)
/

-- object id must be unique
ALTER TABLE KREW_DOC_TYP_APP_STAT_CAT_T
	ADD CONSTRAINT KREW_DOC_TYP_APP_STAT_CAT_TC1
	UNIQUE (OBJ_ID)
/

-- add constraint to tie app doc stat and category together
ALTER TABLE KREW_DOC_TYP_APP_DOC_STAT_T ADD CONSTRAINT KREW_DOC_TYP_APP_DOC_STAT_FK1
FOREIGN KEY (DOC_TYP_ID, CAT_NM) REFERENCES KREW_DOC_TYP_APP_STAT_CAT_T (DOC_TYP_ID, CAT_NM)
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-10-25.sql
--


--
-- KULRICE-7509: Rice KIM documents stay editable after submission
--

DELETE FROM KRIM_ROLE_PERM_T WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator or Reviewer' AND NMSPC_CD = 'KR-WKFLW') AND
PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Edit Kuali ENROUTE Document Route Status Code R' AND NMSPC_CD = 'KUALI')
/
