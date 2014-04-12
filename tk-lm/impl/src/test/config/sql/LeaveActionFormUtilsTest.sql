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

-- delete from hr_calendar_entries_t where hr_calendar_id = 2 and calendar_name = 'BWS-CAL' and begin_period_date = '2012-03-01 00:00:00' and end_period_date = '2012-03-15 00:00:00';
delete from lm_leave_document_header_t where DOCUMENT_ID='2';
delete from krew_doc_hdr_t where DOC_HDR_ID='2';

INSERT INTO krew_doc_hdr_t (`DOC_HDR_ID`,`DOC_TYP_ID`,`DOC_HDR_STAT_CD`,`RTE_LVL`,`STAT_MDFN_DT`,`CRTE_DT`,`APRV_DT`,`FNL_DT`,`RTE_STAT_MDFN_DT`,`TTL`,`APP_DOC_ID`,`DOC_VER_NBR`,`INITR_PRNCPL_ID`,`VER_NBR`,`RTE_PRNCPL_ID`,`DTYPE`,`OBJ_ID`,`APP_DOC_STAT`,`APP_DOC_STAT_MDFN_DT`) VALUES ('2','3096','S',0, '2014-04-11','2014-04-11',null,null,'2014-04-11','LeaveCalendarDocument - admin, admin (admin) - 03/01/2012-03/15/2012',null, 1, 'admin', 6, null, null, uuid(), null, null);
INSERT INTO lm_leave_document_header_t (`DOCUMENT_ID`,`PRINCIPAL_ID`,`DOCUMENT_STATUS`,`OBJ_ID`,`VER_NBR`) VALUES ('2','admin','S',uuid(),1);