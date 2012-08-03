delete from hr_earn_code_t where hr_earn_code_id >= '5000';
delete from hr_earn_code_group_t where hr_earn_code_group_id >= '5000';
delete from hr_earn_code_group_def_t where hr_earn_code_group_def_id >= '5000';

insert into hr_earn_code_t values('5000', 'ECA', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'Y', 'test', null, 'N', 'Hours');
insert into hr_earn_code_t values('5001', 'ECB', 'test', '2012-02-01', 'Y', 'Y', 'B2991ADA-E866-F28C-7E95-A897AC377D0C', '1', now(), 'testAC', '1.5', '1.5', 'testLP', 'None', '99', 'T', 'N', 'Y', 'Y', 'Y', 'N', 'test', null, 'N', 'Hours');

insert into hr_earn_code_group_t (`hr_earn_code_group_id`, `earn_code_group`, `descr`, `show_summary`, `effdt`, `active`, `timestamp`,`warning_text` ) values('5000', 'group1', 'group1', 'Y','2012-02-01', 'Y', now(), 'Test Message');
insert into hr_earn_code_group_t (`hr_earn_code_group_id`, `earn_code_group`, `descr`, `show_summary`, `effdt`, `active`, `timestamp`,`warning_text` ) values('5001', 'group2', 'group2', 'Y','2012-02-01', 'Y', now(), null);

insert into hr_earn_code_group_def_t (`hr_earn_code_group_def_id`, `hr_earn_code_group_id`,`earn_code`) values ('5000', '5000', 'ECA');