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

SET SQL_SAFE_UPDATES=0;
DELETE FROM `hr_position_s` WHERE `id` > '1000';
INSERT INTO `hr_position_s` (`id`) VALUES ('2085'), ('2086'), ('2087'), ('2088'), ('2089'), ('2090'), ('2091'), ('2093');
DELETE FROM `hr_position_t` WHERE `hr_position_id` > '1000';
INSERT INTO `hr_position_t` (`hr_position_id`, `position_nbr`, `description`, `effdt`, `TIMESTAMP`, `obj_id`, `ver_nbr`, `active`, `WORK_AREA`) VALUES ('2085', '2091', 'Position #1', '2011-01-01', '2011-06-15 16:30:32', 'D45E627F-FA3D-A1DF-87FD-37F5B3927D86', '5','Y', '1003'), ('2089', '2086', 'CLRTC', '2011-01-01', '2011-09-21 10:42:26', '49230A32-73F6-8E9A-CBB6-59B94F5418D8', '1','Y', '1005'), ('2090', '2087', 'AST', '2011-09-01', '2011-09-21 10:44:18', 'A74BB119-3521-896D-C25E-D1CF496D27E8', '1','Y', '1007'), ('2091', '2089', 'CLRMN', '2011-09-01', '2011-09-21 10:43:53', '7FF75DBA-1783-D28A-C858-9B29FCD3C48A', '1','Y', '1000');
