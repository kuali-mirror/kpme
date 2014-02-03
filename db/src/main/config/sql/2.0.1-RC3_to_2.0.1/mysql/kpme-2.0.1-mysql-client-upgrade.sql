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
--  Against: tk@localhost@jdbc:mysql://localhost/tk
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/2.0.1/db.changelog-201401171100.xml::1::yingzhou::(Checksum: 3:71ce9c0336edecdcca29052e7ae3fde2)
ALTER TABLE `TK_ASSIGNMENT_T` ADD `PRIMARY_ASSIGN` VARCHAR(1) DEFAULT 'N';
