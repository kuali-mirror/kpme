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

delete from hr_principal_attributes_t where hr_principal_attribute_id in ('10000');
delete from tk_assignment_t where tk_assignment_id in ('10000', '10001');
delete from hr_job_t where hr_job_id in ('10000', '10001');
delete from hr_paytype_t where hr_paytype_id in ('10000', '10001');
delete from hr_earn_code_t where hr_earn_code_id in ('10000', '10001');