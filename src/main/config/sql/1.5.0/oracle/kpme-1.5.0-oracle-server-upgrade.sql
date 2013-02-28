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

-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/config/db/db.changelog-main-upgrade.xml
-- Ran at: 2/28/13 8:34 AM
-- Against: KRDEV@jdbc:oracle:thin:@127.0.0.1:1521:XE
-- Liquibase version: 2.0.5
-- *********************************************************************

-- Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::1::jjhanso::(Checksum: 3:f6161f10970dd2eb24172ac9322e25dd)
-- Adding Rice permission bootstrap data
INSERT INTO KRIM_PERM_T (ACTV_IND, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID) VALUES ('Y', 'Recall LeaveRequest Document', 'KR-WKFLW', SYS_GUID(), 'KPME0001', '68');

-- Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::2::jjhanso::(Checksum: 3:b644760f3390e3de38034428bf4b9b98)
-- Adding Rice permission attributes
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, ATTR_VAL, KIM_ATTR_DEFN_ID, KIM_TYP_ID, OBJ_ID, PERM_ID) VALUES ('KPME0001', 'LeaveRequestDocument', '13', '8', SYS_GUID(), 'KPME0001');

-- Changeset src/main/config/db/1.5.0/db.changelog-201301161442.xml::3::jjhanso::(Checksum: 3:3edec4ab10ca4bc3d18601c2d275c372)
-- Adding Rice group bootstrap data
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND, OBJ_ID, PERM_ID, ROLE_ID, ROLE_PERM_ID) VALUES ('Y', SYS_GUID(), (select PERM_ID from krim_perm_t where nmspc_cd = 'KR-WKFLW' and nm = 'Recall LeaveRequest Document'), (select role_id from krim_role_t where nmspc_cd = 'KR-WKFLW' and role_nm = 'Initiator'), 'KPME0001');
