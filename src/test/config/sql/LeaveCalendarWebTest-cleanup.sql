Delete from lm_leave_document_header_t where document_id='1000';
Delete from lm_leave_document_header_t where document_id='1001';
Delete from lm_leave_document_header_t where document_id='1002';

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('59', '60', '61', '62', '63', '64', '65');