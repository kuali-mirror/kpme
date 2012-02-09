delete from lm_accrual_category_t where lm_accrual_category_id = '3000';
delete from lm_accrual_category_rules_t where lm_accrual_category_rules_id = '3000';

insert into lm_accrual_category_t values('3000', 'testAC', 'testLP', 'test', 'D', 'D', '2010-01-01', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', 'N', 'N', 'Y',now(), '1.5');	
insert into lm_accrual_category_rules_t values ('3000', 'M', 1, 12, 0.50, 100.00, 'NA', 'NA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '3000', 'DEDC243D-4E51-CCDE-1326-E1700B2631E1', '1', 'Y', '2012-01-09 12:10:23');