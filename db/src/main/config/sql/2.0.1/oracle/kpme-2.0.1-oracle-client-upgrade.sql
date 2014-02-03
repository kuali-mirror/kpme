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

-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::1::jjhanso
ALTER TABLE LM_LEAVE_DOCUMENT_HEADER_T MODIFY PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::2::jjhanso
ALTER TABLE TK_CLOCK_LOCATION_RL_T MODIFY PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::3::jjhanso
ALTER TABLE TK_CLOCK_LOCATION_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::4::jjhanso
ALTER TABLE TK_DAILY_OVERTIME_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::5::jjhanso
ALTER TABLE TK_DEPT_LUNCH_RL_T MODIFY PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::6::jjhanso
ALTER TABLE TK_DEPT_LUNCH_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::7::jjhanso
ALTER TABLE TK_GRACE_PERIOD_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::8::jjhanso
ALTER TABLE TK_SHIFT_DIFFERENTIAL_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::9::jjhanso
ALTER TABLE TK_SYSTEM_LUNCH_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::10::jjhanso
ALTER TABLE TK_TIME_COLLECTION_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201310140900.xml::11::jjhanso
ALTER TABLE TK_WEEKLY_OVERTIME_RL_T MODIFY USER_PRINCIPAL_ID VARCHAR2(40)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201311261430.xml::1::yingzhou
ALTER TABLE LM_ACCRUAL_CATEGORY_T MODIFY DESCR NULL
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201312091430.xml::1::jjhanso
ALTER TABLE LM_LEAVE_BLOCK_HIST_T ADD JOB_NUMBER NUMBER(38, 0)
/
ALTER TABLE LM_LEAVE_BLOCK_HIST_T ADD WORK_AREA NUMBER(38, 0)
/
ALTER TABLE LM_LEAVE_BLOCK_HIST_T ADD TASK NUMBER(38, 0)
/
ALTER TABLE LM_LEAVE_BLOCK_HIST_T ADD TRANS_DOC_ID VARCHAR2(5)
/
-- Changeset src/main/config/db/2.0.1/db.changelog-201312251230.xml::1::umehta
ALTER TABLE LM_LEAVE_BLOCK_HIST_T ADD USER_PRINCIPAL_ID VARCHAR2(40)
/
--  Changeset src/main/config/db/2.0.1/db.changelog-201401171100.xml::1::yingzhou::(Checksum: 3:71ce9c0336edecdcca29052e7ae3fde2)
ALTER TABLE TK_ASSIGNMENT_T ADD PRIMARY_ASSIGN VARCHAR2(1) DEFAULT 'N'
/
