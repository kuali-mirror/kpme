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

--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/db.changelog.xml
--  Ran at: 10/5/12 2:07 PM
--  Against: krdev@localhost@jdbc:mysql://localhost:3306/krdev
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::1::kbtaylor::(Checksum: 3:7fbf2b88e3d83b395ab9795f771f2111)
--  Adding Rice group bootstrap data
INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`) VALUES ('Y', 'KPME', 'KPME Workflow Group', 'KPME-WKFLW', UUID());

--  Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::2::kbtaylor::(Checksum: 3:b71833bd45a4fe5b473ca723936a083d)
--  Adding Rice group bootstrap data
INSERT INTO `KRIM_GRP_T` (`ACTV_IND`, `GRP_DESC`, `GRP_ID`, `GRP_NM`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`) VALUES ('Y', 'KPME TK System Administrator Group', 'KPME0001', 'TK_SYSTEM_ADMIN', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-WKFLW', UUID());

--  Changeset src/main/config/db/1.1.0/db.changelog-201207131332.xml::3::kbtaylor::(Checksum: 3:23c6756b69cec13a8928222935c73d49)
--  Adding Rice group bootstrap data
INSERT INTO `KRIM_GRP_MBR_T` (`GRP_ID`, `GRP_MBR_ID`, `LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`) VALUES ((SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND GRP_NM = 'TK_SYSTEM_ADMIN'), 'KPME0001', NOW(), 'admin', 'P', UUID());

--  Changeset src/main/config/db/1.1.0/db.changelog-201207161146.xml::1::kbtaylor::(Checksum: 3:94e8f7efe14a20aa1d51a26e2758a28f)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`) VALUES ('Y', 'KPME TK System Administrator Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-WKFLW', UUID(), 'KPME0001', 'TK_SYS_ADMIN');

--  Changeset src/main/config/db/1.1.0/db.changelog-201207161146.xml::2::kbtaylor::(Checksum: 3:a6ad5d0d9b521f69ba3f95f57d42b6ad)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`) VALUES (NOW(), 'admin', 'P', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_SYS_ADMIN'), 'KPME0001');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::1::kbtaylor::(Checksum: 3:36afce1eeb121be14b1ce450cbc4dd89)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`) VALUES ('Y', 'KPME0001', 'Work Area Role Type', 'KPME-WKFLW', UUID(), 'workAreaQualifierRoleTypeService');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::3::kbtaylor::(Checksum: 3:e9925ff16c79defa83f14c48bd6b01b3)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`) VALUES ('Y', 'KPME TK Organization Administrator Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Derived Role: Principal'), NOW(), 'KPME-WKFLW', UUID(), 'KPME0002', 'TK_ORG_ADMIN');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::4::kbtaylor::(Checksum: 3:6a11cbb2d31dcb7dfbe4d8ab5b7c5a15)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`) VALUES (NOW(), 'admin', 'P', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_ORG_ADMIN'), 'KPME0002');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::6::kbtaylor::(Checksum: 3:d39b4d5793b60edc34309715312646ff)
--  Adding Rice role bootstrap data
INSERT INTO `krim_role_t` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`) VALUES ('Y', 'KPME Approver Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area Role Type'), NOW(), 'KPME-WKFLW', UUID(), 'KPME0003', 'TK_APPROVER');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::7::kbtaylor::(Checksum: 3:a5ddb06ccf146bf1305fd0db028b784a)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`) VALUES (NOW(), 'admin', 'P', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_APPROVER'), 'KPME0003');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::9::kbtaylor::(Checksum: 3:f51af8a9dbdbebfa0d579f8f38f304bc)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`) VALUES ('Y', 'KPME Employee Role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-WKFLW', UUID(), 'KPME0004', 'TK_EMPLOYEE');

--  Changeset src/main/config/db/1.2.0/db.changelog-201207161146.xml::20::kbtaylor::(Checksum: 3:37b615be250cdacef66e576799dc439c)
--  Adding Rice role bootstrap data
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`) VALUES (NOW(), 'admin', 'P', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_EMPLOYEE'), 'KPME0004');

--  Changeset src/main/config/db/1.2.0/db.changelog-201209060957.xml::2::kbtaylor::(Checksum: 3:2fc93ded436e73c85f42cfe690b7e556)
--  Removing admin as role members
DELETE FROM `KRIM_ROLE_MBR_T`  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_ORG_ADMIN') AND MBR_ID = 'admin';

DELETE FROM `KRIM_ROLE_MBR_T`  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_APPROVER') AND MBR_ID = 'admin';

DELETE FROM `KRIM_ROLE_MBR_T`  WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-WKFLW' AND ROLE_NM = 'TK_EMPLOYEE') AND MBR_ID = 'admin';

