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

--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/config/db/db.changelog-main-upgrade.xml
--  Ran at: 1/29/14 4:33 PM
--  Against: krtt@localhost@jdbc:mysql://localhost/krtt
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/2.0.1/db.changelog-201401241035.xml::1::neerajsk::(Checksum: 3:3b9a3b8fc1c5704d979dd82e16fd6c04)
--  Adding Rice KIM type + KIM role data for the Approver Proxy and Approver Delegate Proxy derived roles
INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0006', 'Derived Role: Approver Proxy', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}approverProxyDerivedRoleTypeService', '1');

INSERT INTO `KRIM_TYP_T` (`ACTV_IND`, `KIM_TYP_ID`, `NM`, `NMSPC_CD`, `OBJ_ID`, `SRVC_NM`, `VER_NBR`) VALUES ('Y', 'KPME0007', 'Derived Role: Approver Delegate Proxy', 'KPME-WKFLW', UUID(), '{http://kpme.kuali.org/core/v2_0}approverDelegateProxyDerivedRoleTypeService', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The derived role that acts as a proxy for the Approver role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Derived Role: Approver Proxy'), NOW(), 'KPME-HR', UUID(), 'KPME0020', 'Derived Role: Approver Proxy', '1');

INSERT INTO `KRIM_ROLE_T` (`ACTV_IND`, `DESC_TXT`, `KIM_TYP_ID`, `LAST_UPDT_DT`, `NMSPC_CD`, `OBJ_ID`, `ROLE_ID`, `ROLE_NM`, `VER_NBR`) VALUES ('Y', 'The derived role that acts as a proxy for the Approver Delegate role', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KPME-WKFLW' AND NM = 'Derived Role: Approver Delegate Proxy'), NOW(), 'KPME-HR', UUID(), 'KPME0021', 'Derived Role: Approver Delegate Proxy', '1');
