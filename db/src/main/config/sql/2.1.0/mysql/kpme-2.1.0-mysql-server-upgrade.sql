--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/config/db/db.changelog-main-upgrade.xml
--  Ran at: 7/9/14 2:19 PM
--  Against: krtt@localhost@jdbc:mysql://localhost/krtt
--  Liquibase version: 2.0.5
--  *********************************************************************

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


--  Changeset src/main/config/db/2.1.0/db.changelog-201403040830.xml::9::jwillia::(Checksum: 3:de899a581f2934999bbb4b9c4187b7de)
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

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0009' WHERE AGENDA_ITM_ID = "KPME0008";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Primary Department Admin', 'Route To Primary Department', 'KPME-PM', NULL, 'KPME0010', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0010', 'Route To Primary Department Admin', 'Route To Primary Department', 'KPME-PM', 'KPME0010', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0019', 'KPME0010', '1000', 'KPME0005', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0020', 'KPME0010', '1006', 'Position Primary Hr Department Admin People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0010', NULL, 'KPME0010', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0010' WHERE AGENDA_ITM_ID = "KPME0009";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Previous Fiscal Approver', 'Notify Previous Fiscal Approver', 'KPME-PM', 'KPME0004', 'KPME0011', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0011', 'Notify Previous Fiscal Approver', 'Notify Previous Fiscal Approver', 'KPME-PM', 'KPME0011', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0021', 'KPME0011', '1000', 'KPME0006', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0022', 'KPME0011', '1006', 'Position Previous Fiscal Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0011', NULL, 'KPME0011', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0011' WHERE AGENDA_ITM_ID = "KPME0010";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Additional Fiscal Approvers', 'Notify Additional Fiscal Approvers', 'KPME-PM', NULL, 'KPME0012', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0012', 'Notify Additional Fiscal Approvers', 'Notify Additional Fiscal Approvers', 'KPME-PM', 'KPME0012', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0023', 'KPME0012', '1000', 'KPME0007', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0024', 'KPME0012', '1006', 'Position Additional Fiscal Department Approvers People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0012', NULL, 'KPME0012', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0012' WHERE AGENDA_ITM_ID = "KPME0011";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route to Primary Fiscal Dept Approver', 'Route to Primary Fiscal Dept Approver', 'KPME-PM', NULL, 'KPME0013', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0013', 'Route to Primary Fiscal Dept Approver', 'Route to Primary Fiscal Dept Approver', 'KPME-PM', 'KPME0013', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0025', 'KPME0013', '1000', 'KPME0008', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0026', 'KPME0013', '1006', 'Position Primary Fiscal Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0013', NULL, 'KPME0013', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0013' WHERE AGENDA_ITM_ID = "KPME0012";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Previous Department Approver', 'Notify Previous Department Approver', 'KPME-PM', 'KPME0004', 'KPME0014', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0014', 'Notify Previous Department Approver', 'Notify Previous Department Approver', 'KPME-PM', 'KPME0014', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0027', 'KPME0014', '1000', 'KPME0009', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0028', 'KPME0014', '1006', 'Position Previous Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0014', NULL, 'KPME0014', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0014' WHERE AGENDA_ITM_ID = "KPME0013";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Primary Department Approver', 'Route To Primary Department Approver', 'KPME-PM', NULL, 'KPME0015', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0015', 'Route To Primary Department Approver', 'Route To Primary Department Approver', 'KPME-PM', 'KPME0015', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0029', 'KPME0015', '1000', 'KPME0010', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0030', 'KPME0015', '1006', 'Position Primary Department Approver People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0015', NULL, 'KPME0015', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0015' WHERE AGENDA_ITM_ID = "KPME0014";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Additional Organization Approver', 'Notify Additional Organization Approver', 'KPME-PM', NULL, 'KPME0016', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0016', 'Notify Additional Organization Approver', 'Notify Additional Organization Approver', 'KPME-PM', 'KPME0016', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0031', 'KPME0016', '1000', 'KPME0011', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0032', 'KPME0016', '1006', 'Position Additional Organizations People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0016', NULL, 'KPME0016', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0016' WHERE AGENDA_ITM_ID = "KPME0015";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Organization', 'Route To Organization', 'KPME-PM', NULL, 'KPME0017', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0017', 'Route To Organization', 'Route To Organization', 'KPME-PM', 'KPME0017', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0033', 'KPME0017', '1000', 'KPME0012', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0034', 'KPME0017', '1006', 'Position Organization People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0017', NULL, 'KPME0017', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0017' WHERE AGENDA_ITM_ID = "KPME0016";

INSERT INTO `KRMS_PROP_T` (`CMPND_OP_CD`, `CMPND_SEQ_NO`, `DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES (NULL, NULL, 'The position is an academic position type', 'S', 'KPME0005', 'KPME0018', NULL, '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('T', 'KPME0003', 'KPME0005', 'KPME0013', '0', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('O', '=', 'KPME0005', 'KPME0014', '2', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('C', 'true', 'KPME0005', 'KPME0015', '1', '0');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Academic Location', 'Route To Academic Location', 'KPME-PM', 'KPME0005', 'KPME0018', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0018', 'Route To Academic Location', 'Route To Academic Location', 'KPME-PM', 'KPME0018', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0035', 'KPME0018', '1000', 'KPME0013', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0036', 'KPME0018', '1006', 'Position Academic Location People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0018', NULL, 'KPME0018', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0018' WHERE AGENDA_ITM_ID = "KPME0017";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Academic Institution', 'Route To Academic Institution', 'KPME-PM', 'KPME0005', 'KPME0019', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0019', 'Route To Academic Institution', 'Route To Academic Institution', 'KPME-PM', 'KPME0019', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0037', 'KPME0019', '1000', 'KPME0014', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0038', 'KPME0019', '1006', 'Position Academic Institution People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0019', NULL, 'KPME0019', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0019' WHERE AGENDA_ITM_ID = "KPME0018";

INSERT INTO `KRMS_PROP_T` (`CMPND_OP_CD`, `CMPND_SEQ_NO`, `DESC_TXT`, `DSCRM_TYP_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES (NULL, NULL, 'The position is not an academic position type', 'S', 'KPME0006', 'KPME0020', NULL, '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('T', 'KPME0003', 'KPME0006', 'KPME0016', '0', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('O', '=', 'KPME0006', 'KPME0017', '2', '0');

INSERT INTO `KRMS_PROP_PARM_T` (`PARM_TYP_CD`, `PARM_VAL`, `PROP_ID`, `PROP_PARM_ID`, `SEQ_NO`, `VER_NBR`) VALUES ('C', 'false', 'KPME0006', 'KPME0018', '1', '0');

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Location', 'Route To  Location', 'KPME-PM', 'KPME0006', 'KPME0020', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0020', 'Route To Location', 'Route To Location', 'KPME-PM', 'KPME0020', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0039', 'KPME0020', '1000', 'KPME0015', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0040', 'KPME0020', '1006', 'Position Location People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0020', NULL, 'KPME0020', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0020' WHERE AGENDA_ITM_ID = "KPME0019";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Route To Institution', 'Route To  Institution', 'KPME-PM', 'KPME0006', 'KPME0021', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0021', 'Route To Institution', 'Route To Insitution', 'KPME-PM', 'KPME0021', '1', '1001', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0041', 'KPME0021', '1000', 'KPME0016', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0042', 'KPME0021', '1006', 'Position Institution People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0021', NULL, 'KPME0021', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0021' WHERE AGENDA_ITM_ID = "KPME0020";

INSERT INTO `KRMS_RULE_T` (`ACTV`, `DESC_TXT`, `NM`, `NMSPC_CD`, `PROP_ID`, `RULE_ID`, `TYP_ID`, `VER_NBR`) VALUES ('Y', 'Notify Initiator', 'Notify Initiator', 'KPME-PM', NULL, 'KPME0022', NULL, '0');

INSERT INTO `KRMS_ACTN_T` (`ACTN_ID`, `DESC_TXT`, `NM`, `NMSPC_CD`, `RULE_ID`, `SEQ_NO`, `TYP_ID`, `VER_NBR`) VALUES ('KPME0022', 'Notify Initiator', 'Notify Initiator', 'KPME-PM', 'KPME0022', '1', '1000', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0043', 'KPME0022', '1000', 'KPME0017', '0');

INSERT INTO `KRMS_ACTN_ATTR_T` (`ACTN_ATTR_DATA_ID`, `ACTN_ID`, `ATTR_DEFN_ID`, `ATTR_VAL`, `VER_NBR`) VALUES ('KPME0044', 'KPME0022', '1006', 'Initiator People Flow', '0');

INSERT INTO `KRMS_AGENDA_ITM_T` (`AGENDA_ID`, `AGENDA_ITM_ID`, `ALWAYS`, `RULE_ID`, `SUB_AGENDA_ID`, `VER_NBR`, `WHEN_FALSE`, `WHEN_TRUE`) VALUES ('KPME0005', 'KPME0022', NULL, 'KPME0022', NULL, '1', NULL, NULL);

UPDATE `KRMS_AGENDA_ITM_T` SET `ALWAYS` = 'KPME0022' WHERE AGENDA_ITM_ID = "KPME0021";


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
