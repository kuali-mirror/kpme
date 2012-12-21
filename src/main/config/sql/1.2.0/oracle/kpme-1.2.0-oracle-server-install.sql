--
-- Copyright 2004-2012 The Kuali Foundation
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

-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/db.changelog.xml
-- Ran at: 10/5/12 3:06 PM
-- Against: KRDEV@jdbc:oracle:thin:@127.0.0.1:1521:XE
-- Liquibase version: 2.0.5
-- *********************************************************************

-- Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::1::kbtaylor::(Checksum: 3:0c5395b386f3ac50312cf489acbee6b4)
-- Adding Rice group bootstrap data
INSERT INTO KRCR_NMSPC_T (ACTV_IND, APPL_ID, NM, NMSPC_CD, OBJ_ID) VALUES ('Y', 'KPME', 'KPME Workflow Group', 'KPME-WKFLW', SYS_GUID());

-- Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::2::kbtaylor::(Checksum: 3:cc835488301ee9a9a5d8cd6f6ab40b35)
-- Adding Rice group bootstrap data
INSERT INTO KRIM_GRP_T (ACTV_IND, GRP_DESC, GRP_ID, GRP_NM, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID) VALUES ('Y', 'KPME TK System Administrator Group', 'KPME0001', 'TK_SYSTEM_ADMIN', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), SYSDATE, 'KPME-WKFLW', SYS_GUID());

-- Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::3::kbtaylor::(Checksum: 3:1c07cd2ce9fd458a0824c68917d8bf24)
-- Adding Rice group bootstrap data
INSERT INTO KRIM_GRP_MBR_T (GRP_ID, GRP_MBR_ID, LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID) VALUES ((SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND GRP_NM = 'TK_SYSTEM_ADMIN'), 'KPME0001', SYSDATE, 'admin', 'P', SYS_GUID());

-- Changeset src/main/config/db/1.1.0/db.changelog-201207161146.xml::1::kbtaylor::(Checksum: 3:597b1da4ec97a4a9493808b83ac42418)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM) VALUES ('Y', 'KPME TK System Administrator Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), SYSDATE, 'KPME-WKFLW', SYS_GUID(), 'KPME0001', 'TK_SYS_ADMIN');

-- Changeset src/main/config/db/1.1.0/db.changelog-201207161146.xml::2::kbtaylor::(Checksum: 3:b4930c3a14cea7d71fc8e188a3105f88)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID) VALUES (SYSDATE, 'admin', 'P', SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_SYS_ADMIN'), 'KPME0001');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::1::kbtaylor::(Checksum: 3:6c46a88d5f94a1db13acedf12d816318)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, SRVC_NM) VALUES ('Y', 'KPME0001', 'Work Area Role Type', 'KPME-WKFLW', SYS_GUID(), 'workAreaQualifierRoleTypeService');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::3::kbtaylor::(Checksum: 3:f7b08d819f9227b4aea245c9d5b0e5aa)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM) VALUES ('Y', 'KPME TK Organization Administrator Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Derived Role: Principal'), SYSDATE, 'KPME-WKFLW', SYS_GUID(), 'KPME0002', 'TK_ORG_ADMIN');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::4::kbtaylor::(Checksum: 3:89b8885a11e2b779e9700fff1ac24058)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID) VALUES (SYSDATE, 'admin', 'P', SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_ORG_ADMIN'), 'KPME0002');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::6::kbtaylor::(Checksum: 3:bf1b67170847bd5f213b5d73ae406e8f)
-- Adding Rice role bootstrap data
INSERT INTO krim_role_t (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM) VALUES ('Y', 'KPME Approver Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area Role Type'), SYSDATE, 'KPME-WKFLW', SYS_GUID(), 'KPME0003', 'TK_APPROVER');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::7::kbtaylor::(Checksum: 3:9ccc624fae3259222e7e7394ad3454e9)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID) VALUES (SYSDATE, 'admin', 'P', SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_APPROVER'), 'KPME0003');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::9::kbtaylor::(Checksum: 3:fa91898cad4ebeb3a9080863f281dcac)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM) VALUES ('Y', 'KPME Employee Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), SYSDATE, 'KPME-WKFLW', SYS_GUID(), 'KPME0004', 'TK_EMPLOYEE');

-- Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::20::kbtaylor::(Checksum: 3:058772a0b7c79a48d6ad53de27a9b2e5)
-- Adding Rice role bootstrap data
INSERT INTO KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID) VALUES (SYSDATE, 'admin', 'P', SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_EMPLOYEE'), 'KPME0004');

-- Changeset src/main/config/db/1.2.0/db.changelog-201209060957.xml::2::kbtaylor::(Checksum: 3:2fc93ded436e73c85f42cfe690b7e556)
-- Removing admin as role members
DELETE FROM KRIM_ROLE_MBR_T  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_ORG_ADMIN') AND MBR_ID = 'admin';

DELETE FROM KRIM_ROLE_MBR_T  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_APPROVER') AND MBR_ID = 'admin';

DELETE FROM KRIM_ROLE_MBR_T  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_EMPLOYEE') AND MBR_ID = 'admin';

