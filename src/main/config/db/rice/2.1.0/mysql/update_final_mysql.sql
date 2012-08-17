--
-- Copyright 2005-2012 The Kuali Foundation
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
-- mysql-2012-02-27.sql
-- 



--
-- KULRICE-6842: Don't allow requests for null principals or null groups or null principal types
-- or null roles.
--

ALTER TABLE `KRIM_GRP_MBR_T`
MODIFY COLUMN `GRP_ID` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
MODIFY COLUMN `MBR_ID` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
MODIFY COLUMN `MBR_TYP_CD` CHAR(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL;

ALTER TABLE `KRIM_ROLE_MBR_T`
MODIFY COLUMN `ROLE_ID` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
MODIFY COLUMN `MBR_ID` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
MODIFY COLUMN `MBR_TYP_CD` CHAR(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-03-05.sql
-- 


UPDATE KREW_DOC_TYP_T SET LBL = 'Undefined' WHERE LBL IS NULL
;
ALTER TABLE KREW_DOC_TYP_T MODIFY COLUMN LBL  VARCHAR(128) NOT NULL
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-03-13.sql
-- 


--
-- KULRICE-6916 KRIM_ENTITY_CACHE_T.PRSN_NM is too small
--

ALTER TABLE `KRIM_ENTITY_CACHE_T`
CHANGE COLUMN `PRSN_NM` `PRSN_NM` VARCHAR(255)
CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-03-20.sql
-- 


--
-- KULRICE-5931 increase PLCY_VAL in order to store extended Recall notification configuration
--

ALTER TABLE KREW_DOC_TYP_PLCY_RELN_T MODIFY PLCY_VAL VARCHAR(1024) CHARACTER SET utf8 COLLATE utf8_bin
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-03-28.sql
-- 


-- KULRICE-5931

-- add 'appDocStatus' attr definition
INSERT INTO KRIM_ATTR_DEFN_T VALUES ((SELECT KIM_ATTR_DEFN_ID FROM (SELECT (MAX(CAST(KIM_ATTR_DEFN_ID AS DECIMAL)) + 1) AS KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE KIM_ATTR_DEFN_ID IS NOT NULL AND KIM_ATTR_DEFN_ID REGEXP '^[1-9][0-9]*$' AND CAST(KIM_ATTR_DEFN_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, 'appDocStatus', null, 'Y', 'KR-WKFLW', 'org.kuali.rice.kim.bo.impl.KimAttributes')
;
-- assign it to 'Document Type & Routing Node or State' type
INSERT INTO KRIM_TYP_ATTR_T VALUES ((SELECT KIM_TYP_ATTR_ID FROM (SELECT (MAX(CAST(KIM_TYP_ATTR_ID AS DECIMAL)) + 1) AS KIM_TYP_ATTR_ID FROM KRIM_TYP_ATTR_T WHERE KIM_TYP_ATTR_ID IS NOT NULL AND KIM_TYP_ATTR_ID REGEXP '^[1-9][0-9]*$' AND CAST(KIM_TYP_ATTR_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, 'a', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD='KR-SYS' AND NM='Document Type & Routing Node or State'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD='KR-WKFLW' AND NM='appDocStatus'), 'Y')
;

-- create Recall permission template
INSERT INTO KRIM_PERM_TMPL_T VALUES ((SELECT PERM_TMPL_ID FROM (SELECT (MAX(CAST(PERM_TMPL_ID AS DECIMAL)) + 1) AS PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE PERM_TMPL_ID IS NOT NULL AND PERM_TMPL_ID REGEXP '^[1-9][0-9]*$' AND CAST(PERM_TMPL_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, 'KR-WKFLW', 'Recall Document', null, (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD='KR-SYS' AND NM='Document Type & Routing Node or State'), 'Y')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-04.sql
-- 


-- KULRICE-6784 Add index and constraint on KREW_RULE_ATTR_T.NM
ALTER TABLE KREW_RULE_ATTR_T ADD CONSTRAINT KREW_RULE_ATTR_TC1 UNIQUE(NM)
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-11.sql
-- 


-- insert Recall permission for initiators
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM) VALUES ((SELECT PERM_ID FROM (SELECT (MAX(CAST(PERM_ID AS DECIMAL)) + 1) AS PERM_ID FROM KRIM_PERM_T WHERE PERM_ID IS NOT NULL AND PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(PERM_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Recall Document'), 'KR-WKFLW', 'Recall Document')
;
-- define document type wildcard for permission
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) VALUES ((SELECT ATTR_DATA_ID FROM (SELECT (MAX(CAST(ATTR_DATA_ID AS DECIMAL)) + 1) AS ATTR_DATA_ID FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND ATTR_DATA_ID REGEXP '^[1-9][0-9]*$' AND CAST(ATTR_DATA_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM='Recall Document'), (SELECT KIM_TYP_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Recall Document'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), '*')
;
-- associate Recall permission with initiator derived role
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) VALUES ((SELECT ROLE_PERM_ID FROM (SELECT (MAX(CAST(ROLE_PERM_ID AS DECIMAL)) + 1) AS ROLE_PERM_ID FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND ROLE_PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(ROLE_PERM_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator' AND NMSPC_CD = 'KR-WKFLW'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM='Recall Document'), 'Y')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-12.sql
-- 


-- create a Kim Type wired to the documentRouterRoleTypeService permission-derived role service
INSERT INTO KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) VALUES ((SELECT KIM_TYP_ID FROM (SELECT (MAX(CAST(KIM_TYP_ID AS DECIMAL)) + 1) AS KIM_TYP_ID FROM KRIM_TYP_T WHERE KIM_TYP_ID IS NOT NULL AND KIM_TYP_ID REGEXP '^[1-9][0-9]*$' AND CAST(KIM_TYP_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, 'Derived Role: Permission (Route Document)', 'documentRouterRoleTypeService', 'Y', 'KR-WKFLW')
;
-- define the Route Document derived role
INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND) VALUES ((SELECT ROLE_ID FROM (SELECT (MAX(CAST(ROLE_ID AS DECIMAL)) + 1) AS ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_ID IS NOT NULL AND ROLE_ID REGEXP '^[1-9][0-9]*$' AND CAST(ROLE_ID AS DECIMAL) < 10000) AS TMPTABLE), UUID(), 1, 'Document Router', 'KR-WKFLW', 'This role derives its members from users with the Route Document permission for a given document type.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Derived Role: Permission (Route Document)' AND NMSPC_CD = 'KR-WKFLW'), 'Y')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-16b.sql
-- 



-- KULRICE-6964: Update Rice default From email address

UPDATE KRCR_PARM_T SET VAL='rice.test@kuali.org'
WHERE NMSPC_CD='KR-WKFLW' AND CMPNT_CD='Mailer' AND PARM_NM='FROM_ADDRESS' AND APPL_ID='KUALI'
;


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-16.sql
-- 


-- KULRICE-7128 wire kim type attribute 'qualifierResolverProvidedIdentifier' to Responsibility type
INSERT INTO `KRIM_TYP_ATTR_T`
(`KIM_TYP_ATTR_ID`,
`OBJ_ID`,
`VER_NBR`,
`SORT_CD`,
`KIM_TYP_ID`,
`KIM_ATTR_DEFN_ID`,
`ACTV_IND`)
VALUES
((SELECT KIM_TYP_ATTR_ID FROM (SELECT (MAX(CAST(KIM_TYP_ATTR_ID AS DECIMAL)) + 1)
  AS KIM_TYP_ATTR_ID FROM KRIM_TYP_ATTR_T
  WHERE KIM_TYP_ATTR_ID IS NOT NULL AND KIM_TYP_ATTR_ID REGEXP '^[1-9][0-9]*$' AND CAST(KIM_TYP_ATTR_ID AS DECIMAL) < 10000) AS TMPTABLE),
  '69FA55ACC2EE2598E0404F8189D86880',
  1,
  'e',
  7,
  46,
  'Y')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-19.sql
-- 


CREATE TABLE KRNS_MAINT_DOC_ATT_LST_T  (
    ATT_ID      VARCHAR(40) NOT NULL,
	DOC_HDR_ID	VARCHAR(14) NOT NULL,
	ATT_CNTNT 	LONGBLOB NOT NULL,
	FILE_NM   	VARCHAR(150) NULL,
	CNTNT_TYP 	VARCHAR(255) NULL,
	OBJ_ID    	VARCHAR(36) NOT NULL,
	VER_NBR   	DECIMAL(8,0) NOT NULL DEFAULT 0,
	PRIMARY KEY(ATT_ID),
	CONSTRAINT KRNS_MAINT_DOC_ATT_LST_FK1 FOREIGN KEY (DOC_HDR_ID) REFERENCES KRNS_MAINT_DOC_T (DOC_HDR_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;

ALTER TABLE KRNS_MAINT_DOC_ATT_LST_T
	ADD CONSTRAINT KRNS_MAINT_DOC_ATT_LST_TC0
	UNIQUE (OBJ_ID);

CREATE INDEX KRNS_MAINT_DOC_ATT_LST_TI1 ON KRNS_MAINT_DOC_ATT_LST_T (DOC_HDR_ID);

CREATE TABLE KRNS_MAINT_DOC_ATT_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE MyISAM;
ALTER TABLE KRNS_MAINT_DOC_ATT_S AUTO_INCREMENT = 10000;










-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-24.sql
-- 


DROP TABLE IF EXISTS KRIM_ROLE_PERM_ID_S;

CREATE TABLE KRIM_ROLE_PERM_ID_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE MYISAM;

ALTER TABLE KRIM_ROLE_PERM_ID_S AUTO_INCREMENT = 10000;


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-04-25.sql
-- 


UPDATE KRMS_ATTR_DEFN_T SET NM='actionTypeCode' WHERE ATTR_DEFN_ID='1004';
UPDATE KRMS_ATTR_DEFN_T SET NM='actionMessage' WHERE ATTR_DEFN_ID='1005';
UPDATE KRMS_ATTR_DEFN_T SET NM='ruleTypeCode' WHERE ATTR_DEFN_ID='1001';

DELETE FROM KRMS_TYP_ATTR_T WHERE ATTR_DEFN_ID = '1002';
DELETE FROM KRMS_TYP_ATTR_T WHERE ATTR_DEFN_ID = '1003';
DELETE FROM KRMS_ATTR_DEFN_T WHERE ATTR_DEFN_ID = '1002';
DELETE FROM KRMS_ATTR_DEFN_T WHERE ATTR_DEFN_ID = '1003';


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-05-11.sql
-- 


INSERT INTO KRIM_PERM_T (PERM_ID, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT PERM_ID FROM (SELECT (MAX(CAST(PERM_ID AS DECIMAL)) + 1) AS PERM_ID FROM KRIM_PERM_T WHERE PERM_ID IS NOT NULL AND PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(PERM_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM = 'Send Ad Hoc Request' AND NMSPC_CD = 'KR-NS'),
        'KR-SYS','Send Complete Request Kuali Document','Authorizes users to send Complete ad hoc requests for Kuali Documents','Y',1,UUID())
;

INSERT INTO KRIM_PERM_ATTR_DATA_T
(ATTR_DATA_ID, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, VER_NBR, OBJ_ID)
VALUES ((SELECT ATTR_DATA_ID FROM
          (SELECT (MAX(CAST(ATTR_DATA_ID AS DECIMAL)) + 1) AS ATTR_DATA_ID FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND ATTR_DATA_ID REGEXP '^[1-9][0-9]*$' AND CAST(ATTR_DATA_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Ad Hoc Review' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'documentTypeName'), 'KualiDocument',1,UUID())
;


INSERT INTO KRIM_PERM_ATTR_DATA_T
(ATTR_DATA_ID, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, VER_NBR, OBJ_ID)
VALUES ((SELECT ATTR_DATA_ID FROM
          (SELECT (MAX(CAST(ATTR_DATA_ID AS DECIMAL)) + 1) AS ATTR_DATA_ID FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID IS NOT NULL AND ATTR_DATA_ID REGEXP '^[1-9][0-9]*$' AND CAST(ATTR_DATA_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Ad Hoc Review' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'actionRequestCd'), 'C',1,UUID())
;

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT ROLE_PERM_ID FROM
          (SELECT (MAX(CAST(ROLE_PERM_ID AS DECIMAL)) + 1) AS ROLE_PERM_ID FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND ROLE_PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(ROLE_PERM_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Document Opener' AND NMSPC_CD = 'KR-NS'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Send Complete Request Kuali Document' AND NMSPC_CD = 'KR-SYS'),
        'Y', 1, UUID())
;

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT ROLE_PERM_ID FROM
          (SELECT (MAX(CAST(ROLE_PERM_ID AS DECIMAL)) + 1) AS ROLE_PERM_ID FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND ROLE_PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(ROLE_PERM_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator or Reviewer' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Edit Kuali ENROUTE Document Route Status Code R' AND NMSPC_CD = 'KUALI'),
        'Y', 1, UUID())
;

INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID, ROLE_ID, PERM_ID, ACTV_IND, VER_NBR, OBJ_ID)
VALUES ((SELECT ROLE_PERM_ID FROM
          (SELECT (MAX(CAST(ROLE_PERM_ID AS DECIMAL)) + 1) AS ROLE_PERM_ID FROM KRIM_ROLE_PERM_T WHERE ROLE_PERM_ID IS NOT NULL AND ROLE_PERM_ID REGEXP '^[1-9][0-9]*$' AND CAST(ROLE_PERM_ID AS DECIMAL) < 10000)
         AS TMPTABLE),
        (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Initiator or Reviewer' AND NMSPC_CD = 'KR-WKFLW'),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Cancel Document' AND NMSPC_CD = 'KUALI'),
        'Y', 1, UUID())
;


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-05-17.sql
-- 



-- KULRICE-7237: KRNS_NTE_T is selected by a field with no indexes - full table scan every time
CREATE INDEX KRNS_NTE_TI1 ON KRNS_NTE_T (RMT_OBJ_ID);


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-05-21.sql
-- 


UPDATE KRIM_PERM_ATTR_DATA_T SET ATTR_VAL='org.kuali.rice.core.web.impex.IngesterAction' WHERE ATTR_VAL='org.kuali.rice.kew.batch.web.IngesterAction';




-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-05-24.sql
-- 



--
--  KULRICE-7377: KREW_RTE_NODE_T still defines DOC_TYP_ID as NUMBER(19)
--

ALTER TABLE KREW_RTE_NODE_T MODIFY COLUMN DOC_TYP_ID VARCHAR(40);


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-05-25.sql
-- 



--
--  KULRICE-7375: Rice master data source has KREW_DOC_TYP_PROC_T.INIT_RTE_NODE_ID still defined as a NUMBER
--

ALTER TABLE KREW_DOC_TYP_PROC_T MODIFY COLUMN INIT_RTE_NODE_ID VARCHAR(40);