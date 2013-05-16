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
-- mysql-2012-07-20.sql
-- 



--
-- KULRICE-7439: Assignment of "Add Message to Route Log" permission to the KR-SYS technical administrator is missing from bootstrap dataset
--

DELETE FROM KRIM_ROLE_PERM_T WHERE
ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'Technical Administrator' AND NMSPC_CD = 'KR-SYS') AND
PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Add Message to Route Log' AND NMSPC_CD = 'KUALI')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-08-29.sql
-- 



--
--     KULRICE-6676 - external message tables
--

-- ----------------------------------------------------------------------
-- krad_msg_t
-- ----------------------------------------------------------------------
CREATE TABLE KRAD_MSG_T
(
    NMSPC_CD VARCHAR(20) NOT NULL,
	CMPNT_CD VARCHAR(100) NOT NULL,
	MSG_KEY VARCHAR(100) NOT NULL,
	LOC VARCHAR(80) NOT NULL,
    OBJ_ID VARCHAR(36) NOT NULL,
	VER_NBR DECIMAL(8) NOT NULL DEFAULT 1,
	MSG_DESC VARCHAR(255),
	TXT VARCHAR(4000),
	PRIMARY KEY (NMSPC_CD,CMPNT_CD,MSG_KEY,LOC),
	UNIQUE KRAD_MSG_TC0(OBJ_ID)
)
;

-- note!! the 'loc' field in rice is currently set to 255 chars, but that exceeds the max length for primary
-- keys in mysql 5.1.  Depending on what the rice team does about this, we may need to run the following statement
-- below
-- ALTER TABLE KRAD_MSG_T MODIFY LOC VARCHAR(255)
-- ;

-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-09-13.sql
-- 



--
--     KULRICE-6676 - adding system parameter for default locale
--

INSERT INTO KRCR_PARM_T VALUES ('KR-NS', 'All', 'DEFAULT_LOCALE_CODE', uuid(), 1, 'CONFG', 'en-US',
'The locale code that should be used within the application when otherwise not specified.', 'A', 'KUALI')
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-09-29.sql
-- 



--
--     KULRICE-8349 - guest user access
--

INSERT INTO KRIM_ENTITY_T (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
VALUES ('KR1000', uuid(), 1, 'Y', now())
;

INSERT INTO KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
VALUES ('PERSON', 'KR1000', uuid(), 1, 'Y', now())
;

INSERT INTO KRIM_PRNCPL_T (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT)
VALUES ('guest', uuid(), 1, 'guest', 'KR1000', '', 'Y', now())
;

INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
VALUES ('KR1000', uuid(), 1, 'GuestRole', 'KUALI', 'This role is used for no login guest users.', '1', 'Y', now())
;

INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
VALUES ('KR1000', 1, uuid(), 'KR1000', 'guest', 'P', null, null, now())
;



-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-11-14.sql
-- 



--
-- KULRICE-8539: Apply SQL for new KRMS tables to the 2.2 master db
--
-- KULRICE-7367: Implement KRMS Type-Type Relations feature
-- KULRICE-7368: Implement KRMS Natural Language Translation feature
-- KULRICE-7369: Implement KRMS Reference Object Bindings feature
--

CREATE INDEX KRMS_ATTR_DEFN_TC3 ON KRMS_ATTR_DEFN_T(ATTR_DEFN_ID ASC)
;
CREATE TABLE KRMS_REF_OBJ_KRMS_OBJ_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;
CREATE TABLE KRMS_TYP_RELN_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;
CREATE TABLE KRMS_NL_USAGE_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;
CREATE TABLE KRMS_NL_TMPL_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;

CREATE TABLE KRMS_NL_TMPL_ATTR_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;

CREATE TABLE KRMS_NL_USAGE_ATTR_S (
  ID BIGINT(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
;


CREATE  TABLE KRMS_NL_USAGE_T(
  NL_USAGE_ID VARCHAR(40) NOT NULL ,
  NM VARCHAR(255) NOT NULL,
  NMSPC_CD VARCHAR(40)  NOT NULL,
  DESC_TXT VARCHAR(255) NULL,
  ACTV VARCHAR(1) DEFAULT 'Y'  NOT NULL ,
  VER_NBR DECIMAL(8,0) DEFAULT 0  NOT NULL,
  PRIMARY KEY (NL_USAGE_ID),
  UNIQUE INDEX KRMS_NL_USAGE_TC1 (NM ASC, NMSPC_CD ASC)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;

CREATE  TABLE  KRMS_NL_USAGE_ATTR_T (
  NL_USAGE_ATTR_ID VARCHAR(40)  NOT NULL ,
  NL_USAGE_ID VARCHAR(40)  NOT NULL ,
  ATTR_DEFN_ID VARCHAR(40)  NOT NULL ,
  ATTR_VAL VARCHAR(400) NULL ,
  VER_NBR DECIMAL(8,0) DEFAULT 0  NOT NULL ,
  PRIMARY KEY (NL_USAGE_ATTR_ID) ,
  UNIQUE INDEX KRMS_NL_USAGE_ATTR_TC1 (NL_USAGE_ID ASC, ATTR_DEFN_ID ASC),
  INDEX KRMS_NL_USAGE_ATTR_TC2(ATTR_DEFN_ID ASC),
  CONSTRAINT KRMS_NL_USAGE_ATTR_FK1
    FOREIGN KEY (NL_USAGE_ID )
    REFERENCES KRMS_NL_USAGE_T (NL_USAGE_ID ) ,
  CONSTRAINT KRMS_NL_USAGE_ATTR_FK2
    FOREIGN KEY (ATTR_DEFN_ID )
    REFERENCES KRMS_ATTR_DEFN_T (ATTR_DEFN_ID )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;
CREATE TABLE KRMS_NL_TMPL_T (
  NL_TMPL_ID VARCHAR(40) NOT NULL,
  LANG_CD VARCHAR(2) NOT NULL,
  NL_USAGE_ID VARCHAR(40) NOT NULL,
  TYP_ID VARCHAR(40) NOT NULL,
  TMPL VARCHAR(4000) NOT NULL,
  VER_NBR DECIMAL(8,0) DEFAULT 0  NOT NULL,
  CONSTRAINT KRMS_NL_TMPL_FK1 FOREIGN KEY (NL_USAGE_ID) REFERENCES KRMS_NL_USAGE_T (NL_USAGE_ID),
  CONSTRAINT KRMS_TYP_T FOREIGN KEY (TYP_ID) REFERENCES KRMS_TYP_T (TYP_ID),
  PRIMARY KEY (NL_TMPL_ID),
  UNIQUE INDEX KRMS_NL_TMPL_TC1 (LANG_CD ASC, NL_USAGE_ID ASC, TYP_ID ASC)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;
CREATE  TABLE KRMS_TYP_RELN_T (
  TYP_RELN_ID VARCHAR(40) NOT NULL ,
  FROM_TYP_ID VARCHAR(40) NOT NULL ,
  TO_TYP_ID VARCHAR(40) NOT NULL ,
  RELN_TYP VARCHAR(40) NOT NULL ,
  SEQ_NO DECIMAL(5,0) NOT NULL,
  VER_NBR DECIMAL(8,0) NOT NULL DEFAULT '0',
  ACTV VARCHAR(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (TYP_RELN_ID) ,
  UNIQUE INDEX KRMS_TYP_RELN_TC1 (FROM_TYP_ID ASC, TO_TYP_ID ASC, RELN_TYP ASC) ,
  CONSTRAINT KRMS_TYP_RELN_FK1 FOREIGN KEY (FROM_TYP_ID ) REFERENCES KRMS_TYP_T (TYP_ID ),
  CONSTRAINT KRMS_TYP_RELN_FK2 FOREIGN KEY (TO_TYP_ID ) REFERENCES KRMS_TYP_T (TYP_ID )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;
CREATE  TABLE KRMS_REF_OBJ_KRMS_OBJ_T(
  REF_OBJ_KRMS_OBJ_ID VARCHAR(40) NOT NULL,
  COLLECTION_NM VARCHAR(40) NULL,
  KRMS_OBJ_ID VARCHAR(40) NOT NULL,
  KRMS_DSCR_TYP VARCHAR(40) NOT NULL,
  REF_OBJ_ID VARCHAR(255) NOT NULL,
  REF_DSCR_TYP VARCHAR(255) NOT NULL,
  NMSPC_CD VARCHAR(40)  NOT NULL,
  ACTV VARCHAR(1) DEFAULT 'Y'  NOT NULL ,
  VER_NBR DECIMAL(8,0) DEFAULT 0  NOT NULL,
  PRIMARY KEY (REF_OBJ_KRMS_OBJ_ID),
  UNIQUE INDEX KRMS_REF_OBJ_KRMS_OBJ_TC1 (COLLECTION_NM ASC, KRMS_OBJ_ID ASC, KRMS_DSCR_TYP ASC, REF_OBJ_ID ASC, REF_DSCR_TYP ASC, NMSPC_CD ASC)
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;
CREATE  TABLE  KRMS_NL_TMPL_ATTR_T (
  NL_TMPL_ATTR_ID VARCHAR(40)  NOT NULL ,
  NL_TMPL_ID VARCHAR(40)  NOT NULL ,
  ATTR_DEFN_ID VARCHAR(255)  NOT NULL ,
  ATTR_VAL VARCHAR(400) NULL ,
  VER_NBR DECIMAL(8,0) DEFAULT 0  NOT NULL ,
  PRIMARY KEY (NL_TMPL_ATTR_ID) ,
  UNIQUE INDEX KRMS_NL_TMPL_ATTR_TC1 (NL_TMPL_ID ASC, ATTR_DEFN_ID ASC),
  CONSTRAINT KRMS_NL_TMPL_ATTR_FK1
    FOREIGN KEY (NL_TMPL_ID )
    REFERENCES KRMS_NL_TMPL_T (NL_TMPL_ID ) ,
  CONSTRAINT KRMS_NL_TMPL_ATTR_FK2
    FOREIGN KEY (ATTR_DEFN_ID )
    REFERENCES KRMS_ATTR_DEFN_T (ATTR_DEFN_ID )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin
;


-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
-- mysql-2012-11-28.sql
-- 


INSERT INTO KRCR_PARM_T VALUES ('KR-NS', 'All', 'OLTP_LOCKOUT_DEFAULT_MESSAGE', uuid(), 1, 'CONFG', 'The module you are attempting to access has been locked for maintenance.', 'Default message to display when a module is locked', 'A', 'KUALI')
;