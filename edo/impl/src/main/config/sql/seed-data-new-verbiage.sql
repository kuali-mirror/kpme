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
   
COMMIT;

---------
--------------------------------- EDO_CHECKLIST_SECTION_T
---------

Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (edo_checklist_section_id_s.nextval, 1, 'Research related items', sysdate, 1, 
    sysdate, 1, 'Research', 3);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (edo_checklist_section_id_s.nextval, 1, 'General items', sysdate, 1, 
    sysdate, 1, 'General', 1);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (edo_checklist_section_id_s.nextval, 1, 'Teaching related items', sysdate, 1, 
    sysdate, 1, 'Teaching', 2);
Insert into EDO_CHECKLIST_SECTION_T
   (CHECKLIST_SECTION_ID, CHECKLIST_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, CHECKLIST_SECTION_NAME, CHECKLIST_SECTION_ORDINAL)
 Values
   (edo_checklist_section_id_s.nextval, 1, 'Service related items', sysdate, 1, 
    sysdate, 1, 'Service', 4);
COMMIT;


Insert into EDO_CHECKLIST_T
   (CHECKLIST_ID, DOSSIER_TYPE_CODE, DEPARTMENT_ID, DESCRIPTION, CREATE_DATE, CREATED_BY, LAST_UPDATE_DATE, UPDATED_BY, SCHOOL_ID, CAMPUS_CODE, EFFECTIVE_DATE)
 Values
   (edo_checklist_id_s.nextval, 'XX', 'ALL', 'Standard PnT checklist', sysdate, 
    1, sysdate, 1, 'ALL', 'IU', 
    sysdate);
COMMIT;

---------
--------------------------------- EDO_DOSSIER_T
---------

----------------- Test dossiers

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'mcwhitak', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'nrmalae', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'aknshah', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'dpirkola', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'smccammo', 'nrmalae', 'nrmalae');
    
Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, AOE_CODE, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'B', 'BL', 
    'SPEA', 'SPEA', 'Lecturer', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    123456, sysdate, sysdate, '000234', 'OPEN', 'bradleyt', 'bradleyt', 'bradleyt');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, AOE_CODE, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'B', 'BL', 
    'UITS', 'UITS', 'Lecturer', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    234567, sysdate, sysdate, '000345', 'OPEN', 
    'rpiercy', 'bradleyt', 'bradleyt');
    
Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, AOE_CODE, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'R', 'BL', 
    'UITS', 'UITS', 'Associate Professor', 'Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    345678, sysdate, sysdate, '000456', 'OPEN', 
    'rpembry', 'bradleyt', 'bradleyt');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, AOE_CODE, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'B', 'BL', 
    'SPEA', 'SPEA', 'Lecturer', 'Associate Professor', TO_DATE('12/31/2012 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    456789, sysdate, sysdate, '000567', 'CLOSED', 
    'bradleyt', 'bradleyt', 'bradleyt');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, AOE_CODE, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'R', 'BL', 
    'UITS', 'UITS', 'Lecturer', 'Associate Professor', TO_DATE('12/31/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    123654, sysdate, sysdate, '000123', 'CLOSED', 
    'rpembry', 'bradleyt', 'bradleyt');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, ROUTING_ID, CREATE_DATE, LAST_UPDATE_DATE, DOCUMENT_ID, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval , 1, 1, 'BL', 
    'UITS', 'UITS', 'Lecturer', 'Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    123456, sysdate, sysdate, '000234', 'OPEN', 
    'lee55', 'lee55', 'lee55');

----------------- Real dossiers

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-FINS', 'BL-ARSC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'rickettr', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-THTR', 'BL-ARSC', 'Associate Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'lpisano', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
   (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-FINS', 'BL-ARSC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'onakagaw', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-THTR', 'BL-ARSC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'pbrunner', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-THTR', 'BL-ARSC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'tlabolt', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-THTR', 'BL-ARSC', 'Associate Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'lpisano', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'ahackenb', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'amaltese', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'hschertz', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-ELPS', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'amcc', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-ISTE', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'choyonj', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-ISTE', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'rkhaynes', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-LGED', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'dadomat', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 1, 1, 'BL', 
    'BL-LGED', 'BL-EDUC', 'Assistant Professor', 'Associate Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'blsamuel', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'gabuck', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'egalindo', 'nrmalae', 'nrmalae');

Insert into EDO_DOSSIER_T
  (DOSSIER_ID, DOSSIER_TYPE_ID, CHECKLIST_ID, CAMPUS_CODE, DEPARTMENT_ID, SCHOOL_ID, CURRENT_RANK, RANK_SOUGHT, DUE_DATE, CREATE_DATE, LAST_UPDATE_DATE, DOSSIER_STATUS, CANDIDATE_USERNAME, CREATED_BY, UPDATED_BY)
 Values
  ( edo_dossier_id_s.nextval, 2, 1, 'BL', 
    'BL-CRIN', 'BL-EDUC', 'Associate Professor', 'Full Professor', TO_DATE('12/31/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    sysdate, sysdate, 'OPEN', 'gbutera', 'nrmalae', 'nrmalae');

COMMIT;

---------
----------------------------------------  EDO_DOSSIER_TYPE_T
---------


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
COMMIT;

---------
----------------------------------------  EDO_ITEM_TYPE_T
---------

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

-----------
------------------------------- EDO_AREA_OF_EXCELLENCE
-----------

insert into EDO_AREA_OF_EXCELLENCE_T(area_of_excellence_type_id,option_key,option_value,create_date,created_by,last_update_date,updated_by)
values (1,'R','Research/Creative Activity',sysdate,'nrmalae',sysdate,'nrmalae');

insert into EDO_AREA_OF_EXCELLENCE_T(area_of_excellence_type_id,option_key,option_value,create_date,created_by,last_update_date,updated_by)
values (2,'T','Teaching',sysdate,'nrmalae',sysdate,'nrmalae');

insert into EDO_AREA_OF_EXCELLENCE_T(area_of_excellence_type_id,option_key,option_value,create_date,created_by,last_update_date,updated_by)
values (3,'S','Service Engagement',sysdate,'nrmalae',sysdate,'nrmalae');
                                  
insert into EDO_AREA_OF_EXCELLENCE_T(area_of_excellence_type_id,option_key,option_value,create_date,created_by,last_update_date,updated_by)
values (4,'B','Balanced Case',sysdate,'nrmalae',sysdate,'nrmalae');

COMMIT;

-----------
------------------------------- EDO_CANDIDATE_T
-----------

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Ricketts','Rowland','Assistant Professor','BL-FINS','BL-FINS',2013,'BL-ARSC','BL','rickettr');
                                	
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Nakagawa','James','Assistant Professor','BL-FINS','BL-FINS',2013,'BL-ARSC','BL','onakagaw');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values  
                                (edo_candidate_id_s.nextval,'Brunner','Paul','Associate Professor','BL-THTR','BL-THTR',2013,'BL-ARSC','BL','pbrunner');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'LaBolt','Terry','Assistant Professor','BL-THTR','BL-THTR',2013,'BL-ARSC','BL','tlabolt');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Pisano','Linda','Assistant Professor','BL-THTR','BL-THTR',2013,'BL-ARSC','BL','lpisano');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Hackenberg','Amy','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','ahackenb');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Maltese','Adam','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','amaltese');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Schertz','Hannah','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','hschertz');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'McCormick','Alexander','Associate Professor','BL-ELPS','BL-ELPS',2013,'BL-EDUC','BL','amcc');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Cho','Yonjoo','Associate Professor','BL-ISTE','BL-ISTE',2013,'BL-EDUC','BL','choyonj');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Haynes','Ray','Associate Professor','BL-ISTE','BL-ISTE',2013,'BL-EDUC','BL','rkhaynes');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Adomat','Donna','Associate Professor','BL-LGED','BL-LGED',2013,'BL-EDUC','BL','dadomat');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Samuelson','Beth','Associate Professor','BL-LGED','BL-LGED',2013,'BL-EDUC','BL','blsamuel');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Buck','Gayle','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','gabuck');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Galindo','Enrique','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','egalindo');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Butera','Gretchen','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','gbutera');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Malae','Naga','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','nrmalae');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Shah','Akash','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','aknshah');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Pirkola','Derek','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','dpirkola');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Whitakar','Maria','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','mcwhitak');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Susan','McCammon','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','smccammo');
                                
Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Bradley','Tribble','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','bradleyt');

Insert into EDO.EDO_CANDIDATE_T (candidate_id,last_name,first_name,current_rank,primary_department_id,tnp_department_id,candidacy_year,candidacy_school,candidacy_campus,username) values 
                                (edo_candidate_id_s.nextval,'Lee','Kenneth','Associate Professor','BL-CRIN','BL-CRIN',2013,'BL-EDUC','BL','lee55');
commit;
