
ALTER TABLE lm_ledger_s RENAME lm_leave_block_s;


ALTER TABLE lm_ledger_t RENAME lm_leave_block_t;


ALTER TABLE lm_leave_block_t DROP column active;

ALTER TABLE lm_leave_block_t DROP column principal_activated;
ALTER TABLE lm_leave_block_t DROP column principal_inactivated;

ALTER TABLE lm_leave_block_t DROP column timestamp_activated;
ALTER TABLE lm_leave_block_t DROP column timestamp_inactivated;

ALTER TABLE `lm_leave_block_t` ADD COLUMN `TIMESTAMP` time NOT NULL;
ALTER TABLE `lm_leave_block_t` ADD COLUMN `PRINCIPAL_ID_MODIFIED` varchar(40) DEFAULT NULL;


ALTER TABLE `lm_leave_block_t` ADD COLUMN `TK_ASSIGNMENT_ID` VARCHAR(60) DEFAULT NULL;


ALTER TABLE `lm_leave_block_t` CHANGE LM_LEDGER_ID LM_LEAVE_BLOCK_ID varchar(60) NOT NULL;
ALTER TABLE `lm_leave_block_t` CHANGE LEDGER_DATE LEAVE_DATE date NOT NULL;

ALTER TABLE `lm_leave_block_t` ADD COLUMN REQUEST_STATUS VARCHAR(1) DEFAULT NULL;
