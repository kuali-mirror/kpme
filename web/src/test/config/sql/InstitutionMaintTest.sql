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

delete from pm_institution_t where pm_institution_id = '10000';
insert into pm_institution_t (`pm_institution_id`,`effdt`,`institution_code`,`description`,`active`,`timestamp`,`obj_id`,`ver_nbr`) values('10000', '2012-01-01', 'SOME-CODE', 'DESCRIPTION', 'Y', now(), uuid(), '1');