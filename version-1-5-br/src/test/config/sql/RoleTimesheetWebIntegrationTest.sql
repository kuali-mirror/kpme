# Admin should be setup already, no need to add TK_ADMIN.
# id,user,role,adding_user,work_area,dept,chart,effdt,...
insert into hr_roles_t values ('2000', 'testuser6', 'TK_DEPT_ADMIN', 'admin', NULL,'TEST-DEPT', NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2001', 'fran',      'TK_APPROVER',   'admin', '30', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2002', 'frank',     'TK_REVIEWER',   'admin', '30', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2003', 'edna',      'TK_GLOBAL_VO',  'admin', '30', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);

# Employee Fred: No Role Needed
# insert into hr_roles_t values ('2004', 'fred', 'TK_EMPLOYEE', 'admin', 'WA', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);

# Roles that are not applicable to see data from selected employee.
insert into hr_roles_t values ('2005', 'testuser1', 'TK_DEPT_ADMIN', 'admin', NULL,   'TEST-DEPT2', NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2006', 'testuser2', 'TK_APPROVER',   'admin', '1234', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2007', 'testuser3', 'TK_REVIEWER',   'admin', '1234', NULL, NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);
insert into hr_roles_t values ('2008', 'testuser4', 'TK_DEPT_VO',    'admin', NULL,   'TEST-DEPT2', NULL, '2010-08-20', NULL, '2010-08-20 16:13:57', 'Y', NULL);

# Set up Fred as an employee
insert into tk_assignment_t values('2000', 'fred', '30', '2010-01-01', '30', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'Y', '2010-07-27 10:25:13');
insert into hr_job_t values('2000', 'fred', '30', '2010-01-01', 'TEST-DEPT', 'S12', 'S12', '2010-08-01 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '0.000000', 'BL', '40.00', NULL, 'BW', 'Y', 'N', NULL);

# Set up principal Calendars
insert into hr_principal_calendar_t values('testuser1', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('testuser2', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('testuser3', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('testuser4', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('testuser5', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('testuser6', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('fran', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('frank', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('edna', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');
insert into hr_principal_calendar_t values('fred', 'BWS-CAL', 'HOL', null, '2010-01-01', '2011-03-01 11:45:56', '6113aa9c-4423-11e0-a509-124832221506', '1', 'Y');

