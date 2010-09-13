#
# Departments
DELETE FROM `tk_dept_t`;
INSERT INTO `tk_dept_t`	(`dept_id`,	`DESCRIPTION`,	`ORG`,	`CHART`)	VALUES
	('TEST-DEPT', 'test department', 'TEST', 'DEPT'),
	('TEST-DEPT1', 'test department1', 'TEST', 'DEPT'),
	('TEST-DEPT2', 'test department2', 'TEST', 'DEPT'),
	('TEST-DEPT3', 'test department3', 'TEST', 'DEPT'),
	('TEST-DEPT4', 'test department4', 'TEST', 'DEPT'),
	('TEST-DEPT5', 'test department5', 'TEST', 'DEPT'),
	('TEST-DEPT6', 'test department6', 'TEST', 'DEPT'),
	('TEST-DEPT7', 'test department7', 'TEST', 'DEPT');
#
# Work Areas
DELETE FROM `tk_work_area_s`;
INSERT INTO `tk_work_area_s`	(`ID`)	VALUES	('1000');
DELETE FROM `tk_work_area_t`;
INSERT INTO `tk_work_area_t` (`WORK_AREA_ID`, `EFFDT`,`ACTIVE`,`DESCR`,`DEPT_ID`,`OVERTIME_PREFERENCE`,`ADMIN_DESCR`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`) VALUES
	('100', '2010-01-05', '1', 'work area description', 'TEST-DEPT', 'OT1', 'work area admin description', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
	('101', '2010-01-05', '1', 'work area description2', 'TEST-DEPT2', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
	('102', '2010-01-05', '1', 'work area description3', 'TEST-DEPT3', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20'),
	('103', '2010-01-05', '1', 'work area description4', 'TEST-DEPT4', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20');
#
# Task
DELETE FROM `tk_task_s`;
INSERT INTO `tk_task_s` (`ID`)	VALUES	('110');
DELETE FROM `tk_task_t`;
INSERT INTO `tk_task_t`	(`task_id`,	`work_area_id`,	`effdt`,	`descr`,	`admin_descr`,	`timestamp`,	`active`,	`obj_id`,	`ver_nbr`,	`USER_PRINCIPAL_ID`)	VALUES
	('100', '100', '2010-08-01', 'description 1', 'admin description 1', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
	('101', '100', '2010-08-01', 'description 2', 'admin description 2', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
	('102', '100', '2010-08-01', 'description 3', 'admin description 3', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin');

#
# Assignments
DELETE FROM `tk_assignment_s`;
INSERT INTO `tk_assignment_s` (`ID`) VALUES ('100');
DELETE FROM `tk_assignment_t`;
INSERT INTO `tk_assignment_t`	(`ASSIGNMENT_ID`,	`PRINCIPAL_ID`,	`JOB_NUMBER`,	`EFFDT`,	`EARN_CODE_ID`,	`WORK_AREA_ID`,	`TASK_ID`,	`OBJ_ID`,	`VER_NBR`,	`active`)	VALUES
	('10', 'admin', '1', '2010-08-01', '10', '100', 'TASK_ID', null, '1', '1'),
	('11', 'admin', '2', '2010-08-01', '11', '101', 'TASK_ID', null, '1', '1'),
  ('12', 'eric', '1', '2010-08-01', '10', '100', 'TASK_ID', null, '1', '1'),
	('13', 'eric', '2', '2010-08-01', '11', '101', 'TASK_ID', null, '1', '1');

#
# HR Pay Types
DELETE FROM `hr_paytype_s`;
INSERT INTO `hr_paytype_s` (`ID`)	VALUES	('10');
DELETE FROM `hr_paytype_t`;
INSERT INTO `hr_paytype_t` (`PAYTYPE_ID`,`PAYTYPE`,`DESCR`,`CALENDAR_GROUP`,`REG_ERN_CODE`,`EFFDT`,`TIMESTAMP`,`HOLIDAY_CALENDAR_GROUP`,`OBJ_ID`,`VER_NBR`) VALUES
	('1', 'BW', 'description', 'BW-CAL1', 'RGN', '2010-08-04', '2010-08-04 16:01:07', 'HOL', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1');

#
# HR Job
DELETE FROM `hr_job_s`;
INSERT INTO `hr_job_s` (`ID`)	VALUES	('1100');
DELETE FROM `hr_job_t`;
INSERT INTO `hr_job_t` (`JOB_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`active`,`dept_id`,`TK_SAL_GROUP_ID`,`PAY_GRADE`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`location`,`std_hours`,`fte`,`hr_paytype_id`) VALUES
	('1012', 'admin', '1', '2010-08-10', '1', 'TEST-DEPT', '10', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1013', 'admin', '2', '2010-08-12', '1', 'TEST-DEPT1', '11', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1014', 'admin', '3', '2010-08-12', '1', 'TEST-DEPT2', '12', NULL, '2010-08-10 16:00:14', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1015', 'admin', '4', '2010-08-10', '1', 'TEST-DEPT3', '13', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1016', 'admin', '5', '2010-08-11', '1', 'TEST-DEPT4', '10', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1017', 'eric', '1', '2010-08-12', '1', 'TEST-DEPT5', '10', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1'),
	('1018', 'eric', '2', '2010-08-12', '1', 'TEST-DEPT6', '11', NULL, '2010-08-10 16:22:22', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1');

#
# Sal Group
DELETE FROM `tk_sal_group_s`;
INSERT INTO `tk_sal_group_S` (`ID`) VALUES ('10');
DELETE FROM `tk_sal_group_t`;
INSERT INTO `tk_sal_group_t` (`SAL_GROUP_ID`, `SAL_GROUP`, `EFFDT`, `ACTIVE`) VALUES ('10', 'A10', '2010-01-01', 1);
INSERT INTO `tk_sal_group_t` (`SAL_GROUP_ID`, `SAL_GROUP`, `EFFDT`, `ACTIVE`) VALUES ('11', 'S10', '2010-01-01', 1);
INSERT INTO `tk_sal_group_t` (`SAL_GROUP_ID`, `SAL_GROUP`, `EFFDT`, `ACTIVE`) VALUES ('12', 'A12', '2010-01-01', 1);
INSERT INTO `tk_sal_group_t` (`SAL_GROUP_ID`, `SAL_GROUP`, `EFFDT`, `ACTIVE`) VALUES ('13', 'S12', '2010-01-01', 1);

#
# dept earn code
DELETE FROM `tk_dept_earn_code_s`;
INSERT INTO `tk_dept_earn_code_s` VALUES('10');
DELETE FROM `tk_dept_earn_code_t`;
INSERT INTO `TK_DEPT_EARN_CODE_T` (`DEPT_EARN_CODE_ID`, `DEPT_ID`, `TK_SAL_GROUP_ID`, `EARN_CODE_ID`, `EMPLOYEE`, `APPROVER`, `ORG_ADMIN`) VALUES
	('10', 'TEST-DEPT', '10', '10', 1, 1, 1),
	('11', 'TEST-DEPT', '10', '11', 1, 1, 1),
	('12', 'TEST-DEPT', '10', '12', 1, 1, 1),
	('13', 'TEST-DEPT', '10', '13', 1, 1, 1),
	('14', 'TEST-DEPT2', '11', '14', 1, 1, 1),
	('15', 'TEST-DEPT2', '11', '15', 1, 1, 1),
	('16', 'TEST-DEPT2', '11', '16', 1, 1, 1),
	('17', 'TEST-DEPT2', '11', '17', 1, 1, 1);

#
# earn code
DELETE FROM `tk_earn_code_s`;
INSERT INTO `tk_earn_code_s` VALUES('10');
DELETE FROM `tk_earn_code_T`;
INSERT INTO `TK_EARN_CODE_T` (`EARN_CODE_ID`, `EARN_CODE`, `DESCR`, `EFFDT`, `ACTIVE`) VALUES
	('10', 'RGH', 'REGULAR HOURLY', '2010-01-01', 1),
	('11', 'SCK', 'SICK', '2010-01-01', 1),
	('12', 'VAC', 'VACATION', '2010-01-01', 1),
	('13', 'WEP', 'EMERGENCY', '2010-01-01', 1),
	('14', 'HAZ', 'HAZARD DAY', '2010-01-01', 1),
	('15', 'HIP', 'HOLIDAY INCENTIVE', '2010-01-01', 1),
	('16', 'OC1', 'ON CALL - 1.50', '2010-01-01', 1),
	('17', 'OC2', 'ON CALL - 2.00', '2010-01-01', 1),
	('18', 'PRM', 'PREMIUM', '2010-01-01', 1);

#
# Pay Calendar
DELETE FROM `tk_py_calendar_s`;
INSERT INTO `tk_py_calendar_s`	(`ID`)	VALUES	('30');
DELETE FROM `tk_py_calendar_t`;
INSERT INTO `tk_py_calendar_t`	(`tk_py_calendar_id`,	`calendar_group`,	`chart`,	`begin_date`,	`begin_time`,	`end_date`,	`end_time`)	VALUES
	('20', 'BW-CAL1', 'CHART1', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59'),
	('21', 'BW-CAL2', 'CHART2', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59'),
	('22', 'BW-CAL3', 'CHART3', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59');

#
# Pay Calendar Dates
DELETE FROM `tk_py_calendar_dates_s`;
INSERT INTO `tk_py_calendar_dates_s`	(`ID`)	VALUES	('30');
DELETE FROM `tk_py_calendar_dates_t`;
INSERT INTO `tk_py_calendar_dates_t` (`tk_py_calendar_dates_id`,`tk_py_calendar_id`,`begin_period_date`,`begin_period_time`,`end_period_date`,`end_period_time`,`initiate_date`,`initiate_time`,`end_pay_period_date`,`end_pay_period_time`,`employee_approval_date`,`employee_approval_time`,`supervisor_approval_date`,`supervisor_approval_time`) VALUES
	('1', '20', '2010-08-01', '00:00:00', '2010-08-14', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('2', '20', '2010-08-15', '00:00:00', '2010-08-31', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('3', '20', '2010-09-01', '00:00:00', '2010-09-14', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('4', '20', '2010-09-15', '00:00:00', '2010-09-30', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
