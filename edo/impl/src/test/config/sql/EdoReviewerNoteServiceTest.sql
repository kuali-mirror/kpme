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

delete from EDO_REVIEWER_NOTE_T where edo_reviewer_note_id >= '1000';

insert into EDO_REVIEWER_NOTE_T (`edo_reviewer_note_id`, `edo_dossier_id`, `USER_PRINCIPAL_ID`, `note`, `review_date`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1000', '1000', 'admin', 'reviewer note1','2014-01-01', now(), null, '1');
insert into EDO_REVIEWER_NOTE_T (`edo_reviewer_note_id`, `edo_dossier_id`, `USER_PRINCIPAL_ID`, `note`, `review_date`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1001', '1000', 'admin', 'reviewer note2','2014-02-01', now(), null, '1');
insert into EDO_REVIEWER_NOTE_T (`edo_reviewer_note_id`, `edo_dossier_id`, `USER_PRINCIPAL_ID`, `note`, `review_date`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1002', '1000', 'reviewChair', 'reviewer note3','2014-03-01', now(), null, '1');
insert into EDO_REVIEWER_NOTE_T (`edo_reviewer_note_id`, `edo_dossier_id`, `USER_PRINCIPAL_ID`, `note`, `review_date`, `TIMESTAMP`, `OBJ_ID`, `VER_NBR`) values ('1003', '1000', 'reviewChair', 'reviewer note4','2014-04-01', now(), null, '1');
