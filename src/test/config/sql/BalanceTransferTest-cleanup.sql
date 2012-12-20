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

delete from lm_balance_transfer_t where lm_balance_transfer_id >= '10000';
delete from lm_leave_block_t where lm_leave_block_id >= '10000';
delete from lm_leave_block_hist_t where lm_leave_block_id >= '10000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id >= '3000';
delete from lm_accrual_category_t where lm_accrual_category_id >= '10055';
delete from hr_earn_code_t where hr_earn_code_id >= '5000';
