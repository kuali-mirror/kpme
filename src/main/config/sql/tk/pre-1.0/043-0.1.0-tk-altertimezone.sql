alter table hr_location_t modify column timezone varchar(30) NOT NULL default 'America\\Indianapolis';
alter table tk_principal_calendar_t modify column timezone varchar(30) NULL after holiday_calendar_group;

