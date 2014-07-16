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
--  Ran at: 7/14/14 8:13 AM
--  Against: tk@localhost@jdbc:mysql://localhost/tk
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Changeset src/main/config/db/2.1.0/db.changelog-201310301100.xml::1::tkagata::(Checksum: 3:ceeacf0aa63e2b322b111fad4710ded9)
ALTER TABLE `PM_PSTN_RPS_T` ADD `PM_RPS_OPTION` VARCHAR(20) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201310311200.xml::1::yingzhou::(Checksum: 3:03183abb299f774bf105e13c99a62b75)
ALTER TABLE `PM_PSTN_FLAG_T` MODIFY `CATEGORY` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201310311200.xml::2::yingzhou::(Checksum: 3:e4bd0795a04423e516bbefedb653ff16)
ALTER TABLE `PM_PSTN_CL_FLAG_T` MODIFY `CATEGORY` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311120900.xml::1::tkagata::(Checksum: 3:c2a6a06639e4350de7d807128dac4058)
--  kpme-3016: renamed campus to location and added position status
ALTER TABLE `HR_POSITION_T` CHANGE `CAMPUS` `LOCATION` VARCHAR(15) NOT NULL DEFAULT '*';

ALTER TABLE `HR_POSITION_T` ADD `PSTN_STATUS` VARCHAR(20) NOT NULL DEFAULT 'New';


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::1::yingzhou::(Checksum: 3:d5a6325da26c1a7880ab1cddf8205321)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `PSTN_RPT_GRP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::2::yingzhou::(Checksum: 3:6ba375975554a7093d035ee3fa9117b9)
ALTER TABLE `PM_PSTN_RPT_GRP_T` MODIFY `PSTN_RPT_GRP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::3::yingzhou::(Checksum: 3:51521b7d574e704340f830a379b50109)
ALTER TABLE `PM_PSTN_RPT_CAT_T` MODIFY `PSTN_RPT_TYPE` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::4::yingzhou::(Checksum: 3:994ab6f58fd7885558362beb6acf8383)
ALTER TABLE `PM_PSTN_RPT_TYP_T` MODIFY `PSTN_RPT_TYP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::5::yingzhou::(Checksum: 3:8f7602ab82f5bb5ac21945defe42eeb0)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` MODIFY `PSTN_RPT_GRP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::6::yingzhou::(Checksum: 3:f63d0d7f52801bd1cf37786409e5c261)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` MODIFY `PSTN_RPT_GRP_SUB_CAT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::7::yingzhou::(Checksum: 3:11e3cf981c411e85e839075b7f05023c)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` MODIFY `PSTN_RPT_TYPE` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::8::yingzhou::(Checksum: 3:4cb9033f1b8afd49dc9c47f7b834e55d)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` MODIFY `PSTN_RPT_SUB_CAT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::9::yingzhou::(Checksum: 3:62b8ba79d74ed3e48f896dde34ebbaf7)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` MODIFY `PSTN_RPT_CAT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311131800.xml::10::yingzhou::(Checksum: 3:ce3d40daaf4ce892b7b1b4707340eb46)
ALTER TABLE `PM_PSTN_CL_DUTY_T` MODIFY `NAME` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311141200.xml::1::tkagata::(Checksum: 3:b5c102c6ec786f1ea7f50643362b100a)
ALTER TABLE `HR_POSITION_T` MODIFY `PSTN_RPT_GRP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311141200.xml::2::tkagata::(Checksum: 3:20752f9c500892847317ec54b346ad41)
ALTER TABLE `PM_PSTN_DUTY_T` MODIFY `NAME` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311141500.xml::1::tkagata::(Checksum: 3:98c8129eff1959b73a5431ef6d185d5f)
--  kpme-3016: added new fields
ALTER TABLE `HR_POSITION_T` ADD `APPT_TYP` VARCHAR(15) NOT NULL DEFAULT 'REGULAR';

ALTER TABLE `HR_POSITION_T` ADD `REPORTS_TO` VARCHAR(40);

ALTER TABLE `HR_POSITION_T` ADD `EXP_ENDDT` DATE;

ALTER TABLE `HR_POSITION_T` ADD `RENEW_ELIG` VARCHAR(1) NOT NULL DEFAULT 'N';

ALTER TABLE `HR_POSITION_T` ADD `TEMPORARY` VARCHAR(1) NOT NULL DEFAULT 'N';

ALTER TABLE `HR_POSITION_T` ADD `CONT` VARCHAR(1) NOT NULL DEFAULT 'N';

ALTER TABLE `HR_POSITION_T` ADD `CONT_TYP` VARCHAR(10);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::1::umehta::(Checksum: 3:96d95a62d7b859135afc7ec71a0311f1)
ALTER TABLE `PM_PSTN_RPT_CAT_T` MODIFY `PSTN_RPT_CAT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::2::umehta::(Checksum: 3:c9aeea512eade80b309e1a08880cb344)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` MODIFY `PSTN_RPT_SUB_CAT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::3::umehta::(Checksum: 3:d3b4bd98590c5f8e40c79c69fe457041)
ALTER TABLE `PM_PSTN_TYP_T` MODIFY `PSTN_TYP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::4::umehta::(Checksum: 3:2c4b8e31f1a04830c780d82e15b88d3f)
ALTER TABLE `PM_PSTN_FLG_T` MODIFY `PSTN_FLG_NM` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::5::umehta::(Checksum: 3:b10cad7b180eaad369fb58a5b5a5f0a2)
ALTER TABLE `PM_PSTN_QLFR_TYP_T` MODIFY `CODE` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::6::umehta::(Checksum: 3:87fd5f6feada2c02acb52b333915a4ac)
ALTER TABLE `PM_PSTN_QLFR_TYP_T` MODIFY `TYPE` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::7::umehta::(Checksum: 3:5ffbedb39dff29ba50b3f75e256c42d7)
ALTER TABLE `PM_PSTN_QLFR_TYP_T` MODIFY `TYPE_VL` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::8::umehta::(Checksum: 3:e2dfed0f86f31a0d494fdb4876f80918)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `SAL_GROUP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::9::umehta::(Checksum: 3:15f5aa120c35feee5ee84889efe3bd17)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `LV_PLN` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::10::umehta::(Checksum: 3:8063c0b63e74f1a6dafc0426ae6fc5f6)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `PSTN_TYP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::11::umehta::(Checksum: 3:38922144af7b8e4c77b144a727001a96)
ALTER TABLE `PM_PSTN_DEPT_T` MODIFY `DEPT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::12::umehta::(Checksum: 3:4efaa4da493e0e23ebc82157e3b30ca8)
ALTER TABLE `PM_PSTN_APPOINTMENT_T` MODIFY `PSTN_APPOINTMENT` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::13::umehta::(Checksum: 3:10be9cb7313bba389e2203e55f51b506)
ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` MODIFY `NAME` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::14::umehta::(Checksum: 3:a58e316faeeb798cf0713a7bcbef1677)
ALTER TABLE `HR_POSITION_T` MODIFY `POSITION_NBR` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::15::umehta::(Checksum: 3:ea15e426391a352b2592fc19efd6f920)
ALTER TABLE `HR_POSITION_T` MODIFY `PSTN_TYP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::16::umehta::(Checksum: 3:ff9ea6ae61cba6fbedd7a502818e51a6)
ALTER TABLE `HR_POSITION_T` MODIFY `SAL_GROUP` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311211730.xml::17::umehta::(Checksum: 3:6b1e229679dc5b78252c12c5d36e070b)
ALTER TABLE `HR_POSITION_T` MODIFY `LV_PLN` VARCHAR(50);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311251115.xml::1::jwillia::(Checksum: 3:58d3d2468df5bf9357d630e26734ed10)
ALTER TABLE `PM_PSTN_CL_T` ADD `PAY_GRADE` VARCHAR(20);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::1::jwillia::(Checksum: 3:8df77ea148924ce36dc389711f96b5de)
ALTER TABLE `LM_ACCRUAL_CATEGORY_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::2::jwillia::(Checksum: 3:e9662a798496f7cb4f6716e98f37fe9d)
ALTER TABLE `LM_ACCRUAL_CATEGORY_RULES_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::3::jwillia::(Checksum: 3:c8b29afbfab85d20121585ab5f8382c6)
ALTER TABLE `TK_ASSIGNMENT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::4::jwillia::(Checksum: 3:bdb338055752d2ccf53ffd78a8865208)
ALTER TABLE `TK_ASSIGN_ACCT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::5::jwillia::(Checksum: 3:5d02c7777998c12d73e5a3d9d26c68c2)
ALTER TABLE `HR_DEPT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::6::jwillia::(Checksum: 3:a64cd7610097e83a28ad14aafb8b311e)
ALTER TABLE `HR_EARN_CODE_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::7::jwillia::(Checksum: 3:2e21f62f685a27684002d21f7ee5de49)
ALTER TABLE `HR_EARN_CODE_GROUP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::8::jwillia::(Checksum: 3:4bf46b4c36339bae932c0d011bcff77d)
ALTER TABLE `HR_EARN_CODE_SECURITY_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::9::jwillia::(Checksum: 3:edd4ec930f78437331226683edea7739)
ALTER TABLE `PM_INSTITUTION_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::10::jwillia::(Checksum: 3:8dee0b3d5e5bd12e7adf97ddaf0fba79)
ALTER TABLE `HR_JOB_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::11::jwillia::(Checksum: 3:1419dbc1fdaf45822198e77756520f8a)
ALTER TABLE `LM_LEAVE_PLAN_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::12::jwillia::(Checksum: 3:27c86204de98ba043061f4156bf3131c)
ALTER TABLE `PM_PAY_STEP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::13::jwillia::(Checksum: 3:0fea7fe68b531c0eed581604703efeba)
ALTER TABLE `HR_PAYTYPE_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::14::jwillia::(Checksum: 3:5c06e4e716aed3462486cb6ab70f2076)
ALTER TABLE `HR_POSITION_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::15::jwillia::(Checksum: 3:2ffb6fc02e978912dbbcfd44ae37002a)
ALTER TABLE `HR_PRINCIPAL_ATTRIBUTES_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::16::jwillia::(Checksum: 3:8ddf26b2e6aef87f5b3703e92c714bbf)
ALTER TABLE `HR_SAL_GROUP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::17::jwillia::(Checksum: 3:931481345c067b71fffba716424f9e05)
ALTER TABLE `PM_PSTN_CL_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::18::jwillia::(Checksum: 3:e8ae0bf6a5f6f06450e0e1af74360900)
ALTER TABLE `PM_PSTN_FNDNG_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::19::jwillia::(Checksum: 3:e883541dba98385b4b024315d5f85af4)
ALTER TABLE `PM_PSTN_APPOINTMENT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::20::jwillia::(Checksum: 3:cb1132489857e1080b77cdcd68230528)
ALTER TABLE `PM_PSTN_DEPT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::21::jwillia::(Checksum: 3:eca073c3cdcfb5f5a7e63963455bcf40)
ALTER TABLE `PM_PSTN_DEPT_AFFL_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::22::jwillia::(Checksum: 3:4d8dc7c21135041f049f546f5c55a3e9)
ALTER TABLE `PM_PSTN_FLG_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::23::jwillia::(Checksum: 3:8d3ea400d71337e6f880b9e817d25615)
ALTER TABLE `PM_PSTN_RPT_CAT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::24::jwillia::(Checksum: 3:726d8e258b48a84245317288ce3a945c)
ALTER TABLE `PM_PSTN_RPT_GRP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::25::jwillia::(Checksum: 3:c2f148e1a96ecb17f59bd0dc7a5173f1)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::26::jwillia::(Checksum: 3:1b91ab53aa0360de4824337d9ba68550)
ALTER TABLE `PM_PSTN_RPT_TYP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::27::jwillia::(Checksum: 3:f257a205f576a00fa71829998cbb4c13)
ALTER TABLE `PM_PSTN_RPS_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::28::jwillia::(Checksum: 3:ee57ec0fbc60d7c1f41c2d6c98a0253d)
ALTER TABLE `PSTN_RPS_OPT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::29::jwillia::(Checksum: 3:af1ba82441fd90ac0c6018a3f2394cbb)
ALTER TABLE `PM_PSTN_TYP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::30::jwillia::(Checksum: 3:823860442955d23dcee5f2808fcc1c45)
ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::31::jwillia::(Checksum: 3:57e21530dd6bd37d4fb39ea9337ad7d3)
ALTER TABLE `PM_PSTN_QLFR_TYP_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::32::jwillia::(Checksum: 3:5646cfaa706cd154886cab1cb659c55c)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::33::jwillia::(Checksum: 3:0e0e3ee458d70d3785d66100f2d7a865)
ALTER TABLE `LM_LEAVE_ADJUSTMENT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::34::jwillia::(Checksum: 3:4f5daae2ef16c2ba19a31b72afec525a)
ALTER TABLE `LM_LEAVE_DONATION_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::35::jwillia::(Checksum: 3:49e1e7bee862cc58225a6509f4fd742c)
ALTER TABLE `LM_EMPLOYEE_OVERRIDE_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::36::jwillia::(Checksum: 3:0218d833de01a81ac04de7f35e8cb607)
ALTER TABLE `LM_LEAVE_PAYOUT_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::37::jwillia::(Checksum: 3:4e3bb1fbd8ca6c22fcf599a0911d6da5)
ALTER TABLE `LM_SYS_SCHD_TIMEOFF_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311262013.xml::38::jwillia::(Checksum: 3:ff691ae48668623ae316996fde17f6aa)
ALTER TABLE `LM_BALANCE_TRANSFER_T` ADD `USER_PRINCIPAL_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311271000.xml::1::tkagata::(Checksum: 3:e7950477f0321c24913475b4974ed320)
--  kpme-3016: renamed reportsTo to reportsToPositionId and added reportsToPrincipalId
ALTER TABLE `HR_POSITION_T` CHANGE `REPORTS_TO` `RPT_POS_ID` VARCHAR(40);

ALTER TABLE `HR_POSITION_T` ADD `RPT_PRN_ID` VARCHAR(40);


--  Changeset src/main/config/db/2.1.0/db.changelog-201311271600.xml::1::tkagata::(Checksum: 3:38206259357dbc0af89ea5c662589069)
ALTER TABLE `HR_POSITION_T` MODIFY `RENEW_ELIG` VARCHAR(1);


--  Changeset src/main/config/db/2.1.0/db.changelog-201312021200.xml::1::tkagata::(Checksum: 3:f1ae17f904710611a2799b3beb907eeb)
--  kpme-3016: added pay grade and pay step
ALTER TABLE `HR_POSITION_T` ADD `PAY_GRADE` VARCHAR(10);

ALTER TABLE `HR_POSITION_T` ADD `PAY_STEP` VARCHAR(10);


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::1::jwillia::(Checksum: 3:a6bba52b4736ab46097b78232ea214bc)
ALTER TABLE `LM_ACCRUAL_CATEGORY_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_ACCRUAL_CATEGORY_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::2::jwillia::(Checksum: 3:b076d6696580da304320372d287c8902)
ALTER TABLE `LM_ACCRUAL_CATEGORY_RULES_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_ACCRUAL_CATEGORY_RULES_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::3::jwillia::(Checksum: 3:1593e52b30c15ab0ab6b49d39e5fd9eb)
ALTER TABLE `TK_ASSIGNMENT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_ASSIGNMENT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::4::jwillia::(Checksum: 3:0234b469674e6cd4ced176787fb729cf)
ALTER TABLE `TK_ASSIGN_ACCT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_ASSIGN_ACCT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::5::jwillia::(Checksum: 3:3344b34467c4577fbae8f6f42cd14acf)
ALTER TABLE `HR_DEPT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_DEPT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::6::jwillia::(Checksum: 3:8e38b2c5bef248916edb97d65c32ea03)
ALTER TABLE `HR_EARN_CODE_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_EARN_CODE_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::7::jwillia::(Checksum: 3:5983ff891141b1c4e61f2ace17075eb8)
ALTER TABLE `HR_EARN_CODE_GROUP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_EARN_CODE_GROUP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::8::jwillia::(Checksum: 3:34a859a24b7bb837c65085f6db631f76)
ALTER TABLE `HR_EARN_CODE_SECURITY_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_EARN_CODE_SECURITY_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::9::jwillia::(Checksum: 3:01072dd431c9f58603421084c97a6e00)
ALTER TABLE `PM_INSTITUTION_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_INSTITUTION_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::10::jwillia::(Checksum: 3:f12d30982fb12628c3aaa6c5924152a8)
ALTER TABLE `HR_JOB_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_JOB_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::11::jwillia::(Checksum: 3:cb8431fa2ff05eda317f12a5cccc2b74)
ALTER TABLE `LM_LEAVE_PLAN_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_LEAVE_PLAN_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::12::jwillia::(Checksum: 3:99675225e2e2e079151d8d93760a5453)
ALTER TABLE `PM_PAY_STEP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PAY_STEP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::13::jwillia::(Checksum: 3:53088b701095268eaf3fbd8f36be8e12)
ALTER TABLE `HR_PAYTYPE_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_PAYTYPE_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::14::jwillia::(Checksum: 3:3b4ce7c167578afcdf715b615739754c)
ALTER TABLE `HR_POSITION_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_POSITION_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::15::jwillia::(Checksum: 3:f8b3cbe4e289c4c6294c912a8950ff93)
ALTER TABLE `HR_PRINCIPAL_ATTRIBUTES_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_PRINCIPAL_ATTRIBUTES_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::16::jwillia::(Checksum: 3:7f667b8720ebe30f36245e912690d79d)
ALTER TABLE `HR_SAL_GROUP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_SAL_GROUP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::17::jwillia::(Checksum: 3:cfdff0f843876350abe817945f5c80c8)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_CL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::18::jwillia::(Checksum: 3:0f631f6750b567f52b27351925d3963b)
ALTER TABLE `PM_PSTN_FNDNG_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_FNDNG_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::19::jwillia::(Checksum: 3:23851bba70a5301141421ce5b07636c4)
ALTER TABLE `PM_PSTN_APPOINTMENT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_APPOINTMENT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::20::jwillia::(Checksum: 3:4a820a927da0115133f9df7d15d30959)
ALTER TABLE `PM_PSTN_DEPT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_DEPT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::21::jwillia::(Checksum: 3:579946769fd6420c08949c5fdb52ac1d)
ALTER TABLE `PM_PSTN_DEPT_AFFL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_DEPT_AFFL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::22::jwillia::(Checksum: 3:ed5356915cd67b2dc1106376d26796b9)
ALTER TABLE `PM_PSTN_FLG_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_FLG_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::23::jwillia::(Checksum: 3:ed9cd7849193670cf8726cfa1ff33718)
ALTER TABLE `PM_PSTN_RPT_CAT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_CAT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::24::jwillia::(Checksum: 3:3582d23ae3d330ad35437fb69e51422d)
ALTER TABLE `PM_PSTN_RPT_GRP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_GRP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::25::jwillia::(Checksum: 3:ea1543ae35c9d4f59ec2fc26ab5c2630)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::26::jwillia::(Checksum: 3:a504a424070e55b7dd8a923810a80580)
ALTER TABLE `PM_PSTN_RPT_TYP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_TYP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::27::jwillia::(Checksum: 3:2996ce2d211ce5aa08015855daa9c2cb)
ALTER TABLE `PM_PSTN_RPS_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPS_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::28::jwillia::(Checksum: 3:354d67be184f7bf6661522e7b2b20cb2)
ALTER TABLE `PSTN_RPS_OPT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PSTN_RPS_OPT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::29::jwillia::(Checksum: 3:caa969db1ea03ff41cae934afa8ba6e7)
ALTER TABLE `PM_PSTN_TYP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_TYP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::30::jwillia::(Checksum: 3:fe0b359d654a3af66d2bbe76577f03ba)
ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::31::jwillia::(Checksum: 3:796fe88f1098f471f50a41760bc8c060)
ALTER TABLE `PM_PSTN_QLFR_TYP_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_QLFR_TYP_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::32::jwillia::(Checksum: 3:c1e0ee410d7cd64c785d6de6cb3f429b)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::33::jwillia::(Checksum: 3:2808cef5ed9d8c02fc20a250912b31e3)
ALTER TABLE `LM_LEAVE_ADJUSTMENT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_LEAVE_ADJUSTMENT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::34::jwillia::(Checksum: 3:bd8d57828046d007a9243e54eb20fb45)
ALTER TABLE `LM_LEAVE_DONATION_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_LEAVE_DONATION_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::35::jwillia::(Checksum: 3:e91c4f81aa997e19461c106586829667)
ALTER TABLE `LM_EMPLOYEE_OVERRIDE_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_EMPLOYEE_OVERRIDE_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::36::jwillia::(Checksum: 3:af4fd1ee476243b86298a248eccaa1bc)
ALTER TABLE `LM_LEAVE_PAYOUT_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_LEAVE_PAYOUT_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::37::jwillia::(Checksum: 3:6c7183b970e3f6e19398247619631e21)
ALTER TABLE `LM_SYS_SCHD_TIMEOFF_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_SYS_SCHD_TIMEOFF_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::38::jwillia::(Checksum: 3:3ec4c23aafd20c12f09f93216c516680)
ALTER TABLE `LM_BALANCE_TRANSFER_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `LM_BALANCE_TRANSFER_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::39::jwillia::(Checksum: 3:d89acf3f85388bca66b8b7bfe5b83d2c)
ALTER TABLE `TK_WORK_AREA_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_WORK_AREA_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::40::jwillia::(Checksum: 3:d72bc79bc65c8570692b97ccd39f87cf)
ALTER TABLE `TK_TASK_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_TASK_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::41::jwillia::(Checksum: 3:6ddbfb765c959b3c3cee20dcae0ebc66)
ALTER TABLE `HR_LOCATION_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_LOCATION_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::42::jwillia::(Checksum: 3:ef425fe0bbbec32772fa17a60535ad64)
ALTER TABLE `HR_PAY_GRADE_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `HR_PAY_GRADE_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::43::jwillia::(Checksum: 3:bd6404c87b1bec2bef4872a9f7bd0c0c)
ALTER TABLE `TK_CLOCK_LOCATION_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_CLOCK_LOCATION_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::44::jwillia::(Checksum: 3:7ae5e8b44a93c734145a1c0d5a3d4376)
ALTER TABLE `TK_GRACE_PERIOD_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_GRACE_PERIOD_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::45::jwillia::(Checksum: 3:1b1730f1b8f1e727a2ade616f26fc159)
ALTER TABLE `TK_DEPT_LUNCH_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_DEPT_LUNCH_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::46::jwillia::(Checksum: 3:c29d7e06570b59679edc9b40984f0012)
ALTER TABLE `TK_TIME_COLLECTION_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_TIME_COLLECTION_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::47::jwillia::(Checksum: 3:2a0e132dcf7078f5ff2c91f32cf2c73a)
ALTER TABLE `TK_SYSTEM_LUNCH_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_SYSTEM_LUNCH_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::48::jwillia::(Checksum: 3:42372af5a4da686dbd595f166007b47c)
ALTER TABLE `TK_DAILY_OVERTIME_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_DAILY_OVERTIME_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::49::jwillia::(Checksum: 3:31817b6f260b8e8b1410c56e6ba73929)
ALTER TABLE `TK_WEEKLY_OVERTIME_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_WEEKLY_OVERTIME_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312041513.xml::50::jwillia::(Checksum: 3:494b81bfb2ac5f5b886f53e6642282d2)
ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` MODIFY `USER_PRINCIPAL_ID` VARCHAR(40) NOT NULL;

ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` ALTER `USER_PRINCIPAL_ID` SET DEFAULT 'admin';


--  Changeset src/main/config/db/2.1.0/db.changelog-201312101300.xml::1::jwillia::(Checksum: 3:269f8fc5df0a5ccda2ef064cd0dca37c)
ALTER TABLE `PM_PSTN_DEPT_AFFL_T` CHANGE `PM_PSTN_DEPT_AFFL_ID` `HR_DEPT_AFFL_ID` VARCHAR(60);

ALTER TABLE `PM_PSTN_DEPT_AFFL_T` CHANGE `PSTN_DEPT_AFFL_TYP` `DEPT_AFFL_TYP` VARCHAR(60);


--  Changeset src/main/config/db/2.1.0/db.changelog-201312101300.xml::2::jwillia::(Checksum: 3:b603365abf74c592236ef4a43bafcb65)
ALTER TABLE `PM_PSTN_DEPT_AFFL_T` RENAME `HR_DEPT_AFFL_T`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201312101300.xml::3-mysql::jwillia::(Checksum: 3:b3da8195b7bd94115c24c2782c867dd1)
ALTER TABLE `PM_PSTN_DEPT_AFFL_S` RENAME `HR_DEPT_AFFL_S`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201312101300.xml::4::jwillia::(Checksum: 3:0344212bd2430924641450fd7d0b4b6e)
ALTER TABLE `PM_PSTN_DEPT_T` CHANGE `PSTN_DEPT_AFFL_TYP` `DEPT_AFFL_TYP` VARCHAR(60);


--  Changeset src/main/config/db/2.1.0/db.changelog-201312180104.xml::1::mpritmani::(Checksum: 3:06785c37a30b89f91703f71466ad48ca)
ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `ACTIVE`;

ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `EFFDT`;

ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `TIMESTAMP`;

ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `USER_PRINCIPAL_ID`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201401091510.xml::1::jwillia::(Checksum: 3:0d0b2e39363737a68d3063e250531a35)
ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `location`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201401091510.xml::2::jwillia::(Checksum: 3:c209775f9e2ba2a4d6d271fbc1198d47)
ALTER TABLE `PM_PSTN_RPS_T` DROP COLUMN `institution`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201402060400.xml::1::jkakkad::(Checksum: 3:13e39b6b46265f087d9566e351a1c0af)
--  KPME-3161: Holiday Premium for SSTO
ALTER TABLE `LM_SYS_SCHD_TIMEOFF_T` ADD `PREMIUM_EARN_CODE` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201402071500.xml::1::umehta::(Checksum: 3:7b328495adf2c239da6b2d9c98e944a4)
ALTER TABLE `HR_DEPT_T` ADD `INSTITUTION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201402200930.xml::1::jwillia::(Checksum: 3:3ba72ea726bc07a56b92a9652ff8dfc7)
ALTER TABLE `HR_POSITION_T` MODIFY `PAY_GRADE` VARCHAR(20);

ALTER TABLE `PM_PAY_STEP_T` MODIFY `PAY_GRADE` VARCHAR(20);


--  Changeset src/main/config/db/2.1.0/db.changelog-201402270930.xml::1::jwillia::(Checksum: 3:e0ccd55d437dea011ce5f6d3d97f3326)
ALTER TABLE `PM_PSTN_TYP_T` ADD `ACAD_FLAG` VARCHAR(1);


--  Changeset src/main/config/db/2.1.0/db.changelog-201403181600.xml::1::umehta::(Checksum: 3:8ecc662afdc562f165203d73cdaff8b5)
ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `ACTIVE`;

ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `EFFDT`;

ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `TIMESTAMP`;

ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `USER_PRINCIPAL_ID`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201403191100.xml::1::umehta::(Checksum: 3:1dc14a26b3e2139e610ffefe709a86d7)
ALTER TABLE `PM_PSTN_FNDNG_T` DROP COLUMN `ACTIVE`;

ALTER TABLE `PM_PSTN_FNDNG_T` DROP COLUMN `EFFDT`;

ALTER TABLE `PM_PSTN_FNDNG_T` DROP COLUMN `TIMESTAMP`;

ALTER TABLE `PM_PSTN_FNDNG_T` DROP COLUMN `USER_PRINCIPAL_ID`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404021045.xml::1::jwillia::(Checksum: 3:d981b4b874c1ad4ca9593cabdbe983ad)
ALTER TABLE `HR_DEPT_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `HR_DEPT_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `HR_CALENDAR_ENTRIES_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `HR_CALENDAR_ENTRIES_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `HR_CALENDAR_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `HR_CALENDAR_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `HR_PAY_GRADE_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `HR_PAY_GRADE_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `LM_BALANCE_TRANSFER_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `LM_BALANCE_TRANSFER_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `LM_LEAVE_PAYOUT_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `LM_LEAVE_PAYOUT_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `LM_PRIN_ACCR_RAN_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `LM_PRIN_ACCR_RAN_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `TK_CLOCK_LOC_RL_IP_ADDR_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `TK_CLOCK_LOC_RL_IP_ADDR_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `TK_DAILY_OVERTIME_RL_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `TK_DAILY_OVERTIME_RL_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `TK_USER_PREF_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `TK_USER_PREF_T` ADD `OBJ_ID` VARCHAR(36);

ALTER TABLE `TK_WEEKLY_OVT_GROUP_T` ADD `VER_NBR` DECIMAL(8,0) NOT NULL DEFAULT '1';

ALTER TABLE `TK_WEEKLY_OVT_GROUP_T` ADD `OBJ_ID` VARCHAR(36);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::1-mysql::jjhanso::(Checksum: 3:0b4f916abc15b1622bef828e4421b2cd)
--  another sql file merge
CREATE TABLE HR_GRP_KEY_S (
            ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID)
            ) ENGINE MyISAM;

ALTER TABLE HR_GRP_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::2::jjhanso::(Checksum: 3:37a97d443d3a35f031c1076808c28e5a)
--  another sql file merge
CREATE TABLE `HR_GRP_KEY_T` (`ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `DESCR` VARCHAR(255) NULL, `INSTITUTION` VARCHAR(15) NOT NULL, `CAMPUS_CD` VARCHAR(2) NULL, `LOCATION` VARCHAR(20) NULL, `EFFDT` DATE NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N' NULL, `TIMESTAMP` DATETIME DEFAULT '1970-01-01 00:00:00.0' NULL, `USER_PRINCIPAL_ID` VARCHAR(40) NULL, CONSTRAINT `PK_HR_GRP_KEY_T` PRIMARY KEY (`ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::3::jjhanso::(Checksum: 3:58f0e46e776cf7cc73336ea7a8ab41e3)
--  update constraint for Data Groups
CREATE UNIQUE INDEX `HR_GRP_KEY_IDX1` ON `HR_GRP_KEY_T`(`GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::4::jjhanso::(Checksum: 3:e557a3a92a911c994bd4d197f04dabb4)
INSERT INTO `HR_GRP_KEY_T` (`ACTIVE`, `DESCR`, `EFFDT`, `GRP_KEY_CD`, `ID`, `INSTITUTION`, `OBJ_ID`, `VER_NBR`) VALUES ('Y', 'Basic data grouping', '1970-01-01', 'DEFAULT', 'KPME0001', 'DEFAULT', UUID(), '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::5::jjhanso::(Checksum: 3:0f8c9b766042444dd9aa75791ac6ec73)
ALTER TABLE `TK_ASSIGNMENT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_ASSIGNMENT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_ASSIGNMENT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

DROP INDEX `TK_ASSIGNMENT_IDX1` ON `TK_ASSIGNMENT_T`;

CREATE UNIQUE INDEX `TK_ASSIGNMENT_IDX1` ON `TK_ASSIGNMENT_T`(`PRINCIPAL_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `EFFDT`, `TIMESTAMP`);

DROP INDEX `TK_ASSIGNMENT_IDX2` ON `TK_ASSIGNMENT_T`;

CREATE UNIQUE INDEX `TK_ASSIGNMENT_IDX2` ON `TK_ASSIGNMENT_T`(`PRINCIPAL_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::6::jjhanso::(Checksum: 3:9a496de1bd77513c1593315265ea9668)
ALTER TABLE `TK_CLOCK_LOG_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_CLOCK_LOG_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_CLOCK_LOG_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

DROP INDEX `TK_CLOCK_LOG_IDX1` ON `TK_CLOCK_LOG_T`;

CREATE UNIQUE INDEX `TK_CLOCK_LOG_IDX1` ON `TK_CLOCK_LOG_T`(`PRINCIPAL_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `CLOCK_TS`, `CLOCK_TS_TZ`, `CLOCK_ACTION`, `TIMESTAMP`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::7::jjhanso::(Checksum: 3:76dd2df7a16781e367a9d34d7b368049)
ALTER TABLE `TK_MISSED_PUNCH_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_MISSED_PUNCH_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_MISSED_PUNCH_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

CREATE INDEX `TK_MISSED_PUNCH_IDX1` ON `TK_MISSED_PUNCH_T`(`PRINCIPAL_ID`, `TIMESHEET_DOCUMENT_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `ACTION_DATE`, `CLOCK_ACTION`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::8::jjhanso::(Checksum: 3:329ada88ce48760e50c5a70f5e79b317)
ALTER TABLE `TK_TIME_BLOCK_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_TIME_BLOCK_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_TIME_BLOCK_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

DROP INDEX `TK_TIME_BLOCK_IDX1` ON `TK_TIME_BLOCK_T`;

CREATE UNIQUE INDEX `TK_TIME_BLOCK_IDX1` ON `TK_TIME_BLOCK_T`(`PRINCIPAL_ID`, `DOCUMENT_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `EARN_CODE`, `BEGIN_TS`, `END_TS`, `TIMESTAMP`, `CLOCK_LOG_CREATED`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::9::jjhanso::(Checksum: 3:a33d02f085705d4c7974072b5f4cbaa1)
ALTER TABLE `TK_TIME_BLOCK_HIST_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_TIME_BLOCK_HIST_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_TIME_BLOCK_HIST_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

DROP INDEX `TK_TIME_BLOCK_HIST_IDX3` ON `TK_TIME_BLOCK_HIST_T`;

CREATE INDEX `TK_TIME_BLOCK_HIST_IDX3` ON `TK_TIME_BLOCK_HIST_T`(`PRINCIPAL_ID`, `DOCUMENT_ID`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `EARN_CODE`, `BEGIN_TS`, `END_TS`, `TIMESTAMP`, `CLOCK_LOG_CREATED`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::10::jjhanso::(Checksum: 3:c2df0102fd685d5b195903e8263fa9e5)
ALTER TABLE `LM_LEAVE_BLOCK_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `LM_LEAVE_BLOCK_T` SET `GRP_KEY_CD` = 'DEFAULT';

DROP INDEX `LM_LEAVE_BLOCK_IDX1` ON `LM_LEAVE_BLOCK_T`;

CREATE INDEX `LM_LEAVE_BLOCK_IDX1` ON `LM_LEAVE_BLOCK_T`(`PRINCIPAL_ID`, `EARN_CODE`, `ACCRUAL_CATEGORY`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `LEAVE_BLOCK_TYPE`, `REQUEST_STATUS`, `LEAVE_DATE`, `BEGIN_TS`, `END_TS`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404031000.xml::11::jjhanso::(Checksum: 3:43dab5d258579fa7516a548b48da0c3f)
ALTER TABLE `LM_LEAVE_BLOCK_HIST_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `LM_LEAVE_BLOCK_HIST_T` SET `GRP_KEY_CD` = 'DEFAULT';

DROP INDEX `LM_LEAVE_BLOCK_HIST_IDX1` ON `LM_LEAVE_BLOCK_HIST_T`;

CREATE INDEX `LM_LEAVE_BLOCK_HIST_IDX1` ON `LM_LEAVE_BLOCK_HIST_T`(`PRINCIPAL_ID`, `EARN_CODE`, `ACCRUAL_CATEGORY`, `GRP_KEY_CD`, `JOB_NUMBER`, `WORK_AREA`, `TASK`, `LEAVE_BLOCK_TYPE`, `REQUEST_STATUS`, `LEAVE_DATE`, `BEGIN_TS`, `END_TS`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404101215.xml::1::tkagata::(Checksum: 3:2d70a275386e5a7a83e0224f8b0e224c)
ALTER TABLE `PM_PAY_STEP_T` MODIFY `COMP_RATE` DECIMAL(5,2);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404141100.xml::1::tkagata::(Checksum: 3:208ab09cd1f9815a722d1bcdb2399ccf)
ALTER TABLE `PM_PSTN_APPOINTMENT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_APPOINTMENT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_APPOINTMENT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `PM_PSTN_APPOINTMENT_T` DROP COLUMN `LOCATION`;

ALTER TABLE `PM_PSTN_APPOINTMENT_T` DROP COLUMN `INSTITUTION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404161010.xml::1::jkakkad::(Checksum: 3:05f8fb09c2cddfa0f919248244d10bcc)
ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PSTN_DEPT_T` DROP COLUMN `LOCATION`;

ALTER TABLE `PM_PSTN_DEPT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_DEPT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_DEPT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404171320.xml::1::jkakkad::(Checksum: 3:dd62cbf9e8c79e736aba0532e77b7c49)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` DROP COLUMN `LOCATION`;

ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_RPT_SUB_CAT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404171625.xml::1::neerajsk::(Checksum: 3:6e7c8949df7031e3860360e1378074b0)
--  Adding a non-nullable group key code column to the table and dropping the institution and location columns
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_RPT_GRP_SUB_CAT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` DROP COLUMN `LOCATION`;

ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` DROP COLUMN `INSTITUTION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404181100.xml::1::yingzhou::(Checksum: 3:51e2b3eba08552ca583a8b5fff4ab8c3)
ALTER TABLE `PM_PSTN_RPT_TYP_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_RPT_TYP_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_RPT_TYP_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_TYP_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PSTN_RPT_TYP_T` DROP COLUMN `LOCATION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404211130.xml::1::jkakkad::(Checksum: 3:6ac4f58b68ce36d4e75f9a33e0e12abb)
ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_CNTRCT_TYP_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PSTN_CNTRCT_TYP_T` DROP COLUMN `LOCATION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404221110.xml::1::jkakkad::(Checksum: 3:3ec41707e29f5ff613cfd6d2d1361a36)
ALTER TABLE `HR_PAYTYPE_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `HR_PAYTYPE_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `HR_PAYTYPE_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `HR_PAYTYPE_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `HR_PAYTYPE_T` DROP COLUMN `LOCATION`;

DROP INDEX `HR_PAYTYPE_IDX1` ON `HR_PAYTYPE_T`;

CREATE UNIQUE INDEX `HR_PAYTYPE_IDX1` ON `HR_PAYTYPE_T`(`PAYTYPE`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404221500.xml::1::yingzhou::(Checksum: 3:dc2fda7590e8c906e6ddd9e3191829ca)
ALTER TABLE `PM_PSTN_RPT_CAT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `PM_PSTN_RPT_CAT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `PM_PSTN_RPT_CAT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `PM_PSTN_RPT_CAT_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PSTN_RPT_CAT_T` DROP COLUMN `LOCATION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404231420.xml::1-mysql::jkakkad::(Checksum: 3:8a618e3eddd86f54e0b2853873b2f6b2)
--  KPME-3385 SQLs added for required changes
CREATE TABLE TK_SHIFT_DIFFERENTIAL_RL_TYP_S (
                ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID) 
            ) ENGINE MyISAM;

ALTER TABLE TK_SHIFT_DIFFERENTIAL_RL_TYP_S AUTO_INCREMENT = 1;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404231420.xml::2::jkakkad::(Checksum: 3:891581e10fa89baf644b747a58457977)
--  KPME-3385 SQLs added for required changes
CREATE TABLE `TK_SHIFT_DIFFERENTIAL_RL_TYP_T` (`TK_SHIFT_DIFF_RL_TYP_ID` BIGINT NOT NULL, `NMSPC_CD` VARCHAR(40) NULL, `TYP_NM` VARCHAR(60) NULL, `SERVICE_NM` VARCHAR(100) NULL, `USER_PRINCIPAL_ID` VARCHAR(40) NULL, `EFFDT` DATE NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'Y' NOT NULL, `TIMESTAMP` DATETIME DEFAULT '1970-01-01 00:00:00.0' NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NULL, `OBJ_ID` VARCHAR(36) NULL, CONSTRAINT `PK_TK_SHIFT_DIFFERENTIAL_RL_TYP_T` PRIMARY KEY (`TK_SHIFT_DIFF_RL_TYP_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404231420.xml::3::jkakkad::(Checksum: 3:a271cbb087989f54944f360e7e031b3c)
--  KPME-3385 SQLs added for required changes
ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_T` ADD `RULE_TYP_NM` VARCHAR(60);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404232200.xml::1::tkagata::(Checksum: 3:049732beea21e878f2174249445bc5ea)
ALTER TABLE `HR_DEPT_T` MODIFY `LOCATION` VARCHAR(20) NULL;

ALTER TABLE `HR_DEPT_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `HR_DEPT_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `HR_DEPT_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

DROP INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`;

CREATE UNIQUE INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`(`DEPT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201404241040.xml::1::xichen::(Checksum: 3:75dc192bd5a848d24b80727045bc7b7b)
ALTER TABLE `HR_JOB_T` DROP COLUMN `LOCATION`;

ALTER TABLE `HR_JOB_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `HR_JOB_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `HR_JOB_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201404241715.xml::1::neerajsk::(Checksum: 3:ff9e734cf2798f6244e4da1696ab7a4d)
--  Adding a non-nullable group key code column to the position table and dropping the institution and location columns
ALTER TABLE `HR_POSITION_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `HR_POSITION_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `HR_POSITION_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `HR_POSITION_T` DROP COLUMN `LOCATION`;

ALTER TABLE `HR_POSITION_T` DROP COLUMN `INSTITUTION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405021155.xml::1::xichen::(Checksum: 3:78f6c79fefaa5f8562467fcae9b8ee34)
--  Adding a non-nullable group key code column to tk_clock_location_rl_t
ALTER TABLE `tk_clock_location_rl_t` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `tk_clock_location_rl_t` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `tk_clock_location_rl_t` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405071200.xml::1::tkagata::(Checksum: 3:ad753ef2aaa892c94809a7fbbe329203)
ALTER TABLE `TK_TIME_COLLECTION_RL_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_TIME_COLLECTION_RL_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_TIME_COLLECTION_RL_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405080345.xml::1::xichen::(Checksum: 3:31657c62476d9edb6cbed942f01017b9)
--  Adding a non-nullable group key code column to TK_DAILY_OVERTIME_RL_T
ALTER TABLE `TK_DAILY_OVERTIME_RL_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_DAILY_OVERTIME_RL_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_DAILY_OVERTIME_RL_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;

ALTER TABLE `TK_DAILY_OVERTIME_RL_T` DROP COLUMN `LOCATION`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405081600.xml::1::tkagata::(Checksum: 3:7bac9e9d016b6a6796db0d002a0264ef)
ALTER TABLE `TK_DEPT_LUNCH_RL_T` ADD `GRP_KEY_CD` VARCHAR(30);

UPDATE `TK_DEPT_LUNCH_RL_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_DEPT_LUNCH_RL_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405131320.xml::1::jjhanso::(Checksum: 3:f2f4e0eb10451603e2c030997026cf7a)
ALTER TABLE `TK_SHIFT_DIFFERENTIAL_RL_TYP_T` DROP COLUMN `NMSPC_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405131320.xml::2::jjhanso::(Checksum: 3:4faaf0c90d2e5493dee4a01fd441dcd3)
CREATE UNIQUE INDEX `TK_SHIFT_DIFF_RL_TYP_IDX1` ON `TK_SHIFT_DIFFERENTIAL_RL_TYP_T`(`TYP_NM`, `EFFDT`, `TIMESTAMP`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405131320.xml::3::jjhanso::(Checksum: 3:af2dde253daed864b41c3992cff12f36)
INSERT INTO `TK_SHIFT_DIFFERENTIAL_RL_TYP_T` (`ACTIVE`, `EFFDT`, `OBJ_ID`, `SERVICE_NM`, `TK_SHIFT_DIFF_RL_TYP_ID`, `TYP_NM`, `USER_PRINCIPAL_ID`, `VER_NBR`) VALUES ('Y', '1970-01-01', UUID(), '{http://kpme.kuali.org/tklm/v2_0}shiftTypeServiceBase', '1', 'default', 'admin', '1');


--  Changeset src/main/config/db/2.1.0/db.changelog-201405131320.xml::4::jjhanso::(Checksum: 3:eda72b94a5b7d4c42d09c7c261ba6941)
UPDATE `TK_SHIFT_DIFFERENTIAL_RL_T` SET `RULE_TYP_NM` = 'default';


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151115.xml::1::neerajsk::(Checksum: 3:11a7f9ff9f3e91033fa9e2f978b25ba3)
--  Removing the non-null constraints from the institution and location, we are keeping these columns to accommodate existing data
ALTER TABLE `PM_PSTN_RPT_GRP_T` MODIFY `LOCATION` VARCHAR(15) NULL;

ALTER TABLE `PM_PSTN_RPT_GRP_T` MODIFY `INSTITUTION` VARCHAR(15) NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151210.xml::1-mysql::neerajsk::(Checksum: 3:56a87d7f1529d94dfa24ab07fca561f9)
--  Creating the MySQL sequence table for the position report group key table's primary key
CREATE TABLE PM_PSTN_RPT_GRP_KEY_S (
                ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID) 
            ) ENGINE MyISAM;

ALTER TABLE PM_PSTN_RPT_GRP_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151210.xml::2::neerajsk::(Checksum: 3:37befbb4e7db28a86b860da4b6becc84)
--  Creating the table for the position report group key
CREATE TABLE `PM_PSTN_RPT_GRP_KEY_T` (`PM_PSTN_RPT_GRP_KEY_ID` VARCHAR(60) NOT NULL, `PM_PSTN_RPT_GRP_ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_PM_PSTN_RPT_GRP_KEY_T` PRIMARY KEY (`PM_PSTN_RPT_GRP_KEY_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151210.xml::3::neerajsk::(Checksum: 3:dde72328e2e29daa70d73ce3c6ab4292)
--  Adding the FK constraint referencing the 'owner' position report group
ALTER TABLE `PM_PSTN_RPT_GRP_KEY_T` ADD CONSTRAINT `FK_PSTN_RPT_GRP_KEY` FOREIGN KEY (`PM_PSTN_RPT_GRP_ID`) REFERENCES `PM_PSTN_RPT_GRP_T` (`PM_PSTN_RPT_GRP_ID`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151210.xml::4::neerajsk::(Checksum: 3:08b571e25ac288a06e92d485ba87c3a1)
CREATE UNIQUE INDEX `PM_PSTN_RPT_GRP_KEY_IDX1` ON `PM_PSTN_RPT_GRP_KEY_T`(`PM_PSTN_RPT_GRP_ID`, `GRP_KEY_CD`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405151345.xml::1::mlemons::(Checksum: 3:947d305920d99e8481bab2ba3779f215)
ALTER TABLE `TK_WORK_AREA_T` ADD `GRP_KEY_CD` VARCHAR(30) DEFAULT 'DEFAULT';

UPDATE `TK_WORK_AREA_T` SET `GRP_KEY_CD` = 'DEFAULT';

ALTER TABLE `TK_WORK_AREA_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405161601.xml::1::jkakkad::(Checksum: 3:ed89458668922e2d178c45fc85b2bbcf)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` ADD `LOCATION` VARCHAR(15);

ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` ADD `INSTITUTION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405161601.xml::2::jkakkad::(Checksum: 3:c25186804d8d92753180d415360daada)
ALTER TABLE `PM_PSTN_RPT_SUB_CAT_T` DROP COLUMN `GRP_KEY_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191000.xml::1::jkakkad::(Checksum: 3:9208c730f6135ec7c15f29c84d10bbbe)
ALTER TABLE `PM_PSTN_RPT_CAT_T` ADD `LOCATION` VARCHAR(15);

ALTER TABLE `PM_PSTN_RPT_CAT_T` ADD `INSTITUTION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191000.xml::2::jkakkad::(Checksum: 3:676083fdce701fc48c6bfa5ba558e36f)
ALTER TABLE `PM_PSTN_RPT_CAT_T` DROP COLUMN `GRP_KEY_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191030.xml::1::jkakkad::(Checksum: 3:023dbe23c495339504b2dfbde2615a92)
ALTER TABLE `PM_PSTN_RPT_TYP_T` ADD `LOCATION` VARCHAR(15);

ALTER TABLE `PM_PSTN_RPT_TYP_T` ADD `INSTITUTION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191030.xml::2::jkakkad::(Checksum: 3:91c92b4ed896eda516841052304a1bbb)
ALTER TABLE `PM_PSTN_RPT_TYP_T` DROP COLUMN `GRP_KEY_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191400.xml::1::tkagata::(Checksum: 3:63218e21d427ab38993c4844b784941d)
ALTER TABLE `HR_PAY_GRADE_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `HR_PAY_GRADE_T` DROP COLUMN `LOCATION`;

ALTER TABLE `HR_PAY_GRADE_T` ADD `INSTITUTION` VARCHAR(15);

ALTER TABLE `HR_PAY_GRADE_T` ADD `LOCATION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405191400.xml::2::tkagata::(Checksum: 3:3efe849c620e725888d8e16c381e647a)
ALTER TABLE `PM_PAY_STEP_T` DROP COLUMN `INSTITUTION`;

ALTER TABLE `PM_PAY_STEP_T` DROP COLUMN `LOCATION`;

ALTER TABLE `PM_PAY_STEP_T` ADD `INSTITUTION` VARCHAR(15);

ALTER TABLE `PM_PAY_STEP_T` ADD `LOCATION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405201130.xml::1::umehta::(Checksum: 3:417ed8dde326cb83de083eb288ab5ffd)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` ADD `LOCATION` VARCHAR(15);

ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` ADD `INSTITUTION` VARCHAR(15);


--  Changeset src/main/config/db/2.1.0/db.changelog-201405201130.xml::2::umehta::(Checksum: 3:c234d608179b7068ff911acb00cf0f82)
ALTER TABLE `PM_PSTN_RPT_GRP_SUB_CAT_T` DROP COLUMN `GRP_KEY_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201405201400.xml::1::mlemons::(Checksum: 3:72eaffb5ac93d50d8383769bddc9e38b)
ALTER TABLE `HR_EARN_CODE_SECURITY_T` ADD `GRP_KEY_CD` VARCHAR(30) DEFAULT 'DEFAULT';

UPDATE `HR_EARN_CODE_SECURITY_T` SET `GRP_KEY_CD` = 'DEFAULT';

UPDATE `hr_grp_key_t` SET `LOCATION` = 'IN' WHERE GRP_KEY_CD = 'DEFAULT';

ALTER TABLE `HR_EARN_CODE_SECURITY_T` MODIFY `GRP_KEY_CD` VARCHAR(30) NOT NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031015.xml::1-mysql::jkakkad::(Checksum: 3:c60000a81a2e90a2827f755c2d051a02)
--  Creating the MySQL sequence table for the salary group key table's primary key
CREATE TABLE HR_SAL_GROUP_KEY_S (
                ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID) 
            ) ENGINE MyISAM;

ALTER TABLE HR_SAL_GROUP_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031015.xml::2::jkakkad::(Checksum: 3:37ac0d3b460ca968aa44b8a4ef963d50)
--  Creating the table for the salary group key
CREATE TABLE `HR_SAL_GROUP_KEY_T` (`HR_SAL_GROUP_KEY_ID` VARCHAR(60) NOT NULL, `HR_SAL_GROUP_ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_HR_SAL_GROUP_KEY_T` PRIMARY KEY (`HR_SAL_GROUP_KEY_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031015.xml::3::jkakkad::(Checksum: 3:7d9c6b07db4d87398d03fd9a6119fabe)
--  Adding the FK constraint referencing the 'owner' salary group
ALTER TABLE `HR_SAL_GROUP_KEY_T` ADD CONSTRAINT `FK_SAL_GROUP_KEY` FOREIGN KEY (`HR_SAL_GROUP_ID`) REFERENCES `HR_SAL_GROUP_T` (`HR_SAL_GROUP_ID`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031015.xml::4::jkakkad::(Checksum: 3:fdef793716be4894ebb70a2961eedc87)
CREATE UNIQUE INDEX `HR_SAL_GROUP_KEY_IDX1` ON `HR_SAL_GROUP_KEY_T`(`HR_SAL_GROUP_ID`, `GRP_KEY_CD`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031030.xml::1::jkakkad::(Checksum: 3:affce27bb19fadc970fe11ca7047c5cd)
ALTER TABLE `HR_SAL_GROUP_T` MODIFY `LOCATION` VARCHAR(15) NULL;

ALTER TABLE `HR_SAL_GROUP_T` MODIFY `INSTITUTION` VARCHAR(15) NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031100.xml::1::umehta::(Checksum: 3:964cc96050590014461228ebb71f9e97)
--  Removing the group key code column from the pay type table
ALTER TABLE `HR_PAYTYPE_T` DROP COLUMN `GRP_KEY_CD`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031115.xml::1-mysql::umehta::(Checksum: 3:f2b8b0d95be35c510dcb3d8cd551dc0a)
--  Creating the MySQL sequence table for the pay type key table's primary key
CREATE TABLE HR_PAYTYPE_KEY_S (
			ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID)
			) ENGINE MyISAM;

ALTER TABLE HR_PAYTYPE_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031115.xml::2::umehta::(Checksum: 3:998601e8861d47dd7873cfc6ecdba8a0)
--  Creating the table for the pay type key
CREATE TABLE `HR_PAYTYPE_KEY_T` (`HR_PAYTYPE_KEY_ID` VARCHAR(60) NOT NULL, `HR_PAYTYPE_ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_HR_PAYTYPE_KEY_T` PRIMARY KEY (`HR_PAYTYPE_KEY_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031115.xml::3::umehta::(Checksum: 3:fd809c790bb678365205eab925b29e52)
--  Adding the FK constraint referencing the 'owner' pay type
ALTER TABLE `HR_PAYTYPE_KEY_T` ADD CONSTRAINT `FK_PAYTYPE_KEY` FOREIGN KEY (`HR_PAYTYPE_ID`) REFERENCES `HR_PAYTYPE_T` (`HR_PAYTYPE_ID`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406031115.xml::4::umehta::(Checksum: 3:475f4cdd2417c2c9fc7d9dccf306324a)
CREATE UNIQUE INDEX `HR_PAYTYPE_KEY_IDX1` ON `HR_PAYTYPE_KEY_T`(`HR_PAYTYPE_ID`, `GRP_KEY_CD`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406041220.xml::1-mysql::mlemons::(Checksum: 3:05bdc863dbe9d020c7ff8e6b9bf5951a)
--  Creating the MySQL sequence table for the position type key table's primary key
CREATE TABLE PM_PSTN_TYP_GRP_KEY_S (
    ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID)
    ) ENGINE MyISAM;

ALTER TABLE PM_PSTN_TYP_GRP_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406041220.xml::2::mlemons::(Checksum: 3:b4fbede419865ddf3c4e8251c0ce7a44)
--  Creating the table for the position report group key
CREATE TABLE `PM_PSTN_TYP_GRP_KEY_T` (`PM_PSTN_TYP_GRP_KEY_ID` VARCHAR(60) NOT NULL, `PM_PSTN_TYP_ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_PM_PSTN_TYP_GRP_KEY_T` PRIMARY KEY (`PM_PSTN_TYP_GRP_KEY_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406041220.xml::3::mlemons::(Checksum: 3:b308ceee7ccec28a7fc213884c9a2af6)
--  Adding the FK constraint referencing the 'owner' position type
ALTER TABLE `PM_PSTN_TYP_GRP_KEY_T` ADD CONSTRAINT `FK_PSTN_TYP_GRP_KEY` FOREIGN KEY (`PM_PSTN_TYP_ID`) REFERENCES `PM_PSTN_TYP_T` (`PM_PSTN_TYP_ID`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406041220.xml::4::mlemons::(Checksum: 3:0e80c4a5bea0e54bf120b8d95ddaeb1d)
CREATE UNIQUE INDEX `PM_PSTN_TYP_GRP_KEY_IDX1` ON `PM_PSTN_TYP_GRP_KEY_T`(`PM_PSTN_TYP_ID`, `GRP_KEY_CD`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406061200.xml::1-mysql::mlemons::(Checksum: 3:ba3daee5a4e531ebc8f101ae1fba95dd)
--  Creating the MySQL sequence table for the position type key table's primary key
CREATE TABLE PM_PSTN_CL_GRP_KEY_S (
            ID BIGINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (ID)
            ) ENGINE MyISAM;

ALTER TABLE PM_PSTN_CL_GRP_KEY_S AUTO_INCREMENT = 10000;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406061200.xml::2::mlemons::(Checksum: 3:69b9872028a5faa1a6de288f31651541)
--  Creating the table for the position report group key
CREATE TABLE `PM_PSTN_CL_GRP_KEY_T` (`PM_PSTN_CL_GRP_KEY_ID` VARCHAR(60) NOT NULL, `PM_PSTN_CL_ID` VARCHAR(60) NOT NULL, `GRP_KEY_CD` VARCHAR(30) NOT NULL, `OBJ_ID` VARCHAR(36) NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_PM_PSTN_CL_GRP_KEY_T` PRIMARY KEY (`PM_PSTN_CL_GRP_KEY_ID`)) ENGINE INNODB;


--  Changeset src/main/config/db/2.1.0/db.changelog-201406061200.xml::3::mlemons::(Checksum: 3:2839e56754ce19ca3cf2542bf6da7a31)
--  Adding the FK constraint referencing the 'owner' position type
ALTER TABLE `PM_PSTN_CL_GRP_KEY_T` ADD CONSTRAINT `FK_PSTN_CL_GRP_KEY` FOREIGN KEY (`PM_PSTN_CL_ID`) REFERENCES `PM_PSTN_CL_T` (`PM_PSTN_CL_ID`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406061200.xml::4::mlemons::(Checksum: 3:9c24ac7ab1af949b10d165fbcca5d5f7)
CREATE UNIQUE INDEX `PM_PSTN_CL_GRP_KEY_IDX1` ON `PM_PSTN_CL_GRP_KEY_T`(`PM_PSTN_CL_ID`, `GRP_KEY_CD`);


--  Changeset src/main/config/db/2.1.0/db.changelog-201406121400.xml::1::mlemons::(Checksum: 3:31412408998a8ff09832be5b867458b6)
ALTER TABLE `PM_PSTN_CL_T` MODIFY `LOCATION` VARCHAR(15) NULL;

ALTER TABLE `PM_PSTN_CL_T` MODIFY `INSTITUTION` VARCHAR(15) NULL;

ALTER TABLE `PM_PSTN_TYP_T` MODIFY `LOCATION` VARCHAR(15) NULL;

ALTER TABLE `PM_PSTN_TYP_T` MODIFY `INSTITUTION` VARCHAR(15) NULL;


--  Changeset src/main/config/db/2.1.0/db.changelog-201407081600.xml::1::mlemons::(Checksum: 3:6f71c4006e943f301101acfda59f6736)
DROP INDEX `HR_PAYTYPE_IDX1` ON `HR_PAYTYPE_T`;


--  Changeset src/main/config/db/2.1.0/db.changelog-201407081600.xml::2::mlemons::(Checksum: 3:d64f35de7b70a6af395ba7fcd4c45441)
CREATE UNIQUE INDEX `PM_PSTN_APPOINTMENT_IDX1` ON `PM_PSTN_APPOINTMENT_T`(`PSTN_APPOINTMENT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_CNTRCT_TYP_IDX1` ON `PM_PSTN_CNTRCT_TYP_T`(`NAME`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_RPT_TYP_IDX1` ON `PM_PSTN_RPT_TYP_T`(`PSTN_RPT_TYP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_RPT_GRP_SUB_CAT_IDX1` ON `PM_PSTN_RPT_GRP_SUB_CAT_T`(`PSTN_RPT_GRP`, `PSTN_RPT_SUB_CAT`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_RPT_CAT_IDX1` ON `PM_PSTN_RPT_CAT_T`(`PSTN_RPT_CAT`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`;

CREATE UNIQUE INDEX `HR_DEPT_IDX1` ON `HR_DEPT_T`(`DEPT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_JOB_IDX1` ON `HR_JOB_T`;

CREATE UNIQUE INDEX `HR_JOB_IDX1` ON `HR_JOB_T`(`JOB_NUMBER`, `PRINCIPAL_ID`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `HR_PAYTYPE_IDX1` ON `HR_PAYTYPE_T`(`PAYTYPE`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_PAY_GRADE_IDX1` ON `HR_PAY_GRADE_T`;

CREATE UNIQUE INDEX `HR_PAY_GRADE_IDX1` ON `HR_PAY_GRADE_T`(`SAL_GROUP`, `PAY_GRADE`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_SAL_GROUP_IDX1` ON `HR_SAL_GROUP_T`;

CREATE UNIQUE INDEX `HR_SAL_GROUP_IDX1` ON `HR_SAL_GROUP_T`(`HR_SAL_GROUP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_POSITION_IDX1` ON `HR_POSITION_T`;

CREATE UNIQUE INDEX `HR_POSITION_IDX1` ON `HR_POSITION_T`(`POSITION_NBR`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_CL_IDX1` ON `PM_PSTN_CL_T`(`CL_TTL`, `PSTN_CL`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_DEPT_IDX1` ON `PM_PSTN_DEPT_T`(`HR_PSTN_ID`, `DEPT`, `GRP_KEY_CD`);

CREATE UNIQUE INDEX `PM_PSTN_RPT_GRP_IDX1` ON `PM_PSTN_RPT_GRP_T`(`PSTN_RPT_GRP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_RPT_SUB_CAT_IDX1` ON `PM_PSTN_RPT_SUB_CAT_T`(`PSTN_RPT_SUB_CAT`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PSTN_TYP_IDX1` ON `PM_PSTN_TYP_T`(`PSTN_TYP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `PM_PAY_STEP_IDX1` ON `PM_PAY_STEP_T`(`PAY_STEP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `TK_WORK_AREA_IDX1` ON `TK_WORK_AREA_T`;

DROP INDEX `TK_WORK_AREA_IDX2` ON `TK_WORK_AREA_T`;

CREATE UNIQUE INDEX `TK_WORK_AREA_IDX1` ON `TK_WORK_AREA_T`(`WORK_AREA`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `TK_TIME_COLLECTION_RL_IDX1` ON `TK_TIME_COLLECTION_RL_T`;

CREATE UNIQUE INDEX `TK_TIME_COLLECTION_RL_IDX1` ON `TK_TIME_COLLECTION_RL_T`(`PAY_TYPE`, `DEPT`, `WORK_AREA`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `TK_CLOCK_LOCATION_RL_IDX1` ON `TK_CLOCK_LOCATION_RL_T`(`PRINCIPAL_ID`, `JOB_NUMBER`, `WORK_AREA`, `DEPT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `TK_DEPT_LUNCH_RL_IDX1` ON `TK_DEPT_LUNCH_RL_T`;

CREATE UNIQUE INDEX `TK_DEPT_LUNCH_RL_IDX1` ON `TK_DEPT_LUNCH_RL_T`(`JOB_NUMBER`, `PRINCIPAL_ID`, `WORK_AREA`, `DEPT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE INDEX `TK_SHIFT_DIFFERENTIAL_RL_IDX2` ON `TK_SHIFT_DIFFERENTIAL_RL_T`(`END_TS`, `BEGIN_TS`, `PY_CALENDAR_GROUP`, `EARN_CODE`, `PAY_GRADE`, `HR_SAL_GROUP`, `LOCATION`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

CREATE UNIQUE INDEX `TK_DAILY_OVERTIME_RL_IDX1` ON `TK_DAILY_OVERTIME_RL_T`(`WORK_AREA`, `DEPT`, `PAYTYPE`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

DROP INDEX `HR_EARN_CODE_SECURITY_IDX1` ON `HR_EARN_CODE_SECURITY_T`;

CREATE UNIQUE INDEX `HR_EARN_CODE_SECURITY_IDX1` ON `HR_EARN_CODE_SECURITY_T`(`EARN_CODE`, `HR_SAL_GROUP`, `DEPT`, `GRP_KEY_CD`, `EFFDT`, `TIMESTAMP`, `ACTIVE`);

