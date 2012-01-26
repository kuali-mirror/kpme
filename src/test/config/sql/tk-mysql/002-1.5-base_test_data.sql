
#
# HR Job
DELETE FROM `hr_job_s`;
INSERT INTO `hr_job_s` (`ID`)	VALUES	(1000);
DELETE FROM `hr_job_t`;
INSERT INTO `hr_job_t` (`HR_JOB_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`active`,`dept`,`hr_SAL_GROUP`,`PAY_GRADE`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`location`,`std_hours`,`fte`,`hr_paytype`) VALUES
  ('1',  'admin', 30, '2010-01-01', 'Y', 'TEST-DEPT', 'SD1', 'SD1', '2010-08-1 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', 'SD1', '40.00', NULL, 'BW'),
	('12', 'admin', 1, '2010-08-01', 'Y', 'TEST-DEPT', 'A10', NULL, '2010-08-1 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('13', 'admin', 2, '2010-08-01', 'Y', 'TEST-DEPT', 'A12', NULL, '2010-08-10 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('14', 'admin', 2, '2010-08-11', 'Y', 'TEST-DEPT', 'A12', NULL, '2010-08-11 16:00:14', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
 ('16', 'admin', 2, '2010-08-12', 'Y', 'TEST-DEPT', 'A12', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('17', 'eric', 1, '2010-08-12', 'Y', 'TEST-DEPT', '10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('18', 'eric', 1, '2010-08-12', 'Y', 'TEST-DEPT', '10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('19', 'eric', 2, '2010-08-10', 'Y', 'TEST-DEPT', '10', NULL, '2010-08-10 16:22:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('20', 'eric', 2, '2010-08-13', 'Y', 'TEST-DEPT', '11', NULL, '2010-08-13 16:22:22', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('21', 'admin', 3, '2010-08-15', 'Y', 'NODEP', 'NOP', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('22', 'admin', 4, '2010-08-15', 'Y', 'NODEP', 'A10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('23', 'admin', 5, '2010-08-15', 'Y', 'LORA-DEPT', 'A10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW'),
	('24', 'admin', 6, '2010-01-01', 'Y', 'SHFT-DEPT', 'A10', NULL, '2010-08-12 16:00:13', 'A9225D4A-4871-4277-5638-4C7880A57621', '1', '', '40.00', NULL, 'BW');

#
# Departments
DELETE FROM `hr_dept_s`;
INSERT INTO `hr_dept_s` (`ID`) VALUES (1000);
DELETE FROM `hr_dept_t`;
INSERT INTO `hr_dept_t` (`hr_dept_id`,`dept`,`DESCRIPTION`,`ORG`,`CHART`,`EFFDT`,`TIMESTAMP`,`ACTIVE`, `LOCATION`) VALUES
    ('100' , 'TEST-DEPT'  , 'test department'  , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('101' , 'TEST-DEPT1' , 'test department1' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('102' , 'TEST-DEPT2' , 'test department2' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('103' , 'TEST-DEPT3' , 'test department3' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('104' , 'TEST-DEPT4' , 'test department4' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('105' , 'TEST-DEPT5' , 'test department5' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('106' , 'TEST-DEPT6' , 'test department6' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL')  ,
    ('107' , 'NODEP'      , 'test department7' , 'NODEP', 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' ,'Y', 'BL'),
    ('108' , 'TEST-DEPT7' , 'test department7' , 'TEST' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL'),
    ('109' , 'LORA-DEPT'  , 'loras department' , 'LORA' , 'DEPT' , '2010-01-31' , '2010-07-27 10:25:13' , 'Y', 'BL'),
    ('110' , 'SHFT-DEPT'  , 'shift department' , 'SHFT' , 'DEPT' , '2010-01-01' , '2010-07-27 10:25:13' , 'Y', 'BL');

#
# Work Areas
DELETE FROM `tk_work_area_s`;
INSERT INTO `tk_work_area_s` (`ID`) VALUES (1000);
DELETE FROM `tk_work_area_t`;
INSERT INTO `tk_work_area_t` (`TK_WORK_AREA_ID`, `WORK_AREA`, `EFFDT`,`ACTIVE`,`DESCR`,`DEPT`,`DEFAULT_OVERTIME_EARNCODE`,`ADMIN_DESCR`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`, `OVERTIME_EDIT_ROLE`) VALUES
    ('1'  , '30',    '2010-01-01', 'Y', 'SDR1 Work Area',     'TEST-DEPT', NULL, 'work area admin description', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('100','1234', '2010-01-05', 'Y', 'work area description', 'TEST-DEPT', 'OT1', 'work area admin description', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('101', '2345', '2010-01-05', 'Y', 'work area description2', 'TEST-DEPT2', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('102', '3456', '2010-01-05', 'Y', 'work area description3', 'TEST-DEPT3', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('103', '4567', '2010-01-05', 'Y', 'work area description4', 'TEST-DEPT4', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('104', '1000', '2010-01-05', 'Y', 'loras work area', 'LORA-DEPT', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('105', '1100', '2010-01-01', 'Y', 'shift-workarea', 'SHFT-DEPT', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE'),
    ('106', '5555', '2010-01-05', 'Y', 'work area description5', 'TEST-DEPT5', 'OT1', 'work area admin description2', 'admin', '2010-07-27 10:25:13', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97', '20','TK_EMPLOYEE');

#
# Task
DELETE FROM `tk_task_s`;
INSERT INTO `tk_task_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_task_t`;
INSERT INTO `tk_task_t`(`tk_task_id`,`task`,`work_area`,`tk_work_area_id`,`descr`,`admin_descr`,`obj_id`, `ver_nbr`,`USER_PRINCIPAL_ID`, `effdt`, `active`, `timestamp`)  VALUES
    ('1',  '30', '30',     1  ,'SDR1 task'    , 'admin description 1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now()),
    ('100', '1', '1234', 100,'description 1', 'admin description 1', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now()),
    ('101', '2', '1234', 100,'description 2', 'admin description 2', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now()),
    ('102', '3', '1234', 100,'description 3', 'admin description 3', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now()),
    ('103', '4', '1000', 104,'task 4', 'admin description 4', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now()),
    ('104', '5', '1100', 105,'task 5', 'admin description 4', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE', '1', 'admin', '2010-01-01', 'Y', now());

#
# Assignments
DELETE FROM `tk_assignment_s`;
INSERT INTO `tk_assignment_s` (`ID`) VALUES ('1000');
DELETE FROM `tk_assignment_t`;
INSERT INTO `tk_assignment_t` (`TK_ASSIGNMENT_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`EFFDT`,`WORK_AREA`,`TASK`,`OBJ_ID`,`TIMESTAMP`,`VER_NBR`,`active`) VALUES
    ('1'  , 'admin' , '30', '2010-01-01' , '30' , '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('2'  , 'admin' , '30', '2010-01-01' , '5555', '30', '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('10' , 'admin' , '1' , '2010-08-01' , '1234' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('11' , 'admin' , '2' , '2010-08-01' , '1234' , '2' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('12' , 'eric'  , '1' , '2010-08-01' , '2345' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('13' , 'eric'  , '2' , '2010-08-01' , '3456' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-07-27 10:25:13' , '1' , 'Y')  ,
    ('14' , 'admin' , '3' , '2010-08-15' , '1234' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-08-27 10:25:13' , '1' , 'Y')  ,
    ('15' , 'admin' , '1' , '2010-08-04' , '1234' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-08-27 10:25:13' , '1' , 'Y')  ,
    ('16' , 'admin' , '4' , '2010-08-15' , '1234' , '1' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-08-27 10:25:13' , '1' , 'Y')  ,
    ('17' , 'admin' , '5' , '2010-08-15' , '1000' , '4' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-08-27 10:25:13' , '1' , 'Y')  ,
    ('18' , 'admin' , '6' , '2010-01-01' , '1100' , '5' , '8421CD29-E1F4-4B9A-AE33-F3F4752505CE' , '2010-08-27 10:25:13' , '1' , 'Y')  ;

#
# dept earn code
DELETE FROM `hr_dept_earn_code_s`;
INSERT INTO `hr_dept_earn_code_s` VALUES('1000');
DELETE FROM `hr_dept_earn_code_t`;
INSERT INTO `hr_dept_EARN_CODE_T`
    (`hr_dept_EARN_CODE_ID` , `DEPT`       , `hr_sal_group` , `EARN_CODE` , `EMPLOYEE` , `APPROVER` , `ORG_ADMIN` , `EFFDT`      , `TIMESTAMP`           , `ACTIVE`) VALUES
    ('1'                    , 'TEST-DEPT'  , 'SD1'          , 'RGH'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('2'                    , 'TEST-DEPT'  , 'SD1'          , 'RGN'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('3'                    , 'INVALID'  	 , 'INVALID'      , 'INV'       , 0          , 1          , 0           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')       		,
    ('10'                   , 'TEST-DEPT'  , 'A10'          , 'RGH'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('11'                   , 'TEST-DEPT'  , 'A10'          , 'SCK'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('12'                   , 'TEST-DEPT'  , 'A10'          , 'VAC'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('13'                   , 'TEST-DEPT'  , 'A10'          , 'WEP'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('14'                   , 'TEST-DEPT2' , 'A10'          , 'HAZ'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('15'                   , 'TEST-DEPT2' , 'A10'          , 'HIP'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('16'                   , 'TEST-DEPT2' , 'A10'          , 'OC1'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('17'                   , 'TEST-DEPT2' , 'A10'          , 'OC2'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('18'                   , '%'          , 'A10'          , 'XYZ'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('19'                   , 'TEST-DEPT'  , '%'            , 'XYY'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('20'                   , '%'          , '%'            , 'XZZ'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('21'                   , 'LORA-DEPT'  , 'A10'          , 'RGH'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('22'                   , 'LORA-DEPT'  , 'A10'          , 'SCK'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')               ,
    ('23'                   , 'LORA-DEPT'  , 'A10'          , 'VAC'       , 1          , 1          , 1           , '2010-08-01' , '2010-01-01 08:08:08' , 'Y')       		  ,
    ('24'                    , 'TEST-DEPT'  , 'SD1'          , 'RGH'       , 1          , 1          , 1           , '2010-08-10' , '2010-01-01 08:08:08' , 'Y');

#
# earn code
DELETE FROM `hr_earn_code_s`;
INSERT INTO `hr_earn_code_s` VALUES('1000');
DELETE FROM `hr_earn_code_T`;
INSERT INTO `hr_earn_code_T` (`hr_earn_code_ID`, `EARN_CODE`, `DESCR`, `RECORD_TIME`,`RECORD_HOURS`,`RECORD_AMOUNT`,`EFFDT`, `TIMESTAMP`, `ACTIVE`,`OVT_EARN_CODE`,`ACCRUAL_CATEGORY`) VALUES
    ('1'  , 'SDR' , 'SHIFT DIFF'        , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
    ('2'  , 'LUN' , 'LUNCH'             , '0','1','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('3'  , 'OVT' , 'OVERTIME'          , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'Y',NULL) ,
	('9'  , 'RGN' , 'REGULAR'           , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('10' , 'RGH' , 'REGULAR HOURLY'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('11' , 'SCK' , 'SICK'              , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('12' , 'VAC' , 'VACATION'          , '0','1','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('13' , 'WEP' , 'EMERGENCY'         , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('14' , 'HAZ' , 'HAZARD DAY'        , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' ,'Y', 'N',NULL) ,
	('15' , 'HIP' , 'HOLIDAY INCENTIVE' , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('16' , 'OC1' , 'ON CALL - 1.50'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('17' , 'OC2' , 'ON CALL - 2.00'    , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' ,'Y', 'N',NULL) ,
	('18' , 'PRM' , 'PREMIUM'           , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('19' , 'XYZ' , 'XYZ'               , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('20' , 'XYY' , 'XYY'               , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('21' , 'XZZ' , 'XZZ'               , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL) ,
	('22' , 'TEX' , 'TEX'               , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N','TEX'),
	('23' , 'ABC' , 'ABC'               , '1','0','0', '2010-01-01' , '2010-01-01 08:08:08' , 'Y', 'N',NULL);

# Sal Group
DELETE FROM `hr_sal_group_s`;
INSERT INTO `hr_sal_group_S` (`ID`) VALUES ('1000');
DELETE FROM `hr_sal_group_t`;
INSERT INTO `hr_sal_group_t` (`hr_sal_group_ID`, `hr_sal_group`, `EFFDT`, `TIMESTAMP`, `ACTIVE`) VALUES
    ('1',  'SD1', '2010-01-01', '2010-01-01 01:01:01' , 'Y'),
    ('10', 'A10', '2010-01-01', '2010-01-01 08:08:08' , 'Y'),
    ('11', 'S10', '2010-01-01', '2010-01-01 08:08:08' , 'Y'),
    ('12', 'A12', '2010-01-01', '2010-01-01 08:08:08' , 'Y'),
    ('13', 'S12', '2010-01-01', '2010-01-01 08:08:08' , 'Y'),
    ('14', 'NOP', '2010-01-01', '2010-01-01 08:08:08' , 'Y');

# HR Pay Types
DELETE FROM `hr_paytype_s`;
INSERT INTO `hr_paytype_s` (`ID`)	VALUES	('1000');
DELETE FROM `hr_paytype_t`;
INSERT INTO `hr_paytype_t` (`HR_PAYTYPE_ID`,`PAYTYPE`,`DESCR`,`REG_ERN_CODE`,`EFFDT`,`TIMESTAMP`,`OBJ_ID`,`VER_NBR`,`ACTIVE`) VALUES
	('1', 'BW', 'description', 'RGN', '2010-08-01', '2010-01-01 16:01:07', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1','Y'),
	('2', 'BW', 'description', 'RGN', '2010-01-01', '2010-01-01 16:01:07', '47326FEA-46E7-7D89-0B13-85DFA45EA8C1', '1','Y');


DELETE FROM `hr_earn_group_s`;
INSERT INTO `hr_earn_group_s` VALUES ('1000');
DELETE FROM `hr_earn_group_t`;
INSERT INTO `hr_earn_group_t` (`hr_earn_group_id`,`earn_group`,`descr`,`effdt`,`active`,`OBJ_ID`,`VER_NBR`,`timestamp`) VALUES
  ('1','SD1','Test SD1', '2010-01-01', 'Y', '', 1, '2010-01-01 01:01:01'),
  ('2','SD2','Test SD2', '2010-01-01', 'Y', '', 1, '2010-01-01 01:01:01'),
  ('3','SD3','Test SD3', '2010-01-01', 'Y', '', 1, '2010-01-01 01:01:01'),
	('100','REG','Regular', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13' ),
	('101','OVT','Overtime', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13' ),
	('102','TC1','Regular', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13' ),
	('103','TC2','Regular', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13' ),
	('104','TC3','Regular', '2010-01-01','Y','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97','20','2010-07-27 10:25:13' );

DELETE FROM `hr_earn_group_def_s`;
INSERT INTO `hr_earn_group_def_s` VALUES ('1000');
DELETE FROM `hr_earn_group_def_t`;
INSERT INTO `hr_earn_group_def_t` (`hr_earn_group_def_id`, `hr_earn_group_id`,`earn_code`,`OBJ_ID`,`VER_NBR`) VALUES
  (  '1',  '1','REG','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
  (  '2',  '1','RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
  (  '3',  '2','ABC', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
  (  '4',  '3','XYZ','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	('100','100','REG','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	('101','100','RGN','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	('102','100','RGH','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1),
	('109','101','OVT','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97',1);

DELETE FROM `hr_roles_group_t`;
INSERT INTO `hr_roles_group_t` values ('admin');

DELETE FROM `hr_roles_s`;
INSERT INTO `hr_roles_s` VALUES ('1000');
DELETE FROM `hr_roles_t`;
INSERT INTO `hr_roles_t` (`hr_roles_ID`, `PRINCIPAL_ID`, `ROLE_NAME`, `USER_PRINCIPAL_ID`, `WORK_AREA`, `DEPT`, `EFFDT`, `TIMESTAMP`, `ACTIVE`) VALUES
    ('1', 'admin', 'TK_APPROVER', 'admin', '999', NULL, '2010-08-01', '2010-08-01 15:10:57', 'Y'),
    ('2', 'admin', 'TK_APPROVER', 'admin', '999', NULL, '2010-08-10', '2010-08-10 15:10:57', 'Y'),
    ('3', 'admin', 'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:10:57', 'Y'),
    ('4', 'admin', 'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:11:57', 'Y'),
    ('5', 'admin', 'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:12:57', 'Y'),
    ('6', 'eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-01', '2010-08-01 16:10:57', 'Y'),
    ('7', 'eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-10', '2010-08-10 16:10:57', 'Y'),
    ('8', 'eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:10:57', 'Y'),
    ('9', 'eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:11:57', 'Y'),
    ('10','eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:12:57', 'Y'),
    ('11','admin', 'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-01', '2010-08-01 15:10:57', 'Y'),
    ('12','admin', 'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-10', '2010-08-10 15:10:57', 'Y'),
    ('13','admin', 'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:10:57', 'Y'),
    ('14','admin', 'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:11:57', 'Y'),
    ('15','admin', 'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 15:12:57', 'Y'),
    ('16','eric',  'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-01', '2010-08-01 16:10:57', 'Y'),
    ('17','eric',  'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-10', '2010-08-10 16:10:57', 'Y'),
    ('18','eric',  'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:10:57', 'Y'),
    ('19','eric',  'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:11:57', 'Y'),
    ('20','eric',  'TK_ORG_ADMIN', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:12:57', 'Y'),
    ('21','eric',  'TK_APPROVER', 'admin', '999', NULL, '2010-08-20', '2010-08-20 16:13:57', 'Y'),
    ('22','admin', 'TK_APPROVER', 'admin', '1234', NULL, '2010-01-05', '2010-01-05 15:12:57', 'Y'),
    ('23','admin', 'TK_SYS_ADMIN', 'admin', '999', NULL, '2010-01-01', '2010-01-05 15:12:57', 'Y');

#
# Pay Calendar
DELETE FROM `hr_calendar_s`;
INSERT INTO `hr_calendar_s`	(`ID`)	VALUES	(1000);
insert into hr_calendar_t values ('1', 'BWN-CAL', 'Sun', '00:00:00', 'Pay', 'Test description');
insert into hr_calendar_t values ('2', 'BWS-CAL', 'Sun', '00:00:00', 'Pay', 'Test description');

#
# Pay Calendar Dates
DELETE FROM `hr_calendar_entries_s`;
INSERT INTO `hr_calendar_entries_s`	(`ID`)	VALUES	(1000);
DELETE FROM `hr_calendar_entries_t`;
INSERT INTO `hr_calendar_entries_t` (`hr_calendar_entry_id`,`hr_calendar_id`, `calendar_name`, `begin_period_date`,`end_period_date`,`initiate_date`,`initiate_time`,`end_pay_period_date`,`end_pay_period_time`,`employee_approval_date`,`employee_approval_time`,`supervisor_approval_date`,`supervisor_approval_time`) VALUES
	('1', '2', 'BWS-CAL', '2010-08-01 00:00:00', '2010-08-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('2', '2', 'BWS-CAL', '2010-08-15 00:00:00', '2010-09-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('3', '2', 'BWS-CAL', '2010-09-01 00:00:00', '2010-09-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('4', '2', 'BWS-CAL', '2010-09-15 00:00:00', '2010-10-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('5', '2', 'BWS-CAL', '2010-10-01 00:00:00', '2010-10-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('8', '2', 'BWS-CAL', '2010-10-15 00:00:00', '2010-11-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('9', '2', 'BWS-CAL', '2010-11-01 00:00:00', '2010-11-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('10', '2', 'BWS-CAL', '2010-11-15 00:00:00', '2010-12-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('11', '2', 'BWS-CAL', '2010-01-01 00:00:00', '2010-01-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('12', '2', 'BWS-CAL', '2010-01-15 00:00:00', '2010-02-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('13', '2', 'BWS-CAL', '2010-02-01 00:00:00', '2010-02-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('14', '2', 'BWS-CAL', '2010-02-15 00:00:00', '2010-03-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('15', '2', 'BWS-CAL', '2010-03-01 00:00:00', '2010-03-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('16', '2', 'BWS-CAL', '2010-03-15 00:00:00', '2010-04-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('17', '2', 'BWS-CAL', '2010-04-01 00:00:00', '2010-04-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('18', '2', 'BWS-CAL', '2010-04-15 00:00:00', '2010-05-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('19', '2', 'BWS-CAL', '2010-05-01 00:00:00', '2010-05-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('20', '2', 'BWS-CAL', '2010-05-15 00:00:00', '2010-06-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('21', '2', 'BWS-CAL', '2010-06-01 00:00:00', '2010-06-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('22', '2', 'BWS-CAL', '2010-06-15 00:00:00', '2010-07-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('23', '2', 'BWS-CAL', '2010-07-01 00:00:00', '2010-07-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('24', '2', 'BWS-CAL', '2010-07-15 00:00:00', '2010-08-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('25', '2', 'BWS-CAL', '2010-12-01 00:00:00', '2010-12-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('26', '2', 'BWS-CAL', '2010-12-15 00:00:00', '2011-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('27', '1', 'BW-CAL', '2010-01-01 12:00:00', '2010-01-16 12:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('28', '1', 'BW-CAL', '2010-01-16 12:00:00', '2010-02-01 12:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('29', '2', 'BWS-CAL', '2011-01-01 00:00:00', '2011-01-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('30', '2', 'BWS-CAL', '2011-01-15 00:00:00', '2011-02-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('31', '2', 'BWS-CAL', '2011-02-01 00:00:00', '2011-02-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('32', '2', 'BWS-CAL', '2011-02-15 00:00:00', '2011-03-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('33', '2', 'BWS-CAL', '2011-03-01 00:00:00', '2011-03-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('34', '2', 'BWS-CAL', '2011-03-15 00:00:00', '2011-04-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('35', '2', 'BWS-CAL', '2011-04-01 00:00:00', '2011-04-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('36', '2', 'BWS-CAL', '2011-04-15 00:00:00', '2011-05-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('37', '2', 'BWS-CAL', '2011-05-01 00:00:00', '2011-05-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('38', '2', 'BWS-CAL', '2011-05-15 00:00:00', '2011-06-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('39', '2', 'BWS-CAL', '2011-06-01 00:00:00', '2011-06-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('40', '2', 'BWS-CAL', '2011-06-15 00:00:00', '2011-07-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('41', '2', 'BWS-CAL', '2011-07-01 00:00:00', '2011-07-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('42', '2', 'BWS-CAL', '2011-07-15 00:00:00', '2011-08-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('43', '2', 'BWS-CAL', '2011-08-01 00:00:00', '2011-08-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('44', '2', 'BWS-CAL', '2011-08-15 00:00:00', '2011-08-31 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('45', '2', 'BWS-CAL', '2011-09-01 00:00:00', '2011-09-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('46', '2', 'BWS-CAL', '2011-09-15 00:00:00', '2011-10-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('47', '2', 'BWS-CAL', '2011-10-01 00:00:00', '2011-10-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('48', '2', 'BWS-CAL', '2011-10-15 00:00:00', '2011-11-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('49', '2', 'BWS-CAL', '2011-11-01 00:00:00', '2011-11-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('50', '2', 'BWS-CAL', '2011-11-15 00:00:00', '2011-12-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('51', '2', 'BWS-CAL', '2011-12-01 00:00:00', '2011-12-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('52', '2', 'BWS-CAL', '2011-12-15 00:00:00', '2012-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('53', '2', 'BWS-CAL', '2012-01-01 00:00:00', '2012-01-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('54', '2', 'BWS-CAL', '2012-01-15 00:00:00', '2012-02-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('55', '2', 'BWS-CAL', '2012-02-01 00:00:00', '2012-02-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('56', '2', 'BWS-CAL', '2012-02-15 00:00:00', '2012-03-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('57', '2', 'BWS-CAL', '2012-03-01 00:00:00', '2012-03-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('58', '2', 'BWS-CAL', '2012-03-15 00:00:00', '2012-04-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);



DELETE FROM `tk_daily_overtime_rl_s`;
INSERT INTO `tk_daily_overtime_rl_s` (`ID`) VALUES (1000);

DELETE FROM `HR_WORK_SCHEDULE_ENTRY_S`;
INSERT INTO `HR_WORK_SCHEDULE_ENTRY_S` (`ID`) VALUES (1000);

DELETE FROM `HR_WORK_SCHEDULE_S`;
INSERT INTO `HR_WORK_SCHEDULE_S` (`ID`) VALUES (1000);


DELETE FROM `HR_WORK_SCHEDULE_T`;
#insert into hr_work_schedule_t values('1','test-schedule','2010-01-01','TEST-DEPT','1234','admin','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('2','test-schedule','2010-01-01','TEST-DEPT','-1','admin','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('3','test-schedule','2010-01-01','%','-1','admin','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('4','test-schedule','2010-01-01','TEST-DEPT','1234','%','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('5','test-schedule','2010-01-01','%','-1','%','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('11','test-schedule','2010-01-01','INVALID','1234','%','Y',uuid(),1,now());
#insert into hr_work_schedule_t values('12','test-schedule','2010-01-01','TEST-DEPT','-1','%','Y',uuid(),1,now());


DELETE FROM `tk_time_collection_rl_s`;
INSERT INTO `tk_time_collection_rl_s` VALUES('1000');
DELETE FROM `tk_time_collection_rl_t`;
INSERT INTO `tk_time_collection_rl_t` (`TK_TIME_COLL_RULE_ID`,`DEPT`,`WORK_AREA`,`EFFDT`,`CLOCK_USERS_FL`,`HRS_DISTRIBUTION_FL`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`ACTIVE`) VALUES
  ('1' , 'TEST-DEPT' , 1234 , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 'Y')  ,
  ('2' , '%'         , 1234 , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 'Y')  ,
  ('3' , 'TEST-DEPT' , -1   , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 'Y')  ,
  ('4' , '%'         , -1   , '2010-01-01' , 1 , 1 , 'admin' , '2010-01-01 08:08:08' , 'Y');

DELETE FROM `tk_clock_log_s`;
INSERT INTO `tk_clock_log_s` VALUES('1000');
DELETE FROM `tk_clock_log_t`;
INSERT INTO `tk_clock_log_t` (`TK_CLOCK_LOG_ID`,`PRINCIPAL_ID`,`JOB_NUMBER`,`WORK_AREA`,`TASK`,`TK_WORK_AREA_ID`,`TK_TASK_ID`,`CLOCK_TS`,`CLOCK_TS_TZ`,`CLOCK_ACTION`,`IP_ADDRESS`,`USER_PRINCIPAL_ID`,`TIMESTAMP`,`HR_JOB_ID`,`OBJ_ID`) VALUES
  ('1' , 'admin' , 30 , 30 ,30, 1 , 1 , '2010-01-01 08:08:08', "America/Indianapolis" , "CO","TEST" ,'admin' , '2010-01-01 08:08:08' , 1, '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97')  ;

DELETE FROM `tk_dept_lunch_rl_t`;
INSERT INTO `tk_dept_lunch_rl_t` (`tk_dept_LUNCH_RL_ID`,`DEPT`,`WORK_AREA`, `PRINCIPAL_ID`, `JOB_NUMBER`, `EFFDT`, `REQUIRED_CLOCK_FL`, `MAX_MINS`, `USER_PRINCIPAL_ID`, `TIMESTAMP`, `ACTIVE`, `SHIFT_HOURS`, `DEDUCTION_MINS`, `OBJ_ID`) VALUES
  ('1','INVALID','1234','admin','20','2010-01-01','TST', '30', 'admin', '2010-01-01 08:08:08', 'Y', '2', '30','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97') ,
  ('2','TEST-DEPT','1234','admin','20','2010-01-01','TST', '30', 'admin', '2010-01-01 08:08:08', 'Y', '2', '30','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97') ,
  ('3','TEST-DEPT','9999','admin','20','2010-01-01','TST', '30', 'admin', '2010-01-01 08:08:08', 'Y', '2', '30','7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97') ;

DELETE FROM `hr_holiday_calendar_t`;
INSERT INTO `hr_holiday_calendar_T` (`HR_HOLIDAY_CALENDAR_ID`,`HOLIDAY_CALENDAR_GROUP`,`DESCR`) VALUES
  (1,'REG', "Regular");

DELETE FROM `lm_accrual_category_t`;
INSERT INTO `lm_accrual_category_t` (`LM_ACCRUAL_CATEGORY_ID`,`ACCRUAL_CATEGORY`,`LEAVE_PLAN`,`DESCR`,`ACCRUAL_INTERVAL_EARN`,`UNIT_OF_TIME`,
									 `EFFDT`,`OBJ_ID`,`VER_NBR`,`PRORATION`,`DONATION`,`SHOW_ON_GRID`,`PLANNING_MONTHS`,`ACTIVE`,`TIMESTAMP`,`MIN_PERCENT_WORKED`)
VALUES (1,'TEX','TST','Tex accrual cat','daily','days','2010-01-01',uuid(),1,'Y','N','N',1,'Y',now(),0);

DELETE FROM `lm_accruals_t`;
INSERT INTO `lm_accruals_T` (`LM_ACCRUALS_ID`,`PRINCIPAL_ID`,`ACCRUAL_CATEGORY`, `EFFDT`, `HOURS_ACCRUED`,`HOURS_TAKEN`,`HOURS_ADJUST`,`OBJ_ID`) VALUES
  (1,'admin', "TEX", '2010-01-01', '0.0', '0.0', '0.0', '7EE387AB-26B0-B6A6-9C4C-5B5F687F0E97');

DELETE FROM `tk_time_block_s`;
INSERT INTO `tk_time_block_s` VALUES ('1000');

DELETE FROM `tk_hour_detail_s`;
INSERT INTO `tk_hour_detail_s` VALUES ('1000');

delete from hr_principal_attributes_t;
insert into hr_principal_attributes_t values('admin', 'BWS-CAL', NULL, '2010-01-01', 'Y', 'Y', 'America/Chicago', '2010-01-01', now(), uuid(), 1, 'Y', NULL);
insert into hr_principal_attributes_t values('eric', 'BW-CAL1', NULL, '2010-01-01', 'Y', 'Y',  'America/Chicago', '2010-01-01', now(), uuid(), 1, 'Y', NULL);

delete from tk_system_lunch_rl_t;
insert into tk_system_lunch_rl_t (`TK_SYSTEM_LUNCH_RL_ID`,`EFFDT`,`ACTIVE`,`USER_PRINCIPAL_ID`,`SHOW_LUNCH_BUTTON`) values
(1, '2010-01-01', 'Y', 'admin', 'Y');

delete from tk_grace_period_rl_t;

delete from tk_user_pref_t;
insert into tk_user_pref_t values('admin','America/Chicago');

delete from tk_weekly_ovt_group_t;
insert into tk_weekly_ovt_group_t values(1);
