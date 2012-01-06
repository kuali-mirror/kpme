-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: /home/tomcat/tna-trunk//src/main/resources/org/kuali/kpme/liquibase/tna/db.changelog-master.xml
-- Ran at: 1/6/12 1:54 PM
-- Against: tk@localhost@jdbc:mysql://localhost:3306/tk-stg?sessionVariables=storage_engine=InnoDB
-- Liquibase version: 2.0.1
-- *********************************************************************

-- Create Database Lock Table


-- Lock Database
-- Create Database Change Log Table

-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-1::djunk (generated)::(Checksum: 3:0a908b8fa73af820e47635dc88ca7f97)
CREATE TABLE `ca_account_t` (`FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `ACCOUNT_NBR` VARCHAR(10) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `ACCOUNT_NM` VARCHAR(40), `ORG_CD` VARCHAR(10), `ACCT_CREATE_DT` DATETIME, `ACCT_EFFECT_DT` DATETIME, `ACCT_EXPIRATION_DT` DATETIME, `ACCT_CLOSED_IND` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-2::djunk (generated)::(Checksum: 3:00c363ffde5532da908004847a226b5a)
CREATE TABLE `ca_chart_t` (`FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `FIN_COA_DESC` VARCHAR(40), `FIN_COA_ACTIVE_CD` VARCHAR(1), CONSTRAINT `PK_CA_CHART_T` PRIMARY KEY (`FIN_COA_CD`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-3::djunk (generated)::(Checksum: 3:abedf74f470e8bcf3215b0db95c5988c)
CREATE TABLE `ca_object_code_t` (`UNIV_FISCAL_YR` DECIMAL(4,0) DEFAULT 0 NOT NULL, `FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `FIN_OBJECT_CD` VARCHAR(5) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `FIN_OBJ_CD_NM` VARCHAR(40), `FIN_OBJ_CD_SHRT_NM` VARCHAR(12), `FIN_OBJ_ACTIVE_CD` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-4::djunk (generated)::(Checksum: 3:cc76d86ebff6c47448a562cfe85d5c75)
CREATE TABLE `ca_org_t` (`FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `ORG_CD` VARCHAR(10) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `ORG_NM` VARCHAR(40), `ORG_ACTIVE_CD` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-5::djunk (generated)::(Checksum: 3:04cb48fbfda59fd7b9543c179128450f)
CREATE TABLE `ca_project_t` (`PROJECT_CD` VARCHAR(10) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `PROJECT_NM` VARCHAR(40), `FIN_COA_CD` VARCHAR(2), `ORG_CD` VARCHAR(10), `PROJ_ACTIVE_CD` VARCHAR(1), `PROJECT_DESC` VARCHAR(400), CONSTRAINT `PK_CA_PROJECT_T` PRIMARY KEY (`PROJECT_CD`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-6::djunk (generated)::(Checksum: 3:b629c0dd0842bcb4d48412b8e07187af)
CREATE TABLE `ca_sub_acct_t` (`FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `ACCOUNT_NBR` VARCHAR(10) DEFAULT '' NOT NULL, `SUB_ACCT_NBR` VARCHAR(5) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `SUB_ACCT_NM` VARCHAR(40), `SUB_ACCT_ACTV_CD` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-7::djunk (generated)::(Checksum: 3:748dd322a0ca8dacd1f28e499d336e73)
CREATE TABLE `ca_sub_object_cd_t` (`UNIV_FISCAL_YR` DECIMAL(4,0) DEFAULT 0 NOT NULL, `FIN_COA_CD` VARCHAR(2) DEFAULT '' NOT NULL, `ACCOUNT_NBR` VARCHAR(10) DEFAULT '' NOT NULL, `FIN_OBJECT_CD` VARCHAR(5) DEFAULT '' NOT NULL, `FIN_SUB_OBJ_CD` VARCHAR(3) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `FIN_SUB_OBJ_CD_NM` VARCHAR(40), `FIN_SUBOBJ_SHRT_NM` VARCHAR(12), `FIN_SUBOBJ_ACTV_CD` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-8::djunk (generated)::(Checksum: 3:38c96684ef7cec2a69b734097dca7d96)
CREATE TABLE `hr_dept_earn_code_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_DEPT_EARN_CODE_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-9::djunk (generated)::(Checksum: 3:f8eaa128497308260346ddc1d083b581)
CREATE TABLE `hr_dept_earn_code_t` (`hr_dept_earn_code_id` VARCHAR(60) NOT NULL, `dept` VARCHAR(21) NOT NULL, `hr_sal_group` VARCHAR(10) NOT NULL, `earn_code` VARCHAR(3) NOT NULL, `employee` VARCHAR(1) DEFAULT 'N', `approver` VARCHAR(1) DEFAULT 'N', `org_admin` VARCHAR(1) DEFAULT 'N', `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `location` VARCHAR(10), `active` VARCHAR(1) DEFAULT 'N', `effdt` DATE, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_HR_DEPT_EARN_CODE_T` PRIMARY KEY (`hr_dept_earn_code_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-10::djunk (generated)::(Checksum: 3:3ab5e52d07e151049a68e4b4f137a5c5)
CREATE TABLE `hr_dept_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_DEPT_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-11::djunk (generated)::(Checksum: 3:1d1edb4b5ecc91be52da809ab7447f8d)
CREATE TABLE `hr_dept_t` (`HR_DEPT_ID` BIGINT NOT NULL, `dept` VARCHAR(21), `DESCRIPTION` VARCHAR(75), `ORG` VARCHAR(10), `CHART` VARCHAR(10), `effdt` DATE, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `active` VARCHAR(1) DEFAULT 'N', `location` VARCHAR(20) NOT NULL, CONSTRAINT `PK_HR_DEPT_T` PRIMARY KEY (`HR_DEPT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-12::djunk (generated)::(Checksum: 3:23a561220a9a9a3da88926269a335fc1)
CREATE TABLE `hr_earn_code_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_EARN_CODE_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-13::djunk (generated)::(Checksum: 3:89dd0667f37e773f8e0da176785c466a)
CREATE TABLE `hr_earn_code_t` (`hr_earn_code_id` INT NOT NULL, `earn_code` VARCHAR(3) NOT NULL, `descr` VARCHAR(30), `effdt` DATE, `ovt_earn_code` CHAR(1) DEFAULT 'N' NOT NULL, `record_time` VARCHAR(1) DEFAULT 'N', `record_amount` VARCHAR(1) DEFAULT 'N', `record_hours` VARCHAR(1) DEFAULT 'N', `ACTIVE` VARCHAR(1), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `ACCRUAL_CATEGORY` VARCHAR(3), `inflate_min_hours` DECIMAL(3,2) DEFAULT 0 NOT NULL, `inflate_factor` DECIMAL(3,2) DEFAULT 1 NOT NULL, CONSTRAINT `PK_HR_EARN_CODE_T` PRIMARY KEY (`hr_earn_code_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-14::djunk (generated)::(Checksum: 3:f8d060adf2965bb96c691633d7519263)
CREATE TABLE `hr_earn_group_def_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_EARN_GROUP_DEF_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-15::djunk (generated)::(Checksum: 3:e47f5796a6dd890f90ddd74ab218e9e6)
CREATE TABLE `hr_earn_group_def_t` (`hr_earn_group_id` BIGINT NOT NULL, `earn_code` VARCHAR(3), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `hr_earn_group_def_id` BIGINT NOT NULL, CONSTRAINT `PK_HR_EARN_GROUP_DEF_T` PRIMARY KEY (`hr_earn_group_def_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-16::djunk (generated)::(Checksum: 3:15cefe95c22512812026c3f31c89595b)
CREATE TABLE `hr_earn_group_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_EARN_GROUP_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-17::djunk (generated)::(Checksum: 3:05e38f1ffb0ae805ff05e44bb4c44627)
CREATE TABLE `hr_earn_group_t` (`hr_earn_group_id` BIGINT NOT NULL, `earn_group` VARCHAR(10), `descr` VARCHAR(30), `show_summary` VARCHAR(1) DEFAULT 'N', `effdt` DATE, `active` VARCHAR(1), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_HR_EARN_GROUP_T` PRIMARY KEY (`hr_earn_group_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-18::djunk (generated)::(Checksum: 3:dd98cf0a839df182dc1922956c89c37e)
CREATE TABLE `hr_holiday_calendar_dates_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_DATES_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-19::djunk (generated)::(Checksum: 3:da464cc64b5c3f7630ec2eff73e0927c)
CREATE TABLE `hr_holiday_calendar_dates_t` (`HR_HOLIDAY_CALENDAR_DATES_ID` VARCHAR(60) NOT NULL, `HOLIDAY_DATE` DATE NOT NULL, `HOLIDAY_DESC` VARCHAR(30) NOT NULL, `HR_HOLIDAY_CALENDAR_ID` VARCHAR(60) NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `HOLIDAY_HRS` DECIMAL(4,2) NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_DATES_T` PRIMARY KEY (`HR_HOLIDAY_CALENDAR_DATES_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-20::djunk (generated)::(Checksum: 3:fde6efcc06a92acf68e445d63c59db25)
CREATE TABLE `hr_holiday_calendar_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-21::djunk (generated)::(Checksum: 3:41eb52b1d8cf46b67342f5e6d41288e8)
CREATE TABLE `hr_holiday_calendar_t` (`HR_HOLIDAY_CALENDAR_ID` VARCHAR(60) NOT NULL, `HOLIDAY_CALENDAR_GROUP` VARCHAR(3) NOT NULL, `DESCR` VARCHAR(30), `location` VARCHAR(30), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `active` VARCHAR(1) DEFAULT 'Y' NOT NULL, CONSTRAINT `PK_HR_HOLIDAY_CALENDAR_T` PRIMARY KEY (`HR_HOLIDAY_CALENDAR_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-22::djunk (generated)::(Checksum: 3:ff184b57dd2810bd0455f82d1234c9c2)
CREATE TABLE `hr_job_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_JOB_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-23::djunk (generated)::(Checksum: 3:81298b44ac3d603d54594f5366dc25ed)
CREATE TABLE `hr_job_t` (`HR_JOB_ID` BIGINT NOT NULL, `PRINCIPAL_ID` VARCHAR(40), `JOB_NUMBER` BIGINT, `EFFDT` DATE DEFAULT '0002-11-30' NOT NULL, `dept` VARCHAR(90) NOT NULL, `HR_SAL_GROUP` VARCHAR(10), `pay_grade` VARCHAR(20), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `comp_rate` DECIMAL(18,6) DEFAULT 0, `location` VARCHAR(20), `std_hours` DECIMAL(5,2), `fte` BIT, `hr_paytype` VARCHAR(5), `active` VARCHAR(1) DEFAULT 'N', `primary_indicator` VARCHAR(1) DEFAULT 'N', `position_nbr` VARCHAR(20), CONSTRAINT `PK_HR_JOB_T` PRIMARY KEY (`HR_JOB_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-24::djunk (generated)::(Checksum: 3:d8f15a61473bb62e3bdf033ce8d25bb3)
CREATE TABLE `hr_location_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_LOCATION_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-25::djunk (generated)::(Checksum: 3:4e679d25fc052f7bac8ac56df2accf44)
CREATE TABLE `hr_location_t` (`hr_location_id` VARCHAR(60) NOT NULL, `location` VARCHAR(20) NOT NULL, `timezone` VARCHAR(30) DEFAULT 'America\Indianapolis' NOT NULL, `description` VARCHAR(60) NOT NULL, `effdt` DATE NOT NULL, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `active` CHAR(1) NOT NULL, `user_principal_id` VARCHAR(40), CONSTRAINT `PK_HR_LOCATION_T` PRIMARY KEY (`hr_location_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-26::djunk (generated)::(Checksum: 3:98c16a67bb47bd8db180a7df98fb6075)
CREATE TABLE `hr_pay_grade_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_PAY_GRADE_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-27::djunk (generated)::(Checksum: 3:27fabcc5f46ad058f491ab0fe87c4202)
CREATE TABLE `hr_pay_grade_t` (`hr_pay_grade_id` VARCHAR(60) NOT NULL, `pay_grade` VARCHAR(20) NOT NULL, `description` VARCHAR(40) NOT NULL, `effdt` DATE NOT NULL, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `active` CHAR(1) NOT NULL, `user_principal_id` VARCHAR(40), CONSTRAINT `PK_HR_PAY_GRADE_T` PRIMARY KEY (`hr_pay_grade_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-28::djunk (generated)::(Checksum: 3:00d821dc9cb7717e55731b4e5edf22ed)
CREATE TABLE `hr_paytype_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_PAYTYPE_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-29::djunk (generated)::(Checksum: 3:cb8a4c06212f78ec386b96328bc2c914)
CREATE TABLE `hr_paytype_t` (`HR_PAYTYPE_ID` VARCHAR(60) NOT NULL, `PAYTYPE` VARCHAR(5) NOT NULL, `DESCR` VARCHAR(90), `REG_ERN_CODE` VARCHAR(3) NOT NULL, `EFFDT` DATE NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N', CONSTRAINT `PK_HR_PAYTYPE_T` PRIMARY KEY (`HR_PAYTYPE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-30::djunk (generated)::(Checksum: 3:22c8582157638ef98e643763386373fc)
CREATE TABLE `hr_position_id_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_POSITION_ID_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-31::djunk (generated)::(Checksum: 3:a0977afee7654832f34510700a5bd9de)
CREATE TABLE `hr_position_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_POSITION_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-32::djunk (generated)::(Checksum: 3:96022125222c4d927a0979e5dd0463ca)
CREATE TABLE `hr_position_t` (`HR_POSITION_ID` VARCHAR(60) NOT NULL, `POSITION_NBR` VARCHAR(20) NOT NULL, `DESCRIPTION` VARCHAR(40) NOT NULL, `EFFDT` DATE NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N', CONSTRAINT `PK_HR_POSITION_T` PRIMARY KEY (`HR_POSITION_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-33::djunk (generated)::(Checksum: 3:f81d3c1160621b92ac3e8878f5811d17)
CREATE TABLE `hr_principal_calendar_t` (`principal_id` VARCHAR(40) DEFAULT '' NOT NULL, `py_calendar_group` VARCHAR(45) DEFAULT '' NOT NULL, `holiday_calendar_group` VARCHAR(45), `timezone` VARCHAR(30), `EFFDT` DATE DEFAULT '0002-11-30' NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `active` VARCHAR(1) DEFAULT 'Y', CONSTRAINT `PK_HR_PRINCIPAL_CALENDAR_T` PRIMARY KEY (`principal_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-34::djunk (generated)::(Checksum: 3:4d520a22d0a0e3c654227b899c80edc0)
CREATE TABLE `hr_py_calendar_entries_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_PY_CALENDAR_ENTRIES_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-35::djunk (generated)::(Checksum: 3:abeb725c1c319ababfb76f28805b7191)
CREATE TABLE `hr_py_calendar_entries_t` (`hr_py_calendar_entry_id` BIGINT NOT NULL, `hr_py_calendar_id` BIGINT, `py_calendar_group` VARCHAR(45) NOT NULL, `begin_period_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `end_period_date` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `initiate_date` DATE, `initiate_time` TIME, `end_pay_period_date` DATE, `end_pay_period_time` TIME, `employee_approval_date` DATE, `employee_approval_time` TIME, `supervisor_approval_date` DATE, `supervisor_approval_time` TIME, CONSTRAINT `PK_HR_PY_CALENDAR_ENTRIES_T` PRIMARY KEY (`hr_py_calendar_entry_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-36::djunk (generated)::(Checksum: 3:5e5e83ea76d85f56c8f2efc124618e6f)
CREATE TABLE `hr_py_calendar_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_PY_CALENDAR_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-37::djunk (generated)::(Checksum: 3:0a1d9111af5ddf65af5b0f61135edf05)
CREATE TABLE `hr_py_calendar_t` (`hr_py_calendar_id` BIGINT NOT NULL, `py_calendar_group` VARCHAR(45) NOT NULL, `flsa_begin_day` VARCHAR(9) NOT NULL, `flsa_begin_time` TIME NOT NULL, `active` VARCHAR(1) DEFAULT 'Y' NOT NULL, CONSTRAINT `PK_HR_PY_CALENDAR_T` PRIMARY KEY (`hr_py_calendar_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-38::djunk (generated)::(Checksum: 3:0fe1a3ac1f32063ce74340bab856779b)
CREATE TABLE `hr_roles_group_t` (`principal_id` VARCHAR(20) NOT NULL, CONSTRAINT `PK_HR_ROLES_GROUP_T` PRIMARY KEY (`principal_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-39::djunk (generated)::(Checksum: 3:4da747874e35f8e36c3f5e14ce9a7698)
CREATE TABLE `hr_roles_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_ROLES_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-40::djunk (generated)::(Checksum: 3:47f3255420a3a81ad41dbaacc69711af)
CREATE TABLE `hr_roles_t` (`hr_roles_id` VARCHAR(60) NOT NULL, `principal_id` VARCHAR(40), `role_name` VARCHAR(20) NOT NULL, `user_principal_id` VARCHAR(40), `work_area` BIGINT, `dept` VARCHAR(21), `chart` VARCHAR(10), `effdt` DATE NOT NULL, `expdt` DATE, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `active` VARCHAR(1) DEFAULT 'Y' NOT NULL, `position_nbr` VARCHAR(20), CONSTRAINT `PK_HR_ROLES_T` PRIMARY KEY (`hr_roles_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-41::djunk (generated)::(Checksum: 3:c93f82b97775ea9b68a2b101550f31a4)
CREATE TABLE `hr_sal_group_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_SAL_GROUP_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-42::djunk (generated)::(Checksum: 3:7d3f9ecf1a3796429206dd286e2292bf)
CREATE TABLE `hr_sal_group_t` (`HR_SAL_GROUP_ID` VARCHAR(60) NOT NULL, `HR_SAL_GROUP` VARCHAR(10) NOT NULL, `EFFDT` DATE NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `ACTIVE` VARCHAR(1) NOT NULL, `OBJ_ID` VARCHAR(38) DEFAULT '1' NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `descr` VARCHAR(30), CONSTRAINT `PK_HR_SAL_GROUP_T` PRIMARY KEY (`HR_SAL_GROUP_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-43::djunk (generated)::(Checksum: 3:2fcdf37758e66f3c8641cf66c7845197)
CREATE TABLE `hr_work_schedule_assign_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_ASSIGN_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-44::djunk (generated)::(Checksum: 3:7c32071d2b3c334e0e5980a5199084bc)
CREATE TABLE `hr_work_schedule_assign_t` (`HR_WORK_SCHEDULE_ASSIGNMENT_ID` BIGINT NOT NULL, `HR_WORK_SCHEDULE` BIGINT NOT NULL, `DEPT` VARCHAR(21) NOT NULL, `WORK_AREA` BIGINT, `PRINCIPAL_ID` VARCHAR(40), `USER_PRINCIPAL_ID` VARCHAR(40), `ACTIVE` VARCHAR(1) DEFAULT 'Y' NOT NULL, `EFFDT` DATE DEFAULT '0002-11-30' NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_ASSIGN_T` PRIMARY KEY (`HR_WORK_SCHEDULE_ASSIGNMENT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-45::djunk (generated)::(Checksum: 3:a5baad2759762d6914a300045de4e097)
CREATE TABLE `hr_work_schedule_entry_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_ENTRY_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-46::djunk (generated)::(Checksum: 3:9e415834984666f8f7547db0b09a7b35)
CREATE TABLE `hr_work_schedule_entry_t` (`HR_WORK_SCHEDULE_ENTRY_ID` BIGINT NOT NULL, `HR_WORK_SCHEDULE` BIGINT NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `index_of_day` DECIMAL(8,0) DEFAULT 0 NOT NULL, `begin_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `end_time` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_ENTRY_T` PRIMARY KEY (`HR_WORK_SCHEDULE_ENTRY_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-47::djunk (generated)::(Checksum: 3:5b37c1d06b6f2d753598e850e99e5f00)
CREATE TABLE `hr_work_schedule_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-48::djunk (generated)::(Checksum: 3:7db3659b3928aeff64a327586a2823eb)
CREATE TABLE `hr_work_schedule_t` (`HR_WORK_SCHEDULE_ID` BIGINT NOT NULL, `HR_WORK_SCHEDULE` BIGINT NOT NULL, `WORK_SCHEDULE_DESC` VARCHAR(30) NOT NULL, `EFFDT` DATE DEFAULT '0002-11-30' NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N', `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_HR_WORK_SCHEDULE_T` PRIMARY KEY (`HR_WORK_SCHEDULE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-49::djunk (generated)::(Checksum: 3:81a736eec180e66c79694bb5d47f4c4b)
CREATE TABLE `kr_unittest_t` (`foo` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-50::djunk (generated)::(Checksum: 3:1c60e43b56192fcc12773bba5a8e3b13)
CREATE TABLE `krns_adhoc_rte_actn_recip_t` (`RECIP_TYP_CD` DECIMAL(1,0) DEFAULT 0 NOT NULL, `ACTN_RQST_CD` VARCHAR(30) DEFAULT '' NOT NULL, `ACTN_RQST_RECIP_ID` VARCHAR(70) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `DOC_HDR_ID` VARCHAR(14) DEFAULT '' NOT NULL);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-51::djunk (generated)::(Checksum: 3:d7887989e88087e906593c2f83ed7374)
CREATE TABLE `krns_att_t` (`NTE_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `MIME_TYP` VARCHAR(255), `FILE_NM` VARCHAR(250), `ATT_ID` VARCHAR(36), `FILE_SZ` DECIMAL(14,0), `ATT_TYP_CD` VARCHAR(40), CONSTRAINT `PK_KRNS_ATT_T` PRIMARY KEY (`NTE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-52::djunk (generated)::(Checksum: 3:c21a8d18e7f46457af95b765ef2e318a)
CREATE TABLE `krns_doc_hdr_t` (`DOC_HDR_ID` VARCHAR(14) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `FDOC_DESC` VARCHAR(40), `ORG_DOC_HDR_ID` VARCHAR(10), `TMPL_DOC_HDR_ID` VARCHAR(14), `EXPLANATION` VARCHAR(400), CONSTRAINT `PK_KRNS_DOC_HDR_T` PRIMARY KEY (`DOC_HDR_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-53::djunk (generated)::(Checksum: 3:9ed7954f7f61c06a9c88f16a0f01c03f)
CREATE TABLE `krns_lock_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRNS_LOCK_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-54::djunk (generated)::(Checksum: 3:3907af8a025ffdede927c33bdbe8ac30)
CREATE TABLE `krns_lookup_rslt_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRNS_LOOKUP_RSLT_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-55::djunk (generated)::(Checksum: 3:0a023942fec64a9c687f69d09e88e74b)
CREATE TABLE `krns_lookup_rslt_t` (`LOOKUP_RSLT_ID` VARCHAR(14) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `PRNCPL_ID` VARCHAR(40) NOT NULL, `LOOKUP_DT` DATETIME NOT NULL, `SERIALZD_RSLTS` LONGTEXT, CONSTRAINT `PK_KRNS_LOOKUP_RSLT_T` PRIMARY KEY (`LOOKUP_RSLT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-56::djunk (generated)::(Checksum: 3:4aa09a39ef4a6891c53ef36238f9a304)
CREATE TABLE `krns_lookup_sel_t` (`LOOKUP_RSLT_ID` VARCHAR(14) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `PRNCPL_ID` VARCHAR(40) NOT NULL, `LOOKUP_DT` DATETIME NOT NULL, `SEL_OBJ_IDS` LONGTEXT, CONSTRAINT `PK_KRNS_LOOKUP_SEL_T` PRIMARY KEY (`LOOKUP_RSLT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-57::djunk (generated)::(Checksum: 3:c9b009d8d9f58bc812decdff61d10972)
CREATE TABLE `krns_maint_doc_att_t` (`DOC_HDR_ID` VARCHAR(14) DEFAULT '' NOT NULL, `ATT_CNTNT` LONGBLOB NOT NULL, `FILE_NM` VARCHAR(150), `CNTNT_TYP` VARCHAR(255), `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, CONSTRAINT `PK_KRNS_MAINT_DOC_ATT_T` PRIMARY KEY (`DOC_HDR_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-58::djunk (generated)::(Checksum: 3:df1d6aa0271586f1a36073e34ce2148b)
CREATE TABLE `krns_maint_doc_t` (`DOC_HDR_ID` VARCHAR(14) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `DOC_CNTNT` LONGTEXT, CONSTRAINT `PK_KRNS_MAINT_DOC_T` PRIMARY KEY (`DOC_HDR_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-59::djunk (generated)::(Checksum: 3:7d7ea87844176fa87caf5cb179d10340)
CREATE TABLE `krns_maint_lock_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRNS_MAINT_LOCK_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-60::djunk (generated)::(Checksum: 3:3fb4633080584e469e5173cc07d00a82)
CREATE TABLE `krns_maint_lock_t` (`MAINT_LOCK_REP_TXT` VARCHAR(500), `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `DOC_HDR_ID` VARCHAR(14) NOT NULL, `MAINT_LOCK_ID` VARCHAR(14) DEFAULT '' NOT NULL, CONSTRAINT `PK_KRNS_MAINT_LOCK_T` PRIMARY KEY (`MAINT_LOCK_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-61::djunk (generated)::(Checksum: 3:ae0c62b599f6eead4588376808e23723)
CREATE TABLE `krns_nte_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRNS_NTE_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-62::djunk (generated)::(Checksum: 3:3ad6725d8f2a9e2a0c5cecd7c7efc86e)
CREATE TABLE `krns_nte_t` (`NTE_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `RMT_OBJ_ID` VARCHAR(36) NOT NULL, `AUTH_PRNCPL_ID` VARCHAR(40) NOT NULL, `POST_TS` DATETIME NOT NULL, `NTE_TYP_CD` VARCHAR(4) NOT NULL, `TXT` VARCHAR(800), `PRG_CD` VARCHAR(1), `TPC_TXT` VARCHAR(40), CONSTRAINT `PK_KRNS_NTE_T` PRIMARY KEY (`NTE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-63::djunk (generated)::(Checksum: 3:ac955fb31baf8390e9d71e3599ec455c)
CREATE TABLE `krns_nte_typ_t` (`NTE_TYP_CD` VARCHAR(4) DEFAULT '' NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `TYP_DESC_TXT` VARCHAR(100), `ACTV_IND` VARCHAR(1), CONSTRAINT `PK_KRNS_NTE_TYP_T` PRIMARY KEY (`NTE_TYP_CD`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-64::djunk (generated)::(Checksum: 3:de456e6816a684544c6cabf8f54e57ee)
CREATE TABLE `krns_pessimistic_lock_t` (`PESSIMISTIC_LOCK_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `LOCK_DESC_TXT` VARCHAR(4000), `DOC_HDR_ID` VARCHAR(14) NOT NULL, `GNRT_DT` DATETIME NOT NULL, `PRNCPL_ID` VARCHAR(40) NOT NULL, CONSTRAINT `PK_KRNS_PESSIMISTIC_LOCK_T` PRIMARY KEY (`PESSIMISTIC_LOCK_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-65::djunk (generated)::(Checksum: 3:84b476b2ca98fec06461f2372af283ff)
CREATE TABLE `krns_sesn_doc_t` (`SESN_DOC_ID` VARCHAR(40) DEFAULT '' NOT NULL, `DOC_HDR_ID` VARCHAR(14) DEFAULT '' NOT NULL, `PRNCPL_ID` VARCHAR(40) DEFAULT '' NOT NULL, `IP_ADDR` VARCHAR(60) DEFAULT '' NOT NULL, `SERIALZD_DOC_FRM` LONGBLOB, `LAST_UPDT_DT` DATETIME, `CONTENT_ENCRYPTED_IND` CHAR(1) DEFAULT 'N');


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-66::djunk (generated)::(Checksum: 3:35e8813d5cae3fe42f6ccd6451f4ab01)
CREATE TABLE `krsb_bam_parm_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRSB_BAM_PARM_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-67::djunk (generated)::(Checksum: 3:b7d184d8de9b087490fabb214f275afa)
CREATE TABLE `krsb_bam_parm_t` (`BAM_PARM_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `BAM_ID` DECIMAL(14,0) NOT NULL, `PARM` LONGTEXT NOT NULL, CONSTRAINT `PK_KRSB_BAM_PARM_T` PRIMARY KEY (`BAM_PARM_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-68::djunk (generated)::(Checksum: 3:12c23c1dff43aa30a48e603592fde17b)
CREATE TABLE `krsb_bam_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRSB_BAM_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-69::djunk (generated)::(Checksum: 3:25ec79deb2fd0befd2977d0f779e7d70)
CREATE TABLE `krsb_bam_t` (`BAM_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `SVC_NM` VARCHAR(255) NOT NULL, `SVC_URL` VARCHAR(500) NOT NULL, `MTHD_NM` VARCHAR(2000) NOT NULL, `THRD_NM` VARCHAR(500) NOT NULL, `CALL_DT` DATETIME NOT NULL, `TGT_TO_STR` VARCHAR(2000) NOT NULL, `SRVR_IND` DECIMAL(1,0) NOT NULL, `EXCPN_TO_STR` VARCHAR(2000), `EXCPN_MSG` LONGTEXT, CONSTRAINT `PK_KRSB_BAM_T` PRIMARY KEY (`BAM_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-70::djunk (generated)::(Checksum: 3:7e90583d434b9947be749e902c6c04ba)
CREATE TABLE `krsb_msg_pyld_t` (`MSG_QUE_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `MSG_PYLD` LONGTEXT NOT NULL, CONSTRAINT `PK_KRSB_MSG_PYLD_T` PRIMARY KEY (`MSG_QUE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-71::djunk (generated)::(Checksum: 3:b86170526db03a37f4be9206deb97733)
CREATE TABLE `krsb_msg_que_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRSB_MSG_QUE_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-72::djunk (generated)::(Checksum: 3:38d1a8cebb701b174b1f761e8f814423)
CREATE TABLE `krsb_msg_que_t` (`MSG_QUE_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `DT` DATETIME NOT NULL, `EXP_DT` DATETIME, `PRIO` DECIMAL(8,0) NOT NULL, `STAT_CD` CHAR(1) NOT NULL, `RTRY_CNT` DECIMAL(8,0) NOT NULL, `IP_NBR` VARCHAR(2000) NOT NULL, `SVC_NM` VARCHAR(255), `SVC_NMSPC` VARCHAR(255) NOT NULL, `SVC_MTHD_NM` VARCHAR(2000), `APP_VAL_ONE` VARCHAR(2000), `APP_VAL_TWO` VARCHAR(2000), `VER_NBR` DECIMAL(8,0) DEFAULT 0, CONSTRAINT `PK_KRSB_MSG_QUE_T` PRIMARY KEY (`MSG_QUE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-73::djunk (generated)::(Checksum: 3:f6e93a41a73982fc7851aa137f12c0d1)
CREATE TABLE `krsb_svc_def_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_KRSB_SVC_DEF_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-74::djunk (generated)::(Checksum: 3:5ec9952917dd2e86ea52679a2d31a7ae)
CREATE TABLE `krsb_svc_def_t` (`SVC_DEF_ID` DECIMAL(14,0) DEFAULT 0 NOT NULL, `SVC_NM` VARCHAR(255) NOT NULL, `SVC_URL` VARCHAR(500) NOT NULL, `SRVR_IP` VARCHAR(40) NOT NULL, `SVC_NMSPC` VARCHAR(255) NOT NULL, `SVC_ALIVE` DECIMAL(1,0) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 0, `FLT_SVC_DEF_ID` DECIMAL(14,0) NOT NULL, `SVC_DEF_CHKSM` VARCHAR(30) NOT NULL, CONSTRAINT `PK_KRSB_SVC_DEF_T` PRIMARY KEY (`SVC_DEF_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-75::djunk (generated)::(Checksum: 3:984a89989bd518077b0f8568c11987e7)
CREATE TABLE `lm_accrual_categories_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_LM_ACCRUAL_CATEGORIES_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-76::djunk (generated)::(Checksum: 3:b960d39f51e30a1a97ddb92442db24b5)
CREATE TABLE `lm_accrual_categories_t` (`LM_ACCRUAL_CATEGORY_ID` VARCHAR(60) NOT NULL, `ACCRUAL_CATEGORY` VARCHAR(3) NOT NULL, `DESCR` VARCHAR(30) NOT NULL, `EFFDT` DATE NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N', `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_LM_ACCRUAL_CATEGORIES_T` PRIMARY KEY (`LM_ACCRUAL_CATEGORY_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-77::djunk (generated)::(Checksum: 3:9ac3808bc61bb5ee37f2590fc9d344d4)
CREATE TABLE `lm_accruals_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_LM_ACCRUALS_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-78::djunk (generated)::(Checksum: 3:7994ef914b1fd3e648a71609cd565715)
CREATE TABLE `lm_accruals_t` (`LM_ACCRUALS_ID` VARCHAR(60) NOT NULL, `PRINCIPAL_ID` VARCHAR(21) NOT NULL, `ACCRUAL_CATEGORY` VARCHAR(3) NOT NULL, `EFFDT` DATETIME NOT NULL, `HOURS_ACCRUED` DECIMAL(6,2) NOT NULL, `HOURS_TAKEN` DECIMAL(6,2) NOT NULL, `HOURS_ADJUST` DECIMAL(6,2) NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_LM_ACCRUALS_T` PRIMARY KEY (`LM_ACCRUALS_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-79::djunk (generated)::(Checksum: 3:4cc89349e6b0f2a41d933400e75262e5)
CREATE TABLE `qrtz_blob_triggers` (`TRIGGER_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `BLOB_DATA` LONGBLOB);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-80::djunk (generated)::(Checksum: 3:2427d730c393506c0e7b525648efa509)
CREATE TABLE `qrtz_calendars` (`CALENDAR_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `CALENDAR` LONGBLOB NOT NULL, CONSTRAINT `PK_QRTZ_CALENDARS` PRIMARY KEY (`CALENDAR_NAME`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-81::djunk (generated)::(Checksum: 3:47bb31871ae999a23ecb5aa4de0e956e)
CREATE TABLE `qrtz_cron_triggers` (`TRIGGER_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `CRON_EXPRESSION` VARCHAR(80) NOT NULL, `TIME_ZONE_ID` VARCHAR(80));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-82::djunk (generated)::(Checksum: 3:8f3acc597a5717d3c619fd3a687ef690)
CREATE TABLE `qrtz_fired_triggers` (`ENTRY_ID` VARCHAR(95) DEFAULT '' NOT NULL, `TRIGGER_NAME` VARCHAR(80) NOT NULL, `TRIGGER_GROUP` VARCHAR(80) NOT NULL, `IS_VOLATILE` VARCHAR(1) NOT NULL, `INSTANCE_NAME` VARCHAR(80) NOT NULL, `FIRED_TIME` DECIMAL(13,0) NOT NULL, `PRIORITY` DECIMAL(13,0) NOT NULL, `STATE` VARCHAR(16) NOT NULL, `JOB_NAME` VARCHAR(80), `JOB_GROUP` VARCHAR(80), `IS_STATEFUL` VARCHAR(1), `REQUESTS_RECOVERY` VARCHAR(1), CONSTRAINT `PK_QRTZ_FIRED_TRIGGERS` PRIMARY KEY (`ENTRY_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-83::djunk (generated)::(Checksum: 3:3f857e5b4399ec018eeb4e84f1c02691)
CREATE TABLE `qrtz_job_details` (`JOB_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `JOB_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `DESCRIPTION` VARCHAR(120), `JOB_CLASS_NAME` VARCHAR(128) NOT NULL, `IS_DURABLE` VARCHAR(1) NOT NULL, `IS_VOLATILE` VARCHAR(1) NOT NULL, `IS_STATEFUL` VARCHAR(1) NOT NULL, `REQUESTS_RECOVERY` VARCHAR(1) NOT NULL, `JOB_DATA` LONGBLOB);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-84::djunk (generated)::(Checksum: 3:9ea4f43ce2fff557f2a6c55c682f9356)
CREATE TABLE `qrtz_job_listeners` (`JOB_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `JOB_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `JOB_LISTENER` VARCHAR(80) DEFAULT '' NOT NULL);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-85::djunk (generated)::(Checksum: 3:cccd5a9237fc32ba1f8619ef04cbd137)
CREATE TABLE `qrtz_locks` (`LOCK_NAME` VARCHAR(40) DEFAULT '' NOT NULL, CONSTRAINT `PK_QRTZ_LOCKS` PRIMARY KEY (`LOCK_NAME`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-86::djunk (generated)::(Checksum: 3:ebdd9b53522a840e3b31ac977ee319af)
CREATE TABLE `qrtz_paused_trigger_grps` (`TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, CONSTRAINT `PK_QRTZ_PAUSED_TRIGGER_GRPS` PRIMARY KEY (`TRIGGER_GROUP`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-87::djunk (generated)::(Checksum: 3:5afe30235a3914ca703885c23fcec5c8)
CREATE TABLE `qrtz_scheduler_state` (`INSTANCE_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `LAST_CHECKIN_TIME` DECIMAL(13,0) NOT NULL, `CHECKIN_INTERVAL` DECIMAL(13,0) NOT NULL, CONSTRAINT `PK_QRTZ_SCHEDULER_STATE` PRIMARY KEY (`INSTANCE_NAME`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-88::djunk (generated)::(Checksum: 3:53e8c70fbc67adfed3d54e09fa1f651b)
CREATE TABLE `qrtz_simple_triggers` (`TRIGGER_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `REPEAT_COUNT` DECIMAL(7,0) NOT NULL, `REPEAT_INTERVAL` DECIMAL(12,0) NOT NULL, `TIMES_TRIGGERED` DECIMAL(7,0) NOT NULL);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-89::djunk (generated)::(Checksum: 3:fc5ffab0f5582b283fe792b84e1a964e)
CREATE TABLE `qrtz_trigger_listeners` (`TRIGGER_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_LISTENER` VARCHAR(80) DEFAULT '' NOT NULL);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-90::djunk (generated)::(Checksum: 3:f544981818c508c570531900c52e399e)
CREATE TABLE `qrtz_triggers` (`TRIGGER_NAME` VARCHAR(80) DEFAULT '' NOT NULL, `TRIGGER_GROUP` VARCHAR(80) DEFAULT '' NOT NULL, `JOB_NAME` VARCHAR(80) NOT NULL, `JOB_GROUP` VARCHAR(80) NOT NULL, `IS_VOLATILE` VARCHAR(1) NOT NULL, `DESCRIPTION` VARCHAR(120), `NEXT_FIRE_TIME` DECIMAL(13,0), `PREV_FIRE_TIME` DECIMAL(13,0), `PRIORITY` DECIMAL(13,0), `TRIGGER_STATE` VARCHAR(16) NOT NULL, `TRIGGER_TYPE` VARCHAR(8) NOT NULL, `START_TIME` DECIMAL(13,0) NOT NULL, `END_TIME` DECIMAL(13,0), `CALENDAR_NAME` VARCHAR(80), `MISFIRE_INSTR` DECIMAL(2,0), `JOB_DATA` LONGBLOB);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-91::djunk (generated)::(Checksum: 3:eff2bdd1c1c21fcf4fff36960c0f4786)
CREATE TABLE `tk_assign_acct_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_ASSIGN_ACCT_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-92::djunk (generated)::(Checksum: 3:bf86118b8e2f5ecaa2bc65fa009cf45b)
CREATE TABLE `tk_assign_acct_t` (`TK_ASSIGN_ACCT_ID` BIGINT NOT NULL, `FIN_COA_CD` VARCHAR(2), `ACCOUNT_NBR` VARCHAR(7), `SUB_ACCT_NBR` VARCHAR(5), `FIN_OBJECT_CD` VARCHAR(4), `FIN_SUB_OBJ_CD` VARCHAR(3), `PROJECT_CD` VARCHAR(10), `ORG_REF_ID` VARCHAR(8), `PERCENT` DECIMAL(5,2), `TK_ASSIGNMENT_ID` BIGINT NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `EARN_CODE` VARCHAR(3), CONSTRAINT `PK_TK_ASSIGN_ACCT_T` PRIMARY KEY (`TK_ASSIGN_ACCT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-93::djunk (generated)::(Checksum: 3:8451b4f41448242d13c1f5f1615e0aec)
CREATE TABLE `tk_assignment_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_ASSIGNMENT_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-94::djunk (generated)::(Checksum: 3:d88b4365aed8c788c424f41f2d3e3c3e)
CREATE TABLE `tk_assignment_t` (`TK_ASSIGNMENT_ID` BIGINT NOT NULL, `PRINCIPAL_ID` VARCHAR(40), `JOB_NUMBER` BIGINT, `EFFDT` DATE DEFAULT '0002-11-30' NOT NULL, `WORK_AREA` BIGINT, `TASK` BIGINT, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `active` VARCHAR(1) DEFAULT 'N', `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_TK_ASSIGNMENT_T` PRIMARY KEY (`TK_ASSIGNMENT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-95::djunk (generated)::(Checksum: 3:4efffe4e9b9166aa2ee3803594946f74)
CREATE TABLE `tk_batch_job_entry_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_BATCH_JOB_ENTRY_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-96::djunk (generated)::(Checksum: 3:bbaec818efc8a12369cb6d7aa5cbc89c)
CREATE TABLE `tk_batch_job_entry_t` (`TK_BATCH_JOB_ENTRY_ID` BIGINT NOT NULL, `TK_BATCH_JOB_ID` BIGINT NOT NULL, `document_id` VARCHAR(40), `principal_id` VARCHAR(40), `BATCH_JOB_ENTRY_STATUS` VARCHAR(1) NOT NULL, `HR_PY_CALENDAR_ENTRY_ID` BIGINT NOT NULL, `batch_job_exception` VARCHAR(100), `IP_ADDRESS` VARCHAR(20) NOT NULL, `BATCH_JOB_NAME` VARCHAR(40) NOT NULL, `clock_log_id` BIGINT, CONSTRAINT `PK_TK_BATCH_JOB_ENTRY_T` PRIMARY KEY (`TK_BATCH_JOB_ENTRY_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-97::djunk (generated)::(Checksum: 3:1e578e018362a1368164848c2bdd010f)
CREATE TABLE `tk_batch_job_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_BATCH_JOB_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-98::djunk (generated)::(Checksum: 3:962590e72fe8d73047224b5afa0c7807)
CREATE TABLE `tk_batch_job_t` (`TK_BATCH_JOB_ID` BIGINT NOT NULL, `BATCH_JOB_NAME` VARCHAR(40) NOT NULL, `BATCH_JOB_STATUS` VARCHAR(1) NOT NULL, `HR_PY_CALENDAR_ENTRY_ID` BIGINT NOT NULL, `TIME_ELAPSED` BIGINT NOT NULL, `timestamp` TIMESTAMP, CONSTRAINT `PK_TK_BATCH_JOB_T` PRIMARY KEY (`TK_BATCH_JOB_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-99::djunk (generated)::(Checksum: 3:b5c535b9a7ded67484f82f2620dba9b2)
CREATE TABLE `tk_clock_location_rl_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_CLOCK_LOCATION_RL_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-100::djunk (generated)::(Checksum: 3:8a3f3441a7a5830648d80596cb0eb5d5)
CREATE TABLE `tk_clock_location_rl_t` (`TK_CLOCK_LOC_RULE_ID` BIGINT NOT NULL, `WORK_AREA` BIGINT, `PRINCIPAL_ID` VARCHAR(10), `job_number` BIGINT, `EFFDT` DATE, `active` VARCHAR(1), `IP_ADDRESS` VARCHAR(15), `USER_PRINCIPAL_ID` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `dept` VARCHAR(21));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-101::djunk (generated)::(Checksum: 3:11038876f7d4abde0fa40254ad74d78e)
CREATE TABLE `tk_clock_log_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_CLOCK_LOG_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-102::djunk (generated)::(Checksum: 3:d05eaa423fcf84685ce45ab6b28c2b87)
CREATE TABLE `tk_clock_log_t` (`TK_CLOCK_LOG_ID` BIGINT NOT NULL, `PRINCIPAL_ID` VARCHAR(40), `JOB_NUMBER` BIGINT, `WORK_AREA` BIGINT, `TASK` BIGINT, `TK_WORK_AREA_ID` BIGINT, `TK_TASK_ID` BIGINT, `CLOCK_TS` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `CLOCK_TS_TZ` VARCHAR(50), `CLOCK_ACTION` VARCHAR(2), `IP_ADDRESS` VARCHAR(15), `HR_JOB_ID` BIGINT, `USER_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_TK_CLOCK_LOG_T` PRIMARY KEY (`TK_CLOCK_LOG_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-103::djunk (generated)::(Checksum: 3:6fe6bb199eb6e80084fd0431bea6099d)
CREATE TABLE `tk_daily_overtime_rl_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_DAILY_OVERTIME_RL_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-104::djunk (generated)::(Checksum: 3:bf51024b1de8bdb259db452e81000832)
CREATE TABLE `tk_daily_overtime_rl_t` (`tk_daily_overtime_rl_id` VARCHAR(60) NOT NULL, `LOCATION` VARCHAR(40), `PAYTYPE` VARCHAR(5), `EFFDT` DATE, `USER_PRINCIPAL_ID` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `DEPT` VARCHAR(21), `WORK_AREA` BIGINT, `MAX_GAP` DECIMAL(8,2), `MIN_HOURS` DECIMAL(2,0), `ACTIVE` VARCHAR(1), `FROM_EARN_GROUP` VARCHAR(10), `EARN_CODE` VARCHAR(10), CONSTRAINT `PK_TK_DAILY_OVERTIME_RL_T` PRIMARY KEY (`tk_daily_overtime_rl_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-105::djunk (generated)::(Checksum: 3:e01c063b385017bb0ae4cabd145e3d28)
CREATE TABLE `tk_dept_lunch_rl_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_DEPT_LUNCH_RL_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-106::djunk (generated)::(Checksum: 3:75f6cff8284bb947999502b2d8a3411a)
CREATE TABLE `tk_dept_lunch_rl_t` (`TK_DEPT_LUNCH_RL_ID` BIGINT NOT NULL, `DEPT` VARCHAR(21), `WORK_AREA` BIGINT, `principal_id` VARCHAR(10) DEFAULT '' NOT NULL, `job_number` BIGINT, `EFFDT` DATE NOT NULL, `REQUIRED_CLOCK_FL` VARCHAR(3), `MAX_MINS` DECIMAL(2,0), `user_principal_id` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `active` VARCHAR(1), `SHIFT_HOURS` DECIMAL(2,1), `DEDUCTION_MINS` DECIMAL(3,0), CONSTRAINT `PK_TK_DEPT_LUNCH_RL_T` PRIMARY KEY (`TK_DEPT_LUNCH_RL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-107::djunk (generated)::(Checksum: 3:cfd2970aeeb39ba0b896c46ec63e93ad)
CREATE TABLE `tk_document_header_t` (`DOCUMENT_ID` VARCHAR(14) NOT NULL, `PRINCIPAL_ID` VARCHAR(40), `PAY_END_DT` DATETIME, `DOCUMENT_STATUS` VARCHAR(1), `PAY_BEGIN_DT` DATETIME, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, CONSTRAINT `PK_TK_DOCUMENT_HEADER_T` PRIMARY KEY (`DOCUMENT_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-108::djunk (generated)::(Checksum: 3:bb9aed2dfb4d5336af1bde5e7ce0b91e)
CREATE TABLE `tk_grace_period_rl_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_GRACE_PERIOD_RL_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-109::djunk (generated)::(Checksum: 3:7d9b983afce416d7e80d83b4cf742301)
CREATE TABLE `tk_grace_period_rl_t` (`TK_GRACE_PERIOD_RULE_ID` BIGINT, `EFFDT` DATE, `GRACE_MINS` DECIMAL(2,0), `HOUR_FACTOR` DECIMAL(6,0), `user_principal_id` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTIVE` VARCHAR(1));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-110::djunk (generated)::(Checksum: 3:4072e20b0702bf396693421fda1739ed)
CREATE TABLE `tk_holiday_calendar_dates_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_HOLIDAY_CALENDAR_DATES_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-111::djunk (generated)::(Checksum: 3:ab4cf046c07eec173c5c03542f169f8b)
CREATE TABLE `tk_hour_detail_hist_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_HOUR_DETAIL_HIST_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-112::djunk (generated)::(Checksum: 3:69f70039062ddaac7f3922111ebf5144)
CREATE TABLE `tk_hour_detail_hist_t` (`TK_HOUR_DETAIL_HIST_ID` BIGINT NOT NULL, `TK_HOUR_DETAIL_ID` BIGINT NOT NULL, `EARN_CODE` VARCHAR(3), `HOURS` DECIMAL(5,2), `amount` DECIMAL(6,2) DEFAULT 0, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ACTION_HISTORY` VARCHAR(15), `MODIFIED_BY_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP_MODIFIED` TIMESTAMP DEFAULT '0000-00-00 00:00:00');


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-113::djunk (generated)::(Checksum: 3:32363d1607c724e2894145803b992488)
CREATE TABLE `tk_hour_detail_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_HOUR_DETAIL_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-114::djunk (generated)::(Checksum: 3:553151aa814c21d48d7f0322f73bdf48)
CREATE TABLE `tk_hour_detail_t` (`TK_HOUR_DETAIL_ID` BIGINT NOT NULL, `TK_TIME_BLOCK_ID` BIGINT NOT NULL, `EARN_CODE` VARCHAR(3), `HOURS` DECIMAL(5,2), `amount` DECIMAL(6,2) DEFAULT 0, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_TK_HOUR_DETAIL_T` PRIMARY KEY (`TK_HOUR_DETAIL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-115::djunk (generated)::(Checksum: 3:3f33afd8fadd11c397ded6a398d3578d)
CREATE TABLE `tk_missed_punch_doc_t` (`doc_hdr_id` VARCHAR(14) NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, `principal_id` VARCHAR(40) NOT NULL, `clock_action` VARCHAR(20) NOT NULL, `action_date` DATETIME DEFAULT '0002-11-30 00:00:00.0' NOT NULL, `action_time` TIME DEFAULT '00:00:00' NOT NULL, `timesheet_doc_id` VARCHAR(14) NOT NULL, `document_status` VARCHAR(1), `tk_clock_log_id` VARCHAR(60), `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `assignment` VARCHAR(20) NOT NULL, CONSTRAINT `PK_TK_MISSED_PUNCH_DOC_T` PRIMARY KEY (`doc_hdr_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-116::djunk (generated)::(Checksum: 3:3087331dc08e380cab34b9c3171c518a)
CREATE TABLE `tk_missed_punch_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_MISSED_PUNCH_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-117::djunk (generated)::(Checksum: 3:0102bb6df0cca5bff0066374fb8c4229)
CREATE TABLE `tk_missed_punch_t` (`tk_missed_punch_id` BIGINT NOT NULL, `principal_id` VARCHAR(40) NOT NULL, `clock_action` VARCHAR(20) NOT NULL, `action_date` DATETIME DEFAULT '0002-11-30 00:00:00.0' NOT NULL, `action_time` TIME DEFAULT '00:00:00' NOT NULL, `timesheet_doc_id` VARCHAR(14) NOT NULL, `document_id` VARCHAR(14), `document_status` VARCHAR(1), `tk_clock_log_id` BIGINT, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_TK_MISSED_PUNCH_T` PRIMARY KEY (`tk_missed_punch_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-118::djunk (generated)::(Checksum: 3:7f33247bf216dad5aa28b79a3c84489e)
CREATE TABLE `tk_shift_differential_rl_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_SHIFT_DIFFERENTIAL_RL_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-119::djunk (generated)::(Checksum: 3:16ecd0bd5fa6652a18b8d7ef3fc30974)
CREATE TABLE `tk_shift_differential_rl_t` (`TK_SHIFT_DIFF_RL_ID` VARCHAR(60) NOT NULL, `LOCATION` VARCHAR(45), `HR_SAL_GROUP` VARCHAR(10), `pay_grade` VARCHAR(20), `EFFDT` DATE, `EARN_CODE` VARCHAR(3), `BEGIN_TS` TIME, `END_TS` TIME, `MIN_HRS` DECIMAL(2,0), `PY_CALENDAR_GROUP` VARCHAR(30) NOT NULL, `MAX_GAP` DECIMAL(5,2) DEFAULT 0, `USER_PRINCIPAL_ID` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `ACTIVE` VARCHAR(1) DEFAULT 'N', `sun` VARCHAR(1) DEFAULT 'N' NOT NULL, `mon` VARCHAR(1) DEFAULT 'N' NOT NULL, `tue` VARCHAR(1) DEFAULT 'N' NOT NULL, `wed` VARCHAR(1) DEFAULT 'N' NOT NULL, `thu` VARCHAR(1) DEFAULT 'N' NOT NULL, `fri` VARCHAR(1) DEFAULT 'N' NOT NULL, `sat` VARCHAR(1) DEFAULT 'N' NOT NULL, `from_earn_group` VARCHAR(10), CONSTRAINT `PK_TK_SHIFT_DIFFERENTIAL_RL_T` PRIMARY KEY (`TK_SHIFT_DIFF_RL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-120::djunk (generated)::(Checksum: 3:6779eef3cb490283e91c953cad9135fe)
CREATE TABLE `tk_system_lunch_rl_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_SYSTEM_LUNCH_RL_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-121::djunk (generated)::(Checksum: 3:1e0a320aca99697db88190a223356df1)
CREATE TABLE `tk_system_lunch_rl_t` (`TK_SYSTEM_LUNCH_RL_ID` VARCHAR(60) NOT NULL, `EFFDT` DATE NOT NULL, `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `ACTIVE` VARCHAR(1), `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `USER_PRINCIPAL_ID` VARCHAR(10), `SHOW_LUNCH_BUTTON` VARCHAR(1) DEFAULT '0', CONSTRAINT `PK_TK_SYSTEM_LUNCH_RL_T` PRIMARY KEY (`TK_SYSTEM_LUNCH_RL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-122::djunk (generated)::(Checksum: 3:fc8fd807be2470988af22df9203f75dc)
CREATE TABLE `tk_task_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TASK_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-123::djunk (generated)::(Checksum: 3:f66af9a3641a5453b34a7ecb2dd84d51)
CREATE TABLE `tk_task_t` (`tk_task_id` BIGINT NOT NULL, `task` BIGINT, `work_area` BIGINT, `tk_work_area_id` BIGINT NOT NULL, `descr` VARCHAR(45), `admin_descr` VARCHAR(45), `obj_id` VARCHAR(45), `ver_nbr` BIGINT DEFAULT 1, `USER_PRINCIPAL_ID` VARCHAR(40), `effdt` DATE NOT NULL, `active` VARCHAR(1) DEFAULT 'Y' NOT NULL, `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, CONSTRAINT `PK_TK_TASK_T` PRIMARY KEY (`tk_task_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-124::djunk (generated)::(Checksum: 3:341f35fd9de4a3dd31dfca35d5bdbcb2)
CREATE TABLE `tk_time_block_hist_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TIME_BLOCK_HIST_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-125::djunk (generated)::(Checksum: 3:dfc216064ab3824ba8aff9e8bbe6ab61)
CREATE TABLE `tk_time_block_hist_t` (`TK_TIME_BLOCK_HIST_ID` VARCHAR(60) NOT NULL, `TK_TIME_BLOCK_ID` VARCHAR(60), `DOCUMENT_ID` VARCHAR(14), `principal_id` VARCHAR(40), `JOB_NUMBER` BIGINT, `TASK` BIGINT, `WORK_AREA` BIGINT, `TK_WORK_AREA_ID` BIGINT, `TK_TASK_ID` BIGINT, `EARN_CODE` VARCHAR(3), `BEGIN_TS` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `BEGIN_TS_TZ` VARCHAR(50), `END_TS` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `END_TS_TZ` VARCHAR(50), `CLOCK_LOG_CREATED` VARCHAR(1) DEFAULT 'Y', `HOURS` DECIMAL(5,2), `USER_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `ACTION_HISTORY` VARCHAR(15), `MODIFIED_BY_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP_MODIFIED` TIMESTAMP DEFAULT '0000-00-00 00:00:00', `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `amount` DECIMAL(6,2) DEFAULT 0, `HR_JOB_ID` BIGINT, CONSTRAINT `PK_TK_TIME_BLOCK_HIST_T` PRIMARY KEY (`TK_TIME_BLOCK_HIST_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-126::djunk (generated)::(Checksum: 3:eddcaaa6db1d8921539b51dd807079df)
CREATE TABLE `tk_time_block_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TIME_BLOCK_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-127::djunk (generated)::(Checksum: 3:016fb369cbc22b3f7aba773bdc220054)
CREATE TABLE `tk_time_block_t` (`TK_TIME_BLOCK_ID` BIGINT NOT NULL, `DOCUMENT_ID` VARCHAR(14), `principal_id` VARCHAR(40), `JOB_NUMBER` BIGINT, `WORK_AREA` BIGINT, `TASK` BIGINT, `TK_WORK_AREA_ID` BIGINT, `HR_JOB_ID` BIGINT, `TK_TASK_ID` BIGINT, `EARN_CODE` VARCHAR(3), `BEGIN_TS` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `BEGIN_TS_TZ` VARCHAR(50), `clock_log_begin_id` BIGINT, `END_TS` TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL, `END_TS_TZ` VARCHAR(50), `clock_log_end_id` BIGINT, `CLOCK_LOG_CREATED` VARCHAR(1) DEFAULT 'Y', `HOURS` DECIMAL(5,2), `amount` DECIMAL(6,2) DEFAULT 0, `USER_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` BIGINT DEFAULT 1 NOT NULL, `ovt_pref` VARCHAR(5), CONSTRAINT `PK_TK_TIME_BLOCK_T` PRIMARY KEY (`TK_TIME_BLOCK_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-128::djunk (generated)::(Checksum: 3:bd6c640ed2120b513ebe2d0b40b78026)
CREATE TABLE `tk_time_collection_rl_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TIME_COLLECTION_RL_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-129::djunk (generated)::(Checksum: 3:440c4fc8c7645a9da77a0fc38cd79375)
CREATE TABLE `tk_time_collection_rl_t` (`TK_TIME_COLL_RULE_ID` VARCHAR(60) NOT NULL, `DEPT` VARCHAR(21), `WORK_AREA` BIGINT, `EFFDT` DATE, `CLOCK_USERS_FL` VARCHAR(1), `HRS_DISTRIBUTION_FL` VARCHAR(1), `USER_PRINCIPAL_ID` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `ACTIVE` VARCHAR(1) DEFAULT 'N', CONSTRAINT `PK_TK_TIME_COLLECTION_RL_T` PRIMARY KEY (`TK_TIME_COLL_RULE_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-130::djunk (generated)::(Checksum: 3:efc00138361753c181f953427a2d5f32)
CREATE TABLE `tk_time_sheet_init_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TIME_SHEET_INIT_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-131::djunk (generated)::(Checksum: 3:df6f4a5e7f3fd6365b3129a21fc87556)
CREATE TABLE `tk_time_sheet_init_t` (`tk_time_sheet_init_id` VARCHAR(60) AUTO_INCREMENT  NOT NULL, `calendar_group` VARCHAR(30) NOT NULL, `document_id` BIGINT NOT NULL, `principal_id` VARCHAR(40) NOT NULL, `py_calendar_entries_id` BIGINT NOT NULL, CONSTRAINT `PK_TK_TIME_SHEET_INIT_T` PRIMARY KEY (`tk_time_sheet_init_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-132::djunk (generated)::(Checksum: 3:d215d822a5df5d27949021d345d9387a)
CREATE TABLE `tk_user_pref_t` (`PRINCIPAL_ID` VARCHAR(40) NOT NULL, `TIME_ZONE` VARCHAR(30), CONSTRAINT `PK_TK_USER_PREF_T` PRIMARY KEY (`PRINCIPAL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-133::djunk (generated)::(Checksum: 3:534e50f506cec958269ea8d569e0db7b)
CREATE TABLE `tk_wa_maint_doc_t` (`doc_hdr_id` VARCHAR(50) NOT NULL, `ver_nbr` BIGINT, `obj_id` VARCHAR(50), `doc_cntnt` LONGTEXT, CONSTRAINT `PK_TK_WA_MAINT_DOC_T` PRIMARY KEY (`doc_hdr_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-134::djunk (generated)::(Checksum: 3:9eefc7a49bfb5a7eb0048ec376e5b9ea)
CREATE TABLE `tk_weekly_overtime_rl_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_WEEKLY_OVERTIME_RL_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-135::djunk (generated)::(Checksum: 3:003ac5eb01c3ab8dae64a1cdca75c33c)
CREATE TABLE `tk_weekly_overtime_rl_t` (`TK_WEEKLY_OVERTIME_RL_ID` VARCHAR(60) NOT NULL, `max_hrs_ern_group` VARCHAR(10) NOT NULL, `convert_from_ern_group` VARCHAR(10) NOT NULL, `convert_to_erncd` VARCHAR(9) NOT NULL, `EFFDT` DATE, `STEP` DECIMAL(2,0), `MAX_HRS` DECIMAL(3,0), `USER_PRINCIPAL_ID` VARCHAR(10), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `ACTIVE` VARCHAR(1) DEFAULT 'N', `TK_WEEKLY_OVT_GROUP_ID` BIGINT DEFAULT 1 NOT NULL, CONSTRAINT `PK_TK_WEEKLY_OVERTIME_RL_T` PRIMARY KEY (`TK_WEEKLY_OVERTIME_RL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-136::djunk (generated)::(Checksum: 3:019a6fb7be01dac84ca07035f9ce809a)
CREATE TABLE `tk_weekly_ovt_group_t` (`TK_WEEKLY_OVERTIME_GROUP_ID` BIGINT NOT NULL, CONSTRAINT `PK_TK_WEEKLY_OVT_GROUP_T` PRIMARY KEY (`TK_WEEKLY_OVERTIME_GROUP_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-137::djunk (generated)::(Checksum: 3:b18f093dca7356948b17f2242820dab5)
CREATE TABLE `tk_work_area_document_t` (`doc_hdr_id` VARCHAR(50) NOT NULL, `ver_nbr` BIGINT, `obj_id` VARCHAR(50), CONSTRAINT `PK_TK_WORK_AREA_DOCUMENT_T` PRIMARY KEY (`doc_hdr_id`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-138::djunk (generated)::(Checksum: 3:f35afd18bf3bb4e76a5a819719e02a11)
CREATE TABLE `tk_work_area_key_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_WORK_AREA_KEY_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-139::djunk (generated)::(Checksum: 3:1c2c2af1dcb39de55ad94441891c877e)
CREATE TABLE `tk_work_area_s` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_WORK_AREA_S` PRIMARY KEY (`ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-140::djunk (generated)::(Checksum: 3:27efe8e3bce26149fe3a6c33a467b8e3)
CREATE TABLE `tk_work_area_t` (`TK_WORK_AREA_ID` BIGINT NOT NULL, `work_area` BIGINT NOT NULL, `DEPT` VARCHAR(21), `EFFDT` DATE NOT NULL, `ACTIVE` VARCHAR(1), `DESCR` VARCHAR(30), `DEFAULT_OVERTIME_EARNCODE` VARCHAR(3), `ADMIN_DESCR` VARCHAR(30), `USER_PRINCIPAL_ID` VARCHAR(40), `TIMESTAMP` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, `OBJ_ID` VARCHAR(36), `VER_NBR` BIGINT DEFAULT 1, `OVERTIME_EDIT_ROLE` VARCHAR(20) NOT NULL, CONSTRAINT `PK_TK_WORK_AREA_T` PRIMARY KEY (`TK_WORK_AREA_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-141::djunk (generated)::(Checksum: 3:fddd7bede9540cc2527022a9da72cfa0)
ALTER TABLE `ca_account_t` ADD PRIMARY KEY (`FIN_COA_CD`, `ACCOUNT_NBR`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-142::djunk (generated)::(Checksum: 3:59590d79571cfed1dc00f38fc864fdab)
ALTER TABLE `ca_object_code_t` ADD PRIMARY KEY (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `FIN_OBJECT_CD`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-143::djunk (generated)::(Checksum: 3:d9bba775862e02c03edbab006f2b5167)
ALTER TABLE `ca_org_t` ADD PRIMARY KEY (`FIN_COA_CD`, `ORG_CD`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-144::djunk (generated)::(Checksum: 3:0003f71e31f4d58e7c82de5bf8b14f59)
ALTER TABLE `ca_sub_acct_t` ADD PRIMARY KEY (`FIN_COA_CD`, `ACCOUNT_NBR`, `SUB_ACCT_NBR`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-145::djunk (generated)::(Checksum: 3:0e388f083dd5659dc84e6f29dc57d8c5)
ALTER TABLE `ca_sub_object_cd_t` ADD PRIMARY KEY (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `ACCOUNT_NBR`, `FIN_OBJECT_CD`, `FIN_SUB_OBJ_CD`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-146::djunk (generated)::(Checksum: 3:b70e5ada92683fef92ccb704d0f94dc6)
ALTER TABLE `krns_adhoc_rte_actn_recip_t` ADD PRIMARY KEY (`RECIP_TYP_CD`, `ACTN_RQST_CD`, `ACTN_RQST_RECIP_ID`, `DOC_HDR_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-147::djunk (generated)::(Checksum: 3:59a7f21883399ef22c1ed7817fd9fdb3)
ALTER TABLE `krns_sesn_doc_t` ADD PRIMARY KEY (`SESN_DOC_ID`, `DOC_HDR_ID`, `PRNCPL_ID`, `IP_ADDR`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-148::djunk (generated)::(Checksum: 3:0d46e52f123fbfcdcd9134ef6dc8cac7)
ALTER TABLE `qrtz_blob_triggers` ADD PRIMARY KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-149::djunk (generated)::(Checksum: 3:9466c1576465db267e95e8d7f681cf6e)
ALTER TABLE `qrtz_cron_triggers` ADD PRIMARY KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-150::djunk (generated)::(Checksum: 3:62045f1abc94aa9617c0a7beb916cdb8)
ALTER TABLE `qrtz_job_details` ADD PRIMARY KEY (`JOB_NAME`, `JOB_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-151::djunk (generated)::(Checksum: 3:be833249bc0bcc44db9056c66b518a91)
ALTER TABLE `qrtz_job_listeners` ADD PRIMARY KEY (`JOB_NAME`, `JOB_GROUP`, `JOB_LISTENER`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-152::djunk (generated)::(Checksum: 3:837776d617848725b1cd97a11f732569)
ALTER TABLE `qrtz_simple_triggers` ADD PRIMARY KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-153::djunk (generated)::(Checksum: 3:50cb2f3fe7dc674e41ddc72caf924106)
ALTER TABLE `qrtz_trigger_listeners` ADD PRIMARY KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_LISTENER`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-154::djunk (generated)::(Checksum: 3:da46a02fe8569242f8a6b381681da70b)
ALTER TABLE `qrtz_triggers` ADD PRIMARY KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-155::djunk (generated)::(Checksum: 3:ad17a6eff1de1fd6eb7332d6d90d2227)
CREATE UNIQUE INDEX `CA_ACCOUNT_TC0` ON `ca_account_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-156::djunk (generated)::(Checksum: 3:237ce03fa99d55cbd01ba045b6e2e8c2)
CREATE INDEX `CA_ACCOUNT_TI2` ON `ca_account_t`(`ACCOUNT_NBR`, `FIN_COA_CD`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-157::djunk (generated)::(Checksum: 3:4ed7850d8af65a5967477900cc32a0b0)
CREATE UNIQUE INDEX `CA_CHART_TC0` ON `ca_chart_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-158::djunk (generated)::(Checksum: 3:819de95d1fbf4ba2d5116e55c07fd71b)
CREATE UNIQUE INDEX `CA_OBJECT_CODE_TC0` ON `ca_object_code_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-159::djunk (generated)::(Checksum: 3:572b9ec74884a458c7a6ceef5177ffb1)
CREATE UNIQUE INDEX `CA_ORG_TC0` ON `ca_org_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-160::djunk (generated)::(Checksum: 3:3895b2f0976fcf8ed2321b02ff5627cb)
CREATE UNIQUE INDEX `CA_PROJECT_TC0` ON `ca_project_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-161::djunk (generated)::(Checksum: 3:5d42f8d1cb334de1253d7ff42782801c)
CREATE UNIQUE INDEX `CA_SUB_ACCT_TC0` ON `ca_sub_acct_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-162::djunk (generated)::(Checksum: 3:7ad773a0f24d1c61c3e9e91226453f7e)
CREATE INDEX `CA_SUB_ACCT_TI2` ON `ca_sub_acct_t`(`FIN_COA_CD`, `ACCOUNT_NBR`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-163::djunk (generated)::(Checksum: 3:a55b295ef90ea5796c143b1ee6da7127)
CREATE UNIQUE INDEX `CA_SUB_OBJECT_CD_TC0` ON `ca_sub_object_cd_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-164::djunk (generated)::(Checksum: 3:8d25e715d670e39712b988c4508c82cf)
CREATE INDEX `CA_SUB_OBJECT_CD_TI2` ON `ca_sub_object_cd_t`(`UNIV_FISCAL_YR`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-165::djunk (generated)::(Checksum: 3:8ae8fca9de9eff25f802231b90b793b8)
CREATE INDEX `KRNS_ADHOC_RTE_ACTN_RECIP_T2` ON `krns_adhoc_rte_actn_recip_t`(`DOC_HDR_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-166::djunk (generated)::(Checksum: 3:1880c11caf9695f24081897091b4fe13)
CREATE UNIQUE INDEX `KRNS_ADHOC_RTE_ACTN_RECIP_TC0` ON `krns_adhoc_rte_actn_recip_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-167::djunk (generated)::(Checksum: 3:3424e5c423577d07da3ff26ac45165f0)
CREATE UNIQUE INDEX `KRNS_ATT_TC0` ON `krns_att_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-168::djunk (generated)::(Checksum: 3:f0c24f532946f1d60bf939a01020d86c)
CREATE UNIQUE INDEX `KRNS_DOC_HDR_TC0` ON `krns_doc_hdr_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-169::djunk (generated)::(Checksum: 3:e4c942a8c46a6ee06505cdbd9ed82348)
CREATE INDEX `KRNS_DOC_HDR_TI3` ON `krns_doc_hdr_t`(`ORG_DOC_HDR_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-170::djunk (generated)::(Checksum: 3:43ce2acd482e2f7779359b5d2280cc17)
CREATE UNIQUE INDEX `KRNS_LOOKUP_RSLT_TC0` ON `krns_lookup_rslt_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-171::djunk (generated)::(Checksum: 3:6b54e6beac8cac10a24cea8423c5aa5c)
CREATE UNIQUE INDEX `KRNS_LOOKUP_SEL_TC0` ON `krns_lookup_sel_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-172::djunk (generated)::(Checksum: 3:5b0170f1ecd280cebc1aa5ebb071f966)
CREATE UNIQUE INDEX `KRNS_MAINT_DOC_ATT_TC0` ON `krns_maint_doc_att_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-173::djunk (generated)::(Checksum: 3:e569a49397a9132c364bffd2c778ab33)
CREATE UNIQUE INDEX `KRNS_MAINT_DOC_TC0` ON `krns_maint_doc_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-174::djunk (generated)::(Checksum: 3:0386987647ed8ccec89168c909409ce7)
CREATE UNIQUE INDEX `KRNS_MAINT_LOCK_TC0` ON `krns_maint_lock_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-175::djunk (generated)::(Checksum: 3:a70d27b4dd978cc997171f148b802a41)
CREATE INDEX `KRNS_MAINT_LOCK_TI2` ON `krns_maint_lock_t`(`DOC_HDR_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-176::djunk (generated)::(Checksum: 3:ec33accb71616471d4e9c56556b2e5d1)
CREATE UNIQUE INDEX `KRNS_NTE_TC0` ON `krns_nte_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-177::djunk (generated)::(Checksum: 3:f882e4c211f1f1e9e8b31c880bea97ff)
CREATE UNIQUE INDEX `KRNS_NTE_TYP_TC0` ON `krns_nte_typ_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-178::djunk (generated)::(Checksum: 3:76624c91b1a3ad84f4bb208deda52b75)
CREATE UNIQUE INDEX `KRNS_PESSIMISTIC_LOCK_TC0` ON `krns_pessimistic_lock_t`(`OBJ_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-179::djunk (generated)::(Checksum: 3:51680ed5951b8860766cad1e1a8e9368)
CREATE INDEX `KRNS_PESSIMISTIC_LOCK_TI1` ON `krns_pessimistic_lock_t`(`DOC_HDR_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-180::djunk (generated)::(Checksum: 3:90980bc1f7467098c59f98cd28f7db0e)
CREATE INDEX `KRNS_PESSIMISTIC_LOCK_TI2` ON `krns_pessimistic_lock_t`(`PRNCPL_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-181::djunk (generated)::(Checksum: 3:c396eee38da79de6f1e2e455a5f9ac26)
CREATE INDEX `KRNS_SESN_DOC_TI1` ON `krns_sesn_doc_t`(`LAST_UPDT_DT`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-182::djunk (generated)::(Checksum: 3:b374abb68bc6b86eb07868f20fb57e91)
CREATE INDEX `KREW_BAM_PARM_TI1` ON `krsb_bam_parm_t`(`BAM_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-183::djunk (generated)::(Checksum: 3:7d01ac73decf3a8f14e02d385c6dc0d3)
CREATE INDEX `KRSB_BAM_TI1` ON `krsb_bam_t`(`SVC_NM`, `MTHD_NM`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-184::djunk (generated)::(Checksum: 3:90331fabbe7cd45374f35fefc6079880)
CREATE INDEX `KRSB_BAM_TI2` ON `krsb_bam_t`(`SVC_NM`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-185::djunk (generated)::(Checksum: 3:e0694465042669b2d51054f94deed208)
CREATE INDEX `KRSB_MSG_QUE_TI1` ON `krsb_msg_que_t`(`SVC_NM`, `SVC_MTHD_NM`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-186::djunk (generated)::(Checksum: 3:cf373e9dc0368c24886b39d1f9379fe6)
CREATE INDEX `KRSB_MSG_QUE_TI2` ON `krsb_msg_que_t`(`SVC_NMSPC`, `STAT_CD`, `IP_NBR`, `DT`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-187::djunk (generated)::(Checksum: 3:4f1eab5c2f4b54e7b96b7e86fc94be95)
CREATE INDEX `KRSB_SVC_DEF_TI1` ON `krsb_svc_def_t`(`SRVR_IP`, `SVC_NMSPC`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-188::djunk (generated)::(Checksum: 3:62abd3b332b3472268cf9eaf6e623aed)
CREATE UNIQUE INDEX `KRSB_SVC_DEF_TI2` ON `krsb_svc_def_t`(`FLT_SVC_DEF_ID`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-189::djunk (generated)::(Checksum: 3:2ddc0fddde3dd124ea700d1dd077be52)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI1` ON `qrtz_fired_triggers`(`JOB_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-190::djunk (generated)::(Checksum: 3:3d915f615d5bb390b118c3ae9a5ae29b)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI2` ON `qrtz_fired_triggers`(`JOB_NAME`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-191::djunk (generated)::(Checksum: 3:6f841b431fd65aa3583829c357548100)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI3` ON `qrtz_fired_triggers`(`REQUESTS_RECOVERY`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-192::djunk (generated)::(Checksum: 3:2e9c442bbdf7c8454a59bbf5bb9de38c)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI4` ON `qrtz_fired_triggers`(`IS_STATEFUL`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-193::djunk (generated)::(Checksum: 3:feedce460110280ac1b9aedf6d107c7d)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI5` ON `qrtz_fired_triggers`(`TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-194::djunk (generated)::(Checksum: 3:6453787824acdc65f543eaef4f2e8474)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI6` ON `qrtz_fired_triggers`(`INSTANCE_NAME`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-195::djunk (generated)::(Checksum: 3:031b61222a4c3f3f1d9b68c154ac2478)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI7` ON `qrtz_fired_triggers`(`TRIGGER_NAME`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-196::djunk (generated)::(Checksum: 3:05d7352c4bf8066d48186aad5b237478)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI8` ON `qrtz_fired_triggers`(`TRIGGER_NAME`, `TRIGGER_GROUP`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-197::djunk (generated)::(Checksum: 3:12e3af02f7d7f0f86f93791709c1d3b0)
CREATE INDEX `qrtz_FIRED_TRIGGERS_TI9` ON `qrtz_fired_triggers`(`IS_VOLATILE`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-198::djunk (generated)::(Checksum: 3:534549b826f83600661b7e727a0cd0b5)
CREATE INDEX `qrtz_JOB_DETAILS_TI1` ON `qrtz_job_details`(`REQUESTS_RECOVERY`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-199::djunk (generated)::(Checksum: 3:227261873f118591c2b6d8be85516e92)
CREATE INDEX `qrtz_TRIGGERS_TI1` ON `qrtz_triggers`(`NEXT_FIRE_TIME`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-200::djunk (generated)::(Checksum: 3:a8e0a3f0fefa5db06c56ed8842c323d0)
CREATE INDEX `qrtz_TRIGGERS_TI2` ON `qrtz_triggers`(`NEXT_FIRE_TIME`, `TRIGGER_STATE`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-201::djunk (generated)::(Checksum: 3:e3cbb481997ce2c52a8bb6e078115d60)
CREATE INDEX `qrtz_TRIGGERS_TI3` ON `qrtz_triggers`(`TRIGGER_STATE`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-202::djunk (generated)::(Checksum: 3:9c129c5be2f8454df00d405a64777c85)
CREATE INDEX `qrtz_TRIGGERS_TI4` ON `qrtz_triggers`(`IS_VOLATILE`);


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-203::djunk (generated)::(Checksum: 3:1bd463888445e81ab9bdbd0ca3c4604c)
ALTER TABLE `ca_account_t` ADD CONSTRAINT `CA_ACCOUNT_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-204::djunk (generated)::(Checksum: 3:2365efd7d7752f32c09b9e58a70da6b5)
ALTER TABLE `ca_account_t` ADD CONSTRAINT `CA_ACCOUNT_TR2` FOREIGN KEY (`FIN_COA_CD`, `ORG_CD`) REFERENCES `ca_org_t` (`FIN_COA_CD`, `ORG_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-205::djunk (generated)::(Checksum: 3:60cc4b2d0c9cf59e3012f051264036e5)
ALTER TABLE `ca_org_t` ADD CONSTRAINT `CA_ORG_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-206::djunk (generated)::(Checksum: 3:d5c2c33e84223ab656c80a3515f08bba)
ALTER TABLE `ca_project_t` ADD CONSTRAINT `CA_PROJECT_TR1` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-207::djunk (generated)::(Checksum: 3:a0f2ffd23e0201cf1c4ac31f872ebe57)
ALTER TABLE `ca_project_t` ADD CONSTRAINT `CA_PROJECT_TR2` FOREIGN KEY (`FIN_COA_CD`, `ORG_CD`) REFERENCES `ca_org_t` (`FIN_COA_CD`, `ORG_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-208::djunk (generated)::(Checksum: 3:306f0faa01fd9ae89633275100cdc185)
ALTER TABLE `ca_sub_acct_t` ADD CONSTRAINT `CA_SUB_ACCT_TR2` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-209::djunk (generated)::(Checksum: 3:ba656ba9e492ea192f7cc615c1a6d562)
ALTER TABLE `ca_sub_object_cd_t` ADD CONSTRAINT `CA_SUB_OBJECT_CD_TR3` FOREIGN KEY (`FIN_COA_CD`) REFERENCES `ca_chart_t` (`FIN_COA_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-210::djunk (generated)::(Checksum: 3:77cafd230c838bc523b84a74807ef10d)
ALTER TABLE `ca_sub_object_cd_t` ADD CONSTRAINT `CA_SUB_OBJECT_CD_TR1` FOREIGN KEY (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `FIN_OBJECT_CD`) REFERENCES `ca_object_code_t` (`UNIV_FISCAL_YR`, `FIN_COA_CD`, `FIN_OBJECT_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-211::djunk (generated)::(Checksum: 3:52415d327f6de1cf26158f6ed1b696aa)
ALTER TABLE `krns_att_t` ADD CONSTRAINT `KRNS_ATT_TR1` FOREIGN KEY (`NTE_ID`) REFERENCES `krns_nte_t` (`NTE_ID`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-212::djunk (generated)::(Checksum: 3:8290a6f2f5cbfa9ea199c21bdab0138b)
ALTER TABLE `krns_maint_doc_t` ADD CONSTRAINT `KRNS_MAINT_DOC_TR1` FOREIGN KEY (`DOC_HDR_ID`) REFERENCES `krns_doc_hdr_t` (`DOC_HDR_ID`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-213::djunk (generated)::(Checksum: 3:4518795ecd5391088d128c72f6133dc3)
ALTER TABLE `krns_nte_t` ADD CONSTRAINT `KRNS_NTE_TR1` FOREIGN KEY (`NTE_TYP_CD`) REFERENCES `krns_nte_typ_t` (`NTE_TYP_CD`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-214::djunk (generated)::(Checksum: 3:007eff71e5b95159d235e2b3cb801292)
ALTER TABLE `qrtz_blob_triggers` ADD CONSTRAINT `qrtz_BLOB_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-215::djunk (generated)::(Checksum: 3:c512712756cc8e5e770846757d50e545)
ALTER TABLE `qrtz_cron_triggers` ADD CONSTRAINT `qrtz_CRON_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-216::djunk (generated)::(Checksum: 3:6c7677c18a9398947789b0dd51a65a1b)
ALTER TABLE `qrtz_job_listeners` ADD CONSTRAINT `KRSB_QUARTZ_JOB_LISTENERS_TR1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`JOB_NAME`, `JOB_GROUP`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-217::djunk (generated)::(Checksum: 3:7a97cf493b6cb97e18be8342e6ffab72)
ALTER TABLE `qrtz_simple_triggers` ADD CONSTRAINT `qrtz_SIMPLE_TRIGGERS_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.bootstrap.changelog-1.0.xml::1317396430027-218::djunk (generated)::(Checksum: 3:83dcecfd0cbac309beb6d60836dbdca3)
ALTER TABLE `qrtz_trigger_listeners` ADD CONSTRAINT `qrtz_TRIGGER_LISTENE_TR1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changeset org/kuali/kpme/liquibase/tna/db.changelog-11.1.xml::1::xichen::(Checksum: 3:cf0a11d68c5f37ff0b5953e281a4e54f)
ALTER TABLE `tk_assignment_t` DROP PRIMARY KEY;

ALTER TABLE `tk_assignment_t` ADD PRIMARY KEY (`tk_assignment_id`);


-- Changeset org/kuali/kpme/liquibase/tna/db.changelog-11.1.xml::2::xichen::(Checksum: 3:d8626637b0e47dfa8ece732932aa5fc1)
ALTER TABLE `hr_earn_group_t` DROP PRIMARY KEY;

ALTER TABLE `hr_earn_group_t` ADD PRIMARY KEY (`hr_earn_group_id`);


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.0.xml::1::yingzhou::(Checksum: 3:052b875f8e8b6e13025326c3f3c3c166)
CREATE TABLE `tk_ip_address_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_IP_ADDRESS_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.0.xml::2::yingzhou::(Checksum: 3:61742a485ac6d51615358e249e97cd6e)
CREATE TABLE `tk_ip_address_t` (`TK_IP_ADDRESS_ID` BIGINT NOT NULL, `TK_CLOCK_LOC_RULE_ID` BIGINT NOT NULL, `IP_ADDRESS` VARCHAR(15) NOT NULL, CONSTRAINT `PK_TK_IP_ADDRESS_T` PRIMARY KEY (`TK_IP_ADDRESS_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.0.xml::3::yingzhou::(Checksum: 3:685f62c50575e436cb54a123be7308a1)
ALTER TABLE `tk_clock_location_rl_t` DROP COLUMN `ip_address`;


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.10.xml::1::yingzhou::(Checksum: 3:a77fc3d801112cdbd3e4751d5e366955)
CREATE TABLE `tk_clock_loc_rl_ip_addr_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_CLOCK_LOC_RL_IP_ADDR_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.10.xml::2::yingzhou::(Checksum: 3:ffe37c5e042a0901a54575db344cc91d)
CREATE TABLE `tk_clock_loc_rl_ip_addr_t` (`TK_CLOCK_LOC_RULE_IP_ID` BIGINT NOT NULL, `TK_CLOCK_LOC_RULE_ID` BIGINT NOT NULL, `IP_ADDRESS` VARCHAR(15) NOT NULL, CONSTRAINT `PK_TK_CLOCK_LOC_RL_IP_ADDR_T` PRIMARY KEY (`TK_CLOCK_LOC_RULE_IP_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.10.xml::3::yingzhou::(Checksum: 3:5fa3e925fe5a4b4805833c865153a901)
DROP TABLE `tk_ip_address_s`;


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.10.xml::4::yingzhou::(Checksum: 3:3c21d1b1c7c1ec0ed456d3c31db67212)
DROP TABLE `tk_ip_address_t`;


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.2.xml::1::yingzhou::(Checksum: 3:4b35512a861e50da59257d9cba838581)
ALTER TABLE `hr_earn_group_t` ADD `warning_text` VARCHAR(100);


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.3.xml::1::yingzhou::(Checksum: 3:b377cf143ec1a7f6a707fad2f5cc372c)
CREATE TABLE `tk_time_block_hist_detail_s` (`id` BIGINT AUTO_INCREMENT  NOT NULL, CONSTRAINT `PK_TK_TIME_BLOCK_HIST_DETAIL_S` PRIMARY KEY (`id`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.3.xml::2::yingzhou::(Checksum: 3:21e9105f7633436ecf8ca1295027f353)
CREATE TABLE `tk_time_block_hist_detail_t` (`TK_TIME_BLOCK_HIST_DETAIL_ID` VARCHAR(60) NOT NULL, `TK_TIME_BLOCK_HIST_ID` VARCHAR(60) NOT NULL, `EARN_CODE` VARCHAR(3) NOT NULL, `HOURS` DECIMAL(5,2), `AMOUNT` DECIMAL(6,2), `OBJ_ID` VARCHAR(36) NOT NULL, `VER_NBR` DECIMAL(8,0) DEFAULT 1 NOT NULL, CONSTRAINT `PK_TK_TIME_BLOCK_HIST_DETAIL_T` PRIMARY KEY (`TK_TIME_BLOCK_HIST_DETAIL_ID`));


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.5.xml::1::xichen::(Checksum: 3:b72b75e332a8877318a191d4f84497ba)
ALTER TABLE `tk_time_collection_rl_t` ADD `PAY_TYPE` VARCHAR(5);


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.5.xml::12::xichen::(Checksum: 3:b237bdbfd07d668bf51ed73d76e2db63)
ALTER TABLE `tk_time_collection_rl_t` MODIFY `PAY_TYPE` VARCHAR(5) NOT NULL;


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.6.xml::1::lfox::(Checksum: 3:59a6697f1b005afb264bdb5797eb0e2c)
ALTER TABLE `hr_roles_t` ADD `tk_work_area_id` VARCHAR(60);


-- Changeset org/kuali/kpme/liquibase/tna/release-1.1/db.changelog-release1.1-1.6.xml::2::lfox::(Checksum: 3:f6263f0525953a3df640a2050a971bc8)
ALTER TABLE `tk_assign_acct_t` ADD `active` VARCHAR(1);


