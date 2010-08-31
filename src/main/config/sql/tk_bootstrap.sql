#
# Departments
DELETE FROM `tk`.`tk_dept_t`;
INSERT INTO `tk`.`tk_dept_t`	(`dept_id`,	`DESCRIPTION`,	`ORG`,	`CHART`)	VALUES	
	('TEST-DEPT', 'test department', 'TEST', 'DEPT');

#
# Work Areas
DELETE FROM `tk`.`tk_work_area_s`;
INSERT INTO `tk`.`tk_work_area_s`	(`ID`)	VALUES	('1000');
DELETE FROM `tk`.`tk_work_area_t`;
INSERT INTO `tk`.`tk_work_area_t` (`WORK_AREA_ID`, `EFFDT`,`ACTIVE`,`DESCR`,`DEPT_ID`,`OVERTIME_PREFERENCE`,`ADMIN_DESCR`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`) VALUES
	('100', '2010-01-05', '1', 'work area description', 'TEST-DEPT', 'OT1', 'work area admin description', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20');

#
# Task
DELETE FROM `tk`.`tk_task_s`;
INSERT INTO `tk`.`tk_task_s` (`ID`)	VALUES	('110');
DELETE FROM `tk`.`tk_task_t`;
INSERT INTO `tk`.`tk_task_t`	(`task_id`,	`work_area_id`,	`effdt`,	`descr`,	`admin_descr`,	`timestamp`,	`active`,	`obj_id`,	`ver_nbr`,	`USER_PRINCIPAL_ID`)	VALUES
	('100', '100', '2010-08-01', 'description 1', 'admin description 1', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
	('101', '100', '2010-08-01', 'description 2', 'admin description 2', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin'),
	('102', '100', '2010-08-01', 'description 3', 'admin description 3', '2010-08-10 16:00:13', '1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin');

#
# Assignments
DELETE FROM `tk`.`tk_assignment_s`;
INSERT INTO `tk`.`tk_assignment_s` (`ID`) VALUES ('100');
DELETE FROM `tk`.`tk_assignment_t`;
INSERT INTO `tk`.`tk_assignment_t`	(`ASSIGNMENT_ID`,	`PRINCIPAL_ID`,	`JOB_NUMBER`,	`EFFDT`,	`ERNCD`,	`WORK_AREA_ID`,	`TASK_ID`,	`OBJ_ID`,	`VER_NBR`,	`active`)	VALUES
	('10', 'admin', '1015', '2010-08-01', 'ERNCD', '100', 'TASK_ID', null, '1', '1');

#
# HR Pay Types
DELETE FROM `tk`.`hr_paytype_s`;
INSERT INTO `tk`.`hr_paytype_s` (`ID`)	VALUES	('10');
DELETE FROM `tk`.`hr_paytype_t`;
INSERT INTO `tk`.`hr_paytype_t` (`PAYTYPE_ID`,`PAYTYPE`,`DESCR`,`CALENDAR_GROUP`,`REG_ERN_CODE`,`EFFDT`,`TIMESTAMP`,`HOLIDAY_CALENDAR_GROUP`,`OBJ_ID`,`VER_NBR`) VALUES
	('1', 'BW', 'description', 'BW-CAL1', 'RGN', '2010-08-04', '2010-08-04 16:01:07', 'HOL', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1');

#
# HR Job
DELETE FROM `tk`.`hr_job_s`;
INSERT INTO `tk`.`hr_job_s` (`ID`)	VALUES	('10');
DELETE FROM `tk`.`hr_job_t`;
INSERT INTO `tk`.`hr_job_t` (`JOB_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`dept_id`,`TK_SAL_GROUP`,`PAY_GRADE`,`TIMESTAMP`,`ACTIVE`,`OBJ_ID`,`VER_NBR`,`location`,`std_hours`,`fte`,`hr_paytype_id`) VALUES
	('1015', 'admin', '0', '2010-08-10', 'TEST-DEPT', NULL, NULL, '2010-08-10 16:00:13', '1', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', NULL, '40.00', NULL, '1');

#
# Pay Calendar
DELETE FROM `tk`.`tk_py_calendar_s`;
INSERT INTO `tk`.`tk_py_calendar_s`	(`ID`)	VALUES	('30');
DELETE FROM `tk`.`tk_py_calendar_t`;
INSERT INTO `tk`.`tk_py_calendar_t`	(`tk_py_calendar_id`,	`calendar_group`,	`chart`,	`begin_date`,	`begin_time`,	`end_date`,	`end_time`)	VALUES
	('20', 'BW-CAL1', 'CHART1', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59'),
	('21', 'BW-CAL2', 'CHART2', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59'),
	('22', 'BW-CAL3', 'CHART3', '2010-01-01', '00:00:00', '2010-12-31', '23:59:59');

#
# Pay Calendar Dates
DELETE FROM `tk`.`tk_py_calendar_dates_s`;
INSERT INTO `tk`.`tk_py_calendar_dates_s`	(`ID`)	VALUES	('30');
DELETE FROM `tk`.`tk_py_calendar_dates_t`;
INSERT INTO `tk`.`tk_py_calendar_dates_t` (`tk_py_calendar_dates_id`,`tk_py_calendar_id`,`begin_period_date`,`begin_period_time`,`end_period_date`,`end_period_time`,`initiate_date`,`initiate_time`,`end_pay_period_date`,`end_pay_period_time`,`employee_approval_date`,`employee_approval_time`,`supervisor_approval_date`,`supervisor_approval_time`) VALUES
	('1', '20', '2010-08-01', '00:00:00', '2010-08-14', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('2', '20', '2010-08-15', '00:00:00', '2010-08-31', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('3', '20', '2010-09-01', '00:00:00', '2010-09-14', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('4', '20', '2010-09-15', '00:00:00', '2010-09-30', '23:59:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
