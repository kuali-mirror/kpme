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

delete from hr_roles_t where hr_roles_id = '1000';

insert into hr_roles_t (hr_roles_id, principal_id, role_name, user_principal_id, effdt, active) values ('1000', 'fred', 'TK_SYS_ADMIN', 'admin', '2010-01-01', 'Y');