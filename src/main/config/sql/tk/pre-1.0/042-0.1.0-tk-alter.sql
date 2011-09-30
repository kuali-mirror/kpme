alter table hr_location_t add column timezone varchar(10) NOT NULL after location;
alter table tk_principal_calendar_t add column timezone varchar(10) NULL after holiday_calendar_group;