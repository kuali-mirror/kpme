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
--  Ran at: 12/20/13 9:36 AM
--  Against: tk@localhost@jdbc:mysql://localhost/tk
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::1::jjhanso::(Checksum: 3:66fd6528f167ac059f77d7a5cb452dc6)
ALTER TABLE `LM_LEAVE_DOCUMENT_HEADER_T` MODIFY `PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::2::jjhanso::(Checksum: 3:71242b0b08b0131f508f05c0c6646759)
ALTER TABLE `TK_CLOCK_LOCATION_RL_T` MODIFY `PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::3::jjhanso::(Checksum: 3:4ed0c3a3e5f909d3c390c13a612318fd)
ALTER TABLE `TK_CLOCK_LOCATION_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::4::jjhanso::(Checksum: 3:96c028316cefab0cf925f64537b9b816)
ALTER TABLE `TK_DAILY_OVERTIME_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::5::jjhanso::(Checksum: 3:819614105a00e4d3df5d67f77dc2812f)
ALTER TABLE `TK_DEPT_LUNCH_RL_T` MODIFY `PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::6::jjhanso::(Checksum: 3:639c8b5155022c6dbb4f756061cd41ba)
ALTER TABLE `TK_DEPT_LUNCH_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::7::jjhanso::(Checksum: 3:1aa9bffa2b5974412abcf93d3aeeba1a)
ALTER TABLE `TK_GRACE_PERIOD_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::8::jjhanso::(Checksum: 3:cf0f15ddcc748296aeba299d8129c2ef)
ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::9::jjhanso::(Checksum: 3:c9daf6a96d66136b5ad063ec2e5b5a63)
ALTER TABLE `TK_SYSTEM_LUNCH_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::10::jjhanso::(Checksum: 3:9473682d78c08298b34d8d72c1d8369b)
ALTER TABLE `TK_TIME_COLLECTION_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::11::jjhanso::(Checksum: 3:e5f164a5f91c527c17da3f1b35935655)
ALTER TABLE `TK_WEEKLY_OVERTIME_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40);

--  Changeset src/main/config/db/2.0.1/db.changelog-201311261430.xml::1::yingzhou::(Checksum: 3:2d6bb48b79257287f6710579239d3cb9)
ALTER TABLE `LM_ACCRUAL_CATEGORY_T` MODIFY `DESCR` VARCHAR(50) NULL;

--  Changeset src/main/config/db/2.0.1/db.changelog-201312091430.xml::1::jjhanso::(Checksum: 3:8c03fd79c081c2802c0115575dcdc60a)
ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `JOB_NUMBER` BIGINT;

ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `WORK_AREA` BIGINT;

ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `TASK` BIGINT;

ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `TRANS_DOC_ID` VARCHAR(5);


