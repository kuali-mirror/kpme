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

delete from hr_principal_attributes_t where principal_id in ('edna');
delete from hr_job_t where principal_id in ('edna');
delete from tk_assignment_t where principal_id in ('edna');
#delete from tk_time_block_hist_detail_t;
#delete from tk_time_block_hist_t;
#delete from tk_time_block_t;