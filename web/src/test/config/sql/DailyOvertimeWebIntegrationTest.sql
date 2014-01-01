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

# Create a simple daily overtime rule
#
# REG -> OVT; 8 hours Minimum; 15.00 Max Gap.
# Dept: TEST-DEPT       Work Area: 30
# Location: BW
insert into tk_daily_overtime_rl_t values (2000, 'SD1', 'BW', '2011-01-02', 'admin', '2011-07-26 11:23:34', 'TEST-DEPT', '30', '15.00', '8', 'Y', 'REG', 'OVT');