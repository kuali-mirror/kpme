--
----------------- drop schema objects if they exist
--

drop table EDO_CANDIDATE_T;
drop table EDO_CHECKLIST_ITEM_T;
drop table EDO_CHECKLIST_SECTION_T;
drop table EDO_CHECKLIST_T;
drop table EDO_DOSSIER_T;
drop table EDO_DOSSIER_TYPE_T;
drop table EDO_ITEM_T;
drop table EDO_ITEM_TYPE_T;
drop table EDO_VOTE_RECORD_T;
drop table EDO_AREA_OF_EXCELLENCE_T;

drop view EDO.EDO_CHECKLIST_V;
drop view EDO.EDO_ITEM_V;

drop sequence EDO_CANDIDATE_ID_S        ;
drop sequence EDO_CHECKLIST_ITEM_ID_S   ;
drop sequence EDO_CHECKLIST_SECTION_ID_S;
drop sequence EDO_CHECKLIST_ID_S        ;
drop sequence EDO_DOSSIER_ID_S          ;
drop sequence EDO_DOSSIER_TYPE_ID_S     ;
drop sequence EDO_ITEM_ID_S             ;
drop sequence EDO_ITEM_TYPE_ID_S        ;
drop sequence EDO_VOTE_RECORD_ID_S      ;
drop sequence EDO_SUPPLEMENTAL_TRACKING_ID_S;

--
----------------- create schema starts here
--
create sequence edo_CANDIDATE_ID_S        ;
create sequence edo_CHECKLIST_ITEM_ID_S   ;
create sequence edo_CHECKLIST_SECTION_ID_S;
create sequence edo_CHECKLIST_ID_S        ;
create sequence edo_DOSSIER_ID_S          ;
create sequence edo_ITEM_ID_S             ;
create sequence edo_VOTE_RECORD_ID_S      ;
create sequence edo_SUPPLEMENTAL_TRACKING_ID_S;

create sequence edo_ITEM_TYPE_ID_S start with 6;
create sequence edo_DOSSIER_TYPE_ID_S start with 5;


CREATE TABLE EDO.EDO_CANDIDATE_T
(
  CANDIDATE_ID           NUMBER(8),
  LAST_NAME              VARCHAR2(45 BYTE)      NOT NULL,
  FIRST_NAME             VARCHAR2(45 BYTE),
  CURRENT_RANK           VARCHAR2(45 BYTE),
  PRIMARY_DEPARTMENT_ID  VARCHAR2(12 BYTE)      NOT NULL,
  TNP_DEPARTMENT_ID      VARCHAR2(12 BYTE)      NOT NULL,
  CANDIDACY_YEAR         NUMBER(8)              NOT NULL,
  CANDIDACY_SCHOOL       VARCHAR2(12 BYTE)      NOT NULL,
  CANDIDACY_CAMPUS       CHAR(2 BYTE)           NOT NULL,
  USERNAME               VARCHAR2(16 BYTE),
  CURRENTDOSSIER         VARCHAR2(32 BYTE)
);


CREATE UNIQUE INDEX EDO.EDO_CANDIDATE_TP1 ON EDO.EDO_CANDIDATE_T
(CANDIDATE_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_CANDIDATE_TR" 
   before insert on "EDO_CANDIDATE_T" 
   for each row
begin  
   if inserting then 
      if :NEW."CANDIDATE_ID" is null then 
         select EDO_CANDIDATE_ID_S.nextval into :NEW."CANDIDATE_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_CANDIDATE_T ADD (
  CONSTRAINT EDO_CANDIDATE_TP1
  PRIMARY KEY
  (CANDIDATE_ID)
  USING INDEX EDO.EDO_CANDIDATE_TP1);

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
);


CREATE UNIQUE INDEX EDO.EDO_CHECKLIST_ITEM_TP1 ON EDO.EDO_CHECKLIST_ITEM_T
(CHECKLIST_ITEM_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_CHECKLIST_ITEM_TR" 
   before insert on "EDO_CHECKLIST_ITEM_T" 
   for each row
begin  
   if inserting then 
      if :NEW."CHECKLIST_ITEM_ID" is null then 
         select EDO_CHECKLIST_ITEM_ID_S.nextval into :NEW."CHECKLIST_ITEM_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_CHECKLIST_ITEM_T ADD (
  CONSTRAINT EDO_CHECKLIST_ITEM_TP1
  PRIMARY KEY
  (CHECKLIST_ITEM_ID)
  USING INDEX EDO.EDO_CHECKLIST_ITEM_TP1);

CREATE TABLE EDO.EDO_CHECKLIST_SECTION_T
(
  CHECKLIST_SECTION_ID       NUMBER(8),
  CHECKLIST_ID               NUMBER(8)          NOT NULL,
  DESCRIPTION                CLOB,
  CREATE_DATE                DATE,
  CREATED_BY                 NUMBER(8),
  LAST_UPDATE_DATE           DATE,
  UPDATED_BY                 NUMBER(8),
  CHECKLIST_SECTION_NAME     VARCHAR2(24 BYTE),
  CHECKLIST_SECTION_ORDINAL  NUMBER(4)
);


CREATE UNIQUE INDEX EDO.EDO_CHECKLIST_SECTION_TP1 ON EDO.EDO_CHECKLIST_SECTION_T
(CHECKLIST_SECTION_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_CHECKLIST_SECTION_TR" 
   before insert on "EDO_CHECKLIST_SECTION_T" 
   for each row
begin  
   if inserting then 
      if :NEW."CHECKLIST_SECTION_ID" is null then 
         select EDO_CHECKLIST_SECTION_ID_S.nextval into :NEW."CHECKLIST_SECTION_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_CHECKLIST_SECTION_T ADD (
  CONSTRAINT EDO_CHECKLIST_SECTION_TP1
  PRIMARY KEY
  (CHECKLIST_SECTION_ID)
  USING INDEX EDO.EDO_CHECKLIST_SECTION_TP1);

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
);


CREATE UNIQUE INDEX EDO.EDO_CHECKLIST_TP1 ON EDO.EDO_CHECKLIST_T
(CHECKLIST_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_CHECKLIST_TR" 
   before insert on "EDO_CHECKLIST_T" 
   for each row
begin  
   if inserting then 
      if :NEW."CHECKLIST_ID" is null then 
         select EDO_CHECKLIST_ID_S.nextval into :NEW."CHECKLIST_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_CHECKLIST_T ADD (
  CONSTRAINT EDO_CHECKLIST_TP1
  PRIMARY KEY
  (CHECKLIST_ID)
  USING INDEX EDO.EDO_CHECKLIST_TP1);

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
  ROUTING_ID          NUMBER(8),
  CREATE_DATE         DATE,
  LAST_UPDATE_DATE    DATE,
  DOCUMENT_ID         VARCHAR2(40 BYTE),
  DOSSIER_STATUS      VARCHAR2(12 BYTE),
  CANDIDATE_USERNAME  VARCHAR2(16 BYTE),
  CREATED_BY          VARCHAR2(16 BYTE),
  UPDATED_BY          VARCHAR2(16 BYTE)
);


CREATE UNIQUE INDEX EDO.EDO_DOSSIER_TP1 ON EDO.EDO_DOSSIER_T
(DOSSIER_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_DOSSIER_TR" 
   before insert on "EDO_DOSSIER_T" 
   for each row
begin  
   if inserting then 
      if :NEW."DOSSIER_ID" is null then 
         select EDO_DOSSIER_ID_S.nextval into :NEW."DOSSIER_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_DOSSIER_T ADD (
  CONSTRAINT EDO_DOSSIER_TP1
  PRIMARY KEY
  (DOSSIER_ID)
  USING INDEX EDO.EDO_DOSSIER_TP1);

CREATE TABLE EDO.EDO_DOSSIER_TYPE_T
(
  DOSSIER_TYPE_ID    NUMBER(8),
  DOSSIER_TYPE_NAME  VARCHAR2(45 BYTE),
  CREATE_DATE        DATE,
  CREATED_BY         NUMBER(8),
  LAST_UPDATE_DATE   DATE,
  UPDATED_BY         NUMBER(8),
  DOSSIER_TYPE_CODE  CHAR(2 BYTE)
);


CREATE UNIQUE INDEX EDO.EDO_DOSSIER_TYPE_TP1 ON EDO.EDO_DOSSIER_TYPE_T
(DOSSIER_TYPE_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_DOSSIER_TYPE_TR" 
   before insert on "EDO_DOSSIER_TYPE_T" 
   for each row
begin  
   if inserting then 
      if :NEW."DOSSIER_TYPE_ID" is null then 
         select EDO_DOSSIER_TYPE_ID_S.nextval into :NEW."DOSSIER_TYPE_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_DOSSIER_TYPE_T ADD (
  CONSTRAINT EDO_DOSSIER_TYPE_TP1
  PRIMARY KEY
  (DOSSIER_TYPE_ID)
  USING INDEX EDO.EDO_DOSSIER_TYPE_TP1);


CREATE TABLE EDO.EDO_ITEM_T
(
  ITEM_ID            NUMBER(8),
  UPLOADER_USERNAME  VARCHAR2(16 BYTE)          NOT NULL,
  ITEM_TYPE_ID       NUMBER(8)                  NOT NULL,
  CHECKLIST_ITEM_ID  NUMBER(8)                  NOT NULL,
  DOSSIER_ID         NUMBER(8)                  NOT NULL,
  UPLOAD_DATE        timestamp                  NOT NULL,
  FILE_NAME          VARCHAR2(1024 BYTE)        NOT NULL,
  FILE_LOCATION      VARCHAR2(1024 BYTE)        NOT NULL,
  NOTES              CLOB,
  ADDENDUM           NUMBER(1),
  CREATE_DATE        timestamp,
  CREATED_BY         VARCHAR2(16 BYTE),
  LAST_UPDATE_DATE   timestamp,
  UPDATED_BY         VARCHAR2(16 BYTE),
  LAYER_LEVEL        VARCHAR2(16 BYTE),
  CONTENT_TYPE       VARCHAR2(128 BYTE),
  ROW_INDEX          NUMBER(15,5)
);


CREATE UNIQUE INDEX EDO.EDO_ITEM_TP1 ON EDO.EDO_ITEM_T
(ITEM_ID);


CREATE OR REPLACE TRIGGER EDO."ITEM_ID_S_TR" 
   before insert on "EDO_ITEM_T" 
   for each row
begin  
   if inserting then 
      if :NEW."ITEM_ID" is null then 
         select EDO_ITEM_ID_S.nextval into :NEW."ITEM_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_ITEM_T ADD (
  CONSTRAINT EDO_ITEM_TP1
  PRIMARY KEY
  (ITEM_ID)
  USING INDEX EDO.EDO_ITEM_TP1);


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
);


CREATE UNIQUE INDEX EDO.EDO_ITEM_TYPE_TP1 ON EDO.EDO_ITEM_TYPE_T
(ITEM_TYPE_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_ITEM_TYPE_TR" 
   before insert on "EDO_ITEM_TYPE_T" 
   for each row
begin  
   if inserting then 
      if :NEW."ITEM_TYPE_ID" is null then 
         select EDO_ITEM_TYPE_ID_S.nextval into :NEW."ITEM_TYPE_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_ITEM_TYPE_T ADD (
  CONSTRAINT EDO_ITEM_TYPE_TP1
  PRIMARY KEY
  (ITEM_TYPE_ID)
  USING INDEX EDO.EDO_ITEM_TYPE_TP1);

CREATE TABLE EDO.EDO_VOTE_RECORD_T
(
	VOTE_RECORD_ID         	NUMBER(8) NOT NULL,
	DOSSIER_ID             	NUMBER(8) NOT NULL,
	REVIEW_LAYER_ID        	NUMBER(8) NOT NULL,
	VOTE_TYPE              	VARCHAR2(16) NOT NULL,
	YES_COUNT_TENURE       	NUMBER(8) NULL,
	NO_COUNT_TENURE        	NUMBER(8) NULL,
	ABSENT_COUNT_TENURE    	NUMBER(8) NULL,
	ABSTAIN_COUNT_TENURE   	NUMBER(8) NULL,
	AOE_CODE               	CHAR(1) NULL,
	CREATED_AT             	TIMESTAMP(6) NULL,
	CREATED_BY             	VARCHAR2(25) NULL,
	UPDATED_AT             	TIMESTAMP(6) NULL,
	UPDATED_BY             	VARCHAR2(25) NULL,
	VOTE_ROUND             	NUMBER(8) NULL,
	VOTE_CATEGORY          	VARCHAR2(16) NULL,
	YES_COUNT_PROMOTION    	NUMBER(8) NULL,
	NO_COUNT_PROMOTION     	NUMBER(8) NULL,
	ABSENT_COUNT_PROMOTION 	NUMBER(8) NULL,
	ABSTAIN_COUNT_PROMOTION	NUMBER(8) NULL,
	VOTE_SUBROUND          	NUMBER(15,5) DEFAULT 0 NULL
);


CREATE UNIQUE INDEX EDO.EDO_VOTE_RECORD_TP1 ON EDO.EDO_VOTE_RECORD_T
(VOTE_RECORD_ID);


CREATE OR REPLACE TRIGGER EDO."EDO_VOTE_RECORD_TR" 
   before insert on "EDO_VOTE_RECORD_T" 
   for each row
begin  
   if inserting then 
      if :NEW."VOTE_RECORD_ID" is null then 
         select EDO_VOTE_RECORD_ID_S.nextval into :NEW."VOTE_RECORD_ID" from dual; 
      end if; 
   end if; 
end;
/


ALTER TABLE EDO.EDO_VOTE_RECORD_T ADD (
  CONSTRAINT EDO_VOTE_RECORD_TP1
  PRIMARY KEY
  (VOTE_RECORD_ID)
  USING INDEX EDO.EDO_VOTE_RECORD_TP1);



create table edo.edo_area_of_excellence_t
  (
    area_of_excellence_type_id   number(8,0),
    option_key varchar2(3 char),
    option_value varchar2(50 byte),   
    create_date date,
    created_by varchar2(50 byte),
    last_update_date date,
    updated_by  varchar2(50 byte),
    constraint area_of_excellence_type_p1 primary key (area_of_excellence_type_id)
    );

CREATE TABLE "EDO"."EDO_SUPPLEMENTAL_TRACKING_T"
   (	"DOSSIER_ID" NUMBER(8,0) NOT NULL ENABLE,
	"REVIEW_LEVEL" NUMBER(8,0) NOT NULL ENABLE,
	"LAST_UPDATED_DATE" DATE DEFAULT SYSDATE NOT NULL ENABLE,
	"UPDATED_BY" VARCHAR2(16 BYTE),
	"ACKNOWLEDGED" CHAR(1 BYTE) NOT NULL ENABLE,
	"SUPPLEMENTAL_TRACKING_ID" NUMBER(*,0) NOT NULL ENABLE,
	 CONSTRAINT "EDO_SUPPLEMENTAL_TRACKING_PK" PRIMARY KEY ("SUPPLEMENTAL_TRACKING_ID")
	);

CREATE OR REPLACE TRIGGER "EDO"."EDO_SUPPLEMENTAL_TRACKING_TR"
   before insert on "EDO"."EDO_SUPPLEMENTAL_TRACKING_T"
   for each row
begin
   if inserting then
      if :NEW."SUPPLEMENTAL_TRACKING_ID" is null then
         select EDO_SUPPLEMENTAL_TRACKING_ID_S.nextval into :NEW."SUPPLEMENTAL_TRACKING_ID" from dual;
      end if;
   end if;
end;

/
ALTER TRIGGER "EDO"."EDO_SUPPLEMENTAL_TRACKING_TR" ENABLE;

------------------------------------------------------------------- Views

CREATE OR REPLACE VIEW EDO.EDO_CHECKLIST_V 
( checklist_desc, department_id, school_id, campus_code, dossier_type_code, effective_date, checklist_section_name, checklist_section_id, section_desc
, checklist_section_ordinal, checklist_item_id, checklist_item_name, item_desc, required, checklist_item_section_ordinal) AS
SELECT l.description      AS checklist_desc
     , l.department_id
     , l.school_id
     , l.campus_code
     , l.dossier_type_code
     , l.effective_date
     , s.checklist_section_name
     , s.checklist_section_id
     , s.description AS section_desc
     , s.checklist_section_ordinal
     , i.checklist_item_id
     , i.checklist_item_name
     , i.description AS item_desc
     , i.required
     , i.checklist_item_section_ordinal
FROM edo_checklist_t l
  LEFT OUTER JOIN edo_checklist_section_t s
       ON s.checklist_id = l.checklist_id
  LEFT OUTER JOIN edo_checklist_item_t i
       ON s.checklist_section_id = i.checklist_section_id;
       
CREATE OR REPLACE VIEW EDO.EDO_ITEM_V 
( ITEM_ID, UPLOADER_USERNAME, DOSSIER_ID, UPLOAD_DATE, FILE_NAME, FILE_LOCATION, NOTES, ADDENDUM, LAYER_LEVEL, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE
, ITEM_TYPE_ID, UPDATED_BY, CONTENT_TYPE, ROW_INDEX, REVIEW_LAYER_DEF_ID, CHECKLIST_SECTION_ID, CHECKLIST_ITEM_ID, ITEM_TYPE_NAME, DESCRIPTION, INSTRUCTIONS
, EXT_AVAILABILITY, CANDIDATE_USERNAME, REVIEW_LAYER_DESCRIPTION, REVIEW_LEVEL )  AS
SELECT i.item_id                                                                                                                                                                                                                                                                                                                                                                                                                  
     , i.uploader_username                                                                                                                                                                                                                                                                                                                                                                                                             
     , i.dossier_id                                                                                                                                                                                                                                                                                                                                                                                                                  
     , i.upload_date                                                                                                                                                                                                                                                                                                                                                                                                                 
     , i.file_name                                                                                                                                                                                                                                                                                                                                                                                                                   
     , i.file_location                                                                                                                                                                                                                                                                                                                                                                                                               
     , i.notes                                                                                                                                                                                                                                                                                                                                                                                                                       
     , i.addendum                                                                                                                                                                                                                                                                                                                                                                                                                    
     , i.layer_level                                                                                                                                                                                                                                                                                                                                                                                                                 
     , i.create_date                                                                                                                                                                                                                                                                                                                                                                                                                 
     , i.created_by                                                                                                                                                                                                                                                                                                                                                                                                                  
     , i.last_update_date                                                                                                                                                                                                                                                                                                                                                                                                            
     , i.item_type_id                                                                                                                                                                                                                                                                                                                                                                                                                
     , i.updated_by                                                                                                                                                                                                                                                                                                                                                                                                                  
     , i.content_type                                                                                                                                                                                                                                                                                                                                                                                                                
     , i.row_index                                                                                                                                                                                                                                                                                                                                                                                                                   
     , i.review_layer_def_id
     , ci.checklist_section_id
     , ci.checklist_item_id                                                                                                                                                                                                                                                                                                                                                                                                          
     , it.item_type_name                                                                                                                                                                                                                                                                                                                                                                                                             
     , it.description                                                                                                                                                                                                                                                                                                                                                                                                                
     , it.instructions                                                                                                                                                                                                                                                                                                                                                                                                               
     , it.ext_availability                                                                                                                                                                                                                                                                                                                                                                                                           
     , do.candidate_username                                                                                                                                                                                                                                                                                                                                                                                                          
     , rl.description as review_layer_description
     , rl.review_level
FROM edo_item_t i
  INNER JOIN edo_checklist_item_t ci                                                                                                                                                                                                                                                                                                                                                                                               
        ON i.checklist_item_id = ci.checklist_item_id                                                                                                                                                                                                                                                                                                                                                                                    
  INNER JOIN edo_item_type_t it                                                                                                                                                                                                                                                                                                                                                                                                    
        ON i.item_type_id = it.item_type_id                                                                                                                                                                                                                                                                                                                                                                                              
  INNER JOIN edo_dossier_t DO                                                                                                                                                                                                                                                                                                                                                                                                      
        ON i.dossier_id = do.dossier_id
  LEFT OUTER JOIN edo_review_layer_def_t rl
        ON i.review_layer_def_id = rl.review_layer_def_id;
