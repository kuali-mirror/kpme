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

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003');
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10000', '2', 'BWS-CAL', ADDDATE(now(),-1), ADDDATE(now(),13));
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10001', '3', 'BWS-LM', ADDDATE(now(),-1), ADDDATE(now(),13));
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10002', '2', 'BWS-CAL', ADDDATE(now(),13), ADDDATE(now(),27));
insert into hr_calendar_entries_t (hr_calendar_entry_id,hr_calendar_id, calendar_name, begin_period_date, end_period_date) values ('10003', '3', 'BWS-LM', ADDDATE(now(),13), ADDDATE(now(),27));