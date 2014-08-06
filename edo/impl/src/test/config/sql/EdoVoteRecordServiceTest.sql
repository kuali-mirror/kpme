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

delete from EDO_VOTE_RECORD_T where edo_vote_record_id >= '1000';

INSERT INTO EDO_VOTE_RECORD_T(`edo_vote_record_id`,`edo_dossier_id`,`EDO_REVIEW_LAYER_DEF_ID`,`vote_type`, `aoe_code`, `yes_count`, `no_count`, `absent_count`,`abstain_count`,`vote_round`, `vote_subround`,`CREATED_AT`,`UPDATED_AT`,`CREATED_BY`,`VER_NBR`, `OBJ_ID`) VALUES ('1000', '1000', '1000', 'P', 'T', '10', '1', '0', '0', '1', '0', now(), now(), 'admin', 1, uuid());
INSERT INTO EDO_VOTE_RECORD_T(`edo_vote_record_id`,`edo_dossier_id`,`EDO_REVIEW_LAYER_DEF_ID`,`vote_type`, `aoe_code`, `yes_count`, `no_count`, `absent_count`,`abstain_count`,`vote_round`, `vote_subround`,`CREATED_AT`,`UPDATED_AT`,`CREATED_BY`,`VER_NBR`, `OBJ_ID`) VALUES ('1001', '1000', '1001', 'P', 'R', '9',  '1', '1', '0', '2', '0', now(), now(), 'admin', 1, uuid());
INSERT INTO EDO_VOTE_RECORD_T(`edo_vote_record_id`,`edo_dossier_id`,`EDO_REVIEW_LAYER_DEF_ID`,`vote_type`, `aoe_code`, `yes_count`, `no_count`, `absent_count`,`abstain_count`,`vote_round`, `vote_subround`,`CREATED_AT`,`UPDATED_AT`,`CREATED_BY`,`VER_NBR`, `OBJ_ID`) VALUES ('1003', '1000', '1001', 'P', 'R', '10', '1', '0', '1', '3', '1', now(), now(), 'admin', 1, uuid());

INSERT INTO EDO_VOTE_RECORD_T(`edo_vote_record_id`,`edo_dossier_id`,`EDO_REVIEW_LAYER_DEF_ID`,`vote_type`, `aoe_code`, `yes_count`, `no_count`, `absent_count`,`abstain_count`,`vote_round`, `vote_subround`,`CREATED_AT`,`UPDATED_AT`,`CREATED_BY`,`VER_NBR`, `OBJ_ID`) VALUES ('1002', '1010', '1000', 'P', 'T', '1',  '9', '0', '0', '1', '0', now(), now(), 'admin', 1, uuid());

INSERT INTO EDO_VOTE_RECORD_T(`edo_vote_record_id`,`edo_dossier_id`,`EDO_REVIEW_LAYER_DEF_ID`,`vote_type`, `aoe_code`, `yes_count`, `no_count`, `absent_count`,`abstain_count`,`vote_round`, `vote_subround`,`CREATED_AT`,`UPDATED_AT`,`CREATED_BY`,`VER_NBR`, `OBJ_ID`) VALUES ('1004', '1020', '1000', 'P', 'T', '10', '1', '0', '0', '1', '0', now(), now(), 'admin', 1, uuid());

