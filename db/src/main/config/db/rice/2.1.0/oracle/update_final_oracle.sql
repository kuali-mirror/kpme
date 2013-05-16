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
-- 2012-02-27.sql
-- 



--
-- KULRICE-6842: Don't allow requests for null principals or null groups or null principal types
-- or null roles.
--

ALTER TABLE
  KRIM_GRP_MBR_T
MODIFY
   (
    MBR_ID VARCHAR2(40) NOT NULL,
    GRP_ID VARCHAR2(40) NOT NULL,
    MBR_TYP_CD CHAR(1) NOT NULL
   )
/

ALTER TABLE
   KRIM_ROLE_MBR_T
MODIFY
   (
    MBR_ID VARCHAR2(40) NOT NULL,
    ROLE_ID VARCHAR2(40) NOT NULL,
    MBR_TYP_CD CHAR(1) NOT NULL
   )
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-03-05.sql
-- 


UPDATE KREW_DOC_TYP_T SET LBL = 'Undefined' WHERE LBL IS NULL
/
ALTER TABLE KREW_DOC_TYP_T MODIFY (LBL NOT NULL)
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-03-13.sql
-- 


--
-- KULRICE-6916 KRIM_ENTITY_CACHE_T.PRSN_NM is too small
--

ALTER TABLE KRIM_ENTITY_CACHE_T MODIFY (PRSN_NM VARCHAR2(255))
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-03-20.sql
-- 


--
-- KULRICE-5931 increase PLCY_VAL in order to store extended Recall notification configuration
--

ALTER TABLE KREW_DOC_TYP_PLCY_RELN_T MODIFY (PLCY_VAL VARCHAR2(1024))
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-03-28.sql
-- 


-- KULRICE-5931

-- add 'appDocStatus' attr definition
INSERT INTO KRIM_ATTR_DEFN_T VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(KIM_ATTR_DEFN_ID, '[^0-9]', ''))) + 1) FROM KRIM_ATTR_DEFN_T WHERE KIM_ATTR_DEFN_ID IS NOT NULL AND REGEXP_LIKE(KIM_ATTR_DEFN_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(KIM_ATTR_DEFN_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, 'appDocStatus', null, 'Y', 'KR-WKFLW', 'org.kuali.rice.kim.bo.impl.KimAttributes')
/

-- assign it to 'Document Type & Routing Node or State' type
INSERT INTO KRIM_TYP_ATTR_T VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ATTR_ID, '[^0-9]', ''))) + 1) FROM KRIM_TYP_ATTR_T WHERE KIM_TYP_ATTR_ID IS NOT NULL AND REGEXP_LIKE(KIM_TYP_ATTR_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ATTR_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, 'a', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD='KR-SYS' AND NM='Document Type & Routing Node or State'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD='KR-WKFLW' AND NM='appDocStatus'), 'Y')
/

-- create Recall permission template
INSERT INTO KRIM_PERM_TMPL_T VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(PERM_TMPL_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_TMPL_T WHERE PERM_TMPL_ID IS NOT NULL AND REGEXP_LIKE(PERM_TMPL_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(PERM_TMPL_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, 'KR-WKFLW', 'Recall Document', null, (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD='KR-SYS' AND NM='Document Type & Routing Node or State'), 'Y')
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-04.sql
-- 


-- KULRICE-6784 Add index and constraint on KREW_RULE_ATTR_T.NM
ALTER TABLE KREW_RULE_ATTR_T ADD CONSTRAINT KREW_RULE_ATTR_TC1 UNIQUE(NM)
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-11.sql
-- 


-- insert Recall permission for initiators
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM) VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_T WHERE PERM_ID IS NOT NULL AND REGEXP_LIKE(PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(PERM_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Recall Document'), 'KR-WKFLW', 'Recall Document')
/
-- define document type wildcard for permission
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND REGEXP_LIKE(ATTR_DATA_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM='Recall Document'), (SELECT KIM_TYP_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Recall Document'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), '*')
/
-- associate Recall permission with initiator derived role
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND REGEXP_LIKE(ROLE_PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator' AND NMSPC_CD = 'KR-WKFLW'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM='Recall Document'), 'Y')
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-12.sql
-- 


-- create a Kim Type wired to the documentRouterRoleTypeService permission-derived role service
INSERT INTO KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ID, '[^0-9]', ''))) + 1) FROM KRIM_TYP_T WHERE KIM_TYP_ID IS NOT NULL AND REGEXP_LIKE(KIM_TYP_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, 'Derived Role: Permission (Route Document)', 'documentRouterRoleTypeService', 'Y', 'KR-WKFLW')
/
-- define the Route Document derived role
INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND) VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ROLE_ID, '[^0-9]', ''))) + 1) FROM KRIM_ROLE_T WHERE ROLE_ID IS NOT NULL AND REGEXP_LIKE(ROLE_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ROLE_ID, '[^0-9]', '')) < 10000), SYS_GUID(), 1, 'Document Router', 'KR-WKFLW', 'This role derives its members from users with the Route Document permission for a given document type.', (select KIM_TYP_ID from KRIM_TYP_T where NM = 'Derived Role: Permission (Route Document)' AND NMSPC_CD = 'KR-WKFLW'), 'Y')
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-16b.sql
-- 



-- KULRICE-6964: Update Rice default From email address

UPDATE KRCR_PARM_T SET VAL='rice.test@kuali.org'
WHERE NMSPC_CD='KR-WKFLW' AND CMPNT_CD='Mailer' AND PARM_NM='FROM_ADDRESS' AND APPL_ID='KUALI'
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-16.sql
-- 


-- KULRICE-7128 wire kim type attribute 'qualifierResolverProvidedIdentifier' to Responsibility type
INSERT INTO KRIM_TYP_ATTR_T
(KIM_TYP_ATTR_ID,
OBJ_ID,
VER_NBR,
SORT_CD,
KIM_TYP_ID,
KIM_ATTR_DEFN_ID,
ACTV_IND)
VALUES
  ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ATTR_ID, '[^0-9]', ''))) + 1) FROM  KRIM_TYP_ATTR_T WHERE KIM_TYP_ATTR_ID IS NOT NULL AND REGEXP_LIKE(KIM_TYP_ATTR_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(KIM_TYP_ATTR_ID, '[^0-9]', '')) < 10000),
  '69FA55ACC2EE2598E0404F8189D86880',
  1,
  'e',
  7,
  46,
  'Y')
/



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
CREATE INDEX KRNS_MAINT_DOC_ATT_LST_TI1 on KRNS_MAINT_DOC_ATT_LST_T (DOC_HDR_ID)
/
CREATE SEQUENCE KRNS_MAINT_DOC_ATT_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/





-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-24.sql
-- 


DECLARE temp NUMBER;
BEGIN
    SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'KRIM_ROLE_PERM_ID_S';
    IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE KRIM_ROLE_PERM_ID_S'; END IF;
END;
/
CREATE SEQUENCE KRIM_ROLE_PERM_ID_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-04-25.sql
-- 


UPDATE KRMS_ATTR_DEFN_T SET NM='actionTypeCode' WHERE ATTR_DEFN_ID='1004'
/
UPDATE KRMS_ATTR_DEFN_T SET NM='actionMessage' WHERE ATTR_DEFN_ID='1005'
/
UPDATE KRMS_ATTR_DEFN_T SET NM='ruleTypeCode' WHERE ATTR_DEFN_ID='1001'
/

DELETE FROM KRMS_TYP_ATTR_T WHERE ATTR_DEFN_ID = '1002'
/
DELETE FROM KRMS_TYP_ATTR_T WHERE ATTR_DEFN_ID = '1003'
/
DELETE FROM KRMS_ATTR_DEFN_T WHERE ATTR_DEFN_ID = '1002'
/
DELETE FROM KRMS_ATTR_DEFN_T WHERE ATTR_DEFN_ID = '1003'
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-11.sql
-- 


INSERT INTO KRIM_PERM_T
(PERM_ID, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_T WHERE PERM_ID IS NOT NULL AND REGEXP_LIKE(PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(PERM_ID, '[^0-9]', '')) < 10000),
        (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM = 'Send Ad Hoc Request' AND NMSPC_CD = 'KR-NS'),
        'KR-SYS','Send Complete Request Kuali Document','Authorizes users to send Complete ad hoc requests for Kuali Documents','Y',1,SYS_GUID())
/

INSERT INTO KRIM_PERM_ATTR_DATA_T
(ATTR_DATA_ID, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND REGEXP_LIKE(ATTR_DATA_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', '')) < 10000),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Ad Hoc Review' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'documentTypeName'), 'KualiDocument',1,SYS_GUID())
/


INSERT INTO KRIM_PERM_ATTR_DATA_T
(ATTR_DATA_ID, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', ''))) + 1) FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND REGEXP_LIKE(ATTR_DATA_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ATTR_DATA_ID, '[^0-9]', '')) < 10000),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Ad Hoc Review' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'ACTIONREQUESTCD'), 'C',1,SYS_GUID())
/

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND REGEXP_LIKE(ROLE_PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', '')) < 10000),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Document Opener' AND NMSPC_CD = 'KR-NS'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        'Y', 1, SYS_GUID())
/

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND REGEXP_LIKE(ROLE_PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', '')) < 10000),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator or Reviewer' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Edit Kuali ENROUTE Document Route Status Code R' AND NMSPC_CD = 'KUALI'),
        'Y', 1, SYS_GUID())
/

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT (MAX(TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', ''))) + 1) FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND REGEXP_LIKE(ROLE_PERM_ID, '^[1-9][0-9]*$') AND TO_NUMBER(REGEXP_REPLACE(ROLE_PERM_ID, '[^0-9]', '')) < 10000),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator or Reviewer' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Cancel Document' AND NMSPC_CD = 'KUALI'),
        'Y', 1, SYS_GUID())
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-17.sql
-- 



-- KULRICE-7237: KRNS_NTE_T is selected by a field with no indexes - full table scan every time
CREATE INDEX KRNS_NTE_TI1 ON KRNS_NTE_T (RMT_OBJ_ID)
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-21.sql
-- 


UPDATE KRIM_PERM_ATTR_DATA_T SET ATTR_VAL='org.kuali.rice.core.web.impex.IngesterAction' WHERE ATTR_VAL='org.kuali.rice.kew.batch.web.IngesterAction'
/





-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-24.sql
-- 



--
--  KULRICE-7377: KREW_RTE_NODE_T still defines DOC_TYP_ID as NUMBER(19)
--

-- rename the old table
ALTER TABLE KREW_RTE_NODE_T RENAME TO OLD_KREW_RTE_NODE_T
/

-- redefine it with the correct column types
CREATE TABLE KREW_RTE_NODE_T
(
   RTE_NODE_ID VARCHAR2(40),
   DOC_TYP_ID VARCHAR2(40),
   NM VARCHAR2(255) NOT NULL,
   TYP VARCHAR2(255) NOT NULL,
   RTE_MTHD_NM VARCHAR2(255),
   RTE_MTHD_CD VARCHAR2(2),
   FNL_APRVR_IND DECIMAL(1),
   MNDTRY_RTE_IND DECIMAL(1),
   ACTVN_TYP VARCHAR2(250),
   BRCH_PROTO_ID VARCHAR2(40),
   VER_NBR DECIMAL(8) DEFAULT 0
        ,
   CONTENT_FRAGMENT VARCHAR2(4000),
   GRP_ID VARCHAR2(40),
   NEXT_DOC_STAT VARCHAR2(64)
)
/

-- copy over the data, casting the column that changed
INSERT INTO KREW_RTE_NODE_T(
RTE_NODE_ID
        , DOC_TYP_ID
        , NM
        , TYP
        , RTE_MTHD_NM
        , RTE_MTHD_CD
        , FNL_APRVR_IND
        , MNDTRY_RTE_IND
        , ACTVN_TYP
        , BRCH_PROTO_ID
        , VER_NBR
        , CONTENT_FRAGMENT
        , GRP_ID
        , NEXT_DOC_STAT
)
SELECT RTE_NODE_ID
        , CAST(DOC_TYP_ID AS VARCHAR2(40))
        , NM
        , TYP
        , RTE_MTHD_NM
        , RTE_MTHD_CD
        , FNL_APRVR_IND
        , MNDTRY_RTE_IND
        , ACTVN_TYP
        , BRCH_PROTO_ID
        , VER_NBR
        , CONTENT_FRAGMENT
        , GRP_ID
        , NEXT_DOC_STAT
FROM OLD_KREW_RTE_NODE_T
/

-- drop the old one
DROP TABLE OLD_KREW_RTE_NODE_T CASCADE CONSTRAINTS PURGE
/

--
-- add back all the constraints and indexes
--

ALTER TABLE KREW_RTE_NODE_T
    ADD CONSTRAINT KREW_RTE_NODE_TP1
PRIMARY KEY (RTE_NODE_ID)
/

CREATE INDEX KREW_RTE_NODE_TI4 ON KREW_RTE_NODE_T(DOC_TYP_ID)
/

CREATE INDEX KREW_RTE_NODE_TI3 ON KREW_RTE_NODE_T(BRCH_PROTO_ID)
/

CREATE INDEX KREW_RTE_NODE_TI2 ON KREW_RTE_NODE_T
(
  DOC_TYP_ID,
  FNL_APRVR_IND
)
/

CREATE INDEX KREW_RTE_NODE_TI1 ON KREW_RTE_NODE_T
(
  NM,
  DOC_TYP_ID
)
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-05-25.sql
-- 



--
--  KULRICE-7375: Rice master data source has KREW_DOC_TYP_PROC_T.INIT_RTE_NODE_ID still defined as a NUMBER
--

-- rename the old table
ALTER TABLE KREW_DOC_TYP_PROC_T RENAME TO OLD_KREW_DOC_TYP_PROC_T
/

-- redefine it with the correct column types
CREATE TABLE KREW_DOC_TYP_PROC_T
(
   DOC_TYP_PROC_ID varchar2(40),
   DOC_TYP_ID varchar2(40) NOT NULL,
   INIT_RTE_NODE_ID varchar2(40),
   NM varchar2(255) NOT NULL,
   INIT_IND decimal(1) DEFAULT 0  NOT NULL,
   VER_NBR decimal(8) DEFAULT 0
)
/

-- copy over the data, casting the column that changed
INSERT INTO KREW_DOC_TYP_PROC_T (
   DOC_TYP_PROC_ID
   , DOC_TYP_ID
   , INIT_RTE_NODE_ID
   , NM
   , INIT_IND
   , VER_NBR
)
SELECT DOC_TYP_PROC_ID
   , DOC_TYP_ID
   , cast(INIT_RTE_NODE_ID AS varchar2(40))
   , NM
   , INIT_IND
   , VER_NBR
FROM OLD_KREW_DOC_TYP_PROC_T
/

-- drop the old one
DROP TABLE OLD_KREW_DOC_TYP_PROC_T CASCADE CONSTRAINTS PURGE
/

--
-- add back all the constraints and indexes
--

ALTER TABLE KREW_DOC_TYP_PROC_T
    ADD CONSTRAINT KREW_DOC_TYP_PROC_TP1
PRIMARY KEY (DOC_TYP_PROC_ID)
/
CREATE INDEX KREW_DOC_TYP_PROC_TI3 ON KREW_DOC_TYP_PROC_T(NM)
/
CREATE INDEX KREW_DOC_TYP_PROC_TI2 ON KREW_DOC_TYP_PROC_T(INIT_RTE_NODE_ID)
/
CREATE INDEX KREW_DOC_TYP_PROC_TI1 ON KREW_DOC_TYP_PROC_T(DOC_TYP_ID)
/
