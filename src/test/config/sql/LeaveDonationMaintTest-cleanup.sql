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

delete from lm_leave_donation_t where LM_LEAVE_DONATION_ID = '3000';
delete from hr_principal_attributes_t where hr_principal_attribute_id in ('10001', '10003');
delete from lm_leave_plan_t where lm_leave_plan_id >= '8000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from lm_leave_block_t where principal_id in('testuser1', 'testuser2');
delete from lm_leave_block_hist_t where principal_id in('testuser1', 'testuser2');