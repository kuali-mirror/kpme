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

delete from EDO_WORKFLOW_DEF_T where EDO_WORKFLOW_ID >= '1000';

insert into EDO_WORKFLOW_DEF_T (`EDO_WORKFLOW_ID`, `WORKFLOW_NAME`, `WORKFLOW_DESCRIPTION`, `USER_PRINCIPAL_ID`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', 'MainWorkFlow', 'MainWorkFlowDesc', 'admin', now(), uuid(), '1');
insert into EDO_WORKFLOW_DEF_T (`EDO_WORKFLOW_ID`, `WORKFLOW_NAME`, `WORKFLOW_DESCRIPTION`, `USER_PRINCIPAL_ID`, `ACTION_TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1001', 'SuppWorkFlow', 'SuppWorkFlowDesc', 'admin', now(), uuid(), '1');



