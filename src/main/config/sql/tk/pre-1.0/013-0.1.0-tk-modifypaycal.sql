ALTER TABLE hr_py_calendar_t DROP COLUMN `begin_date` ,
DROP COLUMN `begin_time` , DROP COLUMN `chart` , DROP COLUMN `end_date` , DROP COLUMN `end_time` ;

alter table tk_py_calendar_dates_t rename hr_py_calendar_entries_t;
alter table tk_py_calendar_dates_s rename tk_py_calendar_entries_s;

ALTER TABLE hr_py_calendar_entries_t CHANGE COLUMN `tk_py_calendar_dates_id` `hr_py_calendar_entry_id` BIGINT(20) NOT NULL
, DROP PRIMARY KEY , ADD PRIMARY KEY (`hr_py_calendar_entry_id`) ;