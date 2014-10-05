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

delete from EDO_SUPPLEMENTAL_TRACKING_T where EDO_SUPPLEMENTAL_TRACKING_ID >= '1000';

insert into EDO_SUPPLEMENTAL_TRACKING_T (`EDO_SUPPLEMENTAL_TRACKING_ID`, `EDO_DOSSIER_ID`, `REVIEW_LEVEL`, `ACKNOWLEDGED`, `USER_PRINCIPAL_ID`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', '1000', 1, 'Y', 'admin', now(), uuid(), '1');



