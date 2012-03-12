
ALTER TABLE lm_ledger_s RENAME lm_leave_block_s;

/* Renaming the table LM_LEDGER_T to LM_LEAVE_BLOCK_T */
ALTER TABLE lm_ledger_t RENAME lm_leave_block_t;

/* Remove Active field */
ALTER TABLE lm_leave_block_t DROP column active;

/* Remove TIMESTAMP_* and PRINICIPAL_* (Activated and Inactivated) and 
 * replace with TIMESTAMP and PRINCIPAL_ID_MODIFIED */
ALTER TABLE lm_leave_block_t DROP column principal_activated;
ALTER TABLE lm_leave_block_t DROP column principal_inactivated;

ALTER TABLE lm_leave_block_t DROP column timestamp_activated;
ALTER TABLE lm_leave_block_t DROP column timestamp_inactivated;

ALTER TABLE `lm_leave_block_t` ADD COLUMN `TIMESTAMP` time NOT NULL;
ALTER TABLE `lm_leave_block_t` ADD COLUMN `PRINCIPAL_ID_MODIFIED` varchar(40) DEFAULT NULL;

/* Add TK_ASSIGNMENT_ID to LM_LEAVE_BLOCK_T*/
ALTER TABLE `lm_leave_block_t` ADD COLUMN `TK_ASSIGNMENT_ID` VARCHAR(60) DEFAULT NULL;

/* rename LM_LEDGER_ID to LM_LEAVE_BLOCK_ID, rename LEDGER_DATE to LEAVE_DATE */
ALTER TABLE `lm_leave_block_t` CHANGE LM_LEDGER_ID LM_LEAVE_BLOCK_ID varchar(60) NOT NULL;
ALTER TABLE `lm_leave_block_t` CHANGE LEDGER_DATE LEAVE_DATE date NOT NULL;

/*Add field REQUEST_STATUS to LM_LEAVE_BLOCK_T. Varchar(1) - 
 * Values - P - Planned, R - Requested, A - Approved, C - Recorded, D - Disapproved, F - Deferred */
ALTER TABLE `lm_leave_block_t` ADD COLUMN REQUEST_STATUS VARCHAR(1) DEFAULT NULL;
