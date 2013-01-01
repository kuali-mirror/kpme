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

delete from tk_document_header_t where document_id = '5000';

insert into tk_document_header_t (`document_id`, `principal_id`, `pay_end_dt`, `document_status`, `pay_begin_dt`, `obj_id`, `ver_nbr`) values ('5000', 'admin', '2012-03-15 00:00:00', 'I', '2012-03-01 00:00:00', NULL, '1');