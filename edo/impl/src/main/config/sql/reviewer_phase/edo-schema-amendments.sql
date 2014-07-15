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
  WORKFLOW_ID          VARCHAR2(25 BYTE)        DEFAULT 'DEFAULT'             NOT NULL
);


CREATE UNIQUE INDEX EDO.EDO_REVIEW_LAYER_DEF_T_PK ON EDO.EDO_REVIEW_LAYER_DEF_T (REVIEW_LAYER_DEF_ID);

ALTER TABLE EDO.EDO_REVIEW_LAYER_DEF_T ADD (
  CONSTRAINT EDO_REVIEW_LAYER_DEF_T_PK
  PRIMARY KEY
  (REVIEW_LAYER_DEF_ID)
  USING INDEX EDO.EDO_REVIEW_LAYER_DEF_T_PK
  ENABLE VALIDATE);

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

----------------------------------------------

Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 1, 'R', 'Research/Creative Activity', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 2, 'T', 'Teaching', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 3, 'S', 'Service Engagement', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 4, 'B', 'Balanced Case', sysdate, 'EDO', sysdate, 'EDO' );

COMMIT;


-------------------

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

----------------------------------------------

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
      if :NEW."GROUP_TRACKING_ID" is null then
         select EDO_GROUP_TRACKING_ID_S.nextval into :NEW."GROUP_TRACKING_ID" from dual;
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

----------------------------------------------
