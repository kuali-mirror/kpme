DROP TABLE IF EXISTS `lm_prin_accr_ran_t`;
CREATE TABLE `lm_prin_accr_ran_t` (
 	`principal_id` varchar(40) NOT NULL DEFAULT '',
   `last_ran_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);