--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/db.changelog.xml
--  Ran at: 7/14/14 8:12 AM
--  Against: krtt@localhost@jdbc:mysql://localhost/krtt
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


--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::1::jjhanso::(Checksum: 3:e9c5f83fd1977f63333fb66c95b953d6)
--  Adding Rice permission bootstrap data
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`) VALUES ('Y', 'Recall LeaveRequest Document', 'KR-WKFLW', UUID(), 'KPME0001', '68');


--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::2::jjhanso::(Checksum: 3:f73991e9fdb23584910a0e6c1ad841e9)
--  Adding Rice permission attributes
INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`) VALUES ('KPME0001', 'LeaveRequestDocument', '13', '8', UUID(), 'KPME0001');


--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::3::jjhanso::(Checksum: 3:67c41cde91baf170fa9d4c4b2c32d72a)
--  Adding Rice group bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`) VALUES ('Y', UUID(), (select PERM_ID from krim_perm_t where nmspc_cd = 'KR-WKFLW' and nm = 'Recall LeaveRequest Document'), (select role_id from krim_role_t where nmspc_cd = 'KR-WKFLW' and role_nm = 'Initiator'), 'KPME0001');


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


--  Changeset src/main/config/db/2.0.1/db.changelog-201310071000.xml::1::jkakkad::(Checksum: 3:1193e7f1ac8f28f3292609cc6dba8cf5)
--  KPME-2844:Fixing incorrect Attribute Definition for Template View KPME Record
UPDATE `KRIM_PERM_TMPL_T` SET `KIM_TYP_ID` = (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)') WHERE PERM_TMPL_ID = 'KPME0003';


--  Changeset src/main/config/db/2.0.1/db.changelog-201310071000.xml::2::jkakkad::(Checksum: 3:b4e26d56af241888d097b74eac73b0b0)
--  KPME-2844:Clearing old records of Permission View KPME Record
DELETE FROM `KRIM_ROLE_PERM_T`  WHERE PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record');

DELETE FROM `KRIM_PERM_ATTR_DATA_T`  WHERE PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record');

DELETE FROM `KRIM_PERM_T`  WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record';


--  Changeset src/main/config/db/2.0.1/db.changelog-201310071000.xml::3::jkakkad::(Checksum: 3:995c40424af0af21beb68ea44e521b7e)
--  KPME-2844:Split "View KPME Record" into separate permissions for each KPME module
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Clock Location Rule', 'View Clock Location Rule', 'KPME-TK', UUID(), 'KPME0092', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0098', 'ClockLocationRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0092', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Daily Overtime Rule', 'View Daily Overtime Rule', 'KPME-TK', UUID(), 'KPME0093', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0099', 'DailyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0093', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Department Lunch Rule', 'View Department Lunch Rule', 'KPME-TK', UUID(), 'KPME0094', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0100', 'DeptLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0094', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Grace Period Rule', 'View Grace Period Rule', 'KPME-TK', UUID(), 'KPME0095', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0101', 'GracePeriodRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0095', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Shift Differential Rule', 'View Shift Differential Rule', 'KPME-TK', UUID(), 'KPME0096', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0102', 'ShiftDifferentialRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0096', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a System Lunch Rule', 'View System Lunch Rule', 'KPME-TK', UUID(), 'KPME0097', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0103', 'SystemLunchRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0097', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Time Collection Rule', 'View Time Collection Rule', 'KPME-TK', UUID(), 'KPME0098', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0104', 'TimeCollectionRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0098', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Weekly Overtime Rule', 'View Weekly Overtime Rule', 'KPME-TK', UUID(), 'KPME0099', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0105', 'WeeklyOvertimeRuleDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0099', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Weekly Overtime Rule Group', 'View Weekly Overtime Rule Group', 'KPME-TK', UUID(), 'KPME0108', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0114', 'WeeklyOvertimeRuleGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0108', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Accrual Category', 'View Accrual Category', 'KPME-LM', UUID(), 'KPME0100', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0106', 'AccrualCategoryDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0100', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Balance Transfer', 'View Balance Transfer', 'KPME-LM', UUID(), 'KPME0101', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0107', 'BalanceTransferDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0101', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Employee Override', 'View Employee Override', 'KPME-LM', UUID(), 'KPME0102', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0108', 'EmployeeOverrideDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0102', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Adjustment', 'View Leave Adjustment', 'KPME-LM', UUID(), 'KPME0103', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0109', 'LeaveAdjustmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0103', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Donation', 'View Leave Donation', 'KPME-LM', UUID(), 'KPME0104', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0110', 'LeaveDonationDocument', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0104', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Payout', 'View Leave Payout', 'KPME-LM', UUID(), 'KPME0105', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0111', 'LeavePayoutDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0105', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Leave Plan', 'View Leave Plan', 'KPME-LM', UUID(), 'KPME0106', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0112', 'LeavePlanDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0106', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a System Scheduled Time Off', 'View System Scheduled Time Off', 'KPME-LM', UUID(), 'KPME0107', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0113', 'SystemScheduledTimeOffDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0107', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view an Assignment', 'View Assignment', 'KPME-HR', UUID(), 'KPME0109', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0115', 'AssignmentDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0109', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Calendar', 'View Calendar', 'KPME-HR', UUID(), 'KPME0110', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0116', 'PayCalendarDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0110', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Calendar Entry', 'View Calendar Entry', 'KPME-HR', UUID(), 'KPME0111', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0117', 'PayCalendarEntriesDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0111', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Department', 'View Department', 'KPME-HR', UUID(), 'KPME0112', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0118', 'DepartmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0112', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view an Earn Code', 'View Earn Code', 'KPME-HR', UUID(), 'KPME0113', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0119', 'EarnCodeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0113', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view an Earn Code Group', 'View Earn Code Group', 'KPME-HR', UUID(), 'KPME0114', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0120', 'EarnCodeGroupDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0114', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view an Earn Code Security', 'View Earn Code Security', 'KPME-HR', UUID(), 'KPME0115', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0121', 'EarnCodeSecurityDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0115', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Job', 'View Job', 'KPME-HR', UUID(), 'KPME0116', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0122', 'JobMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0116', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Location', 'View Location', 'KPME-HR', UUID(), 'KPME0117', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0123', 'LocationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0117', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Pay Grade', 'View Pay Grade', 'KPME-HR', UUID(), 'KPME0118', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0124', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0118', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Pay Type', 'View Pay Type', 'KPME-HR', UUID(), 'KPME0119', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0125', 'PayTypeDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0119', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position', 'View Position', 'KPME-HR', UUID(), 'KPME0120', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0126', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0120', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Principal HR Attribute', 'View Principal HR Attribute', 'KPME-HR', UUID(), 'KPME0121', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0127', 'PrincipalHRAttributesMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0121', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Salary Group', 'View Salary Group', 'KPME-HR', UUID(), 'KPME0122', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0128', 'SalaryGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0122', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Work Area', 'View Work Area', 'KPME-HR', UUID(), 'KPME0123', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0129', 'WorkAreaMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0123', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Base', 'View Position Base', 'KPME-HR', UUID(), 'KPME0124', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0130', 'PositionBaseMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0124', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201310151300.xml::1::jkakkad::(Checksum: 3:7c87222346058f69474249836d1141ce)
--  KPME-2844:Split "View KPME Record" into separate permissions for each KPME module
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0305', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0306', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0307', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0308', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0309', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Clock Location Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0310', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0311', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0312', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0313', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0314', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0315', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Daily Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0316', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0317', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0318', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0319', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0320', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0321', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Department Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0322', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0323', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0324', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0325', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0326', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0327', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Grace Period Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0328', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0329', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0330', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0331', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0332', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0333', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Shift Differential Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0334', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0335', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0336', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0337', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0338', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0339', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View System Lunch Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0340', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0341', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0342', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0343', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0344', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0345', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Time Collection Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0346', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0347', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0348', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0349', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0350', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0351', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0352', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0353', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0354', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0355', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0356', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0357', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Weekly Overtime Rule Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0358', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201310151500.xml::1::jkakkad::(Checksum: 3:c5c4e6b673af9e0708c4799ac033d5e4)
--  KPME-2844:Split "View KPME Record" into separate permissions for each KPME module
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0359', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0360', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0361', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0362', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0363', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Accrual Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0364', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0365', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0366', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0367', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0368', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0369', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Balance Transfer'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0370', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0371', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0372', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0373', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0374', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0375', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Employee Override'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0376', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0377', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0378', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0379', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0380', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0381', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Adjustment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0382', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0383', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0384', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0385', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0386', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0387', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Donation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0388', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0389', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0390', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0391', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0392', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0393', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Payout'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0394', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0395', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0396', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0397', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0398', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0399', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View Leave Plan'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0400', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0401', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0402', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0403', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0404', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0405', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-LM' AND NM = 'View System Scheduled Time Off'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0406', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201310151700.xml::1::jkakkad::(Checksum: 3:66236917bffc618f899a7edd97f56e24)
--  KPME-2844:Split "View KPME Record" into separate permissions for each KPME module
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0407', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0408', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0409', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0410', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0411', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0412', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0413', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0414', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0415', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0416', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0417', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Assignment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0418', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0419', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0420', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0421', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0422', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0423', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0424', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0425', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0426', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0427', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0428', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0429', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0430', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0431', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0432', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0433', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0434', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0435', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0436', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0437', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0438', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0439', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0440', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0441', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Calendar Entry'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0442', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0443', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0444', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0445', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0446', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0447', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0448', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0449', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0450', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0451', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0452', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0453', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Department'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0454', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0455', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0456', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0457', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0458', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0459', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0460', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0461', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0462', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0463', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0464', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0465', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0466', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0467', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0468', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0469', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0470', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0471', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0472', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0473', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0474', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0475', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0476', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0477', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0478', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0479', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0480', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0481', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0482', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0483', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0484', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0485', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0486', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0487', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0488', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0489', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Earn Code Security'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0490', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0491', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0492', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0493', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0494', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0495', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0496', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0497', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0498', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0499', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0500', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0501', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Job'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0502', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0503', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0504', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0505', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0506', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0507', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0508', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0509', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0510', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0511', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0512', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0513', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Location'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0514', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0515', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0516', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0517', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0518', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0519', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0520', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0521', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0522', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0523', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0524', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0525', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0526', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0527', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0528', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0529', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0530', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0531', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0532', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0533', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0534', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0535', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0536', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0537', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Pay Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0538', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0539', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0540', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0541', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0542', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0543', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0544', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0545', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0546', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0547', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0548', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0549', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0550', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0551', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0552', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0553', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0554', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0555', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0556', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0557', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0558', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0559', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0560', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0561', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Principal HR Attribute'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0562', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0563', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0564', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0565', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0566', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0567', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0568', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0569', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0570', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0571', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0572', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0573', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0574', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0575', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0576', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0577', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0578', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0579', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0580', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0581', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0582', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0583', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0584', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0585', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Work Area'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0586', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0587', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0588', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0589', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0590', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0591', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0592', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0593', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0594', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0595', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0596', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0597', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position Base'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0598', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201401021250.xml::1::jwillia::(Checksum: 3:35374c48de78128eea1400e8e4bad66c)
INSERT INTO `KRCR_CMPNT_T` (`ACTV_IND`, `CMPNT_CD`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KeyValues', 'KeyValues', 'KPME-HR', 'uuid()', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201401021250.xml::2::jwillia::(Checksum: 3:7f491801f1f45c65e607e451e81011c4)
INSERT INTO `KRCR_PARM_T` (`APPL_ID`, `CMPNT_CD`, `EVAL_OPRTR_CD`, `NMSPC_CD`, `OBJ_ID`, `PARM_DESC_TXT`, `PARM_NM`, `PARM_TYP_CD`, `VAL`, `VER_NBR`) VALUES ('KPME', 'KeyValues', 'A', 'KPME-HR', 'uuid()', 'A semicolon delimited list of timezones for timezone dropdown lists.', 'TIME_ZONES', 'VALID', 'America/Chicago;America/Denver;America/Detroit;America/Indiana/Indianapolis;America/Phoenix;America/Los_Angeles', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201401151035.xml::1::neerajsk::(Checksum: 3:b458706dfe70fea06c3421e3a462e7b0)
--  Adding Rice KIM type + KIM role bootstrap data for the KPME role-proxy derived role
INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0005', 'Derived Role: KPME Role Proxy', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}kpmeRoleProxyDerivedRoleTypeService', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The derived role that acts as a KPME role proxy', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Derived Role: KPME Role Proxy'), NOW(), 'KPME-HR', UUID(), 'KPME0019', 'Derived Role : KPME Role Proxy', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201401241035.xml::1::neerajsk::(Checksum: 3:3b9a3b8fc1c5704d979dd82e16fd6c04)
--  Adding Rice KIM type + KIM role data for the Approver Proxy and Approver Delegate Proxy derived roles
INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0006', 'Derived Role: Approver Proxy', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}approverProxyDerivedRoleTypeService', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0007', 'Derived Role: Approver Delegate Proxy', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}approverDelegateProxyDerivedRoleTypeService', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The derived role that acts as a proxy for the Approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Derived Role: Approver Proxy'), NOW(), 'KPME-HR', UUID(), 'KPME0020', 'Derived Role: Approver Proxy', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The derived role that acts as a proxy for the Approver Delegate role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Derived Role: Approver Delegate Proxy'), NOW(), 'KPME-HR', UUID(), 'KPME0021', 'Derived Role: Approver Delegate Proxy', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201312181700.xml::1::neerajsk::(Checksum: 3:5ade087d04494d47476d50cf52acd948)
--  Set up base kpme effective date maintenance document type - allows auto-ingestion to work
DELETE FROM `KREW_DOC_TYP_T`  WHERE DOC_TYP_NM = 'KpmeEffectiveDateMaintenanceDocumentType';

INSERT INTO `KREW_DOC_TYP_T` (`ACTV_IND`, `APPL_ID`, `CUR_IND`, `DOC_HDLR_URL`, `DOC_TYP_ID`, `DOC_TYP_NM`, `DOC_TYP_VER_NBR`, `LBL`, `OBJ_ID`, `PARNT_ID`) VALUES ('1', 'KPME', '1', '${kuali.docHandler.url.prefix}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=docHandler', 'KPME0002', 'KpmeEffectiveDateMaintenanceDocumentType', '3', 'KPME Effective Date Maintenance Document', UUID(), (select B.DOC_TYP_ID from KREW_DOC_TYP_T B where B.DOC_TYP_NM = 'KpmeDocument' AND B.ACTV_IND = 1 AND B.CUR_IND = 1));


--  Changeset src/main/config/db/2.1.0/db.changelog-201402210800.xml::1::tkagata::(Checksum: 3:3d67bb1a5786c0e32f5e67416b018a66)
--  KPME-3198: Adding Position Management Namespace
INSERT INTO `KRCR_NMSPC_T` (`ACTV_IND`, `APPL_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KPME', 'KPME Position Management', 'KPME-PM', UUID(), '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402210800.xml::2::tkagata::(Checksum: 3:f4c39b0bdff9c3f6cdb685410b8b9caa)
--  KPME-3198: Adding KIM Attribute Definitions
INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.kpme.core.KPMEAttributes', 'KPME0005', 'institution', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.kpme.core.KPMEAttributes', 'KPME0006', 'organization', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.kpme.core.KPMEAttributes', 'KPME0007', 'positionType', 'KPME-WKFLW', UUID(), '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402210800.xml::3::tkagata::(Checksum: 3:94614d62eb45699f3e5eacffee26e67b)
--  KPME-3198: Adding KIM Types
INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0008', 'Institution', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}institutionRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'institution'), 'KPME0006', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0009', 'Organization', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}organizationRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'organization'), 'KPME0007', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Organization'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0010', 'Institution & Position Type', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}institutionPositionTypeRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'institution'), 'KPME0008', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution & Position Type'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'positionType'), 'KPME0009', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution & Position Type'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0011', 'Location & Position Type', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}locationPositionTypeRoleTypeService', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'location'), 'KPME0010', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location & Position Type'), UUID(), 'a', '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'positionType'), 'KPME0011', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location & Position Type'), UUID(), 'a', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402210800.xml::4::tkagata::(Checksum: 3:e2835dd29fe37acba74ba29e5bed4688)
--  KPME-3198: Adding KIM Roles
INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system administrator role for the Position Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-PM', UUID(), 'KPME0022', 'Position System Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The system view only role for the Position Management system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'), NOW(), 'KPME-PM', UUID(), 'KPME0023', 'Position System View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution administrator role for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), NOW(), 'KPME-HR', UUID(), 'KPME0024', 'KOHR Institution Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution administrator role for academic position types for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution & Position Type'), NOW(), 'KPME-HR', UUID(), 'KPME0025', 'KOHR Academic HR Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution view only role for the the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), NOW(), 'KPME-HR', UUID(), 'KPME0026', 'KOHR Institution View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location administrator role for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-HR', UUID(), 'KPME0027', 'KOHR Location Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location view only role for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-HR', UUID(), 'KPME0028', 'KOHR Location View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The organization administrator role for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Organization'), NOW(), 'KPME-HR', UUID(), 'KPME0029', 'KOHR Organization Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The organization view only role for the KOHR system', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Organization'), NOW(), 'KPME-HR', UUID(), 'KPME0030', 'KOHR Organization View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department human resources administrator role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0031', 'HR Department Administrator', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department human resources view only role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0032', 'HR Department View Only', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution human resources approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), NOW(), 'KPME-HR', UUID(), 'KPME0033', 'HR Institution Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution academic human resources approver', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution & Position Type'), NOW(), 'KPME-HR', UUID(), 'KPME0034', 'Academic HR Institution Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution budget office approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), NOW(), 'KPME-HR', UUID(), 'KPME0035', 'Budget Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The institution payroll approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Institution'), NOW(), 'KPME-HR', UUID(), 'KPME0036', 'Payroll Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The loation human resources approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-HR', UUID(), 'KPME0037', 'HR Location Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The location academic human resources approver', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location & Position Type'), NOW(), 'KPME-HR', UUID(), 'KPME0038', 'Academic HR Location Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The loation academic fiscal approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Location'), NOW(), 'KPME-HR', UUID(), 'KPME0039', 'Fiscal Location Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The organization human resources approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Organization'), NOW(), 'KPME-HR', UUID(), 'KPME0040', 'HR Organization Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The organization fiscal approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Organization'), NOW(), 'KPME-HR', UUID(), 'KPME0041', 'Fiscal Organization Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0042', 'Department Approver', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The department fiscal approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), NOW(), 'KPME-HR', UUID(), 'KPME0043', 'Fiscal Department Approver', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402210800.xml::5::tkagata::(Checksum: 3:f4807dac390883dedff3cc117a5fe38e)
--  KPME-3198: Adding Global System Admin/View Only group to Position Management System Admin/View Only role
INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System Administrator'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0005', '1');

INSERT INTO `KRIM_ROLE_MBR_T` (`LAST_UPDT_DT`, `MBR_ID`, `MBR_TYP_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_MBR_ID`, `VER_NBR`) VALUES (NOW(), (SELECT GRP_ID FROM KRIM_GRP_T WHERE NMSPC_CD = 'KPME-HR' AND GRP_NM = 'System View Only'), 'G', UUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0006', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402261300.xml::1::tkagata::(Checksum: 3:71b038b9f25bbf7a9e72cc5958ab95ff)
--  KPME-3198: Adding KIM permissions for Maintenance Documents
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Classification', 'Create Position Classification', 'KPME-PM', UUID(), 'KPME0125', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0131', 'ClassificationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0125', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Classification', 'Edit Position Classification', 'KPME-PM', UUID(), 'KPME0126', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0132', 'ClassificationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0126', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Classification', 'View Position Classification', 'KPME-PM', UUID(), 'KPME0127', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0133', 'ClassificationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0127', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Salary Group', 'Create Salary Group', 'KPME-PM', UUID(), 'KPME0128', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0134', 'SalaryGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0128', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Salary Group', 'Edit Salary Group', 'KPME-PM', UUID(), 'KPME0129', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0135', 'SalaryGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0129', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Salary Group', 'View Salary Group', 'KPME-PM', UUID(), 'KPME0130', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0136', 'SalaryGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0130', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Pay Step', 'Create Pay Step', 'KPME-PM', UUID(), 'KPME0131', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0137', 'PayStepMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0131', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Pay Step', 'Edit Pay Step', 'KPME-PM', UUID(), 'KPME0132', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0138', 'PayStepMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0132', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Pay Step', 'View Pay Step', 'KPME-PM', UUID(), 'KPME0133', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0139', 'PayStepMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0133', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Pay Grade', 'Create Pay Grade', 'KPME-PM', UUID(), 'KPME0134', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0140', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0134', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Pay Grade', 'Edit Pay Grade', 'KPME-PM', UUID(), 'KPME0135', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0141', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0135', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Pay Grade', 'View Pay Grade', 'KPME-PM', UUID(), 'KPME0136', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0142', 'PayGradeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0136', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402261300.xml::2::tkagata::(Checksum: 3:c40c1549ea89f545934acbd4eaa9c454)
--  KPME-3198: Adding KIM permissions for Setup Documents
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Appointment', 'Create Position Appointment', 'KPME-PM', UUID(), 'KPME0137', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0143', 'PositionAppointmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0137', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Appointment', 'Edit Position Appointment', 'KPME-PM', UUID(), 'KPME0138', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0144', 'PositionAppointmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0138', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Appointment', 'View Position Appointment', 'KPME-PM', UUID(), 'KPME0139', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0145', 'PositionAppointmentMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0139', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Responsibility Option', 'Create Position Responsibility Option', 'KPME-PM', UUID(), 'KPME0140', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0146', 'PositionResponsibilityOptionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0140', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Responsibility Option', 'Edit Position Responsibility Option', 'KPME-PM', UUID(), 'KPME0141', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0147', 'PositionResponsibilityOptionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0141', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Responsibility Option', 'View Position Responsibility Option', 'KPME-PM', UUID(), 'KPME0142', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0148', 'PositionResponsibilityOptionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0142', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Contract Type', 'Create Position Contract Type', 'KPME-PM', UUID(), 'KPME0143', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0149', 'PstnContractTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0143', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Contract Type', 'Edit Position Contract Type', 'KPME-PM', UUID(), 'KPME0144', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0150', 'PstnContractTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0144', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Contract Type', 'View Position Contract Type', 'KPME-PM', UUID(), 'KPME0145', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0151', 'PstnContractTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0145', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Type', 'Create Position Type', 'KPME-PM', UUID(), 'KPME0146', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0152', 'PositionTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0146', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Type', 'Edit Position Type', 'KPME-PM', UUID(), 'KPME0147', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0153', 'PositionTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0147', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Type', 'View Position Type', 'KPME-PM', UUID(), 'KPME0148', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0154', 'PositionTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0148', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Flag', 'Create Position Flag', 'KPME-PM', UUID(), 'KPME0149', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0155', 'PositionFlagMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0149', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Flag', 'Edit Position Flag', 'KPME-PM', UUID(), 'KPME0150', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0156', 'PositionFlagMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0150', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Flag', 'View Position Flag', 'KPME-PM', UUID(), 'KPME0151', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0157', 'PositionFlagMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0151', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Qualifier Type', 'Create Position Qualifier Type', 'KPME-PM', UUID(), 'KPME0152', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0158', 'PstnQlfrTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0152', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Qualifier Type', 'Edit Position Qualifier Type', 'KPME-PM', UUID(), 'KPME0153', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0159', 'PstnQlfrTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0153', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Qualifier Type', 'View Position Qualifier Type', 'KPME-PM', UUID(), 'KPME0154', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0160', 'PstnQlfrTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0154', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402261300.xml::3::tkagata::(Checksum: 3:0b3ffba1a596b10b9ab84a1ce05eeeaf)
--  KPME-3198: Adding KIM permissions for Report Documents
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Report Group', 'Create Position Report Group', 'KPME-PM', UUID(), 'KPME0155', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0161', 'PositionReportGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0155', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Report Group', 'Edit Position Report Group', 'KPME-PM', UUID(), 'KPME0156', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0162', 'PositionReportGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0156', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Report Group', 'View Position Report Group', 'KPME-PM', UUID(), 'KPME0157', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0163', 'PositionReportGroupMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0157', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Report Group Sub Category', 'Create Position Report Group Sub Category', 'KPME-PM', UUID(), 'KPME0158', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0164', 'PstnRptGrpSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0158', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Report Group Sub Category', 'Edit Position Report Group Sub Category', 'KPME-PM', UUID(), 'KPME0159', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0165', 'PstnRptGrpSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0159', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Report Group Sub Category', 'View Position Report Group Sub Category', 'KPME-PM', UUID(), 'KPME0160', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0166', 'PstnRptGrpSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0160', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Report Sub Category', 'Create Position Report Sub Category', 'KPME-PM', UUID(), 'KPME0161', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0167', 'PositionReportSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0161', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Report Sub Category', 'Edit Position Report Sub Category', 'KPME-PM', UUID(), 'KPME0162', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0168', 'PositionReportSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0162', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Report Sub Category', 'View Position Report Sub Category', 'KPME-PM', UUID(), 'KPME0163', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0169', 'PositionReportSubCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0163', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Report Category', 'Create Position Report Category', 'KPME-PM', UUID(), 'KPME0164', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0170', 'PositionReportCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0164', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Report Category', 'Edit Position Report Category', 'KPME-PM', UUID(), 'KPME0165', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0171', 'PositionReportCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0165', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Report Category', 'View Position Report Category', 'KPME-PM', UUID(), 'KPME0166', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0172', 'PositionReportCatMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0166', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position Report Type', 'Create Position Report Type', 'KPME-PM', UUID(), 'KPME0167', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0173', 'PositionReportTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0167', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Position Report Type', 'Edit Position Report Type', 'KPME-PM', UUID(), 'KPME0168', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0174', 'PositionReportTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0168', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position Report Type', 'View Position Report Type', 'KPME-PM', UUID(), 'KPME0169', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0175', 'PositionReportTypeMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0169', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402261300.xml::4::tkagata::(Checksum: 3:594ece29e0e5151f9b5abde7f787b906)
--  KPME-3198: Adding KIM permissions for Position Maintenance document
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Position', 'Create Position', 'KPME-PM', UUID(), 'KPME0170', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0176', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0170', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a saved Position', 'Edit Saved Position', 'KPME-PM', UUID(), 'KPME0171', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0177', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0171', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0178', 'S', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0171', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an enroute Position', 'Edit Enroute Position', 'KPME-PM', UUID(), 'KPME0172', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0179', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0172', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0180', 'R', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0172', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a final Position', 'Edit Final Position', 'KPME-PM', UUID(), 'KPME0173', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-NS' AND NM = 'Edit Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0181', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0173', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0182', 'F', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'routeStatusCode'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0173', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows super users to administer a Position', 'Super User Administer Position', 'KPME-PM', UUID(), 'KPME0174', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Administer Routing for Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0183', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0174', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Position', 'View Position', 'KPME-PM', UUID(), 'KPME0175', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0184', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0175', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271100.xml::1::tkagata::(Checksum: 3:e845dda3e632deb05a4eba2058050dd7)
--  KPME-3198: Assigning permissions to system admin role
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Assign Role'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0600', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Permission'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0601', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Grant Responsibility'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0602', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271100.xml::2::tkagata::(Checksum: 3:b701b889ed3e6c987f22dcdc4be3dcd2)
--  KPME-3198: Assigning permissions to roles for Maintenance Documents
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0603', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0604', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0605', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0606', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0607', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0608', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0609', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0610', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0611', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0612', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0613', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0614', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization View Only'), 'KPME0615', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0616', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Classification'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department View Only'), 'KPME0617', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0618', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0619', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0620', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0621', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0622', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0623', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0624', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0625', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0626', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0627', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization View Only'), 'KPME0628', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0629', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Salary Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department View Only'), 'KPME0630', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0631', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0632', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0633', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0634', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0635', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0636', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0637', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0638', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0639', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0640', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization View Only'), 'KPME0641', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0642', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Step'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department View Only'), 'KPME0643', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0644', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0645', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0646', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0647', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0648', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0649', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0650', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0651', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0652', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0653', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization View Only'), 'KPME0654', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0655', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Pay Grade'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department View Only'), 'KPME0656', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271100.xml::3::tkagata::(Checksum: 3:54762ac8c6d6d245c9268891175616a5)
--  KPME-3198: Assigning permissions to roles for Setup Documents
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0657', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0658', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0659', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0660', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0661', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0662', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0663', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0664', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0665', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Appointment'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0666', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0667', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0668', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0669', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0670', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0671', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0672', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0673', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0674', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0675', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Responsibility Option'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0676', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0677', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0678', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0679', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0680', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0681', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0682', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0683', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0684', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0685', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Contract Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0686', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0687', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0688', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0689', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0690', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0691', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0692', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0693', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0694', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0695', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0696', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0697', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0698', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0699', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0700', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0701', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0702', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0703', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0704', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0705', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Flag'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0706', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0707', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0708', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0709', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0710', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0711', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0712', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0713', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0714', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0715', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Qualifier Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0716', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271100.xml::4::tkagata::(Checksum: 3:333b37be258188ce0284228f7db084a1)
--  KPME-3198: Assigning permissions to roles for Report Documents
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0717', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0718', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0719', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0720', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0721', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0722', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0723', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0724', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0725', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0726', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0727', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0728', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0729', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0730', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0731', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0732', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0733', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Group Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0734', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0735', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0736', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0737', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0738', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0739', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0740', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0741', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Sub Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0742', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0743', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0744', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0745', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0746', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0747', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0748', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0749', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Category'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0750', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0751', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0752', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0753', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0754', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0755', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0756', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0757', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position Report Type'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0758', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::1::tkagata::(Checksum: 3:c64c574855f2c021d039bc62013c1879)
--  KPME-3198: Assigning view permission to roles for Position Maintenance Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0759', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System View Only'), 'KPME0760', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0761', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0762', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0763', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0764', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0765', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0766', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0767', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0768', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0769', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0770', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0771', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Location Approver'), 'KPME0772', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0773', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Organization Approver'), 'KPME0774', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0775', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Department Approver'), 'KPME0776', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::2::tkagata::(Checksum: 3:b4f105e0157a5b37fb212f9914177dda)
--  KPME-3198: Assigning edit saved permission to roles for Position Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0777', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0778', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0779', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0780', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0781', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0782', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0783', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0784', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0785', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0786', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0787', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0788', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Location Approver'), 'KPME0789', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0790', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Organization Approver'), 'KPME0791', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0792', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Saved Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Department Approver'), 'KPME0793', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::3::tkagata::(Checksum: 3:df2d4003aac04234a26fa0e20aef9abf)
--  KPME-3198: Assigning edit enroute permission to roles for Position Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0794', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0795', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0796', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0797', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0798', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0799', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0800', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0801', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0802', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0803', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0804', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Location Approver'), 'KPME0805', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0806', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Organization Approver'), 'KPME0807', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Enroute Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Department Approver'), 'KPME0808', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::4::tkagata::(Checksum: 3:6d7b23d1d010b58256466832cc9b92a2)
--  KPME-3198: Assigning edit final permission to roles for Position Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Edit Final Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0809', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::5::tkagata::(Checksum: 3:de437724fee2131fa4e9b3997b8e8fa7)
--  KPME-3198: Assigning create permission to roles for Position Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0810', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0811', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0812', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0813', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0814', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0815', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0816', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0817', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0818', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0819', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0820', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0821', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0822', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0823', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201402271400.xml::6::tkagata::(Checksum: 3:bc7c8cb39dbdce74e5f6702bc1481cfe)
--  KPME-3198: Assigning super user permission to roles for Position Document
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Super User Administer Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0824', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Super User Administer Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0825', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Super User Administer Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0826', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::1::jwillia::(Checksum: 3:1d9220379e9de889211706462166352d)
--  KPME-2462: Create the KPME Position Management Context
INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'KPME-PM-CONTEXT', 'KPME context for Position Management', 'KPME Position Management Context', 'KPME-PM', 'KPME0001', '0');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::2::jwillia::(Checksum: 3:2460b7f4052e80fdbf9d12dfa8941fec)
--  KPME-2462: Create KRMS Categories of the KPME-PM name space
INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0005', 'Property', 'KPME-PM', '1');

INSERT INTO `KRMS_CTGRY_T` (`CTGRY_ID`, `NM`, `NMSPC_CD`, `VER_NBR`) VALUES ('KPME0006', 'Function', 'KPME-PM', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::3::jwillia::(Checksum: 3:7eee3526e97816a92822a5055309dc99)
--  KPME-2462: Set Valid Actions for the KPME-PM name space
INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`, `VER_NBR`) VALUES ('1000', 'KPME-PM-CONTEXT', 'KPME0013', '0');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`, `VER_NBR`) VALUES ('1001', 'KPME-PM-CONTEXT', 'KPME0014', '0');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`, `VER_NBR`) VALUES ('1003', 'KPME-PM-CONTEXT', 'KPME0015', '0');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::4::jwillia::(Checksum: 3:53cc5898f96b96e5c7d887b0c6e4f857)
--  KPME-2462: Set term specification for the KPME-PM namespace
INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'is the position type flagged as academic', 'academicFlag', 'KPME-PM', 'KPME0011', 'java.lang.Boolean', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'process used when editting the Position Document', 'positionProcess', 'KPME-PM', 'KPME0012', 'java.lang.String', '1');

INSERT INTO `KRMS_TERM_SPEC_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TERM_SPEC_ID`, `TYP`, `VER_NBR`) VALUES ('Y', 'did the Primary Department change', 'primaryDepartmentChanged', 'KPME-PM', 'KPME0013', 'java.lang.Boolean', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::5::jwillia::(Checksum: 3:8b9f30df68349216d253095857db6472)
--  KPME-2462: Set the terms fro the KPME-PM namespace
INSERT INTO `KRMS_TERM_T` (`DESC_TXT`, `TERM_ID`, `TERM_SPEC_ID`, `VER_NBR`) VALUES ('Position Type is Flagged as Academic', 'KPME0003', 'KPME0011', '1');

INSERT INTO `KRMS_TERM_T` (`DESC_TXT`, `TERM_ID`, `TERM_SPEC_ID`, `VER_NBR`) VALUES ('Which Process was used when editting the position Document', 'KPME0004', 'KPME0012', '1');

INSERT INTO `KRMS_TERM_T` (`DESC_TXT`, `TERM_ID`, `TERM_SPEC_ID`, `VER_NBR`) VALUES ('Primary Department changed', 'KPME0005', 'KPME0013', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::6::jwillia::(Checksum: 3:d6d4cbbf7d4a56b7546fd211f2bc49d5)
--  KPME-2462: Set the valid term specifications for the KPME-PM-CONTEXT
INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-PM-CONTEXT', 'KPME0021', 'N', 'KPME0011');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-PM-CONTEXT', 'KPME0022', 'N', 'KPME0012');

INSERT INTO `KRMS_CNTXT_VLD_TERM_SPEC_T` (`CNTXT_ID`, `CNTXT_TERM_SPEC_PREREQ_ID`, `PREREQ`, `TERM_SPEC_ID`) VALUES ('KPME-PM-CONTEXT', 'KPME0023', 'N', 'KPME0013');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::7::jwillia::(Checksum: 3:6c5cca04b828a3948101da9ce7457d0a)
--  KPME-2462: Create Kew People Flow Types
INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Primary Department Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionPrimaryDepartmentPeopleFlowTypeService', 'KPME0003', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Additional Departments Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionAdditionalDepartmentsPeopleFlowTypeService', 'KPME0004', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Previous Department Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionPreviousDepartmentPeopleFlowTypeService', 'KPME0005', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Postion Additional Organizations Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionAdditionalOrganizationsPeopleFlowTypeService', 'KPME0006', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Organizations Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionOrganizationPeopleFlowTypeService', 'KPME0007', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Location Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionLocationPeopleFlowTypeService', 'KPME0008', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Institution Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionInstitutionPeopleFlowTypeService', 'KPME0009', '0');

INSERT INTO `KREW_TYP_T` (`ACTV`, `NM`, `NMSPC_CD`, `SRVC_NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Initiator Type', 'KPME-PM', '{http://kpme.kuali.org/pm/v2_0}positionInitiatorPeopleFlowTypeService', 'KPME0010', '0');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::8::jwillia::(Checksum: 3:b382c2bb84de771f38086eaaf94bea41)
--  KPME-2462: Create PeopleFlows used by the Positon Management KRMS Agenda
INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Previous Hr Department Admin People Flow', 'Position Previous Hr Department Admin People Flow', 'KPME-WKFLW', 'KPME0003', 'KPME0005', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0031', 'R', 'KPME0003', 'KPME0003', '1', 'KPME0005', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Additional Hr Department Admins People Flow', 'Position Additional Hr Department Admins People Flow', 'KPME-WKFLW', 'KPME0004', 'KPME0004', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0031', 'R', 'KPME0004', 'KPME0004', '1', 'KPME0006', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Primary Hr Department Admin People Flow', 'Position Primary Hr Department Admin People Flow', 'KPME-WKFLW', 'KPME0005', 'KPME0003', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0031', 'R', 'KPME0005', 'KPME0005', '1', 'KPME0007', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Previous Fiscal Department Approver People Flow', 'Position Previous Fiscal Department Approver People Flow', 'KPME-WKFLW', 'KPME0006', 'KPME0005', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0043', 'R', 'KPME0006', 'KPME0006', '1', 'KPME0008', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Additional Fiscal Department Approvers People Flow', 'Position Additional Fiscal Department Approvers People Flow', 'KPME-WKFLW', 'KPME0007', 'KPME0004', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0043', 'R', 'KPME0007', 'KPME0007', '1', 'KPME0009', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Primary Fiscal Department Approver People Flow', 'Position Primary Fiscal Department Approver People Flow', 'KPME-WKFLW', 'KPME0008', 'KPME0003', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0043', 'R', 'KPME0008', 'KPME0008', '1', 'KPME0010', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Previous Department Approver People Flow', 'Position Previous Department Approver People Flow', 'KPME-WKFLW', 'KPME0009', 'KPME0005', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0042', 'R', 'KPME0009', 'KPME0009', '1', 'KPME0011', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Primary Department Approver People Flow', 'Position Primary Department Approver People Flow', 'KPME-WKFLW', 'KPME0010', 'KPME0003', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0042', 'R', 'KPME0010', 'KPME0010', '1', 'KPME0012', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Additional Organizations People Flow', 'Position Additional Organizations People Flow', 'KPME-WKFLW', 'KPME0011', 'KPME0006', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('A', 'KPME0040', 'R', 'KPME0011', 'KPME0011', '1', 'KPME0013', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Organization People Flow', 'Position Organization People Flow', 'KPME-WKFLW', 'KPME0012', 'KPME0007', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0040', 'R', 'KPME0012', 'KPME0012', '1', 'KPME0014', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0041', 'R', 'KPME0012', 'KPME0013', '2', 'KPME0015', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Academic Location People Flow', 'Position Academic Location People Flow', 'KPME-WKFLW', 'KPME0013', 'KPME0008', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0038', 'R', 'KPME0013', 'KPME0014', '1', 'KPME0016', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0039', 'R', 'KPME0013', 'KPME0015', '2', 'KPME0017', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Academic Institution People Flow', 'Position Academic Institution People Flow', 'KPME-WKFLW', 'KPME0014', 'KPME0009', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0035', 'R', 'KPME0014', 'KPME0016', '1', 'KPME0018', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0036', 'R', 'KPME0014', 'KPME0017', '2', 'KPME0019', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0034', 'R', 'KPME0014', 'KPME0018', '3', 'KPME0020', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Location People Flow', 'Position Location People Flow', 'KPME-WKFLW', 'KPME0015', 'KPME0008', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0037', 'R', 'KPME0015', 'KPME0019', '1', 'KPME0021', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0039', 'R', 'KPME0015', 'KPME0020', '2', 'KPME0022', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Position Institution People Flow', 'Position Institution People Flow', 'KPME-WKFLW', 'KPME0016', 'KPME0009', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0035', 'R', 'KPME0016', 'KPME0021', '1', 'KPME0023', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0036', 'R', 'KPME0016', 'KPME0022', '2', 'KPME0024', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', 'KPME0033', 'R', 'KPME0016', 'KPME0023', '3', 'KPME0025', '0');

INSERT INTO `KREW_PPL_FLW_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PPL_FLW_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Initiator People Flow', 'Initiator People Flow', 'KPME-WKFLW', 'KPME0017', 'KPME0010', '0');

INSERT INTO `KREW_PPL_FLW_MBR_T` (`ACTN_RQST_PLCY_CD`, `MBR_ID`, `MBR_TYP_CD`, `PPL_FLW_ID`, `PPL_FLW_MBR_ID`, `PRIO`, `RSP_ID`, `VER_NBR`) VALUES ('F', '60', 'R', 'KPME0017', 'KPME0024', '1', 'KPME0026', '0');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::9::jwillia::(Checksum: 3:67f5b9e68c3fdca6171de24810025bd6)
--  Create Initiator Rule
INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'KPME0005', 'KPME-PM-CONTEXT', 'KPME0008', 'KPME Position Management Agenda', NULL, '0');

INSERT INTO `KRMS_PROP_T` (`CMPND_OP_CD`, `CMPND_SEQ_NO`, `DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES (NULL, NULL, 'has the primary department changed?', 'S', 'KPME0004', 'KPME0008', NULL, '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('T', 'KPME0005', 'KPME0004', 'KPME0010', '0', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('O', '=', 'KPME0004', 'KPME0011', '2', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('C', 'true', 'KPME0004', 'KPME0012', '1', '0');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Previous Primary Department', 'Notify Previous Primary Department', 'KPME-PM', 'KPME0004', 'KPME0008', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0008', 'Notify Previous Primary Hr Department Admin', 'Notify Previous Department Admin', 'KPME-PM', 'KPME0008', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0015', 'KPME0008', '1000', 'KPME0003', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0016', 'KPME0008', '1006', 'Position Previous Primary Hr Department Admin People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0008', NULL, 'KPME0008', NULL, '1', NULL, NULL);

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Additional Departments', 'Notify Additional Departments', 'KPME-PM', NULL, 'KPME0009', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0009', 'Notify Additional Hr Department Admins', 'Notify Additional Departments', 'KPME-PM', 'KPME0009', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0017', 'KPME0009', '1000', 'KPME0004', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0018', 'KPME0009', '1006', 'Position Additional Hr Department Admins People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0009', NULL, 'KPME0009', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0009' WHERE AGENDA_ITM_ID = 'KPME0008';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Primary Department Admin', 'Route To Primary Department', 'KPME-PM', NULL, 'KPME0010', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0010', 'Route To Primary Department Admin', 'Route To Primary Department', 'KPME-PM', 'KPME0010', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0019', 'KPME0010', '1000', 'KPME0005', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0020', 'KPME0010', '1006', 'Position Primary Hr Department Admin People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0010', NULL, 'KPME0010', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0010' WHERE AGENDA_ITM_ID = 'KPME0009';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Previous Fiscal Approver', 'Notify Previous Fiscal Approver', 'KPME-PM', 'KPME0004', 'KPME0011', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0011', 'Notify Previous Fiscal Approver', 'Notify Previous Fiscal Approver', 'KPME-PM', 'KPME0011', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0021', 'KPME0011', '1000', 'KPME0006', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0022', 'KPME0011', '1006', 'Position Previous Fiscal Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0011', NULL, 'KPME0011', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0011' WHERE AGENDA_ITM_ID = 'KPME0010';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Additional Fiscal Approvers', 'Notify Additional Fiscal Approvers', 'KPME-PM', NULL, 'KPME0012', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0012', 'Notify Additional Fiscal Approvers', 'Notify Additional Fiscal Approvers', 'KPME-PM', 'KPME0012', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0023', 'KPME0012', '1000', 'KPME0007', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0024', 'KPME0012', '1006', 'Position Additional Fiscal Department Approvers People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0012', NULL, 'KPME0012', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0012' WHERE AGENDA_ITM_ID = 'KPME0011';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route to Primary Fiscal Dept Approver', 'Route to Primary Fiscal Dept Approver', 'KPME-PM', NULL, 'KPME0013', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0013', 'Route to Primary Fiscal Dept Approver', 'Route to Primary Fiscal Dept Approver', 'KPME-PM', 'KPME0013', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0025', 'KPME0013', '1000', 'KPME0008', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0026', 'KPME0013', '1006', 'Position Primary Fiscal Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0013', NULL, 'KPME0013', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0013' WHERE AGENDA_ITM_ID = 'KPME0012';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Previous Department Approver', 'Notify Previous Department Approver', 'KPME-PM', 'KPME0004', 'KPME0014', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0014', 'Notify Previous Department Approver', 'Notify Previous Department Approver', 'KPME-PM', 'KPME0014', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0027', 'KPME0014', '1000', 'KPME0009', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0028', 'KPME0014', '1006', 'Position Previous Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0014', NULL, 'KPME0014', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0014' WHERE AGENDA_ITM_ID = 'KPME0013';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Primary Department Approver', 'Route To Primary Department Approver', 'KPME-PM', NULL, 'KPME0015', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0015', 'Route To Primary Department Approver', 'Route To Primary Department Approver', 'KPME-PM', 'KPME0015', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0029', 'KPME0015', '1000', 'KPME0010', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0030', 'KPME0015', '1006', 'Position Primary Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0015', NULL, 'KPME0015', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0015' WHERE AGENDA_ITM_ID = 'KPME0014';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Additional Organization Approver', 'Notify Additional Organization Approver', 'KPME-PM', NULL, 'KPME0016', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0016', 'Notify Additional Organization Approver', 'Notify Additional Organization Approver', 'KPME-PM', 'KPME0016', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0031', 'KPME0016', '1000', 'KPME0011', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0032', 'KPME0016', '1006', 'Position Additional Organizations People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0016', NULL, 'KPME0016', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0016' WHERE AGENDA_ITM_ID = 'KPME0015';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Organization', 'Route To Organization', 'KPME-PM', NULL, 'KPME0017', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0017', 'Route To Organization', 'Route To Organization', 'KPME-PM', 'KPME0017', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0033', 'KPME0017', '1000', 'KPME0012', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0034', 'KPME0017', '1006', 'Position Organization People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0017', NULL, 'KPME0017', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0017' WHERE AGENDA_ITM_ID = 'KPME0016';

INSERT INTO `KRMS_PROP_T` (`CMPND_OP_CD`, `CMPND_SEQ_NO`, `DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES (NULL, NULL, 'The position is an academic position type', 'S', 'KPME0005', 'KPME0018', NULL, '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('T', 'KPME0003', 'KPME0005', 'KPME0013', '0', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('O', '=', 'KPME0005', 'KPME0014', '2', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('C', 'true', 'KPME0005', 'KPME0015', '1', '0');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Academic Location', 'Route To Academic Location', 'KPME-PM', 'KPME0005', 'KPME0018', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0018', 'Route To Academic Location', 'Route To Academic Location', 'KPME-PM', 'KPME0018', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0035', 'KPME0018', '1000', 'KPME0013', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0036', 'KPME0018', '1006', 'Position Academic Location People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0018', NULL, 'KPME0018', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0018' WHERE AGENDA_ITM_ID = 'KPME0017';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Academic Institution', 'Route To Academic Institution', 'KPME-PM', 'KPME0005', 'KPME0019', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0019', 'Route To Academic Institution', 'Route To Academic Institution', 'KPME-PM', 'KPME0019', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0037', 'KPME0019', '1000', 'KPME0014', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0038', 'KPME0019', '1006', 'Position Academic Institution People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0019', NULL, 'KPME0019', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0019' WHERE AGENDA_ITM_ID = 'KPME0018';

INSERT INTO `KRMS_PROP_T` (`CMPND_OP_CD`, `CMPND_SEQ_NO`, `DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES (NULL, NULL, 'The position is not an academic position type', 'S', 'KPME0006', 'KPME0020', NULL, '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('T', 'KPME0003', 'KPME0006', 'KPME0016', '0', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('O', '=', 'KPME0006', 'KPME0017', '2', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('C', 'false', 'KPME0006', 'KPME0018', '1', '0');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Location', 'Route To  Location', 'KPME-PM', 'KPME0006', 'KPME0020', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0020', 'Route To Location', 'Route To Location', 'KPME-PM', 'KPME0020', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0039', 'KPME0020', '1000', 'KPME0015', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0040', 'KPME0020', '1006', 'Position Location People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0020', NULL, 'KPME0020', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0020' WHERE AGENDA_ITM_ID = 'KPME0019';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Institution', 'Route To  Institution', 'KPME-PM', 'KPME0006', 'KPME0021', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0021', 'Route To Institution', 'Route To Insitution', 'KPME-PM', 'KPME0021', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0041', 'KPME0021', '1000', 'KPME0016', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0042', 'KPME0021', '1006', 'Position Institution People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0021', NULL, 'KPME0021', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0021' WHERE AGENDA_ITM_ID = 'KPME0020';

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Initiator', 'Notify Initiator', 'KPME-PM', NULL, 'KPME0022', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0022', 'Notify Initiator', 'Notify Initiator', 'KPME-PM', 'KPME0022', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0043', 'KPME0022', '1000', 'KPME0017', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0044', 'KPME0022', '1006', 'Initiator People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0022', NULL, 'KPME0022', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0022' WHERE AGENDA_ITM_ID = 'KPME0021';


--  Changeset src/main/config/db/2.1.0/db.changelog-201403101130.xml::1::neerajsk::(Checksum: 3:17fc1084426c922195d1c73c2f1d4c77)
--  KPME-3265: Create the missed punch context in the timekeeping namespace and set the rules and actions types that are valid for this context
INSERT INTO `KRMS_CNTXT_T` (`ACTV`, `CNTXT_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `TYP_ID`) VALUES ('Y', 'KPME-MISSED-PUNCH-CONTEXT', 'KPME context for Missed Punch', 'KPME Missed Punch Context', 'KPME-TK', 'KPME0001');

INSERT INTO `KRMS_CNTXT_VLD_RULE_TYP_T` (`CNTXT_ID`, `CNTXT_VLD_RULE_ID`, `RULE_TYP_ID`) VALUES ('KPME-MISSED-PUNCH-CONTEXT', 'KPME0005', (select TYP_ID from KRMS_TYP_T where NM='Validation Rule' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Validation Action' and NMSPC_CD='KR-RULE'), 'KPME-MISSED-PUNCH-CONTEXT', 'KPME0016');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Notify PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-MISSED-PUNCH-CONTEXT', 'KPME0017');

INSERT INTO `KRMS_CNTXT_VLD_ACTN_TYP_T` (`ACTN_TYP_ID`, `CNTXT_ID`, `CNTXT_VLD_ACTN_ID`) VALUES ((select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'), 'KPME-MISSED-PUNCH-CONTEXT', 'KPME0018');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403101130.xml::2::neerajsk::(Checksum: 3:999f720bbc32964476cecd0cf015031b)
--  KPME-3265: Create the agenda in the above context, create and set the initial (rule) item for this agenda
INSERT INTO `KRMS_AGENDA_T` (`ACTV`, `AGENDA_ID`, `CNTXT_ID`, `INIT_AGENDA_ITM_ID`, `NM`) VALUES ('Y', 'KPME0006', 'KPME-MISSED-PUNCH-CONTEXT', 'KPME0023', 'KPME Missed Punch Agenda');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`) VALUES ('Y', 'Route to Workarea', 'Missed Punch WorkArea PeopleFlow', 'KPME-TK', 'KPME0023');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `RULE_ID`, `VER_NBR`) VALUES ('KPME0006', 'KPME0023', 'KPME0023', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201403101130.xml::3::neerajsk::(Checksum: 3:4d1f2c6fcd3a6844b3bf0dc6681e19e1)
--  KPME-3265: Create the action of "route to people flow type", associate it with the above rule, and set its type attribute values
INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`) VALUES ('KPME0023', 'Route to WorkArea Approvers', 'Missed Punch WorkArea PeopleFlow', 'KPME-TK', 'KPME0023', '1', (select TYP_ID from KRMS_TYP_T where NM='Route to PeopleFlow' and NMSPC_CD='KR-RULE'));

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0045', 'KPME0023', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowId' and NMSPC_CD='KR-RULE'), 'KPME0001');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`) VALUES ('KPME0046', 'KPME0023', (select ATTR_DEFN_ID from KRMS_ATTR_DEFN_T where NM='peopleFlowName' and NMSPC_CD='KR-RULE'), 'WorkArea PeopleFlow');


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151200.xml::1::umehta::(Checksum: 3:2bae66cd7083049178c4b3fd8a28b059)
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Maintain Position KRMS Agenda', 'Maintain KRMS Agenda', 'KPME-PM', UUID(), 'KPME0176', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KR-RULE' AND NM = 'KRMS Agenda Permission'), '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'Maintain KRMS Agenda'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0827', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201405201200.xml::1::jkakkad::(Checksum: 3:0b680bcdd549f720c2a6c3265076bb64)
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Group Key', 'Create Group Key', 'KPME-HR', UUID(), 'KPME0177', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Group Key', 'Edit Group Key', 'KPME-HR', UUID(), 'KPME0178', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to view a Group Key', 'View Group Key', 'KPME-HR', UUID(), 'KPME0179', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0828', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0829', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0830', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0831', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0832', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0833', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System Administrator'), 'KPME0834', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave System View Only'), 'KPME0835', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0836', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location View Only'), 'KPME0837', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department Administrator'), 'KPME0838', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Department View Only'), 'KPME0839', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System Administrator'), 'KPME0840', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time System View Only'), 'KPME0841', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0842', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location View Only'), 'KPME0843', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department Administrator'), 'KPME0844', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Group Key'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Department View Only'), 'KPME0845', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0185', 'HrGroupKeyDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0177', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0186', 'HrGroupKeyDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0178', '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0187', 'HrGroupKeyDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type (Permission)'), UUID(), 'KPME0179', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201406121401.xml::1::jkakkad::(Checksum: 3:bd6e6dcc4d2788dddf6d7d333915b010)
INSERT INTO `KRIM_ATTR_DEFN_T` (`ACTV_IND`, `CMPNT_NM`, `KIM_ATTR_DEFN_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'org.kuali.kpme.core.KPMEAttributes', 'KPME0008', 'groupKeyCode', 'KPME-WKFLW', UUID(), '1');

INSERT INTO `KRIM_TYP_ATTR_T` (`ACTV_IND`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ATTR_ID`, `KIM_TYP_ID`, `OBJ_ID`, `SORT_CD`, `VER_NBR`) VALUES ('Y', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'groupKeyCode'), 'KPME0012', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Department'), UUID(), 'a', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201406121401.xml::2::jjhanso::(Checksum: 3:49ff303016439870525bbd986f1d7727)
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
            SELECT CONCAT(ATTR_DATA_ID, 'a'), UUID(), VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, 'KPME0008', 'DEFAULT'
            FROM KRIM_ROLE_MBR_ATTR_DATA_T
            WHERE KIM_TYP_ID = (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Department' AND NMSPC_CD = 'KPME-WKFLW')
              AND KIM_ATTR_DEFN_ID = (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'department' and NMSPC_CD = 'KPME-WKFLW');


--  Changeset src/main/config/db/2.1.0/db.changelog-201406241400.xml::1::mlemons::(Checksum: 3:21a5601eaaf20ea5988ae2d8df8b3381)
--  Adding Position System Administrator role to Create Position
INSERT INTO `KRIM_PERM_TMPL_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to copy a KPME Maintenance Document', '3', 'Copy KPME Maintenance Document', 'KPME-WKFLW', UUID(), 'KPME0004', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to copy a Position', 'Copy Position', 'KPME-HR', UUID(), 'KPME0180', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Copy KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0188', 'PositionMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0180', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0846', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0847', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0848', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Organization Approver'), 'KPME0849', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0850', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0851', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0852', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Academic HR Administrator'), 'KPME0853', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0854', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0855', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0856', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0857', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0858', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0859', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0860', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization Administrator'), 'KPME0861', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0862', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0863', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0864', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution Administrator'), 'KPME0865', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0866', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0867', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0868', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department Administrator'), 'KPME0869', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0870', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0871', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0872', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Location Approver'), 'KPME0873', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0874', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0875', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0876', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Location Approver'), 'KPME0877', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0878', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0879', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0880', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Department Approver'), 'KPME0881', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0882', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0883', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0884', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Institution Approver'), 'KPME0885', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0886', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0887', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0888', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Academic HR Institution Approver'), 'KPME0889', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0890', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0891', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0892', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Budget Approver'), 'KPME0893', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0894', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0895', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0896', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Payroll Approver'), 'KPME0897', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Department Approver'), 'KPME0898', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Department Approver'), 'KPME0899', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Location Approver'), 'KPME0900', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Location Approver'), 'KPME0901', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Organization Approver'), 'KPME0902', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Fiscal Organization Approver'), 'KPME0903', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Copy Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0904', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0905', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0906', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0907', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201407031100.xml::1::neerajsk::(Checksum: 3:ec2eb9a2da95a5c2eaa0ca0da9059bb6)
--  Creating the permission for viewing missed punch and granting it to the approver and approver delegate roles.
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows user to view missed punch', 'View Missed Punch', 'KPME-TK', UUID(), 'KPME0181', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'View KPME Record'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0189', 'MissedPunchDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0181', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Missed Punch'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver'), 'KPME0908', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-TK' AND NM = 'View Missed Punch'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'Approver Delegate'), 'KPME0909', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201407101500.xml::1::mlemons::(Checksum: 3:728e1b72b760f0a50104ed744da35dca)
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create a Department Affiliation', 'Create Department Affiliation', 'KPME-HR', UUID(), 'KPME0182', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0190', 'DepartmentAffiliationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0182', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit a Department Affiliation', 'Edit Department Affiliation', 'KPME-HR', UUID(), 'KPME0183', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0191', 'DepartmentAffiliationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0183', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Organization', 'Create Organization', 'KPME-HR', UUID(), 'KPME0184', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0192', 'OrganizationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0184', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Organization', 'Edit Organization', 'KPME-HR', UUID(), 'KPME0185', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0193', 'OrganizationMaintenanceDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0185', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to create an Institution', 'Create Institution', 'KPME-HR', UUID(), 'KPME0186', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Create KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0194', 'InstitutionDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0186', '1');

INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `DESC_TXT`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`, `VER_NBR`) VALUES ('Y', 'Allows users to edit an Institution', 'Edit Institution', 'KPME-HR', UUID(), 'KPME0187', (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Edit KPME Maintenance Document'), '1');

INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`, `VER_NBR`) VALUES ('KPME0195', 'InstitutionDocumentType', (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'documentTypeName'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-SYS' AND NM = 'Document Type & Routing Node or State'), UUID(), 'KPME0187', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Department Affiliation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0910', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Department Affiliation'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0911', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0912', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0913', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Institution'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0914', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Institution'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-PM' AND ROLE_NM = 'Position System Administrator'), 'KPME0915', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0916', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location Administrator'), 'KPME0917', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0918', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-TK' AND ROLE_NM = 'Time Location Administrator'), 'KPME0919', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Edit Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0920', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-HR' AND NM = 'Create Organization'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-LM' AND ROLE_NM = 'Leave Location Administrator'), 'KPME0921', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201407111800.xml::1::jkakkad::(Checksum: 3:cf7117be7f1ea0bcabf322b2d58958dd)
--  KPME-3697 - Adding Rice KIM Role Permissions bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Institution View Only'), 'KPME0922', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Location View Only'), 'KPME0923', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'KOHR Organization View Only'), 'KPME0924', '1');

INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`, `VER_NBR`) VALUES ('Y', UUID(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KPME-PM' AND NM = 'View Position'), (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KPME-HR' AND ROLE_NM = 'HR Department View Only'), 'KPME0925', '1');

