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
-- 2012-07-20.sql
-- 



--
-- KULRICE-7439: Assignment of "Add Message to Route Log" permission to the KR-SYS technical administrator is missing from bootstrap dataset
--

DELETE FROM KRIM_ROLE_PERM_T WHERE
ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Technical Administrator' AND NMSPC_CD = 'KR-SYS') AND
PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Add Message to Route Log' AND NMSPC_CD = 'KUALI')
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-08-29.sql
-- 


--
--     KULRICE-6676 - external message tables
--

-- ----------------------------------------------------------------------
-- krad_msg_t
-- ----------------------------------------------------------------------
CREATE TABLE KRAD_MSG_T
(
	NMSPC_CD VARCHAR2(20) NOT NULL,
	CMPNT_CD VARCHAR2(100) NOT NULL,
	MSG_KEY VARCHAR2(100) NOT NULL,
	LOC VARCHAR2(80) NOT NULL,
	OBJ_ID VARCHAR2(36) NOT NULL,
	VER_NBR DECIMAL(8) DEFAULT 1 NOT NULL,
	MSG_DESC VARCHAR2(255),
	TXT VARCHAR2(4000)
)
/

ALTER TABLE KRAD_MSG_T
    ADD CONSTRAINT KRAD_MSG_TC1
PRIMARY KEY (NMSPC_CD,CMPNT_CD,MSG_KEY,LOC)
/

ALTER TABLE KRAD_MSG_T
    ADD CONSTRAINT KRAD_MSG_TC2
UNIQUE (OBJ_ID)
/

-- note!! the 'loc' field in rice is currently set to 255 chars, but that exceeds the max length for primary
-- keys in mysql 5.1.  Depending on what the rice team does about this, we may need to run the following statement
-- below
-- ALTER TABLE KRAD_MSG_T MODIFY LOC VARCHAR2(255)
-- ;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-09-13.sql
-- 



--
--     KULRICE-6676 - adding system parameter for default locale
--

INSERT INTO KRCR_PARM_T VALUES ('KR-NS', 'All', 'DEFAULT_LOCALE_CODE', sys_guid(), 1, 'CONFG', 'en-US',
'The locale code that should be used within the application when otherwise not specified.', 'A', 'KUALI')
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-09-29.sql
-- 


--
--     KULRICE-8349 - guest user access
--

INSERT INTO KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
VALUES ('KR1000', sys_guid(), 1, 'Y', sysdate)
/

INSERT INTO KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
VALUES ('PERSON', 'KR1000', sys_guid(), 1, 'Y', sysdate)
/

INSERT INTO KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT)
VALUES ('guest', sys_guid(), 1, 'guest', 'KR1000', '', 'Y', sysdate)
/

INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
VALUES ('KR1000', sys_guid(), 1, 'GuestRole', 'KUALI', 'This role is used for no login guest users.', '1', 'Y', sysdate)
/

INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
VALUES ('KR1000', 1, sys_guid(), 'KR1000', 'guest', 'P', null, null, sysdate)
/



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-11-14.sql
-- 



--
-- KULRICE-8539: Apply SQL for new KRMS tables to the 2.2 master db
--
-- KULRICE-7367: Implement KRMS Type-Type Relations feature
-- KULRICE-7368: Implement KRMS Natural Language Translation feature
-- KULRICE-7369: Implement KRMS Reference Object Bindings feature
--


CREATE SEQUENCE KRMS_REF_OBJ_KRMS_OBJ_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE SEQUENCE KRMS_TYP_RELN_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE SEQUENCE KRMS_NL_USAGE_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE SEQUENCE KRMS_NL_TMPL_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE SEQUENCE KRMS_NL_TMPL_ATTR_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE SEQUENCE KRMS_NL_USAGE_ATTR_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

CREATE  TABLE KRMS_NL_USAGE_T(
  NL_USAGE_ID VARCHAR2(40) NOT NULL ,
  NM VARCHAR2(255) NOT NULL,
  NMSPC_CD VARCHAR2(40)  NOT NULL,
  DESC_TXT VARCHAR2(255) NULL,
  ACTV VARCHAR2(1) DEFAULT 'Y' NOT NULL,
  VER_NBR NUMBER(8) DEFAULT 0 NOT NULL,
  PRIMARY KEY (NL_USAGE_ID),
  CONSTRAINT KRMS_NL_USAGE_TC1 UNIQUE (NM, NMSPC_CD)
)
/
CREATE  TABLE  KRMS_NL_USAGE_ATTR_T (
  NL_USAGE_ATTR_ID VARCHAR2(40)  NOT NULL ,
  NL_USAGE_ID VARCHAR2(40)  NOT NULL ,
  ATTR_DEFN_ID VARCHAR2(40)  NOT NULL ,
  ATTR_VAL VARCHAR2(400) NULL ,
  VER_NBR NUMBER(8) DEFAULT 0  NOT NULL ,
  PRIMARY KEY (NL_USAGE_ATTR_ID) ,
  CONSTRAINT KRMS_NL_USAGE_ATTR_TC1 UNIQUE (NL_USAGE_ID, ATTR_DEFN_ID),
  CONSTRAINT KRMS_NL_USAGE_ATTR_FK1
    FOREIGN KEY (NL_USAGE_ID )
    REFERENCES KRMS_NL_USAGE_T (NL_USAGE_ID ) ,
  CONSTRAINT KRMS_NL_USAGE_ATTR_FK2
    FOREIGN KEY (ATTR_DEFN_ID )
    REFERENCES KRMS_ATTR_DEFN_T (ATTR_DEFN_ID )
)
/
CREATE TABLE KRMS_NL_TMPL_T (
  NL_TMPL_ID VARCHAR2(40) NOT NULL,
  LANG_CD VARCHAR2(2) NOT NULL,
  NL_USAGE_ID VARCHAR2(40) NOT NULL,
  TYP_ID VARCHAR2(40) NOT NULL,
  TMPL VARCHAR2(4000) NOT NULL,
  VER_NBR NUMBER(8) DEFAULT 0  NOT NULL,
  CONSTRAINT KRMS_NL_TMPL_FK1 FOREIGN KEY (NL_USAGE_ID) REFERENCES KRMS_NL_USAGE_T (NL_USAGE_ID),
  CONSTRAINT KRMS_TYP_T FOREIGN KEY (TYP_ID) REFERENCES KRMS_TYP_T (TYP_ID),
  PRIMARY KEY (NL_TMPL_ID),
  CONSTRAINT KRMS_NL_TMPL_TC1 UNIQUE (LANG_CD, NL_USAGE_ID, TYP_ID)
)
/
CREATE  TABLE KRMS_TYP_RELN_T (
  TYP_RELN_ID VARCHAR2(40) NOT NULL ,
  FROM_TYP_ID VARCHAR2(40) NOT NULL ,
  TO_TYP_ID VARCHAR2(40) NOT NULL ,
  RELN_TYP VARCHAR2(40) NOT NULL ,
  SEQ_NO NUMBER(5) NOT NULL,
  VER_NBR NUMBER(8) DEFAULT '0' NOT NULL,
  ACTV VARCHAR2(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (TYP_RELN_ID) ,
  CONSTRAINT KRMS_TYP_RELN_TC1 UNIQUE (FROM_TYP_ID, TO_TYP_ID, RELN_TYP) ,
  CONSTRAINT KRMS_TYP_RELN_FK1 FOREIGN KEY (FROM_TYP_ID ) REFERENCES KRMS_TYP_T (TYP_ID ),
  CONSTRAINT KRMS_TYP_RELN_FK2 FOREIGN KEY (TO_TYP_ID ) REFERENCES KRMS_TYP_T (TYP_ID )
)
/
CREATE  TABLE KRMS_REF_OBJ_KRMS_OBJ_T(
  REF_OBJ_KRMS_OBJ_ID VARCHAR2(40) NOT NULL,
  COLLECTION_NM VARCHAR2(40) NULL,
  KRMS_OBJ_ID VARCHAR2(40) NOT NULL,
  KRMS_DSCR_TYP VARCHAR2(40) NOT NULL,
  REF_OBJ_ID VARCHAR2(255) NOT NULL,
  REF_DSCR_TYP VARCHAR2(255) NOT NULL,
  NMSPC_CD VARCHAR2(40)  NOT NULL,
  ACTV VARCHAR2(1) DEFAULT 'Y'  NOT NULL ,
  VER_NBR NUMBER(8) DEFAULT 0  NOT NULL,
  PRIMARY KEY (REF_OBJ_KRMS_OBJ_ID),
  CONSTRAINT KRMS_REF_OBJ_KRMS_OBJ_TC1 UNIQUE (COLLECTION_NM, KRMS_OBJ_ID, KRMS_DSCR_TYP, REF_OBJ_ID, REF_DSCR_TYP, NMSPC_CD)
)
/
CREATE  TABLE  KRMS_NL_TMPL_ATTR_T (
  NL_TMPL_ATTR_ID VARCHAR2(40)  NOT NULL ,
  NL_TMPL_ID VARCHAR2(40)  NOT NULL ,
  ATTR_DEFN_ID VARCHAR2(40)  NOT NULL ,
  ATTR_VAL VARCHAR2(400) NULL ,
  VER_NBR NUMBER(8) DEFAULT 0  NOT NULL ,
  PRIMARY KEY (NL_TMPL_ATTR_ID) ,
  CONSTRAINT KRMS_NL_TMPL_ATTR_TC1 UNIQUE (NL_TMPL_ID, ATTR_DEFN_ID),
  CONSTRAINT KRMS_NL_TMPL_ATTR_FK1
    FOREIGN KEY (NL_TMPL_ID )
    REFERENCES KRMS_NL_TMPL_T (NL_TMPL_ID ) ,
  CONSTRAINT KRMS_NL_TMPL_ATTR_FK2
    FOREIGN KEY (ATTR_DEFN_ID )
    REFERENCES KRMS_ATTR_DEFN_T (ATTR_DEFN_ID )
)
/


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- 2012-11-28.sql
-- 


INSERT INTO KRCR_PARM_T VALUES ('KR-NS', 'All', 'OLTP_LOCKOUT_DEFAULT_MESSAGE', sys_guid(), 1, 'CONFG', 'The module you are attempting to access has been locked for maintenance.', 'Default message to display when a module is locked', 'A', 'KUALI')
/