DELETE FROM lm_leave_block_t where lm_leave_block_id >= '5000';
delete from hr_calendar_entries_t where hr_calendar_entry_id = '5000';
delete from LM_LEAVE_DOCUMENT_HEADER_T where principal_id = 'admin';