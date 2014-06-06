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




-- mysql> desc pm_pstn_typ_t;
-- +-------------------+--------------+------+-----+---------------------+-------+
-- | Field             | Type         | Null | Key | Default             | Extra |
-- +-------------------+--------------+------+-----+---------------------+-------+
-- | PM_PSTN_TYP_ID    | varchar(60)  | NO   | PRI | NULL                |       |
-- | PSTN_TYP          | varchar(50)  | YES  |     | NULL                |       |
-- | DESCRIPTION       | varchar(100) | YES  |     | NULL                |       |
-- | INSTITUTION       | varchar(15)  | NO   |     | NULL                |       |
-- | LOCATION          | varchar(15)  | NO   |     | NULL                |       |
-- | EFFDT             | date         | YES  |     | NULL                |       |
-- | ACTIVE            | varchar(1)   | NO   |     | NULL                |       |
-- | TIMESTAMP         | datetime     | YES  |     | 1970-01-01 00:00:00 |       |
-- | OBJ_ID            | varchar(36)  | YES  |     | NULL                |       |
-- | VER_NBR           | bigint(20)   | NO   |     | 1                   |       |
-- | USER_PRINCIPAL_ID | varchar(40)  | NO   |     | admin               |       |
-- | ACAD_FLAG         | varchar(1)   | YES  |     | NULL                |       |
-- +-------------------+--------------+------+-----+---------------------+-------+

DELETE FROM PM_PSTN_TYP_GRP_KEY_T WHERE PM_PSTN_TYP_GRP_KEY_ID LIKE 'kpme_mlemons%';

DELETE FROM PM_PSTN_TYP_T WHERE PM_PSTN_TYP_ID <= 5;


INSERT INTO PM_PSTN_TYP_T (PM_PSTN_TYP_ID, PSTN_TYP, DESCRIPTION, INSTITUTION, LOCATION, EFFDT, ACTIVE, TIMESTAMP, OBJ_ID, VER_NBR, USER_PRINCIPAL_ID, ACAD_FLAG) VALUES ('1', 'Test Type 1', 'Test Type 1 Description', 'ISU', 'IA', NOW(), '1', now(), UUID(), '1', 'admin', '1');

INSERT INTO PM_PSTN_TYP_T (PM_PSTN_TYP_ID, PSTN_TYP, DESCRIPTION, INSTITUTION, LOCATION, EFFDT, ACTIVE, TIMESTAMP, OBJ_ID, VER_NBR, USER_PRINCIPAL_ID, ACAD_FLAG) VALUES ('2', 'Test Type 2', 'Test Type 2 Description', 'ISU', 'IA', NOW(), '1', now(), UUID(), '1', 'admin', '1');

INSERT INTO PM_PSTN_TYP_T (PM_PSTN_TYP_ID, PSTN_TYP, DESCRIPTION, INSTITUTION, LOCATION, EFFDT, ACTIVE, TIMESTAMP, OBJ_ID, VER_NBR, USER_PRINCIPAL_ID, ACAD_FLAG) VALUES ('3', 'Test Type 3', 'Test Type 3 Description', 'ISU', 'IA', NOW(), '1', now(), UUID(), '1', 'admin', '1');

INSERT INTO PM_PSTN_TYP_T (PM_PSTN_TYP_ID, PSTN_TYP, DESCRIPTION, INSTITUTION, LOCATION, EFFDT, ACTIVE, TIMESTAMP, OBJ_ID, VER_NBR, USER_PRINCIPAL_ID, ACAD_FLAG) VALUES ('4', 'Test Type 4', 'Test Type 4 Description', 'ISU', 'IA', NOW(), '1', now(), UUID(), '1', 'admin', '1');

INSERT INTO PM_PSTN_TYP_T (PM_PSTN_TYP_ID, PSTN_TYP, DESCRIPTION, INSTITUTION, LOCATION, EFFDT, ACTIVE, TIMESTAMP, OBJ_ID, VER_NBR, USER_PRINCIPAL_ID, ACAD_FLAG) VALUES ('5', 'Test Type 5', 'Test Type 5 Description', 'ISU', 'IA', NOW(), '1', now(), UUID(), '1', 'admin', '1');



INSERT INTO PM_PSTN_TYP_GRP_KEY_T (PM_PSTN_TYP_GRP_KEY_ID, PM_PSTN_TYP_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_mlemons_test_1000', '1', 'ISU-IA', UUID(), 1);

INSERT INTO PM_PSTN_TYP_GRP_KEY_T (PM_PSTN_TYP_GRP_KEY_ID, PM_PSTN_TYP_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_mlemons_test_1001', '1', 'UGA-GA', UUID(), 1);

INSERT INTO PM_PSTN_TYP_GRP_KEY_T (PM_PSTN_TYP_GRP_KEY_ID , PM_PSTN_TYP_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_mlemons_test_1002', '4', 'UGA-GA', UUID(), 1);

INSERT INTO PM_PSTN_TYP_GRP_KEY_T (PM_PSTN_TYP_GRP_KEY_ID , PM_PSTN_TYP_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_mlemons_test_1003', '4', 'IU-IN', UUID(), 1);
