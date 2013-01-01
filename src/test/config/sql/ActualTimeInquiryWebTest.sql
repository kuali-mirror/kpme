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

delete from hr_calendar_entries_t where hr_calendar_entry_id = '5000';
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('5000', '2', 'BWS-CAL', now(), ADDDATE(now(),14));

delete from tk_document_header_t where document_id = '2';
insert into tk_document_header_t values ('2', 'admin', ADDDATE(now(),14), 'I', now(), NULL, '1');

update tk_grace_period_rl_t set active = 'Y' where tk_grace_period_rule_id = '1';
