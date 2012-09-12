delete from lm_leave_code_t where LM_LEAVE_CODE_ID = '1000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3000';
delete from lm_sys_schd_timeoff_t where LM_SYS_SCHD_TIMEOFF_ID = '3001';
delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
DELETE FROM `lm_leave_block_t`;
DELETE FROM `lm_leave_block_hist_t`;
DELETE FROM `lm_leave_status_hist_t`;
delete from lm_leave_document_header_t where document_id >= '5000';


