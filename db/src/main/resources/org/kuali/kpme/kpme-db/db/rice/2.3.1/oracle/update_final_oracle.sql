--
-- Copyright 2004-2014 The Kuali Foundation
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

DELETE FROM krim_perm_attr_data_t
WHERE perm_id =
  (
    SELECT
      DISTINCT a.perm_id
    FROM krim_perm_t a, krim_perm_tmpl_t b
    WHERE a.perm_tmpl_id = b.perm_tmpl_id AND b.nmspc_cd = 'KR-NS' AND b.nm = 'Edit Document' AND a.nmspc_cd = 'KUALI'
          AND a.nm = 'Edit Kuali ENROUTE Document Node Name PreRoute'
  )
/

DELETE FROM krim_role_perm_t
WHERE perm_id =
  (
    SELECT
      DISTINCT a.perm_id
    FROM krim_perm_t a, krim_perm_tmpl_t b
    WHERE a.perm_tmpl_id = b.perm_tmpl_id AND b.nmspc_cd = 'KR-NS' AND b.nm = 'Edit Document' AND a.nmspc_cd = 'KUALI'
          AND a.nm = 'Edit Kuali ENROUTE Document Node Name PreRoute'
  )
/

DELETE FROM krim_perm_t
WHERE nmspc_cd = 'KUALI' AND nm = 'Edit Kuali ENROUTE Document Node Name PreRoute' AND perm_tmpl_id =
  (
    SELECT
      perm_tmpl_id
    FROM krim_perm_tmpl_t
    WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
  )
/

INSERT INTO krim_perm_t (perm_id, obj_id, ver_nbr, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind)
  VALUES (
    'KR1001', sys_guid(), 1,
    (
      SELECT
        perm_tmpl_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    'KUALI', 'Edit Kuali ENROUTE Document Route Status Code I',
    'Allows users to edit Kuali documents that are in INITIATED status.', 'Y'
  )
/

INSERT INTO krim_perm_attr_data_t (attr_data_id, obj_id, ver_nbr, perm_id, kim_typ_id, kim_attr_defn_id, attr_val)
  VALUES (
    'KR1001', sys_guid(), 1, 'KR1001',
    (
      SELECT
        kim_typ_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    (
      SELECT
        kim_attr_defn_id
      FROM krim_attr_defn_t
      WHERE nmspc_cd = 'KR-WKFLW' AND nm = 'documentTypeName'
    ),
    'KualiDocument'
  )
/

INSERT INTO krim_perm_attr_data_t (attr_data_id, obj_id, ver_nbr, perm_id, kim_typ_id, kim_attr_defn_id, attr_val)
  VALUES (
    'KR1002', sys_guid(), 1, 'KR1001',
    (
      SELECT
        kim_typ_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    (
      SELECT
        kim_attr_defn_id
      FROM krim_attr_defn_t
      WHERE nmspc_cd = 'KR-WKFLW' AND nm = 'routeStatusCode'
    ),
    'I'
  )
/

INSERT INTO krim_role_perm_t (role_perm_id, obj_id, ver_nbr, role_id, perm_id, actv_ind)
  VALUES (
    'KR1002', sys_guid(), 1,
    (
      SELECT
        role_id
      FROM krim_role_t
      WHERE role_nm = 'Initiator' AND nmspc_cd = 'KR-WKFLW'
    ),
    (
      SELECT
        perm_id
      FROM krim_perm_t
      WHERE nmspc_cd = 'KUALI' AND nm = 'Edit Kuali ENROUTE Document Route Status Code I'
    ),
    'Y'
  )
/

INSERT INTO krim_perm_t (perm_id, obj_id, ver_nbr, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind)
  VALUES (
    'KR1002', sys_guid(), 1,
    (
      SELECT
        perm_tmpl_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    'KUALI', 'Edit Kuali ENROUTE Document Route Status Code S',
    'Allows users to edit Kuali documents that are in SAVED status.', 'Y'
  )
/

INSERT INTO krim_perm_attr_data_t (attr_data_id, obj_id, ver_nbr, perm_id, kim_typ_id, kim_attr_defn_id, attr_val)
  VALUES (
    'KR1003', sys_guid(), 1, 'KR1002',
    (
      SELECT
        kim_typ_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    (
      SELECT
        kim_attr_defn_id
      FROM krim_attr_defn_t
      WHERE nmspc_cd = 'KR-WKFLW' AND nm = 'documentTypeName'
    ), 'KualiDocument'
  )
/

INSERT INTO krim_perm_attr_data_t (attr_data_id, obj_id, ver_nbr, perm_id, kim_typ_id, kim_attr_defn_id, attr_val)
  VALUES (
    'KR1004', sys_guid(), 1,'KR1002',
    (
      SELECT
        kim_typ_id
      FROM krim_perm_tmpl_t
      WHERE nmspc_cd = 'KR-NS' AND nm = 'Edit Document'
    ),
    (
      SELECT
        kim_attr_defn_id
      FROM krim_attr_defn_t
      WHERE nmspc_cd = 'KR-WKFLW' AND nm = 'routeStatusCode'
    ),
    'S'
  )
/

INSERT INTO krim_role_perm_t (role_perm_id, obj_id, ver_nbr, role_id, perm_id, actv_ind)
  VALUES (
    'KR1003', sys_guid(), 1,
    (
      SELECT
        role_id
      FROM krim_role_t
      WHERE role_nm = 'Initiator' AND nmspc_cd = 'KR-WKFLW'
    ),
    (
      SELECT
        perm_id
      FROM krim_perm_t
      WHERE nmspc_cd = 'KUALI' AND nm = 'Edit Kuali ENROUTE Document Route Status Code S'
    ),
    'Y'
  )
/
ALTER TABLE KREN_NTFCTN_T ADD DOC_TYP_NM VARCHAR2(64)
/
INSERT INTO krcr_cmpnt_t (nmspc_cd, cmpnt_cd, obj_id, ver_nbr, nm, actv_ind)
  VALUES ('KR-KRAD', 'Lookup', SYS_GUID(), 1, 'Lookup', 'Y')
/

INSERT INTO KRCR_PARM_T
  (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID)
  VALUES ('KR-KRAD', 'Lookup', 'RESULTS_LIMIT', SYS_GUID(), 1, 'CONFG', '200',
          'Maximum number of results returned in a look-up query.', 'A', 'KUALI')
/
UPDATE krms_attr_defn_t SET nmspc_cd = 'KR-RULE' WHERE nm = 'peopleFlowName' AND nmspc_cd = 'KR_RULE'
/
DELETE FROM krim_perm_attr_data_t
WHERE attr_val = 'KRMS_TEST' AND perm_id =
  (
    SELECT
      perm_id
    FROM krim_perm_t
    WHERE nm = 'Maintain KRMS Agenda' AND nmspc_cd = 'KR-RULE-TEST'
  )
/
UPDATE krim_perm_attr_data_t SET attr_val='RiceDocument'
WHERE attr_val = '*' AND perm_id =
  (
    SELECT perm_id FROM krim_perm_t WHERE nm='Recall Document' AND nmspc_cd='KR-WKFLW'
  )
/
ALTER TABLE krlc_cntry_t MODIFY ( postal_cntry_nm VARCHAR2(255) )
/