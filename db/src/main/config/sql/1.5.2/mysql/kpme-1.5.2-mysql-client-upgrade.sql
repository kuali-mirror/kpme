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
--  Ran at: 6/27/13 8:54 AM
--  Against: kpmedev@localhost@jdbc:mysql://localhost:3306/kpmedev
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/1.5.2/db.changelog-201305170850.xml::1::jjhanso::(Checksum: 3:b79078ce37d72ea32d02fde333b7ea5d)
--  KPME-2382: Adding indexes for performance enhancement
CREATE INDEX `TK_TIME_BLOCK_HIST_IDX3` ON `TK_TIME_BLOCK_HIST_T`(`PRINCIPAL_ID`, `DOCUMENT_ID`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `EARN_CODE`, `BEGIN_TS`, `END_TS`, `TIMESTAMP`, `ACTION_HISTORY`, `CLOCK_LOG_CREATED`);

CREATE INDEX `TK_TIME_BLOCK_HIST_IDX4` ON `TK_TIME_BLOCK_HIST_T`(`PRINCIPAL_ID`);

--  Changeset src/main/config/db/1.5.2/db.changelog-201305211113.xml::1::kbtaylor::(Checksum: 3:bdc5df539300db07586cf419eaa899f3)
--  KPME-2394: Adding document id to clock log for better performance
ALTER TABLE `TK_CLOCK_LOG_T` ADD `DOCUMENT_ID` VARCHAR(14);

--  Changeset src/main/config/db/1.5.2/db.changelog-201305211113.xml::2::kbtaylor::(Checksum: 3:a7f4229c06bd1995066702d43d3f76ef)
--  KPME-2394: Adding document id to clock log for better performance
UPDATE TK_CLOCK_LOG_T CL SET DOCUMENT_ID = (SELECT DOCUMENT_ID FROM TK_DOCUMENT_HEADER_T WHERE PRINCIPAL_ID = CL.PRINCIPAL_ID AND PAY_BEGIN_DT <= CL.CLOCK_TS AND PAY_END_DT > CL.CLOCK_TS);

--  Changeset src/main/config/db/1.5.2/db.changelog-201305211113.xml::3::kbtaylor::(Checksum: 3:310d76fc73fe874ecf9060e6c7dcef77)
--  KPME-2394: Adding document id to clock log for better performance
ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `DOCUMENT_ID` VARCHAR(14) NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `PRINCIPAL_ID` VARCHAR(40) NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `JOB_NUMBER` BIGINT NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `WORK_AREA` BIGINT NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `TASK` BIGINT NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `CLOCK_TS_TZ` VARCHAR(50) NOT NULL NOT NULL;

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `CLOCK_ACTION` VARCHAR(2) NOT NULL NOT NULL;

DROP INDEX `TK_CLOCK_LOG_IDX1` ON `TK_CLOCK_LOG_T`;

CREATE UNIQUE INDEX `TK_CLOCK_LOG_IDX1` ON `TK_CLOCK_LOG_T`(`DOCUMENT_ID`, `PRINCIPAL_ID`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `CLOCK_TS`, `CLOCK_TS_TZ`, `CLOCK_ACTION`, `TIMESTAMP`);

--  Changeset src/main/config/db/1.5.2/db.changelog-201306040955.xml::1::jjhanso::(Checksum: 3:b30026913328f03fcc68a36d219825bd)
--  KPME-2445: Adding indexes for dept lunch rule
CREATE UNIQUE INDEX `TK_DEPT_LUNCH_RL_IDX1` ON `TK_DEPT_LUNCH_RL_T`(`DEPT`, `WORK_AREA`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `TIMESTAMP`);
