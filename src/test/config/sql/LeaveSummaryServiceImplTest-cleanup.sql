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

delete from lm_leave_document_header_t where document_id >= '5000';
delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003');
delete from hr_principal_attributes_t where principal_id like('testUser%');
delete from lm_leave_plan_t where lm_leave_plan_id >= '5000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '5000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= '5000';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from lm_leave_block_t where principal_id like('testUser%');