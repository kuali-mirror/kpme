#
# HR Job
DELETE FROM `hr_job_s`;
INSERT INTO `hr_job_s` (`ID`)	VALUES	('1100');
DELETE FROM `hr_job_t`;
INSERT INTO `hr_job_t` (`HR_JOB_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`active`,`dept`,`TK_SAL_GROUP`,`PAY_GRADE`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`location`,`std_hours`,`fte`,`hr_paytype`) VALUES
	('1012', 'admin', '1', '2010-08-10', '1', 'TEST-DEPT', '10', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1013', 'admin', '2', '2010-08-10', '1', 'TEST-DEPT', '11', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1014', 'admin', '2', '2010-08-11', '1', 'TEST-DEPT', '12', NULL, '2010-08-11 16:00:14', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1015', 'admin', '2', '2010-08-11', '1', 'TEST-DEPT', '13', NULL, '2010-08-11 16:00:15', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1016', 'admin', '2', '2010-08-12', '1', 'TEST-DEPT', '10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1017', 'eric', '1', '2010-08-12', '1', 'TEST-DEPT', '10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1018', 'eric', '1', '2010-08-12', '1', 'TEST-DEPT', '10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1019', 'eric', '2', '2010-08-10', '1', 'TEST-DEPT', '10', NULL, '2010-08-10 16:22:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1020', 'eric', '2', '2010-08-13', '1', 'TEST-DEPT', '11', NULL, '2010-08-13 16:22:22', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1');

#
# Departments
DELETE FROM `tk_dept_s`;
INSERT INTO `tk_dept_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_dept_t`;
INSERT INTO `tk_dept_t` (`tk_dept_id`,`dept`,`DESCRIPTION`,`ORG`,`CHART`,`EFFDT`,`TIMESTAMP`,`ACTIVE`) VALUES
    (100 , 'TEST-DEPT'  , 'test department'  , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (101 , 'TEST-DEPT1' , 'test department1' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (102 , 'TEST-DEPT2' , 'test department2' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (103 , 'TEST-DEPT3' , 'test department3' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (104 , 'TEST-DEPT4' , 'test department4' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (105 , 'TEST-DEPT5' , 'test department5' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (106 , 'TEST-DEPT6' , 'test department6' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1)  ,
    (107 , 'TEST-DEPT7' , 'test department7' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 1);

#
# Work Areas
DELETE FROM `tk_work_area_s`;
INSERT INTO `tk_work_area_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_work_area_t`;
INSERT INTO `tk_work_area_t` (`TK_WORK_AREA_ID`, `WORK_AREA`, `EFFDT`,`ACTIVE`,`DESCR`,`DEPT`,`DEFAULT_OVERTIME_PREFERENCE`,`ADMIN_DESCR`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`) VALUES
    (100,'1234', '2010-01-05', '1', 'work area description', 'TEST-DEPT', 'OT1', 'work area admin description', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
    (101, '2345', '2010-01-05', '1', 'work area description2', 'TEST-DEPT2', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
    (102, '3456', '2010-01-05', '1', 'work area description3', 'TEST-DEPT3', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
    (103, '4567', '2010-01-05', '1', 'work area description4', 'TEST-DEPT4', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20');

#
# Task
DELETE FROM `tk_task_s`;
INSERT INTO `tk_task_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_task_t`;
INSERT INTO `tk_task_t`(`tk_task_id`,`task`,`work_area`,`descr`,`admin_descr`,`obj_id`, `ver_nbr`,`USER_PRINCIPAL_ID`)  VALUES
    (100, '1', '1234', 'description 1', 'admin description 1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
    (101, '2', '1234', 'description 2', 'admin description 2', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
    (102, '3', '1234', 'description 3', 'admin description 3', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin');

#
# Assignments
DELETE FROM `tk_assignment_s`;
INSERT INTO `tk_assignment_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_assignment_t`;
INSERT INTO `tk_assignment_t` (`TK_ASSIGNMENT_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`EARN_CODE`,`WORK_AREA`,`TASK`,`OBJ_ID`,`TIMESTAMP`,`VER_NBR`,`active`) VALUES
    ('10', 'admin', '1', '2010-08-01', 'RGN', '1234', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE','2010-07-27 10:25:13', '1', '1'),
    ('11', 'admin', '2', '2010-08-01', 'RGH', '1234', '2', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE','2010-07-27 10:25:13', '1', '1'),
    ('12', 'eric', '1', '2010-08-01', 'RGH', '2345', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE','2010-07-27 10:25:13', '1', '1'),
    ('13', 'eric', '2', '2010-08-01', 'RGI', '3456', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE','2010-07-27 10:25:13', '1', '1');

#
# dept earn code
DELETE FROM `tk_dept_earn_code_s`;
INSERT INTO `tk_dept_earn_code_s` VALUES('1000');
DELETE FROM `tk_dept_earn_code_t`;
INSERT INTO `TK_DEPT_EARN_CODE_T`
    (`TK_DEPT_EARN_CODE_ID`, `DEPT`, `TK_SAL_GROUP`, `EARN_CODE`, `EMPLOYEE`, `APPROVER`, `ORG_ADMIN`, `EFFDT`, `TIMESTAMP`, `ACTIVE`) VALUES
	('10' , 'TEST-DEPT'  , '10' , 'RGH' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('11' , 'TEST-DEPT'  , '10' , 'SCK' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('12' , 'TEST-DEPT'  , '10' , 'VAC' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('13' , 'TEST-DEPT'  , '10' , 'WEP' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('14' , 'TEST-DEPT2' , '11' , 'HAZ' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('15' , 'TEST-DEPT2' , '11' , 'HIP' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('16' , 'TEST-DEPT2' , '11' , 'OC1' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1)  ,
	('17' , 'TEST-DEPT2' , '11' , 'OC2' , 1 , 1 , 1 , '2010-08-01' , '2010-01-01 08:08:08' , 1);

#
# earn code
DELETE FROM `tk_earn_code_s`;
INSERT INTO `tk_earn_code_s` VALUES('1000');
DELETE FROM `tk_earn_code_T`;
INSERT INTO `TK_EARN_CODE_T` (`TK_EARN_CODE_ID`, `EARN_CODE`, `DESCR`, `RECORD_TIME`,`RECORD_HOURS`,`RECORD_AMOUNT`,`EFFDT`, `TIMESTAMP`, `ACTIVE`) VALUES
	('10' , 'RGH' , 'REGULAR HOURLY'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('11' , 'SCK' , 'SICK'              , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('12' , 'VAC' , 'VACATION'          , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('13' , 'WEP' , 'EMERGENCY'         , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('14' , 'HAZ' , 'HAZARD DAY'        , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('15' , 'HIP' , 'HOLIDAY INCENTIVE' , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('16' , 'OC1' , 'ON CALL - 1.50'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('17' , 'OC2' , 'ON CALL - 2.00'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ,
	('18' , 'PRM' , 'PREMIUM'           , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 1) ;
