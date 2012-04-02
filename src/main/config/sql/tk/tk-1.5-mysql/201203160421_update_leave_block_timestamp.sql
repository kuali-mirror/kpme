ALTER TABLE `lm_leave_block_t` CHANGE `TIMESTAMP` `TIMESTAMP` timestamp NOT NULL;
ALTER TABLE `lm_leave_block_hist_t` CHANGE `TIMESTAMP` `TIMESTAMP` timestamp NOT NULL;
ALTER TABLE `lm_leave_block_hist_t` CHANGE `TIMESTAMP_DELETED` `TIMESTAMP_DELETED` timestamp;
ALTER TABLE `lm_leave_status_hist_t` CHANGE `TIMESTAMP` `TIMESTAMP` timestamp NOT NULL;
ALTER TABLE `lm_leave_block_t` CHANGE `hours` `leave_amount` int(11) NOT NULL;
ALTER TABLE `lm_leave_block_hist_t` CHANGE `hours` `leave_amount` int(11) NOT NULL;