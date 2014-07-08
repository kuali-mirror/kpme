--
-- --------------------------------------------------------------------------------- GENERAL
--

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2
   , 'Expectations for Tenure/Promotion'
   , 1, sysdate, 1, sysdate, 1
   , 'Department and School Criteria', 1);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2, 'On CV, indicate peer reviewed publications; list separately publications to be considered research, teaching or service; for promotion to full, indicate work done since appointment as associate professor.'
   , 1, sysdate, 1, sysdate, 1
   , 'Candidate''s Curriculum Vitae', 2);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2
   , 'Separate statements on Research/Creative Activity, Teaching, and Service/Engagement.'
   , 1, sysdate, 1, sysdate, 1
   , 'Candidate''s Statement', 3);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2
   , 'Please indicate those who did or did not respond and reason for non-response.'
   , 1, sysdate, 1, sysdate, 1
   , 'List of Referees Selected ', 4);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2
   , 'Include brief summary of credentials and relationship(s) with candidate.'
   , 1, sysdate, 1, sysdate, 1
   , 'Department (School) List of Prospective Referees', 5);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 2
   , 'Include brief summary of credentials and relationship(s) with candidate.'
   , 1, sysdate, 1, sysdate, 1
   , 'Candidate''s List of Prospective Referees', 6);


--
-- --------------------------------------------------------------------------------- RESEARCH/CREATIVE
--

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Copies of Publications and/or Evidence of Creative Work', 1);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Reviews of Candidate’s Books, Creative Performances and Exhibitions', 2);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , 'For each grant, include cover sheet or abstract, funding source, dollar amount requested and awarded, and role (e.g., PI, co-PI etc.)'
   , 1, sysdate, 1, sysdate, 1
   , 'List of Grants Applied for/Received', 3);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Copies of Manuscripts or Creative Works in Progress', 4);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence for the Impact/Influence of Publications or Creative Works', 5);


Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence for the Stature/Visibility of Journals, Presses or Artistic Venues', 6);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Awards and Honors for Research/Creative Activity', 7);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 1
   , 'Includes letters from collaborators.'
   , 1, sysdate, 1, sysdate, 1
   , 'Candidate''s Contributions to Collaborative Projects', 8);


--
-- --------------------------------------------------------------------------------- TEACHING
--
Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , 'List chronologically by semester, number of students enrolled, and grade distribution'
   , 1, sysdate, 1, sysdate, 1
   , 'List of Courses Taught', 1);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , 'Syllabi, exercises, assignments, exams, student work'
   , 1, sysdate, 1, sysdate, 1
   , 'Sample of Course Materials', 2);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3, 'List PhD and Masters, role (e.g., chair, committee member), and include dissertation titles.'
   , 1, sysdate, 1, sysdate, 1
   , 'Graduate Training'
   , 3);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Student Awards, Honors, Collaborative Publications, Achievements', 4);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Undergraduate Research Experiences and Mentoring', 5);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , 'Include a summary of quantitative data and all qualitative responses.'
   , 1, sysdate, 1, sysdate, 1
   , 'Student Course Evaluations', 6);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Solicited and Unsolicited Letters from Former Students', 7);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , 'Document assessment strategies, supporting data, and any pedagogical adjustments made.'
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence of Learning Outcomes', 8);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 3
   , 'Solicited by the chair or dean, these include letters from peer observers and or teaching mentors.'
   , 1, sysdate, 1, sysdate, 1
   , 'Peer Evaluations', 9);

--
-- --------------------------------------------------------------------------------- SERVICE/ENGAGEMENT
--

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 4
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence of Service to the University, School and Department', 1);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 4
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence of Service to the Profession', 2);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 4
   , ''
   , 1, sysdate, 1, sysdate, 1
   , 'Evidence of Engagement with Non-Academic Communities and Agencies', 3);

Insert into EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID, CHECKLIST_SECTION_ID, DESCRIPTION, REQUIRED, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_ITEM_NAME, CHECKLIST_ITEM_SECTION_ORDINAL)
Values
   ( EDO_CHECKLIST_ITEM_ID_S.nextval, 5
   , 'Includes any supporting material added to the dossier after submiiting for consideration'
   , 1, sysdate, 1, sysdate, 1
   , 'Supplemental Supporting Items', 1);

--
-- ----------------------------------------------------- ADMINISTRATIVE FOLDERS
--
INSERT INTO EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID,CHECKLIST_SECTION_ID,DESCRIPTION,REQUIRED,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,CHECKLIST_ITEM_NAME,CHECKLIST_ITEM_SECTION_ORDINAL)
VALUES
(10900,	0, 'Dossier Level',	0, '3/18/2014 12:00:00 PM',	1, '3/18/2014 12:00:00 PM',	1, 'Vote Records', 1);

INSERT INTO EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID,CHECKLIST_SECTION_ID,DESCRIPTION,REQUIRED,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,CHECKLIST_ITEM_NAME,CHECKLIST_ITEM_SECTION_ORDINAL)
VALUES
(10901,	0, 'Dossier Level',	0, '3/18/2014 12:00:00 PM',	1, '3/18/2014 12:00:00 PM',	1, 'Review Letters', 2);

INSERT INTO EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID,CHECKLIST_SECTION_ID,DESCRIPTION,REQUIRED,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,CHECKLIST_ITEM_NAME,CHECKLIST_ITEM_SECTION_ORDINAL)
VALUES
(10902,	0, 'Dossier Level',	0, '3/18/2014 12:00:00 PM',	1, '3/18/2014 12:00:00 PM',	1, 'External Letters', 3);

INSERT INTO EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID,CHECKLIST_SECTION_ID,DESCRIPTION,REQUIRED,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,CHECKLIST_ITEM_NAME,CHECKLIST_ITEM_SECTION_ORDINAL)
VALUES
(10903,	0, 'Dossier Level',	0, '3/18/2014 12:00:00 PM',	1, '3/18/2014 12:00:00 PM',	1, 'Solicited Letters', 5);

INSERT INTO EDO_CHECKLIST_ITEM_T (CHECKLIST_ITEM_ID,CHECKLIST_SECTION_ID,DESCRIPTION,REQUIRED,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,CHECKLIST_ITEM_NAME,CHECKLIST_ITEM_SECTION_ORDINAL)
VALUES
(10904,	0, 'Dossier Level',	0, '3/18/2014 12:00:00 PM',	1, '3/18/2014 12:00:00 PM',	1, 'Administrative Documents', 5);

COMMIT;


Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (1, 1, 'Research related items', sysdate, 1,
    sysdate, 1, 'Research', 3);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (2, 1, 'General items', sysdate, 1,
    sysdate, 1, 'General', 1);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (3, 1, 'Teaching related items', sysdate, 1,
    sysdate, 1, 'Teaching', 2);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (4, 1, 'Service related items', sysdate, 1,
    sysdate, 1, 'Service', 4);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (5, 1, 'Supplemental items', sysdate, 1,
    sysdate, 1, 'Supplemental Items', 5);

INSERT INTO "EDO_CHECKLIST_SECTION_T"("CHECKLIST_SECTION_ID", "CHECKLIST_ID", "DESCRIPTION", "CREATE_DATE", "CREATED_BY", "LAST_UPDATE_DATE", "UPDATED_BY", "CHECKLIST_SECTION_NAME", "CHECKLIST_SECTION_ORDINAL")
  VALUES(21, 1, 'Reconsideration items', TO_DATE('2014-01-22 08:00:00','YYYY-MM-DD HH24:MI:SS'), 1, TO_DATE('2014-01-22 08:00:00','YYYY-MM-DD HH24:MI:SS'), 1, 'Reconsideration Items', 6)                                                           ;

INSERT INTO "EDO_CHECKLIST_ITEM_T"("CHECKLIST_SECTION_ID", "DESCRIPTION", "REQUIRED", "CREATE_DATE", "CREATED_BY", "LAST_UPDATE_DATE", "UPDATED_BY", "CHECKLIST_ITEM_NAME", "CHECKLIST_ITEM_SECTION_ORDINAL")
  VALUES(21, 'Include any items in support of the reconsideration of the dossier', 0, TO_DATE('2014-01-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 1, TO_DATE('1014-01-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 1, 'Reconsideration Supporting Items', 1) ;

COMMIT;

Insert into EDO_CHECKLIST_T
   (CHECKLIST_ID, DOSSIER_TYPE_CODE, DEPARTMENT_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, SCHOOL_ID, CAMPUS_CODE, EFFECTIVE_DATE)
 Values
   (1, 'XX', 'ALL', 'Standard PnT checklist', sysdate,
    1, sysdate, 1, 'ALL', 'IU',
    sysdate);
COMMIT;

----------------------------------------  EDO_DOSSIER_TYPE_T

Insert into EDO_DOSSIER_TYPE_T
   (DOSSIER_TYPE_ID, DOSSIER_TYPE_NAME, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, DOSSIER_TYPE_CODE)
 Values
   (3, 'Tenure+Promotion', sysdate, 1, sysdate,
    1, 'TP');
Insert into EDO_DOSSIER_TYPE_T
   (DOSSIER_TYPE_ID, DOSSIER_TYPE_NAME, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, DOSSIER_TYPE_CODE)
 Values
   (4, 'Tenure+Promotion Dry Run', sysdate, 1, sysdate,
    1, 'DR');
Insert into EDO_DOSSIER_TYPE_T
   (DOSSIER_TYPE_ID, DOSSIER_TYPE_NAME, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, DOSSIER_TYPE_CODE)
 Values
   (1, 'Tenure', sysdate, 1, sysdate,
    1, 'TN');
Insert into EDO_DOSSIER_TYPE_T
   (DOSSIER_TYPE_ID, DOSSIER_TYPE_NAME, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, DOSSIER_TYPE_CODE)
 Values
   (2, 'Promotion', sysdate, 1, sysdate,
    1, 'PR');
COMMIT;

Insert into EDO.EDO_DOSSIER_TYPE_T (DOSSIER_TYPE_ID,DOSSIER_TYPE_NAME,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,DOSSIER_TYPE_CODE,DOC_TYPE_NAME)
values (7,'Tenure Promotion Supplemental',sysdate,1,sysdate,1,'SS','TenureSupplementalProcessDocument');

Insert into EDO.EDO_DOSSIER_TYPE_T (DOSSIER_TYPE_ID,DOSSIER_TYPE_NAME,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,DOSSIER_TYPE_CODE,DOC_TYPE_NAME)
values (5,'Tenure Supplemental',sysdate,1,sysdate,1,'TS','TenureSupplementalProcessDocument');

Insert into EDO.EDO_DOSSIER_TYPE_T (DOSSIER_TYPE_ID,DOSSIER_TYPE_NAME,CREATE_DATE,CREATED_BY,LAST_UPDATE_DATE,UPDATED_BY,DOSSIER_TYPE_CODE,DOC_TYPE_NAME)
values (6,'Promotion Supplemental',sysdate,1,sysdate,1,'PS','PromotionSupplementalProcessDocument');

commit;

----------------------------------------  EDO_ITEM_TYPE_T

Insert into EDO_ITEM_TYPE_T
   (ITEM_TYPE_ID, ITEM_TYPE_NAME, DESCRIPTION, EXT_AVAILABILITY, CREATE_DATE, LAST_UPDATE_DATE)
 Values
   (1, 'Supporting Document', 'Documents submitted in support of the faculty review', 0,
    sysdate, TO_DATE('01/11/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into EDO_ITEM_TYPE_T
   (ITEM_TYPE_ID, ITEM_TYPE_NAME, DESCRIPTION, EXT_AVAILABILITY, CREATE_DATE, LAST_UPDATE_DATE)
 Values
   (2, 'External Supporting Document', 'Document submitted in support of the faculty review from an external source', 0,
    sysdate, sysdate);
Insert into EDO_ITEM_TYPE_T
   (ITEM_TYPE_ID, ITEM_TYPE_NAME, DESCRIPTION, EXT_AVAILABILITY, CREATE_DATE, LAST_UPDATE_DATE)
 Values
   (3, 'Review Letter', 'Letter submitted by reviewer in response to faculty review', 0,
    sysdate, sysdate);
Insert into EDO_ITEM_TYPE_T
   (ITEM_TYPE_ID, ITEM_TYPE_NAME, DESCRIPTION, EXT_AVAILABILITY, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY)
 Values
   (4, 'Appeal', 'Document submitted by faculty member in response to committee review.', 0,
    sysdate, 1, sysdate, 1);
Insert into EDO_ITEM_TYPE_T
   (ITEM_TYPE_ID, ITEM_TYPE_NAME, DESCRIPTION, EXT_AVAILABILITY, CREATE_DATE, LAST_UPDATE_DATE)
 Values
   (5, 'Addendumxx', 'Document submitted by faculty in support of review, after dossier has been submitted for consideration.', 0,
    sysdate, sysdate);
COMMIT;


----------------------------------------------------------------- EDO_AREA_OF_EXCELLENCE_T

Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 1, 'R', 'Research/Creative Activity', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 2, 'T', 'Teaching', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 3, 'S', 'Service Engagement', sysdate, 'EDO', sysdate, 'EDO' );
Insert into EDO_AREA_OF_EXCELLENCE_T( area_of_excellence_type_id, option_key, option_value, create_date, created_by, last_update_date, updated_by )
Values ( 4, 'B', 'Balanced Case', sysdate, 'EDO', sysdate, 'EDO' );

COMMIT;

-------------------------------------------------- EDO_SUPP_REVIEW_LAYER_DEF_T

insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(1,2,'supplDeptLevelAck','Y');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(2,2,'supplDeptLevel','N');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(3,3,'supplSchoolLevelAck','Y');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(4,3,'supplSchoolLevel','N');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(5,4,'supplDeanAck','Y');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(6,4,'supplDean','N');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(7,5,'supplCampusLevelAck','Y');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(8,5,'supplCampusLevel','N');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(9,6,'supplViceProvostAck','Y');
insert into EDO_SUPP_REVIEW_LAYER_DEF_T(SUPP_REVIEW_LAYER_DEF_ID,REVIEW_LAYER_DEF_ID,SUPPLEMENTAL_NODE_NAME,ACKNOWLEDGE_FLAG) values(10,6,'supplViceProvost','N');


-------------------------------------------------- EDO_REVIEW_LAYER_DEF_T

Insert into EDO.EDO_REVIEW_LAYER_DEF_T (REVIEW_LAYER_DEF_ID,NODE_NAME,VOTE_TYPE,REVIEW_LETTER,CREATE_DATE,CREATED_BY,LAST_UPDATED_DATE,UPDATED_BY,OBJ_ID,VER_NBR,ROUTE_LEVEL,REVIEW_LEVEL,SUPP_NOTIFY,SUPPLEMENTAL_NODE_NAME)
values (12,'CandidateFYI','None','N',to_date('11-SEP-13','DD-MON-RR'),'nrmalae',to_date('11-SEP-13','DD-MON-RR'),'nrmalae',null,null,1,null,null,null);


