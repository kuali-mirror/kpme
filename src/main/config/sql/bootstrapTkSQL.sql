DELETE FROM `tk_dept_s`;
INSERT INTO `tk_dept_s` (`ID`) VALUES (1000);
DELETE FROM `tk_dept_t`;
INSERT INTO `tk_dept_t` (`tk_dept_id`,`dept`,`DESCRIPTION`,`ORG`,`CHART`,`EFFDT`,`TIMESTAMP`,`ACTIVE`) VALUES
    (100 , 'UA-FMOP'  , 'Financial Management Services'  , 'UA' , 'FMOP' , '2010-01-01' , '2010-01-01 10:25:13' , 'Y'),
    (101 , 'BL-CHEM'  , 'Chemistry'  , 'BL' , 'CHEM' , '2010-01-01' , '2010-01-01 10:25:13' , 'Y');

# HR Pay Types
DELETE FROM `hr_paytype_s`;
INSERT INTO `hr_paytype_s` (`ID`)	VALUES	('1000');
DELETE FROM `hr_paytype_t`;
INSERT INTO `hr_paytype_t` (`HR_PAYTYPE_ID`,`PAYTYPE`,`DESCR`,`REG_ERN_CODE`,`EFFDT`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`ACTIVE`) VALUES
	(1, 'BW1', 'Biweekly Support Staff', 'RGN', '2010-01-01', '2010-01-01 16:01:07', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1','Y'),
	(2, 'BWP', 'Biweekly Professional', 'RBP', '2010-01-01', '2010-01-01 16:01:07', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1','Y'),
	(3, 'HRR', 'Hourly Staff', 'RGH', '2010-01-01', '2010-01-01 16:01:07', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1','Y');

# Sal Group
DELETE FROM `tk_sal_group_s`;
INSERT INTO `tk_sal_group_S` (`ID`) VALUES ('1000');
DELETE FROM `tk_sal_group_t`;
INSERT INTO `tk_sal_group_t` (`TK_SAL_GROUP_ID`, `TK_SAL_GROUP`, `EFFDT`, `TIMESTAMP`, `ACTIVE`) VALUES
    (1,  'SS', '2010-01-01', '2010-01-01 01:01:01' , 'Y'),
    (2, 'PAE', '2010-01-01', '2010-01-01 08:08:08' , 'Y'),
    (3, 'HR', '2010-01-01', '2010-01-01 08:08:08' , 'Y');

DELETE FROM `hr_job_s`;
INSERT INTO `hr_job_s` (`ID`)	VALUES	(1000);
DELETE FROM `hr_job_t`;
INSERT INTO `hr_job_t` (`HR_JOB_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`active`,`dept`,`TK_SAL_GROUP`,`PAY_GRADE`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`location`,`std_hours`,`hr_paytype`, `primary_indicator`) VALUES
  	(1,  'fran', 0, '2010-01-01', 'Y', 'UA-FMOP', 'SS', '0F', '2010-01-01 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', 'BL', '40.00','BW1', 'Y'),
	(2, 'frank', 1, '2010-01-01', 'Y', 'BL-CHEM', 'PAE', '1IT', '2010-01-1 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', 'BL', '40.00', 'BWP', 'Y'),
	(3, 'frank', 2, '2010-01-01', 'Y', 'BL-CHEM', 'HR', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', 'BL', '0.01', 'HRR', 'N'),
	(4, 'eric', 0, '2010-01-01', 'Y', 'BL-CHEM', 'HR', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', 'BL', '0.01', 'HRR', 'Y');

# Work Areas
DELETE FROM `tk_work_area_s`;
INSERT INTO `tk_work_area_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_work_area_t`;
INSERT INTO `tk_work_area_t` (`TK_WORK_AREA_ID`, `WORK_AREA`, `EFFDT`,`ACTIVE`,`DESCR`,`DEPT`,`DEFAULT_OVERTIME_PREFERENCE`,`ADMIN_DESCR`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`) VALUES
    (100,'8529', '2010-01-05', 'Y', 'custservice', 'UA-FMOP', 'OT1', 'custservice', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1'),
    (101, '8540', '2010-01-05', 'Y', 'lab', 'BL-CHEM', 'OT1', 'lab', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1'),
    (102, '8541', '2010-01-05', 'Y', 'lab2', 'BL-CHEM', 'OT1', 'lab2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '1');

# Task
DELETE FROM `tk_task_s`;
INSERT INTO `tk_task_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_task_t`;
INSERT INTO `tk_task_t`(`tk_task_id`,`task`,`work_area`,`tk_work_area_id`,`descr`,`admin_descr`,`obj_id`, `ver_nbr`,`USER_PRINCIPAL_ID`)  VALUES
    (100, '1', '8540', 101,'test tube scrubber', 'admin test tube scrubber', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
    (101, 'C3', '8540', 101,'potion tester', 'lead potion tester', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin');

# Assignments
DELETE FROM `tk_assignment_s`;
INSERT INTO `tk_assignment_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_assignment_t`;
INSERT INTO `tk_assignment_t` (`TK_ASSIGNMENT_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`WORK_AREA`,`TASK`,`OBJ_ID`,`TIMESTAMP`,`VER_NBR`,`active`) VALUES
    (10 , 'fran' , 0 , '2010-08-01' , '8539' , NULL , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    (11 , 'frank' , 1 , '2010-08-01' , '8540' , 1 , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    (12 , 'frank'  ,2 , '2010-08-01' , '8541' , 'C3' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    (13 , 'eric'  , 0 , '2010-08-01' , '8541' , 'C3' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y');

# earn code
DELETE FROM `tk_earn_code_s`;
INSERT INTO `tk_earn_code_s` VALUES('1000');
DELETE FROM `tk_earn_code_T`;
INSERT INTO `TK_EARN_CODE_T` (`TK_EARN_CODE_ID`, `EARN_CODE`, `DESCR`, `RECORD_TIME`,`RECORD_HOURS`,`RECORD_AMOUNT`,`EFFDT`, `TIMESTAMP`, `ACTIVE`,`INFLATE_FACTOR`) VALUES
	('1'  , 'RGN' , 'REGULAR'           , 'y','n','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('2' , 'RGH' , 'REGULAR HOURLY'    , 'y','n','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('3' , 'SCK' , 'SICK'              , 'n','y','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('4' , 'VAC' , 'VACATION'          , 'n','y','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('5' , 'PTO' , 'PAID TIME OFF'     , 'n','y','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('6' , 'CPE' , 'COMP TIME'         , 'y','n','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0') ,
	('7' , 'OVT' , 'OVERTIME' 			, 'y','n','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.5') ,
	('8' , 'PRM' , 'PREMIUM'           , 'y','n','n', '2010-01-01' , '2010-01-01 08:08:08' , 'Y','1.0');
	insert into tk_earn_code_t values(9,'RBP','Regular Biweekly','2010-01-01','y','n','n','Y',null, 1,now(), null,0,1);
# time collection rule
DELETE FROM `tk_time_collection_rl_s`;
INSERT INTO `tk_time_collection_rl_s` VALUES('1000');
DELETE FROM `tk_time_collection_rl_t`;
INSERT INTO `tk_time_collection_rl_t` (`TK_TIME_COLL_RULE_ID`,`DEPT`,`WORK_AREA`,`EFFDT`,`CLOCK_USERS_FL`,`HRS_DISTRIBUTION_FL`,`USER_PRINCIPAL_ID`,
`TIMESTAMP`,`ACTIVE`) VALUES
	('1' , 'UA-FMOP' , 8529 , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 1)  ,
	('2' , 'BL-CHEM' , '8540' , '2010-01-01' , 0 , 1 , 'admin' , '2010-01-01 08:08:08' , 1)  ,
	('3' , 'BL-CHEM' , '8541'   , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 1);

DELETE FROM `tk_earn_group_s`;
INSERT INTO `tk_earn_group_s` VALUES ('1000');
DELETE FROM `tk_earn_group_t`;
INSERT INTO `tk_earn_group_t` (`tk_earn_group_id`,`earn_group`,`descr`,`effdt`,`active`,`OBJ_ID`,`VER_NBR`,`timestamp`, `show_summary`) VALUES
	(100,'R01','Summary for Regular Pay Hrs', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13','Y' ),
	(101,'R05','Summary for Prem/Shift Hrs', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13','Y' ),
	(102,'R02','Summary for Overtime Comp', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13','Y' ),
	(103,'O01','Overtime Eligible', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13','Y' ),
	(104,'O02','Hourly Ovt Eligible', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0EA7','20','2010-07-27 10:25:13','Y' ),
	(105,'O03','Biweekly Ovt Eligible', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0EF7','20','2010-07-27 10:25:13','Y' );

DELETE FROM `tk_earn_group_def_s`;
INSERT INTO `tk_earn_group_def_s` VALUES ('1000');
DELETE FROM `tk_earn_group_def_t`;
INSERT INTO `tk_earn_group_def_t` (`tk_earn_group_def_id`, `tk_earn_group_id`,`earn_code`,`OBJ_ID`,`VER_NBR`) VALUES
	(101,100,'RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(102,100,'RGH','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(103,100,'SCK','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(104,100,'VAC','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(105,100,'PTO','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(106,101,'PRM','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(107,102,'OVT','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(108,102,'CPE','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(109,103,'RGH','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(110,103,'RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(111,104,'RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	(112,105,'RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1);

DELETE FROM `la_accrual_categories_t`;
DELETE FROM `la_accrual_categories_s`;
INSERT INTO `la_accrual_categories_s` VALUES('1000');
insert into la_accrual_categories_t values('1','SCK','Sick','2010-01-01',uuid(),1,'Y',now());
insert into la_accrual_categories_t values('2','VAC','Vacation','2010-01-01',uuid(),1,'Y',now());

DELETE FROM `la_accruals_t`;
DELETE FROM `la_accruals_s`;
INSERT INTO `la_accruals_s` values ('1000');
insert into la_accruals_t values('1','fran','SCK','2010-01-01',100,0,0,uuid(),1);
insert into la_accruals_t values('2','fran','VAC','2010-01-01',10,0,0,uuid(),1);
insert into la_accruals_t values('3','frank','SCK','2010-01-01',5,0,0,uuid(),1);
insert into la_accruals_t values('4','frank','VAC','2010-01-01',90,0,0,uuid(),1);

DELETE FROM `tk_weekly_ovt_group_t`;
insert into tk_weekly_ovt_group_t values(1);

