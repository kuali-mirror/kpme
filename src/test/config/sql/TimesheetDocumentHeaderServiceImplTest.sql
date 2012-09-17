delete from tk_document_header_t where document_id = '5000';

insert into tk_document_header_t (`document_id`, `principal_id`, `pay_end_dt`, `document_status`, `pay_begin_dt`, `obj_id`, `ver_nbr`) values ('5000', 'admin', '2012-03-15 00:00:00', 'I', '2012-03-01 00:00:00', NULL, '1');