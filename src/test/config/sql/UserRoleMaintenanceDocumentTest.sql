delete from hr_roles_t where hr_roles_id = '1000';

insert into hr_roles_t (hr_roles_id, principal_id, role_name, user_principal_id, effdt, active) values ('1000', 'fred', 'TK_SYS_ADMIN', 'admin', '2010-01-01', 'Y');