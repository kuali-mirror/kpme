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

INSERT INTO HR_PAYTYPE_T(HR_PAYTYPE_ID, PAYTYPE, DESCR, REG_ERN_CODE, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, ACTIVE, FLSA_STATUS, PAY_FREQUENCY, USER_PRINCIPAL_ID) VALUES('4', 'BW3', 'description', 'RGN', '2010-01-01', '2010-01-01 16:01:07.0', UUID(), 1, 'Y', 'NE', 'M', 'admin');

INSERT INTO HR_PAYTYPE_T(HR_PAYTYPE_ID, PAYTYPE, DESCR, REG_ERN_CODE, EFFDT, TIMESTAMP, OBJ_ID, VER_NBR, ACTIVE, FLSA_STATUS, PAY_FREQUENCY, USER_PRINCIPAL_ID) VALUES('5', 'BW4', 'description', 'RGN', '2010-01-01', '2010-01-01 16:01:07.0', UUID(), 1, 'Y', 'NE', 'M', 'admin');

INSERT INTO HR_PAYTYPE_KEY_T (HR_PAYTYPE_KEY_ID, HR_PAYTYPE_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_paytype_test_1000', '1', 'ISU-IA', UUID(), 1);

INSERT INTO HR_PAYTYPE_KEY_T (HR_PAYTYPE_KEY_ID, HR_PAYTYPE_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_paytype_test_1001', '1', 'UGA-GA', UUID(), 1);

INSERT INTO HR_PAYTYPE_KEY_T (HR_PAYTYPE_KEY_ID, HR_PAYTYPE_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_paytype_test_1002', '5', 'UGA-GA', UUID(), 1);

INSERT INTO HR_PAYTYPE_KEY_T (HR_PAYTYPE_KEY_ID, HR_PAYTYPE_ID, GRP_KEY_CD, OBJ_ID, VER_NBR) VALUES ('kpme_paytype_test_1003', '5', 'IU-IN', UUID(), 1);
