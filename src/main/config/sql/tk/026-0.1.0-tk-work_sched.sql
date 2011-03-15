alter table hr_work_schedule_t drop column DEPT, drop column work_area, drop column principal_id;

alter table hr_work_schedule_entry_t drop column calendar_group, drop column day_of_period, drop column reg_hours;

alter table hr_work_schedule_entry_t add column index_of_day decimal(8,0) NOT NULL DEFAULT '0';

alter table hr_work_schedule_entry_t add column begin_time timestamp NOT NULL;

alter table hr_work_schedule_entry_t add column end_time timestamp NOT NULL;