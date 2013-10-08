--
-- Copyright 2004-2013 The Kuali Foundation
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
--  Change Log: src/main/config/db/db.changelog-main-upgrade.xml
--  Ran at: 10/3/13 2:18 PM
--  Against: krtt@localhost@jdbc:mysql://localhost/krtt
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::1::kbtaylor::(Checksum: 3:92e445c7dbe1541928b454cf93bc53c5)
--  Adding Rice Namespace bootstrap data
UPDATE `KRCR_NMSPC_T` SET `NM` = 'KPME Workflow' WHERE NMSPC_CD = 'KPME-WKFLW';

INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KPME', 'KPME Human Resources', 'KPME-HR', UUID(), '1');

INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KPME', 'KPME Timekeeping', 'KPME-TK', UUID(), '1');

INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KPME', 'KPME Leave Management', 'KPME-LM', UUID(), '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::2::kbtaylor::(Checksum: 3:4c03e0a502d3a499402125f68e858fec)
--  Adding Rice KIM Attribute Definition bootstrap data
INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.hr.core.KPMEAttributes', 'KPME0001', 'workArea', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.hr.core.KPMEAttributes', 'KPME0002', 'department', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.hr.core.KPMEAttributes', 'KPME0003', 'location', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.hr.core.KPMEAttributes', 'KPME0004', 'position', 'KPME-WKFLW', UUID(), '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::3::kbtaylor::(Checksum: 3:bdc11e005d06e25230cb3c146a934a47)
--  Adding Rice KIM Type bootstrap data
UPDATE `KRIM_TYP_T` SET `NM` = 'Work Area', `SRVC_NM` = '{http://kpme.kuali.org/core/v2_0}workAreaRoleTypeService' WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area Role Type';

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'workArea'), 'KPME0001', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0002', 'Department', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}departmentRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'department'), 'KPME0002', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0003', 'Location', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}locationRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'location'), 'KPME0003', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0004', 'Position', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}positionDerivedRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'position'), 'KPME0004', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Position'), UUID(), 'a', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::4::kbtaylor::(Checksum: 3:e7123da431d1e1a4386c01474eef7574)
--  Adding Rice KIM Group bootstrap data
DELETE FROM `KRIM_GRP_MBR_T`  WHERE GRP_ID = 'KPME0001';

DELETE FROM `KRIM_GRP_T`  WHERE GRP_ID = 'KPME0001';

INSERT INTO `KRIM_GRP_T` (`ACTV_IND`, `GRP_DESC`, `GRP_ID`, `GRP_NM`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'System Administrator Group', 'KPME0001', 'System Administrator', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-HR', UUID(), '1');

INSERT INTO `KRIM_GRP_T` (`ACTV_IND`, `GRP_DESC`, `GRP_ID`, `GRP_NM`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'System View Only Group', 'KPME0002', 'System View Only', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-HR', UUID(), '1');

INSERT INTO `KRIM_GRP_MBR_T` (`GRP_ID`, `GRP_MBR_ID`, `LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`) VALUES ('KPME0001', 'KPME0001', NOW(), 'admin', 'P', UUID());

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::5::kbtaylor::(Checksum: 3:ac8eafeeddc4f34ff575305d2d2ac6b1)
--  Adding Rice KIM Role bootstrap data
DELETE FROM `KRIM_ROLE_MBR_T`  WHERE ROLE_ID = 'KPME0001';

DELETE FROM `KRIM_ROLE_T`  WHERE ROLE_ID IN ('KPME0001', 'KPME0002', 'KPME0003', 'KPME0004');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system administrator role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-TK', UUID(), 'KPME0001', 'Time System Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system administrator role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-LM', UUID(), 'KPME0002', 'Leave System Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system view only role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-TK', UUID(), 'KPME0003', 'Time System View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system view only role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-LM', UUID(), 'KPME0004', 'Leave System View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location administrator role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-TK', UUID(), 'KPME0005', 'Time Location Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location administrator role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-LM', UUID(), 'KPME0006', 'Leave Location Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location view only role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-TK', UUID(), 'KPME0007', 'Time Location View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location view only role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-LM', UUID(), 'KPME0008', 'Leave Location View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department administrator role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-TK', UUID(), 'KPME0009', 'Time Department Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department administrator role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-LM', UUID(), 'KPME0010', 'Leave Department Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department view only role for the Timesheet system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-TK', UUID(), 'KPME0011', 'Time Department View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department view only role for the Leave Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-LM', UUID(), 'KPME0012', 'Leave Department View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), NOW(), 'KPME-HR', UUID(), 'KPME0013', 'Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The approver delegate role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), NOW(), 'KPME-HR', UUID(), 'KPME0014', 'Approver Delegate', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The reviewer role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), NOW(), 'KPME-HR', UUID(), 'KPME0015', 'Reviewer', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The position derived role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Position'), NOW(), 'KPME-HR', UUID(), 'KPME0016', 'Derived Role : Position', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::6::kbtaylor::(Checksum: 3:a0bc6622a3a4b80491c8b616ff2b3b3b)
--  Adding Rice KIM Role Member bootstrap data
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System Administrator'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0001', '1');

INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System Administrator'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0002', '1');

INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System View Only'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0003', '1');

INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System View Only'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0004', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291257.xml::7::kbtaylor::(Checksum: 3:cb3363377ac66e7a4a76f82a1e96a6bc)
--  Role and Permission Cleanup
DELETE FROM `KRIM_ROLE_PERM_T`  WHERE ROLE_PERM_ID = 'KPME0001';

DELETE FROM `KRIM_PERM_ATTR_DATA_T`  WHERE ATTR_DATA_ID = 'KPME0001';

DELETE FROM `KRIM_PERM_T`  WHERE PERM_ID = 'KPME0001';

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291258.xml::1::kbtaylor::(Checksum: 3:703525f604f7720dc2ac6eedd7aa4852)
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Timesheet', 'View Timesheet', 'KPME-TK', UUID(), 'KPME0001', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Open Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0001', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0001', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a saved Timesheet', 'Edit Saved Timesheet', 'KPME-TK', UUID(), 'KPME0002', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0002', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0002', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0003', 'S', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0002', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an enroute Timesheet', 'Edit Enroute Timesheet', 'KPME-TK', UUID(), 'KPME0003', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0004', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0003', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0005', 'R', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0003', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a final Timesheet', 'Edit Final Timesheet', 'KPME-TK', UUID(), 'KPME0004', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0006', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0004', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0007', 'F', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0004', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to submit a Timesheet', 'Submit Timesheet', 'KPME-TK', UUID(), 'KPME0005', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Route Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0008', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0005', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows super users to administer a Timesheet', 'Super User Administer Timesheet', 'KPME-TK', UUID(), 'KPME0006', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Administer Routing for Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0009', 'TimesheetDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0006', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Calendar', 'View Leave Calendar', 'KPME-LM', UUID(), 'KPME0007', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Open Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0010', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0007', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a saved Leave Calendar', 'Edit Saved Leave Calendar', 'KPME-LM', UUID(), 'KPME0008', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0011', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0008', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0012', 'S', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0008', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an enroute Leave Calendar', 'Edit Enroute Leave Calendar', 'KPME-LM', UUID(), 'KPME0009', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0013', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0009', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0014', 'R', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0009', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a final Leave Calendar', 'Edit Final Leave Calendar', 'KPME-LM', UUID(), 'KPME0010', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0015', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0010', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0016', 'F', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0010', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to submit a Leave Calendar', 'Submit Leave Calendar', 'KPME-LM', UUID(), 'KPME0011', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Route Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0017', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0011', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows super users to administer a Leave Calendar', 'Super User Administer Leave Calendar', 'KPME-LM', UUID(), 'KPME0012', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Administer Routing for Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0018', 'LeaveCalendarDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0012', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Request', 'View Leave Request', 'KPME-LM', UUID(), 'KPME0013', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Open Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0019', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0013', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a saved Leave Request', 'Edit Saved Leave Request', 'KPME-LM', UUID(), 'KPME0014', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0020', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0014', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0021', 'S', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0014', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an enroute Leave Request', 'Edit Enroute Leave Request', 'KPME-LM', UUID(), 'KPME0015', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0022', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0015', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0023', 'R', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0015', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a final Leave Request', 'Edit Final Leave Request', 'KPME-LM', UUID(), 'KPME0016', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0024', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0016', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0025', 'F', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0016', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to submit a Leave Request', 'Submit Leave Request', 'KPME-LM', UUID(), 'KPME0017', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Route Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0026', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0017', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows super users to administer a Leave Request', 'Super User Administer Leave Request', 'KPME-LM', UUID(), 'KPME0018', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Administer Routing for Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0027', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0018', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to recall a Leave Request', 'Recall Leave Request', 'KPME-LM', UUID(), 'KPME0019', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Recall Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0028', 'LeaveRequestDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0019', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::1::kbtaylor::(Checksum: 3:f94f46db385bec77c2fc49afb82a193b)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Super User Administer Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0001', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Super User Administer Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0002', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Super User Administer Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0003', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::2::kbtaylor::(Checksum: 3:dbb4187494480034eaf35905601364e3)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0004', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0005', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0006', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::3::kbtaylor::(Checksum: 3:2be50a615f778d90ee9a70ee0587c87d)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Super User Administer Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0007', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Super User Administer Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0008', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Super User Administer Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0009', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::4::kbtaylor::(Checksum: 3:6eb7d209c937937987c177eba8b7e901)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0010', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0011', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0012', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::5::kbtaylor::(Checksum: 3:940e2c5c4d7213513bd5daf4ffd40f72)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0013', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0014', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0015', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::6::kbtaylor::(Checksum: 3:7988b21b1f1109b840768e49d5eb38b7)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0016', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0017', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0018', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::7::kbtaylor::(Checksum: 3:5a3b98d44ddd67ce1f0224c0f24ca8b8)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0019', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0020', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0021', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Submit Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0022', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0023', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0024', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0025', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0026', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0027', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0028', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0029', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Final Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0030', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0031', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::8::kbtaylor::(Checksum: 3:042bba1a5b83ae1ddc36e8792049c7d9)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0032', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0033', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0034', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Submit Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0035', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0036', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0037', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0038', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0039', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0040', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0041', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0042', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Final Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0043', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0044', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::9::kbtaylor::(Checksum: 3:c22e3f944afec43885e38479e70b6b42)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0045', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0046', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0047', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0048', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0049', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0050', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0051', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0052', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Reviewer'), 'KPME0053', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291259.xml::10::kbtaylor::(Checksum: 3:7ddf63505ee95b3f140393abc5c0f3f2)
--  Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0054', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0055', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0056', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Submit Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0057', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0058', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0059', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0060', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0061', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0062', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0063', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0064', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Final Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0065', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0066', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Recall Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KR-WKFLW' AND ROLE_NM = 'Initiator'), 'KPME0067', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291300.xml::1::kbtaylor::(Checksum: 3:48007a3611f0caad88afb6f9ab3ac5e2)
--  Adding Rice KIM Permission bootstrap data
INSERT INTO `KRIM_PERM_TMPL_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), 'Create KPME Maintenance Document', 'KPME-WKFLW', UUID(), 'KPME0001', '1');

INSERT INTO `KRIM_PERM_TMPL_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), 'Edit KPME Maintenance Document', 'KPME-WKFLW', UUID(), 'KPME0002', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291300.xml::2::kbtaylor::(Checksum: 3:f96ec703526e7c84ebc075091f6f30d9)
--  Adding Rice KIM Permission bootstrap data
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Assignment', 'Create Assignment', 'KPME-HR', UUID(), 'KPME0020', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0029', 'AssignmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0020', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Assignment', 'Edit Assignment', 'KPME-HR', UUID(), 'KPME0021', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0030', 'AssignmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0021', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Calendar', 'Create Calendar', 'KPME-HR', UUID(), 'KPME0022', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0031', 'PayCalendarDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0022', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Calendar', 'Edit Calendar', 'KPME-HR', UUID(), 'KPME0023', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0032', 'PayCalendarDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0023', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Calendar Entry', 'Create Calendar Entry', 'KPME-HR', UUID(), 'KPME0024', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0033', 'PayCalendarEntriesDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0024', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Calendar Entry', 'Edit Calendar Entry', 'KPME-HR', UUID(), 'KPME0025', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0034', 'PayCalendarEntriesDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0025', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Department', 'Create Department', 'KPME-HR', UUID(), 'KPME0026', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0035', 'DepartmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0026', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Department', 'Edit Department', 'KPME-HR', UUID(), 'KPME0027', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0036', 'DepartmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0027', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Earn Code', 'Create Earn Code', 'KPME-HR', UUID(), 'KPME0028', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0037', 'EarnCodeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0028', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Earn Code', 'Edit Earn Code', 'KPME-HR', UUID(), 'KPME0029', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0038', 'EarnCodeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0029', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Earn Code Group', 'Create Earn Code Group', 'KPME-HR', UUID(), 'KPME0030', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0039', 'EarnCodeGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0030', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Earn Code Group', 'Edit Earn Code Group', 'KPME-HR', UUID(), 'KPME0031', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0040', 'EarnCodeGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0031', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Earn Code Security', 'Create Earn Code Security', 'KPME-HR', UUID(), 'KPME0032', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0041', 'EarnCodeSecurityDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0032', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Earn Code Security', 'Edit Earn Code Security', 'KPME-HR', UUID(), 'KPME0033', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0042', 'EarnCodeSecurityDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0033', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Job', 'Create Job', 'KPME-HR', UUID(), 'KPME0034', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0043', 'JobMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0034', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Job', 'Edit Job', 'KPME-HR', UUID(), 'KPME0035', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0044', 'JobMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0035', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Location', 'Create Location', 'KPME-HR', UUID(), 'KPME0036', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0045', 'LocationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0036', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Location', 'Edit Location', 'KPME-HR', UUID(), 'KPME0037', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0046', 'LocationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0037', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Pay Grade', 'Create Pay Grade', 'KPME-HR', UUID(), 'KPME0038', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0047', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0038', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Pay Grade', 'Edit Pay Grade', 'KPME-HR', UUID(), 'KPME0039', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0048', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0039', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Pay Type', 'Create Pay Type', 'KPME-HR', UUID(), 'KPME0040', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0049', 'PayTypeDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0040', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Pay Type', 'Edit Pay Type', 'KPME-HR', UUID(), 'KPME0041', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0050', 'PayTypeDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0041', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position', 'Create Position', 'KPME-HR', UUID(), 'KPME0042', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0051', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0042', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position', 'Edit Position', 'KPME-HR', UUID(), 'KPME0043', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0052', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0043', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Principal HR Attribute', 'Create Principal HR Attribute', 'KPME-HR', UUID(), 'KPME0044', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0053', 'PrincipalHRAttributesMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0044', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Principal HR Attribute', 'Edit Principal HR Attribute', 'KPME-HR', UUID(), 'KPME0045', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0054', 'PrincipalHRAttributesMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0045', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Salary Group', 'Create Salary Group', 'KPME-HR', UUID(), 'KPME0046', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0055', 'SalGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0046', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Salary Group', 'Edit Salary Group', 'KPME-HR', UUID(), 'KPME0047', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0056', 'SalGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0047', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Work Area', 'Create Work Area', 'KPME-HR', UUID(), 'KPME0048', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0057', 'WorkAreaMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0048', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Work Area', 'Edit Work Area', 'KPME-HR', UUID(), 'KPME0049', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0058', 'WorkAreaMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0049', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291300.xml::3::kbtaylor::(Checksum: 3:860821dc3cffe81180842258070d1e27)
--  Adding Rice KIM Permission bootstrap data
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Clock Location Rule', 'Create Clock Location Rule', 'KPME-TK', UUID(), 'KPME0050', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0059', 'ClockLocationRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0050', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Clock Location Rule', 'Edit Clock Location Rule', 'KPME-TK', UUID(), 'KPME0051', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0060', 'ClockLocationRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0051', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Daily Overtime Rule', 'Create Daily Overtime Rule', 'KPME-TK', UUID(), 'KPME0052', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0061', 'DailyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0052', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Daily Overtime Rule', 'Edit Daily Overtime Rule', 'KPME-TK', UUID(), 'KPME0053', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0062', 'DailyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0053', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Department Lunch Rule', 'Create Department Lunch Rule', 'KPME-TK', UUID(), 'KPME0054', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0063', 'DeptLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0054', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Department Lunch Rule', 'Edit Department Lunch Rule', 'KPME-TK', UUID(), 'KPME0055', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0064', 'DeptLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0055', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Grace Period Rule', 'Create Grace Period Rule', 'KPME-TK', UUID(), 'KPME0056', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0065', 'GracePeriodRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0056', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Grace Period Rule', 'Edit Grace Period Rule', 'KPME-TK', UUID(), 'KPME0057', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0066', 'GracePeriodRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0057', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Shift Differential Rule', 'Create Shift Differential Rule', 'KPME-TK', UUID(), 'KPME0058', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0067', 'ShiftDifferentialRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0058', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Shift Differential Rule', 'Edit Shift Differential Rule', 'KPME-TK', UUID(), 'KPME0059', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0068', 'ShiftDifferentialRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0059', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a System Lunch Rule', 'Create System Lunch Rule', 'KPME-TK', UUID(), 'KPME0060', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0069', 'SystemLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0060', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a System Lunch Rule', 'Edit System Lunch Rule', 'KPME-TK', UUID(), 'KPME0061', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0070', 'SystemLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0061', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Time Collection Rule', 'Create Time Collection Rule', 'KPME-TK', UUID(), 'KPME0062', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0071', 'TimeCollectionRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0062', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Time Collection Rule', 'Edit Time Collection Rule', 'KPME-TK', UUID(), 'KPME0063', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0072', 'TimeCollectionRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0063', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Weekly Overtime Rule', 'Create Weekly Overtime Rule', 'KPME-TK', UUID(), 'KPME0064', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0073', 'WeeklyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0064', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Weekly Overtime Rule', 'Edit Weekly Overtime Rule', 'KPME-TK', UUID(), 'KPME0065', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0074', 'WeeklyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0065', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291300.xml::4::kbtaylor::(Checksum: 3:a39c7ef57a7d901b49f51055119ed4b1)
--  Adding Rice KIM Permission bootstrap data
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Accrual Category', 'Create Accrual Category', 'KPME-LM', UUID(), 'KPME0066', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0075', 'AccrualCategoryDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0066', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Accrual Category', 'Edit Accrual Category', 'KPME-LM', UUID(), 'KPME0067', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0076', 'AccrualCategoryDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0067', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Balance Transfer', 'Create Balance Transfer', 'KPME-LM', UUID(), 'KPME0068', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0077', 'BalanceTransferDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0068', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Balance Transfer', 'Edit Balance Transfer', 'KPME-LM', UUID(), 'KPME0069', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0078', 'BalanceTransferDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0069', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Employee Override', 'Create Employee Override', 'KPME-LM', UUID(), 'KPME0070', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0079', 'EmployeeOverrideDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0070', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Employee Override', 'Edit Employee Override', 'KPME-LM', UUID(), 'KPME0071', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0080', 'EmployeeOverrideDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0071', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Leave Adjustment', 'Create Leave Adjustment', 'KPME-LM', UUID(), 'KPME0072', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0081', 'LeaveAdjustmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0072', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Leave Adjustment', 'Edit Leave Adjustment', 'KPME-LM', UUID(), 'KPME0073', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0082', 'LeaveAdjustmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0073', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Leave Donation', 'Create Leave Donation', 'KPME-LM', UUID(), 'KPME0074', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0083', 'LeaveDonationDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0074', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Leave Donation', 'Edit Leave Donation', 'KPME-LM', UUID(), 'KPME0075', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0084', 'LeaveDonationDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0075', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Leave Payout', 'Create Leave Payout', 'KPME-LM', UUID(), 'KPME0076', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0085', 'LeavePayoutDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0076', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Leave Payout', 'Edit Leave Payout', 'KPME-LM', UUID(), 'KPME0077', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0086', 'LeavePayoutDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0077', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Leave Plan', 'Create Leave Plan', 'KPME-LM', UUID(), 'KPME0078', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0087', 'LeavePlanDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0078', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Leave Plan', 'Edit Leave Plan', 'KPME-LM', UUID(), 'KPME0079', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0088', 'LeavePlanDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0079', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a System Scheduled Time Off', 'Create System Scheduled Time Off', 'KPME-LM', UUID(), 'KPME0080', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0089', 'SystemScheduledTimeOffDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0080', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a System Scheduled Time Off', 'Edit System Scheduled Time Off', 'KPME-LM', UUID(), 'KPME0081', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0090', 'SystemScheduledTimeOffDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0081', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291301.xml::1::kbtaylor::(Checksum: 3:f4df542c8530b6270d68d244f12f8330)
--  Adding Rice KIM Role Permission bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0068', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0069', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0070', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0071', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0072', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0073', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0074', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0075', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0076', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0077', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0078', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0079', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0080', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0081', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0082', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0083', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0084', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0085', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0086', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0087', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0088', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0089', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0090', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0091', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0092', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0093', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0094', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0095', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0096', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0097', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0098', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0099', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0100', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0101', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0102', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0103', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0104', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0105', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0106', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0107', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0108', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0109', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0110', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0111', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0112', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0113', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0114', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0115', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0116', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0117', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0118', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0119', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0120', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0121', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0122', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0123', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0124', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0125', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0126', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0127', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0128', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0129', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0130', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0131', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0132', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0133', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0134', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0135', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0136', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0137', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0138', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0139', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0140', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0141', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0142', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0143', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0144', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0145', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0146', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0147', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0148', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0149', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0150', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0151', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0152', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0153', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0154', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0155', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0156', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0157', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0158', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0159', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0160', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0161', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0162', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0163', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0164', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0165', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0166', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0167', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0168', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0169', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0170', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0171', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0172', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0173', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0174', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0175', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0176', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0177', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0178', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0179', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0180', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0181', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0182', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0183', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0184', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0185', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0186', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0187', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0188', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0189', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0190', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0191', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291301.xml::2::kbtaylor::(Checksum: 3:22f76a8bdc0ece2c77fd43e2e6934cd5)
--  Adding Rice KIM Role Permission bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0192', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0193', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0194', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0195', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0196', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0197', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0198', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0199', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0200', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0201', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0202', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0203', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0204', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0205', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0206', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0207', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0208', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0209', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0210', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0211', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0212', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0213', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0214', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0215', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0216', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0217', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0218', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0219', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201303291301.xml::3::kbtaylor::(Checksum: 3:4d9ed37f2d06f660c2da549b517cc874)
--  Adding Rice KIM Role Permission bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0220', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0221', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0222', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0223', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0224', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0225', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0226', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0227', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0228', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0229', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0230', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0231', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0232', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0233', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0234', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0235', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0236', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0237', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0238', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0239', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0240', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0241', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0242', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Create System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0243', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0244', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201304241349.xml::1::kbtaylor::(Checksum: 3:7abdd1e72c63e3a527db2d51940cab84)
--  Adding row-based security for maintenance documents
INSERT INTO `KRIM_PERM_TMPL_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), 'View KPME Record', 'KPME-WKFLW', UUID(), 'KPME0003', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201304241349.xml::2::kbtaylor::(Checksum: 3:64510e05894402867705a2727381710f)
--  Adding row-based security for maintenance documents
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a KPME Record', 'View KPME Record', 'KPME-WKFLW', UUID(), 'KPME0082', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0245', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0246', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0247', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0248', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0249', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0250', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0251', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0252', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0253', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0254', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0255', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0256', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201304271154.xml::1::dgodfrey::(Checksum: 3:929d52b5bdc9b21d48855995f3926e00)
--  Modifying Rice KRIM Attribute Definition's component name
UPDATE krim_attr_defn_t SET CMPNT_NM = 'org.kuali.kpme.core.KPMEAttributes' WHERE KIM_ATTR_DEFN_ID = 'KPME0001';

UPDATE krim_attr_defn_t SET CMPNT_NM = 'org.kuali.kpme.core.KPMEAttributes' WHERE KIM_ATTR_DEFN_ID = 'KPME0002';

UPDATE krim_attr_defn_t SET CMPNT_NM = 'org.kuali.kpme.core.KPMEAttributes' WHERE KIM_ATTR_DEFN_ID = 'KPME0003';

UPDATE krim_attr_defn_t SET CMPNT_NM = 'org.kuali.kpme.core.KPMEAttributes' WHERE KIM_ATTR_DEFN_ID = 'KPME0004';

--  Changeset src/main/config/db/2.0.0/db.changelog-201305060909.xml::8::kbtaylor::(Checksum: 3:4e78eeb1dc2247ad4a14f2abd4c19440)
UPDATE `KREW_DOC_TYP_T` SET `DOC_HDLR_URL` = '${kuali.docHandler.url.prefix}/kr-krad/missedPunch?methodToCall=docHandler', `POST_PRCSR` = NULL WHERE DOC_TYP_NM = 'MissedPunchDocumentType';

--  Changeset src/main/config/db/2.0.0/db.changelog-201305291330.xml::1::dgodfrey::(Checksum: 3:683fd2bf4f94c70bfe1e2282c8dc4643)
UPDATE `KREW_DOC_TYP_T` SET `POST_PRCSR` = 'org.kuali.kpme.tklm.leave.workflow.postprocessor.LeaveRequestPostProcessor' WHERE DOC_TYP_NM = 'LeaveRequestDocument';

--  Changeset src/main/config/db/2.0.0/db.changelog-201305291330.xml::2::dgodfrey::(Checksum: 3:0e3af181908afd211cf19dae08cd7044)
UPDATE `KREW_DOC_TYP_T` SET `POST_PRCSR` = 'org.kuali.kpme.tklm.leave.workflow.postprocessor.LmPostProcessor' WHERE DOC_TYP_NM = 'LeaveCalendarDocument';

--  Changeset src/main/config/db/2.0.0/db.changelog-201305291330.xml::3::dgodfrey::(Checksum: 3:a163da4e7967e39e2d2f7669f0d6e3ec)
UPDATE `KREW_DOC_TYP_T` SET `POST_PRCSR` = 'org.kuali.kpme.tklm.time.workflow.postprocessor.TkPostProcessor' WHERE DOC_TYP_NM = 'TimesheetDocument';

--  Changeset src/main/config/db/2.0.0/db.changelog-201306111214.xml::1::kbtaylor::(Checksum: 3:969bf146393c628d0795ff64cbea0cf4)
UPDATE `KREW_DOC_TYP_T` SET `DOC_HDLR_URL` = '${kuali.docHandler.url.prefix}/kpme/missedPunch?methodToCall=docHandler' WHERE DOC_TYP_NM = 'MissedPunchDocumentType';

--  Changeset src/main/config/db/2.0.0/db.changelog-201307101700.xml::1::yingzhou::(Checksum: 3:194ae07eec7a9a0c4a621d4efeaf4072)
--  Adding Payroll Processor and Delegate KIM Role bootstrap data
INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The payroll processor role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0017', 'Payroll Processor', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The Payroll Processor Delegate role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0018', 'Payroll Processor Delegate', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307101700.xml::2::yingzhou::(Checksum: 3:fa950536828a5c23ab38eeca7620c1a2)
--  Adding Payroll Processor KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0257', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0258', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0259', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Submit Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0260', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0261', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0262', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0263', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0264', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0265', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0266', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0267', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Final Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0268', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor'), 'KPME0269', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307101700.xml::3::yingzhou::(Checksum: 3:53e98041a7aacfb60541b2241743c1de)
--  Adding Payroll processor Delegate KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0270', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Saved Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0271', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Enroute Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0272', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Submit Timesheet'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0273', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0274', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0275', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0276', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0277', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0278', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Saved Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0279', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Enroute Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0280', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Edit Final Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0281', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Submit Leave Request'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Processor Delegate'), 'KPME0282', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::1::jjhanso::(Checksum: 3:925f04fc52fc9f81d3cf241516d96069)
INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KPME', 'KPME Rules Infrastructure', 'KPME-KRMS', UUID(), '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::2::jjhanso::(Checksum: 3:ac5983d18025e87f1e2e580eb0bbf49d)
--  Set up Kim Permissions and assign them to roles
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Authorizes users to modify the information on the Assignees Tab of the Role Document and the Roles section of the Membership Tab on the Person Document for Roles with a Module Code beginning with KPME.', 'Assign Role', 'KPME-HR', UUID(), 'KPME0083', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Assign Role'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Authorizes users to modify the information on the Permissions tab of the Role Document for roles with a module code beginning with KPME.', 'Grant Permission', 'KPME-HR', UUID(), 'KPME0084', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Grant Permission'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Authorizes users to modify the information on the Responsibility tab of the Role Document for roles with a module code beginning with KPME.', 'Grant Responsibility', 'KPME-HR', UUID(), 'KPME0085', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Grant Responsibility'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Maintain Timesheet KRMS Agenda', 'Maintain KRMS Agenda', 'KPME-TK', UUID(), 'KPME0086', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-RULE' AND NM = 'KRMS Agenda Permission'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Maintain Leave KRMS Agenda', 'Maintain KRMS Agenda', 'KPME-LM', UUID(), 'KPME0087', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-RULE' AND NM = 'KRMS Agenda Permission'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0091', 'KPME*', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'namespaceCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Role'), UUID(), 'KPME0083', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0092', 'KPME*', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'namespaceCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Permission'), UUID(), 'KPME0084', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0093', 'KPME*', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'namespaceCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-IDM' AND NM = 'Responsibility'), UUID(), 'KPME0085', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Assign Role'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0283', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Permission'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0284', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Responsibility'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0285', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Assign Role'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0286', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Permission'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0287', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Responsibility'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0288', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Maintain KRMS Agenda'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0289', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'Maintain KRMS Agenda'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0290', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::3::jjhanso::(Checksum: 3:cbcede6a15f6cea37ae0a6385a0d573b)
--  Adding krms context type
INSERT INTO `KRMS_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'CONTEXT', 'KPME-KRMS', 'KPME0001');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::4::jjhanso::(Checksum: 3:7933e62e4c1efece7c43190d9e3c4cf5)
--  setup Timesheet Agenda and peopleflow
INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`) VALUES ('Y', 'Work Area Type', 'KPME-HR', '{http://kpme.kuali.org/core/v2_0}workAreaPeopleFlowTypeService', 'KPME0001');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`) VALUES ('Y', 'Payroll Type', 'KPME-HR', '{http://kpme.kuali.org/core/v2_0}payrollPeopleFlowTypeService', 'KPME0002');

INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPME-TIMESHEET-CONTEXT', 'KPME context for Timesheet', 'KPME Timesheet Context', 'KPME-TK', 'KPME0001');

INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPME-LEAVE-CAL-CONTEXT', 'KPME context for Leave Calendar', 'KPME Leave Calendar Context', 'KPME-LM', 'KPME0001');

INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`) VALUES ('Y', 'KPME0001', 'KPME-TIMESHEET-CONTEXT', 'KPME0001', 'Timesheet Agenda');

INSERT INTO `KRMS_CNTXT_VLD_AGENDA_TYP_T` (`AGENDA_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_AGENDA_ID`) VALUES ('KPME0002', 'KPME-TIMESHEET-CONTEXT', 'KPME0001');

INSERT INTO `KRMS_CNTXT_VLD_RULE_TYP_T` (`CNTXT_ID`, `CNTXT_VLD_RULE_ID`, `RULE_TYP_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0001', (select TYP_ID from KRMS_TYP_T where NM='Validation Rule' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Validation Action' and NMSPC_CD='KR-RULE'), 'KPME-TIMESHEET-CONTEXT', 'KPME0001');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Notify PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-TIMESHEET-CONTEXT', 'KPME0002');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-TIMESHEET-CONTEXT', 'KPME0003');

INSERT INTO `KREW_ATTR_DEFN_T` (`ACTV`, `ATTR_DEFN_ID`, `CMPNT_NM`, `LBL`, `NM`, `NMSPC_CD`) VALUES ('Y', 'KPME0001', 'org.kuali.kpme.core.KPMEAttributes', 'Work Area', 'workArea', 'KPME-WKFLW');

INSERT INTO `KREW_ATTR_DEFN_T` (`ACTV`, `ATTR_DEFN_ID`, `CMPNT_NM`, `LBL`, `NM`, `NMSPC_CD`) VALUES ('Y', 'KPME0002', 'org.kuali.kpme.core.KPMEAttributes', 'Department', 'department', 'KPME-WKFLW');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`) VALUES ('Y', 'Work Area people flow', 'WorkArea PeopleFlow', 'KPME-WKFLW', 'KPME0001', 'KPME0001');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`) VALUES ('F', 'KPME0013', 'R', 'KPME0001', 'KPME0001', '1', 'KPME0001');

INSERT INTO `KREW_PPL_FLW_DLGT_T` (`ACTN_RQST_PLCY_CD`, `DLGN_TYP_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_DLGT_ID`, `PPL_FLW_MBR_ID`, `RSP_ID`) VALUES ('F', 'P', 'KPME0014', 'R', 'KPME0001', 'KPME0001', 'KPME0002');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to workarea', 'WorkArea PeopleFlow', 'KPME-TK', 'KPME0001');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to payroll processor', 'Payroll Processor PeopleFlow', 'KPME-TK', 'KPME0002');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0001', 'Route to WorkArea Approvers', 'WorkArea PeopleFlow', 'KPME-TK', 'KPME0001', '1', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0002', 'Route to Payroll Processors', 'Payroll PeopleFlow', 'KPME-TK', 'KPME0002', '2', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0001', 'KPME0001', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0001');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0002', 'KPME0001', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'WorkArea PeopleFlow');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0003', 'KPME0002', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0002');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0004', 'KPME0002', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'Payroll PeopleFlow');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0001', 'KPME0002', 'KPME0002', '1');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0001', 'KPME0001', 'KPME0001', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::5::jjhanso::(Checksum: 3:c53727aae2de86b2f2236c4e1122ab6a)
--  Set up Leave Calendar Routing
INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to workarea', 'WorkArea PeopleFlow', 'KPME-LM', 'KPME0003');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to payroll processor', 'Payroll Processor PeopleFlow', 'KPME-LM', 'KPME0004');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0003', 'Route to WorkArea Approvers', 'WorkArea PeopleFlow', 'KPME-LM', 'KPME0003', '1', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0004', 'Route to Payroll Processors', 'Payroll PeopleFlow', 'KPME-LM', 'KPME0004', '2', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0005', 'KPME0003', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0001');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0006', 'KPME0003', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'WorkArea PeopleFlow');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0007', 'KPME0004', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0002');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0008', 'KPME0004', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'Payroll PeopleFlow');

INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`) VALUES ('Y', 'KPME0002', 'KPME-LEAVE-CAL-CONTEXT', 'KPME0003', 'Leave Calendar Agenda');

INSERT INTO `KRMS_CNTXT_VLD_AGENDA_TYP_T` (`AGENDA_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_AGENDA_ID`) VALUES ('KPME0003', 'KPME-LEAVE-CAL-CONTEXT', 'KPME0002');

INSERT INTO `KRMS_CNTXT_VLD_RULE_TYP_T` (`CNTXT_ID`, `CNTXT_VLD_RULE_ID`, `RULE_TYP_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0002', (select TYP_ID from KRMS_TYP_T where NM='Validation Rule' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Validation Action' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-CAL-CONTEXT', 'KPME0004');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Notify PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-CAL-CONTEXT', 'KPME0005');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-CAL-CONTEXT', 'KPME0006');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`) VALUES ('Y', 'Work Area people flow', 'Payroll PeopleFlow', 'KPME-WKFLW', 'KPME0002', 'KPME0002');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`) VALUES ('F', 'KPME0017', 'R', 'KPME0002', 'KPME0002', '1', 'KPME0003');

INSERT INTO `KREW_PPL_FLW_DLGT_T` (`ACTN_RQST_PLCY_CD`, `DLGN_TYP_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_DLGT_ID`, `PPL_FLW_MBR_ID`, `RSP_ID`) VALUES ('F', 'P', 'KPME0018', 'R', 'KPME0002', 'KPME0002', 'KPME0004');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0002', 'KPME0004', 'KPME0004', '1');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0002', 'KPME0003', 'KPME0003', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::6::jjhanso::(Checksum: 3:3e3ff78ef45be78ec585c5fa308e6418)
--  Set order of Agenda Items
UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0002' WHERE AGENDA_ITM_ID = 'KPME0001';

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0004' WHERE AGENDA_ITM_ID = 'KPME0003';

--  Changeset src/main/config/db/2.0.0/db.changelog-201307110930.xml::7::jjhanso::(Checksum: 3:a02877f965830052a9ce68cd116641e4)
--  Set up rule structures
INSERT INTO `KRMS_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPMETermResolver', 'KPME-KRMS', 'KPME0002');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'does the department require Payroll Processor approval', 'payrollProcessorApproval', 'KPME-TK', 'KPME0001', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'does the department require Payroll Processor approval', 'payrollProcessorApproval', 'KPME-LM', 'KPME0002', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_T` (`DESC_TXT`, `TERM_ID`, `TERM_SPEC_ID`, `VER_NBR`) VALUES ('Department requires Payroll Processor approval', 'KPME0001', 'KPME0001', '1');

INSERT INTO `KRMS_TERM_T` (`DESC_TXT`, `TERM_ID`, `TERM_SPEC_ID`, `VER_NBR`) VALUES ('Department requires Payroll Processor approval', 'KPME0002', 'KPME0002', '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`) VALUES ('Y', 'payrollProcessorApprovalResolver', 'KPME-KRMS', 'KPME0001', 'KPME0001', 'KPME0002');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `TERM_SPEC_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0001', 'KPME0001');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0002', 'KPME0002');

INSERT INTO `KRMS_PROP_T` (`DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`) VALUES ('Department requires Payroll Processor approval', 'S', 'KPME0001', 'KPME0002');

INSERT INTO `KRMS_PROP_T` (`DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`) VALUES ('Department requires Payroll Processor approval', 'S', 'KPME0002', 'KPME0004');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('T', 'KPME0001', 'KPME0001', 'KPME0001', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('O', '=', 'KPME0001', 'KPME0002', '2');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('C', 'true', 'KPME0001', 'KPME0003', '1');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('T', 'KPME0002', 'KPME0002', 'KPME0004', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('O', '=', 'KPME0002', 'KPME0005', '2');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('C', 'true', 'KPME0002', 'KPME0006', '1');

UPDATE `KRMS_RULE_T` SET `PROP_ID` = 'KPME0001' WHERE RULE_ID = 'KPME0002';

UPDATE `KRMS_RULE_T` SET `PROP_ID` = 'KPME0002' WHERE RULE_ID = 'KPME0004';

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020900.xml::1::jjhanso::(Checksum: 3:039d6c4ef55167333627c452dc9438e1)
INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to workarea', 'LM WorkArea PeopleFlow', 'KPME-LM', 'KPME0005');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to payroll processor', 'LM Payroll Processor PeopleFlow', 'KPME-LM', 'KPME0006');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'route to workarea', 'Leave Req WorkArea PeopleFlow', 'KPME-LM', 'KPME0007');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0005', 'Route to WorkArea Approvers', 'LM WorkArea PeopleFlow', 'KPME-LM', 'KPME0005', '1', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0006', 'Route to Payroll Processors', 'LM Payroll PeopleFlow', 'KPME-LM', 'KPME0006', '2', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0007', 'Route to WorkArea Approvers', 'Leave Request WorkArea PeopleFlow', 'KPME-LM', 'KPME0007', '1', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0009', 'KPME0005', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0001');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0010', 'KPME0005', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'WorkArea PeopleFlow');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0011', 'KPME0006', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0002');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0012', 'KPME0006', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'Payroll PeopleFlow');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0013', 'KPME0007', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0001');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0014', 'KPME0007', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'WorkArea PeopleFlow');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020900.xml::4::jjhanso::(Checksum: 3:a1fc48bc245d60930f2e4a52ec5b7a3d)
--  setup Timesheet Agenda and peopleflow
INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPME-LM-CONTEXT', 'KPME context for Leave Management', 'KPME Leave Context', 'KPME-LM', 'KPME0001');

INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`) VALUES ('Y', 'KPME0003', 'KPME-LM-CONTEXT', 'KPME0005', 'KPME Leave Management Agenda');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `TERM_SPEC_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0003', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NM='payrollProcessorApproval' and NMSPC_CD = 'KPME-LM'));

INSERT INTO `KRMS_CNTXT_VLD_RULE_TYP_T` (`CNTXT_ID`, `CNTXT_VLD_RULE_ID`, `RULE_TYP_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0003', (select TYP_ID from KRMS_TYP_T where NM='Validation Rule' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Validation Action' and NMSPC_CD='KR-RULE'), 'KPME-LM-CONTEXT', 'KPME0007');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Notify PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LM-CONTEXT', 'KPME0008');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LM-CONTEXT', 'KPME0009');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0003', 'KPME0006', 'KPME0006', '1');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0003', 'KPME0005', 'KPME0005', '1');

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0006' WHERE AGENDA_ITM_ID = 'KPME0005';

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020900.xml::5::jjhanso::(Checksum: 3:b192d4de4c8e149010df5b81d155b9b0)
--  Set up Leave Request Routing
INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPME-LEAVE-REQ-CONTEXT', 'KPME context for Leave Management', 'KPME Leave Request Context', 'KPME-LM', 'KPME0001');

INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`) VALUES ('Y', 'KPME0004', 'KPME-LEAVE-REQ-CONTEXT', 'KPME0007', 'KPME Leave Request Agenda');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0004', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NM='payrollProcessorApproval' and NMSPC_CD = 'KPME-LM'));

INSERT INTO `KRMS_CNTXT_VLD_RULE_TYP_T` (`CNTXT_ID`, `CNTXT_VLD_RULE_ID`, `RULE_TYP_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0004', (select TYP_ID from KRMS_TYP_T where NM='Validation Rule' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Validation Action' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-REQ-CONTEXT', 'KPME0010');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Notify PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-REQ-CONTEXT', 'KPME0011');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-LEAVE-REQ-CONTEXT', 'KPME0012');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0004', 'KPME0007', 'KPME0007', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020930.xml::1::jjhanso::(Checksum: 3:f2d6ba6aaac935da4a488e0aae71baf2)
--  set up custom function type for KRMS and TKLM
INSERT INTO `KRMS_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`) VALUES ('Y', 'KPME Java Function Term Service', 'KPME-KRMS', 'kpmeJavaFunctionTermService', 'KPME0003');

INSERT INTO `KRMS_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`) VALUES ('Y', 'KPME Java Function Term Resolver Service', 'KPME-KRMS', '{http://kpme.kuali.org/core/v2_0}kpmeJavaFunctionTermResolverService', 'KPME0004');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020930.xml::2::jjhanso::(Checksum: 3:dc066f7caec60bf1837105a0deb20f17)
--  set up some KRMS Categories
INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0001', 'Property', 'KPME-LM', '1');

INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0002', 'Function', 'KPME-LM', '1');

INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0003', 'Property', 'KPME-TK', '1');

INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0004', 'Function', 'KPME-TK', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308020930.xml::3::jjhanso::(Checksum: 3:267ab941f4c1a4ca1aa2b93d281628e9)
--  set up custom functions
INSERT INTO `KRMS_FUNC_T` (`ACTV`, `DESC_TXT`, `FUNC_ID`, `NM`, `NMSPC_CD`, `RTRN_TYP`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Check to see if document or object contains the passed in department', 'KPME0001', 'containsDept', 'KPME-KRMS', 'java.lang.Boolean', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_FUNC_T` (`ACTV`, `DESC_TXT`, `FUNC_ID`, `NM`, `NMSPC_CD`, `RTRN_TYP`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Check to see if document or object contains the passed in work area', 'KPME0002', 'containsWorkArea', 'KPME-KRMS', 'java.lang.Boolean', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_FUNC_T` (`ACTV`, `DESC_TXT`, `FUNC_ID`, `NM`, `NMSPC_CD`, `RTRN_TYP`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Check to see if document or object contains the passed in job number', 'KPME0003', 'containsJobNumber', 'KPME-KRMS', 'java.lang.Boolean', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_FUNC_T` (`ACTV`, `DESC_TXT`, `FUNC_ID`, `NM`, `NMSPC_CD`, `RTRN_TYP`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Check to see if document or object contains the passed in task number', 'KPME0004', 'containsTask', 'KPME-KRMS', 'java.lang.Boolean', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Assignments on Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS'), 'KPME0001', 'Assignments', '1', 'org.kuali.kpme.core.api.assignment.Assignable');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Department', (select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS'), 'KPME0002', 'Department', '2', 'java.lang.String');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Assignments on Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS'), 'KPME0003', 'Assignments', '1', 'org.kuali.kpme.core.api.assignment.Assignable');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('WorkArea', (select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS'), 'KPME0004', 'WorkArea', '2', 'java.lang.Long');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Assignments on Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS'), 'KPME0005', 'Assignments', '1', 'org.kuali.kpme.core.api.assignment.Assignable');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Job Number', (select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS'), 'KPME0006', 'JobNumber', '2', 'java.lang.Long');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Assignments on Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS'), 'KPME0007', 'Assignments', '1', 'org.kuali.kpme.core.api.assignment.Assignable');

INSERT INTO `KRMS_FUNC_PARM_T` (`DESC_TXT`, `FUNC_ID`, `FUNC_PARM_ID`, `NM`, `SEQ_NO`, `TYP`) VALUES ('Task', (select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS'), 'KPME0008', 'Task', '2', 'java.lang.Long');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Department is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS'), 'KPME-LM', 'KPME0003', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Department is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS'), 'KPME-TK', 'KPME0004', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in WorkArea is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS'), 'KPME-LM', 'KPME0005', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in WorkArea is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS'), 'KPME-TK', 'KPME0006', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Job Number is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS'), 'KPME-LM', 'KPME0007', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Job Number is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS'), 'KPME-TK', 'KPME0008', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Task Number is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS'), 'KPME-LM', 'KPME0009', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'Check if passed in Task Number is valid for Object', (select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS'), 'KPME-TK', 'KPME0010', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0005', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0006', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0007', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0008', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0009', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0010', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0011', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0012', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0013', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0014', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0015', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0016', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-CAL-CONTEXT', 'KPME0017', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LM-CONTEXT', 'KPME0018', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-LEAVE-REQ-CONTEXT', 'KPME0019', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-TIMESHEET-CONTEXT', 'KPME0020', 'Y', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-LM' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-TK' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-LM' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-TK' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-LM' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-TK' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-LM' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_SPEC_CTGRY_T` (`CTGRY_ID`, `TERM_SPEC_ID`) VALUES ((select CTGRY_ID from KRMS_CTGRY_T where NMSPC_CD='KPME-TK' and NM='Function'), (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')));

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with department Resolver', 'KPME-LM', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')), 'KPME0002', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with workArea Resolver', 'KPME-LM', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')), 'KPME0003', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with jobNumber Resolver', 'KPME-LM', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')), 'KPME0004', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with task Resolver', 'KPME-LM', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-LM' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')), 'KPME0005', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with department Resolver', 'KPME-TK', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsDept' and NMSPC_CD='KPME-KRMS')), 'KPME0006', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with workArea Resolver', 'KPME-TK', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsWorkArea' and NMSPC_CD='KPME-KRMS')), 'KPME0007', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with jobNumber Resolver', 'KPME-TK', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsJobNumber' and NMSPC_CD='KPME-KRMS')), 'KPME0008', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_T` (`ACTV`, `NM`, `NMSPC_CD`, `OUTPUT_TERM_SPEC_ID`, `TERM_RSLVR_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Assignments with task Resolver', 'KPME-TK', (select TERM_SPEC_ID from KRMS_TERM_SPEC_T where NMSPC_CD='KPME-TK' and NM=(select FUNC_ID from KRMS_FUNC_T where  NM='containsTask' and NMSPC_CD='KPME-KRMS')), 'KPME0009', (select TYP_ID from KRMS_TYP_T where NM='KPME Java Function Term Resolver Service' and NMSPC_CD='KPME-KRMS'), '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('Department', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with department Resolver' and NMSPC_CD='KPME-LM'), 'KPME0001', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('WorkArea', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with workArea Resolver' and NMSPC_CD='KPME-LM'), 'KPME0002', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('JobNumber', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with jobNumber Resolver' and NMSPC_CD='KPME-LM'), 'KPME0003', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('Task', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with task Resolver' and NMSPC_CD='KPME-LM'), 'KPME0004', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('Department', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with department Resolver' and NMSPC_CD='KPME-TK'), 'KPME0005', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('WorkArea', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with workArea Resolver' and NMSPC_CD='KPME-TK'), 'KPME0006', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('JobNumber', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with jobNumber Resolver' and NMSPC_CD='KPME-TK'), 'KPME0007', '1');

INSERT INTO `KRMS_TERM_RSLVR_PARM_SPEC_T` (`NM`, `TERM_RSLVR_ID`, `TERM_RSLVR_PARM_SPEC_ID`, `VER_NBR`) VALUES ('Task', (select TERM_RSLVR_ID from KRMS_TERM_RSLVR_T where NM='Assignments with task Resolver' and NMSPC_CD='KPME-TK'), 'KPME0008', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308141600.xml::1::yingzhou::(Checksum: 3:c098e469eb5058321c5f3d482693b7e0)
--  Adding positon as a type attribute to Work Area
INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'position'), 'KPME0005', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Work Area'), UUID(), 'a', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201308291900.xml::1::vchauhan::(Checksum: 3:efe4f905c16d6a51ad96c6dc77489ab0)
--  Change Value for Documents Type
UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'OrganizationMaintenanceDocumentType' WHERE DOC_TYP_NM='ORGN' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'AccountMaintenanceDocumentType' WHERE DOC_TYP_NM='ACCT' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'SubObjectCodeMaintenanceDocumentType' WHERE DOC_TYP_NM='SOBJ'  and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'SubAccountMaintenanceDocumentType' WHERE DOC_TYP_NM='SACC' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'ProjectCodeMaintenanceDocumentType', `LBL` = 'Project Code' WHERE DOC_TYP_NM='PROJ' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'ObjectCodeMaintenanceDocumentType' WHERE DOC_TYP_NM='OBJT' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

UPDATE `KREW_DOC_TYP_T` SET `APPL_ID` = 'KPME', `DOC_TYP_NM` = 'ChartMaintenanceDocumentType' WHERE DOC_TYP_NM='COAT' and (APPL_ID IS NULL or APPL_ID NOT IN ('KFS'));

--  Changeset src/main/config/db/2.0.0/db.changelog-201309101500.xml::1::jjhanso::(Checksum: 3:b1d146d02093a6eaf186d467a47af164)
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Base', 'Create Position Base', 'KPME-HR', UUID(), 'KPME0088', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0094', 'PositionBaseMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0088', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Base', 'Edit Position Base', 'KPME-HR', UUID(), 'KPME0089', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0095', 'PositionBaseMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0089', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201309111500.xml::1::jjhanso::(Checksum: 3:4ad3a2e09caa48bcabf45e3718c12071)
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0291', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0292', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0293', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0294', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201309111700.xml::1::jjhanso::(Checksum: 3:6b2d36b8cac7163674ae281895be5745)
--  Fixing incorrect document type name for Salary Group
UPDATE `KRIM_PERM_ATTR_DATA_T` SET `ATTR_VAL` = 'SalaryGroupMaintenanceDocumentType' WHERE PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Salary Group') AND ATTR_VAL = 'SalGroupDocumentType';

UPDATE `KRIM_PERM_ATTR_DATA_T` SET `ATTR_VAL` = 'SalaryGroupMaintenanceDocumentType' WHERE PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Salary Group') AND ATTR_VAL = 'SalGroupDocumentType';

--  Changeset src/main/config/db/2.0.0/db.changelog-201309121000.xml::1::jjhanso::(Checksum: 3:1da48e8affba0993a3faba5b6c1469db)
--  Adding permissions for weekly overtime group rule
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Weekly Overtime Rule Group', 'Create Weekly Overtime Rule Group', 'KPME-TK', UUID(), 'KPME0090', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0096', 'WeeklyOvertimeRuleGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0090', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Weekly Overtime Rule Group', 'Edit Weekly Overtime Rule Group', 'KPME-TK', UUID(), 'KPME0091', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0097', 'WeeklyOvertimeRuleGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0091', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Create Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0295', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'Edit Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0296', '1');

--  Changeset src/main/config/db/2.0.0/db.changelog-201309181200.xml::1::jjhanso::(Checksum: 3:91ae8853a77be7bfc3fca88586a5bf55)
INSERT INTO `KRMS_PROP_T` (`DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`) VALUES ('Department requires Payroll Processor approval', 'S', 'KPME0003', (select RULE_ID from KRMS_RULE_T where NM='LM Payroll Processor PeopleFlow' and NMSPC_CD='KPME-LM'));

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('T', 'KPME0001', 'KPME0003', 'KPME0007', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('O', '=', 'KPME0003', 'KPME0008', '2');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`) VALUES ('C', 'true', 'KPME0003', 'KPME0009', '1');

UPDATE `KRMS_RULE_T` SET `PROP_ID` = 'KPME0003' WHERE NM = 'LM Payroll Processor PeopleFlow' AND NMSPC_CD='KPME-LM';

--  Changeset src/main/config/db/2.0.0/db.changelog-201309201200.xml::1::jjhanso::(Checksum: 3:fbd0d5f46fab73a87b97455040300752)
--  Set up base kpme document type - allows auto-ingestion to work
DELETE FROM `KREW_DOC_TYP_T`  WHERE DOC_TYP_NM = 'KpmeDocument';

INSERT INTO `KREW_DOC_TYP_T` (`ACTV_IND`, `APPL_ID`, `CUR_IND`, `DOC_HDLR_URL`, `DOC_TYP_ID`, `DOC_TYP_NM`, `DOC_TYP_VER_NBR`, `LBL`, `OBJ_ID`, `PARNT_ID`) VALUES ('1', 'KPME', '1', '${kuali.docHandler.url.prefix}/kr/maintenance.do?methodToCall=docHandler', 'KPME0001', 'KpmeDocument', '3', 'Base KPME Rule Document', UUID(), (select B.DOC_TYP_ID from KREW_DOC_TYP_T B where B.DOC_TYP_NM = 'RiceDocument' AND B.ACTV_IND = 1 AND B.CUR_IND = 1));

--  Changeset src/main/config/db/2.0.0/db.changelog-201309271200.xml::1::jjhanso::(Checksum: 3:347052843f13ad3fd5d064be80b0f2ae)
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0297', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0298', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0299', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0300', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0301', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0302', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0303', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0304', '1');
