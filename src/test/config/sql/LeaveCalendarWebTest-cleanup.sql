Delete from lm_leave_document_header_t where document_id='1000';
Delete from lm_leave_document_header_t where document_id='1001';
Delete from lm_leave_document_header_t where document_id='1002';

delete from hr_calendar_entries_t where hr_calendar_entry_id in ('10000', '10001', '10002', '10003', '10004', '10005', '10006');