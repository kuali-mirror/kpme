CREATE SEQUENCE "EDO_SUBMIT_BUTTON_DISPLAY_ID_S"
	INCREMENT BY 1
	START WITH 1
	MAXVALUE 9999999999999999999999999999
	NOMINVALUE
	NOCYCLE
	NOCACHE
	ORDER
GO

ALTER TABLE "EDO_DOSSIER_T"
	ADD ( WORKFLOW_ID VARCHAR2(25) NULL,
	      SECONDARY_UNIT VARCHAR2(12) NULL )
GO

ALTER TABLE "EDO_REVIEW_LAYER_DEF_T"
	ADD ( WORKFLOW_QUALIFIER VARCHAR2(25) NULL )
GO

CREATE TABLE "EDO_SUBMIT_BUTTON_DISPLAY_T"  (
	"EDO_SUBMIT_BUTTON_DISPLAY_ID"	NUMBER(8) NOT NULL,
	"CAMPUS_CODE"                 	CHAR(2) NULL,
	"ACTIVE"                      	CHAR(1) NULL
	)
GO

ALTER TABLE "EDO_SUPP_REVIEW_LAYER_DEF_T"
	ADD ( WORKFLOW_ID VARCHAR2(25) DEFAULT 'DEFAULT' NOT NULL,
	      WORKFLOW_QUALIFIER VARCHAR2(25) NOT NULL )
GO

CREATE TABLE "EDO_WORKFLOW_DEF_T"  (
	"WORKFLOW_ID"         	VARCHAR2(25) NOT NULL,
	"WORKFLOW_NAME"       	VARCHAR2(25) NOT NULL,
	"WORKFLOW_DESCRIPTION"	VARCHAR2(512) NULL,
	"WORKFLOW_UPDATED_BY" 	VARCHAR2(25) NOT NULL,
	"WORKFLOW_UPDATED"    	DATE DEFAULT sysdate NOT NULL,
	CONSTRAINT "workflow_pk" PRIMARY KEY("WORKFLOW_ID")
	NOT DEFERRABLE
	 VALIDATE
)
GO

CREATE OR REPLACE VIEW "EDO_CANDIDATEDOSSIER_V" ( FIRST_NAME, "LAST_NAME", "USERNAME", "CANDIDACY_CAMPUS", "CANDIDACY_SCHOOL", "CANDIDACY_YEAR", "CANDIDATE_ID", "CURRENT_RANK", "PRIMARY_DEPARTMENT_ID", "DOSSIER_ID", "DOSSIER_STATUS", "RANK_SOUGHT", "DUE_DATE", "DOCUMENT_ID", "WORKFLOW_ID" )
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
  INNER JOIN edo_dossier_t d ON c.username = d.candidate_username
GO

CREATE UNIQUE INDEX "workflow_pk"
	ON "EDO_WORKFLOW_DEF_T"("WORKFLOW_ID")
GO

CREATE TRIGGER "EDO_SUBMIT_BUTTON_DISPLAY_T_TR"
   before insert on edo_submit_button_display_t
   for each row
begin
   if inserting then
      if :NEW.edo_submit_button_display_id is null then
         select EDO_SUBMIT_BUTTON_DISPLAY_ID_S.nextval into :NEW.edo_submit_button_display_id from dual;
      end if;
   end if;
end;
/
GO

CREATE TRIGGER "EDO_CANDIDATE_TR"
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
GO

