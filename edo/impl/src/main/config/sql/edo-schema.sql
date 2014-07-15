--------------------------------------------------------
--  File created - Tuesday-March-19-2013
--------------------------------------------------------
--  DDL for Sequence CANDIDATE_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.CANDIDATE_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 32 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CHECKLIST_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.CHECKLIST_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CHECKLIST_ITEM_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.CHECKLIST_ITEM_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 23 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CHECKLIST_SECTION_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.CHECKLIST_SECTION_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 5 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence COMMITTEE_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.COMMITTEE_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence DOSSIER_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.DOSSIER_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 28 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence DOSSIER_TYPE_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.DOSSIER_TYPE_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 5 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ITEM_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.ITEM_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 25 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ITEM_TYPE_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.ITEM_TYPE_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 6 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PERSON_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.PERSON_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REVIEW_LAYER_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.REVIEW_LAYER_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ROLE_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.ROLE_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence VOTE_RECORD_ID_S
--------------------------------------------------------

   CREATE SEQUENCE  EDO.VOTE_RECORD_ID_S  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;

--------------------------------------------------------
--  DDL for Table EDO_REVIEW_LAYER_DEF_T
--------------------------------------------------------
CREATE TABLE EDO.EDO_REVIEW_LAYER_DEF_T
(
  REVIEW_LAYER_DEF_ID  NUMBER(8)                NOT NULL,
  NODE_NAME            VARCHAR2(30 BYTE)        NOT NULL,
  VOTE_TYPE            VARCHAR2(16 BYTE)        NOT NULL,
  DESCRIPTION          CLOB,
  REVIEW_LETTER        CHAR(1 BYTE)             NOT NULL,
  CREATE_DATE          DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(16 BYTE)        NOT NULL,
  LAST_UPDATED_DATE    DATE,
  UPDATED_BY           VARCHAR2(16 BYTE),
  OBJ_ID               VARCHAR2(36 BYTE),
  VER_NBR              NUMBER(38),
  ROUTE_LEVEL          NUMBER(3),
  REVIEW_LEVEL         NUMBER(3),
  WORKFLOW_ID          VARCHAR2(25 BYTE)        DEFAULT 'DEFAULT'             NOT NULL,
  WORKFLOW_QUALIFIER   VARCHAR2(25 BYTE)
);

CREATE UNIQUE INDEX EDO.EDO_REVIEW_LAYER_DEF_T_PK ON EDO.EDO_REVIEW_LAYER_DEF_T (REVIEW_LAYER_DEF_ID);

ALTER TABLE EDO.EDO_REVIEW_LAYER_DEF_T ADD (
  CONSTRAINT EDO_REVIEW_LAYER_DEF_T_PK
  PRIMARY KEY
  (REVIEW_LAYER_DEF_ID)
  USING INDEX EDO.EDO_REVIEW_LAYER_DEF_T_PK
  ENABLE VALIDATE);

--------------------------------------------------------
--  DDL for Table EDO_DOSSIER_DOCUMENT_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_DOSSIER_DOCUMENT_T
(
  DOCUMENT_ID      VARCHAR2(14 BYTE)            NOT NULL,
  DOSSIER_ID       NUMBER(8),
  PRINCIPAL_ID     VARCHAR2(40 BYTE),
  DOCUMENT_STATUS  VARCHAR2(1 BYTE),
  OBJ_ID           VARCHAR2(36 BYTE),
  VER_NBR          NUMBER(38),
  DOC_TYPE_NAME    VARCHAR2(64 BYTE)
);


CREATE UNIQUE INDEX EDO.EDO_DOSSIER_DOCUMENT_PK ON EDO.EDO_DOSSIER_DOCUMENT_T (DOCUMENT_ID);

ALTER TABLE EDO.EDO_DOSSIER_DOCUMENT_T ADD (
  CONSTRAINT EDO_DOSSIER_DOCUMENT_PK
  PRIMARY KEY
  (DOCUMENT_ID)
  USING INDEX EDO.EDO_DOSSIER_DOCUMENT_PK
  ENABLE VALIDATE);

--------------------------------------------------------
--  DDL for Table EDO_AREA_OF_EXCELLENCE_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_AREA_OF_EXCELLENCE_T
(
  AREA_OF_EXCELLENCE_TYPE_ID  NUMBER(8),
  OPTION_KEY                  VARCHAR2(3 CHAR),
  OPTION_VALUE                VARCHAR2(50 BYTE),
  CREATE_DATE                 DATE,
  CREATED_BY                  VARCHAR2(50 BYTE),
  LAST_UPDATE_DATE            DATE,
  UPDATED_BY                  VARCHAR2(50 BYTE)
);


CREATE UNIQUE INDEX EDO.AREA_OF_EXCELLENCE_TYPE_P1 ON EDO.EDO_AREA_OF_EXCELLENCE_T (AREA_OF_EXCELLENCE_TYPE_ID);

ALTER TABLE EDO.EDO_AREA_OF_EXCELLENCE_T ADD (
  CONSTRAINT AREA_OF_EXCELLENCE_TYPE_P1
  PRIMARY KEY
  (AREA_OF_EXCELLENCE_TYPE_ID)
  USING INDEX EDO.AREA_OF_EXCELLENCE_TYPE_P1
  ENABLE VALIDATE);

--------------------------------------------------------
--  DDL for Table EDO_GROUP_DEF_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_GROUP_DEF_T
(
  GROUP_ID        NUMBER(15,5)                  NOT NULL,
  WORKFLOW_ID     VARCHAR2(25 BYTE)             NOT NULL,
  WORKFLOW_LEVEL  VARCHAR2(25 BYTE)             NOT NULL,
  DOSSIER_TYPE    VARCHAR2(25 BYTE),
  WORKFLOW_TYPE   VARCHAR2(25 BYTE)             NOT NULL,
  KIM_TYPE_NAME   VARCHAR2(25 BYTE)             NOT NULL,
  KIM_ROLE_NAME   VARCHAR2(64 BYTE)
);

CREATE UNIQUE INDEX EDO.GROUP_PK ON EDO.EDO_GROUP_DEF_T (GROUP_ID);

ALTER TABLE EDO.EDO_GROUP_DEF_T ADD (
  CONSTRAINT GROUP_PK
  PRIMARY KEY
  (GROUP_ID)
  USING INDEX EDO.GROUP_PK
  ENABLE VALIDATE);

--------------------------------------------------------
--  DDL for Table EDO_GROUP_TRACKING_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_GROUP_TRACKING_T
(
  GROUP_TRACKING_ID  NUMBER(15,5)               NOT NULL,
  DEPARTMENT_ID      VARCHAR2(64 BYTE),
  SCHOOL_ID          VARCHAR2(64 BYTE),
  CAMPUS_ID          VARCHAR2(64 BYTE),
  REVIEW_LEVEL_NAME  VARCHAR2(25 BYTE)          NOT NULL,
  KIM_GROUP_NAME     VARCHAR2(64 BYTE)          NOT NULL,
  GROUP_ADDED_DATE   DATE                       NOT NULL
);

CREATE UNIQUE INDEX EDO.GROUP_TRACKING_PK ON EDO.EDO_GROUP_TRACKING_T (GROUP_TRACKING_ID);

CREATE OR REPLACE TRIGGER EDO.EDO_GROUP_TRACKING_TR   before insert ON EDO.EDO_GROUP_TRACKING_T   for each row
begin
   if inserting then
      if :NEW.GROUP_TRACKING_ID is null then
         select EDO_GROUP_TRACKING_ID_S.nextval into :NEW.GROUP_TRACKING_ID from dual;
      end if;
   end if;
end;
/

ALTER TABLE EDO.EDO_GROUP_TRACKING_T ADD (
  CONSTRAINT GROUP_TRACKING_PK
  PRIMARY KEY
  (GROUP_TRACKING_ID)
  USING INDEX EDO.GROUP_TRACKING_PK
  ENABLE VALIDATE);

--------------------------------------------------------
--  DDL for Table ACCT_DD_ATTR_DOC
--------------------------------------------------------

  CREATE TABLE EDO.ACCT_DD_ATTR_DOC
   (	DOC_HDR_ID VARCHAR2(14 BYTE),
	OBJ_ID VARCHAR2(36 BYTE),
	VER_NBR NUMBER(14,0),
	ACCT_NUM NUMBER(14,0),
	ACCT_OWNR VARCHAR2(50 BYTE),
	ACCT_BAL NUMBER(16,2),
	ACCT_OPN_DAT DATE,
	ACCT_STAT VARCHAR2(30 BYTE),
	ACCT_UPDATE_DT_TM DATE,
	ACCT_AWAKE VARCHAR2(1 BYTE)
   ) SEGMENT CREATION DEFERRED
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  TABLESPACE USERS01 ;
--------------------------------------------------------
--  DDL for Table EDO_CANDIDATE_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_CANDIDATE_T
(
  CANDIDATE_ID           NUMBER(8),
  LAST_NAME              VARCHAR2(45 BYTE)      NOT NULL,
  FIRST_NAME             VARCHAR2(45 BYTE),
  CURRENT_RANK           VARCHAR2(45 BYTE),
  PRIMARY_DEPARTMENT_ID  VARCHAR2(12 BYTE)      NOT NULL,
  TNP_DEPARTMENT_ID      VARCHAR2(12 BYTE)      NOT NULL,
  CANDIDACY_SCHOOL       VARCHAR2(12 BYTE)      NOT NULL,
  CANDIDACY_CAMPUS       CHAR(2 BYTE)           NOT NULL,
  USERNAME               VARCHAR2(16 BYTE),
  CURRENTDOSSIER         VARCHAR2(32 BYTE)
) ;
--------------------------------------------------------
--  DDL for Table EDO_CHECKLIST_ITEM_T
--------------------------------------------------------

 CREATE TABLE EDO.EDO_CHECKLIST_ITEM_T
(
  CHECKLIST_ITEM_ID               NUMBER(8),
  CHECKLIST_SECTION_ID            NUMBER(8)     NOT NULL,
  DESCRIPTION                     CLOB,
  REQUIRED                        NUMBER(1),
  CREATE_DATE                     DATE,
  CREATED_BY                      NUMBER(8),
  LAST_UPDATE_DATE                DATE,
  UPDATED_BY                      NUMBER(8),
  CHECKLIST_ITEM_NAME             VARCHAR2(200 BYTE),
  CHECKLIST_ITEM_SECTION_ORDINAL  NUMBER(4)
) ;
--------------------------------------------------------
--  DDL for Table EDO_CHECKLIST_SECTION_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_CHECKLIST_SECTION_T
(
  CHECKLIST_SECTION_ID       NUMBER(8),
  CHECKLIST_ID               NUMBER(8)          NOT NULL,
  DESCRIPTION                CLOB,
  CREATE_DATE                DATE,
  CREATED_BY                 NUMBER(8),
  LAST_UPDATE_DATE           DATE,
  UPDATED_BY                 NUMBER(8),
  CHECKLIST_SECTION_NAME     VARCHAR2(30 BYTE),
  CHECKLIST_SECTION_ORDINAL  NUMBER(4)
) ;
--------------------------------------------------------
--  DDL for Table EDO_CHECKLIST_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_CHECKLIST_T
(
  CHECKLIST_ID       NUMBER(8),
  DOSSIER_TYPE_CODE  CHAR(2 BYTE)               NOT NULL,
  DEPARTMENT_ID      VARCHAR2(12 BYTE),
  DESCRIPTION        CLOB,
  CREATE_DATE        DATE,
  CREATED_BY         NUMBER(8),
  LAST_UPDATE_DATE   DATE,
  UPDATED_BY         NUMBER(8),
  SCHOOL_ID          VARCHAR2(12 BYTE),
  CAMPUS_CODE        CHAR(2 BYTE),
  EFFECTIVE_DATE     DATE                       NOT NULL
) ;

--------------------------------------------------------
--  DDL for Table EDO_DOSSIER_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_DOSSIER_T
(
  DOSSIER_ID          NUMBER(8),
  DOSSIER_TYPE_ID     NUMBER(8)                 NOT NULL,
  CHECKLIST_ID        NUMBER(8),
  AOE_CODE            CHAR(1 BYTE),
  CAMPUS_CODE         CHAR(2 BYTE),
  DEPARTMENT_ID       VARCHAR2(12 BYTE),
  SCHOOL_ID           VARCHAR2(12 BYTE),
  CURRENT_RANK        VARCHAR2(32 BYTE),
  RANK_SOUGHT         VARCHAR2(32 BYTE),
  DUE_DATE            DATE,
  CREATE_DATE         DATE,
  LAST_UPDATE_DATE    DATE,
  DOCUMENT_ID         VARCHAR2(40 BYTE),
  DOSSIER_STATUS      VARCHAR2(12 BYTE),
  CANDIDATE_USERNAME  VARCHAR2(16 BYTE),
  CREATED_BY          VARCHAR2(16 BYTE),
  UPDATED_BY          VARCHAR2(16 BYTE),
  WORKFLOW_ID         VARCHAR2(25 BYTE),
  SECONDARY_UNIT      VARCHAR2(12 BYTE),
  CANDIDACY_YEAR      NUMBER(8)
) ;
--------------------------------------------------------
--  DDL for Table EDO_DOSSIER_TYPE_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_DOSSIER_TYPE_T
(
  DOSSIER_TYPE_ID    NUMBER(8),
  DOSSIER_TYPE_NAME  VARCHAR2(45 BYTE),
  CREATE_DATE        DATE,
  CREATED_BY         NUMBER(8),
  LAST_UPDATE_DATE   DATE,
  UPDATED_BY         NUMBER(8),
  DOSSIER_TYPE_CODE  CHAR(2 BYTE),
  DOC_TYPE_NAME      VARCHAR2(64 BYTE)
) ;
--------------------------------------------------------
--  DDL for Table EDO_ITEM_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_ITEM_T
(
  ITEM_ID              NUMBER(8),
  UPLOADER_USERNAME    VARCHAR2(16 BYTE)        NOT NULL,
  ITEM_TYPE_ID         NUMBER(8)                NOT NULL,
  CHECKLIST_ITEM_ID    NUMBER(8)                NOT NULL,
  DOSSIER_ID           NUMBER(8)                NOT NULL,
  UPLOAD_DATE          TIMESTAMP(6)             NOT NULL,
  FILE_NAME            VARCHAR2(1024 BYTE)      NOT NULL,
  FILE_LOCATION        VARCHAR2(1024 BYTE)      NOT NULL,
  NOTES                CLOB,
  ADDENDUM_ROUTED      NUMBER(1),
  CREATE_DATE          TIMESTAMP(6),
  CREATED_BY           VARCHAR2(16 BYTE),
  LAST_UPDATE_DATE     TIMESTAMP(6),
  UPDATED_BY           VARCHAR2(16 BYTE),
  LAYER_LEVEL          VARCHAR2(16 BYTE),
  CONTENT_TYPE         VARCHAR2(128 BYTE),
  ROW_INDEX            NUMBER(15,5),
  REVIEW_LAYER_DEF_ID  NUMBER(8),
  FILE_DESCRIPTION     VARCHAR2(4000 BYTE)
) ;
--------------------------------------------------------
--  DDL for Table EDO_ITEM_TYPE_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_ITEM_TYPE_T
(
  ITEM_TYPE_ID      NUMBER(8),
  ITEM_TYPE_NAME    VARCHAR2(32 BYTE)           NOT NULL,
  DESCRIPTION       CLOB,
  INSTRUCTIONS      CLOB,
  EXT_AVAILABILITY  NUMBER(1),
  CREATE_DATE       DATE,
  CREATED_BY        NUMBER(8),
  LAST_UPDATE_DATE  DATE,
  UPDATED_BY        NUMBER(8)
) ;

--------------------------------------------------------
--  DDL for Table EDO_VOTE_RECORD_T
--------------------------------------------------------

CREATE TABLE EDO.EDO_VOTE_RECORD_T
(
  VOTE_RECORD_ID           NUMBER(8),
  DOSSIER_ID               NUMBER(8)            NOT NULL,
  REVIEW_LAYER_ID          NUMBER(8)            NOT NULL,
  VOTE_TYPE                VARCHAR2(16 BYTE)    NOT NULL,
  YES_COUNT_TENURE         NUMBER(8),
  NO_COUNT_TENURE          NUMBER(8),
  ABSENT_COUNT_TENURE      NUMBER(8),
  ABSTAIN_COUNT_TENURE     NUMBER(8),
  AOE_CODE                 CHAR(1 BYTE),
  CREATED_AT               TIMESTAMP(6),
  CREATED_BY               VARCHAR2(25 BYTE),
  UPDATED_AT               TIMESTAMP(6),
  UPDATED_BY               VARCHAR2(25 BYTE),
  VOTE_ROUND               NUMBER(8),
  VOTE_CATEGORY            VARCHAR2(16 BYTE),
  YES_COUNT_PROMOTION      NUMBER(8),
  NO_COUNT_PROMOTION       NUMBER(8),
  ABSENT_COUNT_PROMOTION   NUMBER(8),
  ABSTAIN_COUNT_PROMOTION  NUMBER(8),
  VOTE_SUBROUND            NUMBER(15,5)         DEFAULT 0
) ;
--------------------------------------------------------
--  DDL for View EDO_CHECKLIST_V
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW EDO.EDO_CHECKLIST_V (CHECKLIST_DESC, DEPARTMENT_ID, SCHOOL_ID, CAMPUS_CODE, DOSSIER_TYPE_CODE, EFFECTIVE_DATE, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ID, SECTION_DESC, CHECKLIST_SECTION_ORDINAL, CHECKLIST_ITEM_ID, CHECKLIST_ITEM_NAME, ITEM_DESC, REQUIRED, CHECKLIST_ITEM_SECTION_ORDINAL) AS
  select l.description as checklist_desc, l.department_id,l.school_id,l.campus_code,l.dossier_type_code,l.effective_date,
       s.checklist_section_name,s.checklist_section_id, s.description as section_desc, s.checklist_section_ordinal,
       i.checklist_item_id, i.checklist_item_name,i.description as item_desc,i.required,
       i.checklist_item_section_ordinal
  from edo_checklist_t l left outer join edo_checklist_section_t s on s.checklist_id = l.checklist_id
       left outer join edo_checklist_item_t i on s.checklist_section_id = i.checklist_section_id;
--------------------------------------------------------
--  DDL for View EDO_ITEM_V
--------------------------------------------------------

CREATE OR REPLACE VIEW EDO.EDO_ITEM_V ( ITEM_ID, UPLOADER_USERNAME, DOSSIER_ID, UPLOAD_DATE, FILE_NAME, FILE_LOCATION, FILE_DESCRIPTION, NOTES, ADDENDUM_ROUTED, LAYER_LEVEL, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, ITEM_TYPE_ID, UPDATED_BY, CONTENT_TYPE, ROW_INDEX, REVIEW_LAYER_DEF_ID, CHECKLIST_SECTION_ID, CHECKLIST_ITEM_ID, ITEM_TYPE_NAME, DESCRIPTION, INSTRUCTIONS, EXT_AVAILABILITY, CANDIDATE_USERNAME, REVIEW_LAYER_DESCRIPTION, REVIEW_LEVEL )
AS
SELECT i.item_id,
  i.uploader_username,
  i.dossier_id,
  i.upload_date,
  i.file_name,
  i.file_location,
  i.file_description,
  i.notes,
  i.addendum_routed,
  i.layer_level,
  i.create_date,
  i.created_by,
  i.last_update_date,
  i.item_type_id,
  i.updated_by,
  i.content_type,
  i.row_index,
  i.review_layer_def_id,
  ci.checklist_section_id,
  ci.checklist_item_id,
  it.item_type_name,
  it.description,
  it.instructions,
  it.ext_availability,
  do.candidate_username,
  rl.description AS review_layer_description,
  rl.review_level
 FROM edo_item_t i
  INNER JOIN edo_checklist_item_t ci
 ON i.checklist_item_id = ci.checklist_item_id
  INNER JOIN edo_item_type_t it ON i.item_type_id = it.item_type_id
  INNER JOIN edo_dossier_t do ON i.dossier_id = do.dossier_id
  LEFT OUTER JOIN edo_review_layer_def_t rl
 ON i.review_layer_def_id = rl.review_layer_def_id
--------------------------------------------------------
--  DDL for Index EDO_CHECKLIST_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_CHECKLIST_TP1 ON EDO.EDO_CHECKLIST_T (CHECKLIST_ID);
--------------------------------------------------------
--  DDL for Index EDO_DOSSIER_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_DOSSIER_TP1 ON EDO.EDO_DOSSIER_T (DOSSIER_ID);
--------------------------------------------------------
--  DDL for Index EDO_CHECKLIST_SECTION_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_CHECKLIST_SECTION_TP1 ON EDO.EDO_CHECKLIST_SECTION_T (CHECKLIST_SECTION_ID);
--------------------------------------------------------
--  DDL for Index EDO_VOTE_RECORD_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_VOTE_RECORD_TP1 ON EDO.EDO_VOTE_RECORD_T (VOTE_RECORD_ID);
--------------------------------------------------------
--  DDL for Index EDO_ITEM_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_ITEM_TP1 ON EDO.EDO_ITEM_T (ITEM_ID);
--------------------------------------------------------
--  DDL for Index EDO_DOSSIER_TYPE_TP1
--------------------------------------------------------

  CREATE UNIQUE INDEX EDO.EDO_DOSSIER_TYPE_TP1 ON EDO.EDO_DOSSIER_TYPE_T (DOSSIER_TYPE_ID);
--------------------------------------------------------
--  Constraints for Table EDO_VOTE_RECORD_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_VOTE_RECORD_T ADD CONSTRAINT EDO_VOTE_RECORD_TP1 PRIMARY KEY (VOTE_RECORD_ID);
  ALTER TABLE EDO.EDO_VOTE_RECORD_T MODIFY (DOSSIER_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_VOTE_RECORD_T MODIFY (REVIEW_LAYER_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_VOTE_RECORD_T MODIFY (VOTE_TYPE NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EDO_ITEM_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_ITEM_T MODIFY (UPLOADER_USERNAME NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (ITEM_TYPE_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (CHECKLIST_ITEM_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (DOSSIER_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (UPLOAD_DATE NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (FILE_NAME NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T MODIFY (FILE_LOCATION NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_ITEM_T ADD CONSTRAINT EDO_ITEM_TP1 PRIMARY KEY (ITEM_ID);0
--------------------------------------------------------
--  Constraints for Table EDO_ITEM_TYPE_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_ITEM_TYPE_T ADD CONSTRAINT EDO_ITEM_TYPE_TP1 PRIMARY KEY (ITEM_TYPE_ID);
  ALTER TABLE EDO.EDO_ITEM_TYPE_T MODIFY (ITEM_TYPE_NAME NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EDO_DOSSIER_TYPE_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_DOSSIER_TYPE_T ADD CONSTRAINT EDO_DOSSIER_TYPE_TP1 PRIMARY KEY (DOSSIER_TYPE_ID);
--------------------------------------------------------
--  Constraints for Table EDO_CHECKLIST_ITEM_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_CHECKLIST_ITEM_T ADD CONSTRAINT EDO_CHECKLIST_ITEM_TP1 PRIMARY KEY (CHECKLIST_ITEM_ID);
  ALTER TABLE EDO.EDO_CHECKLIST_ITEM_T MODIFY (CHECKLIST_SECTION_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EDO_CANDIDATE_T
--------------------------------------------------------

  ALTER TABLE EDO.EDO_CANDIDATE_T ADD CONSTRAINT EDO_CANDIDATE_TP1 PRIMARY KEY (CANDIDATE_ID);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (LAST_NAME NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (PRIMARY_DEPARTMENT_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (TNP_DEPARTMENT_ID NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (CANDIDACY_YEAR NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (CANDIDACY_SCHOOL NOT NULL ENABLE);
  ALTER TABLE EDO.EDO_CANDIDATE_T MODIFY (CANDIDACY_CAMPUS NOT NULL ENABLE);
--------------------------------------------------------
--  DDL for Trigger EDO_CANDIDATE_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_CANDIDATE_TR
   before insert on EDO_CANDIDATE_T
   for each row
begin
   if inserting then
      if :NEW.CANDIDATE_ID is null then
         select CANDIDATE_ID_S.nextval into :NEW.CANDIDATE_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_CANDIDATE_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger EDO_CHECKLIST_ITEM_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_CHECKLIST_ITEM_TR
   before insert on EDO_CHECKLIST_ITEM_T
   for each row
begin
   if inserting then
      if :NEW.CHECKLIST_ITEM_ID is null then
         select CHECKLIST_ITEM_ID_S.nextval into :NEW.CHECKLIST_ITEM_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_CHECKLIST_ITEM_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger EDO_CHECKLIST_SECTION_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_CHECKLIST_SECTION_TR
   before insert on EDO_CHECKLIST_SECTION_T
   for each row
begin
   if inserting then
      if :NEW.CHECKLIST_SECTION_ID is null then
         select CHECKLIST_SECTION_ID_S.nextval into :NEW.CHECKLIST_SECTION_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_CHECKLIST_SECTION_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger EDO_CHECKLIST_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_CHECKLIST_TR
   before insert on EDO_CHECKLIST_T
   for each row
begin
   if inserting then
      if :NEW.CHECKLIST_ID is null then
         select CHECKLIST_ID_S.nextval into :NEW.CHECKLIST_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_CHECKLIST_TR ENABLE;

--------------------------------------------------------
--  DDL for Trigger EDO_DOSSIER_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_DOSSIER_TR
   before insert on EDO_DOSSIER_T
   for each row
begin
   if inserting then
      if :NEW.DOSSIER_ID is null then
         select DOSSIER_ID_S.nextval into :NEW.DOSSIER_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_DOSSIER_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger EDO_DOSSIER_TYPE_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_DOSSIER_TYPE_TR
   before insert on EDO_DOSSIER_TYPE_T
   for each row
begin
   if inserting then
      if :NEW.DOSSIER_TYPE_ID is null then
         select DOSSIER_TYPE_ID_S.nextval into :NEW.DOSSIER_TYPE_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_DOSSIER_TYPE_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger EDO_ITEM_TYPE_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_ITEM_TYPE_TR
   before insert on EDO_ITEM_TYPE_T
   for each row
begin
   if inserting then
      if :NEW.ITEM_TYPE_ID is null then
         select ITEM_TYPE_ID_S.nextval into :NEW.ITEM_TYPE_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_ITEM_TYPE_TR ENABLE;

--------------------------------------------------------
--  DDL for Trigger EDO_VOTE_RECORD_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.EDO_VOTE_RECORD_TR
   before insert on EDO_VOTE_RECORD_T
   for each row
begin
   if inserting then
      if :NEW.VOTE_RECORD_ID is null then
         select VOTE_RECORD_ID_S.nextval into :NEW.VOTE_RECORD_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.EDO_VOTE_RECORD_TR ENABLE;
--------------------------------------------------------
--  DDL for Trigger ITEM_ID_S_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER EDO.ITEM_ID_S_TR
   before insert on EDO_ITEM_T
   for each row
begin
   if inserting then
      if :NEW.ITEM_ID is null then
         select ITEM_ID_S.nextval into :NEW.ITEM_ID from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER EDO.ITEM_ID_S_TR ENABLE;

---------------------------------------------------
-- DDL for EDO_WORKFLOW_DEF_T

CREATE TABLE EDO_WORKFLOW_DEF_T  (
	WORKFLOW_ID         	VARCHAR2(25) NOT NULL,
	WORKFLOW_NAME       	VARCHAR2(25) NOT NULL,
	WORKFLOW_DESCRIPTION	VARCHAR2(512) NULL,
	WORKFLOW_UPDATED_BY 	VARCHAR2(25) NOT NULL,
	WORKFLOW_UPDATED    	DATE DEFAULT sysdate NOT NULL,
	CONSTRAINT workflow_pk PRIMARY KEY(WORKFLOW_ID)
	NOT DEFERRABLE
	 VALIDATE
);


CREATE UNIQUE INDEX workflow_pk
	ON EDO_WORKFLOW_DEF_T(WORKFLOW_ID);

---------------------------------------------------
-- DDL for EDO_SUPP_REVIEW_LAYER_DEF_T

CREATE TABLE EDO.EDO_SUPP_REVIEW_LAYER_DEF_T
(
  SUPP_REVIEW_LAYER_DEF_ID  NUMBER(8)           NOT NULL,
  REVIEW_LAYER_DEF_ID       NUMBER(8)           NOT NULL,
  SUPPLEMENTAL_NODE_NAME    VARCHAR2(100 BYTE)  NOT NULL,
  ACKNOWLEDGE_FLAG          CHAR(1 BYTE)        NOT NULL,
  WORKFLOW_ID               VARCHAR2(25 BYTE)   DEFAULT 'DEFAULT'             NOT NULL,
  WORKFLOW_QUALIFIER        VARCHAR2(25 BYTE)   NOT NULL,
  DESCRIPTION               CLOB
);

CREATE UNIQUE INDEX EDO.EDO_SUPP_REVIEW_LAYER_DEF_T_PK ON EDO.EDO_SUPP_REVIEW_LAYER_DEF_T (SUPP_REVIEW_LAYER_DEF_ID);

ALTER TABLE EDO.EDO_SUPP_REVIEW_LAYER_DEF_T ADD (
  CONSTRAINT EDO_SUPP_REVIEW_LAYER_DEF_T_PK
  PRIMARY KEY
  (SUPP_REVIEW_LAYER_DEF_ID)
  USING INDEX EDO.EDO_SUPP_REVIEW_LAYER_DEF_T_PK
  ENABLE VALIDATE);

ALTER TABLE EDO.EDO_SUPP_REVIEW_LAYER_DEF_T ADD (
  CONSTRAINT EDO_SUPP_REVIEW_LAYER_DEF_T_FK
  FOREIGN KEY (REVIEW_LAYER_DEF_ID)
  REFERENCES EDO.EDO_REVIEW_LAYER_DEF_T (REVIEW_LAYER_DEF_ID)
  ENABLE VALIDATE);


CREATE OR REPLACE VIEW EDO_CANDIDATEDOSSIER_V ( FIRST_NAME, LAST_NAME, USERNAME, CANDIDACY_CAMPUS, CANDIDACY_SCHOOL, CANDIDACY_YEAR, CANDIDATE_ID, CURRENT_RANK, PRIMARY_DEPARTMENT_ID, SECONDARY_UNIT, DOSSIER_ID, DOSSIER_STATUS, RANK_SOUGHT, DUE_DATE, DOCUMENT_ID, WORKFLOW_ID )
AS
SELECT c.first_name,
  c.last_name,
  c.username,
  c.candidacy_campus,
  c.candidacy_school,
  c.candidacy_year,
  c.candidate_id,
  c.current_rank,
  c.primary_department_id,
  d.secondary_unit,
  d.dossier_id,
  d.dossier_status,
  d.rank_sought,
  d.due_date,
  d.document_id,
  d.workflow_id
 FROM edo_candidate_t c
  INNER JOIN edo_dossier_t d ON c.username = d.candidate_username;

CREATE SEQUENCE EDO.EDO_SUBMIT_BUTTON_DISPLAY_ID_S MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER NOCYCLE ;

CREATE OR REPLACE TRIGGER EDO_SUBMIT_BUTTON_DISPLAY_T_TR
before insert on edo_submit_button_display_t
for each row
begin
if inserting then
if :NEW.edo_submit_button_display_id is null then
select EDO_SUBMIT_BUTTON_DISPLAY_ID_S.nextval into :NEW.edo_submit_button_display_id from dual;
end if;
end if;
end;

-----------------------------------------------------------------------------
-- The DDL for the report view requires this function to exist.

create or replace function get_ethnicity( v_emplid varchar2) return varchar2
as
--
cursor c1 is
  select et.ethnic_grp_cd
  from sysadm.ps_divers_ethnic et
  where emplid = v_emplid;
--
v_ethnic varchar2( 50 );
v_first  boolean;
begin
    v_ethnic := '';
    v_first  := true;
    --
    begin
        for rec in c1 loop

            if (v_first = true) then
                v_ethnic := rec.ethnic_grp_cd;
                v_first  := false;
            else
                v_ethnic := v_ethnic || ' ' || rec.ethnic_grp_cd;
            end if;
        end loop;
    exception when others then
        v_ethnic := v_ethnic || ' ' || 'error';
    end;
    --
    return v_ethnic;
end;
/

-----------------------------------------------------------------------------
-- DDL for edo_pt_report_v for handling the P&T report

create or replace view EDO_PT_REPORT_V as
select vr.vote_record_id
     , a.candidate_username, tr.iu_last_name_prf, tr.iu_first_name_prf, ae.option_value
     , a.current_rank, a.dossier_id, a.dossier_status, a.rank_sought, a.department_id
     , a.school_id, gn.sex as gender, b.dossier_type_name,  a.dossier_type_id
     , vr.vote_round, df.route_level, to_char(df.description)review_layer
     , df.vote_type, df.workflow_id, vr.absent_count_promotion, vr.absent_count_tenure
     , vr.abstain_count_promotion, vr.abstain_count_tenure, vr.no_count_promotion
     , vr.no_count_tenure, vr.yes_count_promotion, vr.yes_count_tenure, vr.aoe_code
     , vt.visa_permit_type, get_ethnicity(tr.emplid) ethnicity
from edo.edo_dossier_t            a
   , edo.edo_dossier_type_t       b
   , edo.edo_area_of_excellence_t ae
   , edo.edo_vote_record_t        vr
   , edo.edo_review_layer_def_t   df
   , ims.ims_translate_t          tr
   , sysadm.ps_visa_pmt_data      vt
   , sysadm.ps_pers_data_effdt    gn
where a.dossier_type_id  = b.dossier_type_id
  and a.aoe_code         = ae.option_key
  and a.dossier_id       = vr.dossier_id
  and vr.review_layer_id = df.review_layer_def_id
  and vr.vote_round      = ( select max(vote_round)
                             from edo.edo_vote_record_t
                             where dossier_id      = vr.dossier_id
                               and review_layer_id = vr.review_layer_id )
  and tr.emplid          = vt.emplid
  and vt.effdt           = ( select max( effdt )
                             from sysadm.ps_visa_pmt_data
                             where emplid       = vt.emplid
                               and dependent_id = vt.dependent_id
                               and country      = vt.country
                               and effdt       <= sysdate )
  and tr.emplid          = gn.emplid
  and gn.effdt           = ( select max( effdt )
                             from sysadm.ps_pers_data_effdt
                             where emplid = gn.emplid
                               and effdt <= sysdate )
  and tr.username        = a.candidate_username;

create table edo_submit_button_display_t(
   edo_submit_button_display_id NUMBER(8,0) NOT NULL enable,
   campus_code CHAR(2 BYTE),
   active_flag CHAR(1 BYTE)
   );

