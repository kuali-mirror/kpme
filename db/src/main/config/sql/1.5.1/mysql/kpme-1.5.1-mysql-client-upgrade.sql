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
--  Ran at: 5/15/13 10:20 AM
--  Against: kpmedev@localhost@jdbc:mysql://localhost:3306/kpmedev
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/1.5.1/db.changelog-201302281337.xml::1::jkakkad::(Checksum: 3:0bd20a150e8fc026a4159fb59c4bd3a6)
--  KPME - 2061 Required fields added for Record Method Time for EarnCode
ALTER TABLE `LM_LEAVE_BLOCK_T` ADD `BEGIN_TS` DATETIME;

ALTER TABLE `LM_LEAVE_BLOCK_T` ADD `END_TS` DATETIME;

ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `BEGIN_TS` DATETIME;

ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `END_TS` DATETIME;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303040834.xml::1::kbtaylor::(Checksum: 3:0f4960c48e54b847029a19ee482d563a)
--  KPME-2161: Refactor Timesheet Initiate into a Widget
DROP TABLE `TK_TIME_SHEET_INIT_T`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303040834.xml::2-mysql::kbtaylor::(Checksum: 3:d0596aaeeddcb3c472899d4232556781)
--  KPME-2161: Refactor Timesheet Initiate into a Widget
DROP TABLE `TK_TIME_SHEET_INIT_S`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303111525.xml::1::jkakkad::(Checksum: 3:6724a8edb45e68dfc22ad3679d852b1d)
--  KPME-2211: Remove all references to Leave Code
DROP TABLE `LM_LEAVE_CODE_T`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303111525.xml::2-mysql::jkakkad::(Checksum: 3:efd008b0ede85aeaa005e8f573af5732)
--  KPME-2211: Remove all references to Leave Code
DROP TABLE `LM_LEAVE_CODE_S`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303151500.xml::1-mysql::vchauhan::(Checksum: 3:1147321fddd6eff6a0292d429d34c70d)
DROP TABLE `HR_WORK_SCHEDULE_ASSIGN_S`;

DROP TABLE `HR_WORK_SCHEDULE_S`;

DROP TABLE `HR_WORK_SCHEDULE_ENTRY_S`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303151510.xml::1::vchauhan::(Checksum: 3:f197071e3e8a889976201b3802a3e759)
--  KPME-2222: Remove all references to TimeOffAccrual and LeaveAccrualCategory
DROP TABLE `LM_ACCRUALS_T`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303151510.xml::2-mysql::vchauhan::(Checksum: 3:a7ae9b90514f6c3ff72d27c7fdc48e57)
--  KPME-2222: Remove all references to TimeOffAccrual and LeaveAccrualCategory
DROP TABLE `LM_ACCRUALS_S`;

--  Changeset src/main/config/db/1.5.1/db.changelog-201303201245.xml::1::jjhanso::(Checksum: 3:371fcd918da163eabf83dbddd11a526b)
--  Adding indexes for performance enhancement
CREATE UNIQUE INDEX `HR_CALENDAR_IDX1` ON `HR_CALENDAR_T`(`CALENDAR_NAME`);

CREATE UNIQUE INDEX `HR_CALENDAR_ENTRIES_IDX1` ON `HR_CALENDAR_ENTRIES_T`(`HR_CALENDAR_ID`, `END_PERIOD_DATE`);

CREATE INDEX `HR_CALENDAR_ENTRIES_IDX2` ON `HR_CALENDAR_ENTRIES_T`(`BEGIN_PERIOD_DATE`, `END_PERIOD_DATE`);

CREATE UNIQUE INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`(`DEPT`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `HR_DEPT_IDX2` ON `HR_DEPT_T`(`CHART`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `HR_DEPT_IDX3` ON `HR_DEPT_T`(`LOCATION`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_EARN_CODE_GRP_IDX1` ON `HR_EARN_CODE_GROUP_T`(`EARN_CODE_GROUP`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_EARN_CODE_IDX1` ON `HR_EARN_CODE_T`(`EARN_CODE`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `HR_EARN_CODE_SEC_IDX2` ON `HR_EARN_CODE_SECURITY_T`(`EARN_CODE`, `DEPT`, `HR_SAL_GROUP`, `LOCATION`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `HR_EARN_CODE_IDX2` ON `HR_EARN_CODE_T`(`ACCRUAL_CATEGORY`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_JOB_IDX1` ON `HR_JOB_T`(`PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `HR_JOB_IDX2` ON `HR_JOB_T`(`POSITION_NBR`, `HR_PAYTYPE`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_LOCATION_IDX1` ON `HR_LOCATION_T`(`LOCATION`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_PAY_GRADE_IDX1` ON `HR_PAY_GRADE_T`(`PAY_GRADE`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_PAYTYPE_IDX1` ON `HR_PAYTYPE_T`(`PAYTYPE`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_POSITION_IDX1` ON `HR_POSITION_T`(`POSITION_NBR`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_PRIN_ATTR_IDX1` ON `HR_PRINCIPAL_ATTRIBUTES_T`(`PRINCIPAL_ID`, `EFFDT`, `TIMESTAMP`);

CREATE UNIQUE INDEX `HR_SAL_GROUP_IDX1` ON `HR_SAL_GROUP_T`(`HR_SAL_GROUP`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_ACCRUAL_CAT_RULES_IDX1` ON `LM_ACCRUAL_CATEGORY_RULES_T`(`LM_ACCRUAL_CATEGORY_ID`);

CREATE UNIQUE INDEX `LM_ACCRUAL_CATEGORY_IDX1` ON `LM_ACCRUAL_CATEGORY_T`(`ACCRUAL_CATEGORY`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_ACCRUAL_CATEGORY_IDX2` ON `LM_ACCRUAL_CATEGORY_T`(`LEAVE_PLAN`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_BALANCE_TRANSFER_IDX1` ON `LM_BALANCE_TRANSFER_T`(`PRINCIPAL_ID`, `EFFECTIVE_DATE`);

CREATE UNIQUE INDEX `LM_EMPLOYEE_OVERRIDE_IDX1` ON `LM_EMPLOYEE_OVERRIDE_T`(`PRINCIPAL_ID`, `LEAVE_PLAN`, `ACCRUAL_CAT`, `OVERRIDE_TYPE`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_LEAVE_ADJ_IDX1` ON `LM_LEAVE_ADJUSTMENT_T`(`PRINCIPAL_ID`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_LEAVE_BLOCK_HIST_IDX1` ON `LM_LEAVE_BLOCK_HIST_T`(`LM_LEAVE_BLOCK_ID`, `PRINCIPAL_ID`, `EARN_CODE`, `ACCRUAL_CATEGORY`, `LEAVE_BLOCK_TYPE`, `REQUEST_STATUS`, `LEAVE_DATE`, `BEGIN_TS`, `END_TS`);

CREATE INDEX `LM_LEAVE_BLOCK_IDX1` ON `LM_LEAVE_BLOCK_T`(`PRINCIPAL_ID`, `EARN_CODE`, `ACCRUAL_CATEGORY`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `LEAVE_BLOCK_TYPE`, `REQUEST_STATUS`, `LEAVE_DATE`, `BEGIN_TS`, `END_TS`);

CREATE INDEX `LM_LEAVE_BLOCK_IDX2` ON `LM_LEAVE_BLOCK_T`(`DOCUMENT_ID`);

CREATE INDEX `LM_LEAVE_BLOCK_IDX3` ON `LM_LEAVE_BLOCK_T`(`LEAVE_REQUEST_ID`);

CREATE INDEX `LM_LEAVE_DOC_HDR_IDX1` ON `LM_LEAVE_DOCUMENT_HEADER_T`(`PRINCIPAL_ID`, `BEGIN_DATE`, `END_DATE`);

CREATE INDEX `LM_LEAVE_PAYOUT_IDX1` ON `LM_LEAVE_PAYOUT_T`(`PRINCIPAL_ID`, `EARN_CODE`, `EFFECTIVE_DATE`);

CREATE UNIQUE INDEX `LM_LEAVE_PLAN_IDX1` ON `LM_LEAVE_PLAN_T`(`LEAVE_PLAN`, `EFFDT`, `TIMESTAMP`);

CREATE INDEX `LM_LEAVE_REQ_DOC_IDX1` ON `LM_LEAVE_REQ_DOC_T`(`LM_LEAVE_BLOCK_ID`);

CREATE INDEX `LM_SYS_SCHD_TIMEOFF_IDX1` ON `LM_SYS_SCHD_TIMEOFF_T`(`LEAVE_PLAN`, `EFFDT`, `TIMESTAMP`);

--  Changeset src/main/config/db/1.5.1/db.changelog-201303201245.xml::2::kbtaylor::(Checksum: 3:2b3137b074d6b5e3c74bb120f4a92017)
--  Correcting indicies
DROP INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`;

CREATE UNIQUE INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`(`DEPT`, `LOCATION`, `EFFDT`, `TIMESTAMP`);

DROP INDEX `HR_PAY_GRADE_IDX1` ON `HR_PAY_GRADE_T`;

CREATE UNIQUE INDEX `HR_PAY_GRADE_IDX1` ON `HR_PAY_GRADE_T`(`PAY_GRADE`, `SAL_GROUP`, `EFFDT`, `TIMESTAMP`);
