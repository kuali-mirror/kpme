--
-- Copyright 2004-2012 The Kuali Foundation
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

delete from lm_sys_schd_timeoff_t;
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from hr_principal_attributes_t where principal_id = '111';
delete from hr_location_t where hr_location_id = '1000';
delete from hr_earn_code_t where HR_EARN_CODE_ID = '1000';
delete from hr_earn_code_t where HR_EARN_CODE_ID = '1001';