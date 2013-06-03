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

DELETE FROM HR_LOCATION_T WHERE HR_LOCATION_ID >= 1000;

INSERT INTO HR_LOCATION_T (HR_LOCATION_ID, LOCATION, DESCRIPTION, EFFDT, TIMESTAMP, TIMEZONE, ACTIVE) VALUES('1000','BL','Bloomington','2010-01-01','2012-08-21 09:44:28.0','America/Indianapolis','Y');
INSERT INTO HR_LOCATION_T (HR_LOCATION_ID, LOCATION, DESCRIPTION, EFFDT, TIMESTAMP, TIMEZONE, ACTIVE) VALUES('1001','IN','Indianapolis','2010-01-01','2012-08-21 09:44:28.0','America/Indianapolis','Y');
