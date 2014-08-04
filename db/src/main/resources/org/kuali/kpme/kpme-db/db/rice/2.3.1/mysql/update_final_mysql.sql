--
-- Copyright 2005-2013 The Kuali Foundation
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

--
-- KULRICE-9288  - Column 'SESN_ID' cannot be null causes issues
--

DELETE FROM KRIM_PERM_ATTR_DATA_T
WHERE perm_id =
      (
        SELECT
          DISTINCT a.PERM_ID
        FROM KRIM_PERM_T a, KRIM_PERM_TMPL_T b
        WHERE a.PERM_TMPL_ID = b.PERM_TMPL_ID AND b.NMSPC_CD = 'KR-NS' AND b.NM = 'Edit Document' AND a.NMSPC_CD = 'KUALI'
              AND a.NM = 'Edit Kuali ENROUTE Document Node Name PreRoute'
      )
;

DELETE FROM KRIM_ROLE_PERM_T
WHERE PERM_ID =
      (
        SELECT
          DISTINCT a.PERM_ID
        FROM KRIM_PERM_T a, KRIM_PERM_TMPL_T b
        WHERE a.PERM_TMPL_ID = b.PERM_TMPL_ID AND b.NMSPC_CD = 'KR-NS' AND b.NM = 'Edit Document' AND a.NMSPC_CD = 'KUALI'
              AND a.NM = 'Edit Kuali ENROUTE Document Node Name PreRoute'
      )
;

DELETE FROM KRIM_PERM_T
WHERE NMSPC_CD = 'KUALI' AND NM = 'Edit Kuali ENROUTE Document Node Name PreRoute' AND PERM_TMPL_ID =
                                                                                       (
                                                                                         SELECT
                                                                                           PERM_TMPL_ID
                                                                                         FROM KRIM_PERM_TMPL_T
                                                                                         WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
                                                                                       )
;

INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (
    'KR1001', uuid(), 1,
    (
      SELECT
        PERM_TMPL_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    'KUALI', 'Edit Kuali ENROUTE Document Route Status Code I',
    'Allows users to edit Kuali documents that are in INITIATED status.', 'Y'
  )
;

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (
    'KR1001', uuid(), 1, 'KR1001',
    (
      SELECT
        KIM_TYP_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    (
      SELECT
        KIM_ATTR_DEFN_ID
      FROM KRIM_ATTR_DEFN_T
      WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'
    ),
    'KualiDocument'
  )
;

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (
    'KR1002', uuid(), 1, 'KR1001',
    (
      SELECT
        KIM_TYP_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    (
      SELECT
        KIM_ATTR_DEFN_ID
      FROM KRIM_ATTR_DEFN_T
      WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'
    ),
    'I'
  )
;

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (
    'KR1002', uuid(), 1,
    (
      SELECT
        ROLE_ID
      FROM KRIM_ROLE_T
      WHERE ROLE_NM = 'Initiator' AND NMSPC_CD = 'KR-WKFLW'
    ),
    (
      SELECT
        PERM_ID
      FROM KRIM_PERM_T
      WHERE NMSPC_CD = 'KUALI' AND NM = 'Edit Kuali ENROUTE Document Route Status Code I'
    ),
    'Y'
  )
;

INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (
    'KR1002', uuid(), 1,
    (
      SELECT
        PERM_TMPL_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    'KUALI', 'Edit Kuali ENROUTE Document Route Status Code S',
    'Allows users to edit Kuali documents that are in SAVED status.', 'Y'
  )
;

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (
    'KR1003', uuid(), 1, 'KR1002',
    (
      SELECT
        KIM_TYP_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    (
      SELECT
        KIM_ATTR_DEFN_ID
      FROM KRIM_ATTR_DEFN_T
      WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'
    ), 'KualiDocument'
  )
;

INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (
    'KR1004', uuid(), 1,'KR1002',
    (
      SELECT
        KIM_TYP_ID
      FROM KRIM_PERM_TMPL_T
      WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'
    ),
    (
      SELECT
        KIM_ATTR_DEFN_ID
      FROM KRIM_ATTR_DEFN_T
      WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'
    ),
    'S'
  )
;

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (
    'KR1003', uuid(), 1,
    (
      SELECT
        ROLE_ID
      FROM KRIM_ROLE_T
      WHERE ROLE_NM = 'Initiator' AND NMSPC_CD = 'KR-WKFLW'
    ),
    (
      SELECT
        PERM_ID
      FROM KRIM_PERM_T
      WHERE NMSPC_CD = 'KUALI' AND NM = 'Edit Kuali ENROUTE Document Route Status Code S'
    ),
    'Y'
  )
;
ALTER TABLE KREN_NTFCTN_T ADD COLUMN DOC_TYP_NM VARCHAR(64)
;
INSERT INTO KRCR_CMPNT_T (NMSPC_CD, CMPNT_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
  VALUES ('KR-KRAD', 'Lookup', uuid(), 1, 'Lookup', 'Y')
;

INSERT INTO KRCR_PARM_T
  (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID)
  VALUES ('KR-KRAD', 'Lookup', 'RESULTS_LIMIT', uuid(), 1, 'CONFG', '200',
          'Maximum number of results returned in a look-up query.', 'A', 'KUALI')
;
UPDATE KRMS_ATTR_DEFN_T SET NMSPC_CD = 'KR-RULE' WHERE NM = 'peopleFlowName' AND NMSPC_CD = 'KR_RULE'
;
DELETE FROM KRIM_PERM_ATTR_DATA_T
WHERE ATTR_VAL = 'KRMS_TEST' AND PERM_ID =
  (
    SELECT
      PERM_ID
    FROM KRIM_PERM_T
    WHERE NM = 'Maintain KRMS Agenda' AND NMSPC_CD = 'KR-RULE-TEST'
  )
;
UPDATE KRIM_PERM_ATTR_DATA_T SET ATTR_VAL='RiceDocument'
WHERE ATTR_VAL = '*' AND PERM_ID =
  (
    SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Recall Document' AND nmspc_cd='KR-WKFLW'
  )
;
ALTER TABLE KRLC_CNTRY_T MODIFY COLUMN POSTAL_CNTRY_NM VARCHAR(255) DEFAULT NULL
;
