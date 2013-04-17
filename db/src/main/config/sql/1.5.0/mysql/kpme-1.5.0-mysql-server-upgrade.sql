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
--  Ran at: 2/28/13 8:24 AM
--  Against: krdev@localhost@jdbc:mysql://localhost:3306/krdev
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::1::jjhanso::(Checksum: 3:e9c5f83fd1977f63333fb66c95b953d6)
--  Adding Rice permission bootstrap data
INSERT INTO `KRIM_PERM_T` (`ACTV_IND`, `NM`, `NMSPC_CD`, `OBJ_ID`, `PERM_ID`, `PERM_TMPL_ID`) VALUES ('Y', 'Recall LeaveRequest Document', 'KR-WKFLW', UUID(), 'KPME0001', '68');

--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::2::jjhanso::(Checksum: 3:f73991e9fdb23584910a0e6c1ad841e9)
--  Adding Rice permission attributes
INSERT INTO `KRIM_PERM_ATTR_DATA_T` (`ATTR_DATA_ID`, `ATTR_VAL`, `KIM_ATTR_DEFN_ID`, `KIM_TYP_ID`, `OBJ_ID`, `PERM_ID`) VALUES ('KPME0001', 'LeaveRequestDocument', '13', '8', UUID(), 'KPME0001');

--  Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::3::jjhanso::(Checksum: 3:67c41cde91baf170fa9d4c4b2c32d72a)
--  Adding Rice group bootstrap data
INSERT INTO `KRIM_ROLE_PERM_T` (`ACTV_IND`, `OBJ_ID`, `PERM_ID`, `ROLE_ID`, `ROLE_PERM_ID`) VALUES ('Y', UUID(), (select PERM_ID from krim_perm_t where nmspc_cd = 'KR-WKFLW' and nm = 'Recall LeaveRequest Document'), (select role_id from krim_role_t where nmspc_cd = 'KR-WKFLW' and role_nm = 'Initiator'), 'KPME0001');
