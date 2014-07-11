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

delete from EDO_ITEM_TYPE_T where EDO_ITEM_TYPE_ID >= '1000';

insert into EDO_ITEM_TYPE_T (`EDO_ITEM_TYPE_ID`, `ITEM_TYPE_NAME`, `DESCRIPTION`, `INSTRUCTIONS`, `EXT_AVAILABLE`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', 'Supporting Document', '', '', 'Y', '2012-01-01', 'Y', now(), null, '1');
insert into EDO_ITEM_TYPE_T (`EDO_ITEM_TYPE_ID`, `ITEM_TYPE_NAME`, `DESCRIPTION`, `INSTRUCTIONS`, `EXT_AVAILABLE`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1001', 'Review Letter', '', '', 'Y', '2012-01-01', 'Y', now(), null, '1');
insert into EDO_ITEM_TYPE_T (`EDO_ITEM_TYPE_ID`, `ITEM_TYPE_NAME`, `DESCRIPTION`, `INSTRUCTIONS`, `EXT_AVAILABLE`, `EFFDT`, `ACTIVE`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1002', 'Appeal', '', '', 'Y', '2012-01-01', 'Y', now(), null, '1');

