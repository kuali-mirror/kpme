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
--  Ran at: 1/9/14 8:32 AM
--  Against: krtt@localhost@jdbc:mysql://localhost/krtt
--  Liquibase version: 2.0.5
--  *********************************************************************



--  Changeset src/main/config/db/2.0.1/db.changelog-201401021250.xml::1::jwillia::(Checksum: 3:35374c48de78128eea1400e8e4bad66c)
INSERT INTO `KRCR_CMPNT_T` (`ACTV_IND`, `CMPNT_CD`, `NM`, `NMSPC_CD`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'KeyValues', 'KeyValues', 'KPME-HR', 'uuid()', '1');


--  Changeset src/main/config/db/2.0.1/db.changelog-201401021250.xml::2::jwillia::(Checksum: 3:7f491801f1f45c65e607e451e81011c4)
INSERT INTO `KRCR_PARM_T` (`APPL_ID`, `CMPNT_CD`, `EVAL_OPRTR_CD`, `NMSPC_CD`, `OBJ_ID`, `PARM_DESC_TXT`, `PARM_NM`, `PARM_TYP_CD`, `VAL`, `VER_NBR`) VALUES ('KPME', 'KeyValues', 'A', 'KPME-HR', 'uuid()', 'A semicolon delimited list of timezones for timezone dropdown lists.', 'TIME_ZONES', 'VALID', 'America/Chicago;America/Denver;America/Detroit;America/Indiana/Indianapolis;America/Phoenix;America/Los_Angeles', '1');
